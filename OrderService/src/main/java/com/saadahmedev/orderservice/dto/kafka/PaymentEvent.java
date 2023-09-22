package com.saadahmedev.orderservice.dto.kafka;

import com.saadahmedev.orderservice.entity.PaymentStatusEnum;
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
    private PaymentStatusEnum paymentStatus;
    private PayType payType;
}
