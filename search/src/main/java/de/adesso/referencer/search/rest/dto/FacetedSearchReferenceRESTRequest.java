/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.rest.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class FacetedSearchReferenceRESTRequest {

    private List<String> freeText;
    private Integer[] ptSpan;
    private Double[] costSpan;
    private List<String> lobs;
    private List<String> branches;
    private List<String> technologies;
    private Integer projectEndOption, serviceEndOption;
    private Boolean serviceContract;
    private RestPageRequest page;
    private SortOptionsDTO sort;

    public List<String> getFreeText() {
        return freeText;
    }

    public void setFreeText(List<String> freeText) {
        this.freeText = freeText;
    }

    public Integer[] getPtSpan() {
        return ptSpan;
    }

    public void setPtSpan(Integer[] ptSpan) {
        this.ptSpan = ptSpan;
    }

    public Double[] getCostSpan() {
        return costSpan;
    }

    public void setCostSpan(Double[] costSpan) {
        this.costSpan = costSpan;
    }

    public List<String> getLobs() {
        return lobs;
    }

    public void setLobs(List<String> lobs) {
        this.lobs = lobs;
    }

    public List<String> getBranches() {
        return branches;
    }

    public void setBranches(List<String> branchesId) {
        this.branches = branchesId;
    }

    public List<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<String> technologies) {
        this.technologies = technologies;
    }

    public RestPageRequest getPage() {
        return page;
    }

    public void setPage(RestPageRequest page) {
        this.page = page;
    }

    public Integer getProjectEndOption() {
        return projectEndOption;
    }

    public void setProjectEndOption(Integer projectEndOption) {
        this.projectEndOption = projectEndOption;
    }

    public Integer getServiceEndOption() {
        return serviceEndOption;
    }

    public void setServiceEndOption(Integer serviceEndOption) {
        this.serviceEndOption = serviceEndOption;
    }

    public Boolean getServiceContract() {
        return serviceContract;
    }

    public void setServiceContract(Boolean serviceContract) {
        this.serviceContract = serviceContract;
    }

    public SortOptionsDTO getSort() {
        return sort;
    }

    public void setSort(SortOptionsDTO sort) {
        this.sort = sort;
    }

}
