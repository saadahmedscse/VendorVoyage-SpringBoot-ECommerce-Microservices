package com.saadahmedev.orderservice.service;

import com.saadahmedev.orderservice.dto.ApiResponse;
import com.saadahmedev.orderservice.dto.OrderPlaceResponse;
import com.saadahmedev.orderservice.dto.Token;
import com.saadahmedev.orderservice.dto.UserResponse;
import com.saadahmedev.orderservice.dto.product.Product;
import com.saadahmedev.orderservice.entity.*;
import com.saadahmedev.orderservice.feign.AuthService;
import com.saadahmedev.orderservice.feign.CartService;
import com.saadahmedev.orderservice.repository.OrderRepository;
import com.saadahmedev.orderservice.repository.PaymentStatusRepository;
import com.saadahmedev.orderservice.repository.ShippingDetailsRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    @Autowired
    private ShippingDetailsRepository shippingDetailsRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private CartService cartService;

    @Override
    public ResponseEntity<?> createOrder(HttpServletRequest request, ShippingDetails shippingDetails) {
        long userId = getUserId(request);
        if (userId == -1) return userNotFound();

        List<Product> productList = cartService.getCartItems(userId).getBody();
        List<Long> productIds = new ArrayList<>();

        double totalPrice = 0;
        double grandTotal = 0;

        assert productList != null;
        for (Product product : productList) {
            productIds.add(product.getId());
            totalPrice += product.getPrice();
            double discountPrice;
            if (product.getDiscount() == 0) discountPrice = product.getPrice();
            else {
                discountPrice = product.getPrice() - (product.getPrice() * product.getDiscount()) / 100;
            }
            grandTotal += discountPrice;
        }

        PaymentStatus paymentStatus = PaymentStatus.builder()
                .transactionId(UUID.randomUUID().toString())
                .status(PaymentStatusEnum.SUCCESS)
                .amount(grandTotal)
                .message("Payment completed successfully")
                .build();

        Date creationTime = new Date();
        Order order = Order.builder()
                .userId(userId)
                .totalPrice(totalPrice)
                .discount(0)
                .grandTotal(grandTotal)
                .productIds(productIds)
                .deliveryStatus(DeliveryStatus.PENDING)
                .shippingDetails(shippingDetails)
                .paymentStatus(paymentStatus) //This will come from payment service using Kafka (SAGA Choreography Architecture)
                .createdAt(creationTime)
                .updatedAt(creationTime)
                .build();

        try {
            shippingDetailsRepository.save(shippingDetails);
            paymentStatusRepository.save(paymentStatus);
            Order savedOrder = orderRepository.save(order);
            return new ResponseEntity<>(new OrderPlaceResponse(true, "Order placed successfully", savedOrder.getId()), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getOrders(HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> getOrder(HttpServletRequest request, String id) {
        return null;
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
