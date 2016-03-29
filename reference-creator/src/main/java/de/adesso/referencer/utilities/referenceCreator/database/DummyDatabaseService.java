/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

import de.adesso.referencer.utilities.referenceCreator.ElasticConfig;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.*;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 * @author odzhara-ongom
 */
public class DummyDatabaseService {

    private ReferenceJavaDatabase db;
    private ReferencesTestValues referencesTestValues;
    private Random random = new Random();

    private List<Person> adessoPersons = new ArrayList<>();
    private List<Person> people = new ArrayList<>();
    private List<ElasticUser> users = new ArrayList<>();
    private List<ElasticUser> admins = new ArrayList<>();
    private List<ElasticUser> editorial = new ArrayList<>();
    private List<ReferenceEntity> references = new ArrayList<>();
    private List<AdessoClient> adessoClients = new ArrayList<>();

    private DummyDatabaseService() {
    }

    public DummyDatabaseService(ReferenceJavaDatabase db) {
        this.db = db;
        referencesTestValues = new ReferencesTestValues();
        adessoClients = referencesTestValues.getClientsList();
    }

    public void createDummyPeoples(int number) {
        Person person;
        for (int i = 0; i < number; i++) {
            person = referencesTestValues.createRandomPerson();
            db.getAllPerson().addEntity(person);
            people.add(person);
        }
    }

    private ElasticUser createUserFromNewPerson(Person person) {
        ElasticUser user = new ElasticUser();
        user.setUsername(person.getForename() + "." + person.getName());
        user.setPerson(person);
        user.setGroup(ElasticConfig.getReferenceGroupNames()[0]);
        return user;
    }

    public void createDummyAdessoPeoples(int number) {
        Person person;
        ElasticUser user;
        for (int i = 0; i < number; i++) {
            person = referencesTestValues.createRandomAdessoPerson();
            db.getAllPerson().addEntity(person);
            people.add(person);
            user = createUserFromNewPerson(person);
            user.setGroup(ElasticConfig.getReferenceGroupNames()[i % ElasticConfig.getReferenceGroupNames().length]);
            db.getAllElasticUser().addEntity(user);
            users.add(user);
            if (user.getGroup().matches(ElasticConfig.getReferenceGroupNames()[1])) {
                admins.add(user);
            }
            if (user.getGroup().matches(ElasticConfig.getReferenceGroupNames()[2])) {
                editorial.add(user);
            }
        }
    }

    private LOB getRandomLob() {
        int randomInt = random.nextInt(db.getAllLobs().getEntityList().size());
        return (db.getAllLobs().getEntityList().get(randomInt));
    }

    private Branch getRandomBranch() {
        int randomInt = random.nextInt(db.getAllBranches().getEntityList().size());
        return (db.getAllBranches().getEntityList().get(randomInt));
    }

    private Technology getRandomTechnology() {
        int randomInt = random.nextInt(db.getAllTechnology().getEntityList().size());
        return (db.getAllTechnology().getEntityList().get(randomInt));
    }

    public void createDummyReferences(int number) {
        ReferenceEntity reference;
        Date date, startDate, endDate, startService, endService, changedDate, releasedDate;
        AdessoClient adessoClient;
        AccessStamp accessStamp;
        for (int i = 0; i < number; i++) {
            reference = new ReferenceEntity();
            adessoClient = adessoClients.get(random.nextInt(adessoClients.size()));
            reference.setClientname(adessoClient.getName());
            reference.setClientProfil(adessoClient.getDescription());
            reference.setProjectname(referencesTestValues.getRandomProjectName());
            reference.setLob(getRandomLob().getId());
            reference.setBranchList(new ArrayList<>());
            for (int j = 0; j < random.nextInt(5) + 1; j++) {
                reference.getBranchList().add(getRandomBranch().getId());
            }
            reference.setTechnics(new ArrayList<>());
            for (int j = 0; j < random.nextInt(5) + 1; j++) {
                reference.getTechnics().add(getRandomTechnology().getId());
            }
            reference.setPtAdesso(random.nextInt(10) * 10);
            reference.setPtTotalProject(reference.getPtAdesso() + random.nextInt(50));
            reference.setCostAdesso(random.nextInt(1000000000) / 100.0);
            reference.setCostTotal(reference.getCostAdesso() + random.nextInt(1000) / 100.0);
            date = new Date();
            date = MyHelpMethods.addMonthToDate(date, random.nextInt(6) - 3);
            startDate = MyHelpMethods.addYearsToDate(date, random.nextInt(number) % 10 - 8);
            endDate = MyHelpMethods.addYearsToDate(startDate, random.nextInt(5));
            startService = MyHelpMethods.addYearsToDate(startDate, random.nextInt(3));
            endService = MyHelpMethods.addYearsToDate(startService, random.nextInt(3));
            changedDate = MyHelpMethods.addMonthToDate(endDate, random.nextInt(3));
            releasedDate = MyHelpMethods.addMonthToDate(changedDate, random.nextInt(2));
            accessStamp = new AccessStamp();
            accessStamp.date = changedDate;
            reference.setChanged(accessStamp);
            accessStamp = new AccessStamp();
            accessStamp.date = releasedDate;
            reference.setReleased(accessStamp);
            reference.setTimeInterval(new TimeInterval(startDate, endDate));
            reference.setServiceTime(new TimeInterval(startService, endService));
            reference.setPreciseDescription(referencesTestValues.getLoremIpsum());
            reference.setProjectBackground(referencesTestValues.getLoremIpsum());
            reference.setProjectSolution(referencesTestValues.getLoremIpsum());
            reference.setProjectResults(referencesTestValues.getLoremIpsum());
            reference.setFreigabestatus(i % ElasticConfig.getReferenceStatusNames().length);
            db.getAllReferenceEntity().addEntity(reference);
            references.add(reference);
        }
    }

    public List<Person> getPeople() {
        return people;
    }

    public List<Person> getAdessoPersons() {
        return adessoPersons;
    }

    public List<ElasticUser> getUsers() {
        return users;
    }

    public List<ElasticUser> getAdmins() {
        return admins;
    }

    public List<ElasticUser> getEditorial() {
        return editorial;
    }

    public List<ReferenceEntity> getReferences() {
        return references;
    }

}
