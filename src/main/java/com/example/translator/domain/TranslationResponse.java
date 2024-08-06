package com.example.translator.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TranslationResponse {

    private Translations[] translations;

    @Getter
    @Setter
    public static class Translations {
        private String text;
    }
}
