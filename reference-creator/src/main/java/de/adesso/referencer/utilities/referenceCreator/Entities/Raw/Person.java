/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Entities.Raw;

import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class Person {
    private String id=null;
    private String name="";
    private String forename="";
    private String titel="";
    private List <String> email=null;
    private List <String> tel=null;
    private List <PersonAddress> address=null;
    private String testimonal="";
    private String description="";
    private String role=null;

    public Person() {
    }

    public Person(String name, String description) {
        this.name=name;
        this.description=description;
    }

    public String addresses2Strings(){
        if (address==null) return null;
        String result="";
        for (PersonAddress a:address){
            result+=a.printPerson("DE")+"\n";
        }
        return result;
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the forename
     */
    public String getForename() {
        return forename;
    }

    /**
     * @param forename the forename to set
     */
    public void setForename(String forename) {
        this.forename = forename;
    }

    /**
     * @return the titel
     */
    public String getTitel() {
        return titel;
    }

    /**
     * @param titel the titel to set
     */
    public void setTitel(String titel) {
        this.titel = titel;
    }

    /**
     * @return the email
     */
    public List <String> getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(List <String> email) {
        this.email = email;
    }

    /**
     * @return the tel
     */
    public List <String> getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(List <String> tel) {
        this.tel = tel;
    }

    /**
     * @return the address
     */
    public List <PersonAddress> getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(List <PersonAddress> address) {
        this.address = address;
    }

    /**
     * @return the testimonal
     */
    public String getTestimonal() {
        return testimonal;
    }

    /**
     * @param testimonal the testimonal to set
     */
    public void setTestimonal(String testimonal) {
        this.testimonal = testimonal;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    
}
