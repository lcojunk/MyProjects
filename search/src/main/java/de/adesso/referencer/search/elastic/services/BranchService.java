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
public class BranchService {
    @Autowired
    private BranchRepository repository;
    
    public List<Branch> getEntityByName(String name) {
        return repository.findByName(name);
    }

    public Branch addEntity(Branch entity) {
        return repository.save(entity);
    }        
        
    public Branch getEntity(String id) {
        return repository.findOne(id);
    }
    
    public List<Branch> getAll(){
        Iterable<Branch> itr=repository.findAll();
        List <Branch> entityList=new ArrayList<>();
        Branch entity=null;
        for (Iterator <Branch> it=itr.iterator(); it.hasNext();){
            entity=it.next();
            if (entity!=null) entityList.add(entity);
        }    
        return entityList;           
    }    
    
}
