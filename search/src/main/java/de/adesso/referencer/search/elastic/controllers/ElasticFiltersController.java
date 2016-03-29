/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.controllers;

import de.adesso.referencer.search.config.ElasticSearchConfig;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ExistsFilterBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.MissingFilterBuilder;
import org.elasticsearch.index.query.NestedFilterBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import org.elasticsearch.index.query.QueryFilterBuilder;
import org.elasticsearch.index.query.RangeFilterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class ElasticFiltersController {

    private String idFieldname = ElasticSearchConfig.ID_FIELD_NAME;

    @Autowired
    private QueriesController queriesController;

    public BoolFilterBuilder buildNestedMissingFieldFilter(String path, String idName) {
        if (path == null || idName == null) {
            return null;
        }
        return FilterBuilders
                .boolFilter()
                .mustNot(FilterBuilders
                        .nestedFilter(path, QueryBuilders
                                .filteredQuery(QueryBuilders.matchAllQuery(), FilterBuilders.existsFilter(path + "." + idName))));
    }

    public NestedFilterBuilder buildNestedShouldFilterWithoutNullFields(String path, String idName, List<String> searchstrings) {
        if (path == null || idName == null) {
            return null;
        }
        if (searchstrings == null || searchstrings.size() == 0) {
            return FilterBuilders.nestedFilter(path, QueryBuilders.boolQuery().should(QueryBuilders.matchAllQuery()));
        };
        BoolQueryBuilder idQuery = QueryBuilders.boolQuery().should(QueryBuilders.matchQuery(path + "." + idName, searchstrings.get(0)));
        if (searchstrings.size() == 1) {
            return FilterBuilders.nestedFilter(path, idQuery);
        }
        for (int i = 1; i < searchstrings.size(); i++) {
            idQuery.should(QueryBuilders.matchQuery(path + "." + idName, searchstrings.get(i)));
        }
        return FilterBuilders.nestedFilter(path, idQuery);
    }

    public BoolFilterBuilder buildNestedShouldFilterWithMissingFields(String path, String idName, List<String> userSearchstrings) {
        if (path == null || idName == null) {
            return null;
        }
        List<String> searchstrings = null;
        if (userSearchstrings != null) {
            searchstrings = new ArrayList<>();
            for (String s : userSearchstrings) {
                if (s != null) {
                    searchstrings.add(s);
                }
            }
        }
        NestedFilterBuilder searchFieldQuery = buildNestedShouldFilterWithoutNullFields(path, idName, searchstrings);
        BoolFilterBuilder missingFieldQuery = buildNestedMissingFieldFilter(path, idName);
        if (searchstrings == null || searchstrings.size() == 0) {
            return FilterBuilders.boolFilter().should(missingFieldQuery).should(searchFieldQuery);
        } else {
            return FilterBuilders.boolFilter().should(searchFieldQuery);
        }
    }

    public FilterBuilder buildShouldNestedFilter(String path, String idName, List<String> searchstrings) {
        return buildNestedShouldFilterWithMissingFields(path, idName, searchstrings);
    }

    public FilterBuilder buildShouldNestedFilterOld(String path, String idName, List<String> searchstrings) {
        return FilterBuilders
                .nestedFilter(
                        path,
                        queriesController.buildShouldNestedQuery(path, idName, searchstrings));
    }

    public FilterBuilder buildShouldNestedFilter(String path, List<String> searchstrings) {
        return buildShouldNestedFilter(path, idFieldname, searchstrings);
    }

    public RangeFilterBuilder buildIntegerRangeFilter(String field, Integer from, Integer to) {
        RangeFilterBuilder result = null;
        if (field == null) {
            return null;
        }
        Integer minValue, maxValue;
        if (from == null && to == null) {
            minValue = Integer.MIN_VALUE;
            maxValue = Integer.MAX_VALUE;
        } else {
            minValue = from;
            maxValue = to;
        }
        result = FilterBuilders.rangeFilter(field)
                .from(minValue).to(maxValue)
                .includeLower(true).includeUpper(true);
        return result;
    }

    public RangeFilterBuilder buildDoubleRangeFilter(String field, Double from, Double to) {
        RangeFilterBuilder result = null;
        if (field == null) {
            return null;
        }
        Double minValue, maxValue;
        if (from == null && to == null) {
            minValue = -Double.MAX_VALUE;
            maxValue = Double.MAX_VALUE;
        } else {
            minValue = from;
            maxValue = to;
        }
        result = FilterBuilders.rangeFilter(field)
                .from(minValue).to(maxValue)
                .includeLower(true).includeUpper(true);
        return result;
    }

    public RangeFilterBuilder buildDateRangeFilter(String field, Date from, Date to) {
        RangeFilterBuilder result = null;
        if (field == null) {
            return null;
        }
        if (from == null && to == null) {
            return FilterBuilders.rangeFilter(field)
                    .from(ElasticSearchConfig.MINIMAL_DATE).to(ElasticSearchConfig.MAXIMAL_DATE)
                    .includeLower(true).includeUpper(true);
        }
        result = FilterBuilders.rangeFilter(field)
                .from(from).to(to)
                .includeLower(true).includeUpper(true);
        return result;
    }

    public MissingFilterBuilder buildMissingFilter(String field) {
        if (field == null) {
            return null;
        }
        return FilterBuilders
                .missingFilter(field).existence(true).nullValue(true);
    }

    public ExistsFilterBuilder buildExistsFilter(String field) {
        if (field == null) {
            return null;
        }
        return FilterBuilders
                .existsFilter(field);

    }

    public BoolFilterBuilder buildDateRangeORMissingFilter(String field, Date from, Date to) {
        if (field == null) {
            return null;
        }
        MissingFilterBuilder missingFilterBuilder = buildMissingFilter(field);
        RangeFilterBuilder rangefilterBuilder = buildDateRangeFilter(field, from, to);

        if (rangefilterBuilder == null) {
            return FilterBuilders.boolFilter().should(missingFilterBuilder);
        }
        return FilterBuilders.boolFilter()
                .should(missingFilterBuilder)
                .should(rangefilterBuilder);
    }

}
