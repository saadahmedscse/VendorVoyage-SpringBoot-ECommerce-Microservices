package com.saadahmedev.orderservice.dto.kafka;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderEvent {
    private long userId;
    private long orderId;
    private double amount;
    private String message;
    private PayType payType;
}
