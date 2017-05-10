package com.Controllers;
import com.MenuView.MenuView;
import com.db.connection.StaffTableConnection;
import com.staff.Model.OthantileStaff;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Barry Gray Kapelembe
 */
@ManagedBean(name = "StaffMembers")
@RequestScoped
public class StaffMemberController extends OthantileStaff implements Serializable {

    private int staffiid;
    private List<OthantileStaff> staff = new ArrayList<>();
    private List<OthantileStaff> selectedStaffs = new ArrayList<>();
    private OthantileStaff selectedsatff; 

    public OthantileStaff getSelectedsatff() {
        return selectedsatff;
    }

    public void setSelectedsatff(OthantileStaff selectedsatff) {
        this.selectedsatff = selectedsatff;
    }

   
    
    private List<String> roles = new ArrayList<>();
    private String role;
    private int id;

    public String getRole() {
        return role;
    }

    public int getStaffiid() {
        return staffiid;
    }

    public void setStaffiid(int staffiid) {
        this.staffiid = staffiid;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @PostConstruct
    public void init() {
        roles = new ArrayList<>();
        roles.add("Admin");
        roles.add("Caregiver");
        roles.add("Intern");
    }

    public List<OthantileStaff> getStaff() {
        return staff;
    }

    public void setStaff(List<OthantileStaff> staff) {
        this.staff = staff;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void addStaffMember() throws ClassNotFoundException, SQLException {
        OthantileStaff staff;

        if (getFirstname().trim().isEmpty() || getLastname().trim().isEmpty() || getAddress().trim().isEmpty() || getPlaceOfBirth().trim().isEmpty()) {
            MenuView view = new MenuView();
            view.error("Spaces", "Spaces are not avild input");

        } else {
            staff = new OthantileStaff(staffiid, getFirstname(), getLastname(), getGender(), getAddress(), getPlaceOfBirth(), getDateOfBirth(), getEmailAddress());
            System.out.println(role);
            staff.setAccessLevel(assignAccessLevel(role));
            staff.setRoleName(role);
            new StaffTableConnection().addStaffMemeber(staff);
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        }

    }

    public List<OthantileStaff> getAllStaffMemebers() {
        staff = new StaffTableConnection().getStaffMemebers();
        return staff;
    }
    
    public void loasSaffMember(int id) {
        staffiid = id;
        this.id = id;
        setStaffID(id);
        setStaffiid(staffiid);
        try {
            OthantileStaff sta = new StaffTableConnection().getAStaffMember(id);
            //super(sta.getFirstname(),sta.getLastname(),sta.getGender(),sta.getAddress(),sta.getDateOfBirth(),sta.getDateOfBirth(),sta.getEmailAddress());
            setFirstname(sta.getFirstname());
            setLastname(sta.getLastname());
            setGender(sta.getGender());
            setAddress(sta.getAddress());
            setEmailAddress(sta.getEmailAddress());
            setDateOfBirth(sta.getDateOfBirth());
            setPlaceOfBirth(sta.getPlaceOfBirth());
            role = sta.getRoleName();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StaffMemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String updateStaffRecord() throws ClassNotFoundException, SQLException {
        OthantileStaff staff = new OthantileStaff(getStaffID(), getFirstname(), getLastname(), getGender(), getAddress(), getPlaceOfBirth(), getDateOfBirth(), getEmailAddress());
        System.out.println(role);
        staff.setAccessLevel(assignAccessLevel(role));
        staff.setRoleName(role);
        new StaffTableConnection().updateStaffMember(staff);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "dashboard";
    }

    public String deleteStaffMember(int id) throws ClassNotFoundException {
        new StaffTableConnection().deleteStaff(id);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "dashboard";
    }
    public int assignAccessLevel(String role) {
        int access = 0;
        switch (role) {
            case "Admin":
                access = 1;
                break;
            case "Caregiver":
                access = 2;
                break;
            case "Intern":
                access = 3;
                break;
        }
        return access;
    }
}
