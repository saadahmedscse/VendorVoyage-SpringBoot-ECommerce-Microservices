package com.saadahmedev.productservice.controller;

import com.saadahmedev.productservice.dto.ApiResponse;
import com.saadahmedev.productservice.dto.ProductRequest;
import com.saadahmedev.productservice.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Value("${application.mode}")
    private String applicationMode;

    @PostMapping
    @CircuitBreaker(name = "createProduct", fallbackMethod = "fallback")
    public ResponseEntity<?> createProduct(@Nullable @RequestPart("images") List<MultipartFile> images, @ModelAttribute ProductRequest productRequest) {
        return productService.createProduct(images, productRequest);
    }

    @GetMapping
    @CircuitBreaker(name = "getProducts", fallbackMethod = "fallback")
    public ResponseEntity<?> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "getProduct", fallbackMethod = "fallback")
    public ResponseEntity<?> getProduct(@PathVariable long id) {
        return productService.getProduct(id);
    }

    @PostMapping("/{id}")
    @CircuitBreaker(name = "updateProduct", fallbackMethod = "fallback")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @Nullable @RequestPart("images") List<MultipartFile> images, @ModelAttribute ProductRequest productRequest) {
        return productService.updateProduct(id, images, productRequest);
    }

    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "deleteProduct", fallbackMethod = "fallback")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }

    @DeleteMapping("/image/{id}/{imageId}")
    @CircuitBreaker(name = "deleteImage", fallbackMethod = "fallback")
    public ResponseEntity<?> deleteImage(@PathVariable long id, @PathVariable long imageId) {
        return productService.deleteProductImage(id, imageId);
    }

    @PostMapping("/cart")
    @CircuitBreaker(name = "getCartProducts", fallbackMethod = "fallback")
    public ResponseEntity<?> getCartProducts(@RequestBody List<Long> productIds) {
        return productService.getCartProducts(productIds);
    }

    private ResponseEntity<?> fallback(Exception e) {
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
