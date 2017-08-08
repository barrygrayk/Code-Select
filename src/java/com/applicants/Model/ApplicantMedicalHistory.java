
package com.applicants.Model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author efsan1
 */

public class ApplicantMedicalHistory {
    
    private boolean currentMedicalCondions;
    private String currentCondDesc;
    private boolean pastMedicalCondition;
    private String pastCondDesc;
    private boolean dietaryRestriction;
    private String dietaryRestricton;
    private boolean physicalHandicaps; 
    private String handicapsDesc;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ApplicantMedicalHistory() {
    }
    
    

    public boolean isCurrentMedicalCondions() {
        return currentMedicalCondions;
    }

    public void setCurrentMedicalCondions(boolean currentMedicalCondions) {
        this.currentMedicalCondions = currentMedicalCondions;
    }

    public String getCurrentCondDesc() {
        return currentCondDesc;
    }

    public void setCurrentCondDesc(String currentCondDesc) {
        this.currentCondDesc = currentCondDesc;
    }

    public boolean isPastMedicalCondition() {
        return pastMedicalCondition;
    }

    public void setPastMedicalCondition(boolean pastMedicalCondition) {
        this.pastMedicalCondition = pastMedicalCondition;
    }

    public String getPastCondDesc() {
        return pastCondDesc;
    }

    public void setPastCondDesc(String pastCondDesc) {
        this.pastCondDesc = pastCondDesc;
    }

    public boolean isDietaryRestriction() {
        return dietaryRestriction;
    }

    public void setDietaryRestriction(boolean dietaryRestriction) {
        this.dietaryRestriction = dietaryRestriction;
    }

    public String getDietaryRestricton() {
        return dietaryRestricton;
    }

    public void setDietaryRestricton(String dietaryRestricton) {
        this.dietaryRestricton = dietaryRestricton;
    }

    public boolean isPhysicalHandicaps() {
        return physicalHandicaps;
    }

    public void setPhysicalHandicaps(boolean physicalHandicaps) {
        this.physicalHandicaps = physicalHandicaps;
    }

    public String getHandicapsDesc() {
        return handicapsDesc;
    }

    public void setHandicapsDesc(String handicapsDesc) {
        this.handicapsDesc = handicapsDesc;
    }
    
    
    
    
}
