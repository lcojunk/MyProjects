/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
public class MyHelpMethods {

    private static Gson gson = new Gson();
    private static Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final long MAX_CONTENT_LENGTH = 1024 * 1024;

    public static Random random = new Random();

    public static Gson getGson() {
        return gson;
    }
    public static final int ID_LENGTH = 16;

    /**
     * Returns a pseudo-random number between min and max, inclusive. The
     * difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value. Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randomInt(int min, int max) {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = random.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static String randomNumericString(int n) {
        String result = "" + (new Date()).getTime();
        for (int i = 0; i < n; i++) {
            result += randomInt(0, 9);
        }
        return result;
    }

    public static String generateRandomWord(int length, boolean capitalized, String endedWith) {
        Random random = new Random();
        if (length < 0) {
            return null;
        }
        if (length == 0) {
            return "";
        }
        char[] wordArray = new char[length];
        if (capitalized) {
            wordArray[0] = (char) ('A' + random.nextInt(26));
        } else {
            wordArray[0] = (char) ('a' + random.nextInt(26));
        }
        for (int i = 1; i < wordArray.length; i++) {
            wordArray[i] = (char) ('a' + random.nextInt(26));
        }
        String word = new String(wordArray);
        if (endedWith == null) {
            return word;
        } else {
            return word + endedWith;
        }
    }

    public static String[] generateRandomWords(int numberOfWords) {
        String[] randomStrings = new String[numberOfWords];
        Random random = new Random();
        for (int i = 0; i < numberOfWords; i++) {
            randomStrings[i] = generateRandomWord(random.nextInt(8) + 3, false, null);
        }
        return randomStrings;
    }

    public static void setGsonDateFormat(String dateFormat) {
        if (dateFormat == null) {
            return;
        }
        if (dateFormat.matches("dateOptionalTime")) {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            gsonPretty = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").setPrettyPrinting().create();
        }
        if (dateFormat.matches("dateOptionalTime2")) {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
            gsonPretty = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").setPrettyPrinting().create();
        }
    }

    public static void resetGsonDateFormat() {
        gson = new Gson();
        gsonPretty = new GsonBuilder().setPrettyPrinting().create();
    }

    public static String printString(String s) {
        if (s == null) {
            return "";
        } else {
            return s;
        }
    }

    public static void saveObject2File(Object o, String filename) {

        FileOutputStream fop = null;
        File file;
        String content = gson.toJson(o);

        try {

            file = new File(filename);
            fop = new FileOutputStream(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save2File(Object r, String filename) {

        FileOutputStream fop = null;
        File file;
        String content = gsonPretty.toJson(r);

        try {

            file = new File(filename);
            fop = new FileOutputStream(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    public static String object2PrettyString(Object o) {
        if (o != null) {
            return gsonPretty.toJson(o);
        } else {
            return "null";
        }
    }

    public static String object2GsonString(Object o) {
        if (o != null) {
            return gson.toJson(o);
        } else {
            return "null";
        }
    }

    public static String sendHttpRequest(String url, String requestBody, String requestType) throws IOException {
        String result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse;
        try {
            switch (requestType) {
                case "Get":
                    httpResponse = httpclient.execute(new HttpGet(url));
                    break;
                case "Post":
                    HttpPost httppost = new HttpPost(url);
                    if (requestBody != null) {
                        httppost.setEntity(new StringEntity(requestBody, DEFAULT_CHARSET));
                    }
                    httpResponse = httpclient.execute(httppost);
                    break;
                case "Put":
                    HttpPut httpPut = new HttpPut(url);
                    httpPut.addHeader("Content-Type", "application/json");
                    httpPut.addHeader("Accept", "application/json");
                    if (requestBody != null) {
                        httpPut.setEntity(new StringEntity(requestBody, DEFAULT_CHARSET));
                    }
                    httpResponse = httpclient.execute(httpPut);
                    break;
                case "Delete":
                    httpResponse = httpclient.execute(new HttpDelete(url));
                    break;
                default:
                    httpResponse = httpclient.execute(new HttpGet(url));
                    break;
            }
            try {
                HttpEntity entity1 = httpResponse.getEntity();
                if (entity1 != null) {
                    long len = entity1.getContentLength();
                    if (len != -1 && len < MAX_CONTENT_LENGTH) {
                        result = EntityUtils.toString(entity1, DEFAULT_CHARSET);
                    } else {
                        System.out.println("Error!!!! entity length=" + len);
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

    public static String sendHttpRequest2(String url, String requestBody, String requestType) throws IOException {
        String result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse;
        try {
            switch (requestType) {
                case "Get":
                    httpResponse = httpclient.execute(new HttpGet(url));
                    break;
                case "Post":
                    HttpPost httppost = new HttpPost(url);
                    if (requestBody != null) {
                        httppost.setEntity(new StringEntity(requestBody));
                    }
                    httpResponse = httpclient.execute(httppost);
                    break;
                case "Put":
                    HttpPut httpPut = new HttpPut(url);
                    httpPut.addHeader("Content-Type", "application/json");
                    httpPut.addHeader("Accept", "application/json");
                    if (requestBody != null) {
                        httpPut.setEntity(new StringEntity(requestBody, DEFAULT_CHARSET));
                    }
                    httpResponse = httpclient.execute(httpPut);
                    break;
                case "Delete":
                    httpResponse = httpclient.execute(new HttpDelete(url));
                    break;
                default:
                    httpResponse = httpclient.execute(new HttpGet(url));
                    break;
            }
            try {
                HttpEntity entity1 = httpResponse.getEntity();
                if (entity1 != null) {
                    long len = entity1.getContentLength();
                    if (len != -1 && len < MAX_CONTENT_LENGTH) {
                        result = EntityUtils.toString(entity1, DEFAULT_CHARSET);
                    } else {
                        System.out.println("Error!!!! entity length=" + len);
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

    public static String hashMap2String(HashMap hm) {
        return MyHelpMethods.object2GsonString(hm);
    }

    public static String Date2String(Date date) {
        if (date == null) {
            return null;
        }
        return DateFormat.getDateInstance().format(date);
    }

    public static String getClassNameFromListOfClasses(Field f) {
        String result = "";
        try {
            ParameterizedType listType = (ParameterizedType) f.getGenericType();
            Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
            result = listClass.getName();
        } catch (Exception e) {
            result = "Error!";
            e.printStackTrace();
        }
        return result;
    }

    public static Class getClassFromListOfClasses(Field f) {
        try {
//            System.out.println("f="+f.toString());
//            System.out.println("f.getGenericType()="+f.getGenericType().toString());
            ParameterizedType listType = (ParameterizedType) f.getGenericType();
            Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];

            return listClass;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // return null;
    }

    public static String dateToSearchString(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat("MMMMM MM/yyyy").format(date);
    }

    public static Date addYearsToDate(Date date, int years) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    public static Date addMonthToDate(Date date, int month) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static void pauseMls(long mls) {
        try {
            Thread.sleep(mls);	  // one second
        } catch (Exception e) {
        }	   // this never happen... nobody check for it
    }

    public static boolean isInInterval(long valueMin, long valueMax, long intMin, long intMax) {
        if (valueMin <= intMax && valueMin > intMin && valueMax > intMax) {
            return true;
        }
        if (valueMin <= intMin && valueMax > intMax) {
            return true;
        }
        if (valueMin > intMin && valueMin <= intMax && valueMax > intMin && valueMax <= intMax) {
            return true;
        }
        if (valueMin <= intMin && valueMax >= intMin && valueMax < intMax) {
            return true;
        }
        return false;
    }

    public static Date setDateWithYearMonthDay(int year, int month, int day) {
        if (year < -3000 || year > 3000) {
            return null; //bad year
        }
        if (month < 1 || month > 12) {
            return null;
        }
        if (day < 0 || day > 31) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        Date date = new Date(cal.getTime().getTime());
        return date;
    }

    private static List<String> stripNullValues(List<String> strings) {
        if (strings == null) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for (String string : strings) {
            if (string != null) {
                result.add(string);
            }
        }
        return result;
    }

    public static boolean match(String str1, String str2) {
        return (str1 == null ? str2 == null : str1.equals(str2));
    }

    public static boolean match(String[] one, String[] two) {
        return match(Arrays.asList(one), Arrays.asList(two));
    }

    public static boolean match(List<String> one, List<String> two) {
        if (one == null && two == null) {
            return true;
        }

        if ((one == null && two != null)
                || one != null && two == null
                || one.size() != two.size()) {
            return false;
        }

        //to avoid messing the order of the lists we will use a copy
        //as noted in comments by A. R. S.
        List<String> list1 = stripNullValues(one);
        List<String> list2 = stripNullValues(two);
        if (list1.size() != list2.size()) {
            return false;
        }
        Collections.sort(list1);
        Collections.sort(list2);
        return list1.equals(list2);
    }

    public static Map<String, Long> setWeight(Map<String, Long> wordsMap, int weight) {
        if (wordsMap == null) {
            return null;
        }
        Long count;
        for (String word : wordsMap.keySet()) {
            count = wordsMap.get(word);
            if (count != null) {
                wordsMap.put(word, count * weight);
            }
        }
        return wordsMap;
    }

    public static Map<String, Long> mergeMapOfWords(Map<String, Long> map1, Map<String, Long> map2) {
        if (map1 == null && map2 == null) {
            return null;
        }
        Map<String, Long> result = new HashMap<>();
        if (map1 != null && map1.size() > 0) {
            result.putAll(map1);
            if (map2 != null && map2.size() > 0) {
                for (String s : map2.keySet()) {
                    if (result.containsKey(s)) {
                        if (result.get(s) != null && map2.get(s) != null) {
                            result.put(s, result.get(s) + map2.get(s));
                        } else if (result.get(s) == null && map2.get(s) != null) {
                            result.put(s, map2.get(s));
                        } else if (result.get(s) != null && map2.get(s) == null) {
                            result.put(s, map1.get(s));
                        } else {
                            result.put(s, map1.get(s));
                        }
                    } else {
                        result.put(s, map2.get(s));
                    }
                }
            }
        } else if (map2 != null && map2.size() > 0) {
            result.putAll(map2);
        }
        return result;
    }

    public static boolean containsStringInList(String s, List<String> list) {
        boolean result = false;
        if (s == null || list == null) {
            return false;
        }
        for (String stringFromList : list) {
            if (s.contains(stringFromList)) {
                return true;
            }
        }
        return result;
    }

    public static boolean matchesStringInList(String s, List<String> list) {
        boolean result = false;
        if (s == null || list == null) {
            return false;
        }
        for (String stringFromList : list) {
            if (s.matches(stringFromList)) {
                return true;
            }
        }
        return result;
    }
}
