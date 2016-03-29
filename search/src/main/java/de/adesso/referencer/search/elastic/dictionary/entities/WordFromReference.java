/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.dictionary.entities;

import de.adesso.referencer.search.config.ElasticSearchConfig;
import de.adesso.referencer.search.elastic.dictionary.controllers.dto.SuggestWordsPayLoad;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.completion.Completion;

/**
 *
 * @author odzhara-ongom
 */
@Document(indexName = ElasticSearchConfig.DICTIONARY_INDEX_NAME, type = ElasticSearchConfig.DICTIONARY_TYPE_NAME)
public class WordFromReference {

    @Id
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String id;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String name;

    @CompletionField(payloads = true, indexAnalyzer = ElasticSearchConfig.SUGGESTWORD_INDEX_ANALYZER_NAME, searchAnalyzer = ElasticSearchConfig.SUGGESTWORD_SEARCH_ANALYZER_NAME)
    private Completion suggest;

    public WordFromReference() {
    }

    public static WordFromReference createSimpleWord(String word, Integer weight) {
        WordFromReference result = new WordFromReference();
        result.setName(word);
        String[] input = new String[1];
        input[0] = word;
        Completion completion = new Completion(input);
        completion.setOutput(word);
        completion.setWeight(weight);
        SuggestWordsPayLoad payload = new SuggestWordsPayLoad().addRef("");
        completion.setPayload(payload);
        result.setSuggest(completion);
        return result;
    }

    public WordFromReference(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Completion getSuggest() {
        return suggest;
    }

    public void setSuggest(Completion suggest) {
        this.suggest = suggest;
    }

}
