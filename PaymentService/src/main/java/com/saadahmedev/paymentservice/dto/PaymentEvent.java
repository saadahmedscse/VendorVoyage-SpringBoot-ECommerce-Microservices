package com.saadahmedev.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {
    private long userId;
    private long orderId;
    private double amount;
    private String message;
    private PaymentStatus paymentStatus;
    private PayType payType;
}
