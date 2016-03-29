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
public class TechnologyService {
    @Autowired
    private TechnologyRepository repository;
    
    public List<Technology> getEntityByName(String name) {
        return repository.findByName(name);
    }

    public Technology addEntity(Technology entity) {
        return repository.save(entity);
    }        
        
    public Technology getEntity(String id) {
        return repository.findOne(id);
    }
    
    public List<Technology> getAll(){
        Iterable<Technology> itr=repository.findAll();
        List <Technology> entityList=new ArrayList<>();
        Technology entity=null;
        for (Iterator <Technology> it=itr.iterator(); it.hasNext();){
            entity=it.next();
            if (entity!=null) entityList.add(entity);
        }    
        return entityList;           
    }    
    
}
