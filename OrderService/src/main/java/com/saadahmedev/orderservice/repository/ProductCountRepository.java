package com.saadahmedev.orderservice.repository;

import com.saadahmedev.orderservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCountRepository extends JpaRepository<Product, Long> {
}
