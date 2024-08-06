package com.example.translator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jdk.jfr.Name;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
public class TranslationErrorResponse {

    private Integer code;
    private String message;
    private Details[] details;

    @Getter
    @Setter
    public static class Details {

        private String type;
        private String requestId;
    }
}
