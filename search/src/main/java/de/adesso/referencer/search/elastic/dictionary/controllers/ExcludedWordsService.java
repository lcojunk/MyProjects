/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.dictionary.controllers;

import de.adesso.referencer.search.config.ElasticSearchConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class ExcludedWordsService {

    private static final Logger LOGGER = Logger.getLogger(ExcludedWordsService.class.getName());

    @Autowired
    private ResourceLoader resourceLoader;

    private List<Exception> exceptions = new ArrayList<>();
    private List<String> excludedWordsList = new ArrayList<>();

    private Date serviceStart = new Date();

    @PostConstruct
    public void init() {
        loadExcludedWordsFromFile();
    }

    public Date getServiceStart() {
        return serviceStart;
    }

    private void loadExcludedWordsFromFile() {
        BufferedReader br;
        excludedWordsList = new ArrayList<>();
        try {
            InputStream is = resourceLoader.getResource("classpath:" + ElasticSearchConfig.EXCLUDED_WORDS_FILENAME).getInputStream();
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Could not read excluded words list file!", ex);
            addException(ex);
            return;
        }
        try {
            String line = br.readLine();
            addWord(line);
            while (line != null) {
                line = br.readLine();
                addWord(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            addException(ex);
            return;
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                addException(ex);
                return;
            }
        }
        return;
    }

    private boolean existsWord(String word) {
        if (word == null || excludedWordsList == null || excludedWordsList.size() == 0) {
            return false;
        }
        for (String excludedWord : excludedWordsList) {
            if (excludedWord != null && word.matches(excludedWord)) {
                return true;
            }
        }
        return false;
    }

    private boolean addWord(String word) {
        if (!existsWord(word)) {
            return excludedWordsList.add(word);
        }
        return false;
    }

    public Map<String, Long> excludeWords(Map<String, Long> wordsMap) {
        if (wordsMap == null || wordsMap.size() == 0 || getExcludedWordsList().size() == 0) {
            return wordsMap;
        }
        for (String word : getExcludedWordsList()) {
            if (wordsMap.get(word) != null) {
                wordsMap.remove(word);
            }
        }
        return wordsMap;
    }

    private void addException(Exception ex) {
        if (exceptions == null) {
            exceptions = new ArrayList<>();
        }
        exceptions.add(ex);
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public List<String> getExcludedWordsList() {
        return excludedWordsList;
    }
}
