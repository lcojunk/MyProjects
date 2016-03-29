/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

/**
 *
 * @author odzhara-ongom
 */
public class BooleanReply {
    private boolean exists;
    private String reason;

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String toString(int art){
        switch (art) {
            case 1: 
                return "{\n exists:"+exists+",\n reason: \""+reason+"\"\n}";
            default: 
                return toString();
        }
    }
}
