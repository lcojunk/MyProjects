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
public class RestWordSuggestion {

    private String text;
    private Float score;
    private Object payload;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

}
