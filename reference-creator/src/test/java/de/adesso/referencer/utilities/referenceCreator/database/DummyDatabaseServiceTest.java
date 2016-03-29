/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

import de.adesso.referencer.utilities.referenceCreator.ElasticConfig;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.*;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author odzhara-ongom
 */
public class DummyDatabaseServiceTest {

    String databaseFilename = ElasticConfig.getJsonDefaultDatabaseFilename();
    ReferenceJavaDatabase db;

    public DummyDatabaseServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        db = new ReferenceJavaDatabase();
        if (!db.createDatabaseFromFile(databaseFilename)) {
            System.out.println("Loading database from file '" + databaseFilename + "' failed. Creating default database");
            db.createDefaultDatabase();
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testcreateDummyPeoples() {
        DummyDatabaseService instance = new DummyDatabaseService(db);
        instance.createDummyPeoples(100);
        Assert.assertNotNull(instance);
        Person person = db.getAllPerson().getEntityById(instance.getPeople().get(0).getId());
        Assert.assertNotNull(person);

    }

    @Test
    public void testCreateDummyAdessoPeoples() {
        DummyDatabaseService instance = new DummyDatabaseService(db);
        instance.createDummyAdessoPeoples(20);
        Assert.assertNotNull(instance);
        Person person = db.getAllPerson().getEntityById(instance.getPeople().get(0).getId());
        Assert.assertNotNull(person);
        ElasticUser user = db.getAllElasticUser().getEntityById(instance.getUsers().get(0).getId());
        Assert.assertNotNull(user);
    }

    @Test
    public void testCreateReferences() {
        DummyDatabaseService instance = new DummyDatabaseService(db);
        Assert.assertNotNull(instance);
        instance.createDummyReferences(100);
        ReferenceEntity reference = db.getAllReferenceEntity().getEntityById(instance.getReferences().get(0).getId());
        Assert.assertNotNull(reference);
    }

}
