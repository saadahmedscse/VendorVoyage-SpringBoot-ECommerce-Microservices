package com.saadahmedev.cartservice.controller;

import com.saadahmedev.cartservice.entity.Cart;
import com.saadahmedev.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Cart cart) {
        return cartService.addProduct(cart);
    }

    @GetMapping("/replace")
    public ResponseEntity<?> replaceItemCount(@RequestBody Cart cart) {
        return cartService.replaceItemCount(cart);
    }

    @GetMapping("/increase/{id}")
    public ResponseEntity<?> increaseItem(@PathVariable long id) {
        return cartService.increaseItem(id);
    }

    @GetMapping("/decrease/{id}")
    public ResponseEntity<?> decreaseItem(@PathVariable long id) {
        return cartService.decreaseItem(id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeItem(@PathVariable long id) {
        return cartService.removeItem(id);
    }

    @GetMapping
    public ResponseEntity<?> getCartItems() {
        return cartService.getCartItems();
    }
}
