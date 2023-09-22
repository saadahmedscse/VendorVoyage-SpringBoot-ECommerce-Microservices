package com.saadahmedev.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saadahmedev.paymentservice.dto.OrderEvent;
import com.saadahmedev.paymentservice.service.payment.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventService implements OnMessageReceived {

    @Autowired
    @Qualifier("bkash")
    private PaymentMethod bkash;

    @Autowired
    @Qualifier("card")
    private PaymentMethod card;

    @Autowired
    @Qualifier("rocket")
    private PaymentMethod rocket;

    @Autowired
    @Qualifier("nagad")
    private PaymentMethod nagad;

    @Override
    public void onEvent(String event) throws JsonProcessingException {
        OrderEvent orderEvent = new ObjectMapper().readValue(event, OrderEvent.class);

        switch (orderEvent.getPayType()) {
            case CARD -> card.makePayment(orderEvent);
            case BKASH -> bkash.makePayment(orderEvent);
            case ROCKET -> rocket.makePayment(orderEvent);
            case NAGAD -> nagad.makePayment(orderEvent);
        }
    }
}
