/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.child.Model;

/**
 *
 * @author efsan1
 */
public class SocialWorker {
    
    private String SocialWorkerName;
    private long phoneNumber;
    private String organization;
    private String email;
    private String additionalInfo;
    
    // super constructor
    public SocialWorker(String SocialWorkerName, long phoneNumber,
            String organization, String email, String additionalInfo){
        
        this.SocialWorkerName = SocialWorkerName;
        this.phoneNumber = phoneNumber;
        this.organization = organization;
        this.email = email;
        this.additionalInfo = additionalInfo;
    }
    
    // defaul Constructor 
    public SocialWorker(){
        
    }

    public String getSocialWorkerName() {
        return SocialWorkerName;
    }

    public void setSocialWorkerName(String SocialWorkerName) {
        this.SocialWorkerName = SocialWorkerName;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
