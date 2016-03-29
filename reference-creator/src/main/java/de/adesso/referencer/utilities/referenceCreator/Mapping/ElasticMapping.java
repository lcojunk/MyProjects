/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Mapping;

import de.adesso.referencer.utilities.referenceCreator.Mapping.Types.*;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 *
 * @author odzhara-ongom
 */
public class ElasticMapping {
    public static String ELASTIC_ENTITIES_PACKAGE = "Entities.Raw";

    public static EFieldType makeEFieldType(Class c, Field f) {
    EFieldType fieldMapping=null;
    String fieldType = c.getName();
    String key=null; EFieldType value=null;
    if (fieldType.contains("String")) fieldMapping=new EStringType();
    else if (fieldType.contains("int")) fieldMapping=new ENumberType(ENumberType.NType.Integer);
    else if (fieldType.contains("double"))fieldMapping=new ENumberType(ENumberType.NType.Double);
    else if (fieldType.contains("long")) fieldMapping=new ENumberType(ENumberType.NType.Long);
    else if (fieldType.contains("Date")) fieldMapping=new EDateType();
    else if (fieldType.contains("java.util")&&fieldType.contains("List")&&f!=null) {
        fieldMapping=makeEFieldType(MyHelpMethods.getClassFromListOfClasses(f), f);
    }
    else if (fieldType.contains(ELASTIC_ENTITIES_PACKAGE)) {
        System.out.println("My Fields found: "+fieldType);
        EObjectType objectMapping = new EObjectType();
        Field [] fieldArray=(c).getDeclaredFields();            
        for (int i=0; i<fieldArray.length; i++){
            key = fieldArray[i].getName();
            value=makeEFieldType(fieldArray[i].getType(),fieldArray[i]);
            if (key!=null && value!=null) objectMapping.properties.put(key, value);                
        }
        fieldMapping=objectMapping;
    }
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
    

    
//    public static String makeMappingString(Class c) {         
//        String result="{\"properties\" : ";
//        HashMap<String,EFieldType> hm=mapFieldsFromClass(c);
//        result+=MyHelpMethods.object2PrettyString(hm);        
//        result+="}";
//        return result;
//    }
}
