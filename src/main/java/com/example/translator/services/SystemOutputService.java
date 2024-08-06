package com.example.translator.services;

import org.springframework.stereotype.Service;

@Service
public class SystemOutputService implements OutputService {

    @Override
    public void print(String text) {
        System.out.println(text);
    }
}
