/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.LOB;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class AllLobs {
    private static AllLobs instance;
    private List <LOB> entityList;
    
    private AllLobs() {
        entityList=new ArrayList<LOB>();
        addEntity(createDefaultEntity());
    }
    
    private LOB createDefaultEntity(){
        return new LOB("Test LOB","New created Line Of Bussness");
    }

    public void setEntityList(List<LOB> entityList) {
        this.entityList = entityList;
    }
    
    public static AllLobs getInstance(){
        if (instance == null)
        {instance = new AllLobs();}
        return instance;
    }
    
    public String addEntity(LOB l) {
        l.setId(MyHelpMethods.randomNumericString(16));
        entityList.add(l);
        return l.getId();
    }
    
    public LOB getEntityById(String id) {
        LOB result=null;
        if (id==null) return null;
        for (LOB l:entityList) 
            if(l.getId()!=null)
                if (l.getId().compareTo(id)==0) return l;
        return result;
    }
    
    public boolean updateEntity(LOB lob) {
        if (lob==null) return false;
        if (lob.getId()==null) return false;
        LOB l=null;
        for (int i=0; i<entityList.size(); i++){ 
            l=entityList.get(i);
            if(l.getId()!=null)
                if (l.getId().compareTo(lob.getId())==0) {
                    entityList.set(i, lob);
                    return true;
                }
        }
        return false;
    }
    
    public boolean deleteEntity(LOB lob){
        if (lob==null) return false;
        if (lob.getId()==null) return false;
        LOB l=null;
        for (int i=0; i<entityList.size(); i++){ 
            l=entityList.get(i);
            if(l.getId()!=null)
                if (l.getId().compareTo(lob.getId())==0) {
                    entityList.remove(i);
                    return true;
                }
        }
        return false;
    }

    public boolean deleteEntityById(String id){        
        if (id==null) return false;
        return deleteEntity(new LOB(id));
    }
    
    
    public List <LOB> getEntityList() {
        return entityList;
    }
}
