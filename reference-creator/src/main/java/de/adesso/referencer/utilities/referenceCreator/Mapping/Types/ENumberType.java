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
public class ENumberType extends EFlatFieldType{
    
    public static enum NType {Float, Double, Integer, Long, Short, Byte}
    
    public ENumberType(){
        type="double";
    }
    
    public ENumberType(NType ntype) {
//        type="double";
        switch (ntype) {
            case Float:     type="float";
                            break;
            case Double:    type="double";
                            break;
            case Integer:   type="integer";
                            break;
            case Long:      type="long";
                            break;
            case Short:     type="short";
                            break;
            case Byte:      type="byte";
                            break;
            default:        type="double";
        }
    }
    
}
