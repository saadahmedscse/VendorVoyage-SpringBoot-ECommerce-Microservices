package com.saadahmedev.authservice.util;

import com.saadahmedev.authservice.dto.ApiResponse;
import com.saadahmedev.authservice.dto.CreateAccountRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator {

    public ResponseEntity<?> isCreateAccountRequestValid(CreateAccountRequest createAccountRequest) {
        if (createAccountRequest.getName() == null || createAccountRequest.getName().isEmpty()) return generateBadRequest("Name is required");
        if (createAccountRequest.getEmail() == null || createAccountRequest.getEmail().isEmpty()) return generateBadRequest("Email is required");
        if (createAccountRequest.getPassword() == null || createAccountRequest.getPassword().isEmpty()) return generateBadRequest("Password is required");
        if (createAccountRequest.getConfirmPassword() == null || createAccountRequest.getConfirmPassword().isEmpty()) return generateBadRequest("Confirm password is required");
        if (!createAccountRequest.getPassword().equals(createAccountRequest.getConfirmPassword())) return generateBadRequest("Password did not match");

        return ResponseEntity.ok().body(null);
    }

    private ResponseEntity<?> generateBadRequest(String message) {
        return new ResponseEntity<>(new ApiResponse(false, message), HttpStatus.BAD_REQUEST);
    }
}
