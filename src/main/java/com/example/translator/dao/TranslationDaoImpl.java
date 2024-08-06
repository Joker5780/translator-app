package com.example.translator.dao;

import com.example.translator.domain.Translation;
import com.example.translator.services.SystemInputService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TranslationDaoImpl {

    private final SystemInputService inputService;

    private final String pattern = "(\\w)[→](\\w)";

    public Translation getTranslation() {
        String inputLang = inputService.readLine();
        // create separator in properties
        String[] langArray = inputLang.split("→");
        String sourceLang = langArray[0].trim();
        String targetLang = langArray[1].trim();
        String text = inputService.readLine().trim();
        return new Translation(sourceLang, targetLang, text);
    }
}
