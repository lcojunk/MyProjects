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
public class CharacteristicService {
    @Autowired
    private CharacteristicRepository repository;
    
    public List<Characteristic> getEntityByName(String name) {
        return repository.findByName(name);
    }

    public Characteristic addEntity(Characteristic entity) {
        return repository.save(entity);
    }        
        
    public Characteristic getEntity(String id) {
        return repository.findOne(id);
    }
    
    public List<Characteristic> getAll(){
        Iterable<Characteristic> itr=repository.findAll();
        List <Characteristic> entityList=new ArrayList<>();
        Characteristic entity=null;
        for (Iterator <Characteristic> it=itr.iterator(); it.hasNext();){
            entity=it.next();
            if (entity!=null) entityList.add(entity);
        }    
        return entityList;           
    }    
    
}
