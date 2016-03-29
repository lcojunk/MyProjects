/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Mapping.Types;

import java.util.HashMap;

/**
 *
 * @author odzhara-ongom
 */
public class ENestedType extends EObjectType {
    
    public boolean include_in_parent;
    //public boolean include_in_root;
    
    
    public ENestedType (){
        type="nested";
        properties=  new HashMap<String, EFieldType>();
    }
    
}
