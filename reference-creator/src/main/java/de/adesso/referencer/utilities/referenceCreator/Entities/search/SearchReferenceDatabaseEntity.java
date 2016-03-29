/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Entities.search;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class SearchReferenceDatabaseEntity {

    private List<AdessoRole> allAdessoRoles = new ArrayList<>();
    private List<Branch> allBranches = new ArrayList<>();
    private List<Characteristic> allCharacteristics = new ArrayList<>();
    private List<Focus> allFocuses = new ArrayList<>();
    private List<InvolvedRole> allInvolvedRoles = new ArrayList<>();
    private List<Lob> allLobs = new ArrayList<>();
    private List<ProjectRole> allProjectRoles = new ArrayList<>();
    private List<Reference> allReferences = new ArrayList<>();
    private List<Technology> allTechnologies = new ArrayList<>();
    private List<User> allUsers = new ArrayList<>();

    public List<AdessoRole> getAllAdessoRoles() {
        return allAdessoRoles;
    }

    public void setAllAdessoRoles(List<AdessoRole> allAdessoRoles) {
        this.allAdessoRoles = allAdessoRoles;
    }

    public List<Branch> getAllBranches() {
        return allBranches;
    }

    public void setAllBranches(List<Branch> allBranches) {
        this.allBranches = allBranches;
    }

    public List<Characteristic> getAllCharacteristics() {
        return allCharacteristics;
    }

    public void setAllCharacteristics(List<Characteristic> allCharacteristics) {
        this.allCharacteristics = allCharacteristics;
    }

    public List<Focus> getAllFocuses() {
        return allFocuses;
    }

    public void setAllFocuses(List<Focus> allFocuses) {
        this.allFocuses = allFocuses;
    }

    public List<InvolvedRole> getAllInvolvedRoles() {
        return allInvolvedRoles;
    }

    public void setAllInvolvedRoles(List<InvolvedRole> allInvolvedRoles) {
        this.allInvolvedRoles = allInvolvedRoles;
    }

    public List<Lob> getAllLobs() {
        return allLobs;
    }

    public void setAllLobs(List<Lob> allLobs) {
        this.allLobs = allLobs;
    }

    public List<ProjectRole> getAllProjectRoles() {
        return allProjectRoles;
    }

    public void setAllProjectRoles(List<ProjectRole> allProjectRoles) {
        this.allProjectRoles = allProjectRoles;
    }

    public List<Reference> getAllReferences() {
        return allReferences;
    }

    public void setAllReferences(List<Reference> allReferences) {
        this.allReferences = allReferences;
    }

    public List<Technology> getAllTechnologies() {
        return allTechnologies;
    }

    public void setAllTechnologies(List<Technology> allTechnologies) {
        this.allTechnologies = allTechnologies;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
    }

}
