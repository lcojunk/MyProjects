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
public interface IndexFilterBuilder {

    public String getName();

    public IndexFilter getFilter();
}
