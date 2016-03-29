/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.services;

import de.adesso.referencer.search.elastic.entities.*;
import de.adesso.referencer.search.elastic.repositories.AdessoRoleRepository;
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
public class AdessoRoleService {
    @Autowired
    private AdessoRoleRepository repository;
    
    public List<AdessoRole> getEntityByName(String name) {
        return repository.findByName(name);
    }

    public AdessoRole addEntity(AdessoRole entity) {
        return repository.save(entity);
    }        
        
    public AdessoRole getEntity(String id) {
        return repository.findOne(id);
    }
    
    public List<AdessoRole> getAll(){
        Iterable<AdessoRole> itr=repository.findAll();
        List <AdessoRole> entityList=new ArrayList<>();
        AdessoRole entity=null;
        for (Iterator <AdessoRole> it=itr.iterator(); it.hasNext();){
            entity=it.next();
            if (entity!=null) entityList.add(entity);
        }    
        return entityList;           
    }    
    
}
