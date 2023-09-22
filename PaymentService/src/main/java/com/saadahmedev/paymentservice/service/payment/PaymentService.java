package com.saadahmedev.paymentservice.service.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saadahmedev.paymentservice.dto.OrderEvent;
import com.saadahmedev.paymentservice.dto.PaymentEvent;
import com.saadahmedev.paymentservice.dto.PaymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public void makePayment(OrderEvent orderEvent, PaymentStatus paymentStatus, String message) {
        PaymentEvent paymentEvent = PaymentEvent.builder()
                .userId(orderEvent.getUserId())
                .orderId(orderEvent.getOrderId())
                .paymentStatus(paymentStatus)
                .amount(orderEvent.getAmount())
                .message(message)
                .payType(orderEvent.getPayType())
                .build();

        try {
            String serializedPaymentEvent = new ObjectMapper().writeValueAsString(paymentEvent);
            kafkaTemplate.send("payment-event", serializedPaymentEvent);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
