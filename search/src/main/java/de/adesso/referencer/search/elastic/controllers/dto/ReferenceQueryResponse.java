/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.controllers.dto;

import de.adesso.referencer.search.elastic.entities.Reference;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.data.domain.Page;

/**
 *
 * @author odzhara-ongom
 */
public class ReferenceQueryResponse {

    private Page<Reference> pageOfReferences;
    private Aggregations aggregations;

    public Page<Reference> getPageOfReferences() {
        return pageOfReferences;
    }

    public void setPageOfReferences(Page<Reference> pageOfReferences) {
        this.pageOfReferences = pageOfReferences;
    }

    public Aggregations getAggregations() {
        return aggregations;
    }

    public void setAggregations(Aggregations aggregations) {
        this.aggregations = aggregations;
    }

}
