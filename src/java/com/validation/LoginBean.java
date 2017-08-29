package com.validation;

import com.MenuView.MenuView;
import com.db.connection.InternAplicationTableConnection;
import com.db.connection.StaffTableConnection;
import com.staff.Model.Authenticate;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

//import javax.ws.rs.Path;
/**
 *
 * @author Barry Gray Kapelembe
 */
@ManagedBean(name = "Login", eager = true)
@SessionScoped
//@Path("/login")
public class LoginBean extends Passwords implements Serializable {
//us MrKaplan for error checking

    public String getRole() {
        return role;
    }
    private String username, fullname, role, status, password;
    private int id;
    private String expectedHash = null;
    private byte[] salt = null;
    private static final long serialVersionUID = 1094801825228386363L;
    private List<Authenticate> authenticatedStaff, authenticatedApplicant;
    private StaffTableConnection staffDB;
    private InternAplicationTableConnection ApplicantDB;
    private final MenuView feedBack = new MenuView();

    public LoginBean() {
        super();
    }

    public void init() {
        try {

            staffDB = new StaffTableConnection();
            ApplicantDB = new InternAplicationTableConnection();
            authenticatedStaff = staffDB.getAuthenticatedStaff();
            authenticatedApplicant =ApplicantDB.getAuthenticatedApplicant();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            feedBack.error("Database Error", ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            feedBack.error("Read Error", ex.getMessage());
        }
    }

    public String doLogin() throws InvalidKeySpecException, UnsupportedEncodingException {
        String page = "";
        if (username.contains("@")) {
            setAuthApplicant();
        } else {
            setAuthStaff();
        }
        if (password != null && salt != null && expectedHash != null && getSecurePassword(password, salt).equals(expectedHash)) {
            switch (status) {
                case "Pending activaton":
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                            fullname + "'s account has not been activated",
                            "Please check your mail for the activation link or contact admin for help: othantile@admin.com"));
                    break;
                case "Pending reset":
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                            fullname + "'s account has not been reset",
                            "Please check your mail for the reset link or contact admin for help: othantile@admin.com"));
                    break;
                case "Active":
                    if (username.contains("@")) {
                        HttpSession session = SessionUtils.getSession();
                        session.setAttribute("username", username);
                          session.setAttribute("id", id);
                      
                        
                        page = "apply.xhtml?faces-redirect=true";
                    } else if (role.equals("Admin")) {
                        HttpSession session = SessionUtils.getSession();
                        session.setAttribute("username", username);
                        session.setAttribute("LoggedIn", "LoggedIn");
                        page = "dashboard.xhtml?faces-redirect=true";
                    } else {
                        feedBack.error("Access denied ", fullname + " you do not have enough rights to view requested page."
                                + " Please contact admin: othantile@admin.com");
                    }
                    break;
                case "Deactivated":
                    feedBack.errorMessage(fullname + " Your account has been deactivated please contact admin for help: othantile@admin.com");
                    break;
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Incorrect Username and Passowrd",
                    "Please enter correct username and Password"));
            return "login";
        }
        return page;
    }

    private void setAuthStaff() {
        for (Authenticate auth : authenticatedStaff) {
            if (auth.getUsername().equals(username)) {
                id = auth.authId();
                setUsername(auth.getUsername());
                fullname = staffDB.getFullname(id);
                status = auth.getStatus();
                role = staffDB.getRole(id);
                expectedHash = auth.getHashedPassword();
                salt = auth.getSalt();
            }
        }
    }

    private void setAuthApplicant() {
        for (Authenticate auth : authenticatedApplicant) {
            if (auth.getUsername().equals(username)) {
                id = auth.authId();
                setUsername(auth.getUsername());
                fullname = ApplicantDB.getFullname(id);
                status = auth.getStatus();
                expectedHash = auth.getHashedPassword();
                salt = auth.getSalt();
            }
        }
    }

    public void apply() throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + "internApplicationRequest.xhtml");

    }

    public void forgot() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Forgot password?",
                "Please contact admin for password recovery: othantile@admin.com"));
    }

    public String doLogOut() {
        HttpSession hs = SessionUtils.getSession();
        hs.invalidate();

        return "login.xhtml?faces-redirect=true";
    }

    public String getPassWord() {
        return password;
    }

    public void setPassWord(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    void getAuthenticationDetails() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getFullname() {
        return fullname;
    }

    public int getId() {
        return id;
    }

}
