/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.search.elastic.entities;

import de.adesso.referencer.search.config.ElasticSearchConfig;
import de.adesso.referencer.search.helper.MyHelpMethods;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;
import java.util.List;

import static java.lang.Math.round;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author odzhara-ongom
 */
//@Document(indexName = ElasticSearchConfig.INDEX_NAME, type = "springdata_reference")
@Document(indexName = ElasticSearchConfig.INDEX_NAME, type = ElasticSearchConfig.TYPE_NAME_REFERENCE)
public class Reference implements Entity {

    @Id
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String id;
    /**
     * Metadaten
     */
    //@Field(type = FieldType.String)
    @MultiField(mainField = @Field(type = FieldType.String),
            otherFields = {
                //                @NestedField(dotSuffix = "raw", type = FieldType.String, index = FieldIndex.not_analyzed),
                @NestedField(dotSuffix = ElasticSearchConfig.SORT_FIELD, type = FieldType.String, indexAnalyzer = ElasticSearchConfig.GERMANY_LANGUAGE_ANALYZER)
            })
    private String clientname;
    @Field(type = FieldType.String)
    private String clientlogo;
    //@Field(type = FieldType.String)
    @MultiField(mainField = @Field(type = FieldType.String),
            otherFields = {
                @NestedField(dotSuffix = ElasticSearchConfig.SORT_FIELD, type = FieldType.String, indexAnalyzer = ElasticSearchConfig.GERMANY_LANGUAGE_ANALYZER)
            })
    private String projectname;
    @Field(type = FieldType.Nested)
    private Lob lob;
    @Field(type = FieldType.Nested)
    private List<Branch> branches;
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private Date projectStart;
    @Field(type = FieldType.String)
    private String projectStartString;
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private Date projectEnd;
    @Field(type = FieldType.String)
    private String projectEndString;
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private Date serviceStart;
    @Field(type = FieldType.String)
    private String serviceStartString;
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private Date serviceEnd;
    @Field(type = FieldType.String)
    private String serviceEndString;
    @Field(type = FieldType.Boolean)
    private Boolean serviceContract;
    @Field(type = FieldType.Integer)
    private int ptTotalProject;
    @Field(type = FieldType.String, indexAnalyzer = ElasticSearchConfig.REFERENCE_INDEX_ANALYZER_NAME, searchAnalyzer = ElasticSearchConfig.REFERENCE_SEARCH_ANALYZER_NAME)
    private String ptTotalProjectString = ptTotalProject + "";
    @Field(type = FieldType.Integer)
    private int ptAdesso;
    @Field(type = FieldType.String, indexAnalyzer = ElasticSearchConfig.REFERENCE_INDEX_ANALYZER_NAME, searchAnalyzer = ElasticSearchConfig.REFERENCE_SEARCH_ANALYZER_NAME)
    private String ptAdessoString = ptAdesso + "";
    @Field(type = FieldType.Double)
    private double costTotal;
    @Field(type = FieldType.String)
    private String costTotalString = costTotal + "";
    @Field(type = FieldType.Double)
    private double costAdesso;
    @Field(type = FieldType.String, indexAnalyzer = ElasticSearchConfig.REFERENCE_INDEX_ANALYZER_NAME, searchAnalyzer = ElasticSearchConfig.REFERENCE_SEARCH_ANALYZER_NAME)
    private String costAdessoString = costAdesso + "";
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private List<String> refIdList;

    //@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    @MultiField(mainField = @Field(type = FieldType.String, index = FieldIndex.not_analyzed),
            otherFields = {
                @NestedField(dotSuffix = "raw", type = FieldType.String, indexAnalyzer = ElasticSearchConfig.REFERENCE_INDEX_ANALYZER_NAME, searchAnalyzer = ElasticSearchConfig.REFERENCE_SEARCH_ANALYZER_NAME)
            })
    private ReleaseStatus releaseStatus;
    @Field(type = FieldType.String)
    private String releaseStatusString;
    @Field(type = FieldType.Nested)
    private AccessStamp changed;
    @Field(type = FieldType.Nested)
    private AccessStamp released;
    @Field(type = FieldType.String)
    private String description;
    /**
     * Administratives
     */
    @Field(type = FieldType.Nested)
    private User owner;
    @Field(type = FieldType.Nested)
    private User editor;
    @Field(type = FieldType.Nested)
    private User qAssurance;
    @Field(type = FieldType.Nested)
    private User approver;
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private Date deadlineEditor;
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private Date deadlineQA;
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private Date deadlineApproval;
    /**
     * Daten
     */
    @Field(type = FieldType.Nested)
    private List<InvolvedRole> involvedRoles;
    @Field(type = FieldType.Nested)
    private List<User> teammembers;
    @Field(type = FieldType.Nested)
    private List<Technology> technologies;
    @Field(type = FieldType.Nested)
    private List<Focus> focuses;
    @Field(type = FieldType.Nested)
    private List<Characteristic> characteristics;
    //adesso-Ansprechpartner

//    @Field(type = FieldType.Nested)
//    private User adessoPartner;
    @Field(type = FieldType.String)
    private String adessoContactName;
    @Field(type = FieldType.String, indexAnalyzer = ElasticSearchConfig.EMAIL_ANALYZER_NAME, searchAnalyzer = ElasticSearchConfig.EMAIL_ANALYZER_NAME)
    private String adessoContactMail;
    @Field(type = FieldType.String, indexAnalyzer = ElasticSearchConfig.REFERENCE_INDEX_ANALYZER_NAME, searchAnalyzer = ElasticSearchConfig.REFERENCE_SEARCH_ANALYZER_NAME)
    private String adessoContactPhone;
    @Field(type = FieldType.Nested)
    private AdessoRole adessoContactAdessoRole;

    @Field(type = FieldType.Nested)
    private ProjectRole adessoContactProjectRole;

    //Stellvertreter des adesso-Ansprechpartners
//    @Field(type = FieldType.Nested)
//    private User deputy;
    @Field(type = FieldType.String)
    private String adessoDeputyContactName;
    @Field(type = FieldType.Nested)
    private ProjectRole adessoDeputyContactProjectRole;

    //Kontaktdaten Kunde
    @Field(type = FieldType.Nested)
    private Contact clientData;
    //Unstrukturierte Informationen (Word)
    @Field(type = FieldType.String)
    private String clientProfil;
    @Field(type = FieldType.String)
    private String projectBackground;
    @Field(type = FieldType.String)
    private String projectSolution;
    @Field(type = FieldType.String)
    private String projectResults;
    @Field(type = FieldType.String)
    private String preciseDescription;
    @Field(type = FieldType.String)
    private String testimonial;
    @Field(type = FieldType.String)
    private String testimonialInfo;

    //-------------------------------
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String classVersionID = "0.03";
//    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    @Field(type = FieldType.Date)
    private Date documentCreationTime;

    private String cost2String(double cost) {
        return String.format(Locale.GERMAN, "%1$,.2f", cost);
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

    public Lob getLob() {
        return lob;
    }

    public void setLob(Lob lob) {
        this.lob = lob;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public Date getProjectStart() {
        return projectStart;
    }

    public void setProjectStart(Date projectStart) {
        this.projectStart = projectStart;
        if (projectStart != null) {
            this.projectStartString = MyHelpMethods.dateToSearchString(projectStart);
        }
    }

    public String getProjectStartString() {
        return projectStartString;
    }

    public Date getProjectEnd() {
        return projectEnd;
    }

    public void setProjectEnd(Date projectEnd) {
        this.projectEnd = projectEnd;
        if (projectEnd != null) {
            this.projectEndString = MyHelpMethods.dateToSearchString(projectEnd);
        }
    }

    public Date getServiceStart() {
        return serviceStart;
    }

    public void setServiceStart(Date serviceStart) {
        this.serviceContract = true;
        this.serviceStart = serviceStart;
        if (serviceStart != null) {
            this.serviceStartString = MyHelpMethods.dateToSearchString(serviceStart);
        }
    }

    public String getServiceStartString() {
        return serviceStartString;
    }

    public void setServiceStartString(String serviceStartString) {
        this.serviceStartString = serviceStartString;
    }

    public Date getServiceEnd() {
        return serviceEnd;
    }

    public void setServiceEnd(Date serviceEnd) {
        this.serviceEnd = serviceEnd;
        if (serviceEnd != null) {
            this.serviceEndString = MyHelpMethods.dateToSearchString(serviceEnd);
        }
    }

    public String getServiceEndString() {
        return serviceEndString;
    }

    public int getPtTotalProject() {
        return ptTotalProject;
    }

    public void setPtTotalProject(int ptTotalProject) {
        this.ptTotalProject = ptTotalProject;
        this.ptTotalProjectString = ptTotalProject + "";
    }

    public String getPtTotalProjectString() {
        return ptTotalProjectString;
    }

    public int getPtAdesso() {
        return ptAdesso;
    }

    public void setPtAdesso(int ptAdesso) {
        this.ptAdesso = ptAdesso;
        this.ptAdessoString = ptAdesso + "";
    }

    public String getPtAdessoString() {
        return ptAdessoString;
    }

    public double getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(double costTotal) {
        this.costTotal = costTotal;
        this.costTotalString = cost2String(costTotal);
    }

    public String getCostTotalString() {
        return costTotalString;
    }

    public double getCostAdesso() {
        return costAdesso;
    }

    public void setCostAdesso(double costAdesso) {
        this.costAdesso = costAdesso;
        this.costAdessoString = cost2String(costAdesso);
    }

    public String getCostAdessoString() {
        return costAdessoString;
    }

    public List<String> getRefIdList() {
        return refIdList;
    }

    public void setRefIdList(List<String> refIdList) {
        this.refIdList = refIdList;
    }

    public ReleaseStatus getReleaseStatus() {
        return releaseStatus;
    }

    public static String releaseStatusToSynonym(ReleaseStatus releaseStatus) {
        switch (releaseStatus) {
            case ANONYMOUSLY_RELEASED:
                return "Anonyme Freigabe";
            case FULLY_RELEASED:
                return "Volle Freigabe";
            case INDIVIDUALLY_RELEASED:
                return "Einzelfreigabe";
            case NOT_RELEASED:
                return "Keine Freigabe";
            default:
                return "Keine Freigabe";
        }
    }

    public void setReleaseStatus(ReleaseStatus releaseStatus) {
        this.releaseStatus = releaseStatus;
        if (releaseStatus != null) {
            this.releaseStatusString = releaseStatusToSynonym(releaseStatus);
        }
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }

    public User getqAssurance() {
        return qAssurance;
    }

    public void setqAssurance(User qAssurance) {
        this.qAssurance = qAssurance;
    }

    public User getApprover() {
        return approver;
    }

    public void setApprover(User approver) {
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

    public List<InvolvedRole> getInvolvedRoles() {
        return involvedRoles;
    }

    public void setInvolvedRoles(List<InvolvedRole> involvedRoles) {
        this.involvedRoles = involvedRoles;
    }

    public List<User> getTeammembers() {
        return teammembers;
    }

    public void setTeammembers(List<User> teammembers) {
        this.teammembers = teammembers;
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    public List<Focus> getFocuses() {
        return focuses;
    }

    public void setFocuses(List<Focus> focuses) {
        this.focuses = focuses;
    }

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

//    public User getAdessoPartner() {
//        return adessoPartner;
//    }
//
//    public void setAdessoPartner(User adessoPartner) {
//        this.adessoPartner = adessoPartner;
//    }
    public String getAdessoContactName() {
        return adessoContactName;
    }

    public void setAdessoContactName(String adessoContactName) {
        this.adessoContactName = adessoContactName;
    }

    public String getAdessoContactMail() {
        return adessoContactMail;
    }

    public void setAdessoContactMail(String adessoContactMail) {
        this.adessoContactMail = adessoContactMail;
    }

    public String getAdessoContactPhone() {
        return adessoContactPhone;
    }

    public void setAdessoContactPhone(String adessoContactPhone) {
        this.adessoContactPhone = adessoContactPhone;
    }

    public AdessoRole getAdessoContactAdessoRole() {
        return adessoContactAdessoRole;
    }

    public void setAdessoContactAdessoRole(AdessoRole adessoContactAdessoRole) {
        this.adessoContactAdessoRole = adessoContactAdessoRole;
    }

    public ProjectRole getAdessoContactProjectRole() {
        return adessoContactProjectRole;
    }

    public void setAdessoContactProjectRole(ProjectRole adessoContactProjectRole) {
        this.adessoContactProjectRole = adessoContactProjectRole;
    }

//    public User getDeputy() {
//        return deputy;
//    }
//
//    public void setDeputy(User deputy) {
//        this.deputy = deputy;
//    }
    public String getAdessoDeputyContactName() {
        return adessoDeputyContactName;
    }

    public void setAdessoDeputyContactName(String adessoDeputyContactName) {
        this.adessoDeputyContactName = adessoDeputyContactName;
    }

    public ProjectRole getAdessoDeputyContactProjectRole() {
        return adessoDeputyContactProjectRole;
    }

    public void setAdessoDeputyContactProjectRole(ProjectRole adessoDeputyContactProjectRole) {
        this.adessoDeputyContactProjectRole = adessoDeputyContactProjectRole;
    }

    public Contact getClientData() {
        return clientData;
    }

    public void setClientData(Contact clientData) {
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

    public String getClassVersionID() {
        return classVersionID;
    }

    public void setClassVersionID(String classVersionID) {
        this.classVersionID = classVersionID;
    }

    public String getProjectEndString() {
        return projectEndString;
    }

    public Boolean getServiceContract() {
        return serviceContract;
    }

    @Override
    public String getName() {
        return getProjectname();
    }

    @Override
    public void setName(String name) {
        setProjectname(projectname);
    }

    public enum ReleaseStatus {

        FULLY_RELEASED,
        ANONYMOUSLY_RELEASED,
        INDIVIDUALLY_RELEASED,
        NOT_RELEASED
    }

    public Reference addBranch(Branch branch) {
        if (branch == null) {
            return this;
        }
        if (this.branches == null) {
            this.branches = new ArrayList<>();
        }
        branches.add(branch);
        return this;
    }

    public Reference addTech(Technology tech) {
        if (tech == null) {
            return this;
        }
        if (this.technologies == null) {
            this.technologies = new ArrayList<>();
        }
        technologies.add(tech);
        return this;
    }

    public Date getDocumentCreationTime() {
        return documentCreationTime;
    }

    public void setDocumentCreationTime(Date documentCreationTime) {
        this.documentCreationTime = documentCreationTime;
    }

    public String getTestimonial() {
        return testimonial;
    }

    public void setTestimonial(String testimonial) {
        this.testimonial = testimonial;
    }

    public String getTestimonialInfo() {
        return testimonialInfo;
    }

    public void setTestimonialInfo(String testimonialInfo) {
        this.testimonialInfo = testimonialInfo;
    }

    public String getReleaseStatusString() {
        return releaseStatusString;
    }

    public void setReleaseStatusString(String releaseStatusString) {
        this.releaseStatusString = releaseStatusString;
    }

}
