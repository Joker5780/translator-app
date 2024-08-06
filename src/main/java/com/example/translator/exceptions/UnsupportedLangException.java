package com.example.translator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedLangException extends RuntimeException {

    public UnsupportedLangException(String message) {
        super(message);
    }
}
