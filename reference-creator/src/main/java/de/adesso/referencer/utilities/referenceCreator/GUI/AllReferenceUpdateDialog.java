/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.GUI;

import de.adesso.referencer.utilities.referenceCreator.ElasticConfig;
import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.*;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods;
import de.adesso.referencer.utilities.referenceCreator.MyHelpMethods4GUI;
import de.adesso.referencer.utilities.referenceCreator.Controllers.RefCreatorGUIController;
import de.adesso.referencer.utilities.referenceCreator.database.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.TableModel;

/**
 *
 * @author odzhara-ongom
 */
public class AllReferenceUpdateDialog extends javax.swing.JDialog {
    
    private ReferenceEntity entity;
    
    private TableModel branchTableModel, referenceTableModel, involvedRoleTableModel,
            technicsTableModel, topicTableModel, teamTableModel, extrasTableModel;
    
   

    public ReferenceEntity getEntity() {
        return entity;
    }

    public void setEntity(ReferenceEntity entity) {
        this.entity = entity;
    }

    public void setJComboBoxStatus(){
//        jComboBoxStatus=new javax.swing.JComboBox();
/*        String [] itemStrings=ElasticConfig.getReferenceStatusNames();
        System.out.println("bla bla");
        for (int i=0; i<itemStrings.length; i++ )
            jComboBoxStatus.addItem(itemStrings[i]);
        if (entity.getFreigabestatus()<0||entity.getFreigabestatus()>itemStrings.length)
            jComboBoxStatus.setSelectedIndex(0);
        else jComboBoxStatus.setSelectedIndex(entity.getFreigabestatus());
*/    
        jComboBoxStatus.setModel(new javax.swing.DefaultComboBoxModel(ElasticConfig.getReferenceStatusNames()));

        if (entity.getFreigabestatus()<0||entity.getFreigabestatus()>ElasticConfig.getReferenceStatusNames().length)
            jComboBoxStatus.setSelectedIndex(0);
        else jComboBoxStatus.setSelectedIndex(entity.getFreigabestatus());
    }
 
    public void getJComboBoxStatus(){
        entity.setFreigabestatus(jComboBoxStatus.getSelectedIndex());
    }
    private void setTableBranches(){
        if(entity.getBranchList()!=null) {
            List <Branch> branches=new ArrayList<Branch>();
            for (String s:entity.getBranchList())
                if (s!=null) branches.add(AllBranches.getInstance().getEntityById(s));
            branchTableModel = new AllBranchesTableModel(branches);
            jTableBranch = new JTable(branchTableModel);
            jScrollPaneBranch.setAutoscrolls(true);
            jScrollPaneBranch.setViewportView(jTableBranch);                
        }
    }
    private void setTableInvolvedRoles(){
        if(entity.getRoles()!=null) {
            List <InvolvedRole> entities=new ArrayList<InvolvedRole>();
            for (String s:entity.getRoles())
                if (s!=null) entities.add(AllInvolvedRole.getInstance().getEntityById(s));
            involvedRoleTableModel = new AllInvolvedRoleTableModel(entities);
            jTableInvolvedRoles = new JTable(involvedRoleTableModel);
            jScrollPaneInvolvedRoles.setAutoscrolls(true);
            jScrollPaneInvolvedRoles.setViewportView(jTableInvolvedRoles);                
        }        
    }

    private void setTableTechnics(){
        if(entity.getTechnics()!=null) {
            List <Technology> entities=new ArrayList<Technology>();
            for (String s:entity.getTechnics())
                if (s!=null) entities.add(AllTechnology.getInstance().getEntityById(s));
            technicsTableModel = new AllTechnologyTableModel(entities);
            jTableTechnics = new JTable(technicsTableModel);
            jScrollPaneTechnics.setAutoscrolls(true);
            jScrollPaneTechnics.setViewportView(jTableTechnics);                
        }        
    }
    
    private void setTableTopic(){
        if(entity.getTopics()!=null) {
            List <Topic> entities=new ArrayList<Topic>();
            for (String s:entity.getTopics())
                if (s!=null) entities.add(AllTopic.getInstance().getEntityById(s));
            topicTableModel = new AllTopicTableModel(entities);
            jTableTopic = new JTable(topicTableModel);
            jScrollPaneTopic.setAutoscrolls(true);
            jScrollPaneTopic.setViewportView(jTableTopic);                
        }        
    }
    
    private void setTableExtras() {
        if(entity.getExtras()!=null) {
            List <Extras> entities=new ArrayList<Extras>();
            for (String s:entity.getExtras())
                if (s!=null) entities.add(AllExtras.getInstance().getEntityById(s));
            extrasTableModel = new AllExtrasTableModel(entities);
            jTableExtras = new JTable(extrasTableModel);
            jScrollPaneExtras.setAutoscrolls(true);
            jScrollPaneExtras.setViewportView(jTableExtras);                
        }     
    }    
    private void setTableTeam(){
        if(entity.getTeammembers()!=null) {
/*            List <Person> entities=new ArrayList<Person>();
            for (String s:entity.getTopics())
                if (s!=null) entities.add(AllTopic.getInstance().getEntityById(s));
*/
            teamTableModel = new AllPersonTableModel(entity.getTeammembers());
            jTableTeam = new JTable(teamTableModel);
            jScrollPaneTeam.setAutoscrolls(true);
            jScrollPaneTeam.setViewportView(jTableTeam);                
        }        
    }
    
    private void setAdessoPartnerFields(){
        if (entity.getAdessoPartner()==null) return;
        Person p = entity.getAdessoPartner();
        if (p!=null){
            jTextFieldAdessoPartnerName.setText(p.getName());
            if (p.getEmail()!=null) 
                if(p.getEmail().size()>0) jTextFieldAdessoPartnerEmail.setText(p.getEmail().get(0));
            if (p.getTel()!=null) 
                if(p.getEmail().size()>0) jTextFieldAdessoPartnerTel.setText(p.getTel().get(0));
            AdessoRole role =AllAdessoRole.getInstance().getEntityById(p.getRole());
            if (role!=null)
                jTextFieldAdessoPartnerRoleInAdesso.setText(AllAdessoRole.getInstance().getEntityById(p.getRole()).getName());
        } else {
            jTextFieldAdessoPartnerName.setText(null);
            jTextFieldAdessoPartnerEmail.setText(null);
            jTextFieldAdessoPartnerTel.setText(null);
            jTextFieldAdessoPartnerRoleInAdesso.setText(null);
            
        }
    }

    private void setDeputyPersonField(){
        if (entity==null) return;
        Person person=AllPerson.getInstance().getEntityById(entity.getDeputyName());
//        ProjectRole role=AllProjectRole.getInstance().getEntityById(entity.getDeputyRole());
        if (person!=null){
            jTextFieldDeputyName.setText(person.getName()+" "+person.getForename());            
        } else jTextFieldDeputyName.setText(null);
//        if (role!=null) {
//            jTextFieldDeputyRole.setText(role.getName());
//            //System.out.println("aasda"+role.getName());
//        } else jTextFieldDeputyRole.setText(null);
    }
    private void setDeputyRoleField(){
        if (entity==null) return;
//        Person person=AllPerson.getInstance().getEntityById(entity.getDeputyName());
        ProjectRole role=AllProjectRole.getInstance().getEntityById(entity.getDeputyRole());
//        if (person!=null){
//            jTextFieldDeputyName.setText(person.getName()+" "+person.getForename());            
//        } else jTextFieldDeputyName.setText(null);
        if (role!=null) {
            jTextFieldDeputyRole.setText(role.getName());
            //System.out.println("aasda"+role.getName());
        } else jTextFieldDeputyRole.setText(null);
    }
        
    private void setClientFields(){
        if (entity==null) return;
        Person person = entity.getClientData();
        if (person!=null){
            jTextFieldClientPersonTitel.setText(person.getTitel());
            jTextFieldClientPersonName.setText(person.getForename()+" "+person.getName());
            if(person.getTel()!=null)
                if(person.getTel().size()>0)
                    jTextFieldClientPersonTel.setText(person.getTel().get(0));
            if(person.getEmail()!=null)
                if(person.getEmail().size()>0)
                    jTextFieldClientPersonEmail.setText(person.getEmail().get(0));
            jTextAreaClientPersonAddress.setText(person.addresses2Strings());
            jTextAreaClientTestimonial.setText(person.getTestimonal());            
        } else {
            jTextFieldClientPersonTitel.setText(null);
            jTextFieldClientPersonName.setText(null);
            jTextFieldClientPersonTel.setText(null);
            jTextFieldClientPersonEmail.setText(null);
            jTextAreaClientPersonAddress.setText(null);
            jTextAreaClientTestimonial.setText(null);            
        }
    }
    
    public void setFields() {
        jTextAreaClientProfil.setWrapStyleWord(true);        
        if (entity!=null) {
            if(entity.getId()!=null) jLabelId.setText("id: "+entity.getId());
            MyHelpMethods4GUI.setJTextFeld(jTextFieldClientname, entity.getClientname());
            MyHelpMethods4GUI.setJTextFeld(jTextFieldProjectName, entity.getProjectname());
            
            setLobFields();
            if (AllProjectRole.getInstance().getEntityById(entity.getAdessoPartnerRole())!=null)
                jTextFieldAdessoPartnerRoleInProject.setText(AllProjectRole.getInstance().getEntityById(entity.getAdessoPartnerRole()).getName());
            else jTextFieldAdessoPartnerRoleInProject.setText(null);
            //MyHelpMethods4GUI.setSpinnerOfDate(jSpinnerTimeIntervalStart, )
            if (entity.getTimeInterval()!=null){
                if (entity.getTimeInterval().startTime!=null){
                    jCheckBoxTimeIntervalStart.setSelected(true);                    
                    MyHelpMethods4GUI.setSpinnerOfDate(jSpinnerTimeIntervalStart, entity.getTimeInterval().startTime);   
                    jSpinnerTimeIntervalStart.setEnabled(true);
                } else {
                    jCheckBoxTimeIntervalStart.setSelected(false);
                    jSpinnerTimeIntervalStart.setEnabled(false);
                }
                if (entity.getTimeInterval().endTime!=null){
                    jCheckBoxTimeIntervalEnd.setSelected(true);
                    MyHelpMethods4GUI.setSpinnerOfDate(jSpinnerTimeIntervalEnd, entity.getTimeInterval().endTime);
                    jSpinnerTimeIntervalEnd.setEnabled(true);
                } else {
                    jCheckBoxTimeIntervalEnd.setSelected(false);
                    jSpinnerTimeIntervalEnd.setEnabled(false);
                }
            } else {
                    jCheckBoxTimeIntervalStart.setSelected(false);
                    jSpinnerTimeIntervalStart.setEnabled(false);
                    jCheckBoxTimeIntervalEnd.setSelected(false);
                    jSpinnerTimeIntervalEnd.setEnabled(false);                
            }
            if (entity.getServiceTime()!=null){
                if (entity.getServiceTime().startTime!=null){
                    jCheckBoxServiceTimeStart.setSelected(true);        
                    MyHelpMethods4GUI.setSpinnerOfDate(jSpinnerServiceTimeStart, entity.getServiceTime().startTime);
                    jSpinnerServiceTimeStart.setEnabled(true);
                } else {
                    jCheckBoxServiceTimeStart.setSelected(false);
                    jSpinnerServiceTimeStart.setEnabled(false);
                }
                if (entity.getServiceTime().endTime!=null){
                    jCheckBoxServiceTimeEnd.setSelected(true);        
                    MyHelpMethods4GUI.setSpinnerOfDate(jSpinnerServiceTimeEnd, entity.getServiceTime().endTime);
                    jSpinnerServiceTimeEnd.setEnabled(true);
                } else {
                    jCheckBoxServiceTimeEnd.setSelected(false);
                    jSpinnerServiceTimeEnd.setEnabled(false);
                }            
            }else {
                    jCheckBoxServiceTimeStart.setSelected(false);
                    jSpinnerServiceTimeStart.setEnabled(false);
                    jCheckBoxServiceTimeEnd.setSelected(false);
                    jSpinnerServiceTimeEnd.setEnabled(false);                
            }
            MyHelpMethods4GUI.setJTextFeld(jTextFieldPtTotalProject, entity.getPtTotalProject()+"");
            MyHelpMethods4GUI.setJTextFeld(jTextFieldPtAdesso, entity.getPtAdesso()+"");
            MyHelpMethods4GUI.setJTextFeld(jTextFieldCostTotal, entity.getCostTotal()+"");
            MyHelpMethods4GUI.setJTextFeld(jTextFieldCostAdesso, entity.getCostAdesso()+"");
            
            setTableBranches();
            setTableInvolvedRoles();
            setTableTechnics();
            setTableTopic();
            setTableExtras();
            setTableTeam();
            setTableRefIDList();            
            setRefChangedFields();
            setRefReleasedFields();
            
/*            
            if (entity.getChanged()!=null){
                MyHelpMethods4GUI.setJTextFeld(jTextFieldChangedBy, entity.getChanged().name);
                MyHelpMethods4GUI.setJTextFeld(jTextFieldChangedDescription, entity.getChanged().action);
                MyHelpMethods4GUI.setSpinnerOfDate(jSpinnerChanged, entity.getChanged().date);
            }
            
            if(entity.getReleased()!=null) {
                MyHelpMethods4GUI.setJTextFeld(jTextFieldReleased, entity.getReleased().name);
                MyHelpMethods4GUI.setSpinnerOfDate(jSpinnerReleasedDate, entity.getReleased().date);                
            }
*/            
            if (entity.getDescription()!=null) jTextAreaDescription.setText(entity.getDescription());
            MyHelpMethods4GUI.setJTextFeld(jTextFieldLogo, entity.getClientlogo());
            
            setUserNameTextField(jTextFieldOwner,entity.getOwner());
            setUserNameTextField(jTextFieldEditor,entity.getEditor());
            setUserNameTextField(jTextFieldQA,entity.getqAssurance());
            setUserNameTextField(jTextFieldApprover,entity.getApprover());
            MyHelpMethods4GUI.setSpinnerOfDate(jSpinnerDeadlineEditor, entity.getDeadlineEditor());
            MyHelpMethods4GUI.setSpinnerOfDate(jSpinnerDeadlineQA, entity.getDeadlineQA());
            MyHelpMethods4GUI.setSpinnerOfDate(jSpinnerDeadlineApproval, entity.getDeadlineApproval());

            setAdessoPartnerFields();
            setDeputyPersonField();
            setClientFields();
            setJComboBoxStatus();
            jTextAreaProjectSolution.setText(entity.getProjectSolution());
            jTextAreaProjectBackground.setText(entity.getProjectBackground());
            jTextAreaProjectResults.setText(entity.getProjectResults());
            jTextAreaPreciseDescription.setText(entity.getPreciseDescription());
            jTextAreaClientProfil.setText(entity.getClientProfil());

        }
    }

    private void setTableRefIDList() {
        if(entity.getRefIDList()!=null) {
            List <ReferenceEntity> refList=new ArrayList<ReferenceEntity>();
            for (String s:entity.getRefIDList())
                if (s!=null) refList.add(AllReferenceEntity.getInstance().getEntityById(s));
            referenceTableModel = new AllReferenceTableModel(refList);
            jTableReferrence = new JTable(referenceTableModel);
            jScrollPaneReferrence.setAutoscrolls(true);
            jScrollPaneReferrence.setViewportView(jTableReferrence);
        }
    }

    private void setLobFields() {
        if (entity.getLob()!=null) MyHelpMethods4GUI.setJTextFeld(jTextFieldLOB, AllLobs.getInstance().getEntityById(entity.getLob()).getName());
    }
    
    
    public void setRefChangedFields(){
        if (entity.getChanged()==null){
            jTextFieldChangedBy.setText(null);
            jTextFieldChangedDescription.setText(null);
            return;
        }
        //jTextFieldChangedBy.setText(entity.getChanged().);
//        jTextFieldChangedDescription.setText(entity.getChanged().name);
        MyHelpMethods4GUI.setJTextFeld(jTextFieldChangedDescription, entity.getChanged().action);

        MyHelpMethods4GUI.setSpinnerOfDate(jSpinnerChanged, entity.getChanged().date);
        if(entity.getChanged().eUser!=null){ 
            MyHelpMethods4GUI.setJTextFeld(jTextFieldChangedBy, entity.getChanged().eUser.getUsername());
        }
        
    }
    
    public void getRefChangedFields(){
        if (entity.getChanged()==null) {
            entity.setChanged(new AccessStamp());
            entity.getChanged().date=((SpinnerDateModel)jSpinnerChanged.getModel()).getDate();            
            entity.getChanged().action=jTextFieldChangedDescription.getText();            
        } else {
            entity.getChanged().date=((SpinnerDateModel)jSpinnerChanged.getModel()).getDate();
            entity.getChanged().action=jTextFieldChangedDescription.getText();
            entity.getChanged().name=jTextFieldChangedBy.getText();            
        }
        
    }
    
    public void setRefReleasedFields(){
        jTextFieldReleased.setEnabled(false);        
        if (entity.getReleased()==null){
            jCheckBoxReleased.setSelected(false);
            jTextFieldReleased.setText(null);            
            jSpinnerReleasedDate.setEnabled(false);
            jButtonReleased.setEnabled(false);
            return;
        }
        jCheckBoxReleased.setSelected(true);
        jSpinnerReleasedDate.setEnabled(true);
        jButtonReleased.setEnabled(true);
        MyHelpMethods4GUI.setSpinnerOfDate(jSpinnerReleasedDate, entity.getReleased().date);
        if(entity.getReleased().eUser!=null){ 
            MyHelpMethods4GUI.setJTextFeld(jTextFieldReleased, entity.getReleased().eUser.getUsername());
        }        
        
    }
    
    public void setUserNameTextField(JTextField field, ElasticUser user){
        if (user==null) return;        
        field.setText(user.getUsername());        
    }
    
    
    private ElasticUser setUserInEntity(ElasticUser user){
        if (entity==null) return null;        
        String userID=null;
        if(user!=null) userID=user.getId();        
        String entityID=RefCreatorGUIController.askElasticUserID(userID);
        if (entityID==null) return null;
        return AllElasticUser.getInstance().getEntityById(entityID);        
    }
    
    
    public void getRefReleasedFields(){
        if (!jCheckBoxReleased.isSelected()) {
            entity.setReleased(null);
            return;
        }        
        if (entity.getReleased()==null) {
            entity.setReleased(new AccessStamp());
            entity.getReleased().date=((SpinnerDateModel)jSpinnerReleasedDate.getModel()).getDate(); 
        } else {
            entity.getReleased().date=((SpinnerDateModel)jSpinnerReleasedDate.getModel()).getDate();
        }        
    }
    
    
    public void getFields() {
        if (entity==null) return;
        entity.setClientname(jTextFieldClientname.getText());
        entity.setProjectname(jTextFieldProjectName.getText());
        if (entity.getTimeInterval()==null)
            if(jCheckBoxTimeIntervalStart.isSelected()||jCheckBoxTimeIntervalEnd.isSelected()){
                entity.setTimeInterval(new TimeInterval());
                if (jCheckBoxTimeIntervalStart.isSelected()) 
                    entity.getTimeInterval().startTime=((SpinnerDateModel)jSpinnerTimeIntervalStart.getModel()).getDate();
                else entity.getTimeInterval().startTime=null;
                if (jCheckBoxTimeIntervalEnd.isSelected()) 
                    entity.getTimeInterval().endTime=((SpinnerDateModel)jSpinnerTimeIntervalEnd.getModel()).getDate();
                else entity.getTimeInterval().endTime=null;                
            }

        if (entity.getServiceTime()==null) 
            if(jCheckBoxServiceTimeStart.isSelected()||jCheckBoxServiceTimeEnd.isSelected()) {
                entity.setServiceTime(new TimeInterval());
                if(jCheckBoxServiceTimeStart.isSelected())
                    entity.getServiceTime().startTime=((SpinnerDateModel)jSpinnerServiceTimeStart.getModel()).getDate();
                else entity.getServiceTime().startTime=null;
                if(jCheckBoxServiceTimeEnd.isSelected())
                    entity.getServiceTime().endTime=((SpinnerDateModel)jSpinnerServiceTimeEnd.getModel()).getDate();
                else entity.getServiceTime().endTime=null;                
            } 
        
        getRefChangedFields();
        getRefReleasedFields();
        try { entity.setPtTotalProject(Integer.parseInt(jTextFieldPtTotalProject.getText()));
        } catch (Exception e) {e.printStackTrace();}
        try { entity.setPtAdesso(Integer.parseInt(jTextFieldPtAdesso.getText()));
        } catch (Exception e) {e.printStackTrace();}
        try { entity.setCostTotal(Double.parseDouble(jTextFieldCostTotal.getText()));
        } catch (Exception e) {e.printStackTrace();}
        try {  entity.setCostAdesso(Double.parseDouble(jTextFieldCostAdesso.getText()));
        } catch (Exception e) {e.printStackTrace();}
        entity.setDescription(jTextAreaDescription.getText());
        entity.setClientlogo(jTextFieldLogo.getText());
        
        entity.setPreciseDescription(jTextAreaPreciseDescription.getText());
        entity.setProjectSolution(jTextAreaProjectSolution.getText());
        entity.setProjectBackground(jTextAreaProjectBackground.getText());
        entity.setProjectResults(jTextAreaProjectResults.getText());
        entity.setClientProfil(jTextAreaClientProfil.getText());
        
    }
    

    /**
     * Creates new form UpdateBranchDialog
     */
    public AllReferenceUpdateDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        entity=new ReferenceEntity();
        setFields();
    }

    public AllReferenceUpdateDialog(java.awt.Frame parent, ReferenceEntity entity, boolean modal) {
        super(parent, modal);
        initComponents();
        this.entity=entity;
        setFields();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelId = new javax.swing.JLabel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelMetadata = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldProjectName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldClientname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldLOB = new javax.swing.JTextField();
        jPanelBranches = new javax.swing.JPanel();
        jScrollPaneBranch = new javax.swing.JScrollPane();
        jTableBranch = new javax.swing.JTable();
        jButtonInsertBranch = new javax.swing.JButton();
        jButtonDelBranch = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jSpinnerTimeIntervalStart = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jSpinnerTimeIntervalEnd = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jSpinnerServiceTimeStart = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jSpinnerServiceTimeEnd = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldPtTotalProject = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldPtAdesso = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldCostTotal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldCostAdesso = new javax.swing.JTextField();
        jPanelReferences = new javax.swing.JPanel();
        jScrollPaneReferrence = new javax.swing.JScrollPane();
        jTableReferrence = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldChangedBy = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldChangedDescription = new javax.swing.JTextField();
        jSpinnerChanged = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        jSpinnerReleasedDate = new javax.swing.JSpinner();
        jTextFieldReleased = new javax.swing.JTextField();
        jButtonReleased = new javax.swing.JButton();
        jCheckBoxReleased = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaDescription = new javax.swing.JTextArea();
        jButton31 = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jTextFieldLogo = new javax.swing.JTextField();
        jCheckBoxServiceTimeStart = new javax.swing.JCheckBox();
        jCheckBoxServiceTimeEnd = new javax.swing.JCheckBox();
        jCheckBoxTimeIntervalStart = new javax.swing.JCheckBox();
        jCheckBoxTimeIntervalEnd = new javax.swing.JCheckBox();
        jPanelAdmin = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldOwner = new javax.swing.JTextField();
        jButtonOwner = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldEditor = new javax.swing.JTextField();
        jTextFieldQA = new javax.swing.JTextField();
        jTextFieldApprover = new javax.swing.JTextField();
        jButtonEditor = new javax.swing.JButton();
        jButtonQA = new javax.swing.JButton();
        jButtonApprover = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jSpinnerDeadlineEditor = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        jSpinnerDeadlineQA = new javax.swing.JSpinner();
        jLabel21 = new javax.swing.JLabel();
        jSpinnerDeadlineApproval = new javax.swing.JSpinner();
        jPanel3 = new javax.swing.JPanel();
        jScrollPaneInvolvedRoles = new javax.swing.JScrollPane();
        jTableInvolvedRoles = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanelTeam = new javax.swing.JPanel();
        jScrollPaneTeam = new javax.swing.JScrollPane();
        jTableTeam = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanelTechnics = new javax.swing.JPanel();
        jScrollPaneTechnics = new javax.swing.JScrollPane();
        jTableTechnics = new javax.swing.JTable();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jPanelTopic = new javax.swing.JPanel();
        jScrollPaneTopic = new javax.swing.JScrollPane();
        jTableTopic = new javax.swing.JTable();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jPanelExtras = new javax.swing.JPanel();
        jScrollPaneExtras = new javax.swing.JScrollPane();
        jTableExtras = new javax.swing.JTable();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jPanelContacts = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jTextFieldAdessoPartnerRoleInProject = new javax.swing.JTextField();
        jButton21 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jTextFieldAdessoPartnerTel = new javax.swing.JTextField();
        jTextFieldAdessoPartnerEmail = new javax.swing.JTextField();
        jTextFieldAdessoPartnerName = new javax.swing.JTextField();
        jTextFieldAdessoPartnerRoleInAdesso = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jTextFieldDeputyName = new javax.swing.JTextField();
        jTextFieldDeputyRole = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jTextFieldClientPersonTitel = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jTextFieldClientPersonName = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTextFieldClientPersonEmail = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jTextFieldClientPersonTel = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextAreaClientTestimonial = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaClientPersonAddress = new javax.swing.JTextArea();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextAreaClientProfil = new javax.swing.JTextArea();
        jPanelAusgangslage = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextAreaProjectBackground = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTextAreaProjectSolution = new javax.swing.JTextArea();
        jPanel16 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTextAreaProjectResults = new javax.swing.JTextArea();
        jPanel17 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTextAreaPreciseDescription = new javax.swing.JTextArea();
        jComboBoxStatus = new javax.swing.JComboBox();
        jLabel39 = new javax.swing.JLabel();
        jButton30 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelId.setText("id:");

        jButtonOk.setText("Ok");
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jLabel1.setText("ProjektName");

        jLabel2.setText("Kundenname");

        jLabel3.setText("LOB");

        jTextFieldLOB.setEditable(false);

        jPanelBranches.setBorder(javax.swing.BorderFactory.createTitledBorder("Branche"));

        jTableBranch.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPaneBranch.setViewportView(jTableBranch);

        jButtonInsertBranch.setText("Add");
        jButtonInsertBranch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertBranchActionPerformed(evt);
            }
        });

        jButtonDelBranch.setText("Delete");
        jButtonDelBranch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDelBranchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelBranchesLayout = new javax.swing.GroupLayout(jPanelBranches);
        jPanelBranches.setLayout(jPanelBranchesLayout);
        jPanelBranchesLayout.setHorizontalGroup(
            jPanelBranchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBranchesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBranchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneBranch, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBranchesLayout.createSequentialGroup()
                        .addComponent(jButtonInsertBranch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDelBranch)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelBranchesLayout.setVerticalGroup(
            jPanelBranchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBranchesLayout.createSequentialGroup()
                .addComponent(jScrollPaneBranch, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBranchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonInsertBranch)
                    .addComponent(jButtonDelBranch)))
        );

        jLabel4.setText("Zeitraum von");

        jSpinnerTimeIntervalStart.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(946681200000L), null, null, java.util.Calendar.YEAR));

        jLabel5.setText("bis");

        jSpinnerTimeIntervalEnd.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.MONTH));

        jLabel6.setText("Wartung von");

        jSpinnerServiceTimeStart.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(946681200000L), null, null, java.util.Calendar.DAY_OF_MONTH));

        jLabel7.setText("bis");

        jSpinnerServiceTimeEnd.setModel(new javax.swing.SpinnerDateModel());

        jLabel8.setText("PT Gesamtprojekt");

        jLabel9.setText("PT adesso");

        jLabel10.setText("Volumen in € Gesamtprojekt");

        jTextFieldCostTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCostTotalActionPerformed(evt);
            }
        });

        jLabel11.setText("Volumen in € adesso");

        jPanelReferences.setBorder(javax.swing.BorderFactory.createTitledBorder("Zugehörige Referenzen"));

        jTableReferrence.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPaneReferrence.setViewportView(jTableReferrence);

        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelReferencesLayout = new javax.swing.GroupLayout(jPanelReferences);
        jPanelReferences.setLayout(jPanelReferencesLayout);
        jPanelReferencesLayout.setHorizontalGroup(
            jPanelReferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReferencesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelReferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneReferrence)
                    .addGroup(jPanelReferencesLayout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanelReferencesLayout.setVerticalGroup(
            jPanelReferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReferencesLayout.createSequentialGroup()
                .addComponent(jScrollPaneReferrence, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelReferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Letzte Änderung"));

        jLabel12.setText("Bei");

        jTextFieldChangedBy.setText("jTextField1");
        jTextFieldChangedBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldChangedByActionPerformed(evt);
            }
        });

        jButton3.setText("Edit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel13.setText("Grund");

        jTextFieldChangedDescription.setText("jTextField1");

        jSpinnerChanged.setModel(new javax.swing.SpinnerDateModel());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldChangedBy)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldChangedDescription)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinnerChanged, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldChangedBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldChangedDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSpinnerChanged, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Freigegeben durch /am"));

        jSpinnerReleasedDate.setModel(new javax.swing.SpinnerDateModel());

        jTextFieldReleased.setEditable(false);
        jTextFieldReleased.setText("bla bla bla!!!");

        jButtonReleased.setText("Edit");
        jButtonReleased.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReleasedActionPerformed(evt);
            }
        });

        jCheckBoxReleased.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxReleasedItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jCheckBoxReleased)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinnerReleasedDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldReleased)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonReleased))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBoxReleased)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jSpinnerReleasedDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldReleased, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonReleased)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabel14.setText("Notizen");

        jTextAreaDescription.setColumns(20);
        jTextAreaDescription.setRows(5);
        jScrollPane3.setViewportView(jTextAreaDescription);

        jButton31.setText("Edit");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jLabel40.setText("logo");

        jTextFieldLogo.setText("jTextField1");

        jCheckBoxServiceTimeStart.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxServiceTimeStartItemStateChanged(evt);
            }
        });

        jCheckBoxServiceTimeEnd.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxServiceTimeEndItemStateChanged(evt);
            }
        });

        jCheckBoxTimeIntervalStart.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxTimeIntervalStartItemStateChanged(evt);
            }
        });

        jCheckBoxTimeIntervalEnd.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxTimeIntervalEndItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanelMetadataLayout = new javax.swing.GroupLayout(jPanelMetadata);
        jPanelMetadata.setLayout(jPanelMetadataLayout);
        jPanelMetadataLayout.setHorizontalGroup(
            jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMetadataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMetadataLayout.createSequentialGroup()
                        .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMetadataLayout.createSequentialGroup()
                                .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMetadataLayout.createSequentialGroup()
                                        .addComponent(jTextFieldLOB)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton31))
                                    .addComponent(jTextFieldProjectName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanelMetadataLayout.createSequentialGroup()
                                        .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(jPanelMetadataLayout.createSequentialGroup()
                                                .addComponent(jCheckBoxServiceTimeStart)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSpinnerServiceTimeStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanelMetadataLayout.createSequentialGroup()
                                                .addComponent(jCheckBoxTimeIntervalStart)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jSpinnerTimeIntervalStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanelMetadataLayout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jCheckBoxTimeIntervalEnd)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jSpinnerTimeIntervalEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanelMetadataLayout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jCheckBoxServiceTimeEnd)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSpinnerServiceTimeEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(jPanelMetadataLayout.createSequentialGroup()
                                .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldCostTotal)
                                    .addComponent(jTextFieldCostAdesso)))
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelMetadataLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldPtTotalProject, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldPtAdesso)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelReferences, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelMetadataLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldClientname))
                            .addComponent(jPanelBranches, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane3)
                    .addGroup(jPanelMetadataLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelMetadataLayout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldLogo)))
                .addContainerGap())
        );
        jPanelMetadataLayout.setVerticalGroup(
            jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMetadataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldClientname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMetadataLayout.createSequentialGroup()
                        .addComponent(jPanelBranches, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelReferences, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelMetadataLayout.createSequentialGroup()
                        .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldLOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jSpinnerTimeIntervalStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(jSpinnerTimeIntervalEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCheckBoxTimeIntervalEnd))
                            .addComponent(jCheckBoxTimeIntervalStart, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6)
                                .addComponent(jCheckBoxServiceTimeStart))
                            .addComponent(jSpinnerServiceTimeEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jSpinnerServiceTimeStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7)
                                .addComponent(jCheckBoxServiceTimeEnd)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextFieldPtTotalProject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jTextFieldPtAdesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jTextFieldCostTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jTextFieldCostAdesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jTextFieldLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Metadaten", jPanelMetadata);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Administratives"));

        jLabel15.setText("Owner");

        jTextFieldOwner.setEditable(false);

        jButtonOwner.setText("ändern");
        jButtonOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOwnerActionPerformed(evt);
            }
        });

        jLabel16.setText("Editor");

        jLabel17.setText("Quality Assurance");

        jLabel18.setText("Approver");

        jTextFieldEditor.setEditable(false);

        jTextFieldQA.setEditable(false);

        jTextFieldApprover.setEditable(false);

        jButtonEditor.setText("ändern");
        jButtonEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditorActionPerformed(evt);
            }
        });

        jButtonQA.setText("ändern");
        jButtonQA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonQAActionPerformed(evt);
            }
        });

        jButtonApprover.setText("ändern");
        jButtonApprover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApproverActionPerformed(evt);
            }
        });

        jLabel19.setText("Deadline Editor");

        jSpinnerDeadlineEditor.setModel(new javax.swing.SpinnerDateModel());

        jLabel20.setText("Deadline QA");

        jSpinnerDeadlineQA.setModel(new javax.swing.SpinnerDateModel());

        jLabel21.setText("Deadline Approval");

        jSpinnerDeadlineApproval.setModel(new javax.swing.SpinnerDateModel());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldApprover)
                                    .addComponent(jTextFieldQA, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextFieldEditor, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextFieldOwner, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonOwner, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButtonEditor, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButtonQA, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButtonApprover, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jSpinnerDeadlineEditor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel20)
                                .addGap(18, 18, 18)
                                .addComponent(jSpinnerDeadlineQA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSpinnerDeadlineApproval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextFieldOwner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonOwner))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextFieldEditor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEditor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jTextFieldQA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonQA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTextFieldApprover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonApprover))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jSpinnerDeadlineEditor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jSpinnerDeadlineQA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jSpinnerDeadlineApproval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Involvierte Rollen"));

        jTableInvolvedRoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPaneInvolvedRoles.setViewportView(jTableInvolvedRoles);

        jButton6.setText("Add");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Edit");
        jButton7.setEnabled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Delete");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPaneInvolvedRoles)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPaneInvolvedRoles, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addContainerGap())
        );

        jPanelTeam.setBorder(javax.swing.BorderFactory.createTitledBorder("Mitglieder des Projektteams"));

        jTableTeam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPaneTeam.setViewportView(jTableTeam);

        jButton9.setText("Add");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Edit");
        jButton10.setEnabled(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Delete");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTeamLayout = new javax.swing.GroupLayout(jPanelTeam);
        jPanelTeam.setLayout(jPanelTeamLayout);
        jPanelTeamLayout.setHorizontalGroup(
            jPanelTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTeamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPaneTeam)
        );
        jPanelTeamLayout.setVerticalGroup(
            jPanelTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTeamLayout.createSequentialGroup()
                .addComponent(jScrollPaneTeam, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(jButton11))
                .addContainerGap())
        );

        jPanelTechnics.setBorder(javax.swing.BorderFactory.createTitledBorder("Verwendete Technologien und Methoden"));

        jTableTechnics.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPaneTechnics.setViewportView(jTableTechnics);

        jButton12.setText("Add");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("Edit");
        jButton13.setEnabled(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setText("Delete");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTechnicsLayout = new javax.swing.GroupLayout(jPanelTechnics);
        jPanelTechnics.setLayout(jPanelTechnicsLayout);
        jPanelTechnicsLayout.setHorizontalGroup(
            jPanelTechnicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTechnicsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton14)
                .addContainerGap(303, Short.MAX_VALUE))
            .addComponent(jScrollPaneTechnics)
        );
        jPanelTechnicsLayout.setVerticalGroup(
            jPanelTechnicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTechnicsLayout.createSequentialGroup()
                .addComponent(jScrollPaneTechnics, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTechnicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton13)
                    .addComponent(jButton14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelTopic.setBorder(javax.swing.BorderFactory.createTitledBorder("Schwerpunktthemen, beson. Augenmerk, Besonderheiten"));

        jTableTopic.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPaneTopic.setViewportView(jTableTopic);

        jButton15.setText("Add");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setText("Edit");
        jButton16.setEnabled(false);
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setText("Delete");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTopicLayout = new javax.swing.GroupLayout(jPanelTopic);
        jPanelTopic.setLayout(jPanelTopicLayout);
        jPanelTopicLayout.setHorizontalGroup(
            jPanelTopicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopicLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTopicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneTopic, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                    .addGroup(jPanelTopicLayout.createSequentialGroup()
                        .addComponent(jButton15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton17)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelTopicLayout.setVerticalGroup(
            jPanelTopicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopicLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneTopic, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanelTopicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton15)
                    .addComponent(jButton16)
                    .addComponent(jButton17))
                .addContainerGap())
        );

        jPanelExtras.setBorder(javax.swing.BorderFactory.createTitledBorder("Besonderheiten  (z.B. Bieterkonstellation)"));

        jTableExtras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPaneExtras.setViewportView(jTableExtras);

        jButton18.setText("Add");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setText("Edit");
        jButton19.setEnabled(false);

        jButton20.setText("Delete");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelExtrasLayout = new javax.swing.GroupLayout(jPanelExtras);
        jPanelExtras.setLayout(jPanelExtrasLayout);
        jPanelExtrasLayout.setHorizontalGroup(
            jPanelExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelExtrasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneExtras)
                    .addGroup(jPanelExtrasLayout.createSequentialGroup()
                        .addComponent(jButton18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton20)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelExtrasLayout.setVerticalGroup(
            jPanelExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelExtrasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneExtras, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton18)
                    .addComponent(jButton19)
                    .addComponent(jButton20))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelAdminLayout = new javax.swing.GroupLayout(jPanelAdmin);
        jPanelAdmin.setLayout(jPanelAdminLayout);
        jPanelAdminLayout.setHorizontalGroup(
            jPanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTopic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelExtras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTeam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTechnics, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelAdminLayout.setVerticalGroup(
            jPanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAdminLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelTeam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanelTechnics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelAdminLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelTopic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelExtras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Administratives", jPanelAdmin);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("adesso-Ansprechpartner"));

        jLabel22.setText("Rolle im Projekt");

        jTextFieldAdessoPartnerRoleInProject.setEditable(false);

        jButton21.setText("Edit");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel23.setText("Name");

        jLabel24.setText("Email");

        jLabel25.setText("Telefonnummer");

        jLabel26.setText("Rolle bei adesso");

        jButton22.setText("Add");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setText("Edit");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setText("Delete");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jTextFieldAdessoPartnerTel.setEditable(false);
        jTextFieldAdessoPartnerTel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAdessoPartnerTelActionPerformed(evt);
            }
        });

        jTextFieldAdessoPartnerEmail.setEditable(false);

        jTextFieldAdessoPartnerName.setEditable(false);

        jTextFieldAdessoPartnerRoleInAdesso.setEditable(false);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jButton22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton24)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldAdessoPartnerName)
                            .addComponent(jTextFieldAdessoPartnerEmail)
                            .addComponent(jTextFieldAdessoPartnerTel)
                            .addComponent(jTextFieldAdessoPartnerRoleInAdesso))))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTextFieldAdessoPartnerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jTextFieldAdessoPartnerEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jTextFieldAdessoPartnerTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jTextFieldAdessoPartnerRoleInAdesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton22)
                    .addComponent(jButton23)
                    .addComponent(jButton24))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Delete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldAdessoPartnerRoleInProject, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jTextFieldAdessoPartnerRoleInProject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton21)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Stellvertreter des adesso-Ansprechpartners"));

        jLabel27.setText("Name");

        jLabel28.setText("Rolle im Projekt");

        jButton25.setText("Edit");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton26.setText("Edit");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jTextFieldDeputyName.setEditable(false);

        jTextFieldDeputyRole.setEditable(false);

        jButton5.setText("Delete");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton32.setText("Delete");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldDeputyName))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldDeputyRole)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jButton25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jButton26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton32))))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jButton25)
                    .addComponent(jTextFieldDeputyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jTextFieldDeputyRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton26)
                    .addComponent(jButton32))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Kontaktdaten Kunde"));

        jLabel29.setText("Titel");

        jTextFieldClientPersonTitel.setEditable(false);

        jLabel30.setText("Name");

        jTextFieldClientPersonName.setEditable(false);

        jLabel31.setText("Email");

        jTextFieldClientPersonEmail.setEditable(false);

        jLabel32.setText("Telefonnummer");

        jTextFieldClientPersonTel.setEditable(false);

        jLabel33.setText("Anschrift");

        jLabel34.setText("Testimonial Kunde");

        jButton27.setText("Add");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jButton28.setText("Edit");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jButton29.setText("Delete");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jTextAreaClientTestimonial.setEditable(false);
        jTextAreaClientTestimonial.setColumns(20);
        jTextAreaClientTestimonial.setRows(5);
        jScrollPane10.setViewportView(jTextAreaClientTestimonial);

        jTextAreaClientPersonAddress.setEditable(false);
        jTextAreaClientPersonAddress.setColumns(20);
        jTextAreaClientPersonAddress.setRows(5);
        jScrollPane1.setViewportView(jTextAreaClientPersonAddress);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldClientPersonTitel, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldClientPersonName))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldClientPersonEmail))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldClientPersonTel))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jButton27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton29))
                            .addComponent(jLabel33)
                            .addComponent(jLabel34))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jTextFieldClientPersonTitel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(jTextFieldClientPersonName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jTextFieldClientPersonEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jTextFieldClientPersonTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton28)
                        .addComponent(jButton29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jButton27)
                        .addContainerGap())))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Kundenprofil"));

        jTextAreaClientProfil.setColumns(20);
        jTextAreaClientProfil.setRows(5);
        jScrollPane9.setViewportView(jTextAreaClientProfil);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelContactsLayout = new javax.swing.GroupLayout(jPanelContacts);
        jPanelContacts.setLayout(jPanelContactsLayout);
        jPanelContactsLayout.setHorizontalGroup(
            jPanelContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContactsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelContactsLayout.createSequentialGroup()
                        .addGroup(jPanelContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelContactsLayout.setVerticalGroup(
            jPanelContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContactsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelContactsLayout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Kontakte", jPanelContacts);

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel35.setText("Ausgangslage/Projekthintergrund/Herausforderung");

        jTextAreaProjectBackground.setColumns(20);
        jTextAreaProjectBackground.setRows(5);
        jScrollPane11.setViewportView(jTextAreaProjectBackground);

        javax.swing.GroupLayout jPanelAusgangslageLayout = new javax.swing.GroupLayout(jPanelAusgangslage);
        jPanelAusgangslage.setLayout(jPanelAusgangslageLayout);
        jPanelAusgangslageLayout.setHorizontalGroup(
            jPanelAusgangslageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAusgangslageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAusgangslageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11)
                    .addGroup(jPanelAusgangslageLayout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(0, 600, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelAusgangslageLayout.setVerticalGroup(
            jPanelAusgangslageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAusgangslageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Ausgangslage/Projekthintergrund/Herausforderung", jPanelAusgangslage);

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel36.setText("Lösungsansatz");

        jTextAreaProjectSolution.setColumns(20);
        jTextAreaProjectSolution.setRows(5);
        jScrollPane12.setViewportView(jTextAreaProjectSolution);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 1139, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Lösungsansatz", jPanel10);

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel37.setText("Ergebnis und Kundenmehrwert");

        jTextAreaProjectResults.setColumns(20);
        jTextAreaProjectResults.setRows(5);
        jScrollPane13.setViewportView(jTextAreaProjectResults);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane13)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addGap(0, 812, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Ergebnis und Kundenmehrwert", jPanel16);

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel38.setText("Ausführliche Projektbeschreibung");

        jTextAreaPreciseDescription.setColumns(20);
        jTextAreaPreciseDescription.setRows(5);
        jScrollPane14.setViewportView(jTextAreaPreciseDescription);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addGap(0, 788, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Ausführliche Projektbeschreibung", jPanel17);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jComboBoxStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "test1", "test2" }));
        jComboBoxStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxStatusItemStateChanged(evt);
            }
        });
        jComboBoxStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxStatusActionPerformed(evt);
            }
        });

        jLabel39.setText("Freigabestatus");

        jButton30.setText("Test");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton30)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelId)
                    .addComponent(jComboBoxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOk)
                    .addComponent(jButtonCancel)
                    .addComponent(jButton30))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        // TODO add your handling code here:
        getFields();
        dispose();
    }//GEN-LAST:event_jButtonOkActionPerformed

    private void jTextFieldAdessoPartnerTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAdessoPartnerTelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAdessoPartnerTelActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
        if (entity==null) return;
        Person p=entity.getAdessoPartner();
        p=RefCreatorGUIController.askPersonID(p);
        if (p==null) return;
        entity.setAdessoPartner(p);
        //MyHelpMethods4GUI.setJTextFeld(jTextFieldLOB, AllL);
        //setFields();                    
        setAdessoPartnerFields();
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
        if (entity==null) return;
        entity.setClientData(null);
        //setFields();
        setClientFields();
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jComboBoxStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxStatusItemStateChanged
        // TODO add your handling code here:
        getJComboBoxStatus(); 
    }//GEN-LAST:event_jComboBoxStatusItemStateChanged

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:
        getFields();       
        System.out.println(MyHelpMethods.object2PrettyString(entity));
        //System.out.println("");
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
        String entityID=RefCreatorGUIController.askLobID(entity.getLob());
        if (entityID==null) return;
        entity.setLob(entityID);
        setLobFields();
        //MyHelpMethods4GUI.setJTextFeld(jTextFieldLOB, AllL);
        //setFields();
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButtonInsertBranchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertBranchActionPerformed
        // TODO add your handling code here:
        String entityID=RefCreatorGUIController.askBranchID();
        if (entityID==null) return;
        if (entity.getBranchList()==null) entity.setBranchList(new ArrayList<String>());
        entity.getBranchList().add(entityID);
        //setFields();
        setTableBranches();
        
    }//GEN-LAST:event_jButtonInsertBranchActionPerformed

    private void jButtonDelBranchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDelBranchActionPerformed
        // TODO add your handling code here:
        int [] sRows = jTableBranch.getSelectedRows();
        if (sRows.length<=0) return;
            entity.getBranchList().remove(sRows[0]);
        setTableBranches();            
        //setFields();
    }//GEN-LAST:event_jButtonDelBranchActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String entityID=RefCreatorGUIController.askReferenceID();
        if (entityID==null) return;
        if (entity.getRefIDList()==null) entity.setRefIDList(new ArrayList<String>());
        entity.getRefIDList().add(entityID);
        setTableRefIDList();
        //setFields();        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int [] sRows = jTableReferrence.getSelectedRows();
        if (sRows.length<=0) return;
            entity.getRefIDList().remove(sRows[0]);
        setTableRefIDList();    
        //setFields();        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (entity==null) return;
        String userID=null;
        if (entity.getChanged()!=null)
            if(entity.getChanged().eUser!=null)
                userID=entity.getChanged().eUser.getId();
        
        String entityID=RefCreatorGUIController.askElasticUserID(userID);
        if (entityID==null) return;
        if (entity.getChanged()==null) {
            entity.setChanged(new AccessStamp());
            entity.getChanged().date=new Date();
            entity.getChanged().eUser=AllElasticUser.getInstance().getEntityById(entityID);
        } else entity.getChanged().eUser=AllElasticUser.getInstance().getEntityById(entityID);
        
        //MyHelpMethods4GUI.setJTextFeld(jTextFieldLOB, AllL);
        //setFields();        
        setRefChangedFields();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButtonReleasedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReleasedActionPerformed
        // TODO add your handling code here:
        if (entity==null) return;
        String userID=null;
        if (entity.getReleased()!=null)
            if(entity.getReleased().eUser!=null)
                userID=entity.getReleased().eUser.getId();
        
        String entityID=RefCreatorGUIController.askElasticUserID(userID);
        if (entityID==null) return;
        if (entity.getReleased()==null) {
            entity.setReleased(new AccessStamp());
            entity.getReleased().date=new Date();
            entity.getReleased().eUser=AllElasticUser.getInstance().getEntityById(entityID);
        } else entity.getReleased().eUser=AllElasticUser.getInstance().getEntityById(entityID);
        
        //MyHelpMethods4GUI.setJTextFeld(jTextFieldLOB, AllL);
        //setFields();        
        setRefReleasedFields();
        
    }//GEN-LAST:event_jButtonReleasedActionPerformed

    private void jButtonOwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOwnerActionPerformed
        // TODO add your handling code here:
        if (entity==null) return;
        entity.setOwner(setUserInEntity(entity.getOwner()));
        //setFields();
        setUserNameTextField(jTextFieldOwner,entity.getOwner());
    }//GEN-LAST:event_jButtonOwnerActionPerformed

    private void jButtonEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditorActionPerformed
        // TODO add your handling code here:
        if (entity==null) return;
        entity.setEditor(setUserInEntity(entity.getEditor()));
        setUserNameTextField(jTextFieldEditor,entity.getEditor());
        //setFields();
        
    }//GEN-LAST:event_jButtonEditorActionPerformed

    private void jButtonQAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonQAActionPerformed
        // TODO add your handling code here:
        if (entity==null) return;
        entity.setqAssurance(setUserInEntity(entity.getqAssurance()));
        setUserNameTextField(jTextFieldQA,entity.getqAssurance());
        
        //setFields();        
    }//GEN-LAST:event_jButtonQAActionPerformed

    private void jButtonApproverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApproverActionPerformed
        // TODO add your handling code here:
        if (entity==null) return;
        entity.setApprover(setUserInEntity(entity.getApprover()));
        setUserNameTextField(jTextFieldApprover,entity.getApprover());                
        
//        setFields();        
    }//GEN-LAST:event_jButtonApproverActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        String entityID=RefCreatorGUIController.askInvolvedRoleID();
        if (entityID==null) return;
        if (entity.getRoles()==null) entity.setRoles(new ArrayList<String>());
        entity.getRoles().add(entityID);
        //setFields();        
        setTableInvolvedRoles();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        int [] sRows = jTableInvolvedRoles.getSelectedRows();
        if (sRows.length<=0) return;
            entity.getRoles().remove(sRows[0]);
        //setFields();        
        setTableInvolvedRoles();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTextFieldCostTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCostTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCostTotalActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        String entityID=RefCreatorGUIController.askProjectRoleID(entity.getAdessoPartnerRole());
        if (entityID==null) return;
        entity.setAdessoPartnerRole(entityID);                
       // setFields();        
        if (AllProjectRole.getInstance().getEntityById(entity.getAdessoPartnerRole())!=null)
            jTextFieldAdessoPartnerRoleInProject.setText(AllProjectRole.getInstance().getEntityById(entity.getAdessoPartnerRole()).getName());
        else jTextFieldAdessoPartnerRoleInProject.setText(null);
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
        Person p=null;
        p=RefCreatorGUIController.askPersonID(p);
        if (p==null) return;
        entity.setAdessoPartner(p);
        //MyHelpMethods4GUI.setJTextFeld(jTextFieldLOB, AllL);
        //setFields();           
        setAdessoPartnerFields();
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:
        String entityID=RefCreatorGUIController.askProjectRoleID(entity.getDeputyRole());
        if (entityID==null) return;
        entity.setDeputyRole(entityID);
        //setFields();          
        setDeputyRoleField();
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:        
        if (entity==null) return;
        entity.setDeputyName(RefCreatorGUIController.askPersonID(entity.getDeputyName()));
        //setFields();         
        setDeputyPersonField();
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
        Person p=null;
        p=RefCreatorGUIController.askPersonID(p);
        if (p==null) return;
        entity.setClientData(p);
        //MyHelpMethods4GUI.setJTextFeld(jTextFieldLOB, AllL);
        //setFields();        
        setClientFields();
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (entity==null) return;
        Person p=entity.getClientData();
        p=RefCreatorGUIController.askPersonID(p);
        if (p==null) return;
        entity.setClientData(p);
        //MyHelpMethods4GUI.setJTextFeld(jTextFieldLOB, AllL);
        //setFields();        
        setClientFields();
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        int [] sRows = jTableTeam.getSelectedRows();
        if (sRows.length<=0) return;
            entity.getTeammembers().remove(sRows[0]);
        setTableTeam();            
        //setFields();         
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        int [] sRows = jTableTopic.getSelectedRows();
        if (sRows.length<=0) return;
            entity.getTopics().remove(sRows[0]);
        //setFields();           
        setTableTopic();            
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        if (entity==null) return;
        String oldID=null;
        String entityID=RefCreatorGUIController.askTopicID(oldID);
        if (entityID==null) return;
        if (entity.getTopics()==null) entity.setTopics(new ArrayList<String>());        
        entity.getTopics().add(entityID);
        //setFields();
        setTableTopic();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        if (entity==null) return;
        String oldID=null;
        String entityID=RefCreatorGUIController.askExtrasID(oldID);
        if (entityID==null) return;
        if (entity.getExtras()==null) entity.setExtras(new ArrayList<String>());
        entity.getExtras().add(entityID);
        setTableExtras();
        //setFields();            
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        if (entity==null) return;
        String oldID=null;
        String entityID=RefCreatorGUIController.askTechnoloyID(oldID);
        if (entityID==null) return;
        if (entity.getTechnics()==null) entity.setTechnics(new ArrayList<String>());
        entity.getTechnics().add(entityID);
        setTableTechnics();
        //setFields();         
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        if (entity==null) return;
        entity.setAdessoPartner(null);
        //setFields();
        setAdessoPartnerFields();
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        Person p=null;
        p=RefCreatorGUIController.askPersonID(p);
        if (p==null) return;
        if (entity.getTeammembers()==null) entity.setTeammembers(new ArrayList<Person>());
        entity.getTeammembers().add(p);
        //MyHelpMethods4GUI.setJTextFeld(jTextFieldLOB, AllL);
        //setFields();           
        setTableTeam();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        int [] sRows = jTableTechnics.getSelectedRows();
        if (sRows.length<=0) return;
            entity.getTechnics().remove(sRows[0]);
        setFields();        
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        int [] sRows = jTableExtras.getSelectedRows();
        if (sRows.length<=0) return;
            entity.getExtras().remove(sRows[0]);
        setFields();   
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jCheckBoxTimeIntervalStartItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxTimeIntervalStartItemStateChanged
        // TODO add your handling code here:
        jSpinnerTimeIntervalStart.setEnabled(jCheckBoxTimeIntervalStart.isSelected());
    }//GEN-LAST:event_jCheckBoxTimeIntervalStartItemStateChanged

    private void jCheckBoxTimeIntervalEndItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxTimeIntervalEndItemStateChanged
        // TODO add your handling code here:
        jSpinnerTimeIntervalEnd.setEnabled(jCheckBoxTimeIntervalEnd.isSelected());        
    }//GEN-LAST:event_jCheckBoxTimeIntervalEndItemStateChanged

    private void jCheckBoxServiceTimeStartItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxServiceTimeStartItemStateChanged
        // TODO add your handling code here:
        jSpinnerServiceTimeStart.setEnabled(jCheckBoxServiceTimeStart.isSelected());             
    }//GEN-LAST:event_jCheckBoxServiceTimeStartItemStateChanged

    private void jCheckBoxServiceTimeEndItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxServiceTimeEndItemStateChanged
        // TODO add your handling code here:
        jSpinnerServiceTimeEnd.setEnabled(jCheckBoxServiceTimeEnd.isSelected());          
    }//GEN-LAST:event_jCheckBoxServiceTimeEndItemStateChanged

    private void jCheckBoxReleasedItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxReleasedItemStateChanged
        // TODO add your handling code here:
        boolean isReleased= jCheckBoxReleased.isSelected();
        jSpinnerReleasedDate.setEnabled(isReleased);
        jButtonReleased.setEnabled(isReleased);
    }//GEN-LAST:event_jCheckBoxReleasedItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        entity.setAdessoPartnerRole(null);
        //setFields();                
        if (AllProjectRole.getInstance().getEntityById(entity.getAdessoPartnerRole())!=null)
            jTextFieldAdessoPartnerRoleInProject.setText(AllProjectRole.getInstance().getEntityById(entity.getAdessoPartnerRole()).getName());
        else jTextFieldAdessoPartnerRoleInProject.setText(null);        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (entity==null) return;
        entity.setDeputyName(null);
        //setFields();         
        setDeputyPersonField();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        // TODO add your handling code here:
        if (entity==null) return;
        entity.setDeputyRole(null);
        //setFields();              
        setDeputyRoleField();
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jTextFieldChangedByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldChangedByActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldChangedByActionPerformed

    private void jComboBoxStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxStatusActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void runMain(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AllReferenceUpdateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AllReferenceUpdateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AllReferenceUpdateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AllReferenceUpdateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AllReferenceUpdateDialog dialog = new AllReferenceUpdateDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonApprover;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDelBranch;
    private javax.swing.JButton jButtonEditor;
    private javax.swing.JButton jButtonInsertBranch;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JButton jButtonOwner;
    private javax.swing.JButton jButtonQA;
    private javax.swing.JButton jButtonReleased;
    private javax.swing.JCheckBox jCheckBoxReleased;
    private javax.swing.JCheckBox jCheckBoxServiceTimeEnd;
    private javax.swing.JCheckBox jCheckBoxServiceTimeStart;
    private javax.swing.JCheckBox jCheckBoxTimeIntervalEnd;
    private javax.swing.JCheckBox jCheckBoxTimeIntervalStart;
    private javax.swing.JComboBox jComboBoxStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelAdmin;
    private javax.swing.JPanel jPanelAusgangslage;
    private javax.swing.JPanel jPanelBranches;
    private javax.swing.JPanel jPanelContacts;
    private javax.swing.JPanel jPanelExtras;
    private javax.swing.JPanel jPanelMetadata;
    private javax.swing.JPanel jPanelReferences;
    private javax.swing.JPanel jPanelTeam;
    private javax.swing.JPanel jPanelTechnics;
    private javax.swing.JPanel jPanelTopic;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JScrollPane jScrollPaneBranch;
    private javax.swing.JScrollPane jScrollPaneExtras;
    private javax.swing.JScrollPane jScrollPaneInvolvedRoles;
    private javax.swing.JScrollPane jScrollPaneReferrence;
    private javax.swing.JScrollPane jScrollPaneTeam;
    private javax.swing.JScrollPane jScrollPaneTechnics;
    private javax.swing.JScrollPane jScrollPaneTopic;
    private javax.swing.JSpinner jSpinnerChanged;
    private javax.swing.JSpinner jSpinnerDeadlineApproval;
    private javax.swing.JSpinner jSpinnerDeadlineEditor;
    private javax.swing.JSpinner jSpinnerDeadlineQA;
    private javax.swing.JSpinner jSpinnerReleasedDate;
    private javax.swing.JSpinner jSpinnerServiceTimeEnd;
    private javax.swing.JSpinner jSpinnerServiceTimeStart;
    private javax.swing.JSpinner jSpinnerTimeIntervalEnd;
    private javax.swing.JSpinner jSpinnerTimeIntervalStart;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableBranch;
    private javax.swing.JTable jTableExtras;
    private javax.swing.JTable jTableInvolvedRoles;
    private javax.swing.JTable jTableReferrence;
    private javax.swing.JTable jTableTeam;
    private javax.swing.JTable jTableTechnics;
    private javax.swing.JTable jTableTopic;
    private javax.swing.JTextArea jTextAreaClientPersonAddress;
    private javax.swing.JTextArea jTextAreaClientProfil;
    private javax.swing.JTextArea jTextAreaClientTestimonial;
    private javax.swing.JTextArea jTextAreaDescription;
    private javax.swing.JTextArea jTextAreaPreciseDescription;
    private javax.swing.JTextArea jTextAreaProjectBackground;
    private javax.swing.JTextArea jTextAreaProjectResults;
    private javax.swing.JTextArea jTextAreaProjectSolution;
    private javax.swing.JTextField jTextFieldAdessoPartnerEmail;
    private javax.swing.JTextField jTextFieldAdessoPartnerName;
    private javax.swing.JTextField jTextFieldAdessoPartnerRoleInAdesso;
    private javax.swing.JTextField jTextFieldAdessoPartnerRoleInProject;
    private javax.swing.JTextField jTextFieldAdessoPartnerTel;
    private javax.swing.JTextField jTextFieldApprover;
    private javax.swing.JTextField jTextFieldChangedBy;
    private javax.swing.JTextField jTextFieldChangedDescription;
    private javax.swing.JTextField jTextFieldClientPersonEmail;
    private javax.swing.JTextField jTextFieldClientPersonName;
    private javax.swing.JTextField jTextFieldClientPersonTel;
    private javax.swing.JTextField jTextFieldClientPersonTitel;
    private javax.swing.JTextField jTextFieldClientname;
    private javax.swing.JTextField jTextFieldCostAdesso;
    private javax.swing.JTextField jTextFieldCostTotal;
    private javax.swing.JTextField jTextFieldDeputyName;
    private javax.swing.JTextField jTextFieldDeputyRole;
    private javax.swing.JTextField jTextFieldEditor;
    private javax.swing.JTextField jTextFieldLOB;
    private javax.swing.JTextField jTextFieldLogo;
    private javax.swing.JTextField jTextFieldOwner;
    private javax.swing.JTextField jTextFieldProjectName;
    private javax.swing.JTextField jTextFieldPtAdesso;
    private javax.swing.JTextField jTextFieldPtTotalProject;
    private javax.swing.JTextField jTextFieldQA;
    private javax.swing.JTextField jTextFieldReleased;
    // End of variables declaration//GEN-END:variables

    
}
