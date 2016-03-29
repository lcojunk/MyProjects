/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author odzhara-ongom
 */
public class ElasticConfig {
    
    private static String SERVER_NAME="localhost";
    private static String SERVER_PORT="9200";
    private static String jsonDefaultDatabaseFilename="ReferencesDB.json"; 
    private static final String DEFAULT_CHARSET="UTF-8";
    private static final long MAX_CONTENT_LENGTH=1024*1024;
    public static final String DATE_FORMAT_STRING="yyyy-MM-dd'T'HH:mm:ss";
    private static String [] referenceStatusNames={"not released",
                                            "individually released",
                                            "anonymously released",
                                            "fully released"};
    private static String [] referenceGroupNames={"Other",
                                            "Admins",
                                            "Editorial",
                                            "Adesso"};

    public static String[] getReferenceGroupNames() {
        return referenceGroupNames;
    }

    public static String getJsonDefaultDatabaseFilename() {
        return jsonDefaultDatabaseFilename;
    }
    
    private static Gson gson=new Gson();
    
    public static String getURL() {
        return "http://"+SERVER_NAME+":"+SERVER_PORT+"/";
    }

    public static String getURL(String index) {
        return "http://"+SERVER_NAME+":"+SERVER_PORT+"/"+index;
    }

    public static String getURL(String index, String type) {
        return "http://"+SERVER_NAME+":"+SERVER_PORT+"/"+index+"/"+type;
    }
    
    public static String getURLForSearchByField(String index, String type, String fieldname, String fieldvalue) {
        return "http://"+SERVER_NAME+":"+SERVER_PORT+"/"+index+"/"+type+"/_search?q="+fieldname+":"+fieldvalue;
    }
    
    public static String getURLForSearchExists(String index, String type){
        return "http://"+SERVER_NAME+":"+SERVER_PORT+"/"+index+"/"+type+"/_search/exists";
    }
    
    public static String getRequestBodyForSearchExists(String fieldname, String fieldvalue) {
        return  "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : { \""+fieldname+"\" : \""+fieldvalue+"\" }\n" +
                "    }\n" +
                "}";
    }
    
    public static String sendHttpRequest(String url, String requestBody, String requestType) throws IOException{
        String result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse;        
        try {
            switch (requestType) {
                case "Get":     httpResponse = httpclient.execute(new HttpGet(url));                        
                                break;
                case "Post":    HttpPost httppost = new HttpPost(url);
                                if (requestBody!=null)                                     
                                    httppost.setEntity(new StringEntity(requestBody,DEFAULT_CHARSET));                                                                    
                                httpResponse = httpclient.execute(httppost);                        
                                break;
                case "Put":     HttpPut httpPut = new HttpPut(url);
                                httpPut.addHeader("Content-Type", "application/json");
                                httpPut.addHeader("Accept", "application/json");
                                if (requestBody!=null)                                     
                                    httpPut.setEntity(new StringEntity(requestBody,DEFAULT_CHARSET));
                                httpResponse = httpclient.execute(httpPut);                        
                                break;
                case "Delete":  httpResponse = httpclient.execute(new HttpDelete(url));                        
                                break;
                default:        httpResponse = httpclient.execute(new HttpGet(url));                        
                                break;
            }
            try {                
                HttpEntity entity1 = httpResponse.getEntity();
                if (entity1 != null) {
                    long len = entity1.getContentLength();
                    if (len != -1 && len < MAX_CONTENT_LENGTH) {
                        result  = EntityUtils.toString(entity1,DEFAULT_CHARSET);
                    } else {
                        System.out.println("Error!!!! entity length="+len);
                    }
                }
                EntityUtils.consume(entity1);
            } finally {
                httpResponse.close();
            }            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpclient.close();
        }
        return result;        
    }
    
    public static String delIndex(String indexname) {
        String result = null;
        try {
            String url=getURL(indexname);            
            result=ElasticConfig.sendHttpRequest(url, null, "Delete");
        } catch (Exception e) {
            e.printStackTrace();
            result=e.getMessage();
        }
        return result;        
    }    
    
    public static String getSuggestURL(String indexname) {
        return getURL(indexname)+"/_suggest";
    }

    public static String getSuggestRequestBody(String typename, String fieldname, String aWord) {
        int maxCount=100;
        String result="{\n" +
                "  \""+typename+"\" : {\n" +
                "    \"text\" : \""+aWord+"\",\n" +               
                "    \"completion\" : {\n" +
                "      \"field\" : \""+fieldname+"\",\n" +
                "      \"size\":"+maxCount+"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        System.out.println(result);
        return  result;
    }
    public static String getSuggestRequestBody(String typename, String fieldname, String aWord, int maxCount) {        
        String result="{\n" +
                "  \""+typename+"\" : {\n" +
                "    \"text\" : \""+aWord+"\",\n" +               
                "    \"completion\" : {\n" +
                "      \"field\" : \""+fieldname+"\",\n" +
                "      \"size\":"+maxCount+"\n" +
                "    }\n" +
                "  }\n" +
                "}";
       // System.out.println(result);
        return  result;
    }
    
    
    
    public static String sendSuggestRequest(String indexname, 
                                            String typename, 
                                            String fieldname, 
                                            String aWord) throws IOException {
        String url=getSuggestURL(indexname);
        String requestBody=getSuggestRequestBody(typename, fieldname, aWord);
        String reply=sendHttpRequest(url, requestBody, "Post");
        return reply;
    }
    
    public static String sendSuggestRequest(String indexname, 
                                            String typename, 
                                            String fieldname, 
                                            String aWord,
                                            int maxCount) throws IOException {
        String url=getSuggestURL(indexname);
        String requestBody=getSuggestRequestBody(typename, fieldname, aWord, maxCount);
        String reply=sendHttpRequest(url, requestBody, "Post");
        return reply;
    }
    
    public static String bulkURL(){ return ElasticConfig.getURL()+"_bulk";}  
    
    public static String createBulkBody(String command, String index, String type, String id, Object o){
        if (command==null||index==null||type==null||o==null) return null;
        String result=null;
        if (command.matches("create")) result="{ \"create\":";
        else if (command.matches("index")) result="{ \"index\":";
        else if (command.matches("update")) result="{ \"update\":";
        else if (command.matches("delete")) result="{ \"delete\":";
        else return null;        
        result+="{ \"_index\": \""+index+"\", \"_type\": \""+type+"\"";
        if (id==null) result+="}}";
        else result+=", \"_id\": \""+id+"\" }}";
        if (command.matches("delete")) return result;
        MyHelpMethods.setGsonDateFormat("dateOptionalTime");
        result+="\n"+MyHelpMethods.object2GsonString(o);
        MyHelpMethods.resetGsonDateFormat();
        return result;
    }
    
    public static String sendBulkHttpRequest(String requestBody){
        String reply;
        try {
            reply = ElasticConfig.sendHttpRequest(ElasticConfig.bulkURL(), requestBody, "Post");
        } catch (IOException ex) {
            reply="Http Error"+ex.toString();
        }        
        return reply;
    }    
    public static String getSERVER_NAME() {
        return SERVER_NAME;
    }

    public static String getSERVER_PORT() {
        return SERVER_PORT;
    }

    public static String getDEFAULT_CHARSET() {
        return DEFAULT_CHARSET;
    }

    public static long getMAX_CONTENT_LENGTH() {
        return MAX_CONTENT_LENGTH;
    }

    public static Gson getGson() {
        return gson;
    }

    public static String[] getReferenceStatusNames() {
        return referenceStatusNames;
    }
    
    
    
    public static String referenceStatus2String(int status){
        if (status<0||status>=referenceStatusNames.length) return "Status ist unbekannt";
        else return referenceStatusNames[status];
    }

    public static void setSERVER_NAME(String SERVER_NAME) {
        ElasticConfig.SERVER_NAME = SERVER_NAME;
    }

    public static void setSERVER_PORT(String SERVER_PORT) {
        ElasticConfig.SERVER_PORT = SERVER_PORT;
    }
    
    
}
