package com.saadahmedev.orderservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saadahmedev.orderservice.service.OnMessageReceived;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class ConsumerConfig {

    @Autowired
    private OnMessageReceived onMessageReceivedListener;

    @KafkaListener(topics = "payment-event", groupId = "order-group")
    public void onKafkaEventReceived(String event) throws JsonProcessingException {
        onMessageReceivedListener.onEvent(event);
    }
}
