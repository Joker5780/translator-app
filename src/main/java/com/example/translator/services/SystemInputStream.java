package com.example.translator.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component("systemISComponent")
public class SystemInputStream {

    @Bean(name = "systemInputStream")
    public InputStream getSystemInputStream() {
        return System.in;
    }
}
