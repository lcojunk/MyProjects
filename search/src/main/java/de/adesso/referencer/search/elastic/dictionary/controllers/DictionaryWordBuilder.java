/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.dictionary.controllers;

import de.adesso.referencer.search.elastic.dictionary.entities.WordFromReference;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

/**
 *
 * @author odzhara-ongom
 */
public class DictionaryWordBuilder {

    private WordFromReference result;

    public DictionaryWordBuilder(String id) {
        result = new WordFromReference(id);
    }

    public DictionaryWordBuilder name(String name) {
        result.setName(name);
        return this;
    }

    public DictionaryWordBuilder suggest(String[] input) {
        return suggest(input, null, null, null);
    }

    public DictionaryWordBuilder suggest(String[] input, String output) {
        return suggest(input, output, null, null);
    }

    public DictionaryWordBuilder suggest(String[] input, String output, Object payload) {
        return suggest(input, output, payload, null);
    }

    public DictionaryWordBuilder suggest(String[] input, String output, Object payload, Integer weight) {
        Completion suggest = new Completion(input);
        suggest.setOutput(output);
        suggest.setPayload(payload);
        suggest.setWeight(weight);
        result.setSuggest(suggest);
        return this;
    }

    public WordFromReference build() {
        return result;
    }

    public IndexQuery buildIndex() {
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setId(result.getId());
        indexQuery.setObject(result);
        return indexQuery;
    }
}
