package com.saadahmedev.cartservice.feing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @SneakyThrows
    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 503) {
            Map<String, Object> responseBody = parseResponseBody(response);
            String message = (String) responseBody.get("message");

            return new RuntimeException("SNAE_" + message); //Service Not Available Exception
        }

        return FeignException.errorStatus(s, response);
    }

    private Map<String, Object> parseResponseBody(Response response) throws IOException {
        String responseBody = Util.toString(response.body().asReader());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseBody, new TypeReference<>() {});
    }
}
