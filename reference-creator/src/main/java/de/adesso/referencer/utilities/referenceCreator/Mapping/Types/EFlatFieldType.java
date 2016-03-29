/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Mapping.Types;

/**
 *
 * @author odzhara-ongom
 */
public class EFlatFieldType extends EFieldType{   
    public static enum Analyzed {analyzed, not_analyzed, no}
    
    public boolean store = false;        
    public Analyzed index=null;    
    public double boost=1.0;    
    public EFlatFieldType(){
        type="FlatFieldType";
    }
}
