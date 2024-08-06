package com.example.translator.services;

import com.example.translator.dao.TranslationDaoImpl;
import com.example.translator.dao.TranslationDaoJDBC;
import com.example.translator.domain.Translation;
import com.example.translator.domain.TranslationRequest;
import com.example.translator.domain.TranslationResponse;
import com.example.translator.domain.TranslationRest;
import com.example.translator.exceptions.ThreadPoolShutdownException;
import com.example.translator.exceptions.TranslationErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl {

    private final TranslationDaoJDBC translationDaoJDBC;
    private final SystemOutputService outputService;
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    private final List<String> translatedWords = new ArrayList<>();
    private final String ERROR_FLG = "HTTP 400 Bad Request - unsupported source_language_code";

    public String translateText(Translation translation, String ip) {
        String[] words = translation.getText().split(" ");

        for (String word : words) {
            executor.submit(() -> {
                try {
                    ResponseEntity<TranslationResponse> responseEntity = postTranslationRequest(translation, word);
                    if (responseEntity.getStatusCode().is2xxSuccessful()) {
                        translatedWords.add(responseEntity.getBody().getTranslations()[0].getText());
                    } else {
                        translatedWords.add(ERROR_FLG);
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        try {
            executor.shutdown();
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(15, TimeUnit.SECONDS)) {
                    throw new ThreadPoolShutdownException("Невозможно остановить пул соединение");
                }
            }
        } catch(InterruptedException e) {
            executor.shutdownNow();
            e.printStackTrace();

        }

        String translatedPhrase = concatTranslatedWords(translatedWords);
        saveToDB(ip, translation.getText(), translatedPhrase);

        return translatedPhrase;
    }

    private ResponseEntity<TranslationResponse> postTranslationRequest(Translation translation, String word) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new TranslationErrorHandler(outputService));

        String url = "https://translate.api.cloud.yandex.net/translate/v2/translate";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("t1.9euelZqPnMzNl5yanZzKl5qdl42ale3rnpWanpfPksePyp6Pz5OZyZmVmI3l8_cGXDVK-e96Zi8Z_t3z90YKM0r573pmLxn-zef1656VmsiUyJjNno2YlMnKnMvJk8mU7_zF656VmsiUyJjNno2YlMnKnMvJk8mU.L2grGaMrK_lndmdfv9FB4oN_fHa4utpYIz9z0_XSxTfpUPkM9-8AWRdMOZaZM6jLz7xL43JmJY5xlu3S8jmfDA");
        TranslationRest translationRest = new TranslationRest(translation.getSourceLang(),
                translation.getTargetLang(), new String[]{word}, "b1gpk00a14mtcf2f159u");

        HttpEntity<TranslationRest> request = new HttpEntity<>(translationRest, headers);
        return restTemplate.postForEntity(url, request, TranslationResponse.class);
   }


   public void saveToDB(String ip, String sourceText, String translatedText) {
       TranslationRequest translationRequest = new TranslationRequest(ip, sourceText, translatedText);
       translationDaoJDBC.insert(translationRequest);
   }

   private String concatTranslatedWords(List<String> translatedWords) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : translatedWords) {
            if (word.equals(ERROR_FLG)) {
                return ERROR_FLG;
            }
            stringBuilder.append(word);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
   }


}
