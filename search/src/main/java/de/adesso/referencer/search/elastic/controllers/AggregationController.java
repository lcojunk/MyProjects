/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.controllers;

import de.adesso.referencer.search.helper.MyHelpMethods;
import de.adesso.referencer.search.rest.dto.EntitiesCount;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filters.InternalFilters;

import org.elasticsearch.search.aggregations.bucket.missing.InternalMissing;
import org.elasticsearch.search.aggregations.bucket.missing.MissingBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.nested.NestedBuilder;
import org.elasticsearch.search.aggregations.bucket.range.InternalRange;
import org.elasticsearch.search.aggregations.bucket.range.RangeBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.max.MaxBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.StatsBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.InternalTopHits;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class AggregationController {

    private String costAggregation = "costAdesso";
    private String branchAggregation = "branches";
    private String idFieldname = "id";
    private static final int TOP_HIT_AGGS_SIZE = 1;
    private static String[] dateRangeKeys = {
        "notFinished",
        "oneYear",
        "threeYears",
        "fiveYears"
    };
    private static final String TOP_HITS_AGGS_NAME = "topHitsName";
    private static final String STATISTIC_ID_NAME = "ids";
    private static final String BUCKET_KEY_STRING = "0";
    private static final int TOP_HITS_AGGS_SIZE = 1;

    public String getTopHitsAggsName() {
        return TOP_HITS_AGGS_NAME;
    }

    public static String[] getDateRangeKeys() {
        return dateRangeKeys;
    }

    public StatsBuilder buildStatsAggregation(String name, String fieldname) {
        return AggregationBuilders.stats(name).field(fieldname);
    }

    public NestedBuilder buildNestedTermAggregation(String aggsName, String pathname, String subAggsName, String termName, int count) {
        return AggregationBuilders
                .nested(aggsName)
                .path(pathname)
                .subAggregation(
                        AggregationBuilders.terms(subAggsName).field(termName).size(count)
                );
    }

    public NestedBuilder buildNestedTermAggregationWithTopHits(String aggsName, String fieldName, String subAggsName, String nestedFieldName, int count) {
        return AggregationBuilders
                .nested(aggsName)
                .path(fieldName)
                .subAggregation(
                        AggregationBuilders
                        .terms(subAggsName).field(nestedFieldName).size(count)
                        .subAggregation(AggregationBuilders.topHits(TOP_HITS_AGGS_NAME).setSize(TOP_HITS_AGGS_SIZE))
                );
    }

    public RangeBuilder buildRangeAggregation(String name, String field) {
        Date today = new Date();
        double todayDouble = today.getTime();
        double minus1year = MyHelpMethods.addYearsToDate(today, -1).getTime();
        double minus3year = MyHelpMethods.addYearsToDate(today, -3).getTime();
        double minus5year = MyHelpMethods.addYearsToDate(today, -5).getTime();

        RangeBuilder result = AggregationBuilders.range(name).field(field);
        result.addRange(dateRangeKeys[0], todayDouble, Double.MAX_VALUE);
        result.addRange(dateRangeKeys[1], minus1year, todayDouble);
        result.addRange(dateRangeKeys[2], minus3year, todayDouble);
        result.addRange(dateRangeKeys[3], minus5year, todayDouble);
        return result;
    }

    public Long[] getDocCountForDate(InternalRange rangeAggregation) {
        if (rangeAggregation == null) {
            return null;
        }

        Long[] result = new Long[dateRangeKeys.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = rangeAggregation.getBucketByKey(dateRangeKeys[i]).getDocCount();
        }
        return result;
    }

    public MissingBuilder buildMissingAggregation(String name, String field) {
        MissingBuilder result = AggregationBuilders
                .missing(name)
                .field(field);
        return result;
    }

    public HashMap<String, Long> getStatisticFromNestedAggregation(String path, String id, Aggregations aggregations) {
        if (path == null || id == null || aggregations == null) {
            return null;
        }
        HashMap<String, Long> result = new HashMap<>();
        Nested bucketAggregation = (Nested) aggregations.asMap().get(path);
        Map<String, Aggregation> bucketMap = bucketAggregation.getAggregations().asMap();
        Terms terms = (Terms) bucketMap.get(id);
        List<Terms.Bucket> buckets = terms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            result.put(bucket.getKey(), bucket.getDocCount());
        }
        return result;
    }

    public HashMap<String, Long> getStatisticFromSubNestedAggregation(
            String mainName,
            String subName,
            String idName,
            Aggregations aggregations) {
        Map<String, Aggregation> aggregationsMap = aggregations.asMap();
        InternalFilters filter = (InternalFilters) aggregationsMap.get(mainName);
        MultiBucketsAggregation.Bucket bucket = filter.getBucketByKey("0");
        Aggregations bucketAggregations = bucket.getAggregations();
        return getStatisticFromNestedAggregation(subName, idName, bucketAggregations);
    }

    public Double[] getStatisticFromStatAggregation(
            String name,
            Aggregations aggregations) {
        if (name == null || aggregations == null) {
            return null;
        }
        Map<String, Aggregation> aggsMap = aggregations.asMap();

        Stats stat = (Stats) aggsMap.get(name);

        Double[] result = new Double[2];
        if (stat.getCount() == 0) {
            result[0] = 0.0;
            result[1] = 0.0;
            return result;
        }
        result[0] = stat.getMin();
        result[1] = stat.getMax();
        return result;
    }

    public Double[] getStatisticFromStatSubNestedAggregation(
            String mainName,
            String subName,
            Aggregations aggregations) {
        if (mainName == null || subName == null || aggregations == null) {
            return null;
        }
        Map<String, Aggregation> aggregationsMap = aggregations.asMap();
        InternalFilters filter = (InternalFilters) aggregationsMap.get(mainName);
        MultiBucketsAggregation.Bucket bucket = filter.getBucketByKey("0");
        Aggregations bucketAggregations = bucket.getAggregations();
        return getStatisticFromStatAggregation(subName, bucketAggregations);
    }

    public Aggregations extractSubAggregations(String aggsName, Aggregations aggregations) {
        if (aggsName == null || aggregations == null) {
            return null;
        }
        Map<String, Aggregation> aggregationsMap = aggregations.asMap();
        InternalFilters filter = (InternalFilters) aggregationsMap.get(aggsName);
        MultiBucketsAggregation.Bucket bucket = filter.getBucketByKey("0");
        Aggregations bucketAggregations = bucket.getAggregations();
        return bucketAggregations;
    }

    public <T> List<EntitiesCount<T>> getEntitiesCountFromNestedTermAggs(Terms termAggs, Class<T> clazz) {
        List<org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket> buckets = termAggs.getBuckets();
        InternalTopHits topHits;
        String s;
        T entity;
        EntitiesCount<T> entitiesCount;
        List<EntitiesCount<T>> result = new ArrayList<>();
        for (org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket bucket : buckets) {
            topHits = (InternalTopHits) bucket.getAggregations().asMap().get(TOP_HITS_AGGS_NAME);
            s = topHits.getHits().getHits()[0].getSourceAsString();
            entity = MyHelpMethods.getGson().fromJson(s, clazz);
            entitiesCount = new EntitiesCount<>();
            entitiesCount.setEntity(entity);
            entitiesCount.setCount(bucket.getDocCount());
            result.add(entitiesCount);
        }
        return result;
    }

    public Terms getTermsFromNestedAggregations(
            Aggregations aggregations,
            String aggsName,
            String nestedName,
            String fieldName) {
        if (aggregations == null) {
            return null;
        }
        Map<String, Aggregation> myMap = aggregations.asMap();
        InternalFilters filter = (InternalFilters) myMap.get(aggsName);
        MultiBucketsAggregation.Bucket bucket = filter.getBucketByKey(BUCKET_KEY_STRING);
        Aggregations bucketAggregations = bucket.getAggregations();
        Nested nestedAggs = (Nested) bucketAggregations.asMap().get(nestedName);
        Terms result = (Terms) nestedAggs.getAggregations().asMap().get(STATISTIC_ID_NAME);
        return result;
    }

}
