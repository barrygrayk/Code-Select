package com.applicants.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Barry Gray
 */
public class ApplicationProgress {

    private Date interviewDate;
    private String comments;
    private List<String> requiredDocs = new ArrayList<>();
    private List<String> interviewers = new ArrayList<>();
    private String outcoume;
    private String reqDocToString;
    private String intervwersToString;

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<String> getRequiredDocs() {
        return requiredDocs;
    }

    public void setRequiredDocs(List<String> requiredDocs) {
        this.requiredDocs = requiredDocs;
    }

    public List<String> getInterviewers() {
        return interviewers;
    }

    public void setInterviewers(List<String> interviewers) {
        this.interviewers = interviewers;
    }

    public String getOutcoume() {
        return outcoume;
    }

    public void setOutcoume(String outcoume) {
        this.outcoume = outcoume;
    }

    public String getReqDocToString() {
        return reqDocToString;
    }

    public void setReqDocToString(String reqDocToString) {
        this.reqDocToString = reqDocToString;
    }

    public String getIntervwersToString() {
        return intervwersToString;
    }

    public void setIntervwersToString(String intervwersToString) {
        this.intervwersToString = intervwersToString;
    }
    
    

}
