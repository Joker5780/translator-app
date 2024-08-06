package com.example.translator.exceptions;

public class ThreadPoolShutdownException extends RuntimeException {

    public ThreadPoolShutdownException(String message) {
        super(message);
    }
}
