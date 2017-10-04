package com.child.Model;

import java.util.Date;
/**
 *
 * @author efsan1
 */
public class VaccineTaken {
    
    private int vaccineTakenId;
    private String vaccineTaken;
    private String ageGroup;
    private Date scheduleDate; // this is actually Due date
    private Date givenDate;
    private String location;
    private String site;
    private boolean status;
    private String comments;
    
    public VaccineTaken(){
    }
    
    public VaccineTaken(String vaccineTaken, String ageGroup, String site){
        this.vaccineTaken =vaccineTaken;
        this.ageGroup = ageGroup;
        this.site = site;   
    }
    
    public VaccineTaken(String vaccineTaken, String ageGroup, String site, Date dueDate){
        this.vaccineTaken =vaccineTaken;
        this.ageGroup = ageGroup;
        this.site = site;  
        this.scheduleDate = dueDate;
    }
    
    public VaccineTaken(String vaccineTaken, String ageGroup, String site, Date dueDate,
            Date givenDate, String location, String comments, boolean status ){
        this.vaccineTaken =vaccineTaken;
        this.ageGroup = ageGroup;
        this.site = site;
        this.scheduleDate = dueDate;
        this.givenDate =  givenDate;
        this.location = location;
        this.comments = comments;
        this.status = status;
    }
    public int getVaccineTakenId() {
        return vaccineTakenId;
    }

    public void setVaccineTakenId(int vaccineTakenId) {
        this.vaccineTakenId = vaccineTakenId;
    }

    public String getVaccineTaken() {
        return vaccineTaken;
    }

    public void setVaccineTaken(String vaccineTaken) {
        this.vaccineTaken = vaccineTaken;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Date getGivenDate() {
        return givenDate;
    }

    public void setGivenDate(Date givenDate) {
        this.givenDate = givenDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
}
