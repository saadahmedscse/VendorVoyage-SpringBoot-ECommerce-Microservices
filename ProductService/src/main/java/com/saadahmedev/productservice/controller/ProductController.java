package com.saadahmedev.productservice.controller;

import com.saadahmedev.productservice.dto.ProductRequest;
import com.saadahmedev.productservice.service.ProductService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<?> createProduct(@Nullable @RequestPart("images") List<MultipartFile> images, @ModelAttribute ProductRequest productRequest) {
        return productService.createProduct(images, productRequest);
    }

    @GetMapping
    public ResponseEntity<?> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable long id) {
        return productService.getProduct(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @Nullable @RequestPart("images") List<MultipartFile> images, @ModelAttribute ProductRequest productRequest) {
        return productService.updateProduct(id, images, productRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }

    @DeleteMapping("/image/{id}/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable long id, @PathVariable long imageId) {
        return productService.deleteProductImage(id, imageId);
    }

    @PostMapping("/cart")
    public ResponseEntity<?> getCartProducts(@RequestBody List<Long> productIds) {
        return productService.getCartProducts(productIds);
    }
}
