/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Entities.search;

import de.adesso.referencer.utilities.referenceCreator.Controllers.SearchReferenceDatabaseController;
import de.adesso.referencer.utilities.referenceCreator.ElasticConfig;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ElasticUser;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Person;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.PersonAddress;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ReferenceEntity;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.TimeInterval;
import de.adesso.referencer.utilities.referenceCreator.database.ReferenceJavaDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class SearchRefefenceEntityFactory {

    private ReferenceJavaDatabase database;
    private SearchReferenceDatabaseController searchReferenceDatabaseController;

    public ReferenceJavaDatabase getDatabase() {
        return database;
    }

    private void loadSearchReferenceDatabaseController(ReferenceJavaDatabase database) {
        searchReferenceDatabaseController = new SearchReferenceDatabaseController(database.getJavaDatabaseEntity());
        searchReferenceDatabaseController.createUsersFromPersonsThatWasNotUsers(database);
    }

    public void setDatabase(ReferenceJavaDatabase database) {
        this.database = database;
        loadSearchReferenceDatabaseController(database);
    }

    public SearchRefefenceEntityFactory() {
    }

    public SearchRefefenceEntityFactory(ReferenceJavaDatabase database) {
        this.database = database;
        loadSearchReferenceDatabaseController(database);
    }

    public Lob createSearchLobEntity(String id) {
        if (database == null) {
            return null;
        }
        if (id == null) {
            return null;
        }
        if (database.getAllLobs() == null) {
            return null;
        }
        de.adesso.referencer.utilities.referenceCreator.Entities.Raw.LOB entity
                = database.getAllLobs().getEntityById(id);
        if (entity == null) {
            return null;
        }
        Lob result = new Lob();
        result.setId(entity.getId());
        result.setName(entity.getName());
        result.setDescription(entity.getDescription());
        return result;
    }

    public List<Branch> createListOfSearchBranchEntities(
            List<String> idList) {
        if (database == null || database.getAllBranches() == null || idList == null) {
            return null;
        }
        List<Branch> result = new ArrayList<Branch>();
        Branch searchEntity;
        de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Branch dbEntity;
        for (String id : idList) {
            dbEntity = database.getAllBranches().getEntityById(id);
            if (dbEntity != null) {
                searchEntity = new Branch();
                searchEntity.setId(dbEntity.getId());
                searchEntity.setName(dbEntity.getName());
                searchEntity.setDescription(dbEntity.getDescription());
                result.add(searchEntity);
            }
        }
        return result;
    }

    private Reference.ReleaseStatus getReleaseStatusFromReferenceEntity(ReferenceEntity entity) {
        if (entity == null) {
            return null;
        }
        switch (entity.getFreigabestatus()) {
            // see ElasticConfig.referenceStatusNames
            case 0:
                return Reference.ReleaseStatus.NOT_RELEASED;
            case 1:
                return Reference.ReleaseStatus.INDIVIDUALLY_RELEASED;
            case 2:
                return Reference.ReleaseStatus.ANONYMOUSLY_RELEASED;
            case 3:
                return Reference.ReleaseStatus.FULLY_RELEASED;
        }
        return null;
    }

    private User.UserGroup getUserGroupFromDBUser(ElasticUser eUser) {
        if (eUser == null) {
            return null;
        }
        String userGroup = eUser.getGroup();
        if (userGroup == null) {
            return null;
        }
        for (int i = 0; i < ElasticConfig.getReferenceGroupNames().length; i++) {
            if (userGroup.matches(ElasticConfig.getReferenceGroupNames()[i])) {
                switch (i) {
                    case 0:
                        return User.UserGroup.BASIC;
                    case 1:
                        return User.UserGroup.ADMIN;
                    case 2:
                        return User.UserGroup.EDITOR;
                    case 3:
                        return User.UserGroup.BASIC;
                }
            }
        }
        return null;
    }

    private AdessoRole createAdessoRole(String id) {
        if (id == null || database == null || database.getAllAdessoRole() == null) {
            return null;
        }
        de.adesso.referencer.utilities.referenceCreator.Entities.Raw.AdessoRole role = database.getAllAdessoRole().getEntityById(id);
        if (role == null) {
            return null;
        }
        AdessoRole result = new AdessoRole();
        result.setId(role.getId());
        result.setName(role.getName());
        result.setDescription(role.getDescription());
        return result;
    }

    private AdessoRole getAdessoRoleFromDBUser(ElasticUser eUser) {
        if (eUser == null) {
            return null;
        }
        AdessoRole result = createAdessoRole(eUser.getRole());
        return result;
    }

    private User getUserFromJavaDBUser(ElasticUser eUser) {
        if (eUser == null) {
            return null;
        }
        User user = new User();
        user.setId(eUser.getId());
        user.setUsername(eUser.getUsername());
        user.setPass(eUser.getPass());
        user.setGroup(getUserGroupFromDBUser(eUser));
        user.setRole(getAdessoRoleFromDBUser(eUser));
        if (eUser.getPerson() != null) {
            user.setFirst_name(eUser.getPerson().getForename());
            user.setLast_name(eUser.getPerson().getName());
//            if (eUser.getPerson().getEmail()!=null && eUser.getPerson().getEmail().size()>0) {
//                user.setEmail("");
//                for (String email:eUser.getPerson().getEmail()) {
//                    user.setEmail(user.getEmail()+" "+email);
//                }
//            }
//            if (eUser.getPerson().getTel()!=null && eUser.getPerson().getTel().size()>0) {
//                user.setTel("");
//                for (String tel:eUser.getPerson().getTel()) {
//                    user.setTel(user.getTel()+" "+tel);
//                }
//            }
            user.setEmail(listOfString2String(eUser.getPerson().getEmail()));
            user.setTel(listOfString2String(eUser.getPerson().getTel()));
        }
        return user;
    }

    private AccessStamp getAccessStampFromReferenceEnity(de.adesso.referencer.utilities.referenceCreator.Entities.Raw.AccessStamp accessStamp) {
        if (accessStamp == null) {
            return null;
        }
        AccessStamp result = new AccessStamp();
        result.setDate(accessStamp.date);
        result.setUser(getUserFromJavaDBUser(accessStamp.eUser));
        return result;
    }

    private List<InvolvedRole> createInvolvedRoles(List<String> rolesId) {
        if (rolesId == null || database == null || database.getAllInvolvedRole() == null) {
            return null;
        }
        List<InvolvedRole> result = new ArrayList<>();
        de.adesso.referencer.utilities.referenceCreator.Entities.Raw.InvolvedRole dbEntity;
        InvolvedRole searchEntity;
        for (String id : rolesId) {
            dbEntity = database.getAllInvolvedRole().getEntityById(id);
            if (dbEntity != null) {
                searchEntity = new InvolvedRole();
                searchEntity.setId(dbEntity.getId());
                searchEntity.setName(dbEntity.getName());
                searchEntity.setDescription(dbEntity.getDescription());
                result.add(searchEntity);
            }
        }
        return result;
    }

    private List<Technology> createTechnologies(List<String> idList) {
        if (idList == null || database == null || database.getAllTechnology() == null) {
            return null;
        }
        List<Technology> result = new ArrayList<>();
        de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Technology dbEntity;
        Technology searchEntity;
        for (String id : idList) {
            dbEntity = database.getAllTechnology().getEntityById(id);
            if (dbEntity != null) {
                searchEntity = new Technology();
                searchEntity.setId(dbEntity.getId());
                searchEntity.setName(dbEntity.getName());
                searchEntity.setDescription(dbEntity.getDescription());
                result.add(searchEntity);
            }
        }
        return result;
    }

    private List<Focus> createFocuses(List<String> idList) {
        if (idList == null || database == null || database.getAllTopic() == null) {
            return null;
        }
        List<Focus> result = new ArrayList<>();
        de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Topic dbEntity;
        Focus searchEntity;
        for (String id : idList) {
            dbEntity = database.getAllTopic().getEntityById(id);
            if (dbEntity != null) {
                searchEntity = new Focus();
                searchEntity.setId(dbEntity.getId());
                searchEntity.setName(dbEntity.getName());
                searchEntity.setDescription(dbEntity.getDescription());
                result.add(searchEntity);
            }
        }
        return result;
    }

    private List<Characteristic> createCharacteristics(List<String> idList) {
        if (idList == null || database == null || database.getAllExtras() == null) {
            return null;
        }
        List<Characteristic> result = new ArrayList<>();
        de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Extras dbEntity;
        Characteristic searchEntity;
        for (String id : idList) {
            dbEntity = database.getAllExtras().getEntityById(id);
            if (dbEntity != null) {
                searchEntity = new Characteristic();
                searchEntity.setId(dbEntity.getId());
                searchEntity.setName(dbEntity.getName());
                searchEntity.setDescription(dbEntity.getDescription());
                result.add(searchEntity);
            }
        }
        return result;
    }

    private ProjectRole createProjectRole(String id) {
        if (id == null || database == null || database.getAllProjectRole() == null) {
            return null;
        }
        de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ProjectRole role = database.getAllProjectRole().getEntityById(id);
        if (role == null) {
            return null;
        }
        ProjectRole result = new ProjectRole();
        result.setId(role.getId());
        result.setName(role.getName());
        result.setDescription(role.getDescription());
        return result;
    }

    private String listOfString2String(List<String> sList) {
        if (sList == null) {
            return null;
        }
        String result = "";
        for (String s : sList) {
            result += s + " ";
        }
        return result;
    }

    private String createAddress(List<PersonAddress> addressList) {
        if (addressList == null) {
            return null;
        }
        String result = "";
        String addressString;
        for (PersonAddress address : addressList) {
            addressString = "";
            if (address.getStreet() != null) {
                addressString += address.getStreet();
            }
            if (address.getPlz() != null) {
                if (addressString.matches("")) {
                    addressString += address.getPlz();
                } else {
                    addressString += ", " + address.getPlz();
                }
            }
            if (address.getCity() != null) {
                if (addressString.matches("")) {
                    addressString += address.getCity();
                } else {
                    addressString += " " + address.getCity();
                }
            }
            if (address.getCountry() != null) {
                if (addressString.matches("")) {
                    addressString += address.getCountry();
                } else {
                    addressString += ", " + address.getCountry();
                }
            }
            if (address.getDescription() != null) {
                if (addressString.matches("")) {
                    addressString += address.getDescription();
                } else {
                    addressString += ", " + address.getDescription();
                }
            }
            result += addressString + ". ";
        }
        return result;
    }

    private Contact createContact(Person entity) {
        if (entity == null) {
            return null;
        }
        Contact result = new Contact();
        result.setFirst_name(entity.getForename());
        result.setLast_name(entity.getName());
        result.setTitel(entity.getTitel());
        result.setEmail(listOfString2String(entity.getEmail()));
        result.setTel(listOfString2String(entity.getTel()));
        result.setAddress(createAddress(entity.getAddress()));
        result.setTestimonal(entity.getTestimonal());
        result.setDescription(entity.getDescription());
        return result;
    }

    private List<User> createTeammembers(List<Person> teammembers) {
        if (teammembers == null || database == null) {
            return null;
        }
        List<User> result = new ArrayList<>();
        User user;
        ElasticUser eUser;
        for (Person teammember : teammembers) {
            if (teammember != null) {
                eUser = database.findFirstUserByPersonId(teammember.getId());
                user = getUserFromJavaDBUser(eUser);
                if (user != null) {
                    result.add(user);
                }
            }
        }
        return result;
    }

    private String concatHumanNames(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return null;
        }
        if (s1 != null && s2 == null) {
            return s1;
        }
        if (s1 == null && s2 != null) {
            return s2;
        }
        return s1 + " " + s2;
    }

    private String concatTelEmails(List<String> ls) {
        if (ls == null) {
            return null;
        }
        String result = "";
        if (ls.size() == 0) {
            return result;
        }
        for (String s : ls) {
            if (s != null) {
                result += s + " ";
            }
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    public Reference createSearchReferenceEntity(ReferenceEntity entity) {
        if (entity == null) {
            return null;
        }
        User user;
        Reference result = new Reference();
        result.setId(entity.getId());
        result.setClientname(entity.getClientname());
        result.setClientlogo(entity.getClientlogo());
        result.setProjectname(entity.getProjectname());
        result.setLob(createSearchLobEntity(entity.getLob()));
        result.setBranches(createListOfSearchBranchEntities(entity.getBranchList()));
        TimeInterval timeInterval = entity.getTimeInterval();
        if (timeInterval != null) {
            result.setProjectStart(timeInterval.startTime);
            result.setProjectEnd(timeInterval.endTime);
        }
        timeInterval = entity.getServiceTime();
        if (timeInterval != null) {
            result.setServiceStart(timeInterval.startTime);
            result.setServiceEnd(timeInterval.endTime);
        }
        result.setPtTotalProject(entity.getPtTotalProject());
        result.setPtAdesso(entity.getPtAdesso());
        result.setCostTotal(entity.getCostTotal());
        result.setCostAdesso(entity.getCostAdesso());
        result.setRefIdList(entity.getRefIDList());
        result.setReleaseStatus(getReleaseStatusFromReferenceEntity(entity));
        result.setChanged(getAccessStampFromReferenceEnity(entity.getChanged()));
        result.setReleased(getAccessStampFromReferenceEnity(entity.getReleased()));
        result.setDescription(entity.getDescription());
        result.setOwner(getUserFromJavaDBUser(entity.getOwner()));
        result.setEditor(getUserFromJavaDBUser(entity.getEditor()));
        result.setqAssurance(getUserFromJavaDBUser(entity.getqAssurance()));
        result.setApprover(getUserFromJavaDBUser(entity.getApprover()));
        result.setDeadlineEditor(entity.getDeadlineEditor());
        result.setDeadlineQA(entity.getDeadlineQA());
        result.setDeadlineApproval(entity.getDeadlineApproval());
        result.setInvolvedRoles(createInvolvedRoles(entity.getRoles()));
        result.setTeammembers(createTeammembers(entity.getTeammembers()));
        result.setTechnologies(createTechnologies(entity.getTechnics()));
        result.setFocuses(createFocuses(entity.getTopics()));
        result.setCharacteristics(createCharacteristics(entity.getExtras()));
        //result.setAdessoPartner(createAdessoPartner(entity.getAdessoPartner()));
        user = createAdessoPartner(entity.getAdessoPartner());
        if (user != null) {
            result.setAdessoContactName(
                    concatHumanNames(user.getFirst_name(), user.getLast_name()));
            result.setAdessoContactMail(user.getEmail());
            result.setAdessoContactPhone(user.getLast_name());
            result.setAdessoContactAdessoRole(user.getRole());
        }
        result.setAdessoContactProjectRole(createProjectRole(entity.getAdessoPartnerRole()));
        //result.setDeputy(createDeputy(entity.getDeputyName()));
        user = createDeputy(entity.getDeputyName());
        if (user != null) {
            result.setAdessoDeputyContactName(concatHumanNames(user.getFirst_name(), user.getLast_name()));
        }
//        result.setDeputyRole(createProjectRole(entity.getDeputyRole()));
        result.setAdessoDeputyContactProjectRole(createProjectRole(entity.getDeputyRole()));
        result.setClientData(createContact(entity.getClientData()));
        if (entity.getClientData() != null && entity.getClientData().getTestimonal() != null) {
            result.setTestimonial(entity.getClientData().getTestimonal());
        }
        result.setClientProfil(entity.getClientProfil());
        result.setProjectBackground(entity.getProjectBackground());
        result.setProjectSolution(entity.getProjectSolution());
        result.setProjectResults(entity.getProjectResults());
        result.setPreciseDescription(entity.getPreciseDescription());
        return result;
    }

    private User createAdessoPartner(Person adessoPartner) {
        if (adessoPartner == null) {
            return null;
        }
//        ElasticUser elasticUser = database.findFirstUserByPersonId(adessoPartner.getId());
//        User user;
//        if (elasticUser == null) {
//            user = createUserFromPerson(adessoPartner);
//        } else {
//            user = getUserFromJavaDBUser(elasticUser);
//        }
//        return user;
        return searchReferenceDatabaseController.findUserByPerson(adessoPartner);
    }

    private User createUserFromPerson(Person adessoPartner) {
        if (adessoPartner == null) {
            return null;
        }

        return null;
    }

    private User createDeputy(String deputyName) {
        Person person = database.getAllPerson().getEntityById(deputyName);
        return searchReferenceDatabaseController.findUserByPerson(person);
    }

}
