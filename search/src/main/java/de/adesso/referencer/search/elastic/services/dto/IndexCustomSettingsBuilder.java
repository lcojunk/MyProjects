/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.services.dto;

import de.adesso.referencer.search.config.ElasticSearchConfig;
import de.adesso.referencer.search.helper.MyHelpMethods;
import java.util.HashMap;

/**
 *
 * @author odzhara-ongom
 */
public class IndexCustomSettingsBuilder {

    private static final String DEFAULT_NUMBER_OF_REPLICAS = "1";
    private static final String DEFAULT_NUMBER_OF_SHARDS = "5";
    private static final String REFERENZER_NGRAM_FILTER = "mynGram";
    private static final String REFERENZER_AUTOCOMPLETE_FILTER = "autocomplete_filter";
    private static final String REFERENZER_BREAK_AT_POINT_FILTER = "breakAtPoint";
    private static final String REFERENZER_BREAK_AT_POINT_MAPPING = ".=>. . ";
    private static final String REFERENZER_EMAIL_ANALYZER = ElasticSearchConfig.EMAIL_ANALYZER_NAME;
    private static final String REFERENZER_INDEX_ANALYZER = ElasticSearchConfig.REFERENCE_INDEX_ANALYZER_NAME;
    private static final String REFERENZER_SEARCH_ANALYZER = ElasticSearchConfig.ELASTIC_SEARCH_ANALYZER_NAME;

    private static final String SUGGEST_WORDS_ANALYZER = ElasticSearchConfig.SUGGESTWORD_ANALYZER_NAME;
    private static final String SUGGEST_WORDS_TOKENIZER = ElasticSearchConfig.ELASTIC_LOWERCASE_TOKENIZER;

    private static final String ELASTIC_LOWERCASE_FILTER = ElasticSearchConfig.ELASTIC_LOWERCASE_FILTER_NAME;
    private static final String ELASTIC_STANDARD_FILTER = ElasticSearchConfig.ELASTIC_STANDARD_FILTER_NAME;

    // Filters and analysers for sorting text fields
    private static final String GERMANY_LANGUAGE_FILTER = ElasticSearchConfig.GERMANY_LANGUAGE_FILTER;
    private static final String GERMANY_PHONEBOOK_FILTER = ElasticSearchConfig.GERMANY_PHONEBOOK_FILTER;
    private static final String GERMANY_LANGUAGE_ANALYZER = ElasticSearchConfig.GERMANY_LANGUAGE_ANALYZER;
    private static final String GERMANY_PHONEBOOK_ANALYZER = ElasticSearchConfig.GERMANY_PHONEBOOK_ANALYZER;

    private IndexCustomSettings indexCustomSettings;

    public IndexCustomSettingsBuilder() {
    }

    public IndexCustomSettingsBuilder setIndexSettingsParameters(String numberOfReplicas, String numberOfShards) {
        if (indexCustomSettings == null) {
            indexCustomSettings = new IndexCustomSettings();
        }
        if (indexCustomSettings.getIndex() == null) {
            indexCustomSettings.setIndex(new IndexSettingsParameters());
        }
        indexCustomSettings.getIndex().setNumber_of_replicas(numberOfReplicas);
        indexCustomSettings.getIndex().setNumber_of_shards(numberOfShards);
        return this;
    }

    public IndexCustomSettingsBuilder setDefaultIndexSettingsParameters() {
        return setIndexSettingsParameters(DEFAULT_NUMBER_OF_REPLICAS, DEFAULT_NUMBER_OF_SHARDS);
    }

    public IndexCustomSettingsBuilder setIndexAnalysis(IndexAnalysis indexAnalysis) {
        if (indexAnalysis == null) {
            return this;
        }
        if (indexCustomSettings == null) {
            indexCustomSettings = new IndexCustomSettings();
        }
        if (indexCustomSettings.getIndex() == null) {
            indexCustomSettings.setIndex(new IndexSettingsParameters());
        }
        indexCustomSettings.getIndex().setAnalysis(indexAnalysis);
        return this;
    }

    private static IndexAnalysis createRefAnalysis() {
        HashMap<String, IndexFilter> filter = new HashMap<>();
        HashMap<String, IndexCharFilter> char_filter = new HashMap<>();
        HashMap<String, IndexAnalyzer> analyzer = new HashMap<>();
        String mynGram = "mynGram";
        String autocomplete_filter = "autocomplete_filter";
        String breakAtPoint = "breakAtPoint";
        filter.put(mynGram, new IndexGramFilter("nGram", 2, 50));
        filter.put(autocomplete_filter, new IndexGramFilter("edge_ngram", 1, 20));
        char_filter.put(breakAtPoint, new IndexCharFilter("mapping", ".=>. . "));

        IndexAnalysis result = new IndexAnalysis();
        result.setFilter(filter);
        result.setChar_filter(char_filter);

        IndexAnalyzer emailAnalyzer = new IndexAnalyzer("custom", "standard");
        emailAnalyzer.addFilter("lowercase");
        emailAnalyzer.addChar_Filter(breakAtPoint);
        analyzer.put("emailAnalyzer", emailAnalyzer);

        IndexAnalyzer my_index_analyzer = new IndexAnalyzer("custom", "standard");
        my_index_analyzer.addFilter("lowercase");
        my_index_analyzer.addFilter(mynGram);
        analyzer.put("my_index_analyzer", my_index_analyzer);

        IndexAnalyzer my_search_analyzer = new IndexAnalyzer("custom", "standard");
        my_search_analyzer.addFilter("standard");
        my_search_analyzer.addFilter("lowercase");
        my_search_analyzer.addFilter(mynGram);
        analyzer.put("my_search_analyzer", my_search_analyzer);

        result.setAnalyzer(analyzer);
        return result;
    }

    private static IndexAnalysis createRefenzerAnalysis() {
//        IndexAnalysisBuilder builder = IndexAnalysisBuilder
        return IndexAnalysisBuilder
                .getIndexAnalysisBuilder()
                .addFilter(IndexGramFilterBuilder
                        .ngram(REFERENZER_NGRAM_FILTER)
                        .minGram(2).maxGram(50))
                .addFilter(IndexGramFilterBuilder
                        .edgeNgram(REFERENZER_AUTOCOMPLETE_FILTER)
                        .minGram(1).maxGram(20))
                .addFilter(IndexIcuCollationFilterBuilder
                        .icuFilter(GERMANY_LANGUAGE_FILTER)
                        .language("de").country("DE"))
                .addFilter(IndexIcuCollationFilterBuilder
                        .icuFilter(GERMANY_PHONEBOOK_FILTER)
                        .language("de").country("DE").variant("@collation=phonebook"))
                .addCharFilter(IndexCharFilterBuilder
                        .mappingFilter(REFERENZER_BREAK_AT_POINT_FILTER)
                        .addMapping(REFERENZER_BREAK_AT_POINT_MAPPING))
                .addAnalyzer(IndexAnalyzerBuilder
                        .customAnalyzer(REFERENZER_EMAIL_ANALYZER)
                        .addFilter(ELASTIC_LOWERCASE_FILTER)
                        .addCharFilter(REFERENZER_BREAK_AT_POINT_FILTER))
                .addAnalyzer(IndexAnalyzerBuilder
                        .customAnalyzer(REFERENZER_INDEX_ANALYZER)
                        .addFilter(ELASTIC_LOWERCASE_FILTER)
                        .addFilter(REFERENZER_NGRAM_FILTER))
                .addAnalyzer(IndexAnalyzerBuilder
                        .customAnalyzer(REFERENZER_SEARCH_ANALYZER)
                        .addFilter(ELASTIC_STANDARD_FILTER)
                        .addFilter(ELASTIC_LOWERCASE_FILTER)
                        .addFilter(REFERENZER_NGRAM_FILTER))
                .addAnalyzer(IndexAnalyzerBuilder
                        .customAnalyzer(GERMANY_LANGUAGE_ANALYZER)
                        .addFilter(GERMANY_LANGUAGE_FILTER)
                        .tokenizer("keyword"))
                .addAnalyzer(IndexAnalyzerBuilder
                        .customAnalyzer(GERMANY_PHONEBOOK_ANALYZER)
                        .addFilter(GERMANY_PHONEBOOK_FILTER)
                        .tokenizer("keyword"))
                .getIndexAnalysis();
        // return builder.getIndexAnalysis();
    }

    private static IndexAnalysis createSuggestWordsAnalysis() {
//        IndexAnalysisBuilder builder = IndexAnalysisBuilder
        return IndexAnalysisBuilder
                .getIndexAnalysisBuilder()
                .addAnalyzer(IndexAnalyzerBuilder
                        .customAnalyzer(SUGGEST_WORDS_ANALYZER)
                        .tokenizer(SUGGEST_WORDS_TOKENIZER)
                )
                .getIndexAnalysis();
        // return builder.getIndexAnalysis();
    }

    public static IndexCustomSettingsBuilder getIndexDefaultSettingsBuilder() {
        return (new IndexCustomSettingsBuilder())
                .setDefaultIndexSettingsParameters()
                .setIndexAnalysis(createRefenzerAnalysis());
    }

    public static IndexCustomSettingsBuilder getSuggestWordsIndexDefaultSettingsBuilder() {
        return (new IndexCustomSettingsBuilder())
                .setDefaultIndexSettingsParameters()
                .setIndexAnalysis(createSuggestWordsAnalysis());
    }

    public String build() {
        return MyHelpMethods.object2GsonString(indexCustomSettings);
    }

    public IndexCustomSettings getIndexCustomSettings() {
        return indexCustomSettings;
    }

}
