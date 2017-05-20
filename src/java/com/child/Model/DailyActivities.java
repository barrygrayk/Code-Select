package com.child.Model;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author efsan1
 */
public class DailyActivities {
    
    private int activityID;
    private Date dateRecorded;
    private Time activityOccuringTime;
    private String title;
    private String status;
    private String comment;

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public Time getActivityOccuringTime() {
        return activityOccuringTime;
    }

    public void setActivityOccuringTime(Time activityOccuringTime) {
        this.activityOccuringTime = activityOccuringTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
