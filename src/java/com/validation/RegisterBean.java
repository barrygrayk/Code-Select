/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.validation;

import com.MenuView.MenuView;
import com.db.connection.StaffTableConnection;
import com.staff.Model.Authenticate;
import com.staff.Model.Authentication;
import java.io.Serializable;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Barry Gray
 */
@ManagedBean(name = "RegisterStaff", eager = true)
@ViewScoped

public class RegisterBean extends Passwords implements Serializable {

    private List<Authenticate> authenticatedStaff;
    private StaffTableConnection staffDB;
    private final MenuView feedBack = new MenuView();
    private boolean disable = true;
    private String message = "Welcome ";
    private String username;
    private String fullname;
    private String password;
    private String rePassword;

    public RegisterBean() {
        super();
    }

    public String getUsername() {
        if (username == null) {
            feedBack.error("Token error", "This token is already active. Contact admistration for a new token");
        }
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    @PostConstruct
    public void init() {
        System.out.println("int-------");
        HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        System.out.println(origRequest.getQueryString());
        try {
            staffDB = new StaffTableConnection();
            authenticatedStaff = staffDB.getAuthenticatedStaff();
            String token = origRequest.getQueryString().replace("token=", "").trim();
            System.out.println("replaced===" + token);
            JJWT jwt = new JJWT();
            jwt.verifyToken(token);
            for (Authenticate auth : authenticatedStaff) {
                System.out.println(auth.getUsername());
                if (auth.getToken().equals(token)) {
                    if (jwt.getSubject().equals(auth.getUsername())) {
                        if (!auth.getStatus().equals("Active")) {
                            username = auth.getUsername();
                            fullname = staffDB.getFullname(auth.authId());
                            setDisable(false);
                        } else {
                            message = "This token is already active. Contact admistration for a new token";
                        }

                    }
                }
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            feedBack.error("Database Error", ex.getMessage());

        } catch (SQLException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            feedBack.error("Read Error", ex.getMessage());
        }
        System.out.println("Done-----------");
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public String activate() throws InvalidKeySpecException {
        System.out.println("activting-------");
        String goTo =   "";

        if (checkLength() && checkEquality()) {
            byte[] salt = getNextSalt();
            byte[] hashed = hash(password.toCharArray(), salt);
            System.out.println(salt);
            System.out.println(hashed);
            Authenticate auth = new Authentication();
            auth.setSalt(salt);
            auth.sethashPassword(hashed);
            auth.setUsername(username);
            auth.setStatus(2);
            try {
                staffDB.updatePassword(auth);
               goTo =  "login.xhtml?faces-redirect=true";
                //setDisable(true);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
                feedBack.error("Database Error", ex.getMessage());
            }
        }
        
        return goTo;
    }

    public boolean isDisable() {
        return disable;
    }

    private void setDisable(boolean disable) {
        this.disable = disable;
    }

    private boolean checkLength() {
        boolean valid = false;
        final Pattern pattern = Pattern.compile("^[a-zA-Z]\\w{3,14}$");
        Matcher mat = pattern.matcher(password);
        if (mat.matches()) {
            valid = true;
        } else {
            feedBack.error("Password strenght", "The password's first "
                    + "character "
                    + "must be a letter, it must contain at least 4"
                    + " characters and no more than 15 characte"
                    + "rs and no characters other than letters, number"
                    + "s and the underscore may be used");
        }
        return valid;
    }

    private boolean checkEquality() {
        boolean valid = false;
        if (password.equals(rePassword)) {
            valid = true;
        } else {
            feedBack.error("Password strenght", "Password miss match");

        }
        return valid;
    }

    @Override
    void getAuthenticationDetails() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
