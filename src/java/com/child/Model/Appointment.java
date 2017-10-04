
package com.child.Model;

import java.util.Date;

/**
 *
 * @author efsan1
 */
public class Appointment {
    
    private int appointmentId;
    private Date date;
    private String doctor;
    private String hospital;
    private String complaints;
    private String physicalExamination;
    private String doctorFeedback;
    private String bloodTestNote;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getComplaints() {
        return complaints;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public String getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(String physicalExamination) {
        this.physicalExamination = physicalExamination;
    }

    public String getDoctorFeedback() {
        return doctorFeedback;
    }

    public void setDoctorFeedback(String doctorFeedback) {
        this.doctorFeedback = doctorFeedback;
    }

    public String getBloodTestNote() {
        return bloodTestNote;
    }

    public void setBloodTestNote(String bloodTestNote) {
        this.bloodTestNote = bloodTestNote;
    }
    
    @Override
    public String toString(){
        return this.date+"   with   "+"Dr.: "+this.doctor+"   at:  "+this.hospital;
    }
}
