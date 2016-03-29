/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.dictionary.controllers;

import de.adesso.referencer.search.config.ElasticSearchConfig;
import de.adesso.referencer.search.elastic.dictionary.controllers.dto.SplitStringInTokenException;
import de.adesso.referencer.search.elastic.dictionary.controllers.dto.SuggestWordsPayLoad;
import de.adesso.referencer.search.elastic.dictionary.entities.WordFromReference;
import de.adesso.referencer.search.elastic.entities.Branch;
import de.adesso.referencer.search.elastic.entities.Entity;
import de.adesso.referencer.search.elastic.entities.Reference;
import de.adesso.referencer.search.elastic.entities.Technology;
import de.adesso.referencer.search.helper.MyHelpMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class ReferenceTokenizer {

    private String indexName = ElasticSearchConfig.DICTIONARY_INDEX_NAME;

    private String analyzerName = ElasticSearchConfig.SUGGESTWORD_ANALYZER_NAME;

    private static final int BRANCH_WEIGHT = 2, LOB_WEIGHT = 2, TECH_WEIGHT = 2,
            PROJECTNAME_WEIGHT = 3, CLIENTNAME_WEIGHT = 3,
            FREE_TEXT_WEIGHT = 1;

    private static final Long FULL_ENTITY_WEIGHT = 5L;
    private static final Integer MINIMAL_WEIGHT = 1;

    @Autowired
    private ExcludedWordsService excludedWordsService;

    private Map<String, Long> getWordsFromEntity(Entity entity) throws SplitStringInTokenException {
        if (entity == null) {
            return null;
        }
        Map<String, Long> result = countWordsInString(entity.getName());
        if (result != null) {
            result.put(entity.getName(), FULL_ENTITY_WEIGHT);
        }
        return result;
    }

    private Map<String, Long> getWordsFromBranches(List<Branch> entities) throws SplitStringInTokenException {
        if (entities == null) {
            return null;
        }
        Map<String, Long> result = new HashMap<>();
        if (entities.size() == 0) {
            return result;
        }
        for (Entity entity : entities) {
            result = MyHelpMethods.mergeMapOfWords(result, getWordsFromEntity(entity));
        }
        return result;
    }

    private Map<String, Long> getWordsFromTech(List<Technology> entities) throws SplitStringInTokenException {
        if (entities == null) {
            return null;
        }
        Map<String, Long> result = new HashMap<>();
        if (entities.size() == 0) {
            return result;
        }
        for (Entity entity : entities) {
            result = MyHelpMethods.mergeMapOfWords(result, getWordsFromEntity(entity));
        }
        return result;
    }

    public Map<String, Long> getWordsFromReference(Reference reference) throws SplitStringInTokenException {
        if (reference == null) {
            return null;
        }
        Map<String, Long> result = MyHelpMethods.setWeight(countWordsInString(reference.getProjectname()), PROJECTNAME_WEIGHT);
        result = MyHelpMethods.mergeMapOfWords(result,
                MyHelpMethods.setWeight(countWordsInString(reference.getClientname()), CLIENTNAME_WEIGHT));
        result = MyHelpMethods.mergeMapOfWords(result,
                MyHelpMethods.setWeight(getWordsFromEntity(reference.getLob()), LOB_WEIGHT));
        result = MyHelpMethods.mergeMapOfWords(result,
                MyHelpMethods.setWeight(getWordsFromBranches(reference.getBranches()), BRANCH_WEIGHT));
        result = MyHelpMethods.mergeMapOfWords(result,
                MyHelpMethods.setWeight(getWordsFromTech(reference.getTechnologies()), TECH_WEIGHT));
        result = MyHelpMethods.mergeMapOfWords(result,
                MyHelpMethods.setWeight(countWordsInString(reference.getClientProfil()), FREE_TEXT_WEIGHT));
        result = MyHelpMethods.mergeMapOfWords(result,
                MyHelpMethods.setWeight(countWordsInString(reference.getProjectBackground()), FREE_TEXT_WEIGHT));
        result = MyHelpMethods.mergeMapOfWords(result,
                MyHelpMethods.setWeight(countWordsInString(reference.getProjectSolution()), FREE_TEXT_WEIGHT));
        result = MyHelpMethods.mergeMapOfWords(result,
                MyHelpMethods.setWeight(countWordsInString(reference.getPreciseDescription()), FREE_TEXT_WEIGHT));
        result = MyHelpMethods.mergeMapOfWords(result,
                MyHelpMethods.setWeight(countWordsInString(reference.getProjectResults()), FREE_TEXT_WEIGHT));
        result = MyHelpMethods.mergeMapOfWords(result,
                MyHelpMethods.setWeight(countWordsInString(reference.getDescription()), FREE_TEXT_WEIGHT));
        return result;
    }

    public List<WordFromReference> createDictionaryEntities(Map<String, Long> words, SuggestWordsPayLoad payload) {
        if (words == null) {
            return null;
        }

        List<WordFromReference> result = new ArrayList<>();
        for (String s : words.keySet()) {
            if (words.get(s) != null) {
                result.add(createWordFromReference(s, words.get(s), payload));
            }
        }
        return result;
    }

    public Map<String, Long> countWordsInString(String string) throws SplitStringInTokenException {
        Map<String, Long> result = TokenController.mapStringInTokens(string, indexName, analyzerName);
        result = excludedWordsService.excludeWords(result);
        return result;
    }

    private String[] splitString(String string) throws SplitStringInTokenException {
        if (string == null) {
            return new String[0];
        }
        Map<String, Long> wordMap = countWordsInString(string);
        if (wordMap == null || wordMap.size() == 0) {
            return new String[0];
        }
        wordMap.put(string, 1L);
        List<String> wordList = new ArrayList<>();
        for (String s : wordMap.keySet()) {
            if (s != null && wordMap.get(s) != null && wordMap.get(s) > 0L) {
                wordList.add(s);
            }
        }
        String[] result = new String[wordList.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = wordList.get(i);
        }
        return result;
    }

    private WordFromReference createWordFromReference(String s, Long count, SuggestWordsPayLoad payloads) {
        if (s == null) {
            return null;
        }
        WordFromReference result = new WordFromReference();
        result.setName(s);
        String[] input;
        try {
            input = splitString(s);
        } catch (SplitStringInTokenException ex) {
            //Logger.getLogger(ReferenceTokenizer.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            input = new String[1];
            input[0] = s;
        }
        //String[] input = new String[1];
        //input[0] = s;
        Completion completion = new Completion(input);
        completion.setOutput(s);
        if (count == null || count < MINIMAL_WEIGHT) {
            completion.setWeight(MINIMAL_WEIGHT);
        } else {
            completion.setWeight(count.intValue());
        }
        completion.setPayload(payloads);
        result.setSuggest(completion);
        return result;
    }

    public ExcludedWordsService getExcludedWordsService() {
        return excludedWordsService;
    }

}
