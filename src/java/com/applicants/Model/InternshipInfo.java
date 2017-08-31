/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.applicants.Model;

import java.util.Date;

/**
 * 
 * @author Barry Gray
 */
public class InternshipInfo {
    private Date startDate;
    private Date endDate;
    private String areDayFlex;
    private String howUHeard=null;
    private String internshipGoal;

    public InternshipInfo() {
    }
    
   

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getHowUHeard() {
        return howUHeard;
    }

    public void setHowUHeard(String howUHeard) {
        this.howUHeard = howUHeard;
    }

    public String getInternshipGoal() {
        return internshipGoal;
    }

    public void setInternshipGoal(String internshipGoal) {
        this.internshipGoal = internshipGoal;
    }

    public String getAreDayFlex() {
        return areDayFlex;
    }

    public void setAreDayFlex(String areDayFlex) {
        this.areDayFlex = areDayFlex;
    }
    
    
    
    
    

}
