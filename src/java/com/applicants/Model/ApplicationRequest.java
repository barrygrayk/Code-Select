package com.applicants.Model;

import com.staff.Model.Person;

/**
 * 
 * @author Barry Gray
 */
public class ApplicationRequest extends Person {
    
    private int id;
    private String message;

    public ApplicationRequest(int id, String firstname, String lastname, String phoneNumber, String address) {
        super(firstname, lastname, phoneNumber, address);
        this.id = id;
    }

    public ApplicationRequest() {
    }
    
    

    public ApplicationRequest(String firstname, String lastname, String phoneNumber, String address) {
        super(firstname, lastname, phoneNumber, address);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
    
    
    

    @Override
    public int getAge() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
