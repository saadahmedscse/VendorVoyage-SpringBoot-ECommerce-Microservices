package com.saadahmedev.authservice.service;

import com.saadahmedev.authservice.dto.CreateAccountRequest;
import com.saadahmedev.authservice.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    ResponseEntity<?> createAccount(CreateAccountRequest createAccountRequest);

    ResponseEntity<?> login(LoginRequest loginRequest);

    ResponseEntity<?> logout(HttpServletRequest request);
}
