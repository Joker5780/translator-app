package com.example.translator.services;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Scanner;

@Service
public class SystemInputService implements InputService {

    private final Scanner scanner;

    public SystemInputService(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }


}
