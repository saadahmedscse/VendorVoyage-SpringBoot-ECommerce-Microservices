package com.saadahmedev.cartservice.controller;

import com.saadahmedev.cartservice.dto.ApiResponse;
import com.saadahmedev.cartservice.entity.Cart;
import com.saadahmedev.cartservice.service.CartService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Value("${application.mode}")
    private String applicationMode;

    @PostMapping("/add")
    @CircuitBreaker(name = "addProduct", fallbackMethod = "fallback")
    public ResponseEntity<?> addProduct(HttpServletRequest request, @RequestBody Cart cart) {
        return cartService.addProduct(request, cart);
    }

    @GetMapping("/increase/{id}")
    @CircuitBreaker(name = "increaseItem", fallbackMethod = "fallback")
    public ResponseEntity<?> increaseItem(HttpServletRequest request, @PathVariable long id) {
        return cartService.increaseItem(request, id);
    }

    @GetMapping("/decrease/{id}")
    @CircuitBreaker(name = "decreaseItem", fallbackMethod = "fallback")
    public ResponseEntity<?> decreaseItem(HttpServletRequest request, @PathVariable long id) {
        return cartService.decreaseItem(request, id);
    }

    @DeleteMapping("/remove/{id}")
    @CircuitBreaker(name = "removeItem", fallbackMethod = "fallback")
    public ResponseEntity<?> removeItem(HttpServletRequest request, @PathVariable long id) {
        return cartService.removeItem(request, id);
    }

    @DeleteMapping("/remove")
    @CircuitBreaker(name = "removeCart", fallbackMethod = "fallback")
    public ResponseEntity<?> removeCart(HttpServletRequest request) {
        return cartService.deleteCart(request);
    }

    @GetMapping
    @CircuitBreaker(name = "getCartItems", fallbackMethod = "fallback")
    public ResponseEntity<?> getCartItems(HttpServletRequest request) {
        return cartService.getCartItems(request);
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "getCartItemsByUserId", fallbackMethod = "fallback")
    public ResponseEntity<?> getCartItemsByUserId(@PathVariable long id) {
        return cartService.getCartItemsById(id);
    }

    @DeleteMapping("/remove/user/{id}")
    @CircuitBreaker(name = "removeCartByUserId", fallbackMethod = "fallback")
    public ResponseEntity<?> removeCartByUserId(@PathVariable long id) {
        return cartService.deleteCartById(id);
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
