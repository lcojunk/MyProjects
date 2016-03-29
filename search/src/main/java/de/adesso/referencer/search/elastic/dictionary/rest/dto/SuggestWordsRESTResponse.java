/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.dictionary.rest.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class SuggestWordsRESTResponse {

    private SuggestWordsRESTRequest request;
    private List<RestWordSuggestion> suggestions;

    private Date suggestWordControllerStart;
    private Date suggestWordServiceStart;
    private Date created = new Date();

    public Date getSuggestWordControllerStart() {
        return suggestWordControllerStart;
    }

    public void setSuggestWordControllerStart(Date suggestWordControllerStart) {
        this.suggestWordControllerStart = suggestWordControllerStart;
    }

    public Date getSuggestWordServiceStart() {
        return suggestWordServiceStart;
    }

    public void setSuggestWordServiceStart(Date suggestWordServiceStart) {
        this.suggestWordServiceStart = suggestWordServiceStart;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public SuggestWordsRESTRequest getRequest() {
        return request;
    }

    public void setRequest(SuggestWordsRESTRequest request) {
        this.request = request;
    }

    public List<RestWordSuggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<RestWordSuggestion> suggestions) {
        this.suggestions = suggestions;
    }

}
