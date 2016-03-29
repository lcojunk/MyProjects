/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.*;
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
public class ReferencesTestValuesTest {

    public ReferencesTestValuesTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateRandomPersonName() {
        System.out.println("createRandomPersonName");
        ReferencesTestValues instance = new ReferencesTestValues();
        String result = instance.createRandomPersonName();
        Assert.assertNotNull(result);
    }

    @Test
    public void testCreateRandomPersonAdress() {
        System.out.println("createRandomPersonAdress");
        ReferencesTestValues instance = new ReferencesTestValues();
        PersonAddress result = instance.createRandomPersonAdress();
        Assert.assertNotNull(result);
    }

    @Test
    public void testCreateRandomPerson() {
        System.out.println("createRandomPerson");
        ReferencesTestValues instance = new ReferencesTestValues();
        Person result = instance.createRandomPerson();
        Assert.assertNotNull(result);
    }

}
