package com.saadahmedev.orderservice.dto;

public record OrderPlaceResponse(boolean status, String message, long orderId) {
}
