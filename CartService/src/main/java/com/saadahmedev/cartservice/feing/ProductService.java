package com.saadahmedev.cartservice.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@FeignClient("PRODUCT-SERVICE")
public interface ProductService {

    @PostMapping("api/product/cart")
    ResponseEntity<?> getCartProducts(@RequestBody List<Long> productIds);
}