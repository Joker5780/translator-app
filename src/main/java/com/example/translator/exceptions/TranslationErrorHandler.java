package com.example.translator.exceptions;

import com.example.translator.domain.TranslationErrorResponse;
import com.example.translator.services.SystemOutputService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class TranslationErrorHandler extends DefaultResponseErrorHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SystemOutputService outputService;

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        String errorText = String.format("HTTP %d %s - %s", response.getStatusCode().value(),
                response.getStatusText(), response.getStatusCode().getReasonPhrase());
        String jsonResponse = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
        jsonResponse = jsonResponse.replaceAll("@type", "type");
        System.out.println(jsonResponse);
        TranslationErrorResponse errorResponse = objectMapper.readValue(jsonResponse, TranslationErrorResponse.class);
        outputService.print(errorResponse.getMessage());
        if (response.getStatusCode().value() == 403) {
            throw new UnsupportedLangException(errorText);
        }
    }
}
