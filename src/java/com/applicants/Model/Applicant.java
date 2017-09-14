package com.applicants.Model;

import com.staff.Model.Person;
import java.util.Date;


/**
 *
 * @author efsan1
 */

public class Applicant extends Person {
    private String applicationStatus;
    private String city;
    private String zipCode;
    private String maritalStatus;
    private String prename;
    private String country;
    private EmergencyContact nextSkin; // needs to add on Design class diagram
    private SpiritualLife beliefs =  new SpiritualLife();; // same above...
    private LegalHistory legalHistory = new LegalHistory();
    private MediaclHistory mediaclHistory = new MediaclHistory();
    private EducationAndQualification formation = new EducationAndQualification();
    private PersonanlityTraits personalityTraits = new PersonanlityTraits();
    private ApplicantMedicalHistory healthRecord;
    private WorkExperience experience = new WorkExperience();
    private InternshipInfo internshipInfo = new InternshipInfo();

    public InternshipInfo getInternshipInfo() {
        return internshipInfo;
    }

    public void setInternshipInfo(InternshipInfo internshipInfo) {
        this.internshipInfo = internshipInfo;
    }
    private String motivationForApllication;
    private String preredName;
    private int id;
    
    public Applicant(String firstname, String lastname, char gender, String phoneNumber, String address, String placeOfBirth, Date dateOfBirth, String emailAddress) {
        super(firstname, lastname, gender, phoneNumber, address, placeOfBirth, dateOfBirth, emailAddress);
    }

    public Applicant() {
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    

    public String getMotivationForApllication() {
        return motivationForApllication;
    }

    public void setMotivationForApllication(String motivationForApllication) {
        this.motivationForApllication = motivationForApllication;
    }
    
    
    public void setCity(String city) {
        this.city = city;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public EmergencyContact getNextSkin() {
        return nextSkin;
    }

    public void setNextSkin(EmergencyContact nextSkin) {
        this.nextSkin = nextSkin;
    }

    public SpiritualLife getBeliefs() {
        return beliefs;
    }

    public void setBeliefs(SpiritualLife beliefs) {
        this.beliefs = beliefs;
    }

    public EducationAndQualification getFormation() {
        return formation;
    }

    public void setFormation(EducationAndQualification formation) {
        this.formation = formation;
    }

    public ApplicantMedicalHistory getHealthRecord() {
        return healthRecord;
    }

    public void setHealthRecord(ApplicantMedicalHistory healthRecord) {
        this.healthRecord = healthRecord;
    }

    public WorkExperience getExperience() {
        return experience;
    }

    public void setExperience(WorkExperience experience) {
        this.experience = experience;
    }

    @Override
    public int getAge() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getPreredName() {
        return preredName;
    }

    public void setPreredName(String preredName) {
        this.preredName = preredName;
    }

    public PersonanlityTraits getPersonalityTraits() {
        return personalityTraits;
    }

    public void setPersonalityTraits(PersonanlityTraits personalityTraits) {
        this.personalityTraits = personalityTraits;
    }

    public LegalHistory getLegalHistory() {
        return legalHistory;
    }

    public void setLegalHistory(LegalHistory legalHistory) {
        this.legalHistory = legalHistory;
    }

    public MediaclHistory getMediaclHistory() {
        return mediaclHistory;
    }

    public void setMediaclHistory(MediaclHistory mediaclHistory) {
        this.mediaclHistory = mediaclHistory;
    }
    
    
    
    
    
    
}
