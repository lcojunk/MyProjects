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
public class IndexCustomSettings {

    private IndexSettingsParameters index;

    public IndexSettingsParameters getIndex() {
        return index;
    }

    public void setIndex(IndexSettingsParameters index) {
        this.index = index;
    }

    public IndexCustomSettings() {
    }

    public IndexCustomSettings(IndexSettingsParameters index) {
        this.index = index;
    }

}
