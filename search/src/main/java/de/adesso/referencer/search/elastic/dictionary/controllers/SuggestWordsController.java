/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.dictionary.controllers;

import de.adesso.referencer.search.elastic.dictionary.controllers.dto.*;
import de.adesso.referencer.search.elastic.dictionary.entities.WordFromReference;
import de.adesso.referencer.search.elastic.dictionary.rest.dto.*;
import de.adesso.referencer.search.elastic.entities.Reference;
import de.adesso.referencer.search.elastic.dictionary.services.DictionaryService;
import de.adesso.referencer.search.elastic.services.ReferenceService;
import de.adesso.referencer.search.helper.MyHelpMethods;
import de.adesso.referencer.search.rest.dto.RestReply;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class SuggestWordsController {

    @Autowired
    private SuggestWordsFactory suggestWordsFactory;

    @Autowired
    private SuggestWordsService suggestWordsService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private ReferenceService referenceService;

    private Date serviceStart = new Date();

    public Date getServiceStart() {
        return serviceStart;
    }

    public void setServiceStart(Date serviceStart) {
        this.serviceStart = serviceStart;
    }

    public SuggestionRestReply<SuggestWordsRESTResponse> suggestWord(SuggestWordsRESTRequest userRequest) {
        SuggestionRestReply<SuggestWordsRESTResponse> result = new SuggestionRestReply<>();
        try {
            if (dictionaryService.count() <= 0) {
                result.setError("Dictionary is empty. Entity count=" + dictionaryService.count());
                result.setCount(0);
                return result;
            }
            SuggestWordsRequest request = suggestWordsFactory.createSuggestWordsRequest(userRequest);
            SuggestWordsResponse serverResponse = suggestWordsService.suggestWords(request);
            SuggestWordsRESTResponse response = suggestWordsFactory.createSuggestWordsRESTResponse(serverResponse);
            response.setRequest(userRequest);
            response.setSuggestWordServiceStart(suggestWordsService.getServiceStart());
            response.setSuggestWordControllerStart(getServiceStart());
            result.setResult(response);
            result.setCount(response.getSuggestions().size());
            result.setError("under development");

        } catch (Exception e) {
            e.printStackTrace();
            result.setError(result.getError() + " Error! Error by suggestion: " + e.getMessage() + "; error=" + MyHelpMethods.object2PrettyString(e));
            return result;
        }
        result.setSuccess(true);

        return result;
    }

    public RestReply indexExists() {
        RestReply result = new RestReply();
        try {
            Boolean reply = new Boolean(dictionaryService.indexExists());
            result.setResult(reply);
            result.setSuccess(reply.booleanValue());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
            result.setResult(new Boolean(false));
            return result;
        }
    }

    public RestReply createIfNotExistsIndex() {
        RestReply result = new RestReply();
        try {
            if (dictionaryService.indexExists()) {
                if (!dictionaryService.deleteIndex()) {
                    result.setResult(new Boolean(false));
                    result.setError("Index exists, but cannot be deleted");
                    return result;
                }
            }
            if (!dictionaryService.createIndex()) {
                result.setResult(new Boolean(false));
                result.setError("Cannot create index");
                return result;
            }
            if (!dictionaryService.putMapping()) {
                result.setResult(new Boolean(false));
                result.setError("Cannot map data in the index");
                return result;
            }
            result.setResult(new Boolean(true));
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
            result.setResult(new Boolean(false));
            return result;
        }
    }

    public RestReply deleteIndex() {
        RestReply result = new RestReply();
        try {
            if (dictionaryService.indexExists()) {
                Boolean reply = new Boolean(dictionaryService.deleteIndex());
                result.setResult(reply);
                result.setSuccess(true);
                return result;
            }
            result.setResult(new Boolean(false));
            result.setError("Index not existed");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e.getMessage());
            result.setResult(new Boolean(false));
            return result;
        }
    }

    public SuggestionRestReply<IndexReferenceResponse> indexReference(Reference reference) {
        SuggestionRestReply<IndexReferenceResponse> result = new SuggestionRestReply<>();
        try {
            long timeStamp = (new Date()).getTime();
            if (reference == null) {
                result.setError("Cannot null reference");
                result.setSuccess(new Boolean(false));
                return result;
            }
            if (!dictionaryService.indexExists()) {
                result.setError("Dictionary index do not exists");
                result.setSuccess(new Boolean(false));
                return result;
            }
            if (!referenceService.indexExists()) {
                result.setError("Reference index do not exists");
                result.setSuccess(new Boolean(false));
                return result;
            }
            List<WordFromReference> wordList = suggestWordsService.indexReference(reference);
            if (wordList == null) {
                result.setError("index of reference with id=" + reference.getId() + " returned null result");
                result.setSuccess(new Boolean(false));
                return result;
            }
            timeStamp = (new Date()).getTime() - timeStamp;

            result.setResult(new IndexReferenceResponse(reference.getId(), timeStamp, wordList.size()));
            result.setCount(1L);
            result.setSuccess(new Boolean(true));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError("Something bad happend: " + e.getMessage() + "\n \nError=" + MyHelpMethods.object2PrettyString(e));
            result.setSuccess(new Boolean(false));
            return result;
        }
    }

    public SuggestionRestReply<IndexReferenceResponse> indexReferenceById(String id) {
        SuggestionRestReply<IndexReferenceResponse> result = new SuggestionRestReply<>();
        try {
            long timeStamp = (new Date()).getTime();
            if (id == null) {
                result.setError("Cannot reference with id=null");
                result.setSuccess(new Boolean(false));
                return result;
            }
            if (!dictionaryService.indexExists()) {
                result.setError("Dictionary index do not exists");
                result.setSuccess(new Boolean(false));
                return result;
            }
            if (!referenceService.indexExists()) {
                result.setError("Reference index do not exists");
                result.setSuccess(new Boolean(false));
                return result;
            }
            Reference reference = referenceService.getEntity(id);
            if (reference == null) {
                result.setError("Reference with id=" + id + " do not exists");
                result.setSuccess(new Boolean(false));
                return result;
            }
            List<WordFromReference> wordList = suggestWordsService.indexReference(reference);
            if (wordList == null) {
                result.setError("index of reference with id=" + id + " returned null result");
                result.setSuccess(new Boolean(false));
                return result;
            }
            timeStamp = (new Date()).getTime() - timeStamp;

            result.setResult(new IndexReferenceResponse(id, timeStamp, wordList.size()));
            result.setCount(1L);
            result.setSuccess(new Boolean(true));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError("Something bad happend: " + e.getMessage() + "\n \nError=" + MyHelpMethods.object2PrettyString(e));
            result.setSuccess(new Boolean(false));
            return result;
        }
    }

    private Integer indexReferencesFromDatabase(Integer count) throws SplitStringInTokenException {
        List<Reference> references = referenceService.getAll();
        if (references == null) {
            return null;
        }
        if (references.size() == 0) {
            return 0;
        }
        int workingCount = references.size();
        if (count != null && count > 0 && count < workingCount) {
            workingCount = count;
        }
        Reference reference;
        for (int i = 0; i < workingCount; i++) {
            reference = references.get(i);
            suggestWordsService.indexReference(reference);
        }
        return count;
    }

    public SuggestionRestReply<String> indexReferenceByCount(Integer count) {
        SuggestionRestReply<String> result = new SuggestionRestReply<>();
        try {
            long timeStamp = (new Date()).getTime();
            if (dictionaryService.indexExists()) {
                if (!dictionaryService.deleteIndex()) {
                    result.setSuccess(new Boolean(false));
                    result.setError("Index exists, but cannot be deleted");
                    return result;
                }
            }
            if (!dictionaryService.createIndex()) {
                result.setSuccess(new Boolean(false));
                result.setError("Cannot create index");
                return result;
            }
            if (!dictionaryService.putMapping()) {
                result.setSuccess(new Boolean(false));
                result.setError("Cannot map data in the index");
                return result;
            }
            if (!referenceService.indexExists()) {
                result.setError("Reference index do not exists");
                result.setSuccess(new Boolean(false));
                return result;
            }
            Integer wordCount = indexReferencesFromDatabase(count);
            if (wordCount == null) {
                result.setError("indexing of reference ended with  null result");
                result.setSuccess(new Boolean(false));
                return result;
            }
            timeStamp = (new Date()).getTime() - timeStamp;
            String reply = wordCount + " references was processed in " + timeStamp + " ms. ";
            result.setResult(reply);

            result.setSuccess(new Boolean(true));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError("Something bad happend: " + e.getMessage() + "\n \nError=" + MyHelpMethods.object2PrettyString(e));
            result.setSuccess(new Boolean(false));
            return result;
        }
    }

    public SuggestionRestReply<WordFromReference> findByName(SuggestWordsRESTRequest request) {
        SuggestionRestReply<WordFromReference> result = new SuggestionRestReply<>();
        if (request == null || request.getSuggestion() == null) {
            result.setError("Cannot search for empty words");
            result.setSuccess(new Boolean(false));
            return result;
        }
        try {
            WordFromReference reply = dictionaryService.findByName(request.getSuggestion());
            result.setResult(reply);
            if (reply != null) {
                result.setCount(1);
            }
            result.setSuccess(new Boolean(true));
            return result;
        } catch (Exception e) {
            //e.printStackTrace();
            result.setError("Something bad happend: " + e.getMessage() + "\n \nError=" + MyHelpMethods.object2PrettyString(e));
            result.setSuccess(new Boolean(false));
            return result;
        }
    }

    public SuggestionRestReply<WordFromReference> searchByName(SuggestWordsRESTRequest request) {
        SuggestionRestReply<WordFromReference> result = new SuggestionRestReply<>();
        if (request == null || request.getSuggestion() == null) {
            result.setError("Cannot search for empty words");
            result.setSuccess(new Boolean(false));
            return result;
        }
        try {
            WordFromReference reply = dictionaryService.searchByName(request.getSuggestion());
            result.setResult(reply);
            if (reply != null) {
                result.setCount(1);
            }
            result.setSuccess(new Boolean(true));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setError("Something bad happend: " + e.getMessage() + "\n \nError=" + MyHelpMethods.object2PrettyString(e));
            result.setSuccess(new Boolean(false));
            return result;
        }
    }

    public SuggestionRestReply<ExcludedWordsService> getExcludedWords() {
        SuggestionRestReply<ExcludedWordsService> result = new SuggestionRestReply<>();
        ExcludedWordsService service = suggestWordsService.getExcludedWordsService();
        result.setResult(service);
        result.setSuccess(true);
        return result;
    }

}
