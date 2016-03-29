/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.rest.controllers;

import de.adesso.referencer.search.elastic.controllers.CRUDReferenceController;
import de.adesso.referencer.search.elastic.entities.*;
import de.adesso.referencer.search.rest.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author odzhara-ongom
 */
@RestController
//@RequestMapping(value = {"/api/references", "/${spring.application.name}" + "/api/references"})
@RequestMapping(value = {"/api/references"})
public class CrudReferenceRESTController {

    @Autowired
    private CRUDReferenceController controller;

    @RequestMapping(method = RequestMethod.POST)
    public RestReply createReference(@RequestBody SaveReferenceRestRequest entity) {
        return controller.save(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestReply getEntityById(@PathVariable String id) {
        return controller.getReferenceById(id);
    }

    @RequestMapping(value = "/{id}/document", method = RequestMethod.POST)
    public RestReply<BigDocument> saveOrUpdateWordDocument(@PathVariable String id, @RequestBody SaveBigDocumentRestRequest request) {
        if (request != null) {
            request.setRefId(id);
            request.setType(BigDocumentType.DOCUMENT);
        }
        return controller.saveOrUpdateWordDocument(request);
    }

    @RequestMapping(value = "/{id}/logo", method = RequestMethod.POST)
    public RestReply<BigDocument> saveOrUpdateLogoDocument(@PathVariable String id, @RequestBody SaveBigDocumentRestRequest request) {
        if (request != null) {
            request.setRefId(id);
            request.setType(BigDocumentType.LOGO);
        }
        return controller.saveOrUpdateLogoDocument(request);
    }

    @RequestMapping(value = "/{id}/document", method = RequestMethod.GET)
    ResponseEntity<ByteArrayResource> downloadDocumentByRefId(@PathVariable String id) {
        return controller.downloadDocumentByRefId(id);
    }

    @RequestMapping(value = "/{id}/logo", method = RequestMethod.GET)
    ResponseEntity<ByteArrayResource> downloadLogoByRefId(@PathVariable String id) {
        return controller.downloadLogoByRefId(id);
    }

}
