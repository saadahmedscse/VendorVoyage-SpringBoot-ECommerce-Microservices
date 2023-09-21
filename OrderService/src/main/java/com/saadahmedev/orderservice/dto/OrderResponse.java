package com.saadahmedev.orderservice.dto;

public record OrderResponse(boolean status, String message, long orderId) {
}
