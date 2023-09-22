package com.saadahmedev.apigateway.dto;

public record ApiResponse(int statusCode, boolean status, String message) {
}
