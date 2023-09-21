package com.saadahmedev.cartservice.controller;

import com.saadahmedev.cartservice.entity.Cart;
import com.saadahmedev.cartservice.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(HttpServletRequest request, @RequestBody Cart cart) {
        return cartService.addProduct(request, cart);
    }

    @GetMapping("/replace")
    public ResponseEntity<?> replaceItemCount(HttpServletRequest request, @RequestBody Cart cart) {
        return cartService.replaceItemCount(request, cart);
    }

    @GetMapping("/increase/{id}")
    public ResponseEntity<?> increaseItem(HttpServletRequest request, @PathVariable long id) {
        return cartService.increaseItem(request, id);
    }

    @GetMapping("/decrease/{id}")
    public ResponseEntity<?> decreaseItem(HttpServletRequest request, @PathVariable long id) {
        return cartService.decreaseItem(request, id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeItem(HttpServletRequest request, @PathVariable long id) {
        return cartService.removeItem(request, id);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeCart(HttpServletRequest request) {
        return cartService.deleteCart(request);
    }

    @GetMapping
    public ResponseEntity<?> getCartItems(HttpServletRequest request) {
        return cartService.getCartItems(request);
    }
}
