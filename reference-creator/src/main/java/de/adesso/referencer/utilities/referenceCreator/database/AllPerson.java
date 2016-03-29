/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Person;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class AllPerson {
    private static final int ID_LENGTH=16;
    private static AllPerson instance;
    private List <Person> entityList;
    
    private void sortEntitiesByName(){
        Collections.sort(entityList, new Comparator<Person>() {
            @Override public int compare(Person entity1, Person entity2) {
                return MyHelpMethods.stringCompare(entity1.getName(), entity2.getName()); // Ascending
            }        
        });
    }
    private void sortEntitiesById(){
        Collections.sort(entityList, new Comparator<Person>() {
            @Override public int compare(Person entity1, Person entity2) {
                return MyHelpMethods.stringCompare(entity1.getId(), entity2.getId()); // Ascending
            }        
        });
    }    
    private AllPerson() {
        entityList=new ArrayList<Person>();
        addEntity(createDefaultEntity());
    }
    
    public List <Person> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Person> entityList) {
        this.entityList = entityList;
        sortEntitiesByName();
    }
    
    private Person createDefaultEntity(){
        Person entity = new Person("Test Person","Automatic created Person");
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        return entity;
    }
    
    public static AllPerson getInstance(){
        if (instance == null)
        {instance = new AllPerson();}
        return instance;
    }
    
    public String addEntity(Person entity) {
        if (entity==null) return null;
        entity.setId(MyHelpMethods.randomNumericString(ID_LENGTH));
        if(entityList.add(entity)){
            sortEntitiesByName();
            return entity.getId();
        }
        else return null;
    }
    
    public Person getEntityById(String id) {
        if (id==null) return null;
        for (Person entity:entityList) 
            if(entity.getId()!=null)
                if (entity.getId().compareTo(id)==0) return entity;
        return null;
    }
    
    public boolean updateEntity(Person entity) {
        if (entity==null) return false;
        if (entity.getId()==null) return false;
        Person currentEntity;
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
        Person currentEntity;
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
        
    public boolean deleteEntity(Person entity){
        if (entity==null) return false;
        return deleteEntityById(entity.getId());
    }  
    

}
