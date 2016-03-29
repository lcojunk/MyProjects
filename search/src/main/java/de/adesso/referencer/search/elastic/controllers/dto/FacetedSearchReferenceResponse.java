/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.controllers.dto;

import de.adesso.referencer.search.elastic.entities.*;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class FacetedSearchReferenceResponse {

    private List<Reference> references;
    private HashMap<String, Long> lobsStatistic, branchStatistic, techStatistic;
    private Integer[] ptStatistic;
    private Double[] costStatistic;
    private HashMap<String, Long> projectEndStatistic, serviceEndStatistic;
    private Integer totalPages;
    private Long totalObjects;

    public List<Reference> getReferences() {
        return references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }

    public HashMap<String, Long> getLobsStatistic() {
        return lobsStatistic;
    }

    public void setLobsStatistic(HashMap<String, Long> lobsStatistic) {
        this.lobsStatistic = lobsStatistic;
    }

    public HashMap<String, Long> getBranchStatistic() {
        return branchStatistic;
    }

    public void setBranchStatistic(HashMap<String, Long> branchStatistic) {
        this.branchStatistic = branchStatistic;
    }

    public HashMap<String, Long> getTechStatistic() {
        return techStatistic;
    }

    public void setTechStatistic(HashMap<String, Long> techStatistic) {
        this.techStatistic = techStatistic;
    }

    public Integer[] getPtStatistic() {
        return ptStatistic;
    }

    public void setPtStatistic(Integer[] ptStatistic) {
        this.ptStatistic = ptStatistic;
    }

    public Double[] getCostStatistic() {
        return costStatistic;
    }

    public void setCostStatistic(Double[] costStatistic) {
        this.costStatistic = costStatistic;
    }

    public HashMap<String, Long> getProjectEndStatistic() {
        return projectEndStatistic;
    }

    public void setProjectEndStatistic(HashMap<String, Long> projectEndStatistic) {
        this.projectEndStatistic = projectEndStatistic;
    }

    public HashMap<String, Long> getServiceEndStatistic() {
        return serviceEndStatistic;
    }

    public void setServiceEndStatistic(HashMap<String, Long> serviceEndStatistic) {
        this.serviceEndStatistic = serviceEndStatistic;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalObjects() {
        return totalObjects;
    }

    public void setTotalObjects(Long totalObjects) {
        this.totalObjects = totalObjects;
    }

}
