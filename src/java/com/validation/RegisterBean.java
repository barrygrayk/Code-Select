package com.validation;

import com.MenuView.MenuView;
import com.applicants.Model.Applicant;
import com.db.connection.InternAplicationTableConnection;
import com.db.connection.StaffTableConnection;
import com.staff.Model.Authenticate;
import com.staff.Model.Authentication;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

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
    private int id;
    private String token;
    private String username;
    private String fullname;
    private String password;
    private String rePassword;

    public RegisterBean() {
        super();
    }

    public String getUsername() {

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
        HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        System.out.println(origRequest.getQueryString());
        try {
            staffDB = new StaffTableConnection();
            authenticatedStaff = staffDB.getAuthenticatedStaff();
            token = origRequest.getQueryString().replace("token=", "").trim();
            JJWT jwt = new JJWT();
            jwt.verifyToken(token);
            System.out.println(token);
            if (jwt.getSubject().contains("@")) {
                List<Applicant> applicants = new InternAplicationTableConnection().getApplicants();
                for (Applicant app : applicants) {
                    if (jwt.getId().equals(app.getId() + "") && app.getApplicationStatus().equals("Request pending")) {
                        if (jwt.getSubject().equals(app.getEmailAddress())) {
                            username = app.getEmailAddress();
                            fullname = app.getFirstname() + " " + app.getLastname();
                            message = "Welcome ";
                            id = Integer.parseInt(jwt.getId());
                            setDisable(false);
                        }
                    } else {
                        message = "Token could not be validated.This account has been deactivated";
                    }
                }
            } else {
                for (Authenticate auth : authenticatedStaff) {
                    if (auth.getToken().equals(token)) {
                        if ((!auth.getStatus().endsWith("Deactivated")) && jwt.getSubject().equals(auth.getUsername())) {
                            if (!auth.getStatus().equals("Active")) {
                                username = auth.getUsername();
                                fullname = staffDB.getFullname(auth.authId());
                                setDisable(false);
                            } else {
                                message = "This token is already active. Contact admistration for a new token";
                            }
                        } else {
                            message = "Token could not be validated.This account has been deactivated";
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
        } catch (NullPointerException ex) {
            message = "Token not found: Contact admin for token: othantil@admin.com";
        }
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public void goToLoginPag() throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + "/faces/login.xhtml");
        //return "login.xhtml?faces-redirect=true";
    }

    public String activate() throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException, ClassNotFoundException, SQLException {
        String goTo = "";
        if (checkLength() && checkEquality()) {
            byte[] salt = getNextSalt();
            String hashed = getSecurePassword(password, salt);
            Authenticate auth = new Authentication();
            auth.setSalt(salt);
            auth.sethashPassword(hashed);
            auth.setUsername(username);
            auth.setStatus(2);
            if (username.contains("@")) {
                auth.setAuthId(id);
                auth.setToken(token);
                new InternAplicationTableConnection().setApplicantAuthDetails(auth);
                
            } else {
                staffDB.updatePassword(auth);
            }
            message = "Hello "+ fullname+ " Click login button to sign into your account";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    fullname + "'s account activated",
                    "Account has been sucessfully activated"));
            setDisable(true);
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
        // "((?=.*\\d)(?=.*[a-zA-Z])(?=.*[~'!@#$%?\\\\/&*\\]|\\[=()}\"{+_:;,.><'-])).{8,}"
        //((?=.*\\\\d)(?=.*[a-zA-Z])(?=.*[~'!@#$%?\\\\\\\\/&*\\\\]|\\\\[=()}\\\"{+_:;,.><'-])).{8,}
        //^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}
        //(?=.*?\\\\d)(?=.*?[a-zA-Z])(?=.*?[^\\\\w]).{8,}
        final Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}");
        Matcher mat = pattern.matcher(password);
        if (mat.matches()) {
            valid = true;
        } else {
            feedBack.error("Password strenght", "Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character");
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
