/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.rest.dto;

import de.adesso.referencer.search.elastic.entities.*;
import de.adesso.referencer.search.helper.MyHelpMethods;
import java.util.Date;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class LimitedReference {

    private String id;
    private String clientname;
    private String projectname;
    private Lob lob;
    private List<Branch> branches;
    private List<Technology> technologies;
    private Date projectStart;
    private String projectStartString;
    private Date projectEnd;
    private String projectEndString;
    private Date serviceStart;
    private Boolean serviceContract;
    private String serviceStartString;
    private Date serviceEnd;
    private String serviceEndString;
    private int ptAdesso;
    private double costAdesso;
    private Reference.ReleaseStatus releaseStatus;
    private Date documentCreationTime;
    private Date releaseDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public Lob getLob() {
        return lob;
    }

    public void setLob(Lob lob) {
        this.lob = lob;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    public Date getProjectStart() {
        return projectStart;
    }

    public void setProjectStart(Date projectStart) {
        this.projectStart = projectStart;
        this.projectStartString = MyHelpMethods.dateToSearchString(projectStart);
    }

    public Date getProjectEnd() {
        return projectEnd;
    }

    public void setProjectEnd(Date projectEnd) {
        this.projectEnd = projectEnd;
        this.projectEndString = MyHelpMethods.dateToSearchString(projectEnd);
    }

    public Date getServiceStart() {
        return serviceStart;
    }

    public void setServiceStart(Date serviceStart) {
        this.serviceStart = serviceStart;
        this.serviceStartString = MyHelpMethods.dateToSearchString(serviceStart);
    }

    public Date getServiceEnd() {
        return serviceEnd;
    }

    public void setServiceEnd(Date serviceEnd) {
        this.serviceEnd = serviceEnd;
        this.serviceEndString = MyHelpMethods.dateToSearchString(serviceEnd);
    }

    public int getPtAdesso() {
        return ptAdesso;
    }

    public void setPtAdesso(int ptAdesso) {
        this.ptAdesso = ptAdesso;
    }

    public double getCostAdesso() {
        return costAdesso;
    }

    public void setCostAdesso(double costAdesso) {
        this.costAdesso = costAdesso;
    }

    public Reference.ReleaseStatus getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(Reference.ReleaseStatus releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getProjectStartString() {
        return projectStartString;
    }

    public String getProjectEndString() {
        return projectEndString;
    }

    public String getServiceStartString() {
        return serviceStartString;
    }

    public String getServiceEndString() {
        return serviceEndString;
    }

    public Boolean getServiceContract() {
        return serviceContract;
    }

    public void setServiceContract(Boolean serviceContract) {
        this.serviceContract = serviceContract;
    }

    public Date getDocumentCreationTime() {
        return documentCreationTime;
    }

    public void setDocumentCreationTime(Date documentCreationTime) {
        this.documentCreationTime = documentCreationTime;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

}
