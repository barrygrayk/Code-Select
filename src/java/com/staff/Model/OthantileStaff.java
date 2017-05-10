package com.staff.Model;

/**
 *
 * @author efsan1
 */

import java.util.Date;


public class OthantileStaff extends Person implements Role {

    private int staffID;
    private Message massage;
    private Authentication authcateDetails;
    private int accesslevel;
    private String roleName;

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public OthantileStaff(int staffID, String firstname, String lastname, char gender, String address, String placeOfBirth, Date dateOfBirth, String emailAddress) {
        super(firstname, lastname, gender, address, placeOfBirth, dateOfBirth, emailAddress);
        this.staffID = staffID;

    }

    public OthantileStaff(String firstname, String lastname, char gender, String address, String placeOfBirth, Date dateOfBirth, String emailAddress) {
        super(firstname, lastname, gender, address, placeOfBirth, dateOfBirth, emailAddress);

    }

    public OthantileStaff(String firstname, String lastname, char gender, String phoneNumber, String address, String placeOfBirth, Date dateOfBirth, String emailAddress) {
        super(firstname, lastname, gender, phoneNumber, address, placeOfBirth, dateOfBirth, emailAddress);

    }

    /**
     *
     */
    public OthantileStaff() {
    }

    public Message getMassage() {
        return massage;
    }

    public Authentication getAuthcateDetails() {
        return authcateDetails;
    }

    public void setMassage(Message massage) {
        this.massage = massage;
    }

    public void setAuthcateDetails(Authentication authcateDetails) {
        this.authcateDetails = authcateDetails;
    }

    /**
     *
     * @return
     */
    @Override
    public int getAge() {
        return 2;
    }

    @Override
    public boolean setRoleName(String name) {
        boolean setRole = false;
        if (name.equals("Admin") || name.equals("Intern") || name.equals("Caregiver")) {
            this.roleName = name;
            setRole = true;
        }
        return setRole;
    }

    @Override
    public String getRoleName() {
        return roleName;
    }

    @Override
    public boolean setAccessLevel(int acess) {
        boolean setAccess = false;
       if (acess==1 || acess == 2 || acess == 3){
           accesslevel = acess;
           setAccess = true;
       }
       return setAccess;
    }

    @Override
    public int getAccessLevel() {
       return accesslevel;
    }

}
