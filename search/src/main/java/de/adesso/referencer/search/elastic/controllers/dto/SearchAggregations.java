/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.controllers.dto;

import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregationBuilder;

/**
 *
 * @author odzhara-ongom
 */
public class SearchAggregations {

    private FiltersAggregationBuilder lobsAggregation;
    private FiltersAggregationBuilder branchAggregation;
    private FiltersAggregationBuilder techAggregation;
    private FiltersAggregationBuilder ptAggregation;
    private FiltersAggregationBuilder costAggregation;
    private FiltersAggregationBuilder projectEndAggregation;
    private FiltersAggregationBuilder serviceEndAggregation;
    private FiltersAggregationBuilder serviceStartAggregation;

    public FiltersAggregationBuilder getLobsAggregation() {
        return lobsAggregation;
    }

    public void setLobsAggregation(FiltersAggregationBuilder lobsAggregation) {
        this.lobsAggregation = lobsAggregation;
    }

    public FiltersAggregationBuilder getBranchAggregation() {
        return branchAggregation;
    }

    public void setBranchAggregation(FiltersAggregationBuilder branchAggregation) {
        this.branchAggregation = branchAggregation;
    }

    public FiltersAggregationBuilder getTechAggregation() {
        return techAggregation;
    }

    public void setTechAggregation(FiltersAggregationBuilder techAggregation) {
        this.techAggregation = techAggregation;
    }

    public FiltersAggregationBuilder getPtAggregation() {
        return ptAggregation;
    }

    public void setPtAggregation(FiltersAggregationBuilder ptAggregation) {
        this.ptAggregation = ptAggregation;
    }

    public FiltersAggregationBuilder getCostAggregation() {
        return costAggregation;
    }

    public void setCostAggregation(FiltersAggregationBuilder costAggregation) {
        this.costAggregation = costAggregation;
    }

    public FiltersAggregationBuilder getProjectEndAggregation() {
        return projectEndAggregation;
    }

    public void setProjectEndAggregation(FiltersAggregationBuilder projectEndAggregation) {
        this.projectEndAggregation = projectEndAggregation;
    }

    public FiltersAggregationBuilder getServiceEndAggregation() {
        return serviceEndAggregation;
    }

    public void setServiceEndAggregation(FiltersAggregationBuilder serviceEndAggregation) {
        this.serviceEndAggregation = serviceEndAggregation;
    }

    public FiltersAggregationBuilder getServiceStartAggregation() {
        return serviceStartAggregation;
    }

    public void setServiceStartAggregation(FiltersAggregationBuilder serviceStartAggregation) {
        this.serviceStartAggregation = serviceStartAggregation;
    }

}
