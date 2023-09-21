package com.saadahmedev.cartservice.service;

import com.saadahmedev.cartservice.entity.Cart;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    ResponseEntity<?> addProduct(HttpServletRequest request, Cart cart);

    ResponseEntity<?> increaseItem(HttpServletRequest request, long productId);

    ResponseEntity<?> decreaseItem(HttpServletRequest request, long productId);

    ResponseEntity<?> removeItem(HttpServletRequest request, long productId);

    ResponseEntity<?> deleteCart(HttpServletRequest request);

    ResponseEntity<?> getCartItems(HttpServletRequest request);
}
