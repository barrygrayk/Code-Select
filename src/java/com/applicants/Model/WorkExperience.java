
package com.applicants.Model;
import java.util.Date;

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

   
    
}
