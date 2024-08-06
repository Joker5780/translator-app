package com.example.translator.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class TranslationRequest {

    private String Id;
    private final String IP;
    private final String inputText;
    private final String outputText;

}
