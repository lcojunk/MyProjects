/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Controllers;

import de.adesso.referencer.utilities.referenceCreator.ElasticConfig;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.*;
import de.adesso.referencer.utilities.referenceCreator.Repositories.ReferencesIndex;
import de.adesso.referencer.utilities.referenceCreator.database.ReferenceJavaDatabase;

/**
 *
 * @author odzhara-ongom
 */
public class RefCreatorElasticController {
    public static String putAllAdessoRole(ReferenceJavaDatabase db){
        String requestBody="";
        for (AdessoRole entity:db.getAllAdessoRole().getEntityList()){
            requestBody+=ElasticConfig.createBulkBody("create",ReferencesIndex.getIndexName(), 
                    ReferencesIndex.getMappingDescription(entity.getClass()), entity.getId(), entity)+"\n";            
        }
        return ElasticConfig.sendBulkHttpRequest(requestBody);
    }
    public static String putAllBranch(ReferenceJavaDatabase db){
        String requestBody="";
        for (Branch entity:db.getAllBranches().getEntityList()){
            requestBody+=ElasticConfig.createBulkBody("create",ReferencesIndex.getIndexName(), 
                    ReferencesIndex.getMappingDescription(entity.getClass()), entity.getId(), entity)+"\n";            
        }
        return ElasticConfig.sendBulkHttpRequest(requestBody);
    }
    public static String putAllElasticUser(ReferenceJavaDatabase db){
        String requestBody="";
        for (ElasticUser entity:db.getAllElasticUser().getEntityList()){
            requestBody+=ElasticConfig.createBulkBody("create",ReferencesIndex.getIndexName(), 
                    ReferencesIndex.getMappingDescription(entity.getClass()), entity.getId(), entity)+"\n";            
        }
        return ElasticConfig.sendBulkHttpRequest(requestBody);
    }
    public static String putAllExtras(ReferenceJavaDatabase db){
        String requestBody="";
        for (Extras entity:db.getAllExtras().getEntityList()){
            requestBody+=ElasticConfig.createBulkBody("create",ReferencesIndex.getIndexName(), 
                    ReferencesIndex.getMappingDescription(entity.getClass()), entity.getId(), entity)+"\n";            
        }
        return ElasticConfig.sendBulkHttpRequest(requestBody);
    }
    public static String putAllFeature(ReferenceJavaDatabase db){
        String requestBody="";
        for (Feature entity:db.getAllFeature().getEntityList()){
            requestBody+=ElasticConfig.createBulkBody("create",ReferencesIndex.getIndexName(), 
                    ReferencesIndex.getMappingDescription(entity.getClass()), entity.getId(), entity)+"\n";            
        }
        return ElasticConfig.sendBulkHttpRequest(requestBody);
    }
    public static String putAllInvolvedRole(ReferenceJavaDatabase db){
        String requestBody="";
        for (InvolvedRole entity:db.getAllInvolvedRole().getEntityList()){
            requestBody+=ElasticConfig.createBulkBody("create",ReferencesIndex.getIndexName(), 
                    ReferencesIndex.getMappingDescription(entity.getClass()), entity.getId(), entity)+"\n";            
        }
        return ElasticConfig.sendBulkHttpRequest(requestBody);
    }
    public static String putAllLob(ReferenceJavaDatabase db){
        String requestBody="";
        for (LOB entity:db.getAllLobs().getEntityList()){
            requestBody+=ElasticConfig.createBulkBody("create",ReferencesIndex.getIndexName(), 
                    ReferencesIndex.getMappingDescription(entity.getClass()), entity.getId(), entity)+"\n";            
        }
        return ElasticConfig.sendBulkHttpRequest(requestBody);
    }
    public static String putAllPerson(ReferenceJavaDatabase db){
        String requestBody="";
        for (Person entity:db.getAllPerson().getEntityList()){
            requestBody+=ElasticConfig.createBulkBody("create",ReferencesIndex.getIndexName(), 
                    ReferencesIndex.getMappingDescription(entity.getClass()), entity.getId(), entity)+"\n";            
        }
        return ElasticConfig.sendBulkHttpRequest(requestBody);
    }
    public static String putAllProjectRole(ReferenceJavaDatabase db){
        String requestBody="";
        for (ProjectRole entity:db.getAllProjectRole().getEntityList()){
            requestBody+=ElasticConfig.createBulkBody("create",ReferencesIndex.getIndexName(), 
                    ReferencesIndex.getMappingDescription(entity.getClass()), entity.getId(), entity)+"\n";            
        }
        return ElasticConfig.sendBulkHttpRequest(requestBody);
    }
    public static String putAllReferenceEntity(ReferenceJavaDatabase db){
        String requestBody="";
        for (ReferenceEntity entity:db.getAllReferenceEntity().getEntityList()){
            requestBody+=ElasticConfig.createBulkBody("create",ReferencesIndex.getIndexName(), 
                    ReferencesIndex.getMappingDescription(entity.getClass()), entity.getId(), entity)+"\n";            
        }
        return ElasticConfig.sendBulkHttpRequest(requestBody);
    }
    public static String putAllTechnology(ReferenceJavaDatabase db){
        String requestBody="";
        for (Technology entity:db.getAllTechnology().getEntityList()){
            requestBody+=ElasticConfig.createBulkBody("create",ReferencesIndex.getIndexName(), 
                    ReferencesIndex.getMappingDescription(entity.getClass()), entity.getId(), entity)+"\n";            
        }
        return ElasticConfig.sendBulkHttpRequest(requestBody);
    }
    public static String putAllTopic(ReferenceJavaDatabase db){
        String requestBody="";
        for (Topic entity:db.getAllTopic().getEntityList()){
            requestBody+=ElasticConfig.createBulkBody("create",ReferencesIndex.getIndexName(), 
                    ReferencesIndex.getMappingDescription(entity.getClass()), entity.getId(), entity)+"\n";            
        }
        return ElasticConfig.sendBulkHttpRequest(requestBody);
    }
    public static void fillDatabase(ReferenceJavaDatabase db) {
        if (db==null) return;
        System.out.println("Recreating index...");
        System.out.println(ReferencesIndex.deleteAndCreateIndex());        
        System.out.println("Copying Data");
        System.out.println(putAllAdessoRole(db));
        System.out.println(putAllBranch(db));
        System.out.println(putAllElasticUser(db));
        System.out.println(putAllExtras(db));
        System.out.println(putAllFeature(db));
        System.out.println(putAllInvolvedRole(db));
        System.out.println(putAllLob(db));
        System.out.println(putAllPerson(db));
        System.out.println(putAllProjectRole(db));
        System.out.println(putAllReferenceEntity(db));
        System.out.println(putAllTechnology(db));
        System.out.println(putAllTopic(db));
    }    
}
