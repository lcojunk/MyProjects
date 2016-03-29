/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.controllers.dto;

import java.util.Date;
import java.util.List;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.data.domain.PageRequest;

/**
 *
 * @author odzhara-ongom
 */
public class FacetedSearchReferenceRequest {

    private List<String> freeText;
    private Integer[] ptSpan;
    private Double[] costSpan;
    private List<String> lobsId;
    private List<String> branchesId;
    private List<String> technologyId;
    private Date[] projectEndSpan, serviceEndSpan;
    private PageRequest page = new PageRequest(0, Integer.MAX_VALUE);
    private SortBuilder sort;
    private Boolean serviceContract;

    public void setPtMin(Integer ptMin) {
        if (ptSpan == null) {
            ptSpan = new Integer[2];
        }
        ptSpan[0] = ptMin;
    }

    public Integer getPtMin() {
        if (ptSpan == null) {
            return null;
        }
        return ptSpan[0];
    }

    public void setPtMax(Integer ptMax) {
        if (ptSpan == null) {
            ptSpan = new Integer[2];
        }
        ptSpan[1] = ptMax;
    }

    public Integer getPtMax() {
        if (ptSpan == null) {
            return null;
        }
        return ptSpan[1];
    }

    public void setCostMin(Double cost) {
        if (costSpan == null) {
            costSpan = new Double[2];
        }
        costSpan[0] = cost;
    }

    public Double getCostMin() {
        if (costSpan == null) {
            return null;
        }
        return costSpan[0];
    }

    public void setCostMax(Double cost) {
        if (costSpan == null) {
            costSpan = new Double[2];
        }
        costSpan[1] = cost;
    }

    public Double getCostMax() {
        if (costSpan == null) {
            return null;
        }
        return costSpan[1];
    }

    public Date getProjectEndMin() {
        if (projectEndSpan == null) {
            return null;
        }
        return projectEndSpan[0];
    }

    public Date getProjectEndMax() {
        if (projectEndSpan == null) {
            return null;
        }
        return projectEndSpan[1];
    }

    public Date getServiceEndMin() {
        if (serviceEndSpan == null) {
            return null;
        }
        return serviceEndSpan[0];
    }

    public Date getServiceEndMax() {
        if (serviceEndSpan == null) {
            return null;
        }
        return serviceEndSpan[1];
    }

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

    public List<String> getLobsId() {
        return lobsId;
    }

    public void setLobsId(List<String> lobsId) {
        this.lobsId = lobsId;
    }

    public List<String> getBranchesId() {
        return branchesId;
    }

    public void setBranchesId(List<String> branchesId) {
        this.branchesId = branchesId;
    }

    public List<String> getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(List<String> technologyId) {
        this.technologyId = technologyId;
    }

    public Date[] getProjectEndSpan() {
        return projectEndSpan;
    }

    public void setProjectEndSpan(Date[] projectEndSpan) {
        this.projectEndSpan = projectEndSpan;
    }

    public Date[] getServiceEndSpan() {
        return serviceEndSpan;
    }

    public void setServiceEndSpan(Date[] serviceEndSpan) {
        this.serviceEndSpan = serviceEndSpan;
    }

    public PageRequest getPage() {
        return page;
    }

    public void setPage(PageRequest page) {
        this.page = page;
    }

    public Boolean getServiceContract() {
        return serviceContract;
    }

    public void setServiceContract(Boolean serviceContract) {
        this.serviceContract = serviceContract;
    }

    public SortBuilder getSort() {
        return sort;
    }

    public void setSort(SortBuilder sort) {
        this.sort = sort;
    }

}
