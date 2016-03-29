/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator;

import java.io.File;
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
public class ResourceReaderTest {

    public ResourceReaderTest() {
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
    public void testGetNotExistedFile() {
        System.out.println("getFile");
        String fileName = "notExistedFile";
        ResourceReader instance = new ResourceReader();
        File expResult = null;
        File result = instance.getFile(fileName);
        assertEquals(expResult, result);
    }

    @Test
    public void defaultDatabaseFile() {
        System.out.println("getFile");
        String fileName = ElasticConfig.getJsonDefaultDatabaseFilename();
        ResourceReader instance = new ResourceReader();
        File result = instance.getFile(fileName);
        System.out.println(result.getPath());
        assertNotNull(result);
    }

}
