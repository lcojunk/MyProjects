/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.services.dto;

/**
 *
 * @author odzhara-ongom
 */
public class IndexGramFilterBuilder implements IndexFilterBuilder {

    private static final int DEFAULT_EDGE_NGRAM_MIN_GRAM = 1;
    private static final int DEFAULT_EDGE_NGRAM_MAX_GRAM = 20;
    private static final String EDGE_NGRAM_TYPE = "edge_ngram";

    private static final int DEFAULT_NGRAM_MIN_GRAM = 2;
    private static final int DEFAULT_NGRAM_MAX_GRAM = 50;
    private static final String NGRAM_TYPE = "nGram";

    private static final String ICU_COLLATION_TYPE = "icu_collation";
    private IndexGramFilter filter;
    private String name;

    public IndexGramFilterBuilder(IndexGramFilter filter, String name) {
        this.filter = filter;
        this.name = name;
    }

    private static IndexGramFilter createDefaultFilter() {
        return new IndexGramFilter();
    }

    private static IndexGramFilter createDefaultNgramFilter() {
        IndexGramFilter filter = new IndexGramFilter(NGRAM_TYPE);
        filter.setMin_gram(DEFAULT_NGRAM_MIN_GRAM);
        filter.setMax_gram(DEFAULT_NGRAM_MAX_GRAM);
        return filter;
    }

    private static IndexGramFilter createDefaultEdgeNgramFilter() {
        IndexGramFilter filter = new IndexGramFilter(EDGE_NGRAM_TYPE);
        filter.setMin_gram(DEFAULT_EDGE_NGRAM_MIN_GRAM);
        filter.setMax_gram(DEFAULT_EDGE_NGRAM_MAX_GRAM);
        return filter;
    }

    public static IndexGramFilterBuilder ngram(String name) {
        return new IndexGramFilterBuilder(createDefaultNgramFilter(), name);
    }

    public static IndexGramFilterBuilder edgeNgram(String name) {
        return new IndexGramFilterBuilder(createDefaultEdgeNgramFilter(), name);
    }

    public IndexGramFilterBuilder minGram(int value) {
        if (filter == null) {
            filter = createDefaultFilter();
        }
        filter.setMin_gram(value);
        return this;
    }

    public IndexGramFilterBuilder maxGram(int value) {
        if (filter == null) {
            filter = createDefaultFilter();
        }
        filter.setMax_gram(value);
        return this;
    }

    @Override
    public IndexFilter getFilter() {
        return filter;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getType() {
        if (filter != null) {
            return filter.getType();
        }
        return null;
    }

}
