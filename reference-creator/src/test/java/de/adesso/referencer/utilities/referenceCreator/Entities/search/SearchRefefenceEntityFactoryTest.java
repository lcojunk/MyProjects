/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Entities.search;

import de.adesso.referencer.utilities.referenceCreator.ElasticConfig;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.Person;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.ReferenceEntity;
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
public class SearchRefefenceEntityFactoryTest {

    private String databaseFilename = ElasticConfig.getJsonDefaultDatabaseFilename();
    private ReferenceJavaDatabase referenceJavaDatabase;

    public SearchRefefenceEntityFactoryTest() {
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

    @Test
    public void testCreateSearchReferenceEntity() {
        System.out.println("createSearchReferenceEntity");
        SearchRefefenceEntityFactory factory = new SearchRefefenceEntityFactory(referenceJavaDatabase);
        assertNotNull(factory);
        List<ReferenceEntity> rList = referenceJavaDatabase.getAllReferenceEntity().getEntityList();
        Reference reference;
        Person person;
        List<User> userlist = new ArrayList<>();
        if (rList != null && rList.size() != 0) {
            for (ReferenceEntity referenceEntity : rList) {
                if (referenceEntity != null) {
                    reference = factory.createSearchReferenceEntity(referenceEntity);
                    assertNotNull(reference);
                    if (referenceEntity.getAdessoPartner() != null) {
//                        assertNotNull(reference.getAdessoPartner());
//                        userlist.add(reference.getAdessoPartner());
                        if (referenceEntity.getAdessoPartner().getName() != null) {
//                            assertNotNull(reference.getAdessoPartner().getLast_name());
//                            assertEquals(referenceEntity.getAdessoPartner().getName(), reference.getAdessoPartner().getLast_name());
                            assertEquals(true, reference.getAdessoContactName().contains(referenceEntity.getAdessoPartner().getName()));
                        }
                    }

//                    if (referenceEntity.getDeputyName() != null) {
//                        assertNotNull(reference.getDeputy());
//                        person = referenceJavaDatabase.getAllPerson().getEntityById(referenceEntity.getDeputyName());
//                        assertNotNull(person);
//                        if (person.getName() != null) {
//                            assertEquals(person.getName(), reference.getDeputy().getLast_name());
//                        }
//                    }
                }
            }
        }
        assertNotNull(userlist);
    }

}
