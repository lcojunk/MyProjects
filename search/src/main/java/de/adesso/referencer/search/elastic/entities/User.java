/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.entities;

import de.adesso.referencer.search.config.ElasticSearchConfig;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 *
 * @author odzhara-ongom
 */
@Document(indexName = ElasticSearchConfig.INDEX_NAME, type = "springdata_user")
public class User {

    public enum UserGroup {

        ADMIN,
        EDITOR,
        BASIC
    }

    @Id
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String id;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String username;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String pass;

    @Field(type = FieldType.String)
    private UserGroup group;

    @Field(type = FieldType.Nested)
    private AdessoRole role;

    @Field(type = FieldType.String)
    private String first_name;

    @Field(type = FieldType.String)
    private String last_name;

    @Field(type = FieldType.String, indexAnalyzer = ElasticSearchConfig.REFERENCE_INDEX_ANALYZER_NAME, searchAnalyzer = ElasticSearchConfig.REFERENCE_SEARCH_ANALYZER_NAME)
    private String tel;
    @Field(type = FieldType.String, indexAnalyzer = ElasticSearchConfig.EMAIL_ANALYZER_NAME, searchAnalyzer = ElasticSearchConfig.EMAIL_ANALYZER_NAME)
    //@Field(type = FieldType.String)
    private String email;

    @Field(type = FieldType.Boolean)
    private boolean active;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String ssid;

    public User() {
    }

    public User(String username, UserGroup group, AdessoRole role, String first_name, String last_name, String tel, String email, boolean active) {
        this.username = username;
        this.group = group;
        this.role = role;
        this.first_name = first_name;
        this.last_name = last_name;
        this.tel = tel;
        this.email = email;
        this.active = active;
    }

    public AdessoRole getRole() {
        return role;
    }

    public void setRole(AdessoRole role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public UserGroup getGroup() {
        return group;
    }

    public void setGroup(UserGroup group) {
        this.group = group;
    }

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

}
