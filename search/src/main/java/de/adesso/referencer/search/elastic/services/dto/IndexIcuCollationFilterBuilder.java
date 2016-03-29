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
public class IndexIcuCollationFilterBuilder implements IndexFilterBuilder {

    private IndexIcuCollationFilter filter;
    private String name;

    private static final String FILTER_TYPE = "icu_collation";

    public static IndexIcuCollationFilterBuilder icuFilter(String name) {
        if (name == null) {
            return null;
        }
        IndexIcuCollationFilterBuilder result = new IndexIcuCollationFilterBuilder();
        result.setName(name);
        IndexIcuCollationFilter filter = new IndexIcuCollationFilter();
        filter.setType(FILTER_TYPE);
        result.setFilter(filter);
        return result;
    }

    public IndexIcuCollationFilterBuilder language(String s) {
        if (s == null) {
            return this;
        }
        if (filter == null) {
            filter = new IndexIcuCollationFilter();
            filter.setType(FILTER_TYPE);
        }
        filter.setLanguage(s);
        return this;
    }

    public IndexIcuCollationFilterBuilder country(String s) {
        if (s == null) {
            return this;
        }
        if (filter == null) {
            filter = new IndexIcuCollationFilter();
            filter.setType(FILTER_TYPE);
        }
        filter.setCountry(s);
        return this;
    }

    public IndexIcuCollationFilterBuilder variant(String s) {
        if (s == null) {
            return this;
        }
        if (filter == null) {
            filter = new IndexIcuCollationFilter();
            filter.setType(FILTER_TYPE);
        }
        filter.setVariant(s);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFilter(IndexIcuCollationFilter filter) {
        this.filter = filter;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IndexFilter getFilter() {
        return filter;
    }

}
