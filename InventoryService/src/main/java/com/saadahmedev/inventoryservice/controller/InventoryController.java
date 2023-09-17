package com.saadahmedev.inventoryservice.controller;

import com.saadahmedev.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/add/{id}/{count}")
    public ResponseEntity<?> createProduct(@PathVariable long id, @PathVariable("count") int productCount) {
        return inventoryService.addProduct(id, productCount);
    }

    @PostMapping("/purchase/{id}/{count}")
    public ResponseEntity<?> purchaseProduct(@PathVariable long id, @PathVariable("count") int purchaseCount) {
        return inventoryService.purchaseProduct(id, purchaseCount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable long id) {
        return inventoryService.getProduct(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        return inventoryService.deleteProduct(id);
    }
}
