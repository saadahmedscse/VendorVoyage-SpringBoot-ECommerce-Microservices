package com.saadahmedev.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OnMessageReceived {
    void onEvent(String event) throws JsonProcessingException;
}
