/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.services.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class IndexAnalysisBuilder {

    private String[] defaultFilters = {"standard", "lowercase"};
    private List<String> filterNames = new ArrayList<>();
    private String[] defaultCharFilters = {"standard", "lowercase"};
    private List<String> charFilterNames = new ArrayList<>();

    private HashMap<String, IndexFilter> filter = new HashMap<>();
    private HashMap<String, IndexCharFilter> char_filter = new HashMap<>();
    private HashMap<String, IndexAnalyzer> analyzer = new HashMap<>();

    public IndexAnalysisBuilder() {
        for (int i = 0; i < defaultFilters.length; i++) {
            filterNames.add(defaultFilters[i]);
        }
        for (int i = 0; i < defaultCharFilters.length; i++) {
            charFilterNames.add(defaultCharFilters[i]);
        }
    }

    public static IndexAnalysisBuilder getIndexAnalysisBuilder() {
        return new IndexAnalysisBuilder();
    }
//
//    public IndexFilterBuilder addNgramFilter(String name, Integer from, Integer to) {
//        IndexFilterBuilder result = null;
//        if (name == null) {
//            return null;
//        }
//        result = IndexFilterBuilder.ngram(name).minGram(from).maxGram(to);
//        filter.put(result.getName(), result.getFilter());
//        return result;
//    }
//
//    public IndexFilterBuilder addEdgeNgramFilter(String name, Integer from, Integer to) {
//        IndexFilterBuilder result = null;
//        if (name == null) {
//            return null;
//        }
//        result = IndexFilterBuilder.edgeNgram(name).minGram(from).maxGram(to);
//        filter.put(result.getName(), result.getFilter());
//        return result;
//    }

    public IndexAnalysisBuilder addFilter(IndexFilterBuilder filterBuilder) {
        if (filterBuilder != null
                && filterBuilder.getName() != null
                && filterBuilder.getFilter() != null) {
            if (this.filter == null) {
                this.filter = new HashMap<>();
            }
            filter.put(filterBuilder.getName(), filterBuilder.getFilter());
        }
        return this;
    }

    public IndexAnalysisBuilder addCharFilter(IndexCharFilterBuilder filterBuilder) {
        if (filterBuilder != null
                && filterBuilder.getName() != null
                && filterBuilder.getFilter() != null) {
            if (this.char_filter == null) {
                this.char_filter = new HashMap<>();
            }
            this.char_filter.put(filterBuilder.getName(), filterBuilder.getFilter());
        }
        return this;
    }

    public IndexAnalysisBuilder addAnalyzer(IndexAnalyzerBuilder analyzerBuilder) {
        if (analyzerBuilder != null
                && analyzerBuilder.getName() != null
                && analyzerBuilder.getIndexAnalyzer() != null) {
            if (this.analyzer == null) {
                this.analyzer = new HashMap<>();
            }
            this.analyzer.put(analyzerBuilder.getName(), analyzerBuilder.getIndexAnalyzer());
        }
        return this;
    }

    public IndexAnalysis getIndexAnalysis() {
        IndexAnalysis result = new IndexAnalysis();
        result.setFilter(filter);
        result.setChar_filter(char_filter);
        result.setAnalyzer(analyzer);
        return result;
    }
}
