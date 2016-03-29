/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.controllers.dto;

import org.springframework.data.elasticsearch.core.query.SearchQuery;

/**
 *
 * @author odzhara-ongom
 */
public class TopHitsQuerries {

    private SearchQuery querryForAggregations;
    private SearchQuery querryForPages;

    public SearchQuery getQuerryForAggregations() {
        return querryForAggregations;
    }

    public void setQuerryForAggregations(SearchQuery querryForAggregations) {
        this.querryForAggregations = querryForAggregations;
    }

    public SearchQuery getQuerryForPages() {
        return querryForPages;
    }

    public void setQuerryForPages(SearchQuery querryForPages) {
        this.querryForPages = querryForPages;
    }

}
