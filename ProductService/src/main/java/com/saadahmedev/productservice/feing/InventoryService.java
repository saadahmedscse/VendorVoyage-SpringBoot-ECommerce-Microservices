package com.saadahmedev.productservice.feing;

import com.saadahmedev.productservice.dto.ApiResponse;
import com.saadahmedev.productservice.dto.InventoryResponse;
import com.saadahmedev.productservice.util.AppConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@FeignClient(name = "INVENTORY-SERVICE", url = AppConstants.BASE_URL)
public interface InventoryService {

    @PostMapping("/api/inventory/add/{id}/{count}")
    ResponseEntity<ApiResponse> createProduct(@PathVariable long id, @PathVariable("count") int productCount);

    @GetMapping("/api/inventory/{id}")
    ResponseEntity<InventoryResponse> getProduct(@PathVariable long id);

    @DeleteMapping("/api/inventory/{id}")
    ResponseEntity<ApiResponse> deleteProduct(@PathVariable long id);
}
