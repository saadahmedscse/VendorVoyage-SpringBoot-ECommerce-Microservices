package com.saadahmedev.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface OnMessageReceived {
    void onEvent(String event) throws JsonProcessingException;
}
