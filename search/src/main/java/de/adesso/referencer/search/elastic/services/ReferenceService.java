/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.services;

import de.adesso.referencer.search.config.ElasticSearchConfig;
import de.adesso.referencer.search.elastic.controllers.dto.ReferenceQueryResponse;
import de.adesso.referencer.search.elastic.entities.*;
import de.adesso.referencer.search.elastic.repositories.*;
import de.adesso.referencer.search.elastic.services.dto.IndexCustomSettingsBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class ReferenceService {

    @Autowired
    private ReferenceRepository repository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public List<Reference> getEntityByName(String name) {
        return repository.findByProjectname(name);
    }

    public Reference addEntity(Reference entity) {
        if (entity == null) {
            return null;
        }
        entity.setDocumentCreationTime(new Date());
        return repository.save(entity);
    }

    public Reference getEntity(String id) {
        if (id == null) {
            return null;
        }
        return repository.findOne(id);
    }

    public List<Reference> getAll() {
        Iterable<Reference> itr = repository.findAll();
        List<Reference> entityList = new ArrayList<>();
        Reference entity = null;
        for (Iterator<Reference> it = itr.iterator(); it.hasNext();) {
            entity = it.next();
            if (entity != null) {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    public List<Reference> searchForReference(SearchQuery searchQuery) {
        if (searchQuery == null) {
            return null;
        }
        return elasticsearchTemplate.queryForList(searchQuery, Reference.class);
    }

    public Page<Reference> searchForPageOfReference(SearchQuery searchQuery) {
        if (searchQuery == null) {
            return null;
        }
        return elasticsearchTemplate.queryForPage(searchQuery, Reference.class);

    }

    public boolean deleteIndex() {
        return elasticsearchTemplate.deleteIndex(ElasticSearchConfig.INDEX_NAME);
    }

    public boolean createIndex(String settings) {
        return elasticsearchTemplate.createIndex(ElasticSearchConfig.INDEX_NAME, settings);
    }

    public boolean createIndex() {
        //return createIndex(DEFAULT_SETTINGS_STRING);
        return createIndex(IndexCustomSettingsBuilder.getIndexDefaultSettingsBuilder().build());
    }

    public boolean putMapping() {
        if (!elasticsearchTemplate.putMapping(Reference.class)) {
            return false;
        }
        if (!elasticsearchTemplate.putMapping(AdessoRole.class)) {
            return false;
        }
        if (!elasticsearchTemplate.putMapping(Branch.class)) {
            return false;
        }
        if (!elasticsearchTemplate.putMapping(Characteristic.class)) {
            return false;
        }
        if (!elasticsearchTemplate.putMapping(Focus.class)) {
            return false;
        }
        if (!elasticsearchTemplate.putMapping(InvolvedRole.class)) {
            return false;
        }
        if (!elasticsearchTemplate.putMapping(Lob.class)) {
            return false;
        }
        if (!elasticsearchTemplate.putMapping(ProjectRole.class)) {
            return false;
        }
        if (!elasticsearchTemplate.putMapping(Technology.class)) {
            return false;
        }
        if (!elasticsearchTemplate.putMapping(User.class)) {
            return false;
        }
        if (!elasticsearchTemplate.putMapping(BigDocument.class)) {
            return false;
        }
        return elasticsearchTemplate.putMapping(Reference.class);
    }

    public boolean indexExists() {
        return elasticsearchTemplate.indexExists(ElasticSearchConfig.INDEX_NAME);
    }

    private ReferenceQueryResponse queryForReferences(SearchQuery searchQuery) {
        ReferenceQueryResponse result = new ReferenceQueryResponse();
        Page<Reference> page = searchForPageOfReference(searchQuery);
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });
        result.setPageOfReferences(page);
        result.setAggregations(aggregations);
        return result;
    }

    public Aggregations queryForAggregations(SearchQuery searchQuery) {
        return queryForReferences(searchQuery).getAggregations();
    }

    public boolean createIndex_old() {
        String settings = "{\n"
                + "    \"index\" : {\n"
                + "      \"analysis\" : {\n"
                + "        \"analyzer\" : {\n"
                + "          \"emailAnalyzer\": {\n"
                + "             \"filter\": [\n"
                + "                \"lowercase\"\n"
                + "             ],\n"
                + "             \"char_filter\": [\n"
                + "                \"breakAtPoint\"\n"
                + "             ],\n"
                + "             \"type\": \"custom\",\n"
                + "             \"tokenizer\": \"standard\"\n"
                + "          },          \n"
                + "          \"my_index_analyzer\" : {\n"
                + "            \"type\" : \"custom\",\n"
                + "            \"tokenizer\" : \"standard\",\n"
                + "            \"filter\" : [\"lowercase\", \"mynGram\"]\n"
                + "          },\n"
                + "          \"my_search_analyzer\" : {\n"
                + "            \"type\" : \"custom\",\n"
                + "            \"tokenizer\" : \"standard\",\n"
                + "            \"filter\" : [\"standard\", \n"
                + "                        \"lowercase\", \n"
                + "                        \"mynGram\"]\n"
                + "          }\n"
                + "          \n"
                + "        },\n"
                + "        \"char_filter\": {\n"
                + "            \"breakAtPoint\": {\n"
                + "               \"type\": \"mapping\",\n"
                + "               \"mappings\": [\n"
                + "                  \".=>. . \"\n"
                + "               ]\n"
                + "            }\n"
                + "         },        \n"
                + "        \"filter\" : {\n"
                + "          \"mynGram\" : {\n"
                + "            \"type\" : \"nGram\",\n"
                + "            \"min_gram\" : 2,\n"
                + "            \"max_gram\" : 50\n"
                + "          },\n"
                + "          \"autocomplete_filter\": { \n"
                + "            \"type\":     \"edge_ngram\",\n"
                + "            \"min_gram\": 1,\n"
                + "            \"max_gram\": 20\n"
                + "          }\n"
                + "        }\n"
                + "      },\n"
                + "      \"number_of_shards\": \"5\",\n"
                + "      \"number_of_replicas\": \"1\"\n"
                + "    }\n"
                + "  }";
        //System.out.println(settings);
        return elasticsearchTemplate.createIndex(ElasticSearchConfig.INDEX_NAME, settings);
    }

    public static final String DEFAULT_SETTINGS_STRING = "{\n"
            + "    \"index\" : {\n"
            + "      \"analysis\" : {\n"
            + "        \"analyzer\" : {\n"
            + "          \"emailAnalyzer\": {\n"
            + "             \"filter\": [\n"
            + "                \"lowercase\"\n"
            + "             ],\n"
            + "             \"char_filter\": [\n"
            + "                \"breakAtPoint\"\n"
            + "             ],\n"
            + "             \"type\": \"custom\",\n"
            + "             \"tokenizer\": \"standard\"\n"
            + "          },          \n"
            + "          \"my_index_analyzer\" : {\n"
            + "            \"type\" : \"custom\",\n"
            + "            \"tokenizer\" : \"standard\",\n"
            + "            \"filter\" : [\"lowercase\", \"mynGram\"]\n"
            + "          },\n"
            + "          \"my_search_analyzer\" : {\n"
            + "            \"type\" : \"custom\",\n"
            + "            \"tokenizer\" : \"standard\",\n"
            + "            \"filter\" : [\"standard\", \n"
            + "                        \"lowercase\", \n"
            + "                        \"mynGram\"]\n"
            + "          }\n"
            + "          \n"
            + "        },\n"
            + "        \"char_filter\": {\n"
            + "            \"breakAtPoint\": {\n"
            + "               \"type\": \"mapping\",\n"
            + "               \"mappings\": [\n"
            + "                  \".=>. . \"\n"
            + "               ]\n"
            + "            }\n"
            + "         },        \n"
            + "        \"filter\" : {\n"
            + "          \"mynGram\" : {\n"
            + "            \"type\" : \"nGram\",\n"
            + "            \"min_gram\" : 2,\n"
            + "            \"max_gram\" : 50\n"
            + "          },\n"
            + "          \"autocomplete_filter\": { \n"
            + "            \"type\":     \"edge_ngram\",\n"
            + "            \"min_gram\": 1,\n"
            + "            \"max_gram\": 20\n"
            + "          }\n"
            + "        }\n"
            + "      },\n"
            + "      \"number_of_shards\": \"5\",\n"
            + "      \"number_of_replicas\": \"1\"\n"
            + "    }\n"
            + "  }";

}
