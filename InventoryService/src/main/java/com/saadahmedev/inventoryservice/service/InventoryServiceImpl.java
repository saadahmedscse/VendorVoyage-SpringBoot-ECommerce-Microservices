package com.saadahmedev.inventoryservice.service;

import com.saadahmedev.inventoryservice.dto.ApiResponse;
import com.saadahmedev.inventoryservice.entity.Inventory;
import com.saadahmedev.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public ResponseEntity<?> addProduct(long id, int productCount) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
        Inventory inventory = new Inventory();
        inventory.setProductId(id);
        optionalInventory.ifPresent(value -> inventory.setProductCount(value.getProductCount() + productCount));
        inventory.setProductCount(optionalInventory.map(value -> value.getProductCount() + productCount).orElse(productCount));

        try {
            inventoryRepository.save(inventory);
            return new ResponseEntity<>(new ApiResponse(true, "Product added successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> purchaseProduct(long id, int purchaseCount) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
        if (optionalInventory.isEmpty()) return new ResponseEntity<>(new ApiResponse(false, "Product not found in inventory"), HttpStatus.BAD_REQUEST);

        Inventory inventory = optionalInventory.get();
        if (purchaseCount > inventory.getProductCount()) return new ResponseEntity<>(new ApiResponse(false, "Only " + inventory.getProductCount() + " products are in stock"), HttpStatus.BAD_REQUEST);

        inventory.setProductCount(inventory.getProductCount() - purchaseCount);

        try {
            inventoryRepository.save(inventory);
            return new ResponseEntity<>(new ApiResponse(true, "Product updated successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getProduct(long id) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
        if (optionalInventory.isEmpty()) return new ResponseEntity<>(new ApiResponse(false, "Product not found in inventory"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(optionalInventory.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteProduct(long id) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
        if (optionalInventory.isEmpty()) return new ResponseEntity<>(new ApiResponse(false, "Product not found in inventory"), HttpStatus.BAD_REQUEST);
        try {
            inventoryRepository.delete(optionalInventory.get());
            return new ResponseEntity<>(new ApiResponse(true, "Product deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
