/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;


import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Technology;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class AllTechnology {
    private static final int ID_LENGTH=16;
    private static AllTechnology instance;
    private List <Technology> entityList;
    
    
    private void sortEntitiesByName(){
        Collections.sort(entityList, new Comparator<Technology>() {
            @Override public int compare(Technology entity1, Technology entity2) {
                return MyHelpMethods.stringCompare(entity1.getName(), entity2.getName()); // Ascending
            }        
        });
    }
    private void sortEntitiesById(){
        Collections.sort(entityList, new Comparator<Technology>() {
            @Override public int compare(Technology entity1, Technology entity2) {
                return MyHelpMethods.stringCompare(entity1.getId(), entity2.getId()); // Ascending
            }        
        });
    }
    
    private AllTechnology() {
        entityList=new ArrayList<Technology>();
        addEntity(createDefaultEntity());
    }
    
    public List <Technology> getEntityList() {        
        return entityList;
    }

    public void setEntityList(List<Technology> entityList) {
        this.entityList = entityList;
        sortEntitiesByName();
    }
    
    private Technology createDefaultEntity(){
        Technology entity = new Technology("Test Technology","Automatic created Branch");
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        return entity;
    }
    
    public static AllTechnology getInstance(){
        if (instance == null)
        {instance = new AllTechnology();}
        return instance;
    }
    
    public String addEntity(Technology entity) {
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        if(entityList.add(entity)) {
            sortEntitiesByName();
            return entity.getId();
        }
        else return null;
    }
    
    public Technology getEntityById(String id) {
        if (id==null) return null;
        for (Technology entity:entityList) 
            if(entity.getId()!=null)
                if (entity.getId().compareTo(id)==0) return entity;
        return null;
    }
    
    public boolean updateEntity(Technology entity) {
        if (entity==null) return false;
        if (entity.getId()==null) return false;
        Technology currentEntity;
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
        Technology currentEntity;
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
        
    public boolean deleteEntity(Technology entity){
        if (entity==null) return false;
        return deleteEntityById(entity.getId());
    }  
    

}
