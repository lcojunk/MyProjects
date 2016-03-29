/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.authentication.rest;

import de.adesso.referencer.authentication.model.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author odzhara-ongom
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/springContext-test.xml")
public class LDAPAuthenticationRESTControllerTest {

    public static String[] adUsernames = {"referenzer-test01", "referenzer-test02", "referenzer-test03", "referenzer-test04"};
    public static String[] adUserpasswords = {"676qQa7CPW", "Csp1zDnj5c", "4Ma2saXaL6", "VpJ2FF7o3f"};

    @Autowired
    LDAPAuthenticationRESTController instance;

    public LDAPAuthenticationRESTControllerTest() {
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

    /**
     * Test of authenticate method, of class LDAPAuthenticationRESTController.
     */
    @Test
    public void testAuthenticate() {
        System.out.println("authenticate");
        User user = new User(adUsernames[0], adUserpasswords[0]);

        AuthenticationResponse authenticationResponse = instance.authenticatePost(user);
        boolean result = authenticationResponse.exists;
        boolean expResult;
        if (authenticationResponse.error == "") {
            expResult = true;
        } else {
            expResult = false;
        }
        assertEquals(expResult, result);
    }
}
