/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.child.Model;

import java.util.Date;

/**
 *
 * @author efsan1
 */
public class IntakeInfo {
    
    private String personWhoDropped;
    private String fetchLocation;
    private String personWhoFetched;
    private long phoneNumber;
    private Date dateAndTime;
    
    public IntakeInfo(String personWhoDropped, String fetchLocation,
            String personWhoFetched, long phoneNumber, Date dateAndTime){
        
        this.dateAndTime = dateAndTime;
        this.personWhoDropped = personWhoDropped;
        this.fetchLocation = fetchLocation;
        this.personWhoFetched = personWhoFetched;
        this.phoneNumber = phoneNumber;
    }

    IntakeInfo() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getPersonWhoDropped() {
        return personWhoDropped;
    }

    public void setPersonWhoDropped(String personWhoDropped) {
        this.personWhoDropped = personWhoDropped;
    }

    public String getFetchLocation() {
        return fetchLocation;
    }

    public void setFetchLocation(String fetchLocation) {
        this.fetchLocation = fetchLocation;
    }

    public String getPersonWhoFetched() {
        return personWhoFetched;
    }

    public void setPersonWhoFetched(String personWhoFetched) {
        this.personWhoFetched = personWhoFetched;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
