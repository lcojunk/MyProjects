/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Topic;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class AllTopic {
    private static final int ID_LENGTH=16;
    private static AllTopic instance;
    private List <Topic> entityList;
    
    private AllTopic() {
        entityList=new ArrayList<Topic>();
        addEntity(createDefaultEntity());
    }
    
    public List <Topic> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Topic> entityList) {
        this.entityList = entityList;
    }
    
    private Topic createDefaultEntity(){
        Topic entity = new Topic("Test Topic","Automatic created Topic");
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        return entity;
    }
    
    public static AllTopic getInstance(){
        if (instance == null)
        {instance = new AllTopic();}
        return instance;
    }
    
    public String addEntity(Topic entity) {
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        if(entityList.add(entity)) return entity.getId();
        else return null;
    }
    
    public Topic getEntityById(String id) {
        if (id==null) return null;
        for (Topic entity:entityList) 
            if(entity.getId()!=null)
                if (entity.getId().compareTo(id)==0) return entity;
        return null;
    }
    
    public boolean updateEntity(Topic entity) {
        if (entity==null) return false;
        if (entity.getId()==null) return false;
        Topic currentEntity;
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
        Topic currentEntity;
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
        
    public boolean deleteEntity(Topic entity){
        if (entity==null) return false;
        return deleteEntityById(entity.getId());
    }  
    

}
