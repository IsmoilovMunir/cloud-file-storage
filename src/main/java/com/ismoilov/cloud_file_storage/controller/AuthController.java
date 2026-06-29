package com.ismoilov.cloud_file_storage.controller;

import com.ismoilov.cloud_file_storage.dto.RegisterRequest;
import com.ismoilov.cloud_file_storage.entity.User;
import com.ismoilov.cloud_file_storage.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<User> createUser (@RequestBody RegisterRequest registerRequest) {

        User user = authService.register(registerRequest);
        return ResponseEntity.status(201).body(user);
    }
}
