package com.child.Model;

import java.util.ArrayList;
import java.util.Date;
/**
 * 
 * @author efsan1 
 */
public class ChildMedicalHistory {
    
    private String doctersName;
    private String  illnessDetected;
    private String  cause;
    private String medicines;
    private String allergies;
    private String specialTreatmeants ;
    private String clincVisited;
    private Date dateOfVisit; 

    public ChildMedicalHistory(String doctersName, String illnessDetected, String cause,
            String medicines, String allergies, String specialTreatmeants, String clincVisited,
            Date dateOfVisit) {
        
        this.doctersName = doctersName;
        this.illnessDetected = illnessDetected;
        this.cause = cause;
        this.medicines = medicines;
        this.allergies = allergies;
        this.specialTreatmeants = specialTreatmeants;
        this.clincVisited = clincVisited;
        this.dateOfVisit = dateOfVisit;
    }
    
    public ChildMedicalHistory(){}
    
    public String getDoctersName() {
        return doctersName;
    }

    public void setDoctersName(String doctersName) {
        this.doctersName = doctersName;
    }

    public String getIllnessDetected() {
        return illnessDetected;
    }

    public void setIllnessDetected(String illnessDetected) {
        this.illnessDetected = illnessDetected;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause=cause;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines=medicines;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies=allergies;
    }

    public String getSpecialTreatmeants() {
        return specialTreatmeants;
    }

    public void setSpecialTreatmeants(String specialTreatmeants) {
        this.specialTreatmeants=specialTreatmeants;
    }

    public String getClincVisited() {
        return clincVisited;
    }

    public void setClincVisited(String clincVisited) {
        this.clincVisited=clincVisited;
    }

    public Date getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(Date dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }
    
}
