/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.dictionary.controllers.dto;

import de.adesso.referencer.search.elastic.dictionary.rest.dto.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class SuggestWordsFactory {

    public SuggestWordsRequest createSuggestWordsRequest(SuggestWordsRESTRequest userRequest) {
        if (userRequest == null) {
            return null;
        }
        SuggestWordsRequest result = new SuggestWordsRequest();
        result.setSuggestion(userRequest.getSuggestion());
        return result;
    }

    public SuggestWordsRESTResponse createSuggestWordsRESTResponse(SuggestWordsResponse serverResponse) {
        SuggestWordsRESTResponse result = new SuggestWordsRESTResponse();
        if (serverResponse == null) {
            return result;
        }
        result.setSuggestions(createSuggestions(serverResponse.getSuggestions()));
        return result;
    }

    private List<RestWordSuggestion> createSuggestions(List<WordSuggestion> suggestions) {
        if (suggestions == null) {
            return null;
        }
        List<RestWordSuggestion> result = new ArrayList<>();
        for (WordSuggestion word : suggestions) {
            if (word != null) {
                result.add(createSuggestion(word));
            }
        }
        return result;
    }

    private RestWordSuggestion createSuggestion(WordSuggestion word) {
        if (word == null) {
            return null;
        }
        RestWordSuggestion result = new RestWordSuggestion();
        result.setScore(word.getScore());
        result.setText(word.getText());
        result.setPayload(word.getPayload());
        return result;
    }

}
