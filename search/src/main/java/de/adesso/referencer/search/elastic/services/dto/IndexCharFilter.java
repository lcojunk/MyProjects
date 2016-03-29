/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.services.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class IndexCharFilter {

    private String type;
    private List<String> mappings;

    public IndexCharFilter() {
    }

    public IndexCharFilter(String type, List<String> mapping) {
        this.type = type;
        this.mappings = mapping;
    }

    public IndexCharFilter(String type, String mappingString) {
        this.type = type;
        this.addMapping(mappingString);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getMappings() {
        return mappings;
    }

    public void setMappings(List<String> mappings) {
        this.mappings = mappings;
    }

    public void addMapping(String mapping) {
        if (this.mappings == null) {
            mappings = new ArrayList<>();
        }
        mappings.add(mapping);
    }

}
