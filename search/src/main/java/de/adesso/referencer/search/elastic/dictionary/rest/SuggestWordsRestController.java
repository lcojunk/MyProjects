/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.dictionary.rest;

import de.adesso.referencer.search.elastic.dictionary.controllers.SuggestWordsController;
import de.adesso.referencer.search.elastic.dictionary.rest.dto.SuggestWordsRESTRequest;
import de.adesso.referencer.search.elastic.dictionary.rest.dto.SuggestWordsRESTResponse;
import de.adesso.referencer.search.elastic.dictionary.rest.dto.SuggestionRestReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author odzhara-ongom
 */
@RestController
//@RequestMapping(value = {"/api/suggest", "/${spring.application.name}" + "/api/suggest"})
@RequestMapping(value = {"/api/suggest"})
public class SuggestWordsRestController {

    @Autowired
    private SuggestWordsController suggestWordsController;

    @RequestMapping(method = RequestMethod.POST)
    public SuggestionRestReply<SuggestWordsRESTResponse> suggestWords(@RequestBody SuggestWordsRESTRequest request) {
        return suggestWordsController.suggestWord(request).setRestReply("/suggest" + "; " + "/${spring.application.name}" + "/suggest");
    }
}
