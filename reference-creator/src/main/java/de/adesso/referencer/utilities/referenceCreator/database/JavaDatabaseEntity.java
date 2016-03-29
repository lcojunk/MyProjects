/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.*;
import java.util.Date;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class JavaDatabaseEntity {
    private List <AdessoRole> adessoRoles;
    private List <Branch> branches;
    private List <ElasticUser> elasticUsers;
    private List <Feature> features;
    private List <InvolvedRole> involvedRoles;
    private List <LOB> lobs;
    private List <Person> persons;
    private List <ProjectRole> projectRoles;
    private List <ReferenceEntity> referenceEntities;
    private List <Technology> technologies;
    private List <Topic> topics;
    private List <Extras> extras;
    private String version;
    private Date versionDate;
    private Date created;
    
    
    
    public List<AdessoRole> getAdessoRoles() {
        return adessoRoles;
    }

    public void setAdessoRoles(List<AdessoRole> adessoRoles) {
        this.adessoRoles = adessoRoles;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public List<ElasticUser> getElasticUsers() {
        return elasticUsers;
    }

    public void setElasticUsers(List<ElasticUser> elasticUsers) {
        this.elasticUsers = elasticUsers;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public List<InvolvedRole> getInvolvedRoles() {
        return involvedRoles;
    }

    public void setInvolvedRoles(List<InvolvedRole> involvedRoles) {
        this.involvedRoles = involvedRoles;
    }

    public List<LOB> getLobs() {
        return lobs;
    }

    public void setLobs(List<LOB> lobs) {
        this.lobs = lobs;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<ProjectRole> getProjectRoles() {
        return projectRoles;
    }

    public void setProjectRoles(List<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }

    public List<ReferenceEntity> getReferenceEntities() {
        return referenceEntities;
    }

    public void setReferenceEntities(List<ReferenceEntity> referenceEntities) {
        this.referenceEntities = referenceEntities;
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getVersionDate() {
        return versionDate;
    }

    public void setVersionDate(Date versionDate) {
        this.versionDate = versionDate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<Extras> getExtras() {
        return extras;
    }

    public void setExtras(List<Extras> extras) {
        this.extras = extras;
    }
    
    
    
}
