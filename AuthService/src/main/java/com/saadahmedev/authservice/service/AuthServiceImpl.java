package com.saadahmedev.authservice.service;

import com.saadahmedev.authservice.dto.ApiResponse;
import com.saadahmedev.authservice.dto.CreateAccountRequest;
import com.saadahmedev.authservice.dto.LoginRequest;
import com.saadahmedev.authservice.entity.User;
import com.saadahmedev.authservice.repository.UserRepository;
import com.saadahmedev.authservice.util.RequestValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestValidator requestValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> createAccount(CreateAccountRequest createAccountRequest) {
        ResponseEntity<?> validationResult = requestValidator.isCreateAccountRequestValid(createAccountRequest);
        if (validationResult.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST)) return validationResult;

        User user = User.builder()
                .name(createAccountRequest.getName())
                .email(createAccountRequest.getEmail())
                .password(passwordEncoder.encode(createAccountRequest.getPassword()))
                .build();

        try {
            userRepository.save(user);
            return new ResponseEntity<>(new ApiResponse(true, "User created successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> validateToken(String bearerToken) {
        return null;
    }
}
