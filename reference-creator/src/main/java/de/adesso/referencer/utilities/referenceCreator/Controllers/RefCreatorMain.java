/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Controllers;

import de.adesso.referencer.utilities.referenceCreator.ElasticConfig;
import de.adesso.referencer.utilities.referenceCreator.Entities.search.RestReply;
import de.adesso.referencer.utilities.referenceCreator.GUI.MainPageFrame;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import de.adesso.referencer.utilities.referenceCreator.database.*;

/**
 *
 * @author odzhara-ongom
 */
public class RefCreatorMain {

    private static String databaseFilename = ElasticConfig.getJsonDefaultDatabaseFilename();
    private static String serverName, serverPort;

    public static ReferenceJavaDatabase startJavaDatabase() {
        if (serverName != null) {
            ElasticConfig.setSERVER_NAME(serverName);
        }
        if (serverPort != null) {
            ElasticConfig.setSERVER_PORT(serverPort);
        }
        ReferenceJavaDatabase db = new ReferenceJavaDatabase();
        if (!db.createDatabaseFromFile(databaseFilename)) {
            System.out.println("Loading database from file '" + databaseFilename + "' failed. Creating default database");
            db.createDefaultDatabase();
        }
        // db.getAllElasticUser().getEntityList().remove(1);
        BooleanReply reply = db.isDatabaseConsistent();
        if (reply.isExists()) {
            System.out.println("Database is consistent");
        } else {
            boolean choice = RefCreatorGUIController.runYesNoDialog("!!! Database is not consistent !!!",
                    "Warning! Database is not consistent. Abort?",
                    reply.toString(1));
            if (choice) {
                System.exit(0);
            }
        };
        return db;
    }

    public static void jSON2ElasticSearch(String[] args) {
        if (args.length > 1) {
            databaseFilename = args[1];
        }
        if (args.length > 2) {
            serverName = args[2];
        }
        if (args.length > 3) {
            serverPort = args[3];
        }
        System.out.println("Loading and starting Java-Database...");
        ReferenceJavaDatabase database = startJavaDatabase();
        System.out.println("Coping Java Database to Elasticsearch...");
        RefCreatorElasticController.fillDatabase(database);
    }

    public static void runMain(String[] args) {
        if (args.length > 1) {
            databaseFilename = args[1];
        }
        if (args.length > 2) {
            serverName = args[2];
        }
        if (args.length > 3) {
            serverName = args[3];
        }
        ReferenceJavaDatabase db = startJavaDatabase();
        MainPageFrame mainPage = new MainPageFrame(db);
        mainPage.setVisible(true);
    }

    public static boolean recreateIndex(String[] args) {
        ExportReferenceToSearchController controller = new ExportReferenceToSearchController();
        System.out.println("Recreating index... ");
        RestReply reply = controller.reCreateDatabase();
        if (reply.isSuccess()) {
            System.out.println("Index was recreated.\nReply from server:\n"
                    + MyHelpMethods.object2PrettyString(reply));
            return true;
        } else {
            System.out.println("Error during operation of creation.\nReply from server:\n"
                    + MyHelpMethods.object2PrettyString(reply));
            return false;
        }
    }

    public static boolean deleteIndex(String[] args) {
        ExportReferenceToSearchController controller = new ExportReferenceToSearchController();
        System.out.println("Deleting index... ");
        RestReply reply = controller.deleteDatabase();
        if (reply.isSuccess()) {
            System.out.println("Index was deleted.\nReply from server:\n"
                    + MyHelpMethods.object2PrettyString(reply));
            return true;
        } else {
            System.out.println("Error during operation of delete.\nReply from server:\n"
                    + MyHelpMethods.object2PrettyString(reply));
            return false;
        }
    }

    public static void recreateIndexUsingSearchAPI(String[] args, int method) {
        if (args.length > 1) {
            databaseFilename = args[1];
        }
        if (args.length > 2) {
            serverName = args[2];
        }
        if (args.length > 3) {
            serverName = args[3];
        }
        ReferenceJavaDatabase db = startJavaDatabase();
        if (!recreateIndex(args)) {
            System.out.println("Could not recreate index");
            return;
        }
        if (db == null) {
            System.out.println("Database is empty");
            return;
        }
        ExportReferenceToSearchController controller = new ExportReferenceToSearchController(db);
        RestReply reply = controller.exportAllReferences(method);
        if (reply.isSuccess()) {
            System.out.println("Database was recreated. " + reply.getCount()
                    + " references was written");
        } else {
            System.out.println("Error during operation:\n" + MyHelpMethods.object2PrettyString(reply));
        }

//        System.out.println("Checking existence of index");
//        ExportReferenceToSearchController controller=new ExportReferenceToSearchController();
//        RestReply reply=new RestReply();
//        boolean indexExists=false;
//            try {
//                reply=controller.indexExists();
//                if (reply.isSuccess()) {
//                    indexExists= ((Boolean) reply.getResult()).booleanValue();
//                }
//            } catch (Exception ex) {
//                System.out.println("Error");
//                ex.printStackTrace();
//                return;
//            }
//        System.out.println("Index exists: "+indexExists);
//        if (!indexExists) {
//            System.out.println("Index exists. Deleting index");
//            reply=controller.deleteDatabase();
//            if(!reply.isSuccess()) {
//                System.out.println("Could not delete index.\n Server Reply:\n"
//                        +MyHelpMethods.object2PrettyString(reply)+"\n.Exiting");
//                return;
//            }
//        }
//        System.out.println("Creating index");
    }
}
