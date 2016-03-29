/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ElasticUser;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class AllElasticUser {
    private static final int ID_LENGTH=16;
    private static AllElasticUser instance;
    private List <ElasticUser> entityList;
    private void sortEntitiesByName(){
        Collections.sort(entityList, new Comparator<ElasticUser>() {
            @Override public int compare(ElasticUser entity1, ElasticUser entity2) {
                return MyHelpMethods.stringCompare(entity1.getUsername(), entity2.getUsername()); // Ascending
            }        
        });
    }
    private void sortEntitiesById(){
        Collections.sort(entityList, new Comparator<ElasticUser>() {
            @Override public int compare(ElasticUser entity1, ElasticUser entity2) {
                return MyHelpMethods.stringCompare(entity1.getId(), entity2.getId()); // Ascending
            }        
        });
    }
        
    private AllElasticUser() {
        entityList=new ArrayList<ElasticUser>();
        //addEntity(createDefaultEntity());
    }
    
    public List <ElasticUser> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<ElasticUser> entityList) {
        this.entityList = entityList;
    }
    
    private ElasticUser createDefaultEntity(){
        //ElasticUser entity = new ElasticUser("Test ElasticUser","Automatic created ElasticUser");
        ElasticUser entity = new ElasticUser();
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        return entity;
    }
    
    public static AllElasticUser getInstance(){
        if (instance == null)
        {instance = new AllElasticUser();}
        return instance;
    }
    
    public String addEntity(ElasticUser entity) {
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        if(entityList.add(entity)) return entity.getId();
        else return null;
    }
    
    public ElasticUser getEntityById(String id) {
        if (id==null) return null;
        for (ElasticUser entity:entityList) 
            if(entity.getId()!=null)
                if (entity.getId().compareTo(id)==0) return entity;
        return null;
    }
    
    public boolean updateEntity(ElasticUser entity) {
        if (entity==null) return false;
        if (entity.getId()==null) return false;
        ElasticUser currentEntity;
        for (int i=0; i<entityList.size(); i++){ 
            currentEntity=entityList.get(i);
            if(currentEntity.getId()!=null)
                if (currentEntity.getId().compareTo(entity.getId())==0) {
                    entityList.set(i, entity);
                    return true;
                }
        }
        return false;
    }

    public boolean deleteEntityById(String id){        
        if (id==null) return false;
        ElasticUser currentEntity;
        for (int i=0; i<entityList.size(); i++){ 
            currentEntity=entityList.get(i);
            if(currentEntity.getId()!=null)
                if (currentEntity.getId().compareTo(id)==0) {
                    entityList.remove(i);
                    return true;
                }
        }
        return false;
    }
        
    public boolean deleteEntity(ElasticUser entity){
        if (entity==null) return false;
        return deleteEntityById(entity.getId());
    }  
    

}
