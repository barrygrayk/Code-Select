package com.validation;

import com.MenuView.MenuView;
import com.db.connection.StaffTableConnection;
import com.staff.Model.Authenticate;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
    private String password;
    private String username;
    private String fullname;
    private int id;
    private static final long serialVersionUID = 1094801825228386363L;
    private List<Authenticate> authenticatedStaff;
    private StaffTableConnection staffDB;
    private final MenuView feedBack = new MenuView();

    public LoginBean() {
        super();
    }

    @PostConstruct
    public void init() {
        try {

            staffDB = new StaffTableConnection();
            authenticatedStaff = staffDB.getAuthenticatedStaff();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            feedBack.error("Database Error", ex.getMessage());

        } catch (SQLException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            feedBack.error("Read Error", ex.getMessage());
        }
        System.out.println("int____" + authenticatedStaff.size());
    }

    public String doLogin() throws InvalidKeySpecException, UnsupportedEncodingException {
        int authCount = 0;
        byte[] expectedHash = null;
        byte[] salt = null;
        System.out.println(authenticatedStaff.size());
        for (Authenticate auth : authenticatedStaff) {
            System.out.println("thatusernam " + auth.getUsername());
            if (auth.getUsername().equals(username)) {
                authCount++;
                id = auth.authId();
                setUsername(auth.getUsername());
                System.out.println("MATCH________________" + username);
                fullname = staffDB.getFullname(id);
                expectedHash = auth.getHashedPassword();
                salt = auth.getSalt();
            }
        }
        if (password!=null&&salt!=null && expectedHash !=null && isExpectedPassword(password.toCharArray(), salt, expectedHash)) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", username);
            session.setAttribute("LoggedIn", "LoggedIn");
            return "dashboard.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Incorrect Username and Passowrd",
                    "Please enter correct username and Password"));
            return "login";
        }
    }

    public String doLogOut() {
        System.out.println("__________________________________com.validation.LoginBean.doLogOut()");
        HttpSession hs = SessionUtils.getSession();
        hs.invalidate();

        return "login.xhtml";
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
