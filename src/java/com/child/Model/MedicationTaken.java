package com.child.Model;

import java.util.Date;

/**
 *
 * @author efsan1
 */
public class MedicationTaken {
    
    private int medicationTakenId;
    private String medicineTaken;
    private String quantity;
    private String metric;
    private Date admisssionTime;
    private String comment;

    public int getMedicationTakenId() {
        return medicationTakenId;
    }

    public void setMedicationTakenId(int medicationTakenId) {
        this.medicationTakenId = medicationTakenId;
    }

    public String getMedicineTaken() {
        return medicineTaken;
    }

    public void setMedicineTaken(String medicineTaken) {
        this.medicineTaken = medicineTaken;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public Date getAdmisssionTime() {
        return admisssionTime;
    }

    public void setAdmisssionTime(Date admisssionTime) {
        this.admisssionTime = admisssionTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
        
}
