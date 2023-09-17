package com.saadahmedev.inventoryservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {

    ResponseEntity<?> addProduct(long id, int productCount);

    ResponseEntity<?> purchaseProduct(long id, int purchaseCount);

    ResponseEntity<?> getProduct(long id);

    ResponseEntity<?> deleteProduct(long id);
}
