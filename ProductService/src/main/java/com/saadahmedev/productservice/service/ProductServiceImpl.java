package com.saadahmedev.productservice.service;

import com.saadahmedev.productservice.dto.ApiResponse;
import com.saadahmedev.productservice.dto.InventoryResponse;
import com.saadahmedev.productservice.dto.ProductRequest;
import com.saadahmedev.productservice.dto.ProductWrapper;
import com.saadahmedev.productservice.entity.Image;
import com.saadahmedev.productservice.entity.Product;
import com.saadahmedev.productservice.feing.InventoryService;
import com.saadahmedev.productservice.repository.ImageRepository;
import com.saadahmedev.productservice.repository.ProductRepository;
import com.saadahmedev.productservice.util.DateTimeUtil;
import com.saadahmedev.productservice.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private InventoryService inventoryService;

    @Override
    public ResponseEntity<?> createProduct(List<MultipartFile> images, ProductRequest productRequest) {
        ResponseEntity<?> validationResult = validatorUtil.isCreateProductRequestValid(images, productRequest);
        if (validationResult.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST)) return validationResult;

        List<Image> imageData;
        try {
            imageData = imageUploadService.uploadImage(images);
        } catch (IOException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String creationTime = DateTimeUtil.getCurrentDateTime();

        Product product = Product.builder()
                .title(productRequest.getTitle())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .discount(productRequest.getDiscount() == null ? 0 : productRequest.getDiscount())
                .images(imageData)
                .createdAt(creationTime)
                .updatedAt(creationTime)
                .build();

        try {
            imageRepository.saveAll(imageData);
            Product p = productRepository.save(product);
            ResponseEntity<ApiResponse> apiResponse = inventoryService.createProduct(p.getId(), productRequest.getStock());
            if (!apiResponse.getStatusCode().isSameCodeAs(HttpStatus.CREATED)) return apiResponse;

            return new ResponseEntity<>(new ApiResponse(true, "Product added successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateProduct(long id, List<MultipartFile> images, ProductRequest productRequest) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty())
            return new ResponseEntity<>(new ApiResponse(false, "Product not found"), HttpStatus.BAD_REQUEST);
        Product product = getProduct(productRequest, optionalProduct);
        List<Image> imageData = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            try {
                imageData = imageUploadService.uploadImage(images);
                for (Image image : imageData) product.getImages().add(image);

            } catch (IOException e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        try {
            imageRepository.saveAll(imageData);
            productRepository.save(product);

            return new ResponseEntity<>(new ApiResponse(true, "Product updated successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static Product getProduct(ProductRequest productRequest, Optional<Product> optionalProduct) {
        Product product = optionalProduct.get();

        if (productRequest.getTitle() != null && !productRequest.getTitle().isEmpty()) product.setTitle(productRequest.getTitle());
        if (productRequest.getDescription() != null && !productRequest.getDescription().isEmpty()) product.setDescription(productRequest.getDescription());
        if (productRequest.getPrice() != null) product.setPrice(productRequest.getPrice());
        if (productRequest.getDiscount() != null) product.setDiscount(productRequest.getDiscount());
        product.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
        return product;
    }

    @Override
    public ResponseEntity<?> deleteProductImage(long id, long imageId) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) return new ResponseEntity<>(new ApiResponse(false, "Product not found"), HttpStatus.BAD_REQUEST);
        Optional<Image> optionalImage = imageRepository.findById(imageId);
        if (optionalImage.isEmpty()) return new ResponseEntity<>(new ApiResponse(false, "Image not found"), HttpStatus.BAD_REQUEST);
        Product product = optionalProduct.get();
        Image image = optionalImage.get();
        product.getImages().removeIf(i -> i.getId().equals(imageId));

        try {
            productRepository.save(product);
            imageRepository.delete(image);
            imageUploadService.deleteImage(image);

            return new ResponseEntity<>(new ApiResponse(true, "Image has been deleted"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductWrapper> productWrappers = new ArrayList<>();

        for (Product p : products) {
            InventoryResponse inventoryResponse = inventoryService.getProduct(p.getId()).getBody();

            productWrappers.add(
                    ProductWrapper.builder()
                            .id(p.getId())
                            .title(p.getTitle())
                            .description(p.getDescription())
                            .price(p.getPrice())
                            .discount(p.getDiscount())
                            .images(p.getImages())
                            .isAvailable(inventoryResponse.getProductCount() > 0)
                            .inStock(inventoryResponse.getProductCount())
                            .createdAt(p.getCreatedAt())
                            .updatedAt(p.getUpdatedAt())
                            .build()
            );
        }

        return new ResponseEntity<>(productWrappers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) return new ResponseEntity<>(new ApiResponse(false, "Product not found"), HttpStatus.BAD_REQUEST);

        Product p = optionalProduct.get();
        InventoryResponse inventoryResponse = inventoryService.getProduct(p.getId()).getBody();
        ProductWrapper productWrapper = ProductWrapper.builder()
                .id(p.getId())
                .title(p.getTitle())
                .description(p.getDescription())
                .price(p.getPrice())
                .discount(p.getDiscount())
                .images(p.getImages())
                .isAvailable(inventoryResponse.getProductCount() > 0)
                .inStock(inventoryResponse.getProductCount())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();

        return new ResponseEntity<>(productWrapper, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) return new ResponseEntity<>(new ApiResponse(false, "Product not found"), HttpStatus.BAD_REQUEST);
        Product product = optionalProduct.get();
        List<Image> images = product.getImages();

        try {
            productRepository.delete(product);
            inventoryService.deleteProduct(product.getId());
            imageUploadService.deleteImages(images);
            for (Image image : images) imageRepository.delete(imageRepository.findById(image.getId()).get());

            return new ResponseEntity<>(new ApiResponse(true, "Product has been deleted"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getCartProducts(List<Long> productIds) {
        List<Product> productList = new ArrayList<>();

        for (Long id : productIds) {
            Optional<Product> product = productRepository.findById(id);
            product.ifPresent(productList::add);
        }

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
}
