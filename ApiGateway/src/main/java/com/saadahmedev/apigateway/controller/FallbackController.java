package com.saadahmedev.apigateway.controller;

import com.saadahmedev.apigateway.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping("/product-service")
    public ResponseEntity<?> productFallback() {
        return fallBackResponse("Product");
    }

    @RequestMapping("/cart-service")
    public ResponseEntity<?> cartFallback() {
        return fallBackResponse("Cart");
    }

    @RequestMapping("/auth-service")
    public ResponseEntity<?> authFallback() {
        return fallBackResponse("Auth");
    }

    @RequestMapping("/inventory-service")
    public ResponseEntity<?> inventoryFallback() {
        return fallBackResponse("Inventory");
    }

    @RequestMapping("/order-service")
    public ResponseEntity<?> orderFallback() {
        return fallBackResponse("Order");
    }

    private ResponseEntity<?> fallBackResponse(String serviceName) {
        return new ResponseEntity<>(new ApiResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                false,
                serviceName + " service is currently unavailable, try again after a while."
        ), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
