package com.saadahmedev.paymentservice.service.payment;

import com.saadahmedev.paymentservice.dto.OrderEvent;
import com.saadahmedev.paymentservice.dto.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Nagad implements PaymentMethod {

    @Autowired
    private PaymentService paymentService;

    @Override
    public void makePayment(OrderEvent orderEvent) {
        paymentService.makePayment(orderEvent, PaymentStatus.PENDING, "Payment is in pending state");
    }
}
