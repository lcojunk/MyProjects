/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.services.dto;

/**
 *
 * @author odzhara-ongom
 */
public class IndexCharFilterBuilder {

    private static final String MAPPING_TYPE = "mapping";
    private static final String DEFAULT_MAPPINGS = ".=>. . ";
    private IndexCharFilter filter;
    private String name;

    public IndexCharFilterBuilder(IndexCharFilter filter, String name) {
        this.filter = filter;
        this.name = name;
    }

    private static IndexCharFilter createDefaultFilter() {
        return new IndexCharFilter();
    }

    private static IndexCharFilter createDefaultMappingFilter() {
        return new IndexCharFilter(MAPPING_TYPE, DEFAULT_MAPPINGS);
    }

    public static IndexCharFilterBuilder defaultMappingFilter(String name) {
        return new IndexCharFilterBuilder(createDefaultMappingFilter(), name);
    }

    public static IndexCharFilterBuilder mappingFilter(String name) {
        IndexCharFilter filter = new IndexCharFilter();
        filter.setType(MAPPING_TYPE);
        return new IndexCharFilterBuilder(filter, name);
    }

    public IndexCharFilterBuilder addMapping(String mapping) {
        if (this.filter == null) {
            filter = createDefaultFilter();
        }
        this.filter.addMapping(mapping);
        return this;
    }

    public IndexCharFilter getFilter() {
        return filter;
    }

    public String getName() {
        return name;
    }

}
