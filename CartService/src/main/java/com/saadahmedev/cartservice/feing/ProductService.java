package com.saadahmedev.cartservice.feing;

import com.saadahmedev.cartservice.dto.product.Product;
import com.saadahmedev.cartservice.util.AppConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@FeignClient(name = "PRODUCT-SERVICE", url = AppConstants.BASE_URL)
public interface ProductService {

    @PostMapping("api/product/cart")
    ResponseEntity<List<Product>> getCartProducts(@RequestBody List<Long> productIds);
}
