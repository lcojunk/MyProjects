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
public class InvolvedRole {
    private String id;
    private String name;
    private String description;

    public InvolvedRole() {
        id=MyHelpMethods.randomNumericString(16);
    }

    public InvolvedRole(String id) {
        this.id = id;
    }

    public InvolvedRole(String name, String description) {
        id=MyHelpMethods.randomNumericString(16);
        this.name = name;
        this.description = description;
    }

    public InvolvedRole(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
