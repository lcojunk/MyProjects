/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.controllers.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.PageRequest;

/**
 *
 * @author odzhara-ongom
 */
public class SearchReferenceRequest {

    private List<String> freeText;
    private String ptMin, ptMax;
    private String costMin, costMax;
    private List<String> lobsId;
    private List<String> branchesId;
    private List<String> technologyId;
    private Date projectEnd, serviceEnd;
    private Date[] projectEndSpan, serviceEndSpan;
    private PageRequest page;

    public List<String> getFreeText() {
        return freeText;
    }

    public void setFreeText(List<String> freeText) {
        this.freeText = freeText;
    }

    public String getPtMin() {
        return ptMin;
    }

    public void setPtMin(String ptMin) {
        this.ptMin = ptMin;
    }

    public String getPtMax() {
        return ptMax;
    }

    public void setPtMax(String ptMax) {
        this.ptMax = ptMax;
    }

    public String getCostMin() {
        return costMin;
    }

    public void setCostMin(String costMin) {
        this.costMin = costMin;
    }

    public String getCostMax() {
        return costMax;
    }

    public void setCostMax(String costMax) {
        this.costMax = costMax;
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

    public void setTechnologyId(String id) {
        if (id == null) {
            return;
        }
        this.technologyId = new ArrayList<>();
        this.technologyId.add(id);
    }

    public Date getProjectEnd() {
        return projectEnd;
    }

    public void setProjectEnd(Date projectEnd) {
        this.projectEnd = projectEnd;
    }

    public Date getServiceEnd() {
        return serviceEnd;
    }

    public void setServiceEnd(Date serviceEnd) {
        this.serviceEnd = serviceEnd;
    }

    public PageRequest getPage() {
        return page;
    }

    public void setPage(PageRequest page) {
        this.page = page;
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

    public void setProjectEndMin(Date date) {
        if (projectEndSpan == null) {
            projectEndSpan = new Date[2];
        }
        projectEndSpan[0] = date;
    }

    public void setProjectEndMax(Date date) {
        if (projectEndSpan == null) {
            projectEndSpan = new Date[2];
        }
        projectEndSpan[1] = date;
    }

    public void setServiceEndMin(Date date) {
        if (serviceEndSpan == null) {
            serviceEndSpan = new Date[2];
        }
        serviceEndSpan[0] = date;
    }

    public void setServiceEndMax(Date date) {
        if (serviceEndSpan == null) {
            serviceEndSpan = new Date[2];
        }
        serviceEndSpan[1] = date;
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

}
