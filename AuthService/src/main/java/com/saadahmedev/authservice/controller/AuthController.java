package com.saadahmedev.authservice.controller;

import com.saadahmedev.authservice.dto.CreateAccountRequest;
import com.saadahmedev.authservice.dto.LoginRequest;
import com.saadahmedev.authservice.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        return authService.createAccount(createAccountRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return authService.logout(request);
    }

    @GetMapping("/validate-token/{token}")
    public ResponseEntity<?> validateToken(@PathVariable String token) {
        return authService.validateToken(token);
    }
}