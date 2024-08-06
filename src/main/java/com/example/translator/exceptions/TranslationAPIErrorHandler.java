package com.example.translator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TranslationAPIErrorHandler extends ResponseEntityExceptionHandler {

    private String BAD_REQUEST = "BAD_REQUEST";

    @ExceptionHandler(UnsupportedLangException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException
            (UnsupportedLangException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(BAD_REQUEST, ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
