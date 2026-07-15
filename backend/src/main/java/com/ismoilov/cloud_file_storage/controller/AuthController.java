package com.ismoilov.cloud_file_storage.controller;

import com.ismoilov.cloud_file_storage.dto.RegisterRequest;
import com.ismoilov.cloud_file_storage.entity.User;
import com.ismoilov.cloud_file_storage.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Auth", description = "Auth management API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @Operation(summary = "create User ", description = "create User to storage")
    @PostMapping("register")
    public ResponseEntity<User> createUser (@RequestBody RegisterRequest registerRequest) {

        User user = authService.register(registerRequest);
        return ResponseEntity.status(201).body(user);
    }
}
