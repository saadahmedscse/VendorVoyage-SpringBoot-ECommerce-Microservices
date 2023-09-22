package com.saadahmedev.orderservice.dto.kafka;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentEvent {
    private long userId;
    private long orderId;
    private double amount;
    private String message;
    private PaymentStatus paymentStatus;
    private PayType payType;
}
