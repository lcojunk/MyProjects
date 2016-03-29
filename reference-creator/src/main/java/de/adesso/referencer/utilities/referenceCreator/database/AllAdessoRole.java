/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.AdessoRole;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class AllAdessoRole {
    private static final int ID_LENGTH=16;
    private static AllAdessoRole instance;
    private List <AdessoRole> entityList;
    
    private AllAdessoRole() {
        entityList=new ArrayList<AdessoRole>();
        addEntity(createDefaultEntity());
    }
    
    public List <AdessoRole> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<AdessoRole> entityList) {
        this.entityList = entityList;
    }
    
    private AdessoRole createDefaultEntity(){
        AdessoRole entity = new AdessoRole("Test AdessoRole","Automatic created AdessoRole");
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        return entity;
    }
    
    public static AllAdessoRole getInstance(){
        if (instance == null)
        {instance = new AllAdessoRole();}
        return instance;
    }
    
    public String addEntity(AdessoRole entity) {
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        if(entityList.add(entity)) return entity.getId();
        else return null;
    }
    
    public AdessoRole getEntityById(String id) {
        if (id==null) return null;
        for (AdessoRole entity:entityList) 
            if(entity.getId()!=null)
                if (entity.getId().compareTo(id)==0) return entity;
        return null;
    }
    
    public boolean updateEntity(AdessoRole entity) {
        if (entity==null) return false;
        if (entity.getId()==null) return false;
        AdessoRole currentEntity;
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
        AdessoRole currentEntity;
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
        
    public boolean deleteEntity(AdessoRole entity){
        if (entity==null) return false;
        return deleteEntityById(entity.getId());
    }  
    

}
