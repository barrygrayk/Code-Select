package com.applicants.Model;


import java.util.Date;

/**
 *
 * @author efsan1
 */
public class EducationAndQualification {

    private String highestQualification;
    private Date highestGraduationDate;
    private Date specialGraduationDate;
    private String specialQualification;

    public String getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(String highestQualification) {
        this.highestQualification = highestQualification;
    }

    public Date getHighestGraduationDate() {
        return highestGraduationDate;
    }

    public void setHighestGraduationDate(Date highestGraduationDate) {
        this.highestGraduationDate = highestGraduationDate;
    }

    public Date getSpecialGraduationDate() {
        return specialGraduationDate;
    }

    public void setSpecialGraduationDate(Date specialGraduationDate) {
        this.specialGraduationDate = specialGraduationDate;
    }

    public String getSpecialQualification() {
        return specialQualification;
    }

    public void setSpecialQualification(String specialQualification) {
        this.specialQualification = specialQualification;
    }
    

   
    

}
