/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.Entities.Raw;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public class ReferenceEntity {

    private String id;
    /**
     * Metadaten
     */
    private String clientname;
    private String clientlogo;
    private String projectname;
    private String lob;
    private List<String> branchList; //kein list?

    private TimeInterval timeInterval;
    private TimeInterval serviceTime;
    private int ptTotalProject;
    private int ptAdesso;
    private double costTotal;
    private double costAdesso;
    private List<String> refIDList;
    private int freigabestatus; //0-not released, 1-INDIVIDUALLY_RELEASED, 2-ANONYMOUSLY_RELEASED, 3-FULL_RELEASED
    private AccessStamp changed;
    private AccessStamp released;
    private String description;

    /**
     * Administratives
     */
    private ElasticUser owner;
    private ElasticUser editor;
    private ElasticUser qAssurance;
    private ElasticUser approver;
    private Date deadlineEditor;
    private Date deadlineQA;
    private Date deadlineApproval;

    /**
     * Daten
     */
    private List<String> roles;
    private List<Person> teammembers;
    private List<String> technics;
    private List<String> topics;
    private List<String> extras;

    //adesso-Ansprechpartner
    private Person adessoPartner;
    private String adessoPartnerRole;

    //Stellvertreter des adesso-Ansprechpartners
    private String deputyName;
    private String deputyRole;

    //Kontaktdaten Kunde
    private Person clientData;

    //Unstrukturierte Informationen (Word) 
    private String clientProfil;
    private String projectBackground;
    private String projectSolution;
    private String projectResults;
    private String preciseDescription;

//-------------------------------
    private String objectVersionID = "0.01";
    private String classVersionID = "0.02";

    private String blobdata1, blobdata2;

    public void setDefaultDaten() {
        clientname = "Test Client";
        clientlogo = "no logo";
        projectname = "Test Projekt";
        branchList = new ArrayList<String>();
        timeInterval = new TimeInterval(new Date(110, 1, 1), new Date(111, 1, 1));
        serviceTime = new TimeInterval(new Date(111, 2, 1), new Date(115, 1, 1));
        branchList = new ArrayList<String>();
        refIDList = new ArrayList<String>();
        freigabestatus = 0;
        description = "Automatic created Reference";
        clientProfil = "Beschreibung von Klundenprofil";
        projectBackground = "Beschreibung von Klundenprofil";
        projectSolution = "Beschreibung von projectSolution";
        projectResults = "Beschreibung von projectResults";;
        preciseDescription = "Beschreibung von preciseDescription";;

        /*
         private int ptTotalProject;
         private int ptAdesso;
         private double costTotal;
         private double costAdesso;
         private AccessStamp changed;
         private AccessStamp released;

         private ElasticUser owner;
         private ElasticUser editor;
         private ElasticUser qAssurance;
         private ElasticUser approver;
         private Date deadlineEditor;
         private Date deadlineQA;
         private Date deadlineApproval;

         private List <String> roles;
         private List <Person> teammembers;
         private List <String> technics;
         private List <String> topics;
         private List <String> extras;

         //adesso-Ansprechpartner
         private Person adessoPartner;

         //Stellvertreter des adesso-Ansprechpartners
         private String deputyName;
         private String deputyRole;

         //Kontaktdaten Kunde
         private Person clientData;

         //Unstrukturierte Informationen (Word) 
         private String clientProfil;
         private String projectBackground;
         private String projectSolution;
         private String projectResults;
         private String preciseDescription;
         */
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getClientlogo() {
        return clientlogo;
    }

    public void setClientlogo(String clientlogo) {
        this.clientlogo = clientlogo;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getLob() {
        return lob;
    }

    public void setLob(String lob) {
        this.lob = lob;
    }

    public List<String> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<String> branchList) {
        this.branchList = branchList;
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }

    public TimeInterval getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(TimeInterval serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getPtTotalProject() {
        return ptTotalProject;
    }

    public void setPtTotalProject(int ptTotalProject) {
        this.ptTotalProject = ptTotalProject;
    }

    public int getPtAdesso() {
        return ptAdesso;
    }

    public void setPtAdesso(int ptAdesso) {
        this.ptAdesso = ptAdesso;
    }

    public double getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(double costTotal) {
        this.costTotal = costTotal;
    }

    public double getCostAdesso() {
        return costAdesso;
    }

    public void setCostAdesso(double costAdesso) {
        this.costAdesso = costAdesso;
    }

    public List<String> getRefIDList() {
        return refIDList;
    }

    public void setRefIDList(List<String> refIDList) {
        this.refIDList = refIDList;
    }

    public int getFreigabestatus() {
        return freigabestatus;
    }

    public void setFreigabestatus(int freigabestatus) {
        this.freigabestatus = freigabestatus;
    }

    public AccessStamp getChanged() {
        return changed;
    }

    public void setChanged(AccessStamp changed) {
        this.changed = changed;
    }

    public AccessStamp getReleased() {
        return released;
    }

    public void setReleased(AccessStamp released) {
        this.released = released;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ElasticUser getOwner() {
        return owner;
    }

    public void setOwner(ElasticUser owner) {
        this.owner = owner;
    }

    public ElasticUser getEditor() {
        return editor;
    }

    public void setEditor(ElasticUser editor) {
        this.editor = editor;
    }

    public ElasticUser getqAssurance() {
        return qAssurance;
    }

    public void setqAssurance(ElasticUser qAssurance) {
        this.qAssurance = qAssurance;
    }

    public ElasticUser getApprover() {
        return approver;
    }

    public void setApprover(ElasticUser approver) {
        this.approver = approver;
    }

    public Date getDeadlineEditor() {
        return deadlineEditor;
    }

    public void setDeadlineEditor(Date deadlineEditor) {
        this.deadlineEditor = deadlineEditor;
    }

    public Date getDeadlineQA() {
        return deadlineQA;
    }

    public void setDeadlineQA(Date deadlineQA) {
        this.deadlineQA = deadlineQA;
    }

    public Date getDeadlineApproval() {
        return deadlineApproval;
    }

    public void setDeadlineApproval(Date deadlineApproval) {
        this.deadlineApproval = deadlineApproval;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<Person> getTeammembers() {
        return teammembers;
    }

    public void setTeammembers(List<Person> teammembers) {
        this.teammembers = teammembers;
    }

    public List<String> getTechnics() {
        return technics;
    }

    public void setTechnics(List<String> technics) {
        this.technics = technics;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<String> getExtras() {
        return extras;
    }

    public void setExtras(List<String> extras) {
        this.extras = extras;
    }

    public Person getAdessoPartner() {
        return adessoPartner;
    }

    public void setAdessoPartner(Person adessoPartner) {
        this.adessoPartner = adessoPartner;
    }

    public String getDeputyName() {
        return deputyName;
    }

    public void setDeputyName(String deputyName) {
        this.deputyName = deputyName;
    }

    public String getDeputyRole() {
        return deputyRole;
    }

    public void setDeputyRole(String deputyRole) {
        this.deputyRole = deputyRole;
    }

    public Person getClientData() {
        return clientData;
    }

    public void setClientData(Person clientData) {
        this.clientData = clientData;
    }

    public String getClientProfil() {
        return clientProfil;
    }

    public void setClientProfil(String clientProfil) {
        this.clientProfil = clientProfil;
    }

    public String getProjectBackground() {
        return projectBackground;
    }

    public void setProjectBackground(String projectBackground) {
        this.projectBackground = projectBackground;
    }

    public String getProjectSolution() {
        return projectSolution;
    }

    public void setProjectSolution(String projectSolution) {
        this.projectSolution = projectSolution;
    }

    public String getProjectResults() {
        return projectResults;
    }

    public void setProjectResults(String projectResults) {
        this.projectResults = projectResults;
    }

    public String getPreciseDescription() {
        return preciseDescription;
    }

    public void setPreciseDescription(String preciseDescription) {
        this.preciseDescription = preciseDescription;
    }

    public String getObjectVersionID() {
        return objectVersionID;
    }

    public void setObjectVersionID(String objectVersionID) {
        this.objectVersionID = objectVersionID;
    }

    public String getClassVersionID() {
        return classVersionID;
    }

    public void setClassVersionID(String classVersionID) {
        this.classVersionID = classVersionID;
    }

    public String getBlobdata1() {
        return blobdata1;
    }

    public void setBlobdata1(String blobdata1) {
        this.blobdata1 = blobdata1;
    }

    public String getBlobdata2() {
        return blobdata2;
    }

    public void setBlobdata2(String blobdata2) {
        this.blobdata2 = blobdata2;
    }

    public String getAdessoPartnerRole() {
        return adessoPartnerRole;
    }

    public void setAdessoPartnerRole(String adessoPartnerRole) {
        this.adessoPartnerRole = adessoPartnerRole;
    }

}
