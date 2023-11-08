package com.api.socius.services;

import com.api.socius.models.PostModel;
import com.api.socius.repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    final PostRepository postRepository;
    final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Transactional
    public PostModel save(PostModel postModel) {
        return postRepository.save(postModel);
    }

    public Page<PostModel> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Optional<PostModel> findById(UUID id) {
        return postRepository.findById(id);
    }

    @Transactional
    public void delete(PostModel parkingSpotModel) {
        postRepository.delete(parkingSpotModel);
    }
}
