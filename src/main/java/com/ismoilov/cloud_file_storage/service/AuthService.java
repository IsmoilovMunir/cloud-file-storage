package com.ismoilov.cloud_file_storage.service;

import com.ismoilov.cloud_file_storage.dto.RegisterRequest;
import com.ismoilov.cloud_file_storage.dto.UserDto;
import com.ismoilov.cloud_file_storage.entity.User;
import com.ismoilov.cloud_file_storage.exception.UsernameAlreadyExistsExcaption;
import com.ismoilov.cloud_file_storage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User register (RegisterRequest registerRequest){
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsExcaption("Username already exists");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return userRepository.save(user);
    }
}
