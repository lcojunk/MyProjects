/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.services.dto;

import java.util.HashMap;

/**
 *
 * @author odzhara-ongom
 */
public class IndexAnalysis {

    private HashMap<String, IndexFilter> filter;
    private HashMap<String, IndexCharFilter> char_filter;
    private HashMap<String, IndexAnalyzer> analyzer;

    public HashMap<String, IndexFilter> getFilter() {
        return filter;
    }

    public HashMap<String, IndexCharFilter> getChar_filter() {
        return char_filter;
    }

    public HashMap<String, IndexAnalyzer> getAnalyzer() {
        return analyzer;
    }

    public void setFilter(HashMap<String, IndexFilter> filter) {
        this.filter = filter;
    }

    public void setChar_filter(HashMap<String, IndexCharFilter> char_filter) {
        this.char_filter = char_filter;
    }

    public void setAnalyzer(HashMap<String, IndexAnalyzer> analyzer) {
        this.analyzer = analyzer;
    }

}
