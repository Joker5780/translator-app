package com.example.translator.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
@Setter
public class TranslationRest implements Serializable {

    private final String sourceLanguageCode;
    private final String targetLanguageCode;
    private final String[] texts;
    private final String folderId;
}
