/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Extras;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class AllExtras {
    private static final int ID_LENGTH=16;
    private static AllExtras instance;
    private List <Extras> entityList;
    
    private AllExtras() {
        entityList=new ArrayList<Extras>();
        addEntity(createDefaultEntity());
    }
    
    public List <Extras> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Extras> entityList) {
        this.entityList = entityList;
    }
    
    private Extras createDefaultEntity(){
        Extras entity = new Extras("Test Extras","Automatic created Extras");
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        return entity;
    }
    
    public static AllExtras getInstance(){
        if (instance == null)
        {instance = new AllExtras();}
        return instance;
    }
    
    public String addEntity(Extras entity) {
        if (entity==null) return null;
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        if(entityList.add(entity)) return entity.getId();
        else return null;
    }
    
    public Extras getEntityById(String id) {
        if (id==null) return null;
        for (Extras entity:entityList) 
            if(entity.getId()!=null)
                if (entity.getId().compareTo(id)==0) return entity;
        return null;
    }
    
    public boolean updateEntity(Extras entity) {
        if (entity==null) return false;
        if (entity.getId()==null) return false;
        Extras currentEntity;
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
        Extras currentEntity;
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
        
    public boolean deleteEntity(Extras entity){
        if (entity==null) return false;
        return deleteEntityById(entity.getId());
    }  
    

}
