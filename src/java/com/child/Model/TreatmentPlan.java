package com.child.Model;

import java.util.Date;

/**
 *
 * @author efsan1
 */
public class TreatmentPlan {
    
    private int treatmentPlanId;
    private String description;
    private String approach;
    private Date startDate;
    private Date endDate;

    public int getTreatmentPlanId() {
        return treatmentPlanId;
    }

    public void setTreatmentPlanId(int treatmentPlanId) {
        this.treatmentPlanId = treatmentPlanId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApproach() {
        return approach;
    }

    public void setApproach(String approach) {
        this.approach = approach;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
}
