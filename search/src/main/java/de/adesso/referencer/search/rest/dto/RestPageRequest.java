/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.rest.dto;

import de.adesso.referencer.search.config.ElasticSearchConfig;

/**
 *
 * @author odzhara-ongom
 */
public class RestPageRequest {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = ElasticSearchConfig.MAXIMUM_PAGE_SIZE;

    private Integer page = DEFAULT_PAGE_NUMBER;
    private Integer size = DEFAULT_PAGE_SIZE;

    public RestPageRequest() {
    }

    public RestPageRequest(Integer page, Integer size) {
        if (page == null || page < 0) {
            this.page = DEFAULT_PAGE_NUMBER;
        } else {
            this.page = page;
        }
        if (size == null || size < 0) {
            this.size = DEFAULT_PAGE_SIZE;
        } else {
            this.size = size;
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if (page == null || page < 0) {
            this.page = DEFAULT_PAGE_NUMBER;
        } else {
            this.page = page;
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(Integer size) {
        if (size == null || size < 0) {
            this.size = DEFAULT_PAGE_SIZE;
        } else {
            this.size = size;
        }
    }

}
