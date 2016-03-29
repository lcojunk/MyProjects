/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Branch;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class AllBranches {
    private static final int ID_LENGTH=16;
    private static AllBranches instance;
    private List <Branch> entityList;
    
    private AllBranches() {
        entityList=new ArrayList<Branch>();
        addEntity(createDefaultEntity());
    }
    
    public List <Branch> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Branch> entityList) {
        this.entityList = entityList;
    }
    
    private Branch createDefaultEntity(){
        Branch entity = new Branch("Test Branch","Automatic created Branch");
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        return entity;
    }
    
    public static AllBranches getInstance(){
        if (instance == null)
        {instance = new AllBranches();}
        return instance;
    }
    
    public String addEntity(Branch entity) {
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        if(entityList.add(entity)) return entity.getId();
        else return null;
    }
    
    public Branch getEntityById(String id) {
        if (id==null) return null;
        for (Branch entity:entityList) 
            if(entity.getId()!=null)
                if (entity.getId().compareTo(id)==0) return entity;
        return null;
    }
    
    public boolean updateEntity(Branch entity) {
        if (entity==null) return false;
        if (entity.getId()==null) return false;
        Branch currentEntity;
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
        Branch currentEntity;
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
        
    public boolean deleteEntity(Branch entity){
        if (entity==null) return false;
        return deleteEntityById(entity.getId());
    }  
    

}
