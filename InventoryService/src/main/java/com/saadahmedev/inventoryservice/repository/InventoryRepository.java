package com.saadahmedev.inventoryservice.repository;

import com.saadahmedev.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {}
