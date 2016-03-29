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
public class IndexSettingsParameters {

    private String number_of_shards;
    private String number_of_replicas;
    private IndexAnalysis analysis;

    public String getNumber_of_shards() {
        return number_of_shards;
    }

    public void setNumber_of_shards(String number_of_shards) {
        this.number_of_shards = number_of_shards;
    }

    public String getNumber_of_replicas() {
        return number_of_replicas;
    }

    public void setNumber_of_replicas(String number_of_replicas) {
        this.number_of_replicas = number_of_replicas;
    }

    public IndexAnalysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(IndexAnalysis analysis) {
        this.analysis = analysis;
    }

}
