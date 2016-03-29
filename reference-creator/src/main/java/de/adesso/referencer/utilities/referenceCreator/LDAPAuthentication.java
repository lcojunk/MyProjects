/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator;

import java.util.Properties;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 *
 * @author odzhara-ongom
 */
public class LDAPAuthentication {
    public static String DOMAIN_CONTROLLER = "projekte.local";
    public static String [] DOMAIN_IP ={"10.1.1.191"};
    private static String [] testUsernames={"referenzer-test01","referenzer-test02","referenzer-test03","referenzer-test04"};
    private static String [] testUserpasswords={"676qQa7CPW", "Csp1zDnj5c", "4Ma2saXaL6", "VpJ2FF7o3f"};
    
    public static boolean isUserLDAPAuthenticated ( String user, String password,  String domainController ) throws NamingException {
        
        System.out.println("Checking user '"+user+"' in domain '"+domainController+"'");
        if (password==null) {
            System.out.println("null-password ist verboten");
            return Boolean.FALSE;
        }
        if (password.compareTo("")==0) {
            System.out.println("leeres password ist verboten");
            return Boolean.FALSE;
        }
        Properties env = new Properties();
        env.put( Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory" );
        //env.put( Context.PROVIDER_URL, profilerSystemServiceBean.getLDAPServer() );
        env.put( Context.PROVIDER_URL,"LDAP://" + domainController);
        env.put( Context.SECURITY_AUTHENTICATION, "simple" );
        //env.put( Context.SECURITY_PRINCIPAL, user + "@adesso.local" );
        //env.put( Context.SECURITY_PRINCIPAL, user + "@"+domainController );
        env.put( Context.SECURITY_PRINCIPAL, user);
        env.put( Context.SECURITY_CREDENTIALS, password );

        try {

            DirContext ctx = new InitialDirContext( env );
            
            System.out.println(ctx);
            
            ctx.close();

            env.remove( Context.SECURITY_PRINCIPAL );
            env.remove( Context.SECURITY_CREDENTIALS );

            System.out.println("user exists");
            return Boolean.TRUE;

        } catch( final AuthenticationException e ) {

            env.put( Context.SECURITY_CREDENTIALS, "*********" );
            //log.info( "User konnte nicht bei LDAP authentifiziert werden: " + env, e );
            e.getMessage();
//            e.printStackTrace();
            System.out.println("User konnte nicht bei LDAP authentifiziert werden: " + env+" Error:"+e.toString());

            return Boolean.FALSE;

        } catch( NamingException e ) {

            //throw new ProfilerException( "Fehler bei der Verbindung zu LDAP", e );
            System.out.println( "Fehler bei der Verbindung zu LDAP:"+e.toString() );
            throw new NamingException("Fehler bei der Verbindung zu LDAP:"+e.toString());
        }
        //System.out.println("Some error happend");
        //return Boolean.FALSE;
    }    
    
    public static boolean isUserLDAPAuthenticated( String user, String password) throws NamingException {
        boolean result;
        try {
            result = isUserLDAPAuthenticated(user, password, DOMAIN_CONTROLLER);
        } catch (NamingException ex) {
            result=isUserLDAPAuthenticated(user, password, DOMAIN_IP[0]);
        }
        return result;
    }

    /**
     * @return the testUsernames
     */
    public static String[] getTestUsernames() {
        return testUsernames;
    }

    /**
     * @param aTestUsernames the testUsernames to set
     */
    public static void setTestUsernames(String[] aTestUsernames) {
        testUsernames = aTestUsernames;
    }

    /**
     * @return the testUserpasswords
     */
    public static String[] getTestUserpasswords() {
        return testUserpasswords;
    }

    /**
     * @param aTestUserpasswords the testUserpasswords to set
     */
    public static void setTestUserpasswords(String[] aTestUserpasswords) {
        testUserpasswords = aTestUserpasswords;
    }
}
