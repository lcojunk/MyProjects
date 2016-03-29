/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;


import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Feature;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class AllEntity  {
    private static final int ID_LENGTH=16;
    private static AllEntity instance;
    private List <Feature> entityList;
    
    private AllEntity() {
        entityList=new ArrayList<Feature>();
        addEntity(createDefaultEntity());
    }
    

    public List <Feature> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Feature> entityList) {
        this.entityList = entityList;
    }
    
    private Feature createDefaultEntity(){
        Feature entity = new Feature("Test Feature","Automatic created Feature");
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        return entity;
    }
    
    public static AllEntity getInstance(){
        if (instance == null)
        {instance = new AllEntity();}
        return instance;
    }
    
    public String addEntity(Feature entity) {
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        if(entityList.add(entity)) return entity.getId();
        else return null;
    }
    
    public Feature getEntityById(String id) {
        if (id==null) return null;
        for (Feature entity:entityList) 
            if(entity.getId()!=null)
                if (entity.getId().compareTo(id)==0) return entity;
        return null;
    }
    
    public boolean updateEntity(Feature entity) {
        if (entity==null) return false;
        if (entity.getId()==null) return false;
        Feature currentEntity;
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
        Feature currentEntity;
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
        
    public boolean deleteEntity(Feature entity){
        if (entity==null) return false;
        return deleteEntityById(entity.getId());
    }  
    

}
