/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.dictionary.controllers.dto;

import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class SuggestWordsResponse {

    private List<WordSuggestion> suggestions;

    public List<WordSuggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<WordSuggestion> suggestions) {
        this.suggestions = suggestions;
    }

}
