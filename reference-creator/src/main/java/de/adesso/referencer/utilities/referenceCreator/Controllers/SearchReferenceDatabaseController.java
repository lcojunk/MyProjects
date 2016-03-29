/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Controllers;

import de.adesso.referencer.utilities.referenceCreator.ElasticConfig;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Person;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ReferenceEntity;
import de.adesso.referencer.utilities.referenceCreator.Entities.search.*;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import de.adesso.referencer.utilities.referenceCreator.database.JavaDatabaseEntity;
import de.adesso.referencer.utilities.referenceCreator.database.ReferenceJavaDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class SearchReferenceDatabaseController {

    private SearchReferenceDatabaseEntity database = new SearchReferenceDatabaseEntity();
    private int idLength = 16;
    private List<UserAndPerson> usersAndPersons = new ArrayList<>();

    public class UserAndPerson {

        private User user;
        private Person person;

        public UserAndPerson() {
        }

        public UserAndPerson(User user, Person person) {
            this.user = user;
            this.person = person;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }

    }

    public SearchReferenceDatabaseController() {
    }

    public SearchReferenceDatabaseController(JavaDatabaseEntity javaDatabase) {
        setDatabase(javaDatabase);
    }

    public SearchReferenceDatabaseEntity getDatabase() {
        return database;
    }

    public void setDatabase(SearchReferenceDatabaseEntity database) {
        this.database = database;
    }

    public AdessoRole findAdessoRoleById(String id) {
        if (id == null) {
            return null;
        }
        for (AdessoRole entity : database.getAllAdessoRoles()) {
            if (entity != null && entity.getId() != null && entity.getId().matches(id)) {
                return entity;
            }
        }
        return null;
    }

    public boolean createOrUpdate(de.adesso.referencer.utilities.referenceCreator.Entities.Raw.AdessoRole entity) {
        if (entity == null || entity.getId() == null) {
            return false;
        }
        AdessoRole newEntity = findAdessoRoleById(entity.getId());
        if (newEntity == null) {
            newEntity = new AdessoRole(MyHelpMethods.randomNumericString(idLength),
                    entity.getName(),
                    entity.getDescription());
            database.getAllAdessoRoles().add(newEntity);
        } else {
            newEntity.setName(entity.getName());
            newEntity.setDescription(entity.getDescription());
        }
        return true;
    }

    public User findUserById(String id) {
        if (id == null) {
            return null;
        }
        for (User entity : database.getAllUsers()) {
            if (entity != null && entity.getId() != null && entity.getId().matches(id)) {
                return entity;
            }
        }
        return null;
    }

    public boolean updateUser(de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ElasticUser elasticUser, User user) {
        if (elasticUser == null) {
            return false;
        }
        if (user == null) {
            user = new User();
        }
        if (elasticUser.getId() == null) {
            user.setId(MyHelpMethods.randomNumericString(idLength));
        } else {
            user.setId(elasticUser.getId());
        }
        user.setUsername(elasticUser.getUsername());
        user.setPass(elasticUser.getPass());
        if (elasticUser.getGroup() == null) {
            user.setGroup(null);
        } else if (elasticUser.getGroup().matches(ElasticConfig.getReferenceGroupNames()[0])) {
            user.setGroup(User.UserGroup.BASIC);
        } else if (elasticUser.getGroup().matches(ElasticConfig.getReferenceGroupNames()[1])) {
            user.setGroup(User.UserGroup.ADMIN);
        } else if (elasticUser.getGroup().matches(ElasticConfig.getReferenceGroupNames()[2])) {
            user.setGroup(User.UserGroup.EDITOR);
        } else if (elasticUser.getGroup().matches(ElasticConfig.getReferenceGroupNames()[3])) {
            user.setGroup(User.UserGroup.BASIC);
        } else {
            user.setGroup(User.UserGroup.BASIC);
        }
        if (elasticUser.getRole() != null) {
            user.setRole(findAdessoRoleById(elasticUser.getRole()));
        }
        if (elasticUser.getPerson() != null) {
            user.setFirst_name(elasticUser.getPerson().getForename());
            user.setLast_name(elasticUser.getPerson().getName());
            if (elasticUser.getPerson().getTel() != null) {
                String tels = "";
                for (String tel : elasticUser.getPerson().getTel()) {
                    if (tel != null) {
                        tels += " " + tel + ";";
                    }
                }
                tels = tels.substring(0, tels.length() - 1);
                user.setTel(tels);
            }
            if (elasticUser.getPerson().getEmail() != null) {
                String emails = "";
                for (String email : elasticUser.getPerson().getEmail()) {
                    if (email != null) {
                        emails += " " + email + ";";
                    }
                }
                emails = emails.substring(0, emails.length() - 1);
                user.setEmail(emails);
            }
        }

        return true;
    }

    public User createUser(de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ElasticUser entity) {
        if (entity == null) {
            return null;
        }
        User result = new User();
        if (!updateUser(entity, result)) {
            return null;
        }
        return result;
    }

    public boolean createOrUpdate(de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ElasticUser entity) {
        if (entity == null || entity.getId() == null) {
            return false;
        }
        User newEntity = findUserById(entity.getId());
        if (newEntity == null) {
            newEntity = createUser(entity);
            database.getAllUsers().add(newEntity);
        } else {
            return updateUser(entity, newEntity);
        }
        return true;
    }

    public User person2user(de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Person person) {
        if (person == null) {
            return null;
        }
        User user = new User();
        user.setFirst_name(person.getForename());
        user.setLast_name(person.getName());
        if (person.getTel() != null) {
            String tels = "";
            for (String tel : person.getTel()) {
                if (tel != null) {
                    tels += " " + tel + ";";
                }
            }
            tels = tels.substring(0, tels.length() - 1);
            user.setTel(tels);
        }
        if (person.getEmail() != null) {
            String emails = "";
            for (String email : person.getEmail()) {
                if (email != null) {
                    emails += " " + email + ";";
                }
            }
            emails = emails.substring(0, emails.length() - 1);
            user.setEmail(emails);
        }

        return user;
    }

    private boolean existInList(String id, List<Person> personList) {
        if (personList == null || personList.size() == 0 || id == null) {
            return false;
        }
        for (Person testPerson : personList) {
            if (testPerson.getId() != null) {
                if (testPerson.getId().matches(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean existInList(Person person, List<Person> personList) {
        if (person == null) {
            return false;
        }
        return existInList(person.getId(), personList);
    }

    private List<Person> getAllUniquePersonsFromReferences(ReferenceJavaDatabase referenceJavaDatabase) {
        if (referenceJavaDatabase == null) {
            return null;
        }
        List<ReferenceEntity> referenceEntities = referenceJavaDatabase.getAllReferenceEntity().getEntityList();
        if (referenceEntities == null || referenceEntities.size() == 0) {
            return null;
        }
        List<de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Person> result = new ArrayList<>();
        Person person;
        for (ReferenceEntity referenceEntity : referenceEntities) {
            if (referenceEntity != null) {
                if (referenceEntity.getAdessoPartner() != null && !existInList(referenceEntity.getAdessoPartner(), result)) {

                    result.add(referenceEntity.getAdessoPartner());
                }
                if (referenceEntity.getDeputyName() != null
                        && !existInList(referenceEntity.getDeputyName(), result)) {
                    person = referenceJavaDatabase.getAllPerson().getEntityById(referenceEntity.getDeputyName());
                    if (person != null) {
                        result.add(person);
                    }
                }
            }
        }

        return result;
    }

    public List<Person> getAllPersonsThatAreNotUsers(ReferenceJavaDatabase referenceJavaDatabase) {
        if (referenceJavaDatabase == null) {
            return null;
        }
        List<Person> persons = getAllUniquePersonsFromReferences(referenceJavaDatabase);
        if (persons == null) {
            return null;
        }
        List<Person> result = new ArrayList<>();
        for (Person person : persons) {
            if (person != null) {
                if (referenceJavaDatabase.findFirstUserByPersonId(person.getId()) == null) {
                    result.add(person);
                }
            }
        }
        return result;
    }

    public boolean createUsersFromPersonsThatWasNotUsers(ReferenceJavaDatabase referenceJavaDatabase) {
        if (referenceJavaDatabase == null) {
            return false;
        }
        List<Person> persons = getAllPersonsThatAreNotUsers(referenceJavaDatabase);
        if (persons == null || persons.size() == 0) {
            return false;
        }
        User user;
        AdessoRole adessoRole;
        for (Person person : persons) {
            user = person2user(person);
            user.setId(MyHelpMethods.randomNumericString(idLength));
            user.setUsername(person.getForename() + "." + person.getName());
            user.setGroup(User.UserGroup.BASIC);
            if (person.getRole() != null && referenceJavaDatabase.getAllAdessoRole().getEntityById(person.getRole()) != null) {
                createOrUpdate(referenceJavaDatabase.getAllAdessoRole().getEntityById(person.getRole()));
                user.setRole(findAdessoRoleById(person.getRole()));
            }
            usersAndPersons.add(new UserAndPerson(user, person));
        }
        return true;
    }

    public boolean setDatabase(JavaDatabaseEntity javaDatabase) {
        for (de.adesso.referencer.utilities.referenceCreator.Entities.Raw.AdessoRole entity : javaDatabase.getAdessoRoles()) {
            createOrUpdate(entity);
        }
        for (de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ElasticUser entity : javaDatabase.getElasticUsers()) {
            createOrUpdate(entity);
        }
        return true;
    }

    public User findUserByPerson(Person person) {
        if (person == null || person.getId() == null || usersAndPersons == null || usersAndPersons.size() == 0) {
            return null;
        }
        for (UserAndPerson userAndPerson : usersAndPersons) {
            if (userAndPerson != null && userAndPerson.getPerson() != null && userAndPerson.getPerson().getId() != null) {
                if (person.getId().matches(userAndPerson.getPerson().getId())) {
                    return userAndPerson.getUser();
                }
            }
        }
        return null;
    }

}
