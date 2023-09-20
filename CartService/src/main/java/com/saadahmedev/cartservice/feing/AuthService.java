package com.saadahmedev.cartservice.feing;

import com.saadahmedev.cartservice.dto.ApiResponse;
import com.saadahmedev.cartservice.dto.Token;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient("AUTH-SERVICE")
public interface AuthService {

    @PostMapping("api/auth/validate-token")
    ResponseEntity<ApiResponse> validateToken(@RequestBody Token token);
}
