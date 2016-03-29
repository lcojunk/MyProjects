/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.authentication.rest;

import de.adesso.referencer.authentication.JWTokens.JWTokenFactory;
import de.adesso.referencer.authentication.model.User;
import de.adesso.referencer.authentication.service.LDAPAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author odzhara-ongom
 */
@RestController
public class LDAPAuthenticationRESTController {

    private static final Logger LOGGER = Logger.getLogger(LDAPAuthenticationRESTController.class.getName());
    @Autowired
    private LDAPAuthentication ldapAuthentication;
    private AuthenticationResponse reply = new AuthenticationResponse();

    //@RequestMapping(value = {"/authenticate", "/${spring.application.name}/authenticate"}, method = RequestMethod.POST)
    @RequestMapping(value = {"/authenticate"}, method = RequestMethod.POST)
    //TODO: AuthenticationResponse umbauen auf RestReply<xxx>
    public AuthenticationResponse authenticatePost(@RequestBody User user) {
        String username = user.getUsername();
        String pass = user.getPassword();
        try {
            reply.exists = ldapAuthentication.isUserLDAPAuthenticated(username, pass);
            reply.error = "";
        } catch (NamingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            reply.exists = false;
            reply.error = ex.toString();
        }
        reply.username = username;
        if (reply.exists) {
            //TODO: Die Gruppe der Benutzer muss  aus einer DB o.ä. ausgelesen werden
            reply.group = "Admins";
            reply.token = JWTokenFactory.getTokenForSubjectWithRole(reply.username, reply.group);
        } else {
            reply.group = "";
            reply.token = "";
        }
        return reply;
    }
}
