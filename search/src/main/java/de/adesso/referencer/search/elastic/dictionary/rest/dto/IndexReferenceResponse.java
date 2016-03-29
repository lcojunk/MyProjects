/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.dictionary.rest.dto;

/**
 *
 * @author odzhara-ongom
 */
public class IndexReferenceResponse {

    private String id;
    private Long timeMillis;
    private Integer count;

    public IndexReferenceResponse() {
    }

    public IndexReferenceResponse(String id, Long timeMillis, Integer count) {
        this.id = id;
        this.timeMillis = timeMillis;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(Long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
