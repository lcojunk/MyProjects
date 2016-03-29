/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Repositories.Mapping;

import de.adesso.referencer.utilities.referenceCreator.Mapping.ElasticMapping;
import de.adesso.referencer.utilities.referenceCreator.Mapping.Types.*;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 *
 * @author odzhara-ongom
 */
public class PersonMapping extends ElasticMapping {
     public static String typeName="person";  
    
    public static EFieldType makeEFieldType(Class c, Field f) {
        EFieldType fieldMapping=null;
        
        String fieldname=f.getName();
        if (fieldname.matches("address")) {
            Class c1 = MyHelpMethods.getClassFromListOfClasses(f);
            EObjectType type = new EObjectType();
            type.properties=PersonAddressMapping.mapFieldsFromClass(c1);
            return type;
        } else if (fieldname.matches("email")|| fieldname.matches("tel")
                 ||fieldname.matches("id")||    fieldname.matches("role")
                 ||fieldname.matches("name")||  fieldname.matches("forename")
                 ||fieldname.matches("titel")) {
            EStringType type = new EStringType();
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
    
    public static EFieldType createTypeMapping(Class c){
        EObjectType typeMapping= new EObjectType();
        typeMapping.type=null;
        typeMapping.properties=mapFieldsFromClass(c);
        return typeMapping;        
    }   
}
