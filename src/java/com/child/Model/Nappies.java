package com.child.Model;

import java.util.Date;

/**
 * 
 * @author efsan1
 */
public class Nappies {
    
    private int nappyId;
    private boolean conditionWet;
    private boolean conditionDry;
    private Date nappyChangeTime;
    private Date dateRecorded;
    
    public int getNappyId() {
        return nappyId;
    }

    public void setNappyId(int nappyId) {
        this.nappyId = nappyId;
    }

    public Date getNappyChangeTime() {
        return nappyChangeTime;
    }

    public void setNappyChangeTime(Date nappyChangeTime) {
        this.nappyChangeTime = nappyChangeTime;
    }

    public boolean isConditionWet() {
        return conditionWet;
    }

    public void setConditionWet(boolean conditionWet) {
        this.conditionWet = conditionWet;
    }

    public boolean isConditionDry() {
        return conditionDry;
    }

    public void setConditionDry(boolean conditionDry) {
        this.conditionDry = conditionDry;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }
    

}
