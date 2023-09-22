package com.saadahmedev.paymentservice.service.payment;

import com.saadahmedev.paymentservice.dto.OrderEvent;
import com.saadahmedev.paymentservice.dto.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("bkash")
public class BKash implements PaymentMethod {

    @Autowired
    private PaymentService paymentService;

    @Override
    public void makePayment(OrderEvent orderEvent) {
        paymentService.makePayment(orderEvent, PaymentStatus.CANCELED, "Payment was canceled by user");
    }
}
