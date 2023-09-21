package com.saadahmedev.cartservice.feing;

import com.saadahmedev.cartservice.dto.ApiResponse;
import com.saadahmedev.cartservice.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient("INVENTORY-SERVICE")
public interface InventoryService {

    @GetMapping("/api/inventory/{id}")
    ResponseEntity<InventoryResponse> getProduct(@PathVariable long id);

    @GetMapping("/api/inventory/{id}")
    ResponseEntity<ApiResponse> getProductError(@PathVariable long id);
}
