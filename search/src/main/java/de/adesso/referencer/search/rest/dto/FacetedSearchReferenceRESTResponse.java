/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.rest.dto;

import de.adesso.referencer.search.elastic.entities.*;
import de.adesso.referencer.search.helper.MyHelpMethods;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class FacetedSearchReferenceRESTResponse {

    public enum ServiceContractOptions {

        FIELD_EXISTS,
        FIELD_NOT_EXISTS
    }
    private List<LimitedReference> references;
    private List<Reference> fullReferences;
    private FacetedSearchReferenceRESTRequest request;

    private double costMin, costMax;

    private Date dateMin, dateMax, serviceDateMin, serviceDateMax;
    private String dateMinAsString, dateMaxAsString, serviceDateMinAsString, serviceDateMaxAsString;

    private int ptMin, ptMax;

    private List<EntitiesCount<Branch>> branchStatistic;
    private List<EntitiesCount<Lob>> lobStatistic;
    private List<EntitiesCount<Technology>> technologyStatistic;
    private List<Integer> projectEndOptions;
    private List<Integer> serviceEndOptions;
    private HashMap<ServiceContractOptions, Long> serviceContractStatistic;
    private Integer totalPages;
    private Long totalObjects;

    public List<LimitedReference> getReferences() {
        return references;
    }

    public void setReferences(List<LimitedReference> references) {
        this.references = references;
    }

    public List<Reference> getFullReferences() {
        return fullReferences;
    }

    public void setFullReferences(List<Reference> fullReferences) {
        this.fullReferences = fullReferences;
    }

    public double getCostMin() {
        return costMin;
    }

    public void setCostMin(double costMin) {
        this.costMin = costMin;
    }

    public double getCostMax() {
        return costMax;
    }

    public void setCostMax(double costMax) {
        this.costMax = costMax;
    }

    public Date getDateMin() {
        return dateMin;
    }

    public void setDateMin(Date dateMin) {
        this.dateMin = dateMin;
        this.dateMinAsString = MyHelpMethods.dateToSearchString(dateMin);
    }

    public Date getDateMax() {
        return dateMax;
    }

    public void setDateMax(Date dateMax) {
        this.dateMax = dateMax;
        this.dateMaxAsString = MyHelpMethods.dateToSearchString(dateMax);
    }

    public Date getServiceDateMin() {
        return serviceDateMin;
    }

    public void setServiceDateMin(Date serviceDateMin) {
        this.serviceDateMin = serviceDateMin;
        this.serviceDateMinAsString = MyHelpMethods.dateToSearchString(serviceDateMin);
    }

    public Date getServiceDateMax() {
        return serviceDateMax;
    }

    public void setServiceDateMax(Date serviceDateMax) {
        this.serviceDateMax = serviceDateMax;
        this.serviceDateMaxAsString = MyHelpMethods.dateToSearchString(serviceDateMax);
    }

    public int getPtMin() {
        return ptMin;
    }

    public void setPtMin(int ptMin) {
        this.ptMin = ptMin;
    }

    public int getPtMax() {
        return ptMax;
    }

    public void setPtMax(int ptMax) {
        this.ptMax = ptMax;
    }

    public List<EntitiesCount<Branch>> getBranchStatistic() {
        return branchStatistic;
    }

    public void setBranchStatistic(List<EntitiesCount<Branch>> branchStatistic) {
        this.branchStatistic = branchStatistic;
    }

    public List<EntitiesCount<Lob>> getLobStatistic() {
        return lobStatistic;
    }

    public void setLobStatistic(List<EntitiesCount<Lob>> lobStatistic) {
        this.lobStatistic = lobStatistic;
    }

    public List<EntitiesCount<Technology>> getTechnologyStatistic() {
        return technologyStatistic;
    }

    public void setTechnologyStatistic(List<EntitiesCount<Technology>> technologyStatistic) {
        this.technologyStatistic = technologyStatistic;
    }

    public List<Integer> getProjectEndOptions() {
        return projectEndOptions;
    }

    public void setProjectEndOptions(List<Integer> projectEndOptions) {
        this.projectEndOptions = projectEndOptions;
    }

    public List<Integer> getServiceEndOptions() {
        return serviceEndOptions;
    }

    public void setServiceEndOptions(List<Integer> serviceEndOptions) {
        this.serviceEndOptions = serviceEndOptions;
    }

    public String getDateMinAsString() {
        return dateMinAsString;
    }

    public String getDateMaxAsString() {
        return dateMaxAsString;
    }

    public String getServiceDateMinAsString() {
        return serviceDateMinAsString;
    }

    public String getServiceDateMaxAsString() {
        return serviceDateMaxAsString;
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

    public HashMap<ServiceContractOptions, Long> getServiceContractStatistic() {
        return serviceContractStatistic;
    }

    public void setServiceContractStatistic(HashMap<ServiceContractOptions, Long> serviceContractStatistic) {
        this.serviceContractStatistic = serviceContractStatistic;
    }

    public FacetedSearchReferenceRESTRequest getRequest() {
        return request;
    }

    public void setRequest(FacetedSearchReferenceRESTRequest request) {
        this.request = request;
    }

}
