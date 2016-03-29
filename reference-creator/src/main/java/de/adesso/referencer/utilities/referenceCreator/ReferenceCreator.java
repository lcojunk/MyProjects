/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator;

import de.adesso.referencer.utilities.referenceCreator.Controllers.RefCreatorMain;
import de.adesso.referencer.utilities.referenceCreator.dummies.TestController;

/**
 *
 * @author odzhara-ongom
 */
public class ReferenceCreator {

    private static String[] params = {"gui", "automatic", "recreate_index", "delete_index", "with_dictionary"};
    private static String[] paramsDescription = {
        params[0] + " - start Graphic User Interface",
        params[1] + " - delete old index and create new index from file. \n            Elasticsearch Databank and Search Microservice should already be started",
        params[2] + " - delete old index and create new empty index",
        params[3] + " - delete old index",
        params[4] + " - delete old index and create new index with dictionary from file. \n            Elasticsearch Databank and Search Microservice should already be started"
    };

    private static void printDescription() {
        System.out.println("Command line parameters:");
        for (int i = 0; i < paramsDescription.length; i++) {
            System.out.println(paramsDescription[i]);
        }
    }

    public static void main(String[] args) {
        System.out.println("Staring reference creator...");
        if (args != null) {
            if (args.length > 0) {
                if (args[0].contains(params[0])) {
                    RefCreatorMain.runMain(args);
                    return;
                }
                if (args[0].contains(params[1])) {
                    RefCreatorMain.recreateIndexUsingSearchAPI(args, 0);
                    return;
                }
                if (args[0].contains(params[2])) {
                    RefCreatorMain.recreateIndex(args);
                    return;
                }
                if (args[0].contains(params[3])) {
                    RefCreatorMain.deleteIndex(args);
                    return;
                }
                if (args[0].contains(params[4])) {
                    RefCreatorMain.recreateIndexUsingSearchAPI(args, 1);
                    return;
                }
                if (args[0].matches("test")) {
                    TestController.runMain(args);
                    return;
                }
                if (args[0].contains("directwrite")) {
                    RefCreatorMain.jSON2ElasticSearch(args);
                    return;
                }
            } else {
                printDescription();
            }

        }
        System.out.println("Done.");
    }
}
