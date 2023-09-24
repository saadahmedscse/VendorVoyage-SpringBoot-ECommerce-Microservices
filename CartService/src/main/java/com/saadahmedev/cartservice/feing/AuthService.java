package com.saadahmedev.cartservice.feing;

import com.saadahmedev.cartservice.dto.ApiResponse;
import com.saadahmedev.cartservice.dto.Token;
import com.saadahmedev.cartservice.dto.UserResponse;
import com.saadahmedev.cartservice.util.AppConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(name = "AUTH-SERVICE", url = AppConstants.BASE_URL)
public interface AuthService {

    @PostMapping("api/auth/validate-token")
    ResponseEntity<ApiResponse> validateToken(@RequestBody Token token);

    @GetMapping("api/auth/user")
    ResponseEntity<UserResponse> getUser(@RequestBody Token token);
}
