/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.dictionary.services;

import de.adesso.referencer.search.config.ElasticSearchConfig;
import de.adesso.referencer.search.elastic.dictionary.entities.WordFromReference;
import de.adesso.referencer.search.elastic.entities.Reference;
import de.adesso.referencer.search.elastic.repositories.DictionaryRepository;
import de.adesso.referencer.search.elastic.services.dto.IndexCustomSettingsBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.elasticsearch.action.suggest.SuggestResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionFuzzyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class DictionaryService {

    @Autowired
    private DictionaryRepository repository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public WordFromReference addEntity(WordFromReference entity) {
        return repository.save(entity);
    }

    public long count() {
        return repository.count();
    }

    public WordFromReference putEntity(WordFromReference entity) {
        if (entity == null) {
            return null;
        }
        WordFromReference existingEntity = null, savedEntity = null;
        if (entity.getName() != null) {
            existingEntity = searchByName(entity.getName());
        }
        if (existingEntity == null) {
            savedEntity = repository.save(entity);
            return savedEntity;
        }
        WordFromReference mergedEntity = mergeWords(existingEntity, entity);
        savedEntity = repository.save(mergedEntity);
        return savedEntity;
    }

    private boolean hasNameAndWeight(WordFromReference word) {
        if (word != null && word.getName() != null && word.getSuggest() != null && word.getSuggest().getWeight() != null) {
            return true;
        }
        return false;
    }

    public List<WordFromReference> mergeEntities(List<WordFromReference> entities) {
        if (entities == null) {
            return null;
        }
        List<WordFromReference> result = new ArrayList<>();
        if (entities.size() == 0) {
            return result;
        }
        WordFromReference foundedWord = null;
        int newWeight;
        for (WordFromReference word : entities) {
            if (hasNameAndWeight(word)) {
                foundedWord = searchByName(word.getName());
                if (hasNameAndWeight(foundedWord)) {
                    word.setId(foundedWord.getId());
                    newWeight = word.getSuggest().getWeight().intValue() + foundedWord.getSuggest().getWeight().intValue();
                    word.getSuggest().setWeight(newWeight);

                }
            }
            result.add(word);
        }
        saveList(result);
        return result;
    }

    public WordFromReference getEntity(String id) {
        return repository.findOne(id);
    }

    public WordFromReference findByName(String name) {
        return repository.findByName(name);
    }

    public WordFromReference searchByName(String name) {
        return searchByName(queryForName(name));
    }

    public List<WordFromReference> searchByName(List<String> names) {
        if (names == null) {
            return null;
        }
        List<WordFromReference> result = new ArrayList<>();
        WordFromReference foundedWord = null;
        for (String name : names) {
            if (name != null) {
                foundedWord = searchByName(name);
                if (foundedWord != null) {
                    result.add(foundedWord);
                }
            }
        }
        return result;
    }

    public void saveList(List<WordFromReference> words) {
        repository.save(words);
    }

    private SearchQuery queryForName(String name) {
        if (name == null) {
            return null;
        }
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", name)))
                .withPageable(new PageRequest(0, Integer.MAX_VALUE))
                .build();
        return query;
    }

    private WordFromReference searchByName(SearchQuery searchQuery) {
        if (searchQuery == null) {
            return null;
        }
        List<WordFromReference> words = elasticsearchTemplate.queryForList(searchQuery, WordFromReference.class);
        if (words == null || words.size() <= 0) {
            return null;
        }
        return words.get(0);
    }

    public List<WordFromReference> getAll() {
        Iterable<WordFromReference> itr = repository.findAll();
        List<WordFromReference> entityList = new ArrayList<>();
        WordFromReference entity = null;
        for (Iterator<WordFromReference> it = itr.iterator(); it.hasNext();) {
            entity = it.next();
            if (entity != null) {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    public boolean deleteIndex() {
        return elasticsearchTemplate.deleteIndex(ElasticSearchConfig.DICTIONARY_INDEX_NAME);
    }

    private WordFromReference mergeWords(WordFromReference mainWord, WordFromReference newWord) {
        if (mainWord == null && newWord == null) {
            return null;
        }
        if (mainWord == null && newWord != null) {
            return newWord;
        }
        if (mainWord != null && newWord == null) {
            return mainWord;
        }
        WordFromReference result = mainWord;
        Completion completion = mainWord.getSuggest();
        if (newWord.getSuggest() != null && newWord.getSuggest().getWeight() != null) {
            if (completion != null && completion.getWeight() != null) {
                completion.setWeight(completion.getWeight() + newWord.getSuggest().getWeight());
            } else {
                completion = newWord.getSuggest();
            }
            mainWord.setSuggest(completion);
        }
        return result;
    }

    public boolean createIndex() {
        //return createIndex(DEFAULT_SETTINGS_STRING);
        return createIndex(IndexCustomSettingsBuilder.getSuggestWordsIndexDefaultSettingsBuilder().build());
    }

    public boolean createIndex(String settings) {
        return elasticsearchTemplate.createIndex(ElasticSearchConfig.DICTIONARY_INDEX_NAME, settings);
    }
    public static final String INDEX_SETTINGS_STRING = "{\n"
            + "         \"index\": {\n"
            + "            \"creation_date\": \"1452689481365\",\n"
            + "            \"analysis\": {\n"
            + "               \"analyzer\": {\n"
            + "                  \"suggest_words\": {\n"
            + "                     \"type\": \"custom\",\n"
            + "                     \"tokenizer\": \"lowercase\"\n"
            + "                  }\n"
            + "               }\n"
            + "            },\n"
            + "            \"number_of_shards\": \"5\",\n"
            + "            \"number_of_replicas\": \"1\",\n"
            + "            \"version\": {\n"
            + "               \"created\": \"1050299\"\n"
            + "            },\n"
            + "            \"uuid\": \"HHlV7_OsQAmdICryq0nfLA\"\n"
            + "         }\n"
            + "      }";

    public boolean putMapping() {
        if (!elasticsearchTemplate.putMapping(WordFromReference.class)) {
            return false;
        }
        addEntity(WordFromReference
                .createSimpleWord(
                        Reference.releaseStatusToSynonym(Reference.ReleaseStatus.NOT_RELEASED),
                        100));
        addEntity(WordFromReference
                .createSimpleWord(
                        Reference.releaseStatusToSynonym(Reference.ReleaseStatus.ANONYMOUSLY_RELEASED),
                        100));
        addEntity(WordFromReference
                .createSimpleWord(
                        Reference.releaseStatusToSynonym(Reference.ReleaseStatus.FULLY_RELEASED),
                        100));
        addEntity(WordFromReference
                .createSimpleWord(
                        Reference.releaseStatusToSynonym(Reference.ReleaseStatus.INDIVIDUALLY_RELEASED),
                        100));
        return true;
    }

    public boolean indexExists() {
        return elasticsearchTemplate.indexExists(ElasticSearchConfig.DICTIONARY_INDEX_NAME);
    }

    public SuggestResponse suggest(CompletionSuggestionFuzzyBuilder builder) {
        return elasticsearchTemplate.suggest(builder, WordFromReference.class);
    }

}
