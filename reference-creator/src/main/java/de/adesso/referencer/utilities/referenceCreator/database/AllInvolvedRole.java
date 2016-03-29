/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.InvolvedRole;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class AllInvolvedRole {
    private static final int ID_LENGTH=16;
    private static AllInvolvedRole instance;
    private List <InvolvedRole> entityList;
    
    private AllInvolvedRole() {
        entityList=new ArrayList<InvolvedRole>();
        addEntity(createDefaultEntity());
    }
    
    public List <InvolvedRole> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<InvolvedRole> entityList) {
        this.entityList = entityList;
    }
    
    private InvolvedRole createDefaultEntity(){
        InvolvedRole entity = new InvolvedRole("Test InvolvedRole","Automatic created InvolvedRole");
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        return entity;
    }
    
    public static AllInvolvedRole getInstance(){
        if (instance == null)
        {instance = new AllInvolvedRole();}
        return instance;
    }
    
    public String addEntity(InvolvedRole entity) {
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        if(entityList.add(entity)) return entity.getId();
        else return null;
    }
    
    public InvolvedRole getEntityById(String id) {
        for (InvolvedRole entity:entityList) 
            if(entity.getId()!=null)
                if (entity.getId().compareTo(id)==0) return entity;
        return null;
    }
    
    public boolean updateEntity(InvolvedRole entity) {
        if (entity==null) return false;
        if (entity.getId()==null) return false;
        InvolvedRole currentEntity;
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
        InvolvedRole currentEntity;
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
        
    public boolean deleteEntity(InvolvedRole entity){
        if (entity==null) return false;
        return deleteEntityById(entity.getId());
    }  
    

}
