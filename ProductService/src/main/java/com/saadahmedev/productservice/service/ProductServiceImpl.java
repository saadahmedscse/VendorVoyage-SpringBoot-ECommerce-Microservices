package com.saadahmedev.productservice.service;

import com.saadahmedev.productservice.dto.ApiResponse;
import com.saadahmedev.productservice.dto.ProductRequest;
import com.saadahmedev.productservice.entity.Image;
import com.saadahmedev.productservice.entity.Product;
import com.saadahmedev.productservice.repository.ImageRepository;
import com.saadahmedev.productservice.repository.ProductRepository;
import com.saadahmedev.productservice.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Override
    public ResponseEntity<?> createProduct(List<MultipartFile> images, ProductRequest productRequest) {
        ResponseEntity<?> validationResult = validatorUtil.isCreateProductRequestValid(images, productRequest);
        if (validationResult.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST)) return validationResult;

        List<Image> imageData = new ArrayList<>();
        for (MultipartFile file : images) {
            try {
                imageData.add(
                        Image.builder()
                                .filename(file.getOriginalFilename())
                                .imageData(file.getBytes())
                                .build()
                );
            } catch (IOException e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        Product product = Product.builder()
                .title(productRequest.getTitle())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .discount(productRequest.getDiscount() == null ? 0 : productRequest.getDiscount())
                .images(imageData)
                .build();

        try {
            imageRepository.saveAll(imageData);
            productRepository.save(product);

            return new ResponseEntity<>(new ApiResponse(true, "Product added successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateProduct(long id, List<MultipartFile> images, ProductRequest productRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> getProducts() {
        return null;
    }

    @Override
    public ResponseEntity<?> getProduct(long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteProduct(long id) {
        return null;
    }
}