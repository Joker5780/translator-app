package com.example.translator.dao;

import com.example.translator.domain.TranslationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TranslationDaoJDBC implements TranslationDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void insert(TranslationRequest translationRequest) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("IP", translationRequest.getIP());
        parameterSource.addValue("input", translationRequest.getInputText());
        parameterSource.addValue("output", translationRequest.getOutputText());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into TRANSLATION_REQUEST(IP, inputText, outputText) values (:IP, :input, :output);",
                parameterSource, keyHolder);
    }


}
