/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.services.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class IndexAnalyzer {

    private String type;
    private List<String> filter;
    private List<String> char_filter;
    private String tokenizer;

    public IndexAnalyzer() {
    }

    public IndexAnalyzer(String type, String tokenizer) {
        this.type = type;
        this.tokenizer = tokenizer;
    }

    public void addFilter(String filterName) {
        if (filter == null) {
            filter = new ArrayList<>();
        }
        filter.add(filterName);
    }

    public void addChar_Filter(String filterName) {
        if (char_filter == null) {
            char_filter = new ArrayList<>();
        }
        char_filter.add(filterName);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getFilter() {
        return filter;
    }

    public void setFilter(List<String> filter) {
        this.filter = filter;
    }

    public String getTokenizer() {
        return tokenizer;
    }

    public void setTokenizer(String tokenizer) {
        this.tokenizer = tokenizer;
    }

    public List<String> getChar_filter() {
        return char_filter;
    }

    public void setChar_filter(List<String> char_filter) {
        this.char_filter = char_filter;
    }

}
