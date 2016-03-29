/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Entities.search;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;



/**
 *
 * @author odzhara-ongom
 */
@Document(indexName = ElasticSearchConfig.INDEX_NAME, type = "springdata_project_role")
public class ProjectRole {
    @Id
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)   
    private String id;
    
    @Field(type = FieldType.String)    
    private String name;
    private String description;
    
    @Field(type = FieldType.Boolean)    
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public ProjectRole() {
    }

    public ProjectRole(String id) {
        this.id = id;
    }

    public ProjectRole(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ProjectRole(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
