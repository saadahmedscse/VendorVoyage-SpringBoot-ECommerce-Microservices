package com.saadahmedev.authservice.service;

import com.saadahmedev.authservice.dto.ApiResponse;
import com.saadahmedev.authservice.dto.CreateAccountRequest;
import com.saadahmedev.authservice.dto.LoginRequest;
import com.saadahmedev.authservice.dto.UserResponse;
import com.saadahmedev.authservice.entity.Token;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    ResponseEntity<?> createAccount(CreateAccountRequest createAccountRequest);

    ResponseEntity<?> login(LoginRequest loginRequest);

    ResponseEntity<?> logout(HttpServletRequest request);

    ResponseEntity<ApiResponse> validateToken(Token token);

    ResponseEntity<UserResponse> getUser(Token token);
}
