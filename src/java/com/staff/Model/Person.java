package com.staff.Model;

import java.util.Date;


/**
 * 
 * @author Barry Gray Kapelembe 
 */


public abstract class Person {
    private String firstname;
    private String lastname;
    private char gender;
    private String phoneNumber;
    private String address;

    public Person(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
    
    
    private String placeOfBirth;
    private Date dateOfBirth;
    private String emailAddress;

    public Person(String firstname, String lastname, char gender, String phoneNumber, String address, String placeOfBirth, Date dateOfBirth, String emailAddress) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.placeOfBirth = placeOfBirth;
        this.dateOfBirth = dateOfBirth;
        this.emailAddress = emailAddress;
    }
    
    public Person(String firstname, String lastname, char gender, String address, String placeOfBirth, Date dateOfBirth, String emailAddress) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.address = address;
        this.placeOfBirth = placeOfBirth;
        this.dateOfBirth = dateOfBirth;
        this.emailAddress = emailAddress;
    }
    

    public Person(String firstname, String lastname, char gender, String placeOfBirth, Date dateOfBirth) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.placeOfBirth = placeOfBirth;
        this.dateOfBirth = dateOfBirth;
    }

    public Person(String firstname, String lastname, String phoneNumber, String address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    
     public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Person() {
    }
    

    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getPhoneNumbe() {
        return phoneNumber;
    }

    public void setPhoneNumbe(String phoneNumbe) {
        this.phoneNumber = phoneNumbe;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

   

   
    
    public abstract int getAge();
    

}
