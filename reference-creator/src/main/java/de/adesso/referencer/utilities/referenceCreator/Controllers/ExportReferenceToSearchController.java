/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Controllers;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ReferenceEntity;
import de.adesso.referencer.utilities.referenceCreator.Entities.search.Reference;
import de.adesso.referencer.utilities.referenceCreator.Entities.search.RestReply;
import de.adesso.referencer.utilities.referenceCreator.Entities.search.SearchRefefenceEntityFactory;
import de.adesso.referencer.utilities.referenceCreator.database.ReferenceJavaDatabase;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author odzhara-ongom
 */
public class ExportReferenceToSearchController {

    private ReferenceJavaDatabase db;
    private static final String SERVER_URL = "http://localhost:9097";
    //private static final String POST_REFERENCE_WITHOUT_DICT_URL = "http://localhost:9097/referencer/search/crud/reference";
    private static final String POST_REFERENCE_WITHOUT_DICT_URL = SERVER_URL + "/dev/api" + "/references";
    private static final String POST_REFERENCE_WITH_DICT_URL = SERVER_URL + "/dev/api" + "/references_and_tokens";
    private static String RECREATE_REFERENCE_DATABASE_URL = SERVER_URL + "/dev/api" + "/references/_recreate_index";
    private static String RECREATE_DICTIONARY_DATABASE_URL = SERVER_URL + "/dev/api" + "/dictionary/_recreate_index";
    private static String DELETE_REFERENCE_DATABASE_URL = SERVER_URL + "/referencer/search/crud/reference" + "/_delete_index";
    private static String CHECK_REFERENCE_DATABASE_URL = POST_REFERENCE_WITHOUT_DICT_URL;

    public ReferenceJavaDatabase getDb() {
        return db;
    }

    public void setDb(ReferenceJavaDatabase db) {
        this.db = db;
    }

    public ExportReferenceToSearchController() {
    }

    public ExportReferenceToSearchController(ReferenceJavaDatabase db) {
        this.db = db;
    }

    public RestReply sendHttpRequestWithRestTemplate(String url, Object requestBody, String method) {
        RestReply result = new RestReply();
        if (method == null) {
            result.setError("Request method not defined");
            return result;
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            if (method.matches("Get")) {
                result = restTemplate.getForObject(url, RestReply.class);
                return result;
            }
            if (method.matches("Post")) {
                result = restTemplate.postForObject(url, requestBody, RestReply.class);
                return result;
            }
        } catch (Exception e) {
            result = new RestReply();
            result.setError(e.getMessage());
            return result;
        }
        result.setError("No suitable request method was found");
        return result;
    }

    public RestReply exportReference(String id) {
        RestReply result = new RestReply();
        if (db == null || db.getAllReferenceEntity() == null) {
            result.setError("Database is empty");
            return result;
        }
        ReferenceEntity referenceEntity = db.getAllReferenceEntity().getEntityById(id);
        if (referenceEntity == null) {
            result.setError("Entity not found in Java Database");
            return result;
        }
        SearchRefefenceEntityFactory factory = new SearchRefefenceEntityFactory(db);
        Reference reference = factory.createSearchReferenceEntity(referenceEntity);
        result = sendHttpRequestWithRestTemplate(POST_REFERENCE_WITHOUT_DICT_URL, reference, "Post");
        return result;
    }

    public RestReply exportReference(int n) {
        RestReply result = new RestReply();
        if (db == null || db.getAllReferenceEntity() == null) {
            result.setError("Database is empty");
            return result;
        }
        if (n < 0 || n >= db.getAllReferenceEntity().getEntityList().size()) {
            result.setError("No such entity");
            return result;
        }
        ReferenceEntity referenceEntity = db.getAllReferenceEntity().getEntityList().get(n);
        if (referenceEntity == null) {
            result.setError("Entity not found in Java Database");
            return result;
        }
        SearchRefefenceEntityFactory factory = new SearchRefefenceEntityFactory(db);
        Reference reference = factory.createSearchReferenceEntity(referenceEntity);
        result = sendHttpRequestWithRestTemplate(POST_REFERENCE_WITH_DICT_URL, reference, "Post");

        return result;
    }

    public RestReply exportAllReferences() {
        return exportAllReferences(0);
    }

    public RestReply exportAllReferences(int method) {
        RestReply result = new RestReply();
        if (db == null || db.getAllReferenceEntity() == null) {
            result.setError("Database is empty");
            return result;
        }
        SearchRefefenceEntityFactory factory = new SearchRefefenceEntityFactory(db);
        Reference reference;
        List<Object> refList = new ArrayList<Object>();
        RestReply singleUpdateReply;
        int count = 0;
        int maxCount = db.getAllReferenceEntity().getEntityList().size();
        for (ReferenceEntity entity : db.getAllReferenceEntity().getEntityList()) {
            count++;
            reference = factory.createSearchReferenceEntity(entity);
            if (reference != null) {
                if (method == 1) {
                    singleUpdateReply = sendHttpRequestWithRestTemplate(POST_REFERENCE_WITH_DICT_URL, reference, "Post");
                } else {
                    singleUpdateReply = sendHttpRequestWithRestTemplate(POST_REFERENCE_WITHOUT_DICT_URL, reference, "Post");
                }

                if (singleUpdateReply.isSuccess()) {
                    refList.add(singleUpdateReply.getResult());
                }
            }
            System.out.println("Processed references: " + count + " of " + maxCount);
        }
        result.setResult(refList);
        result.setCount(refList.size());
        if (result.getCount() == db.getAllReferenceEntity().getEntityList().size()) {
            result.setSuccess(true);
        }
        return result;
    }

    public RestReply reCreateDatabase() {
        RestReply reply = sendHttpRequestWithRestTemplate(RECREATE_REFERENCE_DATABASE_URL, null, "Get");
        if (reply == null) {
            reply = new RestReply();
            reply.setSuccess(false);
            reply.setError("sendHttpRequest returned null value");
            return reply;
        }
        if (!reply.isSuccess()) {
            return reply;
        }
        return sendHttpRequestWithRestTemplate(RECREATE_DICTIONARY_DATABASE_URL, null, "Get");
    }

    public RestReply deleteDatabase() {
        return sendHttpRequestWithRestTemplate(DELETE_REFERENCE_DATABASE_URL, null, "Post");
    }

    public RestReply indexExists() {
        RestTemplate restTemplate = new RestTemplate();
        RestReply result = restTemplate.getForObject(CHECK_REFERENCE_DATABASE_URL, RestReply.class);
        return result;
    }
}
