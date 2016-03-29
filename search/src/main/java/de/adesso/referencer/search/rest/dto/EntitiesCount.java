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
public class EntitiesCount<T> {

    long count;
    T entity;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

}
