/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator;

import java.awt.Component;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author odzhara-ongom
 */
public class MyHelpMethods4GUI {

    private static String dateformat="dd-MM-yyyy HH:mm:ss";
    private static String dayformat="dd-MM-yyyy";
    private static String timeformat="HH:mm:ss";    
    private static boolean isValidString(String s) {
        if (s!=null)
           if(s.compareTo("")!=0) return true;
        return false;
    }    
    public static void setJTextAreaFromList(JTextArea area, List<String> sList){
        String text;        
        if(sList!=null)
            if(sList.size()>0) {
                text="";
                for (String s:sList)
                    if(s!=null) text+=s+"\n";
                area.setText(text);
            }        
    }
    
    
    public static void setJTextFeld(JTextField field, String text) {
        if (text!=null) field.setText(text);
    }

    public static int setSpinnerOfDate(javax.swing.JSpinner spinner, String s ) {
            if (s==null) return -1;
            if ((s.compareTo("")==0)||(s.compareTo(" ")==0)) {
                spinner.setModel(new javax.swing.SpinnerDateModel(new Date(),
                                null, null, java.util.Calendar.DAY_OF_MONTH));
                return 1;
            }            
            if (isValidString(s)){                    
                try {
                    spinner.setModel(
                            new javax.swing.SpinnerDateModel(
                                    new SimpleDateFormat(dateformat).parse(s),
                                    null, null, java.util.Calendar.DAY_OF_MONTH)
                    );
                } catch (ParseException ex) {
                    //Logger.getLogger(MyHelpMethods4GUI.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                    return 1;
                }
                return 0;
            }
            return -1;
    }    

    public static void setSpinnerOfDate(javax.swing.JSpinner spinner, Date date ) {
        if (date==null) return;
        spinner.setModel(new javax.swing.SpinnerDateModel(
                date,
                            null, null, java.util.Calendar.DAY_OF_MONTH));
    }
    
    public static File askFile2Open(JFileChooser jfc, Component jf){
        int o=jfc.showOpenDialog(jf);
        if (o==JFileChooser.APPROVE_OPTION) return jfc.getSelectedFile();        
        else return null;
    }
    
    public static File askFile2Save(JFileChooser jfc, Component jf){
        int o=jfc.showSaveDialog(jf);
        if (o==JFileChooser.APPROVE_OPTION) return jfc.getSelectedFile();        
        else return null;
    }
    
    public static String askFile2OpenString(JFileChooser jfc, Component jf){
        File file = askFile2Open( jfc, jf);
        if (file==null) return null;
        String result=file+"";
        if (result==null) return null;
        else return result;
    }


    public static String askFile2SaveString(String ext, JFileChooser jfc, Component jf){
        File file = askFile2Save( jfc, jf);
        if (file==null) return null;
        String result=file+"";
        if (result==null) return null;
        if(ext==null) return result;
        if(result.endsWith("."+ext)) return result;
        return result+"."+ext;
    }
    
}
