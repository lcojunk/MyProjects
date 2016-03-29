/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.controllers.dto;

import de.adesso.referencer.search.elastic.entities.*;
import de.adesso.referencer.search.rest.dto.EntitiesCount;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class FacetedSearchReferenceResponseWithEntitiesCount {

    public enum ServiceContractFields {

        FIELD_EXISTS,
        FIELD_NOT_EXISTS
    }
    private List<Reference> references;
    private List<EntitiesCount<Lob>> lobsStatistic;
    private List<EntitiesCount<Branch>> branchStatistic;
    private List<EntitiesCount<Technology>> techStatistic;
    private Integer[] ptStatistic;
    private Double[] costStatistic;
    private HashMap<String, Long> projectEndStatistic, serviceEndStatistic;
    private HashMap<ServiceContractFields, Long> serviceContractStatistic;
    private Integer totalPages;
    private Long totalObjects;

    public List<Reference> getReferences() {
        return references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
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

    public List<EntitiesCount<Lob>> getLobsStatistic() {
        return lobsStatistic;
    }

    public void setLobsStatistic(List<EntitiesCount<Lob>> lobsStatistic) {
        this.lobsStatistic = lobsStatistic;
    }

    public List<EntitiesCount<Branch>> getBranchStatistic() {
        return branchStatistic;
    }

    public void setBranchStatistic(List<EntitiesCount<Branch>> branchStatistic) {
        this.branchStatistic = branchStatistic;
    }

    public List<EntitiesCount<Technology>> getTechStatistic() {
        return techStatistic;
    }

    public void setTechStatistic(List<EntitiesCount<Technology>> techStatistic) {
        this.techStatistic = techStatistic;
    }

    public HashMap<ServiceContractFields, Long> getServiceContractStatistic() {
        return serviceContractStatistic;
    }

    public void setServiceContractStatistic(HashMap<ServiceContractFields, Long> serviceContractStatistic) {
        this.serviceContractStatistic = serviceContractStatistic;
    }

}
