/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator;

import de.adesso.referencer.utilities.referenceCreator.helpers.ResourceLoaderService;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

/**
 *
 * @author odzhara-ongom
 */
public class ResourceReader {

    public File getFile2(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        if (classLoader == null) {
            return null;
        }
        File file = null;
        try {
            file = new File(classLoader.getResource(fileName).getFile());
            return file;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public File getFile(String filename) {
        ApplicationContext appContext
                = new ClassPathXmlApplicationContext(new String[]{"SpringResourceLoader.xml"});

        ResourceLoaderService service = (ResourceLoaderService) appContext.getBean("resourceLoaderService");
        Resource resource = service.getResource(filename);
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return file;
    }
}
