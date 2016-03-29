/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.dummies;

import de.adesso.referencer.utilities.referenceCreator.Entities.search.RestReply;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.adesso.referencer.utilities.referenceCreator.Controllers.ExportReferenceToSearchController;
import de.adesso.referencer.utilities.referenceCreator.Controllers.RefCreatorMain;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Person;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ReferenceEntity;
import de.adesso.referencer.utilities.referenceCreator.Entities.search.Reference;
import de.adesso.referencer.utilities.referenceCreator.Entities.search.RestReplyReference;
import de.adesso.referencer.utilities.referenceCreator.Entities.search.SearchRefefenceEntityFactory;
import de.adesso.referencer.utilities.referenceCreator.Entities.search.SearchReferenceRequestEntity;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import de.adesso.referencer.utilities.referenceCreator.database.BooleanReply;
import de.adesso.referencer.utilities.referenceCreator.database.ReferenceJavaDatabase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author odzhara-ongom
 */
public class TestController {
    
    
   
    
    public static void testDatabaseConsistency(){
        ReferenceJavaDatabase db= RefCreatorMain.startJavaDatabase();
//        for (ReferenceEntity e:db.getAllReferenceEntity().getEntityList()){
//            if(e.getApprover()!=null)
//                if(e.getApprover().getId()!=null){
//                    String id=e.getApprover().getId();
//                    System.out.println("Deleting approver with id"+id);
//                    db.getAllElasticUser().deleteEntityById(id);
//                    break;
//                }
//        }
        //db.getAllPerson().getEntityList().remove(1);
        //System.out.println(MyHelpMethods.object2PrettyString(db.isUsersListConsistent()));
        //System.out.println(db.isUsersListConsistent().toString(1));
        System.out.println(db.isDatabaseConsistent().toString(1));
        //System.out.println(MyHelpMethods.object2PrettyString(db.getAllElasticUser().getEntityList().get(1)));
    }
    
    public static RestReply sendHttpRequestWithRestTemplate(String url, Object requestBody, String method) {
        RestReply result = new RestReply();
        if (method==null) {
            result.setError("Request method not defined");
            return result;
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            if (method.matches("Get")) {
                result=restTemplate.getForObject(url, RestReply.class);
                return result;
            }
            if (method.matches("Post")) {
                result=restTemplate.postForObject(url,requestBody, RestReply.class);
                return result;
            }            
        } catch (Exception e) {
            result=new RestReply();
            result.setError(e.getMessage());
            return result;
        }
        result.setError("No suitable request method was found");
        return result;
    }
    
    public static void testSearcgReferenceFactory(ReferenceJavaDatabase db) {
        System.out.println("Testing SearcgReferenceFactory... ");
        int entityNumber = 5;
        if (db==null) {
            System.out.println("Database is empty");
            return;
        }
        SearchRefefenceEntityFactory factory = new SearchRefefenceEntityFactory(db);
        if (factory==null) {
            System.out.println("cannot create factory");
            return;            
        }
        Reference reference = factory.createSearchReferenceEntity(db.getAllReferenceEntity().getEntityList().get(entityNumber));        
        String url="http://localhost:9097/crud/reference";        
        RestReply reply;
        reply=sendHttpRequestWithRestTemplate(url,reference,"Post");
        System.out.println(MyHelpMethods.object2PrettyString(reply));
        System.out.println("Test Complete");
    }

    public static void testSearchReferenceFactory2(ReferenceJavaDatabase db) {
        int entityNumber = 5;
        ExportReferenceToSearchController controller=new ExportReferenceToSearchController(db);
        //RestReply reply=controller.exportReference(entityNumber);
        RestReply reply=controller.exportAllReferences();
        //System.out.println(MyHelpMethods.object2PrettyString(reply));
        System.out.println(reply.getCount());
    }
    
    public static void runMain(String[] args) {
        System.out.println("Starting test controller");
        
        //testDatabaseConsistency();
        //ReferenceJavaDatabase db= RefCreatorMain.startJavaDatabase();        
        //testSearchReferenceFactory2(db);
        //String url="http://localhost:9097/referencer/search/reference_entity";
        String url="http://localhost:9097/referencer/search/reference";
        RestTemplate restTemplate = new RestTemplate();
        RestReplyReference reply = restTemplate.postForObject(url, new SearchReferenceRequestEntity(),RestReplyReference.class);
        List <Reference> oList =  reply.getResult();
        Reference r;
        String s;
//        for (Object o : oList) {
//            s=MyHelpMethods.object2GsonString(o);
//            r=MyHelpMethods.getGson().fromJson(s, Reference.class);
//        }
        //System.out.println(MyHelpMethods.object2PrettyString(rList));
        System.out.println(MyHelpMethods.object2PrettyString(oList.get(0)));
        //Reference [] rArray=MyHelpMethods.getGson().fromJson(resultString, Reference[].class);
//        
//        for (int i=0; i<rArray.length; i++) {
//            System.out.println(rArray[i].getClientname());
//        }

//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
//        System.out.println(gson.toJson(db.getAllReferenceEntity().getEntityList().get(0).getObjectVersionID()));
        //ElasticSearchAllObjectReplyWithAggregations es= new ElasticSearchAllObjectReplyWithAggregations();
        System.out.println("Done");
   }
}
