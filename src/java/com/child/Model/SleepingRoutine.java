package com.child.Model;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author efsan1
 */
public class SleepingRoutine {
    
    private int sleepingRoutineID;
    private Time sleepingTime;
    private Time wakingTime;
    private Date dateRecorded;

    public int getSleepingRoutineID() {
        return sleepingRoutineID;
    }

    public void setSleepingRoutineID(int sleepingRoutineID) {
        this.sleepingRoutineID = sleepingRoutineID;
    }

    public Time getSleepingTime() {
        return sleepingTime;
    }

    public void setSleepingTime(Time sleepingTime) {
        this.sleepingTime = sleepingTime;
    }

    public Time getWakingTime() {
        return wakingTime;
    }

    public void setWakingTime(Time wakingTime) {
        this.wakingTime = wakingTime;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }
    
    
    
}
