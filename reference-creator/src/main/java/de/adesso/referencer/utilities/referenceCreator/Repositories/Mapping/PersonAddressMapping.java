/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Repositories.Mapping;

//import de.adesso.referencer.utilities.referenceCreator.Repositories.ElasticUserRepository.*;
import de.adesso.referencer.utilities.referenceCreator.Mapping.ElasticMapping;
import de.adesso.referencer.utilities.referenceCreator.Mapping.Types.*;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 *
 * @author odzhara-ongom
 */
public class PersonAddressMapping extends ElasticMapping {
    
    
    public static EFieldType makeEFieldType(Class c, Field f) {
        EFieldType fieldMapping=null;
        
        String fieldname=f.getName();
        if (fieldname.matches("plz")||fieldname.matches("type")||
            fieldname.matches("country")||fieldname.matches("city")) {

            EStringType type= new EStringType();
            type.index=EFlatFieldType.Analyzed.not_analyzed;
            return type;
        }        
        fieldMapping=ElasticMapping.makeEFieldType(c, f);
        return fieldMapping;
    }
   
    public static HashMap<String,EFieldType> mapFieldsFromClass(Class c) {
        HashMap<String,EFieldType> hm = new HashMap<String,EFieldType>();
        Field [] fieldArray=(c).getDeclaredFields();
        String fieldName, fieldType; EFieldType fieldMapping;
        for (int i=0; i<fieldArray.length; i++){
            fieldName=fieldArray[i].getName();
            fieldType=fieldArray[i].getType().getName();
            fieldMapping=makeEFieldType(fieldArray[i].getType(),fieldArray[i]);
            if (fieldMapping!=null) hm.put(fieldName, fieldMapping);
        }
        return hm;        
    }
  
}
