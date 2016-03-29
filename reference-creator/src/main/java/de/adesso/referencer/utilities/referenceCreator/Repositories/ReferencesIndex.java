/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Repositories;

import de.adesso.referencer.utilities.referenceCreator.ElasticConfig;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.*;
import de.adesso.referencer.utilities.referenceCreator.Mapping.Types.*;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import de.adesso.referencer.utilities.referenceCreator.Repositories.Mapping.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author odzhara-ongom
 */
public class ReferencesIndex {
    private static String indexName="references_v2";
    private static HashMap<Class,String> mappingDescription=createMappingDescription();
    
//    public static String typeName="elastic_user";  
//    private static final String POST_STRING=ElasticConfig.getURL(indexName, typeName);
//    private static final String GET_STRING=POST_STRING+"/";
//    private static final String PUT_STRING=GET_STRING;
//    private static final String SEARCH_STRING=POST_STRING+"/_search";
//    private static final String DEL_STRING=GET_STRING;
//    private static final String GET_COUNT_STRING=POST_STRING+"/_count";

    
    private static final long MAX_CONTENT_LENGTH=ElasticConfig.getMAX_CONTENT_LENGTH();
    private static final String DEFAULT_CHARSET=ElasticConfig.getDEFAULT_CHARSET();
    private static Gson gson=ElasticConfig.getGson();
    
    public static HashMap<Class,String> createMappingDescription(){
        HashMap<Class,String> result = new HashMap<Class,String>();
        result.put((new AdessoRole()).getClass(), "adesso_role");
        result.put((new Branch()).getClass(), "branch");
        result.put((new ElasticUser()).getClass(), "el_user");
        result.put((new Extras()).getClass(), "extras");
        result.put((new Feature()).getClass(), "feature");
        result.put((new InvolvedRole()).getClass(), "involved_role");
        result.put((new LOB()).getClass(), "lob");
        result.put((new Person()).getClass(), "person");
        result.put((new ProjectRole()).getClass(), "project_role");
        result.put((new ReferenceEntity()).getClass(), "reference_entity");
        result.put((new Technology()).getClass(), "technology");
        result.put((new Topic()).getClass(), "topic");
        return result;
    }
    
    public static String getMappingDescription (Class c){
        if (mappingDescription==null) return null;
        return mappingDescription.get(c);
    }
    
    public static HashMap<String,EFieldType> createIndexMapping(){
        String typename;
        EFieldType mapping;
        Class c;
        HashMap<String,EFieldType> hm = new HashMap<String,EFieldType>();
        c=(new AdessoRole()).getClass();
        hm.put(getMappingDescription(c),IdNameDescriptionMapping.createTypeMapping(c));
        c=(new Branch()).getClass();
        hm.put(getMappingDescription(c),IdNameDescriptionMapping.createTypeMapping(c));
        c=(new ElasticUser()).getClass();
        hm.put(getMappingDescription(c),ElasticUserMapping.createTypeMapping(c));
        c=(new Extras()).getClass();
        hm.put(getMappingDescription(c),IdNameDescriptionMapping.createTypeMapping(c));
        c=(new Feature()).getClass();
        hm.put(getMappingDescription(c),IdNameDescriptionMapping.createTypeMapping(c));
        c=(new InvolvedRole()).getClass();
        hm.put(getMappingDescription(c),IdNameDescriptionMapping.createTypeMapping(c));
        c=(new LOB()).getClass();
        hm.put(getMappingDescription(c),IdNameDescriptionMapping.createTypeMapping(c));
        c=(new Person()).getClass();
        hm.put(getMappingDescription(c),PersonMapping.createTypeMapping(c));
        c=(new ProjectRole()).getClass();
        hm.put(getMappingDescription(c),IdNameDescriptionMapping.createTypeMapping(c));
        c=(new ReferenceEntity()).getClass();
        hm.put(getMappingDescription(c),ReferenceMapping.createTypeMapping(c));        
        c=(new Technology()).getClass();
        hm.put(getMappingDescription(c),IdNameDescriptionMapping.createTypeMapping(c));
        c=(new Topic()).getClass();
        hm.put(getMappingDescription(c),IdNameDescriptionMapping.createTypeMapping(c));
        return hm;
    }    
    public static String makeMappingString(HashMap <String,EFieldType> mapping) {
        String result="{\n" +
                    "  \"mappings\":";
        result+=MyHelpMethods.object2PrettyString(mapping)+"\n";
        result+="}";
        return result;
    }
    
    public static String createIndex(){
        
        String url=ElasticConfig.getURL(indexName);
        String requestBody=makeMappingString(createIndexMapping());        
        String result;
        try {
            result = ElasticConfig.sendHttpRequest(url, requestBody, "Put");
        } catch (IOException ex) {
            result=ex.getMessage();
        }
        return result;  
    }
    
    public static String deleteAndCreateIndex() {
        String result=ElasticConfig.delIndex(indexName);
        result+="\n"+createIndex();
        return result;
    }    

    public static String getIndexName() {
        return indexName;
    }

    public static HashMap<Class, String> getMappingDescription() {
        return mappingDescription;
    }
    
    
    
}
