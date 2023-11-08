package com.api.socius.controllers;

import com.api.socius.dtos.LikeDto;
import com.api.socius.dtos.PostDto;
import com.api.socius.models.PostModel;
import com.api.socius.models.UserModel;
import com.api.socius.services.PostService;
import com.api.socius.services.UserService;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/posts")
public class PostController {
    final PostService postService;
    final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> savePost(@RequestBody @Valid PostDto postDto){
        var postModel = new PostModel();
        BeanUtils.copyProperties(postDto, postModel);

        postModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        postModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        Optional<UserModel> userModelOptional = userService.findById(postDto.getUserId());

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado com o ID: " + postDto.getUserId());
        }

        postModel.setUser(userModelOptional.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(postModel));
    }

    @GetMapping
    public ResponseEntity<Page<PostModel>> getAllPosts(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(postService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOnePost(@PathVariable(value = "id") UUID id){
        Optional<PostModel> postModelOptional = postService.findById(id);
        if (!postModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(postModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable(value = "id") UUID id){
        Optional<PostModel> postModelOptional = postService.findById(id);
        if (postModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }

        postService.delete(postModelOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable(value = "id") UUID id, @RequestBody @Valid PostDto postDto){
        Optional<PostModel> postModelOptional = postService.findById(id);

        if (postModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }

        var postModel = new PostModel();
        BeanUtils.copyProperties(postDto, postModel);

        postModel.setUser(postModelOptional.get().getUser());
        postModel.setId(postModelOptional.get().getId());
        postModel.setCreatedAt(postModelOptional.get().getCreatedAt());
        postModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.OK).body(postService.save(postModel));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Object> likePost(@PathVariable(value = "id") UUID postId, @RequestBody @Valid LikeDto likeDto) {
        Optional<PostModel> postModelOptional = postService.findById(postId);

        if (postModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }

        Long userId = likeDto.getUserId();

        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado com o ID: " + userId);
        }

        PostModel postModel = postModelOptional.get();
        List<UserModel> likes = postModel.getLikes();

        if (likes.stream().anyMatch(user -> user.getId().equals(userId))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já deu 'like' neste post.");
        }

        likes.add(userModelOptional.get());

        postModel.setLikes(likes);
        postService.save(postModel);

        return ResponseEntity.status(HttpStatus.OK).body("Like adicionado com sucesso ao post.");
    }

}
