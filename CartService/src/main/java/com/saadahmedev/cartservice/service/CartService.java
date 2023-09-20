package com.saadahmedev.cartservice.service;

import com.saadahmedev.cartservice.entity.Cart;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    ResponseEntity<?> addProduct(Cart cart);

    ResponseEntity<?> replaceItemCount(Cart cart);

    ResponseEntity<?> increaseItem(long productId);

    ResponseEntity<?> decreaseItem(long productId);

    ResponseEntity<?> removeItem(long productId);

    ResponseEntity<?> getCartItems();
}
