package com.saadahmedev.cartservice.service;

import com.saadahmedev.cartservice.dto.ApiResponse;
import com.saadahmedev.cartservice.dto.Token;
import com.saadahmedev.cartservice.dto.UserResponse;
import com.saadahmedev.cartservice.entity.Cart;
import com.saadahmedev.cartservice.feing.AuthService;
import com.saadahmedev.cartservice.repository.CartRepository;
import com.saadahmedev.cartservice.util.DateTimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<Cart> optionalCart = cartRepository.findCartByUserIdAndProductId(userId, cart.getProductId());
        String creationTime = DateTimeUtil.getCurrentDateTime();
        Cart newCart;

        if (optionalCart.isEmpty()) {
            newCart = Cart.builder()
                    .productId(cart.getProductId())
                    .userId(userId)
                    .itemCount(cart.getItemCount())
                    .createdAt(creationTime)
                    .updatedAt(creationTime)
                    .build();
        } else {
            Cart c = optionalCart.get();
            newCart = Cart.builder()
                    .id(c.getId())
                    .productId(c.getProductId())
                    .userId(c.getUserId())
                    .itemCount(cart.getItemCount() + c.getItemCount())
                    .createdAt(c.getCreatedAt())
                    .updatedAt(creationTime)
                    .build();
        }

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
    @Transactional
    public ResponseEntity<?> removeItem(HttpServletRequest request, long productId) {
        long userId = getUserId(request);
        Optional<Cart> optionalCart = cartRepository.findCartByUserIdAndProductId(userId, productId);
        if (optionalCart.isEmpty()) return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

        try {
            cartRepository.deleteCartByUserIdAndProductId(userId, productId);
            return new ResponseEntity<>(new ApiResponse(true, "Item removed from cart successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteCart(HttpServletRequest request) {
        long userId = getUserId(request);

        try {
            cartRepository.deleteAllByUserId(userId);
            return new ResponseEntity<>(new ApiResponse(true, "Cart deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
