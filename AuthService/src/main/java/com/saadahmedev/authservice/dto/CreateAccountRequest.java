package com.saadahmedev.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}
