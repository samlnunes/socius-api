package com.api.socius.controllers;

import com.api.socius.dtos.LoginDto;
import com.api.socius.dtos.UserDto;
import com.api.socius.models.UserModel;
import com.api.socius.repositories.UserRepository;
import com.api.socius.services.AuthService;
import com.api.socius.services.UserService;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthController {
    final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid LoginDto loginDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = authService.generateToken((UserModel) auth.getPrincipal());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto) {
        if (userService.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Email is already registered!");
        }

        if (this.userRepository.findByUsername(userDto.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Username is already registered!");
        }

        var userModel = new UserModel();

        String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);
        BeanUtils.copyProperties(userDto, userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }
}
