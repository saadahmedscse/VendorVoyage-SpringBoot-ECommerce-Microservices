package com.saadahmedev.orderservice.feign;

import com.saadahmedev.orderservice.dto.product.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@FeignClient("CART-SERVICE")
public interface CartService {

    @GetMapping("api/cart/{id}")
    ResponseEntity<List<Product>> getCartItems(@PathVariable long id);
}
