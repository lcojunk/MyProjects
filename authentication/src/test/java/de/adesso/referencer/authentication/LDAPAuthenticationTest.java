/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.authentication;

import de.adesso.referencer.authentication.service.LDAPAuthentication;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 * @author odzhara-ongom
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/springContext-test.xml")
public class LDAPAuthenticationTest {

    public static String[] adUsernames = {"referenzer-test01", "referenzer-test02", "referenzer-test03", "referenzer-test04"};
    public static String[] adUserpasswords = {"676qQa7CPW", "Csp1zDnj5c", "4Ma2saXaL6", "VpJ2FF7o3f"};
    @Autowired
    private LDAPAuthentication ldapAuthentication;

    public LDAPAuthenticationTest() {
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
     * Test of isUserLDAPAuthenticated method, of class LDAPAuthentication.
     */
    @Test
    public void testIsUserLDAPAuthenticatedPos() {
        System.out.println("isUserLDAPAuthenticated");
        boolean result;
        try {
            for (int i = 0; i < adUsernames.length; i++) {
                result = ldapAuthentication.isUserLDAPAuthenticated(adUsernames[i], adUserpasswords[i]);
                assertEquals(true, result);
            }
        } catch (NamingException ex) {
            Logger.getLogger(LDAPAuthenticationTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Must not throw exception!");
        }
    }

//    @Test
//    public void testIsMyPass() {
//        System.out.println("isUserLDAPAuthenticated");
//        String username = "odzhara-ongom@adesso.local";
//        String pass = "****";
//        boolean result;
//        try {
//            result = ldapAuthentication.isUserLDAPAuthenticated(username, pass);
//            assertEquals(true, result);
//        } catch (NamingException ex) {
//            Logger.getLogger(LDAPAuthenticationTest.class.getName()).log(Level.SEVERE, null, ex);
//            fail("Must not throw exception!");
//        }
//    }
//
//    @Test
//    public void testIsMyPassNoAt() {
//        System.out.println("isUserLDAPAuthenticated");
//        String username = "odzhara-ongom";
//        String pass = "****";
//        boolean result;
//        try {
//            result = ldapAuthentication.isUserLDAPAuthenticated(username, pass);
//            assertEquals(true, result);
//        } catch (NamingException ex) {
//            Logger.getLogger(LDAPAuthenticationTest.class.getName()).log(Level.SEVERE, null, ex);
//            fail("Must not throw exception!");
//        }
//    }
    @Test
    public void testIsUserLDAPAuthenticatedNeg() {
        System.out.println("isUserLDAPAuthenticated");
        String username = "referenzer-test-nopass";
        String pass = "no_pass";
        boolean result;
        try {
            result = ldapAuthentication.isUserLDAPAuthenticated(username, pass);
            assertEquals(false, result);
        } catch (NamingException ex) {
            fail("Must not throw exception!");
        }
    }

}
