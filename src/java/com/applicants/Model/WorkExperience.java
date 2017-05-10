
package com.applicants.Model;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author efsan1
 */
@ManagedBean (name= "WorkExperience", eager= true)
@SessionScoped

public class WorkExperience {
    
    private String nameOfEmployer;
    private String jobTitle;
    private Date jobDuration;
    private String reasonForLeaving;

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
