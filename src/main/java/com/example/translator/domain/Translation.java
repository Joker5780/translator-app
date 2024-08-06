package com.example.translator.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Translation {

    private String sourceLang;
    private String targetLang;
    private String text;

}
