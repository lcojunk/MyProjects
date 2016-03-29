/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Controllers;

import de.adesso.referencer.utilities.referenceCreator.ElasticConfig;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ElasticUser;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Person;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ReferenceEntity;
import de.adesso.referencer.utilities.referenceCreator.Entities.search.AdessoRole;
import de.adesso.referencer.utilities.referenceCreator.Entities.search.SearchReferenceDatabaseEntity;
import de.adesso.referencer.utilities.referenceCreator.Entities.search.User;
import de.adesso.referencer.utilities.referenceCreator.database.JavaDatabaseEntity;
import de.adesso.referencer.utilities.referenceCreator.database.ReferenceJavaDatabase;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author odzhara-ongom
 */
public class SearchReferenceDatabaseControllerTest {

    private String databaseFilename = ElasticConfig.getJsonDefaultDatabaseFilename();
    private ReferenceJavaDatabase referenceJavaDatabase;

    public SearchReferenceDatabaseControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        referenceJavaDatabase = new ReferenceJavaDatabase();
        if (!referenceJavaDatabase.createDatabaseFromFile(databaseFilename)) {
            System.out.println("Loading database from file '" + databaseFilename + "' failed. Creating default database");
            referenceJavaDatabase.createDefaultDatabase();
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getDatabase method, of class SearchReferenceDatabaseController.
     */
    @Test
    public void testSetDatabase() {
        System.out.println("setDatabase");
        SearchReferenceDatabaseController instance = new SearchReferenceDatabaseController(referenceJavaDatabase.getJavaDatabaseEntity());
        assertNotNull(instance);
    }

    @Test
    public void testGetAllUniquePersonsFromReferences() {
        System.out.println("getAllUniquePersonsFromReferences");
        SearchReferenceDatabaseController controller = new SearchReferenceDatabaseController(referenceJavaDatabase.getJavaDatabaseEntity());
        List<Person> entities = controller.getAllPersonsThatAreNotUsers(referenceJavaDatabase);
        boolean created = controller.createUsersFromPersonsThatWasNotUsers(referenceJavaDatabase);
        assertNotNull(entities);
        assertEquals(created, true);
    }

    @Test
    public void testFindUserByPerson() {
        System.out.println("findUserByPerson");
        SearchReferenceDatabaseController controller = new SearchReferenceDatabaseController(referenceJavaDatabase.getJavaDatabaseEntity());
        List<Person> entities = controller.getAllPersonsThatAreNotUsers(referenceJavaDatabase);
        boolean created = controller.createUsersFromPersonsThatWasNotUsers(referenceJavaDatabase);
        //Person person=controller.
        assertNotNull(entities);
        assertEquals(created, true);
        List<Person> personList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        User user;
        boolean checkname;
        for (ReferenceEntity referenceEntity : referenceJavaDatabase.getAllReferenceEntity().getEntityList()) {
            if (referenceEntity != null && referenceEntity.getAdessoPartner() != null) {
                personList.add(referenceEntity.getAdessoPartner());
                user = controller.findUserByPerson(referenceEntity.getAdessoPartner());
                if (user != null) {
                    userList.add(user);
                    if (user.getFirst_name() != null && referenceEntity.getAdessoPartner().getForename() != null) {
                        checkname = user.getFirst_name().matches(referenceEntity.getAdessoPartner().getForename());
                        assertEquals(checkname, true);
                    }

                }
                assertNotNull(user);

            }
        }
        assertNotNull(personList);
        assertNotNull(userList);
    }

}
