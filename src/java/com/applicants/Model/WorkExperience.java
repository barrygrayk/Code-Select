
package com.applicants.Model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author efsan1
 */

public class WorkExperience {
    
    private String nameOfEmployer;
    private String jobTitle;
    private Date  jobStart;
    private Date jobEnd;
    private String dailyDuities;
    private List <String> certificates = new ArrayList<> ();
    private String certificatsToString;
    private String dealingWith, dealingWithExplanation;
    private String victimOf,victimOfxplanation;
    private int id;

    public String getNameOfEmployer() {
        return nameOfEmployer;
    }
    

    public void setNameOfEmployer(String nameOfEmployer) {
        this.nameOfEmployer = nameOfEmployer;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Date getJobStart() {
        return jobStart;
    }

    public void setJobStart(Date jobStart) {
        this.jobStart = jobStart;
    }

    public Date getJobEnd() {
        return jobEnd;
    }

    public void setJobEnd(Date jobEnd) {
        this.jobEnd = jobEnd;
    }

    public String getDailyDuities() {
        return dailyDuities;
    }

    public void setDailyDuities(String dailyDuities) {
        this.dailyDuities = dailyDuities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getCertificates() {
        return certificates;
    }

    public void setCertificates(List <String> certificate) {
        this.certificates = certificate;
    }

    public String getDealingWith() {
        return dealingWith;
    }

    public void setDealingWith(String dealingWith) {
        this.dealingWith = dealingWith;
    }

    public String getVictimOf() {
        return victimOf;
    }

    public void setVictimOf(String victimOf) {
        this.victimOf = victimOf;
    }

    public String getDealingWithExplanation() {
        return dealingWithExplanation;
    }

    public void setDealingWithExplanation(String dealingWithExplanation) {
        this.dealingWithExplanation = dealingWithExplanation;
    }

    public String getVictimOfxplanation() {
        return victimOfxplanation;
    }

    public void setVictimOfxplanation(String victimOfxplanation) {
        this.victimOfxplanation = victimOfxplanation;
    }

    public String getCertificatsToString() {
        return certificatsToString;
    }

    public void setCertificatsToString(String certificatsToString) {
        this.certificatsToString = certificatsToString;
    }   
}
