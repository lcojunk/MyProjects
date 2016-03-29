/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

//import MyReferenzerProject2.Authentication.LDAPAuthentication;
import de.adesso.referencer.utilities.referenceCreator.ElasticConfig;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.AccessStamp;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.AdessoRole;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Branch;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ElasticUser;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Extras;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Feature;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.InvolvedRole;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.LOB;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Person;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.PersonAddress;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ProjectRole;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ReferenceEntity;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Technology;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Topic;
import de.adesso.referencer.utilities.referenceCreator.LDAPAuthentication;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import de.adesso.referencer.utilities.referenceCreator.ResourceReader;
import java.io.BufferedInputStream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class ReferenceJavaDatabase {

    public enum ADESSO_GROUPS {

        Admins, Editorial, Adesso, Other
    };
    private String filename = "jsonDatabase.json";
    private String version = "0.3"; //added extras
    private Date versionDate = new Date("08/05/2015"); // MM/DD/YYYY
    private Date created;

    private AllAdessoRole allAdessoRole;
    private AllBranches allBranches;
    private AllElasticUser allElasticUser;
    private AllFeature allFeature;
    private AllInvolvedRole allInvolvedRole;
    private AllLobs allLobs;
    private AllPerson allPerson;
    private AllProjectRole allProjectRole;
    private AllReferenceEntity allReferenceEntity;
    private AllTechnology allTechnology;
    private AllTopic allTopic;
    private AllExtras allExtras;

    public PersonAddress createAdessoCologneAddress() {
        PersonAddress result = new PersonAddress();
        result.setCity("Köln");
        result.setCountry("Deutschland");
        result.setPlz("50678");
        result.setStreet("Agrippinawerft 28");
        result.setType("work");
        result.setDescription("Automatic created address");
        return result;
    }

    public void createDefaultUsers() {
        List<ElasticUser> defaultUserList = new ArrayList<ElasticUser>();
        ElasticUser currentUser;
        Person currentPerson;
        List<PersonAddress> addressList;
        List<String> sList;
        String username;
        for (int i = 0; i < LDAPAuthentication.getTestUsernames().length; i++) {
            username = LDAPAuthentication.getTestUsernames()[i];
            currentUser = new ElasticUser();
            currentUser.setId(MyHelpMethods.randomNumericString(MyHelpMethods.ID_LENGTH));
            currentUser.setUsername(username);
            currentPerson = new Person();
            sList = new ArrayList<String>();
            sList.add(username + "@adesso.de");
            currentPerson.setEmail(sList);
            addressList = new ArrayList<PersonAddress>();
            addressList.add(createAdessoCologneAddress());
            currentPerson.setAddress(addressList);
            currentPerson.setDescription("Automatic created Person");
            currentPerson.setTel(new ArrayList<String>());
            currentPerson.getTel().add("0221-34667-077");
            currentPerson.getTel().add("+49 221 34667-10");
            currentPerson.setForename(username);
            switch (i) {
                case 0:
                    currentUser.setGroup(ADESSO_GROUPS.Admins + "");
                    currentPerson.setName("Admin");
                    break;
                case 1:
                    currentUser.setGroup(ADESSO_GROUPS.Editorial + "");
                    currentPerson.setName("Editorial");
                    break;
                case 2:
                    currentUser.setGroup(ADESSO_GROUPS.Editorial + "");
                    currentPerson.setName("Editorial");
                    break;
                case 3:
                    currentUser.setGroup(ADESSO_GROUPS.Adesso + "");
                    currentPerson.setName("Adesso");
                    break;
                default:
                    currentUser.setGroup(ADESSO_GROUPS.Other + "");
                    break;
            }
            if (AllPerson.getInstance().addEntity(currentPerson) != null) {
                currentUser.setPerson(currentPerson);
            }
            AllElasticUser.getInstance().addEntity(currentUser);
        }
    }

    public void createDefaultTechnology() {
        allTechnology.getInstance().addEntity(
                new Technology("Java", "Automatic created technology")
        );
        allTechnology.getInstance().addEntity(
                new Technology("C++", "Automatic created technology")
        );
        allTechnology.getInstance().addEntity(
                new Technology("ASP.NET (MVC 3.0)", "Automatic created technology")
        );
        allTechnology.getInstance().addEntity(
                new Technology("SQL Server 2005 und 2008 R2", "Automatic created technology")
        );
    }

    public void createDefaultReference() {
        ReferenceEntity ref = new ReferenceEntity();
        ref.setDefaultDaten();
        ref.setPtAdesso(10);
        ref.setPtTotalProject(12);
        ref.setCostAdesso(3011.10);
        ref.setCostTotal(4999.99);
        ref.setLob(allLobs.getEntityList().get(0).getId());
        ref.getBranchList().add(allBranches.getEntityList().get(0).getId());
        ref.setOwner(allElasticUser.getEntityList().get(1));
        ref.setEditor(allElasticUser.getEntityList().get(2));
        ref.setqAssurance(allElasticUser.getEntityList().get(1));
        ref.setApprover(allElasticUser.getEntityList().get(3));
        ref.setRoles(new ArrayList<String>());
        ref.getRoles().add(allInvolvedRole.getInstance().getEntityList().get(0).getId());
        ref.setTechnics(new ArrayList<String>());
        ref.getTechnics().add(allTechnology.getInstance().getEntityList().get(0).getId());
        ref.setExtras(new ArrayList<String>());
        ref.getExtras().add(allExtras.getInstance().getEntityList().get(0).getId());
        ref.setTopics(new ArrayList<String>());
        ref.getTopics().add(allTopic.getEntityList().get(0).getId());
        ref.getTechnics().add(allTechnology.getEntityList().get(0).getId());
        ref.getTechnics().add(allTechnology.getEntityList().get(1).getId());
        ref.setAdessoPartnerRole(allProjectRole.getEntityList().get(0).getId());
        ref.setTeammembers(new ArrayList<Person>());
        ref.getTeammembers().add(ref.getOwner().getPerson());
        ref.getTeammembers().add(ref.getEditor().getPerson());
        ref.getTeammembers().add(ref.getApprover().getPerson());
        AccessStamp as = new AccessStamp();
        as.action = "Created default user";
        as.name = ref.getOwner().getUsername();
        as.date = new Date();
        as.eUser = ref.getOwner();
        ref.setChanged(as);
        allReferenceEntity.addEntity(ref);
    }

    /*
     public void createDefaultAdessoRole(){
     allAdessoRole.getEntityList().add(new AdessoRole("Test Adesso Rolle", "Automatic created Rolle"));
     }

     public void createDefaultBranch(){
     allAdessoRole.getEntityList().add(new AdessoRole("Test Branch", "Automatic created Branch"));
     }

     public void createDefaultFeature(){
     allAdessoRole.getEntityList().add(new AdessoRole("Test Feature", "Automatic created Feature"));
     }

     public void createDefaultInvolvedRole(){
     allAdessoRole.getEntityList().add(new AdessoRole("Test InvolvedRole", "Automatic created InvolvedRole"));
     }
     */
    public void createEmptyDatabase() {
        allAdessoRole = AllAdessoRole.getInstance();
        allBranches = AllBranches.getInstance();
        allElasticUser = AllElasticUser.getInstance();
        allFeature = AllFeature.getInstance();
        allInvolvedRole = AllInvolvedRole.getInstance();
        allLobs = AllLobs.getInstance();
        allPerson = AllPerson.getInstance();
        allProjectRole = AllProjectRole.getInstance();
        allReferenceEntity = AllReferenceEntity.getInstance();
        allTechnology = AllTechnology.getInstance();
        allTopic = AllTopic.getInstance();
        allExtras = AllExtras.getInstance();
        created = new Date();
    }

    public void createDefaultDatabase() {
        createEmptyDatabase();
        System.out.println("Creating default Database");
        if (loadDatabaseFromJsonInJar()) {
            System.out.println("Database loaded from JSON in JAR-File");
            System.out.println(allReferenceEntity.getEntityList().size() + " references was loaded");
            return;
        }
        allLobs.addEntity(new LOB("LOB 2", "Bla bla Bla"));
        createDefaultUsers();
        createDefaultTechnology();
        createDefaultReference();
        /*       ReferenceEntity r= allReferenceEntity.getEntityList().get(0);
         r.setRefIDList( new ArrayList<String>());
         r.getRefIDList().add(r.getId());
         allReferenceEntity.updateEntity(r);
         */ created = new Date();
    }

    public boolean createDatabaseFromFile(String filename) {
        createEmptyDatabase();
        System.out.println("Loading database from: '" + filename + "'");
        boolean result = loadDatabase(filename);
        if (result) {
            this.filename = filename;
        }
        return result;
    }

    public static String string2JSON(String name, String value) {
        if (name == null || value == null) {
            return "";
        }
        return "\"" + name + "\": " + "\"" + value + "\"";
    }

    public static String object2JSON(String name, Object o) {
        String value = MyHelpMethods.object2GsonString(o);
        //System.out.println(value);
        if (name == null || value == null) {
            return "";
        }
        return "\"" + name + "\": " + value;
    }

    public static void writeString2File(Writer fop, String s) throws IOException {
        fop.write(s + "\n");
        fop.flush();
    }

    public static void writeListOfObjects2File(Writer fop, String name, List<Object> oList) throws IOException {
        if (fop == null || name == null || oList == null) {
            return;
        }
        writeString2File(fop, "\"" + name + "\": [");
        int size = oList.size();
        if (size > 0) {
            for (int i = 0; i < size - 1; i++) {
                fop.write(MyHelpMethods.object2GsonString(oList.get(i)) + ",\n");
                fop.flush();
            }
            fop.write(MyHelpMethods.object2GsonString(oList.get(size - 1)) + "\n");
            fop.flush();
        }
        writeString2File(fop, "]");
    }

    public void save2File(JavaDatabaseEntity entity, String filename) {
        Writer fop = null;
        File file;
        String s;
        List<Object> objectList;
        try {
            file = new File(filename);
            //br = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(file))));
            fop = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF8"));

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
//                fop.write(string2Write.getBytes());
//                fop.flush();
            writeString2File(fop, "{");
            writeString2File(fop, string2JSON("version", entity.getVersion()) + ",");
            writeString2File(fop, object2JSON("versionDate", entity.getVersionDate()) + ",");
            writeString2File(fop, object2JSON("created", entity.getCreated()) + ",");
            writeString2File(fop, object2JSON("adessoRoles", entity.getAdessoRoles()) + ",");
            writeString2File(fop, object2JSON("allFeature", entity.getFeatures()) + ",");
            writeString2File(fop, object2JSON("allInvolvedRole", entity.getInvolvedRoles()) + ",");
            writeString2File(fop, object2JSON("allLobs", entity.getLobs()) + ",");
            writeString2File(fop, object2JSON("allBranches", entity.getBranches()) + ",");
            writeString2File(fop, object2JSON("allProjectRole", entity.getProjectRoles()) + ",");
            writeString2File(fop, object2JSON("allTechnology", entity.getTechnologies()) + ",");
            writeString2File(fop, object2JSON("allTopic", entity.getTopics()) + ",");
            writeString2File(fop, object2JSON("allExtras", entity.getExtras()) + "");

            objectList = new ArrayList<Object>();
            for (Person p : allPerson.getEntityList()) {
                objectList.add(p);
            }
            writeListOfObjects2File(fop, "persons", objectList);

            objectList = new ArrayList<Object>();
            for (ElasticUser u : allElasticUser.getEntityList()) {
                objectList.add(u);
            }
            writeListOfObjects2File(fop, "elasticUsers", objectList);

            objectList = new ArrayList<Object>();
            for (ReferenceEntity r : allReferenceEntity.getEntityList()) {
                objectList.add(r);
            }
            writeListOfObjects2File(fop, "referenceEntities", objectList);

            writeString2File(fop, "}");
            fop.close();

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

    public String getJSONStringByName(String name, String string2check) {
        if (name == null || string2check == null) {
            return null;
        }
        String name2check = "\"" + name + "\": ";
        int nameLength = name2check.length();
        if (string2check.startsWith(name2check)) {
            String result = string2check.substring(nameLength, string2check.length());
            if (result.endsWith(",")) {
                result = result.substring(0, result.length() - 1);
            }
            return result;
        }
        return null;
    }

    public List<String> getJSONStringByBR(BufferedReader br) throws IOException {
        String line = br.readLine();
        String s;
        if (line == null) {
            return null;
        }
        List<String> sList = new ArrayList<String>();
        if (line.compareTo("]") == 0) {
            return sList;
        }
        if (line.endsWith(",")) {
            s = line.substring(0, line.length() - 1);
        } else {
            s = line;
        }
        sList.add(s);
        while (line != null) {
            line = br.readLine();
            if (line != null) {
                if (line.compareTo("]") == 0) {
                    break;
                }
                if (line.endsWith(",")) {
                    s = line.substring(0, line.length() - 1);
                } else {
                    s = line;
                }
                sList.add(s);
            }
        }
        //String s=sList.
        return sList;
    }

    public JavaDatabaseEntity loadFromFile(String filename) {
        if (filename == null) {
            return null;
        }
        return loadFromFile(new File(filename));
    }

    public JavaDatabaseEntity loadFromFile(File file) {
        BufferedReader br;
        JavaDatabaseEntity entity = null;
        List<String> sList;
        List<Person> personList;
        List<ElasticUser> userList;
        List<ReferenceEntity> refList;
        try {
            //br = new BufferedReader(new FileReader(file));
            br = Files.newBufferedReader(file.toPath(), Charset.forName("UTF-8"));
            //br = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(file))));
            //br = new BufferedReader(new InputStreamReader(file));

        } catch (Exception ex) {
            //ex.printStackTrace();
            System.out.println("File not found: " + file);
            return null;
        }
        try {
            String line = br.readLine();

            Date date;
            String string;
            entity = new JavaDatabaseEntity();
            while (line != null) {
                line = br.readLine();
//                if (line!=null)
//                    System.out.println("created="+getJSONStringByName("created", line));

                if (getJSONStringByName("created", line) != null) {

                    date = MyHelpMethods.getGson().fromJson(getJSONStringByName("created", line),
                            (new Date()).getClass());
                    entity.setCreated(date);
                }
                if (getJSONStringByName("versionDate", line) != null) {
                    date = MyHelpMethods.getGson().fromJson(getJSONStringByName("versionDate", line),
                            (new Date()).getClass());
                    entity.setVersionDate(date);
                }
                if (getJSONStringByName("version", line) != null) {
                    string = MyHelpMethods.getGson().fromJson(getJSONStringByName("version", line),
                            ("").getClass());
                    if (string.compareTo(version) != 0) {
                        System.err.println("Error! Database version missmatch!");
                        System.err.println("Database version: " + string);
                        System.err.println("Should be: " + version);
                        return null;
                    }
                    entity.setVersion(string);
                }
                if (getJSONStringByName("adessoRoles", line) != null) {
                    AdessoRole[] arr = MyHelpMethods.getGson().fromJson(
                            getJSONStringByName("adessoRoles", line),
                            AdessoRole[].class);
                    List<AdessoRole> list = new ArrayList<AdessoRole>();
                    for (int i = 0; i < arr.length; i++) {
                        list.add(arr[i]);
                    }
                    entity.setAdessoRoles(list);
//                    entity.setAdessoRoles(
//                        Arrays.asList(
//                            MyHelpMethods.getGson().fromJson(
//                                getJSONStringByName("adessoRoles", line),
//                                                    AdessoRole[].class))
//                    );
                }
                if (getJSONStringByName("allFeature", line) != null) {
                    Feature[] arr = MyHelpMethods.getGson().fromJson(
                            getJSONStringByName("allFeature", line),
                            Feature[].class);
                    List<Feature> list = new ArrayList<Feature>();
                    for (int i = 0; i < arr.length; i++) {
                        list.add(arr[i]);
                    }
                    entity.setFeatures(list);
//                    entity.setFeatures(
//                        Arrays.asList(
//                            MyHelpMethods.getGson().fromJson(
//                                getJSONStringByName("allFeature", line),
//                                                    Feature[].class))
//                    );
                }
                if (getJSONStringByName("allInvolvedRole", line) != null) {
                    InvolvedRole[] arr = MyHelpMethods.getGson().fromJson(
                            getJSONStringByName("allInvolvedRole", line),
                            InvolvedRole[].class);
                    List<InvolvedRole> list = new ArrayList<InvolvedRole>();
                    for (int i = 0; i < arr.length; i++) {
                        list.add(arr[i]);
                    }
                    entity.setInvolvedRoles(list);
//                    entity.setInvolvedRoles(
//                        Arrays.asList(
//                            MyHelpMethods.getGson().fromJson(
//                                getJSONStringByName("allInvolvedRole", line),
//                                                    InvolvedRole[].class))
//                    );
                }
                if (getJSONStringByName("allLobs", line) != null) {
                    LOB[] arr = MyHelpMethods.getGson().fromJson(
                            getJSONStringByName("allLobs", line),
                            LOB[].class);
                    List<LOB> list = new ArrayList<LOB>();
                    for (int i = 0; i < arr.length; i++) {
                        list.add(arr[i]);
                    }
                    entity.setLobs(list);
//                    entity.setLobs(
//                        Arrays.asList(
//                            MyHelpMethods.getGson().fromJson(
//                                getJSONStringByName("allLobs", line),
//                                                    LOB[].class))
//                    );
                }
                if (getJSONStringByName("allProjectRole", line) != null) {
                    ProjectRole[] arr = MyHelpMethods.getGson().fromJson(
                            getJSONStringByName("allProjectRole", line),
                            ProjectRole[].class);
                    List<ProjectRole> list = new ArrayList<ProjectRole>();
                    for (int i = 0; i < arr.length; i++) {
                        list.add(arr[i]);
                    }
                    entity.setProjectRoles(list);
//                    entity.setProjectRoles(
//                        Arrays.asList(
//                            MyHelpMethods.getGson().fromJson(
//                                getJSONStringByName("allProjectRole", line),
//                                                    ProjectRole[].class))
//                    );
                }
                if (getJSONStringByName("allTechnology", line) != null) {
                    Technology[] arr = MyHelpMethods.getGson().fromJson(
                            getJSONStringByName("allTechnology", line),
                            Technology[].class);
                    List<Technology> list = new ArrayList<Technology>();
                    for (int i = 0; i < arr.length; i++) {
                        list.add(arr[i]);
                    }
                    entity.setTechnologies(list);
//                    entity.setTechnologies(
//                        Arrays.asList(
//                            MyHelpMethods.getGson().fromJson(
//                                getJSONStringByName("allTechnology", line),
//                                                    Technology[].class))
//                    );
                }
                if (getJSONStringByName("allTopic", line) != null) {
                    Topic[] arr = MyHelpMethods.getGson().fromJson(
                            getJSONStringByName("allTopic", line),
                            Topic[].class);
                    List<Topic> list = new ArrayList<Topic>();
                    for (int i = 0; i < arr.length; i++) {
                        list.add(arr[i]);
                    }
                    entity.setTopics(list);
//                    entity.setTopics(
//                        Arrays.asList(
//                            MyHelpMethods.getGson().fromJson(
//                                getJSONStringByName("allTopic", line),
//                                                    Topic[].class))
//                    );
                }
                if (getJSONStringByName("allExtras", line) != null) {
                    Extras[] arr = MyHelpMethods.getGson().fromJson(
                            getJSONStringByName("allExtras", line),
                            Extras[].class);
                    List<Extras> list = new ArrayList<Extras>();
                    for (int i = 0; i < arr.length; i++) {
                        list.add(arr[i]);
                    }
                    entity.setExtras(list);
//                    entity.setExtras(
//                        Arrays.asList(
//                            MyHelpMethods.getGson().fromJson(
//                                getJSONStringByName("allExtras", line),
//                                                    Extras[].class))
//                    );
                }
                if (getJSONStringByName("allBranches", line) != null) {
                    Branch[] arr = MyHelpMethods.getGson().fromJson(
                            getJSONStringByName("allBranches", line),
                            Branch[].class);
                    List<Branch> list = new ArrayList<Branch>();
                    for (int i = 0; i < arr.length; i++) {
                        list.add(arr[i]);
                    }
                    entity.setBranches(list);
//                    entity.setBranches(
//                        Arrays.asList(
//                            MyHelpMethods.getGson().fromJson(
//                                getJSONStringByName("allBranches", line),
//                                                    Branch[].class))
//                    );
                }

                if (getJSONStringByName("persons", line) != null) {
                    sList = getJSONStringByBR(br);
                    if (sList != null) {
                        personList = new ArrayList<Person>();
                        for (String s : sList) {
                            personList.add(MyHelpMethods.getGson().fromJson(s, Person.class));
                        }
                        entity.setPersons(personList);
                    }

                }
                if (getJSONStringByName("elasticUsers", line) != null) {
                    sList = getJSONStringByBR(br);
                    if (sList != null) {
                        userList = new ArrayList<ElasticUser>();
                        for (String s : sList) {
                            userList.add(MyHelpMethods.getGson().fromJson(s, ElasticUser.class));
                        }
                        entity.setElasticUsers(userList);
                    }
                }
                if (getJSONStringByName("referenceEntities", line) != null) {
                    sList = getJSONStringByBR(br);
                    if (sList != null) {
                        refList = new ArrayList<ReferenceEntity>();
                        for (String s : sList) {
                            refList.add(MyHelpMethods.getGson().fromJson(s, ReferenceEntity.class));
                        }
                        entity.setReferenceEntities(refList);
                    }
                }
                //System.out.println(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return entity;
    }

    public JavaDatabaseEntity getJavaDatabaseEntity() {
        JavaDatabaseEntity entity = new JavaDatabaseEntity();
        entity.setAdessoRoles(allAdessoRole.getEntityList());
        entity.setBranches(allBranches.getEntityList());
        entity.setElasticUsers(allElasticUser.getEntityList());//?
        entity.setFeatures(allFeature.getEntityList());
        entity.setInvolvedRoles(allInvolvedRole.getEntityList());
        entity.setLobs(allLobs.getEntityList());
        entity.setPersons(allPerson.getEntityList());//?
        entity.setProjectRoles(allProjectRole.getEntityList());
        entity.setReferenceEntities(allReferenceEntity.getEntityList());//?
        entity.setTechnologies(allTechnology.getEntityList());
        entity.setTopics(allTopic.getEntityList());
        entity.setExtras(allExtras.getEntityList());
        entity.setVersion(version);
        entity.setVersionDate(versionDate);
        entity.setCreated(new Date());
        return entity;
    }

    public boolean saveDatabase(String filename) {
        JavaDatabaseEntity entity = new JavaDatabaseEntity();
        entity.setAdessoRoles(allAdessoRole.getEntityList());
        entity.setBranches(allBranches.getEntityList());
        entity.setElasticUsers(allElasticUser.getEntityList());//?
        entity.setFeatures(allFeature.getEntityList());
        entity.setInvolvedRoles(allInvolvedRole.getEntityList());
        entity.setLobs(allLobs.getEntityList());
        entity.setPersons(allPerson.getEntityList());//?
        entity.setProjectRoles(allProjectRole.getEntityList());
        entity.setReferenceEntities(allReferenceEntity.getEntityList());//?
        entity.setTechnologies(allTechnology.getEntityList());
        entity.setTopics(allTopic.getEntityList());
        entity.setExtras(allExtras.getEntityList());
        entity.setVersion(version);
        entity.setVersionDate(versionDate);
        entity.setCreated(new Date());
        //MyHelpMethods.save2File(entity, filename);
        save2File(entity, filename);
        return true;
    }

    private void setSingeltonsFromDatabaseEntity(JavaDatabaseEntity entity) {
        if (entity == null) {
            return;
        }
        version = entity.getVersion();
        versionDate = entity.getVersionDate();
        created = entity.getCreated();
        allAdessoRole.setEntityList(entity.getAdessoRoles());
        allBranches.setEntityList(entity.getBranches());
        allElasticUser.setEntityList(entity.getElasticUsers());
        allFeature.setEntityList(entity.getFeatures());
        allInvolvedRole.setEntityList(entity.getInvolvedRoles());
        allLobs.setEntityList(entity.getLobs());
        allPerson.setEntityList(entity.getPersons());
        allProjectRole.setEntityList(entity.getProjectRoles());
        allReferenceEntity.setEntityList(entity.getReferenceEntities());
        allTechnology.setEntityList(entity.getTechnologies());
        allTopic.setEntityList(entity.getTopics());
        allExtras.setEntityList(entity.getExtras());
    }

    public boolean loadDatabaseFromJsonInJar() {
        ResourceReader rr = new ResourceReader();
        File file = rr.getFile(ElasticConfig.getJsonDefaultDatabaseFilename());
        if (file == null) {
            return false;
        }
        JavaDatabaseEntity entity = loadFromFile(file);
        if (entity == null) {
            return false;
        }
        this.filename = ElasticConfig.getJsonDefaultDatabaseFilename() + "_" + MyHelpMethods.randomNumericString(2);
        setSingeltonsFromDatabaseEntity(entity);
        return true;
    }

    public boolean loadDatabase(String filename) {
        JavaDatabaseEntity entity = loadFromFile(filename);
        if (entity == null) {
            return false;
        }
        this.filename = filename;
//        version = entity.getVersion();
//        versionDate = entity.getVersionDate();
//        created = entity.getCreated();
//        allAdessoRole.setEntityList(entity.getAdessoRoles());
//        allBranches.setEntityList(entity.getBranches());
//        allElasticUser.setEntityList(entity.getElasticUsers());
//        allFeature.setEntityList(entity.getFeatures());
//        allInvolvedRole.setEntityList(entity.getInvolvedRoles());
//        allLobs.setEntityList(entity.getLobs());
//        allPerson.setEntityList(entity.getPersons());
//        allProjectRole.setEntityList(entity.getProjectRoles());
//        allReferenceEntity.setEntityList(entity.getReferenceEntities());
//        allTechnology.setEntityList(entity.getTechnologies());
//        allTopic.setEntityList(entity.getTopics());
//        allExtras.setEntityList(entity.getExtras());
        setSingeltonsFromDatabaseEntity(entity);
        return true;
    }

    public AllAdessoRole getAllAdessoRole() {
        return allAdessoRole;
    }

    public void setAllAdessoRole(AllAdessoRole allAdessoRole) {
        this.allAdessoRole = allAdessoRole;
    }

    public AllBranches getAllBranches() {
        return allBranches;
    }

    public void setAllBranches(AllBranches allBranches) {
        this.allBranches = allBranches;
    }

    public AllElasticUser getAllElasticUser() {
        return allElasticUser;
    }

    public void setAllElasticUser(AllElasticUser allElasticUser) {
        this.allElasticUser = allElasticUser;
    }

    public AllFeature getAllFeature() {
        return allFeature;
    }

    public void setAllFeature(AllFeature allFeature) {
        this.allFeature = allFeature;
    }

    public AllInvolvedRole getAllInvolvedRole() {
        return allInvolvedRole;
    }

    public void setAllInvolvedRole(AllInvolvedRole allInvolvedRole) {
        this.allInvolvedRole = allInvolvedRole;
    }

    public AllLobs getAllLobs() {
        return allLobs;
    }

    public void setAllLobs(AllLobs allLobs) {
        this.allLobs = allLobs;
    }

    public AllPerson getAllPerson() {
        return allPerson;
    }

    public void setAllPerson(AllPerson allPerson) {
        this.allPerson = allPerson;
    }

    public AllProjectRole getAllProjectRole() {
        return allProjectRole;
    }

    public void setAllProjectRole(AllProjectRole allProjectRole) {
        this.allProjectRole = allProjectRole;
    }

    public AllReferenceEntity getAllReferenceEntity() {
        return allReferenceEntity;
    }

    public void setAllReferenceEntity(AllReferenceEntity allReferenceEntity) {
        this.allReferenceEntity = allReferenceEntity;
    }

    public AllTechnology getAllTechnology() {
        return allTechnology;
    }

    public void setAllTechnology(AllTechnology allTechnology) {
        this.allTechnology = allTechnology;
    }

    public AllTopic getAllTopic() {
        return allTopic;
    }

    public void setAllTopic(AllTopic allTopic) {
        this.allTopic = allTopic;
    }

    public AllExtras getAllExtras() {
        return allExtras;
    }

    public void setAllExtras(AllExtras allExtras) {
        this.allExtras = allExtras;
    }

    public String getVersion() {
        return version;
    }

    public Date getVersionDate() {
        return versionDate;
    }

    public Date getCreated() {
        return created;
    }

    public BooleanReply isUsersListConsistent() {
        String header = "isUsersListConsistent: ";
        BooleanReply result = new BooleanReply();
        long count = 0, count2 = 0;
        if (allElasticUser == null) {
            result.setReason(header + "No list of elastics users was found");
            return result;
        }
        if (allPerson == null) {
            result.setReason(header + "No list of Persons was found");
            return result;
        }
        for (ElasticUser user : allElasticUser.getEntityList()) {
            if (user.getPerson() != null) {
                if (user.getPerson().getId() != null) {
                    if (allPerson.getEntityById(user.getPerson().getId()) == null) {
                        result.setReason(header + "user with id " + user.getId() + " point to the person with id " + user.getPerson().getId() + ". This person does not exists");
                        return result;
                    }
                }
                count++;
            }
        }
        result.setReason(header + allElasticUser.getEntityList().size() + " references was found. " + count + " users have contact info.");
        if (allReferenceEntity != null) {
            ElasticUser user;
            String error;
            for (ReferenceEntity entity : allReferenceEntity.getEntityList()) {
                user = entity.getApprover();
                if (user != null) {
                    if (user.getId() != null) {
                        if (allElasticUser.getEntityById(user.getId()) == null) {
                            error = result.getReason();
                            error += "\nreference with id" + entity.getId() + " point to the user with id " + user.getId() + ". This user does not exists";
                            result.setReason(error);
                            return result;
                        }
                        count2++;
                    }
                }
                user = entity.getEditor();
                if (user != null) {
                    if (user.getId() != null) {
                        if (allElasticUser.getEntityById(user.getId()) == null) {
                            error = result.getReason();
                            error += "\nreference with id" + entity.getId() + " point to the user with id " + user.getId() + ". This user does not exists";
                            result.setReason(error);
                            return result;
                        }
                        count2++;
                    }
                }
                user = entity.getOwner();
                if (user != null) {
                    if (user.getId() != null) {
                        if (allElasticUser.getEntityById(user.getId()) == null) {
                            error = result.getReason();
                            error += "\nreference with id" + entity.getId() + " point to the user with id " + user.getId() + ". This user does not exists";
                            result.setReason(error);
                            return result;
                        }
                        count2++;
                    }
                }
                user = entity.getqAssurance();
                if (user != null) {
                    if (user.getId() != null) {
                        if (allElasticUser.getEntityById(user.getId()) == null) {
                            error = result.getReason();
                            error += "\nreference with id" + entity.getId() + " point to the user with id " + user.getId() + ". This user does not exists";
                            result.setReason(error);
                            return result;
                        }
                        count2++;
                    }
                }
            }
        }
        String reason = result.getReason();
        reason += "\n" + allReferenceEntity.getEntityList().size() + " references was found. This references points " + count2 + " times to users.";
        result.setReason(reason);
        result.setExists(true);
        return result;
    }

    public BooleanReply isAdessoRoleConsistent() {
        String header = "isAdessoRoleConsistent: ";
        BooleanReply result = new BooleanReply();
        long count = 0;
        if (allPerson == null) {
            result.setReason(header + "No list of person was found");
            return result;
        }
        if (allAdessoRole == null) {
            result.setReason(header + "No list of adesso roles was found");
            return result;
        }
        for (Person entity : allPerson.getEntityList()) {
            if (entity.getRole() != null) {
                if (allAdessoRole.getEntityById(entity.getRole()) == null) {
                    result.setReason(header + "Person with id " + entity.getId() + " point to the role with id " + entity.getRole() + ". This role does not exists");
                    return result;
                }
                count++;
            }
        }
        result.setExists(true);
        result.setReason(header + allPerson.getEntityList().size() + " persons was found. " + count + " persons have role.");
        return result;

    }

    public BooleanReply isBranchConsistent() {
        String header = "isBranchConsistent: ";
        BooleanReply result = new BooleanReply();
        long count = 0;
        if (allReferenceEntity == null) {
            result.setReason(header + "No list of references was found");
            return result;
        }
        if (allBranches == null) {
            result.setReason(header + "No list of Branches was found");
            return result;
        }
        for (ReferenceEntity entity : allReferenceEntity.getEntityList()) {
            if (entity.getBranchList() != null) {
                for (String id : entity.getBranchList()) {
                    if (allBranches.getEntityById(id) == null) {
                        result.setReason(header + "Reference with id " + entity.getId() + " point to the branch with id " + id + ". This branch does not exists");
                        return result;
                    }
                    count++;
                }
            }
        }
        result.setExists(true);
        result.setReason(header + allReferenceEntity.getEntityList().size() + " references was found. " + count + " references have branches.");
        return result;
    }

    public BooleanReply isLobConsistent() {
        String header = "isLobConsistent: ";
        BooleanReply result = new BooleanReply();
        long count = 0;
        if (allReferenceEntity == null) {
            result.setReason(header + "No list of references was found");
            return result;
        }
        if (allLobs == null) {
            result.setReason(header + "No list of LOBS was found");
            return result;
        }
        for (ReferenceEntity entity : allReferenceEntity.getEntityList()) {
            String id = entity.getLob();
            if (id != null) {
                if (allLobs.getEntityById(id) == null) {
                    result.setReason(header + "Reference with id " + entity.getId() + " point to the LOB with id " + id + ". This LOB does not exists");
                    return result;
                }
                count++;
            }
        }
        result.setExists(true);
        result.setReason(header + allReferenceEntity.getEntityList().size() + " reference was found. They are pointing " + count + " times to LOBs.");
        return result;

    }

    public BooleanReply isReferencedReferenceConsistent() {
        String header = "isReferencedReferenceConsistent: ";
        BooleanReply result = new BooleanReply();
        long count = 0;
        if (allReferenceEntity == null) {
            result.setReason(header + "No list of person was found");
            return result;
        }
        for (ReferenceEntity entity : allReferenceEntity.getEntityList()) {
            if (entity.getRefIDList() != null) {
                for (String id : entity.getRefIDList()) {
                    if (id != null) {
                        if (allReferenceEntity.getEntityById(id) == null) {
                            result.setReason(header + "Reference with id " + entity.getId() + " point to the reference with id " + id + ". This reference does not exists");
                            return result;
                        }
                        count++;
                    }
                }
            }
        }
        result.setExists(true);
        result.setReason(header + allReferenceEntity.getEntityList().size() + " reference was found. They are pointing " + count + " times to other references.");
        return result;

    }

    public BooleanReply isInvolvedRoleConsistent() {
        String header = "isInvolvedRoleConsistent: ";
        BooleanReply result = new BooleanReply();
        long count = 0;
        if (allReferenceEntity == null) {
            result.setReason(header + "No list of references was found");
            return result;
        }
        if (allInvolvedRole == null) {
            result.setReason(header + "No list of involved roles was found");
            return result;
        }
        for (ReferenceEntity entity : allReferenceEntity.getEntityList()) {
            if (entity.getRoles() != null) {
                for (String id : entity.getRoles()) {
                    if (id != null) {
                        if (allInvolvedRole.getEntityById(id) == null) {
                            result.setReason(header + "Reference with id " + entity.getId() + " point to the involved role with id " + id + ". This role does not exists");
                            return result;
                        }
                        count++;
                    }
                }
            }
        }
        result.setExists(true);
        result.setReason(header + allReferenceEntity.getEntityList().size() + " reference was found. They are pointing " + count + " times to involved roles.");
        return result;

    }

    public BooleanReply isPersonListInReferencesConsistent() {
        String header = "isPersonListInReferencesConsistent: ";
        BooleanReply result = new BooleanReply();
        long count = 0, count2 = 0;
        String id, error;
        if (allReferenceEntity == null) {
            result.setReason(header + "No list of person was found");
            return result;
        }
        if (allPerson == null) {
            result.setReason(header + "No list of Persons was found");
            return result;
        }
        for (ReferenceEntity entity : allReferenceEntity.getEntityList()) {
            if (entity.getTeammembers() != null) {
                for (Person person : entity.getTeammembers()) {
                    id = person.getId();
                    if (id != null) {
                        if (allPerson.getEntityById(id) == null) {
                            result.setReason(header + "Reference with id " + entity.getId() + " point to the teammember (person) with id " + id + ". This person does not exists");
                            return result;
                        }
                        count++;
                    }
                }
            }
            Person person = entity.getAdessoPartner();
            if (person != null) {
                id = person.getId();
                if (id != null) {
                    if (allPerson.getEntityById(id) == null) {
                        error = result.getReason() + " Reference with id " + entity.getId() + " point to the adesso partners (person) with id " + id + ". This person does not exists";
                        result.setReason(error);
                        return result;
                    }
                    count++;
                }
            }
            person = entity.getClientData();
            if (person != null) {
                id = person.getId();
                if (id != null) {
                    if (allPerson.getEntityById(id) == null) {
                        error = result.getReason() + " Reference with id " + entity.getId() + " point to the client info (person) with id " + id + ". This person does not exists";
                        result.setReason(error);
                        return result;
                    }
                    count++;
                }
            }
            id = entity.getDeputyName();
            if (id != null) {
                if (allPerson.getEntityById(id) == null) {
                    error = result.getReason() + " Reference with id " + entity.getId() + " point to the adesso 'vertreter' (person) with id " + id + ". This person does not exists";
                    result.setReason(error);
                    return result;
                }
                count++;
            }

        }
        result.setExists(true);
        result.setReason(header + allReferenceEntity.getEntityList().size() + " reference was found. They are pointing " + count + " times to teammembers, client info, adesso partners, and adesso 'vertreter' (persons)");
        return result;
    }

    public BooleanReply isTechnicsListConsistent() {
        String header = "isTechnicsListConsistent: ";
        BooleanReply result = new BooleanReply();
        long count = 0;
        if (allReferenceEntity == null) {
            result.setReason(header + "No list of references was found");
            return result;
        }
        if (allTechnology == null) {
            result.setReason(header + "No list of technologies was found");
            return result;
        }
        for (ReferenceEntity entity : allReferenceEntity.getEntityList()) {
            if (entity.getTechnics() != null) {
                for (String id : entity.getTechnics()) {
                    if (id != null) {
                        if (allTechnology.getEntityById(id) == null) {
                            result.setReason(header + "Reference with id " + entity.getId() + " point to the technology with id " + id + ". This technology does not exists");
                            return result;
                        }
                        count++;
                    }
                }
            }
        }
        result.setExists(true);
        result.setReason(header + allReferenceEntity.getEntityList().size() + " reference was found. They are pointing " + count + " times to technologies.");
        return result;

    }

    public BooleanReply isTopicListConsistent() {
        String header = "isTopicListConsistent: ";
        BooleanReply result = new BooleanReply();
        long count = 0;
        if (allReferenceEntity == null) {
            result.setReason(header + "No list of references was found");
            return result;
        }
        if (allTopic == null) {
            result.setReason(header + "No list of topics was found");
            return result;
        }
        for (ReferenceEntity entity : allReferenceEntity.getEntityList()) {
            if (entity.getTopics() != null) {
                for (String id : entity.getTopics()) {
                    if (id != null) {
                        if (allTopic.getEntityById(id) == null) {
                            result.setReason(header + "Reference with id " + entity.getId() + " point to the topic with id " + id + ". This topic does not exists");
                            return result;
                        }
                        count++;
                    }
                }
            }
        }
        result.setExists(true);
        result.setReason(header + allReferenceEntity.getEntityList().size() + " reference was found. They are pointing " + count + " times to topics.");
        return result;

    }

    public BooleanReply isExtrasListConsistent() {
        String header = "isExtrasListConsistent: ";
        BooleanReply result = new BooleanReply();
        long count = 0;
        if (allReferenceEntity == null) {
            result.setReason(header + "No list of references was found");
            return result;
        }
        if (allExtras == null) {
            result.setReason(header + "No list of extras was found");
            return result;
        }
        for (ReferenceEntity entity : allReferenceEntity.getEntityList()) {
            if (entity.getExtras() != null) {
                for (String id : entity.getExtras()) {
                    if (id != null) {
                        if (allExtras.getEntityById(id) == null) {
                            result.setReason(header + "Reference with id " + entity.getId() + " point to the extras with id " + id + ". This extras does not exists");
                            return result;
                        }
                        count++;
                    }
                }
            }
        }
        result.setExists(true);
        result.setReason(header + allReferenceEntity.getEntityList().size() + " reference was found. They are pointing " + count + " times to extras.");
        return result;

    }

    public BooleanReply isProjectRoleConsistent() {
        String id, header = "isProjectRoleConsistent: ";
        BooleanReply result = new BooleanReply();
        long count = 0;
        if (allReferenceEntity == null) {
            result.setReason(header + "No list of references was found");
            return result;
        }
        if (allProjectRole == null) {
            result.setReason(header + "No list of project roles was found");
            return result;
        }
        for (ReferenceEntity entity : allReferenceEntity.getEntityList()) {
            id = entity.getAdessoPartnerRole();
            if (id != null) {
                if (allProjectRole.getEntityById(id) == null) {
                    result.setReason(header + "Reference with id " + entity.getId() + " point to the project role with id " + id + ". This project role does not exists");
                    return result;
                }
                count++;
            }
            id = entity.getDeputyRole();
            if (id != null) {
                if (allProjectRole.getEntityById(id) == null) {
                    result.setReason(header + "Reference with id " + entity.getId() + " point to the deputy role with id " + id + ". This project role does not exists");
                    return result;
                }
                count++;
            }
        }
        result.setExists(true);
        result.setReason(header + allReferenceEntity.getEntityList().size() + " reference was found. They are pointing " + count + " times to project roles for adesso partner and adesso 'vertreter'.");
        return result;

    }

    public List<BooleanReply> testDatabaseConsistency() {
        List<BooleanReply> result = new ArrayList<BooleanReply>();
        result.add(isAdessoRoleConsistent());
        result.add(isBranchConsistent());
        result.add(isUsersListConsistent());
        result.add(isLobConsistent());
        result.add(isReferencedReferenceConsistent());
        result.add(isInvolvedRoleConsistent());
        result.add(isPersonListInReferencesConsistent());
        result.add(isTechnicsListConsistent());
        result.add(isTopicListConsistent());
        result.add(isExtrasListConsistent());
        result.add(isProjectRoleConsistent());
        return result;
    }

    public BooleanReply isDatabaseConsistent() {
        BooleanReply result = new BooleanReply();
        List<BooleanReply> testresults = testDatabaseConsistency();
        result.setReason(MyHelpMethods.object2PrettyString(testresults));
        result.setExists(true);
        for (BooleanReply reply : testresults) {
            if (!reply.isExists()) {
                result.setExists(false);
            }
        }
        return result;
    }

    public ElasticUser findFirstUserByPersonId(String id) {
        if (allElasticUser == null || id == null) {
            return null;
        }
        List<ElasticUser> userList = allElasticUser.getEntityList();
        if (userList == null) {
            return null;
        }
        for (ElasticUser user : userList) {
            if (user != null) {
                if (user.getPerson() != null) {
                    if (user.getPerson().getId() != null) {
                        if (user.getPerson().getId().matches(id)) {
                            return user;
                        }
                    }
                }
            }
        }
        return null;
    }
}
