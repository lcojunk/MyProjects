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
public class BigDocumentService {

    @Autowired
    private BigDocumentRepository repository;

    public BigDocument addEntity(BigDocument entity) {
        if (entity == null) {
            return null;
        }
        return repository.save(entity);
    }

    public BigDocument getEntity(String id) {
        return repository.findOne(id);
    }

    public List<BigDocument> getEntityByReferenceId(String id) {
        return repository.findByRefId(id);
    }

    public List<BigDocument> getEntityByReferenceIdAndType(String id, BigDocumentType type) {
        if (id == null || type == null) {
            return new ArrayList<>();
        }
        return repository.findByRefIdAndType(id, type.toString());
    }

    public List<BigDocument> getAll() {
        Iterable<BigDocument> itr = repository.findAll();
        List<BigDocument> entityList = new ArrayList<>();
        BigDocument entity = null;
        for (Iterator<BigDocument> it = itr.iterator(); it.hasNext();) {
            entity = it.next();
            if (entity != null) {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    public List<BigDocument> deleteByRefId(String refId) {
        if (refId == null || refId == "") {
            return new ArrayList<>();
        } else {
            //return repository.deleteByRefId(refId);
//            return repository.removeByRefId(refId);
            List<BigDocument> toDelete = getEntityByReferenceId(refId);
            if (toDelete == null) {
                return new ArrayList<>();
            }
            for (BigDocument doc : toDelete) {
                repository.delete(doc);
            }
            return toDelete;
        }
    }

    public List<BigDocument> removeByRefId(String refId) {
        if (refId == null || refId == "") {
            return new ArrayList<>();
        } else {
            return repository.removeByRefId(refId);
        }
    }

}
