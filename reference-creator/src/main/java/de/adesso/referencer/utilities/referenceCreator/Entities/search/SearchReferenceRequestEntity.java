/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Entities.search;

import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class SearchReferenceRequestEntity {
    private List<String> freetext;
    private int ptMin=-1;
    private int ptMax=-1;
    private double volMin=-1;
    private double volMax=-1;
    private List<String> lobs;
    private List<String> branches;
    private int duration=-1;

    public List<String> getFreetext() {
        return freetext;
    }

    public void setFreetext(List<String> freetext) {
        this.freetext = freetext;
    }

    public int getPtMin() {
        return ptMin;
    }

    public void setPtMin(int ptMin) {
        this.ptMin = ptMin;
    }

    public int getPtMax() {
        return ptMax;
    }

    public void setPtMax(int ptMax) {
        this.ptMax = ptMax;
    }

    public double getVolMin() {
        return volMin;
    }

    public void setVolMin(double volMin) {
        this.volMin = volMin;
    }

    public double getVolMax() {
        return volMax;
    }

    public void setVolMax(double volMax) {
        this.volMax = volMax;
    }

    public List<String> getLobs() {
        return lobs;
    }

    public void setLobs(List<String> lobs) {
        this.lobs = lobs;
    }

    public List<String> getBranches() {
        return branches;
    }

    public void setBranches(List<String> branches) {
        this.branches = branches;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
}
