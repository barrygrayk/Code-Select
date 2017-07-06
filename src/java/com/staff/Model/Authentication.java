package com.staff.Model;

import java.util.Random;

/**
 *
 * @author Gray
 */

public class Authentication implements Authenticate {
    private int id;
    private String hashedPassword;
    private String salt;
    private String userName;
    String [] statusArray = {"Pending activaton","Pending reset","Active"};
    String status;

    public Authentication() {
        hashedPassword = "testing";
        userName = null;
        salt = "testing";
        status=statusArray[0];
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean createUsername(String firstname, String lastname) {
        boolean set = false;
        Random rnd = new Random();
        int num = rnd.nextInt(99) + 1;
        if (firstname != null && lastname != null) {
            this.userName = firstname.toLowerCase().substring(0,2) + lastname.toLowerCase().substring(0, 3) + num;
            set = true;
        }
        return set;
    }

    @Override
    public boolean sethashPassword(String hash) {
        boolean set = false;
        if (hash != null) {
            this.hashedPassword = hash;
            set = false;
        }
        return set;
    }

    @Override
    public String getHashedPassword() {
        return hashedPassword;
    }

    @Override
    public boolean setSalt(String salt) {
        boolean set = false;
        if (salt != null) {
            this.salt = salt;
            set = false;
        }
        return set;
    }

    @Override
    public String getSalt() {
        return salt;
    }

    @Override
    public boolean setStatus(int status) {
        boolean set = false;
        if (status >=0 && status <= 2){
            this.status = statusArray[status];
            set = true;
        }
        return set;
    }

    @Override
    public String getStatus() {
        return status;      
    }

    @Override
    public boolean setUsername(String username) {
        boolean set = false;
        if (username != null){
             this.userName = username;
             set = true;
        }
       return set;
        
    }

    @Override
    public int authId() {
       return id;
    }

    @Override
    public boolean setAuthId(int id) {
      boolean set = false;
      if (id>0){
          this.id=id;
          set = true;
      }
      return set;
    }
}
