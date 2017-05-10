
package com.applicants.Model;

import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author efsan1
 */
@ManagedBean (name= "EducationAndQualification", eager= true)
@SessionScoped
public class EducationAndQualification {
    
    private String highestQualification;
    private Date graduationDate;
    private String specialQualification;
    private ArrayList<String> strengths;
    private ArrayList<String> weaknesses;

    public String getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(String highestQualification) {
        this.highestQualification = highestQualification;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getSpecialQualification() {
        return specialQualification;
    }

    public void setSpecialQualification(String specialQualification) {
        this.specialQualification = specialQualification;
    }

    public ArrayList<String> getStrengths() {
        return strengths;
    }

    public void setStrengths(ArrayList<String> strengths) {
        this.strengths = strengths;
    }

    public ArrayList<String> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(ArrayList<String> weaknesses) {
        this.weaknesses = weaknesses;
    }
    
    
}
