package com.saadahmedev.cartservice.service;

import com.saadahmedev.cartservice.dto.ApiResponse;
import com.saadahmedev.cartservice.dto.Token;
import com.saadahmedev.cartservice.dto.UserResponse;
import com.saadahmedev.cartservice.entity.Cart;
import com.saadahmedev.cartservice.feing.AuthService;
import com.saadahmedev.cartservice.repository.CartRepository;
import com.saadahmedev.cartservice.util.DateTimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private AuthService authService;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public ResponseEntity<?> addProduct(HttpServletRequest request, Cart cart) {
        long userId = getUserId(request);
        if (userId == -1) return userNotFound();

        String creationTime = DateTimeUtil.getCurrentDateTime();
        Cart newCart = Cart.builder()
                .productId(cart.getProductId())
                .userId(userId)
                .itemCount(cart.getItemCount())
                .createdAt(creationTime)
                .updatedAt(creationTime)
                .build();

        try {
            cartRepository.save(newCart);
            return new ResponseEntity<>(new ApiResponse(true, "Item added to cart successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> replaceItemCount(HttpServletRequest request, Cart cart) {
        return null;
    }

    @Override
    public ResponseEntity<?> increaseItem(HttpServletRequest request, long productId) {
        return null;
    }

    @Override
    public ResponseEntity<?> decreaseItem(HttpServletRequest request, long productId) {
        return null;
    }

    @Override
    public ResponseEntity<?> removeItem(HttpServletRequest request, long productId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getCartItems(HttpServletRequest request) {
        long userId = getUserId(request);
        if (userId == -1) return userNotFound();

        return new ResponseEntity<>(cartRepository.findCartByUserId(userId), HttpStatus.OK);
    }

    private long getUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        UserResponse user = authService.getUser(new Token(token)).getBody();

        return user == null ? -1 : user.getId();
    }

    private ResponseEntity<?> userNotFound() {
        return new ResponseEntity<>(new ApiResponse(false, "User not found"), HttpStatus.BAD_REQUEST);
    }
}
