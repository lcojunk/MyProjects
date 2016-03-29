/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.repositories;

import de.adesso.referencer.search.elastic.dictionary.entities.WordFromReference;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author odzhara-ongom
 */
public interface DictionaryRepository extends ElasticsearchRepository<WordFromReference, String> {

    public WordFromReference findByName(String name);
}
