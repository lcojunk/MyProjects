/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.services;

import de.adesso.referencer.search.elastic.entities.*;
import de.adesso.referencer.search.elastic.repositories.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class FocusService {
    @Autowired
    private FocusRepository repository;
    
    public List<Focus> getEntityByName(String name) {
        return repository.findByName(name);
    }

    public Focus addEntity(Focus entity) {
        return repository.save(entity);
    }        
        
    public Focus getEntity(String id) {
        return repository.findOne(id);
    }
    
    public List<Focus> getAll(){
        Iterable<Focus> itr=repository.findAll();
        List <Focus> entityList=new ArrayList<>();
        Focus entity=null;
        for (Iterator <Focus> it=itr.iterator(); it.hasNext();){
            entity=it.next();
            if (entity!=null) entityList.add(entity);
        }    
        return entityList;           
    }    
    
}
