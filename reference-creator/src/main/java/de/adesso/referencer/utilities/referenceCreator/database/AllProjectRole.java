/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ProjectRole;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class AllProjectRole {
    private static final int ID_LENGTH=16;
    private static AllProjectRole instance;
    private List <ProjectRole> entityList;
    
    private AllProjectRole() {
        entityList=new ArrayList<ProjectRole>();
        addEntity(createDefaultEntity());
    }
    
    public List <ProjectRole> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<ProjectRole> entityList) {
        this.entityList = entityList;
    }
    
    private ProjectRole createDefaultEntity(){
        ProjectRole entity = new ProjectRole("Test ProjectRole","Automatic created ProjectRole");
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        return entity;
    }
    
    public static AllProjectRole getInstance(){
        if (instance == null)
        {instance = new AllProjectRole();}
        return instance;
    }
    
    public String addEntity(ProjectRole entity) {
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        if(entityList.add(entity)) return entity.getId();
        else return null;
    }
    
    public ProjectRole getEntityById(String id) {
        if (id==null) return null;
        for (ProjectRole entity:entityList) 
            if(entity.getId()!=null)
                if (entity.getId().compareTo(id)==0) return entity;
        return null;
    }
    
    public boolean updateEntity(ProjectRole entity) {
        if (entity==null) return false;
        if (entity.getId()==null) return false;
        ProjectRole currentEntity;
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
        ProjectRole currentEntity;
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
        
    public boolean deleteEntity(ProjectRole entity){
        if (entity==null) return false;
        return deleteEntityById(entity.getId());
    }  
    

}
