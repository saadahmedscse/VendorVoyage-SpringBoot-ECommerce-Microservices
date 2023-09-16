package com.saadahmedev.productservice.util;

import com.saadahmedev.productservice.dto.ApiResponse;
import com.saadahmedev.productservice.dto.ProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ValidatorUtil {

    public ResponseEntity<?> isCreateProductRequestValid(List<MultipartFile> images, ProductRequest productRequest) {
        if (images.isEmpty()) return generateErrorResponse("At least one image is required");
        if (productRequest.getTitle() == null || productRequest.getTitle().isEmpty()) return generateErrorResponse("Title is required");
        if (productRequest.getDescription() == null || productRequest.getDescription().isEmpty()) return generateErrorResponse("Description is required");
        if (productRequest.getPrice() == null) return generateErrorResponse("Price is required");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<?> generateErrorResponse(String message) {
        return new ResponseEntity<>(new ApiResponse(false, message), HttpStatus.BAD_REQUEST);
    }
}
