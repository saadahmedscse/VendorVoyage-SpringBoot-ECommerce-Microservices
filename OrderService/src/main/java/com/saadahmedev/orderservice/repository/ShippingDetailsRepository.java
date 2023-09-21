package com.saadahmedev.orderservice.repository;

import com.saadahmedev.orderservice.entity.ShippingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingDetailsRepository extends JpaRepository<ShippingDetails, Long> {
}
