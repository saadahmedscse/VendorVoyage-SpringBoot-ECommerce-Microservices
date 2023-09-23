package com.saadahmedev.orderservice.controller;

import com.saadahmedev.orderservice.dto.ApiResponse;
import com.saadahmedev.orderservice.dto.ShippingDetailsRequest;
import com.saadahmedev.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Value("${application.mode}")
    private String applicationMode;

    @PostMapping
    @CircuitBreaker(name = "createOrder", fallbackMethod = "fallBack")
    public ResponseEntity<?> createOrder(HttpServletRequest request, @RequestBody ShippingDetailsRequest shippingDetails) {
        return orderService.createOrder(request, shippingDetails);
    }

    @GetMapping
    @CircuitBreaker(name = "getOrders", fallbackMethod = "fallBack")
    public ResponseEntity<?> getOrders(HttpServletRequest request) {
        return orderService.getOrders(request);
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "getOrder", fallbackMethod = "fallBack")
    public ResponseEntity<?> getOrder(HttpServletRequest request, @PathVariable long id) {
        return orderService.getOrder(request, id);
    }

    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "deleteOrder", fallbackMethod = "fallBack")
    public ResponseEntity<?> deleteOrder(HttpServletRequest request, @PathVariable long id) {
        return orderService.deleteOrder(request, id);
    }

    private ResponseEntity<?> fallBack(Exception e) {
        if (e.getMessage().startsWith("SNAE_")) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage().split("_")[1]), HttpStatus.SERVICE_UNAVAILABLE);
        } else {
            return new ResponseEntity<>(new ApiResponse(
                    false,
                    applicationMode.equals("debug") ? e.getLocalizedMessage() : "Some of the services are not working, try in a while."
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
