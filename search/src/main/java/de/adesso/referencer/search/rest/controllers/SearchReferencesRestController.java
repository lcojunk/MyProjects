/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.rest.controllers;

import de.adesso.referencer.search.elastic.controllers.RestSearchController;
import de.adesso.referencer.search.rest.dto.FacetedSearchReferenceRESTRequest;
import de.adesso.referencer.search.rest.dto.FacetedSearchReferenceRESTResponse;
import de.adesso.referencer.search.rest.dto.RestReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author odzhara-ongom
 */
@RestController
//@RequestMapping(value = {"/api/search", "/${spring.application.name}" + "/api/search"})
@RequestMapping(value = {"/api/search"})
public class SearchReferencesRestController {

    @Autowired
    private RestSearchController restSearchController;

    @RequestMapping(method = RequestMethod.POST)
    public RestReply<FacetedSearchReferenceRESTResponse> searchReferencesWithfacets(
            @RequestBody FacetedSearchReferenceRESTRequest requestEntity) {
        return restSearchController.facetedSearchForReferenceWithTopHits(requestEntity);
    }

}
