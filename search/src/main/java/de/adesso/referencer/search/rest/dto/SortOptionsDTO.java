/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.rest.dto;

/**
 *
 * @author odzhara-ongom
 */
public class SortOptionsDTO {

    public enum SortOption {

        RELEVANCE,
        PROJECTNAME,
        CLIENTNAME,
        PT,
        VOLUMES,
        LOB,
        BRANCH,
        TECHNOLOGY,
        PROJECTSTART,
        PROJECTEND,
        STATUS,
        DOCUMENT_CREATION_TIME,
        RELEASED_TIME
    }

    private SortOption type;
    private Boolean descending;

    public SortOption getType() {
        return type;
    }

    public void setType(SortOption type) {
        this.type = type;
    }

    public Boolean getDescending() {
        return descending;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

}
