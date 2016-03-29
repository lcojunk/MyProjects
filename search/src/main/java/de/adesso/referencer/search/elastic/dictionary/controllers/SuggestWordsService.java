/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.dictionary.controllers;

import de.adesso.referencer.search.config.ElasticSearchConfig;
import de.adesso.referencer.search.elastic.dictionary.controllers.dto.SplitStringInTokenException;
import de.adesso.referencer.search.elastic.dictionary.controllers.dto.SuggestWordsPayLoad;
import de.adesso.referencer.search.elastic.dictionary.controllers.dto.SuggestWordsRequest;
import de.adesso.referencer.search.elastic.dictionary.controllers.dto.SuggestWordsResponse;
import de.adesso.referencer.search.elastic.dictionary.controllers.dto.WordSuggestion;
import de.adesso.referencer.search.elastic.dictionary.entities.WordFromReference;
import de.adesso.referencer.search.elastic.dictionary.services.DictionaryService;
import de.adesso.referencer.search.elastic.entities.Reference;
import de.adesso.referencer.search.helper.MyHelpMethods;
import org.elasticsearch.action.suggest.SuggestResponse;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionFuzzyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class SuggestWordsService {

    private static final String COMPLETION_SUGGESTION_NAME = "suggest_a_word";

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private ReferenceTokenizer referenceTokenizer;

    private Date serviceStart = new Date();

    public Date getServiceStart() {
        return serviceStart;
    }

    public void setServiceStart(Date serviceStart) {
        this.serviceStart = serviceStart;
    }

    public CompletionSuggestionFuzzyBuilder buildSuggestion(String suggestion) {
        if (suggestion == null) {
            return null;
        }
        return new CompletionSuggestionFuzzyBuilder(COMPLETION_SUGGESTION_NAME)
                .text(suggestion)
                .field(ElasticSearchConfig.DICTIONARY_SUGGEST_FIELD_NAME);
    }

    private WordSuggestion createWordSuggestion(CompletionSuggestion.Entry.Option option) {
        if (option == null) {
            return null;
        }
        WordSuggestion result = new WordSuggestion();
        result.setText(option.getText().string());
        result.setScore(option.getScore());
        String payLoadString = option.getPayloadAsString();
        SuggestWordsPayLoad payload = MyHelpMethods.getGson().fromJson(payLoadString, SuggestWordsPayLoad.class);
        result.setPayload(payload.getRef_id());
        return result;
    }

    public List<WordSuggestion> getWordSuggestions(SuggestResponse suggestResponse) {
        if (suggestResponse == null) {
            return null;
        }
        CompletionSuggestion completionSuggestion = suggestResponse.getSuggest().getSuggestion(COMPLETION_SUGGESTION_NAME);
        List<CompletionSuggestion.Entry.Option> options = completionSuggestion.getEntries().get(0).getOptions();
        if (options == null) {
            return null;
        }
        List<WordSuggestion> result = new ArrayList<>();
        WordSuggestion wSuggestion;
        for (CompletionSuggestion.Entry.Option option : options) {
            wSuggestion = createWordSuggestion(option);
            if (wSuggestion != null) {
                result.add(wSuggestion);
            }
        }
        return result;
    }

    public SuggestWordsResponse suggestWords(SuggestWordsRequest request) {
        if (request == null || request.getSuggestion() == null) {
            return null;
        }
        SuggestWordsResponse result = new SuggestWordsResponse();
        CompletionSuggestionFuzzyBuilder builder = buildSuggestion(request.getSuggestion());
        SuggestResponse sResponse = dictionaryService.suggest(builder);
        List<WordSuggestion> wordSuggestions = getWordSuggestions(sResponse);
        result.setSuggestions(wordSuggestions);
        return result;
    }

    private Map<String, Long> searchByName(List<String> names) {
        List<WordFromReference> foundedWordsList = dictionaryService.searchByName(names);
        if (foundedWordsList == null) {
            return null;
        }
        Map<String, Long> result = new HashMap<>();
        for (WordFromReference foundedWordEntity : foundedWordsList) {
            if (foundedWordEntity != null
                    && foundedWordEntity.getName() != null
                    && foundedWordEntity.getSuggest() != null
                    && foundedWordEntity.getSuggest().getWeight() != null) {
                result.put(foundedWordEntity.getName(), foundedWordEntity.getSuggest().getWeight().longValue());
            }
        }
        return result;
    }

    private Map<String, Long> searchByName(Set<String> names) {
        List<String> namesList = new ArrayList<String>(names);
        return searchByName(namesList);
    }

    public List<WordFromReference> indexReference(Reference reference) throws SplitStringInTokenException {
        if (reference == null) {
            return null;
        }
        Map<String, Long> wordsMap = referenceTokenizer.getWordsFromReference(reference);
        //Map<String, Long> mixedWordsMap = MyHelpMethods.mergeMapOfWords(wordsMap, searchByName(wordsMap.keySet()));
        SuggestWordsPayLoad payLoad = (new SuggestWordsPayLoad()).addRef(reference.getId());
        List<WordFromReference> dict = referenceTokenizer.createDictionaryEntities(wordsMap, payLoad);
        dictionaryService.mergeEntities(dict);
        return dict;
    }

    public List<WordFromReference> indexReferenceNoBulk(Reference reference) throws SplitStringInTokenException {
        if (reference == null) {
            return null;
        }
        Map<String, Long> wordsMap = referenceTokenizer.getWordsFromReference(reference);
        SuggestWordsPayLoad payLoad = (new SuggestWordsPayLoad()).addRef(reference.getId());
        List<WordFromReference> dict = referenceTokenizer.createDictionaryEntities(wordsMap, payLoad);
        for (WordFromReference word : dict) {
            dictionaryService.putEntity(word);
        }
        return dict;
    }

    public ExcludedWordsService getExcludedWordsService() {
        return referenceTokenizer.getExcludedWordsService();
    }

}
