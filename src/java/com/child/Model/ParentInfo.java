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
public class ParentInfo {
    
    private  String fatherName;
    private String motherName;
    private String fatherID, motherID;
    private long contactNumber;
    private String physicalAddress;
    
    //default constructor 
    public ParentInfo(){}
    
    // non-default constructor
    public ParentInfo(String fatherName,String motherName,String fatherID,String motherID,
            long contactNumber,String physicalAddress){
        
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.motherID = motherID;
        this.fatherID = fatherID;
        this.contactNumber = contactNumber;
        this.physicalAddress = physicalAddress;
    }
    
    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }
    
    
}
