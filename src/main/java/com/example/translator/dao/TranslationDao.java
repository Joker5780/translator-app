package com.example.translator.dao;

import com.example.translator.domain.TranslationRequest;

public interface TranslationDao {

    void insert(TranslationRequest translationRequest);
}
