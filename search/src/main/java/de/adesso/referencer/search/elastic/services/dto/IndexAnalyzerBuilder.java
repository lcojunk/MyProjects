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
public class IndexAnalyzerBuilder {

    private static final String CUSTOM_TYPE = "custom";
    private static final String DEFAULT_TOKENIZER = "standard";

    private IndexAnalyzer indexAnalyzer;
    private String name;

    public IndexAnalyzerBuilder(IndexAnalyzer indexAnalyzer, String name) {
        this.indexAnalyzer = indexAnalyzer;
        this.name = name;
    }

    private static IndexAnalyzer createDefaultAnalyzer() {
        return new IndexAnalyzer();
    }

    private static IndexAnalyzer createDefaultCustomAnalyzer() {
        return new IndexAnalyzer(CUSTOM_TYPE, DEFAULT_TOKENIZER);
    }

    public static IndexAnalyzerBuilder customAnalyzer(String name) {
        return new IndexAnalyzerBuilder(createDefaultCustomAnalyzer(), name);
    }

    public IndexAnalyzerBuilder addFilter(String filtername) {
        if (indexAnalyzer == null) {
            indexAnalyzer = createDefaultAnalyzer();
        }
        indexAnalyzer.addFilter(filtername);
        return this;
    }

    public IndexAnalyzerBuilder tokenizer(String name) {
        if (indexAnalyzer == null) {
            indexAnalyzer = createDefaultAnalyzer();
        }
        indexAnalyzer.setTokenizer(name);
        return this;
    }

    public IndexAnalyzerBuilder addCharFilter(String filtername) {
        if (indexAnalyzer == null) {
            indexAnalyzer = createDefaultAnalyzer();
        }
        indexAnalyzer.addChar_Filter(filtername);
        return this;
    }

    public IndexAnalyzer getIndexAnalyzer() {
        return indexAnalyzer;
    }

    public void setIndexAnalyzer(IndexAnalyzer indexAnalyzer) {
        this.indexAnalyzer = indexAnalyzer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
