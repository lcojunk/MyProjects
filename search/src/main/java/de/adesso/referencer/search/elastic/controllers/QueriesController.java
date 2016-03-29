/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.controllers;

import de.adesso.referencer.search.config.ElasticSearchConfig;
import de.adesso.referencer.search.elastic.controllers.dto.FacetedSearchReferenceRequest;
import de.adesso.referencer.search.elastic.controllers.dto.SearchAggregations;
import de.adesso.referencer.search.elastic.controllers.dto.SearchReferenceFilters;
import de.adesso.referencer.search.elastic.controllers.dto.SearchReferenceRequest;
import de.adesso.referencer.search.helper.MyHelpMethods;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.FilteredQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MissingFilterBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import org.elasticsearch.index.query.RangeFilterBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.missing.InternalMissing;
import org.elasticsearch.search.aggregations.bucket.nested.NestedBuilder;
import org.elasticsearch.search.aggregations.bucket.range.InternalRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class QueriesController {

    private int maxPageSize = Integer.MAX_VALUE;
    private String idFieldname = ElasticSearchConfig.ID_FIELD_NAME;

    @Autowired
    private AggregationController aggregationController;

    private BoolQueryBuilder buildMissingFieldQuery(String path, String idName) {
        if (path == null || idName == null) {
            return null;
        }
        return QueryBuilders
                .boolQuery()
                .mustNot(QueryBuilders
                        .nestedQuery(path, QueryBuilders
                                .filteredQuery(QueryBuilders.matchAllQuery(), FilterBuilders.existsFilter(path + "." + idName))));
    }

    public NestedQueryBuilder buildNestedShouldQueryWithoutNullFields(String path, String idName, List<String> searchstrings) {
        if (path == null || idName == null) {
            return null;
        }
        if (searchstrings == null || searchstrings.size() == 0) {
            return QueryBuilders.nestedQuery(path, QueryBuilders.boolQuery().should(QueryBuilders.matchAllQuery()));
        };
        BoolQueryBuilder idQuery = QueryBuilders.boolQuery().should(QueryBuilders.matchQuery(path + "." + idName, searchstrings.get(0)));
        if (searchstrings.size() == 1) {
            return QueryBuilders.nestedQuery(path, idQuery);
        }
        for (int i = 1; i < searchstrings.size(); i++) {
            idQuery.should(QueryBuilders.matchQuery(path + "." + idName, searchstrings.get(i)));
        }
        return QueryBuilders.nestedQuery(path, idQuery);
    }

    public BoolQueryBuilder buildNestedShouldQueryWithMissingFields(String path, String idName, List<String> userSearchstrings) {
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
        NestedQueryBuilder searchFieldQuery = buildNestedShouldQueryWithoutNullFields(path, idName, searchstrings);
        BoolQueryBuilder missingFieldQuery = buildMissingFieldQuery(path, idName);
        if (searchstrings == null || searchstrings.size() == 0) {
            return QueryBuilders.boolQuery().should(missingFieldQuery).should(searchFieldQuery);
        } else {
            return QueryBuilders.boolQuery().should(searchFieldQuery);
        }
    }

    public BoolQueryBuilder buildShouldNestedQuery(String path, String idName, List<String> searchstrings) {
        return buildNestedShouldQueryWithMissingFields(path, idName, searchstrings);
    }

    public BoolQueryBuilder buildShouldNestedQueryOld(String path, String idName, List<String> searchstrings) {
        if (path == null || idName == null) {
            return null;
        }
        if (searchstrings == null || searchstrings.size() == 0) {
            return QueryBuilders.boolQuery().should(matchAllQuery());
        };
        BoolQueryBuilder result = QueryBuilders.boolQuery().should(QueryBuilders.matchQuery(path + "." + idName, searchstrings.get(0)));
        if (searchstrings.size() == 1) {
            return result;
        }
        for (int i = 1; i < searchstrings.size(); i++) {
            result.should(QueryBuilders.matchQuery(path + "." + idName, searchstrings.get(i)));
        }
        return result;
    }

    public RangeQueryBuilder buildIntegerRangeQuery(String field, Integer from, Integer to) {
        RangeQueryBuilder result = null;
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
        result = QueryBuilders.rangeQuery(field)
                .from(minValue).to(maxValue)
                .includeLower(true).includeUpper(true);
        return result;
    }

    public RangeQueryBuilder buildDoubleRangeFilter(String field, Double from, Double to) {
        RangeQueryBuilder result = null;
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
        result = QueryBuilders.rangeQuery(field)
                .from(minValue).to(maxValue)
                .includeLower(true).includeUpper(true);
        return result;
    }

    public RangeQueryBuilder buildDateRangeFilter(String field, Date from, Date to) {
        RangeQueryBuilder result = null;
        if (field == null) {
            return null;
        }
        if (from == null && to == null) {
            return QueryBuilders.rangeQuery(field)
                    .from(ElasticSearchConfig.MINIMAL_DATE).to(ElasticSearchConfig.MAXIMAL_DATE)
                    .includeLower(true).includeUpper(true);
        }
        result = QueryBuilders.rangeQuery(field)
                .from(from).to(to)
                .includeLower(true).includeUpper(true);
        return result;
    }

    public BoolQueryBuilder makeFreeTextQuery(List<String> freiText) {
        if (freiText == null || freiText.size() < 1) {
            return null;
        }
        if (freiText.size() == 1) {
            return makeFreeTextOrQuery(freiText);
        }
        BoolQueryBuilder result = QueryBuilders.boolQuery().must(makeFreeTextOrQuery(freiText.get(0)));
        for (int i = 1; i < freiText.size(); i++) {
            result.must(makeFreeTextOrQuery(freiText.get(i)));
        }
        return result;
    }

    public BoolQueryBuilder makeFreeTextOrQuery(String freeTextString) {
        if (freeTextString == null) {
            return null;
        }
        List<String> freeText = new ArrayList<>();
        freeText.add(freeTextString);
        return makeFreeTextOrQuery(freeText);
    }

    public BoolQueryBuilder makeFreeTextOrQuery(List<String> freiText) {
        if (freiText == null || freiText.size() < 1) {
            return null;
        }
        BoolQueryBuilder result = QueryBuilders.boolQuery()
                .should(buildQueryForTockens("projectname", freiText))
                .should(buildQueryForTockens("clientname", freiText))
                .should(nestedQuery("lob", boolQuery().should(buildQueryForTockens("lob.name", freiText))))
                .should(nestedQuery("branches", boolQuery().should(buildQueryForTockens("branches.name", freiText))))
                .should(buildQueryForTockens("ptTotalProjectString", freiText))
                .should(buildQueryForTockens("ptAdessoString", freiText))
                .should(buildQueryForTockens("costAdessoString", freiText))
                .should(buildQueryForTockens("costTotalString", freiText))
                .should(buildQueryForTockens("projectStartString", freiText))
                .should(buildQueryForTockens("projectEndString", freiText))
                .should(buildQueryForTockens("serviceStartString", freiText))
                .should(buildQueryForTockens("serviceEndString", freiText))
                .should(buildQueryForTockens("releaseStatusString", freiText))
                .should(nestedQuery("released.user", boolQuery().should(buildQueryForTockens("released.user.first_name", freiText))))
                .should(nestedQuery("released.user", boolQuery().should(buildQueryForTockens("released.user.last_name", freiText))))
                .should(nestedQuery("released", boolQuery().should(buildQueryForTockens("released.dateString", freiText))))
                .should(nestedQuery("involvedRoles", boolQuery().should(buildQueryForTockens("involvedRoles.name", freiText))))
                .should(nestedQuery("technologies", boolQuery().should(buildQueryForTockens("technologies.name", freiText))))
                .should(nestedQuery("focuses", boolQuery().should(buildQueryForTockens("focuses.name", freiText))))
                .should(nestedQuery("characteristics", boolQuery().should(buildQueryForTockens("characteristics.name", freiText))))
                .should(nestedQuery("teammembers", boolQuery().should(buildQueryForTockens("teammembers.first_name", freiText))))
                .should(nestedQuery("teammembers", boolQuery().should(buildQueryForTockens("teammembers.last_name", freiText))))
                .should(nestedQuery("teammembers", boolQuery().should(buildQueryForTockens("teammembers.email", freiText))))
                .should(nestedQuery("teammembers", boolQuery().should(buildQueryForTockens("teammembers.tel", freiText))))
                .should(nestedQuery("teammembers.role", boolQuery().should(buildQueryForTockens("teammembers.role.name", freiText))))
                //                .should(nestedQuery("adessoPartner", boolQuery().should(buildQueryForTockens("adessoPartner.first_name", freiText))))
                //                .should(nestedQuery("adessoPartner", boolQuery().should(buildQueryForTockens("adessoPartner.last_name", freiText))))
                .should(buildQueryForTockens("adessoContactName", freiText))
                //                .should(nestedQuery("adessoPartner", boolQuery().should(buildQueryForTockens("adessoPartner.email", freiText))))
                .should(buildQueryForTockens("adessoContactMail", freiText))
                //                .should(nestedQuery("adessoPartner", boolQuery().should(buildQueryForTockens("adessoPartner.tel", freiText))))
                .should(buildQueryForTockens("adessoContactPhone", freiText))
                //                .should(nestedQuery("adessoPartner.role", boolQuery().should(buildQueryForTockens("adessoPartner.role.name", freiText))))
                .should(nestedQuery("adessoContactAdessoRole", boolQuery().should(buildQueryForTockens("adessoContactAdessoRole.name", freiText))))
                .should(nestedQuery("adessoContactProjectRole", boolQuery().should(buildQueryForTockens("adessoContactProjectRole.name", freiText))))
                //                .should(nestedQuery("deputy", boolQuery().should(buildQueryForTockens("deputy.first_name", freiText))))
                //                .should(nestedQuery("deputy", boolQuery().should(buildQueryForTockens("deputy.last_name", freiText))))
                //                .should(nestedQuery("deputy", boolQuery().should(buildQueryForTockens("deputy.email", freiText))))
                //                .should(nestedQuery("deputy", boolQuery().should(buildQueryForTockens("deputy.tel", freiText))))
                .should(buildQueryForTockens("adessoDeputyContactName", freiText))
                //                .should(nestedQuery("deputy.role", boolQuery().should(buildQueryForTockens("deputy.role.name", freiText))))
                .should(nestedQuery("adessoDeputyContactProjectRole", boolQuery().should(buildQueryForTockens("adessoDeputyContactProjectRole.name", freiText))))
                .should(nestedQuery("clientData", boolQuery().should(buildQueryForTockens("clientData.first_name", freiText))))
                .should(nestedQuery("clientData", boolQuery().should(buildQueryForTockens("clientData.last_name", freiText))))
                //                .should(nestedQuery("clientData", boolQuery().should(buildQueryForTockens("clientData.email", freiText))))
                //                .should(nestedQuery("clientData", boolQuery().should(buildQueryForTockens("clientData.tel", freiText))))
                //                .should(nestedQuery("clientData", boolQuery().should(buildQueryForTockens("clientData.titel", freiText))))
                .should(nestedQuery("clientData", boolQuery().should(buildQueryForTockens("clientData.address", freiText))))
                .should(nestedQuery("clientData", boolQuery().should(buildQueryForTockens("clientData.testimonal", freiText))))
                .should(nestedQuery("clientData", boolQuery().should(buildQueryForTockens("clientData.description", freiText))))
                .should(buildQueryForTockens("clientProfil", freiText))
                .should(buildQueryForTockens("projectBackground", freiText))
                .should(buildQueryForTockens("projectSolution", freiText))
                .should(buildQueryForTockens("projectResults", freiText))
                .should(buildQueryForTockens("preciseDescription", freiText))
                .should(buildQueryForTockens("description", freiText));
        return result;
    }

    //Not used, but is not so complicated as makeFreeTextQuery.
    // all token are collected in one String, being divided with space
    private BoolQueryBuilder makeNoTockensFreeTextQuery(List<String> freiText) {
        if (freiText == null || freiText.size() < 1) {
            return null;
        }
        String freiTextString = "";
        for (String s : freiText) {
            if (s != null) {
                freiTextString += s + " ";
            }
        }
        if (freiTextString == null || freiTextString.matches("")) {
            return null;
        }
        BoolQueryBuilder result = QueryBuilders.boolQuery()
                .should(matchQuery("projectname", freiTextString))
                .should(matchQuery("clientname", freiTextString))
                .should(nestedQuery("lob", boolQuery().should(matchQuery("lob.name", freiTextString))))
                .should(nestedQuery("branches", boolQuery().should(matchQuery("branches.name", freiTextString))))
                .should(matchQuery("ptTotalProjectString", freiTextString))
                .should(matchQuery("ptAdessoString", freiTextString))
                .should(matchQuery("costAdessoString", freiTextString))
                .should(matchQuery("costTotalString", freiTextString))
                .should(matchQuery("projectStartString", freiTextString))
                .should(matchQuery("projectEndString", freiTextString))
                .should(matchQuery("serviceStartString", freiTextString))
                .should(matchQuery("serviceEndString", freiTextString))
                .should(nestedQuery("involvedRoles", boolQuery().should(matchQuery("involvedRoles.name", freiTextString))))
                .should(nestedQuery("technologies", boolQuery().should(matchQuery("technologies.name", freiTextString))))
                .should(nestedQuery("focuses", boolQuery().should(matchQuery("focuses.name", freiTextString))))
                .should(nestedQuery("characteristics", boolQuery().should(matchQuery("characteristics.name", freiTextString))))
                .should(nestedQuery("teammembers", boolQuery().should(matchQuery("teammembers.first_name", freiTextString))))
                .should(nestedQuery("teammembers", boolQuery().should(matchQuery("teammembers.last_name", freiTextString))))
                .should(nestedQuery("teammembers", boolQuery().should(matchQuery("teammembers.email", freiTextString))))
                .should(nestedQuery("teammembers", boolQuery().should(matchQuery("teammembers.tel", freiTextString))))
                .should(nestedQuery("teammembers.role", boolQuery().should(matchQuery("teammembers.role.name", freiTextString))))
                //                .should(nestedQuery("adessoPartner", boolQuery().should(buildQueryForTockens("adessoPartner.first_name", freiText))))
                //                .should(nestedQuery("adessoPartner", boolQuery().should(buildQueryForTockens("adessoPartner.last_name", freiText))))
                .should(buildQueryForTockens("adessoContactName", freiText))
                //                .should(nestedQuery("adessoPartner", boolQuery().should(buildQueryForTockens("adessoPartner.email", freiText))))
                .should(buildQueryForTockens("adessoContactMail", freiText))
                //                .should(nestedQuery("adessoPartner", boolQuery().should(buildQueryForTockens("adessoPartner.tel", freiText))))
                .should(buildQueryForTockens("adessoContactPhone", freiText))
                //                .should(nestedQuery("adessoPartner.role", boolQuery().should(buildQueryForTockens("adessoPartner.role.name", freiText))))
                .should(nestedQuery("adessoContactAdessoRole", boolQuery().should(buildQueryForTockens("adessoContactAdessoRole.name", freiText))))
                .should(nestedQuery("adessoContactProjectRole", boolQuery().should(buildQueryForTockens("adessoContactProjectRole.name", freiText))))
                //                .should(nestedQuery("deputy", boolQuery().should(buildQueryForTockens("deputy.first_name", freiText))))
                //                .should(nestedQuery("deputy", boolQuery().should(buildQueryForTockens("deputy.last_name", freiText))))
                //                .should(nestedQuery("deputy", boolQuery().should(buildQueryForTockens("deputy.email", freiText))))
                //                .should(nestedQuery("deputy", boolQuery().should(buildQueryForTockens("deputy.tel", freiText))))
                .should(buildQueryForTockens("adessoDeputyContactName", freiText))
                //                .should(nestedQuery("deputy.role", boolQuery().should(buildQueryForTockens("deputy.role.name", freiText))))
                .should(nestedQuery("adessoDeputyContactProjectRole", boolQuery().should(matchQuery("adessoDeputyContactProjectRole.name", freiTextString))))
                .should(nestedQuery("clientData", boolQuery().should(matchQuery("clientData.first_name", freiTextString))))
                .should(nestedQuery("clientData", boolQuery().should(matchQuery("clientData.last_name", freiTextString))))
                .should(nestedQuery("clientData", boolQuery().should(matchQuery("clientData.email", freiTextString))))
                .should(nestedQuery("clientData", boolQuery().should(matchQuery("clientData.tel", freiTextString))))
                .should(nestedQuery("clientData", boolQuery().should(matchQuery("clientData.titel", freiTextString))))
                .should(nestedQuery("clientData", boolQuery().should(matchQuery("clientData.address", freiTextString))))
                .should(nestedQuery("clientData", boolQuery().should(matchQuery("clientData.testimonal", freiTextString))))
                .should(nestedQuery("clientData", boolQuery().should(matchQuery("clientData.description", freiTextString))))
                .should(matchQuery("clientProfil", freiTextString))
                .should(matchQuery("projectBackground", freiTextString))
                .should(matchQuery("projectSolution", freiTextString))
                .should(matchQuery("projectResults", freiTextString))
                .should(matchQuery("preciseDescription", freiTextString))
                .should(matchQuery("description", freiTextString));
        return result;
    }

    private BoolQueryBuilder appendMustQuery(BoolQueryBuilder baseQueryBuilder, QueryBuilder append) {
        if (append == null) {
            return baseQueryBuilder;
        }
        if (baseQueryBuilder == null) {
            baseQueryBuilder = QueryBuilders.boolQuery();
        }
        return baseQueryBuilder.must(append);
    }

    private BoolQueryBuilder appendShouldQuery(BoolQueryBuilder baseQueryBuilder, QueryBuilder append) {
        if (append == null) {
            return baseQueryBuilder;
        }
        if (baseQueryBuilder == null) {
            baseQueryBuilder = QueryBuilders.boolQuery();
        }
        return baseQueryBuilder.should(append);
    }

    private SearchQuery buildQueryForMax(String indexname, String typename, String fieldname) {
        if (indexname == null || indexname.matches("")
                || typename == null || typename.matches("")
                || fieldname == null || fieldname.matches("")) {
            return null;
        }
        SearchQuery searchQuery;
        searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withSearchType(SearchType.COUNT)
                .withIndices(indexname).withTypes(typename)
                .addAggregation(AggregationBuilders.max(fieldname).field(fieldname))
                .withPageable(new PageRequest(0, maxPageSize))
                .build();
        return searchQuery;
    }

    public SearchQuery buildQueryForMaxForReference(String fieldname) {
        return buildQueryForMax(ElasticSearchConfig.INDEX_NAME, ElasticSearchConfig.TYPE_NAME_REFERENCE, fieldname);
    }

    public QueryBuilder buildQueryForTocken(String name, String tocken) {
        if (name == null || tocken == null) {
            return null;
        }
        return QueryBuilders.boolQuery()
                .should(matchQuery(name, tocken).type(MatchQueryBuilder.Type.PHRASE));
        //.should(matchQuery(name, tocken));
    }

    public QueryBuilder buildQueryForTockens(String name, List<String> tockens) {
        if (name == null || tockens == null || tockens.size() == 0) {
            return null;
        }
        BoolQueryBuilder boolQueryBuilder = null;
        for (String s : tockens) {
            boolQueryBuilder = appendShouldQuery(boolQueryBuilder, buildQueryForTocken(name, s));
            //boolQueryBuilder = appendMustQuery(boolQueryBuilder, buildQueryForTocken(name, s));
        }
        return boolQueryBuilder;
    }

    public SearchQuery buildSearchQueryForTockens(String name, List<String> tockens) {
        QueryBuilder queryBuilder = buildQueryForTockens(name, tockens);
        SearchQuery searchQuery;
        if (queryBuilder == null) {
            searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(matchAllQuery())
                    .withPageable(new PageRequest(0, maxPageSize))
                    .build();
        } else {
            searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(queryBuilder)
                    .withPageable(new PageRequest(0, maxPageSize))
                    .build();
        }
        return searchQuery;
    }

}
