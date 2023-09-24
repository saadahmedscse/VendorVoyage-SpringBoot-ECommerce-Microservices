package com.saadahmedev.inventoryservice.controller;

import com.saadahmedev.inventoryservice.dto.ApiResponse;
import com.saadahmedev.inventoryservice.service.InventoryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Value("${application.mode}")
    private String applicationMode;

    @PostMapping("/add/{id}/{count}")
    @CircuitBreaker(name = "createProduct", fallbackMethod = "fallback")
    public ResponseEntity<?> createProduct(@PathVariable long id, @PathVariable("count") int productCount) {
        return inventoryService.addProduct(id, productCount);
    }

    @PostMapping("/purchase/{id}/{count}")
    @CircuitBreaker(name = "purchaseProduct", fallbackMethod = "fallback")
    public ResponseEntity<?> purchaseProduct(@PathVariable long id, @PathVariable("count") int purchaseCount) {
        return inventoryService.purchaseProduct(id, purchaseCount);
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "getProduct", fallbackMethod = "fallback")
    public ResponseEntity<?> getProduct(@PathVariable long id) {
        return inventoryService.getProduct(id);
    }

    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "deleteProduct", fallbackMethod = "fallback")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        return inventoryService.deleteProduct(id);
    }

    private ResponseEntity<?> fallback(Exception e) {
        return new ResponseEntity<>(new ApiResponse(
                false,
                applicationMode.equals("debug") ? e.getLocalizedMessage() : "Some of the services are not working, try in a while."
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
