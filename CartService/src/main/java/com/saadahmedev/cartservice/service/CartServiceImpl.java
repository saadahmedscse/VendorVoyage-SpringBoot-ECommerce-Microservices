package com.saadahmedev.cartservice.service;

import com.saadahmedev.cartservice.entity.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Override
    public ResponseEntity<?> addProduct(Cart cart) {
        return null;
    }

    @Override
    public ResponseEntity<?> replaceItemCount(Cart cart) {
        return null;
    }

    @Override
    public ResponseEntity<?> increaseItem(long productId) {
        return null;
    }

    @Override
    public ResponseEntity<?> decreaseItem(long productId) {
        return null;
    }

    @Override
    public ResponseEntity<?> removeItem(long productId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getCartItems() {
        return null;
    }
}
