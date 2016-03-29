/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.controllers;

import de.adesso.referencer.search.config.ElasticSearchConfig;
import de.adesso.referencer.search.elastic.controllers.dto.FacetedSearchReferenceRequest;
import de.adesso.referencer.search.elastic.controllers.dto.FacetedSearchReferenceResponse;
import de.adesso.referencer.search.elastic.controllers.dto.FacetedSearchReferenceResponseWithEntitiesCount;
import de.adesso.referencer.search.elastic.controllers.dto.SearchAggregations;
import de.adesso.referencer.search.elastic.controllers.dto.SearchReferenceFilters;
import de.adesso.referencer.search.elastic.controllers.dto.TopHitsQuerries;
import de.adesso.referencer.search.elastic.entities.*;
import de.adesso.referencer.search.elastic.services.ReferenceService;
import de.adesso.referencer.search.helper.MyHelpMethods;
import de.adesso.referencer.search.rest.dto.EntitiesCount;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filters.InternalFilters;
import org.elasticsearch.search.aggregations.bucket.filters.InternalFilters.Bucket;
import org.elasticsearch.search.aggregations.bucket.missing.InternalMissing;
import org.elasticsearch.search.aggregations.bucket.nested.NestedBuilder;
import org.elasticsearch.search.aggregations.bucket.range.InternalRange;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.stats.StatsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class FacetedSearchController {

    @Autowired
    private ElasticFiltersController elasticFiltersController;

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private QueriesController queriesController;

    @Autowired
    private AggregationController aggregationController;

    private static final String idFieldname = ElasticSearchConfig.ID_FIELD_NAME;

    private static final String STATISTIC_ID_NAME = "ids";

    private static final String lobFieldname = ElasticSearchConfig.LOB_FIELD_NAME;
    private static final String LOBS_AGGREGATION_NAME = "lobs_aggregation";
    private static final String LOBS_STATISTIC_NAME = "lobs_statistic";

    private static final String branchFieldname = ElasticSearchConfig.BRANCH_FIELD_NAME;
    private static final String BRANCH_AGGREGATION_NAME = "branch_aggregation";
    private static final String BRANCH_STATISTIC_NAME = "branch_statistic";

    private static final String techFieldname = ElasticSearchConfig.TECHNOLOGY_FIELD_NAME;
    private static final String TECH_AGGREGATION_NAME = "tech_aggregation";
    private static final String TECH_STATISTIC_NAME = "tech_statistic";

    private static final String ptFieldname = ElasticSearchConfig.PT_ADESSO_FIELD_NAME;
    private static final String PT_AGGREGATION_NAME = "pt_aggregation";
    private static final String PT_STATISTIC_NAME = "pt_statistic";

    private static final String costFieldname = ElasticSearchConfig.COST_ADESSO_FIELD_NAME;
    private static final String COST_AGGREGATION_NAME = "cost_aggregation";
    private static final String COST_STATISTIC_NAME = "cost_statistic";

    private static final String PROJECT_END_OPTIONS_AGGS_NAME = "project_end_options";
    private static final String PROJECT_END_DATE_AGGS_NAME = "project_end_date_options";
    private static final String PROJECT_END_MISSING_AGGS_NAME = "project_end_mising_options";
    private static final String projectEndFieldName = ElasticSearchConfig.PROJECT_END_FIELD_NAME;

    private static final String SERVICE_START_AGGS_NAME = "service_start_aggregation";
    private static final String SERVICE_START_EXISTS_AGGS_NAME = "service_start_exists_aggregation";
    private static final String SERVICE_START_MISSING_AGGS_NAME = "service_start_missing_aggregation";
    private static final String serviceStartFieldName = ElasticSearchConfig.SERVICE_START_FIELD_NAME;

    private static final String SERVICE_END_OPTIONS_AGGS_NAME = "service_end_options";
    private static final String SERVICE_END_DATE_AGGS_NAME = "service_end_date_options";
    private static final String SERVICE_END_MISSING_AGGS_NAME = "service_end_mising_options";
    private static final String serviceEndFieldName = ElasticSearchConfig.SERVICE_END_FIELD_NAME;

    private static final String DATE_RANGE_MISSING_KEY = "dateMissing";

    private int maxPageSize = ElasticSearchConfig.MAXIMUM_PAGE_SIZE;

    private static final int[] dateYearDelayOptions = {0, 1, 3, 5};

    private SearchReferenceFilters buildFiltersForReferences(FacetedSearchReferenceRequest request) {
        SearchReferenceFilters result = new SearchReferenceFilters();
        result.setPtAdessoFilter(elasticFiltersController.buildIntegerRangeFilter(
                ptFieldname,
                request.getPtMin(),
                request.getPtMax()));
        result.setCostAdessoFilter(elasticFiltersController.buildDoubleRangeFilter(
                costFieldname,
                request.getCostMin(),
                request.getCostMax()));
        result.setLobsFilter(elasticFiltersController.buildShouldNestedFilter(lobFieldname, request.getLobsId()));
        result.setBranchesFilter(elasticFiltersController.buildShouldNestedFilter(branchFieldname, request.getBranchesId()));
        result.setTechnologyFilter(elasticFiltersController.buildShouldNestedFilter(techFieldname, request.getTechnologyId()));
        result.setProjectEndFilter(elasticFiltersController.buildDateRangeORMissingFilter(
                projectEndFieldName,
                request.getProjectEndMin(),
                request.getProjectEndMax()));
        result.setServiceEndFilter(elasticFiltersController.buildDateRangeORMissingFilter(
                serviceEndFieldName,
                request.getServiceEndMin(),
                request.getServiceEndMax()));
        result.setFreetextFilter(buildFreeTextFilter(request.getFreeText()));
        result.setFreeTextQuery(buildFreeTextQuery(request.getFreeText()));
        if (request.getServiceContract() != null) {
            if (request.getServiceContract().booleanValue() == false) {
                result.setServiceContractFilter(elasticFiltersController.buildMissingFilter(serviceStartFieldName));
            } else {
                result.setServiceContractFilter(elasticFiltersController.buildExistsFilter(serviceStartFieldName));
            }
        } else {
            result.setServiceContractFilter(FilterBuilders.queryFilter(QueryBuilders.matchAllQuery()));
        }

        return result;
    }

    private FilterBuilder buildAllFiltersForReferences(SearchReferenceFilters filters) {
        FilterBuilder result = FilterBuilders
                .boolFilter()
                .must(filters.getFreetextFilter())
                .must(filters.getPtAdessoFilter())
                .must(filters.getCostAdessoFilter())
                .must(filters.getLobsFilter())
                .must(filters.getBranchesFilter())
                .must(filters.getTechnologyFilter())
                .must(filters.getProjectEndFilter())
                .must(filters.getServiceContractFilter())
                .must(filters.getServiceEndFilter());
        return result;
    }

    private FilterBuilder buildNoFreeTextFiltersForReferences(SearchReferenceFilters filters) {
        FilterBuilder result = FilterBuilders
                .boolFilter()
                .must(filters.getPtAdessoFilter())
                .must(filters.getCostAdessoFilter())
                .must(filters.getLobsFilter())
                .must(filters.getBranchesFilter())
                .must(filters.getTechnologyFilter())
                .must(filters.getProjectEndFilter())
                .must(filters.getServiceContractFilter())
                .must(filters.getServiceEndFilter());
        return result;
    }

    private FilterBuilder buildFreeTextFilter(List<String> freiText) {
        BoolQueryBuilder query = queriesController.makeFreeTextQuery(freiText);
        if (query == null) {
            return FilterBuilders.matchAllFilter();
        }
        return FilterBuilders.queryFilter(query);
    }

    private QueryBuilder buildFreeTextQuery(List<String> freiText) {
        BoolQueryBuilder result = queriesController.makeFreeTextQuery(freiText);
        if (result == null) {
            return QueryBuilders.matchAllQuery();
        }
        return result;
    }

    private FilterBuilder buildLobsAggregationFilter(SearchReferenceFilters filters) {
        return FilterBuilders
                .boolFilter()
                .must(filters.getFreetextFilter())
                .must(filters.getPtAdessoFilter())
                .must(filters.getCostAdessoFilter())
                .must(filters.getBranchesFilter())
                .must(filters.getTechnologyFilter())
                .must(filters.getProjectEndFilter())
                .must(filters.getServiceContractFilter())
                .must(filters.getServiceEndFilter());
    }

    private FilterBuilder buildBranchesAggregationFilter(SearchReferenceFilters filters) {
        return FilterBuilders
                .boolFilter()
                .must(filters.getFreetextFilter())
                .must(filters.getPtAdessoFilter())
                .must(filters.getCostAdessoFilter())
                .must(filters.getLobsFilter())
                .must(filters.getTechnologyFilter())
                .must(filters.getProjectEndFilter())
                .must(filters.getServiceContractFilter())
                .must(filters.getServiceEndFilter());
    }

    private FilterBuilder buildTechnologyAggregationFilter(SearchReferenceFilters filters) {
        FilterBuilder result = FilterBuilders
                .boolFilter()
                .must(filters.getFreetextFilter())
                .must(filters.getPtAdessoFilter())
                .must(filters.getCostAdessoFilter())
                .must(filters.getLobsFilter())
                .must(filters.getBranchesFilter())
                .must(filters.getProjectEndFilter())
                .must(filters.getServiceContractFilter())
                .must(filters.getServiceEndFilter());
        return result;
    }

    private FilterBuilder buildPTAggregationFilter(SearchReferenceFilters filters) {
        FilterBuilder result = FilterBuilders
                .boolFilter()
                .must(filters.getFreetextFilter())
                .must(filters.getCostAdessoFilter())
                .must(filters.getLobsFilter())
                .must(filters.getBranchesFilter())
                .must(filters.getTechnologyFilter())
                .must(filters.getProjectEndFilter())
                .must(filters.getServiceContractFilter())
                .must(filters.getServiceEndFilter());
        return result;
    }

    private FilterBuilder buildCostAggregationFilter(SearchReferenceFilters filters) {
        FilterBuilder result = FilterBuilders
                .boolFilter()
                .must(filters.getFreetextFilter())
                .must(filters.getPtAdessoFilter())
                .must(filters.getLobsFilter())
                .must(filters.getBranchesFilter())
                .must(filters.getTechnologyFilter())
                .must(filters.getProjectEndFilter())
                .must(filters.getServiceContractFilter())
                .must(filters.getServiceEndFilter());
        return result;
    }

    private FilterBuilder buildProjectEndAggregationFilter(SearchReferenceFilters filters) {
        FilterBuilder result = FilterBuilders
                .boolFilter()
                .must(filters.getFreetextFilter())
                .must(filters.getPtAdessoFilter())
                .must(filters.getCostAdessoFilter())
                .must(filters.getLobsFilter())
                .must(filters.getBranchesFilter())
                .must(filters.getTechnologyFilter())
                .must(filters.getServiceContractFilter())
                .must(filters.getServiceEndFilter());
        return result;
    }

    private FilterBuilder buildServiceEndAggregationFilter(SearchReferenceFilters filters) {
        FilterBuilder result = FilterBuilders
                .boolFilter()
                .must(filters.getFreetextFilter())
                .must(filters.getPtAdessoFilter())
                .must(filters.getCostAdessoFilter())
                .must(filters.getLobsFilter())
                .must(filters.getBranchesFilter())
                .must(filters.getTechnologyFilter())
                .must(filters.getServiceContractFilter())
                .must(filters.getProjectEndFilter());
        return result;
    }

    private FilterBuilder buildServiceStartAggregationFilter(SearchReferenceFilters filters) {
        FilterBuilder result = FilterBuilders
                .boolFilter()
                .must(filters.getFreetextFilter())
                .must(filters.getPtAdessoFilter())
                .must(filters.getCostAdessoFilter())
                .must(filters.getLobsFilter())
                .must(filters.getBranchesFilter())
                .must(filters.getTechnologyFilter())
                .must(filters.getProjectEndFilter())
                .must(filters.getServiceEndFilter());;
        return result;
    }

    private FiltersAggregationBuilder buildPTAggregation(SearchReferenceFilters filters) {
        FilterBuilder filter = buildPTAggregationFilter(filters);
        StatsBuilder statsBuilder = aggregationController
                .buildStatsAggregation(PT_STATISTIC_NAME, ptFieldname);
        return AggregationBuilders.filters(PT_AGGREGATION_NAME)
                .filter(filter).subAggregation(statsBuilder);
    }

    private FiltersAggregationBuilder buildProjectEndAggregation(SearchReferenceFilters filters) {
        FilterBuilder filter = buildProjectEndAggregationFilter(filters);
        return AggregationBuilders.filters(PROJECT_END_OPTIONS_AGGS_NAME)
                .filter(filter)
                .subAggregation(aggregationController.buildRangeAggregation(
                                PROJECT_END_DATE_AGGS_NAME, projectEndFieldName))
                .subAggregation(aggregationController.buildMissingAggregation(
                                PROJECT_END_MISSING_AGGS_NAME, projectEndFieldName));
    }

    private FiltersAggregationBuilder buildServiceStartAggregation(SearchReferenceFilters filters) {
        FilterBuilder filter = buildServiceStartAggregationFilter(filters);
        return AggregationBuilders.filters(SERVICE_START_AGGS_NAME)
                .filter(filter)
                .subAggregation(aggregationController.buildMissingAggregation(
                                SERVICE_START_MISSING_AGGS_NAME, serviceStartFieldName))
                //                .subAggregation(aggregationController.buildMissingAggregation(
                //                                SERVICE_END_MISSING_AGGS_NAME, serviceEndFieldName));
                .subAggregation(AggregationBuilders.filters(SERVICE_START_EXISTS_AGGS_NAME).filter(FilterBuilders.existsFilter(serviceStartFieldName)));
    }

    private FiltersAggregationBuilder buildServiceEndAggregation(SearchReferenceFilters filters) {
        FilterBuilder filter = buildServiceEndAggregationFilter(filters);
        return AggregationBuilders.filters(SERVICE_END_OPTIONS_AGGS_NAME)
                .filter(filter)
                .subAggregation(aggregationController.buildRangeAggregation(
                                SERVICE_END_DATE_AGGS_NAME, serviceEndFieldName))
                .subAggregation(aggregationController.buildMissingAggregation(
                                SERVICE_END_MISSING_AGGS_NAME, serviceEndFieldName));
    }

    private FiltersAggregationBuilder buildCostAggregation(SearchReferenceFilters filters) {
        FilterBuilder filter = buildCostAggregationFilter(filters);
        StatsBuilder statsBuilder = aggregationController
                .buildStatsAggregation(COST_STATISTIC_NAME, costFieldname);
        return AggregationBuilders.filters(COST_AGGREGATION_NAME)
                .filter(filter).subAggregation(statsBuilder);
    }

    private FiltersAggregationBuilder buildLobsAggregation(SearchReferenceFilters filters) {
        FilterBuilder filter = buildLobsAggregationFilter(filters);
        NestedBuilder nestedBuilder = aggregationController
                .buildNestedTermAggregation(
                        LOBS_STATISTIC_NAME,
                        lobFieldname,
                        STATISTIC_ID_NAME,
                        lobFieldname + "." + idFieldname,
                        maxPageSize);
        return AggregationBuilders.filters(LOBS_AGGREGATION_NAME)
                .filter(filter).subAggregation(nestedBuilder);
    }

    private FiltersAggregationBuilder buildLobsAggregationWithTopHits(SearchReferenceFilters filters) {
        FilterBuilder filter = buildLobsAggregationFilter(filters);
        NestedBuilder nestedBuilder = aggregationController
                .buildNestedTermAggregationWithTopHits(
                        LOBS_STATISTIC_NAME,
                        lobFieldname,
                        STATISTIC_ID_NAME,
                        lobFieldname + "." + idFieldname,
                        maxPageSize);
        return AggregationBuilders.filters(LOBS_AGGREGATION_NAME)
                .filter(filter).subAggregation(nestedBuilder);
    }

    private FiltersAggregationBuilder buildBranchesAggregation(SearchReferenceFilters filters) {
        FilterBuilder filter = buildBranchesAggregationFilter(filters);
        NestedBuilder nestedBuilder = aggregationController
                .buildNestedTermAggregation(
                        BRANCH_STATISTIC_NAME,
                        branchFieldname,
                        STATISTIC_ID_NAME,
                        branchFieldname + "." + idFieldname,
                        maxPageSize);
        return AggregationBuilders.filters(BRANCH_AGGREGATION_NAME)
                .filter(filter).subAggregation(nestedBuilder);
    }

    private FiltersAggregationBuilder buildBranchesAggregationWithTopHits(SearchReferenceFilters filters) {
        FilterBuilder filter = buildBranchesAggregationFilter(filters);
        NestedBuilder nestedBuilder = aggregationController
                .buildNestedTermAggregationWithTopHits(
                        BRANCH_STATISTIC_NAME,
                        branchFieldname,
                        STATISTIC_ID_NAME,
                        branchFieldname + "." + idFieldname,
                        maxPageSize);
        return AggregationBuilders.filters(BRANCH_AGGREGATION_NAME)
                .filter(filter).subAggregation(nestedBuilder);
    }

    private FiltersAggregationBuilder buildTechAggregation(SearchReferenceFilters filters) {
        FilterBuilder filter = buildTechnologyAggregationFilter(filters);
        NestedBuilder nestedBuilder = aggregationController
                .buildNestedTermAggregation(
                        TECH_STATISTIC_NAME,
                        techFieldname,
                        STATISTIC_ID_NAME,
                        techFieldname + "." + idFieldname,
                        maxPageSize);
        return AggregationBuilders.filters(TECH_AGGREGATION_NAME)
                .filter(filter).subAggregation(nestedBuilder);
    }

    private FiltersAggregationBuilder buildTechAggregationWithTopHits(SearchReferenceFilters filters) {
        FilterBuilder filter = buildTechnologyAggregationFilter(filters);
        NestedBuilder nestedBuilder = aggregationController
                .buildNestedTermAggregationWithTopHits(
                        TECH_STATISTIC_NAME,
                        techFieldname,
                        STATISTIC_ID_NAME,
                        techFieldname + "." + idFieldname,
                        maxPageSize);
        return AggregationBuilders.filters(TECH_AGGREGATION_NAME)
                .filter(filter).subAggregation(nestedBuilder);
    }

    private SearchAggregations buildSearchAggregations(SearchReferenceFilters filters) {
        SearchAggregations result = new SearchAggregations();
        result.setLobsAggregation(buildLobsAggregation(filters));
        result.setBranchAggregation(buildBranchesAggregation(filters));
        result.setTechAggregation(buildTechAggregation(filters));
        result.setCostAggregation(buildCostAggregation(filters));
        result.setPtAggregation(buildPTAggregation(filters));
        result.setProjectEndAggregation(buildProjectEndAggregation(filters));
        result.setServiceEndAggregation(buildServiceEndAggregation(filters));
        return result;
    }

    private SearchAggregations buildSearchAggregationsWithTopHits(SearchReferenceFilters filters) {
        SearchAggregations result = new SearchAggregations();
        result.setLobsAggregation(buildLobsAggregationWithTopHits(filters));
        result.setBranchAggregation(buildBranchesAggregationWithTopHits(filters));
        result.setTechAggregation(buildTechAggregationWithTopHits(filters));
        result.setCostAggregation(buildCostAggregation(filters));
        result.setPtAggregation(buildPTAggregation(filters));
        result.setProjectEndAggregation(buildProjectEndAggregation(filters));
        result.setServiceEndAggregation(buildServiceEndAggregation(filters));
        result.setServiceStartAggregation(buildServiceStartAggregation(filters));
        return result;
    }

    private HashMap<String, Long> getLobStatistics(Aggregations aggregations) {
        return aggregationController
                .getStatisticFromSubNestedAggregation(LOBS_AGGREGATION_NAME, LOBS_STATISTIC_NAME, STATISTIC_ID_NAME, aggregations);
    }

    private List<EntitiesCount<Lob>> getLobStatisticsWithTopHits(Aggregations aggregations) {
        Terms termAggs = aggregationController.getTermsFromNestedAggregations(aggregations, LOBS_AGGREGATION_NAME, LOBS_STATISTIC_NAME, idFieldname);
        List<EntitiesCount<Lob>> result = aggregationController.getEntitiesCountFromNestedTermAggs(termAggs, Lob.class);
        return result;
    }

    private HashMap<String, Long> getBranchStatistics(Aggregations aggregations) {
        return aggregationController
                .getStatisticFromSubNestedAggregation(BRANCH_AGGREGATION_NAME, BRANCH_STATISTIC_NAME, STATISTIC_ID_NAME, aggregations);
    }

    private List<EntitiesCount<Branch>> getBranchStatisticsWithTopHits(Aggregations aggregations) {
        Terms termAggs = aggregationController.getTermsFromNestedAggregations(aggregations, BRANCH_AGGREGATION_NAME, BRANCH_STATISTIC_NAME, idFieldname);
        List<EntitiesCount<Branch>> result = aggregationController.getEntitiesCountFromNestedTermAggs(termAggs, Branch.class);
        return result;
    }

    private HashMap<String, Long> getTechStatistics(Aggregations aggregations) {
        return aggregationController
                .getStatisticFromSubNestedAggregation(TECH_AGGREGATION_NAME, TECH_STATISTIC_NAME, STATISTIC_ID_NAME, aggregations);
    }

    private List<EntitiesCount<Technology>> getTechStatisticsWithTopHits(Aggregations aggregations) {
        Terms termAggs = aggregationController.getTermsFromNestedAggregations(aggregations, TECH_AGGREGATION_NAME, TECH_STATISTIC_NAME, idFieldname);
        List<EntitiesCount<Technology>> result = aggregationController.getEntitiesCountFromNestedTermAggs(termAggs, Technology.class);
        return result;
    }

    private Integer[] getPTStatistic(Aggregations aggregations) {
        Double[] stat = aggregationController.getStatisticFromStatSubNestedAggregation(PT_AGGREGATION_NAME, PT_STATISTIC_NAME, aggregations);
        if (stat == null) {
            return null;
        }
        Integer[] result = new Integer[2];
        if (stat[0] != null) {
            result[0] = stat[0].intValue();
        }
        if (stat[1] != null) {
            result[1] = stat[1].intValue();
        }
        return result;
    }

    private Double[] getCostStatistic(Aggregations aggregations) {
        return aggregationController.getStatisticFromStatSubNestedAggregation(COST_AGGREGATION_NAME, COST_STATISTIC_NAME, aggregations);
    }

    private String[] getDateRangeAggsKeys() {
        return aggregationController.getDateRangeKeys();
    }

    public String[] getDateRangeKeys() {
        int resultLength = aggregationController.getDateRangeKeys().length + 1;
        String[] result = new String[resultLength];
        result[0] = DATE_RANGE_MISSING_KEY;
        for (int i = 1; i < resultLength; i++) {
            result[i] = aggregationController.getDateRangeKeys()[i - 1];
        }
        return result;
    }

    private HashMap<String, Long> getDateStatistic(String aggrName, String dateAggrName, String missingAggrName, Aggregations aggregations) {
        Aggregations subAggregations = aggregationController.extractSubAggregations(aggrName, aggregations);
        Map<String, Aggregation> aggsMap = subAggregations.asMap();
        InternalRange dateoptions = (InternalRange) aggsMap.get(dateAggrName);
        InternalMissing dateMissing = (InternalMissing) aggsMap.get(missingAggrName);
        Long[] dateDocCount = aggregationController.getDocCountForDate(dateoptions);
        HashMap<String, Long> result = new HashMap<>();
        result.put(DATE_RANGE_MISSING_KEY, dateMissing.getDocCount());
        for (int i = 0; i < dateDocCount.length; i++) {
            result.put(getDateRangeAggsKeys()[i], dateDocCount[i]);
        }
        return result;
    }

    private HashMap<FacetedSearchReferenceResponseWithEntitiesCount.ServiceContractFields, Long> getServiceContractStatistic(Aggregations aggregations) {
        HashMap<FacetedSearchReferenceResponseWithEntitiesCount.ServiceContractFields, Long> result = new HashMap<>();
        Long value;
        Aggregations subAggregations = aggregationController.extractSubAggregations(SERVICE_START_AGGS_NAME, aggregations);
        Map<String, Aggregation> aggsMap = subAggregations.asMap();
        InternalFilters existsResult = (InternalFilters) subAggregations.asMap().get(SERVICE_START_EXISTS_AGGS_NAME);
        Bucket existsBucket = existsResult.getBucketByKey("0");
        value = existsBucket.getDocCount();
        result.put(FacetedSearchReferenceResponseWithEntitiesCount.ServiceContractFields.FIELD_EXISTS, value);
        InternalMissing missingResult = (InternalMissing) subAggregations.asMap().get(SERVICE_START_MISSING_AGGS_NAME);
        value = missingResult.getDocCount();
        result.put(FacetedSearchReferenceResponseWithEntitiesCount.ServiceContractFields.FIELD_NOT_EXISTS, value);
        return result;
    }

    private HashMap<String, Long> getProjectEndDateStatistic(Aggregations aggregations) {
        return getDateStatistic(PROJECT_END_OPTIONS_AGGS_NAME, PROJECT_END_DATE_AGGS_NAME, PROJECT_END_MISSING_AGGS_NAME, aggregations);
    }

    private HashMap<String, Long> getServiceEndDateStatistic(Aggregations aggregations) {
        return getDateStatistic(SERVICE_END_OPTIONS_AGGS_NAME, SERVICE_END_DATE_AGGS_NAME, SERVICE_END_MISSING_AGGS_NAME, aggregations);
    }

    private SearchQuery buildSearchQueryForReferences(FacetedSearchReferenceRequest request) {
        QueryBuilder query = QueryBuilders.matchAllQuery();

        SearchReferenceFilters filters = buildFiltersForReferences(request);
        FilterBuilder referenceFilter = buildAllFiltersForReferences(filters);

        SearchAggregations aggregations = buildSearchAggregations(filters);
        SearchQuery result = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withFilter(referenceFilter)
                .addAggregation(aggregations.getLobsAggregation())
                .addAggregation(aggregations.getBranchAggregation())
                .addAggregation(aggregations.getTechAggregation())
                .addAggregation(aggregations.getCostAggregation())
                .addAggregation(aggregations.getPtAggregation())
                .addAggregation(aggregations.getProjectEndAggregation())
                .addAggregation(aggregations.getServiceEndAggregation())
                .withPageable(request.getPage())
                //                .withSort(null)
                .build();
        return result;
    }

    private TopHitsQuerries buildSearchQueryForReferencesWithTopHits(FacetedSearchReferenceRequest request) {
        TopHitsQuerries result = new TopHitsQuerries();

        SearchReferenceFilters filters = buildFiltersForReferences(request);

        NativeSearchQueryBuilder queryForPagesBuilder = new NativeSearchQueryBuilder()
                .withQuery(filters.getFreeTextQuery())
                .withFilter(buildNoFreeTextFiltersForReferences(filters))
                .withPageable(request.getPage());
        if (request.getSort() != null) {
            queryForPagesBuilder.withSort(request.getSort());
        }
        SearchQuery queryForPages = queryForPagesBuilder.build();
//        SearchQuery queryForPages = new NativeSearchQueryBuilder()
//                .withQuery(filters.getFreeTextQuery())
//                .withFilter(buildNoFreeTextFiltersForReferences(filters))
//                .withPageable(request.getPage())
//                .build();
        result.setQuerryForPages(queryForPages);

        SearchAggregations aggregations = buildSearchAggregationsWithTopHits(filters);
        SearchQuery queryForAggregations = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withFilter(buildAllFiltersForReferences(filters))
                .addAggregation(aggregations.getLobsAggregation())
                .addAggregation(aggregations.getBranchAggregation())
                .addAggregation(aggregations.getTechAggregation())
                .addAggregation(aggregations.getCostAggregation())
                .addAggregation(aggregations.getPtAggregation())
                .addAggregation(aggregations.getProjectEndAggregation())
                .addAggregation(aggregations.getServiceEndAggregation())
                .addAggregation(aggregations.getServiceStartAggregation())
                .withPageable(request.getPage())
                .build();
        result.setQuerryForAggregations(queryForAggregations);
        return result;
    }

    public FacetedSearchReferenceResponse facetedSearchForReferences(FacetedSearchReferenceRequest request) {
        FacetedSearchReferenceResponse result = new FacetedSearchReferenceResponse();
        SearchQuery searchQuery = buildSearchQueryForReferences(request);
        Page<Reference> pageOfReferences = referenceService.searchForPageOfReference(searchQuery);
        if (pageOfReferences != null) {
            result.setReferences(pageOfReferences.getContent());
            result.setTotalPages(pageOfReferences.getTotalPages());
            result.setTotalObjects(pageOfReferences.getTotalElements());
        }
        Aggregations aggregations = referenceService.queryForAggregations(searchQuery);
        result.setLobsStatistic(getLobStatistics(aggregations));
        result.setBranchStatistic(getBranchStatistics(aggregations));
        result.setTechStatistic(getTechStatistics(aggregations));
        result.setPtStatistic(getPTStatistic(aggregations));
        result.setCostStatistic(getCostStatistic(aggregations));
        result.setProjectEndStatistic(getProjectEndDateStatistic(aggregations));
        result.setServiceEndStatistic(getServiceEndDateStatistic(aggregations));
        return result;
    }

    public FacetedSearchReferenceResponseWithEntitiesCount facetedSearchForReferencesWithTopHits(FacetedSearchReferenceRequest request) {
        FacetedSearchReferenceResponseWithEntitiesCount result = new FacetedSearchReferenceResponseWithEntitiesCount();
        TopHitsQuerries querries = buildSearchQueryForReferencesWithTopHits(request);
        SearchQuery queryForPage = querries.getQuerryForPages();
        SearchQuery queryForAggregations = querries.getQuerryForAggregations();
        Aggregations aggregations = referenceService.queryForAggregations(queryForAggregations);
        result.setLobsStatistic(getLobStatisticsWithTopHits(aggregations));
        result.setBranchStatistic(getBranchStatisticsWithTopHits(aggregations));
        result.setTechStatistic(getTechStatisticsWithTopHits(aggregations));
        result.setPtStatistic(getPTStatistic(aggregations));
        result.setCostStatistic(getCostStatistic(aggregations));
        result.setProjectEndStatistic(getProjectEndDateStatistic(aggregations));
        result.setServiceEndStatistic(getServiceEndDateStatistic(aggregations));
        result.setServiceContractStatistic(getServiceContractStatistic(aggregations));
        Page<Reference> pageOfReferences = referenceService.searchForPageOfReference(queryForPage);
        if (pageOfReferences != null) {
            result.setReferences(pageOfReferences.getContent());
            result.setTotalPages(pageOfReferences.getTotalPages());
            result.setTotalObjects(pageOfReferences.getTotalElements());
        }

        return result;
    }
}
