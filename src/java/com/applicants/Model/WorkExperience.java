
package com.applicants.Model;

import java.util.Date;


/**
 *
 * @author efsan1
 */


public class WorkExperience {
    
    private String nameOfEmployer;
    private String jobTitle;
    private Date jobDuration;
    private String reasonForLeaving;
    private int id;

    public String getNameOfEmployer() {
        return nameOfEmployer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getJobDuration() {
        return jobDuration;
    }

    public void setJobDuration(Date jobDuration) {
        this.jobDuration = jobDuration;
    }

    public String getReasonForLeaving() {
        return reasonForLeaving;
    }

    public void setReasonForLeaving(String reasonForLeaving) {
        this.reasonForLeaving = reasonForLeaving;
    }
    
    
}
