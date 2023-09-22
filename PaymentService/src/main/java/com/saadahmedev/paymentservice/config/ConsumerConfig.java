package com.saadahmedev.paymentservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saadahmedev.paymentservice.service.OnMessageReceived;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class ConsumerConfig {

    @Autowired
    private OnMessageReceived onMessageReceivedListener;

    @KafkaListener(topics = "order-event", groupId = "payment-group")
    private void onKafkaEventReceived(String event) throws JsonProcessingException {
        onMessageReceivedListener.onEvent(event);
    }
}
