/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.controllers.dto;

import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RangeFilterBuilder;

/**
 *
 * @author odzhara-ongom
 */
public class SearchReferenceFilters {

    private RangeFilterBuilder ptAdessoFilter;
    private RangeFilterBuilder costAdessoFilter;
    private FilterBuilder lobsFilter;
    private FilterBuilder branchesFilter;
    private FilterBuilder technologyFilter;
    private FilterBuilder freetextFilter;
    private BoolFilterBuilder projectEndFilter;
    private BoolFilterBuilder serviceEndFilter;
    private FilterBuilder serviceContractFilter;
    private QueryBuilder freeTextQuery;

    public RangeFilterBuilder getPtAdessoFilter() {
        return ptAdessoFilter;
    }

    public void setPtAdessoFilter(RangeFilterBuilder ptAdessoFilter) {
        this.ptAdessoFilter = ptAdessoFilter;
    }

    public RangeFilterBuilder getCostAdessoFilter() {
        return costAdessoFilter;
    }

    public void setCostAdessoFilter(RangeFilterBuilder costAdessoFilter) {
        this.costAdessoFilter = costAdessoFilter;
    }

    public FilterBuilder getLobsFilter() {
        return lobsFilter;
    }

    public void setLobsFilter(FilterBuilder lobsFilter) {
        this.lobsFilter = lobsFilter;
    }

    public FilterBuilder getBranchesFilter() {
        return branchesFilter;
    }

    public void setBranchesFilter(FilterBuilder branchesFilter) {
        this.branchesFilter = branchesFilter;
    }

    public FilterBuilder getTechnologyFilter() {
        return technologyFilter;
    }

    public void setTechnologyFilter(FilterBuilder technologyFilter) {
        this.technologyFilter = technologyFilter;
    }

    public BoolFilterBuilder getProjectEndFilter() {
        return projectEndFilter;
    }

    public void setProjectEndFilter(BoolFilterBuilder projectEndFilter) {
        this.projectEndFilter = projectEndFilter;
    }

    public BoolFilterBuilder getServiceEndFilter() {
        return serviceEndFilter;
    }

    public void setServiceEndFilter(BoolFilterBuilder serviceEndFilter) {
        this.serviceEndFilter = serviceEndFilter;
    }

    public FilterBuilder getFreetextFilter() {
        return freetextFilter;
    }

    public void setFreetextFilter(FilterBuilder freetextFilter) {
        this.freetextFilter = freetextFilter;
    }

    public FilterBuilder getServiceContractFilter() {
        return serviceContractFilter;
    }

    public void setServiceContractFilter(FilterBuilder serviceContractFilter) {
        this.serviceContractFilter = serviceContractFilter;
    }

    public QueryBuilder getFreeTextQuery() {
        return freeTextQuery;
    }

    public void setFreeTextQuery(QueryBuilder freeTextQuery) {
        this.freeTextQuery = freeTextQuery;
    }

}
