/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.entities;

import de.adesso.referencer.search.config.ElasticSearchConfig;
import java.util.List;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 *
 * @author odzhara-ongom
 */
public class Contact {

    @Field(type = FieldType.String)
    private String first_name;
    @Field(type = FieldType.String)
    private String last_name;
    @Field(type = FieldType.String)
    private String titel;
    @Field(type = FieldType.String, indexAnalyzer = ElasticSearchConfig.CONTACT_INDEX_ANALYZER_NAME, searchAnalyzer = ElasticSearchConfig.CONTACT_SEARCH_ANALYZER_NAME)
    //@Field(type = FieldType.String)
    private String email;
    @Field(type = FieldType.String)
    private String tel;
    @Field(type = FieldType.String)
    private String address;
    @Deprecated
    @Field(type = FieldType.String)
    private String testimonal;  // TODO-leon: Feld für Informationen zu Testimonial hinzufügen (testimonalInfo)
    @Field(type = FieldType.String)
    private String description;
    @Field(type = FieldType.String)
    private String role;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Deprecated
    public String getTestimonal() {
        return testimonal;
    }

    @Deprecated
    public void setTestimonal(String testimonal) {
        this.testimonal = testimonal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
