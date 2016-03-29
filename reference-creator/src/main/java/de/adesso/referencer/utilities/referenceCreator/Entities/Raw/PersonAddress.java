/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Entities.Raw;

import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;

/**
 *
 * @author odzhara-ongom
 */
public class PersonAddress {
    private String type="standart";
    private String plz="";
    private String country="";
    private String city="";
    private String street="";
    private String description="";

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    
    private String ps(String s){
        return MyHelpMethods.printString(s);
    }
    
    public String printPerson(String art) {
        String result=ps(type)+": "+", "+ps(street)+", "+ps(plz)+" "+ps(city)+", "+ps(country)+"."+ps(description);
        return result;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the plz
     */
    public String getPlz() {
        return plz;
    }

    /**
     * @param plz the plz to set
     */
    public void setPlz(String plz) {
        this.plz = plz;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
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
}
