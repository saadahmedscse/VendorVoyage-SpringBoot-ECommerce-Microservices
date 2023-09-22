package com.saadahmedev.paymentservice.service.payment;

import com.saadahmedev.paymentservice.dto.OrderEvent;
import org.springframework.stereotype.Service;

@Service
public interface PaymentMethod {
    void makePayment(OrderEvent orderEvent);
}
