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
public class UserService {
    @Autowired
    private UserRepository repository;
    
    public List<User> getEntityByName(String name) {
        return repository.findByUsername(name);
    }

    public User addEntity(User entity) {
        return repository.save(entity);
    }        
        
    public User getEntity(String id) {
        return repository.findOne(id);
    }
    
    public List<User> getAll(){
        Iterable<User> itr=repository.findAll();
        List <User> entityList=new ArrayList<>();
        User entity=null;
        for (Iterator <User> it=itr.iterator(); it.hasNext();){
            entity=it.next();
            if (entity!=null) entityList.add(entity);
        }    
        return entityList;           
    }    
    
}
