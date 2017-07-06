/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.child.Model;

/**
 *
 * @author efsan1
 */
public class RequiredDocumentation {
    
    private boolean form36,clinicCard, birthCertificate, medicalReport;
    private boolean abuse, neglect, others;
    private String medicalReportNote, requiredDocNote;
    
    // super constructor
    public RequiredDocumentation(boolean form36, boolean clinicCard, 
            boolean birthCertificate, boolean medicalReport,boolean abuse,
            boolean neglect, boolean others, String medicalReportNote, String requiredDocNote){
        
        this.form36=form36;
        this.clinicCard = clinicCard;
        this.birthCertificate = birthCertificate;
        this.medicalReport = medicalReport; 
        this.requiredDocNote = requiredDocNote;
        this.medicalReportNote = medicalReportNote;
        this.abuse = abuse;
        this.neglect = neglect;
        this.others = others;
    }
    
    public boolean isForm36() {
        return form36;
    }

    public void setForm36(boolean form36) {
        this.form36 = form36;
    }

    public boolean isClinicCard() {
        return clinicCard;
    }

    public void setClinicCard(boolean clinicCard) {
        this.clinicCard = clinicCard;
    }

    public boolean isBirthCertificate() {
        return birthCertificate;
    }

    public void setBirthCertificate(boolean birthCertificate) {
        this.birthCertificate = birthCertificate;
    }

    public boolean isMedicalReport() {
        return medicalReport;
    }

    public void setMedicalReport(boolean medicalReport) {
        this.medicalReport = medicalReport;
    }

    public boolean isAbuse() {
        return abuse;
    }

    public void setAbuse(boolean abuse) {
        this.abuse = abuse;
    }

    public boolean isNeglect() {
        return neglect;
    }

    public void setNeglect(boolean neglect) {
        this.neglect = neglect;
    }

    public boolean isOthers() {
        return others;
    }

    public void setOthers(boolean others) {
        this.others = others;
    }

    public String getMedicalReportNote() {
        return medicalReportNote;
    }

    public void setMedicalReportNote(String medicalReportNote) {
        this.medicalReportNote = medicalReportNote;
    }

    public String getRequiredDocNote() {
        return requiredDocNote;
    }

    public void setRequiredDocNote(String requiredDocNote) {
        this.requiredDocNote = requiredDocNote;
    }
    

}
