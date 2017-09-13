    

package com.applicants.Model;

import com.staff.Model.Person;


/**
 *
 * @author efsan1
 */


public class EmergencyContact extends Person {
    
    private String relationship;
    private int id;

    public EmergencyContact() {
    }

    
    public EmergencyContact(String firstname, String lastname, String phoneNumber,  String emailAddress) {
        super(firstname, lastname, phoneNumber,  emailAddress);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
    
    @Override
    public int getAge() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
