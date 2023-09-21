package com.saadahmedev.orderservice.service;

import com.saadahmedev.orderservice.entity.ShippingDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    ResponseEntity<?> createOrder(HttpServletRequest request, ShippingDetails shippingDetails);

    ResponseEntity<?> getOrders(HttpServletRequest request);

    ResponseEntity<?> getOrder(HttpServletRequest request, long id);
}
