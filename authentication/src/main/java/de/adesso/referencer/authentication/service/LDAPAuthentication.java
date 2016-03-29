/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.authentication.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class LDAPAuthentication {

    private static final Logger LOGGER = Logger.getLogger(LDAPAuthentication.class.getName());

    private String LdapTestServer = "10.1.1.191";
    private String LdapServer = "ldap01.adesso.de:389";
    private String factory = "com.sun.jndi.ldap.LdapCtxFactory";
    private String authenticationType = "simple";
    private String adessoDomainName = "@adesso.local";

    private String setURL(String domainNameOrIP) {
        if (domainNameOrIP == null) {
            return null;
        }
        if (domainNameOrIP.startsWith("LDAP://")) {
            return domainNameOrIP;
        } else {
            return "LDAP://" + domainNameOrIP;
        }
    }

    private void printString(String string, String type) {
        if (StringUtils.isEmpty(type)) {
            return;
        }
        if (type.matches("Out")) {
            System.out.println(string);
        }
        if (type.matches("Warn")) {
            LOGGER.log(Level.WARNING, string);
        }
        if (type.matches("Info")) {
            LOGGER.log(Level.INFO, string);
        }
        if (type.matches("Err")) {
            LOGGER.log(Level.SEVERE, string);
        }
    }

    private boolean isUserLDAPAuthenticated(String factory, String url, String authenticationType, String username, String pass) throws NamingException {
        String info = "factory=" + factory + "\nurl=" + url + "\nauthenticationType=" + authenticationType
                + "\nusername=" + username + "\npass=****";
        printString(info, "Info");
        if (StringUtils.isEmpty(pass)) {
            printString("null-password are not allowed", "Err");
            return Boolean.FALSE;
        }
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, factory);
        env.put(Context.PROVIDER_URL, url);
        env.put(Context.SECURITY_AUTHENTICATION, authenticationType);
        env.put(Context.SECURITY_PRINCIPAL, username);
        env.put(Context.SECURITY_CREDENTIALS, pass);
        try {
            DirContext ctx = new InitialDirContext(env);
            ctx.close();
            env.remove(Context.SECURITY_PRINCIPAL);
            env.remove(Context.SECURITY_CREDENTIALS);
            return Boolean.TRUE;
        } catch (final AuthenticationException e) {
            env.put(Context.SECURITY_CREDENTIALS, "*********");
            e.getMessage();
            printString("User could not be authorized by LDAP: " + env + " Error:" + e.toString(), "Err");
            return Boolean.FALSE;
        } catch (NamingException e) {
            printString("There was an error while connection to LDAP:" + e.toString(), "Err");
            throw new NamingException("There was an error while connection to LDAP:" + e.toString());
        }
    }

    private boolean isUserLDAPAuthenticated(String domainNameOrIP, String username, String pass) throws NamingException {
        if (domainNameOrIP == null || domainNameOrIP.length() == 0) {
            return false;
        }
        return isUserLDAPAuthenticated(factory, setURL(domainNameOrIP), authenticationType, username, pass);
    }

    public boolean isUserLDAPAuthenticated(String username, String pass) throws NamingException {
        if (StringUtils.isEmpty(username)) {
            return false;
        }
        if (username.startsWith("referenzer-test")) {
            return isUserLDAPAuthenticated(setURL(LdapTestServer), username, pass);
        }
        if (username.contains("@")) {
            return isUserLDAPAuthenticated(setURL(LdapServer), username, pass);
        }
        return isUserLDAPAuthenticated(setURL(LdapServer), username + adessoDomainName, pass);
    }
}
