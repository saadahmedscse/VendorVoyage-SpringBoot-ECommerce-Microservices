package com.saadahmedev.orderservice.service;

import com.saadahmedev.orderservice.dto.ShippingDetailsRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    ResponseEntity<?> createOrder(HttpServletRequest request, ShippingDetailsRequest shippingDetailsRequest);

    ResponseEntity<?> getOrders(HttpServletRequest request);

    ResponseEntity<?> getOrder(HttpServletRequest request, long id);

    ResponseEntity<?> deleteOrder(HttpServletRequest request, long id);
}
