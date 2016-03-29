/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ReferenceEntity;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class AllReferenceEntity {
    private static final int ID_LENGTH=16;
    private static AllReferenceEntity instance;
    private List <ReferenceEntity> entityList;
    
    private AllReferenceEntity() {
        entityList=new ArrayList<ReferenceEntity>();        
    }
    
    public List <ReferenceEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<ReferenceEntity> entityList) {
        this.entityList = entityList;
    }
    
    private ReferenceEntity createDefaultEntity(){
        ReferenceEntity entity = new ReferenceEntity();
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        return entity;
    }
    
    public static AllReferenceEntity getInstance(){
        if (instance == null)
        {instance = new AllReferenceEntity();}
        return instance;
    }
    
    public String addEntity(ReferenceEntity entity) {
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        if(entityList.add(entity)) return entity.getId();
        else return null;
    }
    
    public ReferenceEntity getEntityById(String id) {
        if (id==null) return null;
        for (ReferenceEntity entity:entityList) 
            if(entity.getId()!=null)
                if (entity.getId().compareTo(id)==0) return entity;
        return null;
    }
    
    public boolean updateEntity(ReferenceEntity entity) {
        if (entity==null) return false;
        if (entity.getId()==null) return false;
        ReferenceEntity currentEntity;
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
        ReferenceEntity currentEntity;
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
        
    public boolean deleteEntity(ReferenceEntity entity){
        if (entity==null) return false;
        return deleteEntityById(entity.getId());
    }  
    

}
