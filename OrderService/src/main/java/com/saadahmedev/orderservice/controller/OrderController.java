package com.saadahmedev.orderservice.controller;

import com.saadahmedev.orderservice.entity.ShippingDetails;
import com.saadahmedev.orderservice.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(HttpServletRequest request, @RequestBody ShippingDetails shippingDetails) {
        return orderService.createOrder(request, shippingDetails);
    }

    @GetMapping
    public ResponseEntity<?> getOrders(HttpServletRequest request) {
        return orderService.getOrders(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(HttpServletRequest request, @PathVariable long id) {
        return orderService.getOrder(request, id);
    }
}
