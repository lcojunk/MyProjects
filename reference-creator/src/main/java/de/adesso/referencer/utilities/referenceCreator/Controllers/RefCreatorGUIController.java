/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Controllers;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.*;
import de.adesso.referencer.utilities.referenceCreator.GUI.*;
import de.adesso.referencer.utilities.referenceCreator.database.*;

import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class RefCreatorGUIController {

    public static void editAdessoRole() {
        AllAdessoRoleDialog dialog=new AllAdessoRoleDialog( new javax.swing.JFrame(),
                    AllAdessoRole.getInstance().getEntityList(),
                    true);
        dialog.setVisible(true);
    }
    
    public static String askAdessoRoleID(String id){
        AllAdessoRoleDialog dialog= new AllAdessoRoleDialog( new javax.swing.JFrame(),
                            AllAdessoRole.getInstance().getEntityList(),
                            AllAdessoRole.getInstance().getEntityById(id),true);
        dialog.setVisible(true);
        if (dialog.getSelectedEntities()==null) return null;
        else return dialog.getSelectedEntities().getId();          
    }    
    
    public String changeAdessoRoleID(String id) {
        List <AdessoRole> arList= AllAdessoRole.getInstance().getEntityList();
        int idx=-1;
        if (arList!=null) {
            for (int i=0; i<arList.size(); i++) {
                if (arList.get(i)!=null)
                    if(arList.get(i).getId()!=null)
                        if (arList.get(i).getId().compareTo(id)==0) {
                            idx=i;
                            break;
                }
            }
        }
       // AllAdessoRoleDialog dialog=new AllAdessoRoleDialog(new javax.swing.JFrame(), arList, true)
        return null;
    }
    
    public static String askLobID(String lobID){
        List <LOB> lobs =AllLobs.getInstance().getEntityList();
        LOB selectedLob=AllLobs.getInstance().getEntityById(lobID);
        AllLobsDialog dialog= new AllLobsDialog( new javax.swing.JFrame(),lobs,selectedLob, true);
        dialog.setVisible(true);
        if (dialog.getSelectedEntity()==null) return null;
        else return dialog.getSelectedEntity().getId();
    }
    public static void editLob(){
        AllLobsDialog dialog=new AllLobsDialog( new javax.swing.JFrame(),
                            AllLobs.getInstance().getEntityList(),
                            true);
        dialog.setVisible(true);
    }
    
    public static String askBranchID(){
        List <Branch> entityList=AllBranches.getInstance().getEntityList();
        if (entityList==null) return null;
        AllBranchesDialog dialog= new AllBranchesDialog( new javax.swing.JFrame(),entityList, true);
        dialog.setVisible(true);
        if (dialog.getSelectedEntities()==null) return null;
        else return dialog.getSelectedEntities().getId();        
    }
    public static void editBranch(){
        AllBranchesDialog dialog=new AllBranchesDialog( new javax.swing.JFrame(),
                            AllBranches.getInstance().getEntityList(),
                            true);
        dialog.setVisible(true);
    }
    public static String askInvolvedRoleID(){
        AllInvolvedRoleDialog dialog= new AllInvolvedRoleDialog( new javax.swing.JFrame(),
                            AllInvolvedRole.getInstance().getEntityList(), true);
        dialog.setVisible(true);
        if (dialog.getSelectedEntities()==null) return null;
        else return dialog.getSelectedEntities().getId();          
    }
    public static void editInvolvedRole(){
        AllInvolvedRoleDialog dialog=new AllInvolvedRoleDialog( new javax.swing.JFrame(),
                            AllInvolvedRole.getInstance().getEntityList(),
                            true);
        dialog.setVisible(true);
    }
    public static String askFeatureID(){
        AllFeatureDialog dialog= new AllFeatureDialog( new javax.swing.JFrame(),
                            AllFeature.getInstance().getEntityList(), true);
        dialog.setVisible(true);
        if (dialog.getSelectedEntities()==null) return null;
        else return dialog.getSelectedEntities().getId();          
    }
    public static void editFeature(){
        AllFeatureDialog dialog=new AllFeatureDialog( new javax.swing.JFrame(),
                            AllFeature.getInstance().getEntityList(),
                            true);
        dialog.setVisible(true);
    }
    public static String askProjectRoleID(String id){
        AllProjectRoleDialog dialog= new AllProjectRoleDialog( new javax.swing.JFrame(),
                            AllProjectRole.getInstance().getEntityList(),
                            AllProjectRole.getInstance().getEntityById(id),true);
        dialog.setVisible(true);
        if (dialog.getSelectedEntities()==null) return null;
        else return dialog.getSelectedEntities().getId();          
    }
    public static void editProjectRole(){
        AllProjectRoleDialog dialog=new AllProjectRoleDialog( new javax.swing.JFrame(),
                            AllProjectRole.getInstance().getEntityList(),
                            true);
        dialog.setVisible(true);
    }    
    public static Person askPersonID(Person entity){
        AllPersonDialog dialog= new AllPersonDialog( new javax.swing.JFrame(),
                            AllPerson.getInstance().getEntityList(),
                            entity,true);
        dialog.setVisible(true);
        if (dialog.getSelectedEntities()==null) return null;
        else return dialog.getSelectedEntities();          
    }
    
    public static String askPersonID(String id){
        Person entity=askPersonID(AllPerson.getInstance().getEntityById(id));
        if (entity==null) return null;
        else return entity.getId();        
    }
    public static void editPerson(){
        AllPersonDialog dialog=new AllPersonDialog( new javax.swing.JFrame(),
                            AllPerson.getInstance().getEntityList(),
                            true);
        dialog.setVisible(true);
    }    
    
    public static Technology askTechnoloyID(Technology entity){
        AllTechnologyDialog dialog= new AllTechnologyDialog( new javax.swing.JFrame(),
                            AllTechnology.getInstance().getEntityList(),
                            entity,true);
        dialog.setVisible(true);
        if (dialog.getSelectedEntities()==null) return null;
        else return dialog.getSelectedEntities();          
    }
    public static String askTechnoloyID(String id){
        Technology entity=askTechnoloyID(AllTechnology.getInstance().getEntityById(id));
        if (entity==null) return null;
        else return entity.getId();        
    }    
    public static void editTechnology(){
        AllTechnologyDialog dialog=new AllTechnologyDialog( new javax.swing.JFrame(),
                            AllTechnology.getInstance().getEntityList(),
                            true);
        dialog.setVisible(true);
    }    
    
    public static Extras askExtrasID(Extras entity){
        AllExtrasDialog dialog= new AllExtrasDialog( new javax.swing.JFrame(),
                            AllExtras.getInstance().getEntityList(),
                            entity,true);
        dialog.setVisible(true);
        if (dialog.getSelectedEntities()==null) return null;
        else return dialog.getSelectedEntities();          
    }
    public static String askExtrasID(String id){
        Extras entity=askExtrasID(AllExtras.getInstance().getEntityById(id));
        if (entity==null) return null;
        else return entity.getId();        
    }
    
    public static void editExtras(){
        AllExtrasDialog dialog=new AllExtrasDialog( new javax.swing.JFrame(),
                            AllExtras.getInstance().getEntityList(),
                            true);
        dialog.setVisible(true);
    }

    public static Topic askTopicID(Topic entity){
        AllTopicDialog dialog= new AllTopicDialog( new javax.swing.JFrame(),
                            AllTopic.getInstance().getEntityList(),
                            entity,true);
        dialog.setVisible(true);
        if (dialog.getSelectedEntities()==null) return null;
        else return dialog.getSelectedEntities();          
    }
    public static String askTopicID(String id){
        Topic entity=askTopicID(AllTopic.getInstance().getEntityById(id));
        if (entity==null) return null;
        else return entity.getId();        
    }    
    public static void editTopic(){
        AllTopicDialog dialog=new AllTopicDialog( new javax.swing.JFrame(),
                            AllTopic.getInstance().getEntityList(),
                            true);
        dialog.setVisible(true);
    }
    
    public static String askReferenceID(){
        AllReferenceDialog dialog= new AllReferenceDialog( new javax.swing.JFrame(),
                            AllReferenceEntity.getInstance().getEntityList(), true);
        dialog.setVisible(true);
        if (dialog.getSelectedEntities()==null) return null;
        else return dialog.getSelectedEntities().getId();    
    }
    
    public static String askElasticUserID(String entityID){
        AllElasticUserDialog dialog = new AllElasticUserDialog(new javax.swing.JFrame(),
                        AllElasticUser.getInstance().getEntityList(),
                        AllElasticUser.getInstance().getEntityById(entityID), true);
        dialog.setVisible(true);
        if (dialog.getSelectedEntities()==null) return null;
        else return dialog.getSelectedEntities().getId();
    }
    public static void editElasticUser(){
        AllElasticUserDialog dialog=new AllElasticUserDialog( new javax.swing.JFrame(),
                            AllElasticUser.getInstance().getEntityList(),
                            true);
        dialog.setVisible(true);
    }
    
    public static boolean runYesNoDialog(String titel, String label, String text) {
        YesNoDialogWithTextArea dialog = new YesNoDialogWithTextArea(new javax.swing.JFrame(), titel, label, text, true);
        dialog.setVisible(true);
        return dialog.isChoice();
    }
}
