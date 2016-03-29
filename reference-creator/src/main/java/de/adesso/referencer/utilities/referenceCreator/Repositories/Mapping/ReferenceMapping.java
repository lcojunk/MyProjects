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
public class ReferenceMapping extends ElasticMapping{
     public static String typeName="reference_entity";  
    
    public static EFieldType makeEFieldType(Class c, Field f) {
        EFieldType fieldMapping=null;        
        String fieldname=f.getName();
        if (fieldname.matches("changed")||fieldname.matches("released")) {
            EObjectType type = new EObjectType();
            type.properties=PersonMapping.mapFieldsFromClass(c);
            return type;
        } else if (fieldname.matches("id")||            fieldname.matches("lob")
                 ||fieldname.matches("branchList")||    fieldname.matches("refIDList")
                 ||fieldname.matches("roles")||fieldname.matches("technics")
                 ||fieldname.matches("topics")||fieldname.matches("extras")
                 ||fieldname.matches("adessoPartnerRole")||fieldname.matches("deputyName")
                 ||fieldname.matches("deputyRole")||fieldname.matches("deputyName")
                 ||fieldname.matches("objectVersionID")||fieldname.matches("classVersionID")
                ) {
            EStringType type = new EStringType();
            type.index=EFlatFieldType.Analyzed.not_analyzed;
            return type;
        } else if (fieldname.matches("owner")||fieldname.matches("editor")
                   ||fieldname.matches("approver")||    fieldname.matches("qAssurance")
                   ||fieldname.matches("clientData")) {
            EObjectType type = new EObjectType();
            type.properties=ElasticUserMapping.mapFieldsFromClass(c);
            return type;
        }else if (fieldname.matches("teammembers")||    fieldname.matches("adessoPartner")) {
            EObjectType type = new EObjectType();
            type.properties=PersonMapping.mapFieldsFromClass(c);
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
