/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.applicants.Model;

import com.staff.Model.Person;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author efsan1
 */
@ManagedBean (name= "Applicant", eager= true)
@SessionScoped

public class Applicant extends Person {
    
    private String city;
    private String zipCode;
    private String maritalStatus;
    private EmergencyContact nextSkin; // needs to add on Design class diagram
    private SpiritualLife beliefs; // same above...
    private EducationAndQualification formation;
    private ApplicantMedicalHistory healthRecord;
    private WorkExperience experience;

    public Applicant(String firstname, String lastname, char gender, String phoneNumber, String address, String placeOfBirth, Date dateOfBirth, String emailAddress) {
        super(firstname, lastname, gender, phoneNumber, address, placeOfBirth, dateOfBirth, emailAddress);
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
    
}
