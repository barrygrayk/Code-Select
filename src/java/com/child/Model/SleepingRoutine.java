package com.child.Model;

import java.util.Date;

/**
 *
 * @author efsan1
 */
public class SleepingRoutine {
    
    private int sleepingRoutineID;
    private Date sleepingTime;
    private Date wakingTime;
    private Date dateRecorded;

    public int getSleepingRoutineID() {
        return sleepingRoutineID;
    }

    public void setSleepingRoutineID(int sleepingRoutineID) {
        this.sleepingRoutineID = sleepingRoutineID;
    }

    public Date getSleepingTime() {
        return sleepingTime;
    }

    public void setSleepingTime(Date sleepingTime) {
        this.sleepingTime = sleepingTime;
    }

    public Date getWakingTime() {
        return wakingTime;
    }

    public void setWakingTime(Date wakingTime) {
        this.wakingTime = wakingTime;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }
    
    
    
}
