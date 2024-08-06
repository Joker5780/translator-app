package com.example.translator.controllers;

import com.example.translator.domain.Translation;
import com.example.translator.exceptions.ThreadPoolShutdownException;
import com.example.translator.exceptions.UnsupportedLangException;
import com.example.translator.services.TranslationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class TranslationController {

    private final TranslationServiceImpl translationService;

    @PostMapping(value = "/translate", consumes = "application/json")
    public String translate(@RequestBody Translation translation , HttpServletRequest request) {
        String result = translationService.translateText(translation, request.getRemoteAddr());
        if (!result.equals("HTTP 400 Bad Request - unsupported source_language_code")) {
            return result;
        } else {
            throw new UnsupportedLangException("HTTP 400 Bad Request - unsupported source_language_code");
        }
    }
}
