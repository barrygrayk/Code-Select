package com.staff.Controllers;

import com.MenuView.MenuView;
import com.db.connection.StaffTableConnection;
import com.navigation.Bean;
import com.staff.Model.OthantileStaff;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
//import org.primefaces.event.SelectEvent;
//import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Barry Gray Kapelembe
 */
@ManagedBean(name = "StaffMembers", eager = true)
@RequestScoped
public class StaffController extends OthantileStaff implements Serializable {
    private List <String> staffMemberNames = new ArrayList ();
    private String staffName;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public List<String> getStaffMemberNames() {
        return staffMemberNames;
    }

    public void setStaffMemberNames(List<String> staffMemberNames) {
        this.staffMemberNames = staffMemberNames;
    }
    

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;
    private int staffiid;
    private List<OthantileStaff> staff = new ArrayList<>();
    private List<OthantileStaff> selectedStaffMembers = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private List<OthantileStaff> searchStaff = new ArrayList<>();
    private List<String> roles = new ArrayList<>();
    private String role;
    private OthantileStaff selectedsatff;

    public List<OthantileStaff> getSearchStaff() {
        return searchStaff;
    }

    public void setSearchStaff(List<OthantileStaff> searchStaff) {
        this.searchStaff = searchStaff;
    }
    private int id;

    public List<OthantileStaff> getSelectedStaffMembers() {
        System.out.println("____________selection_______geting");

        return selectedStaffMembers;
    }

    public void setSelectedStaffMembers(List<OthantileStaff> staff) {
        System.out.println("____________selection_______setting");

        this.selectedStaffMembers = staff;
    }

    public void onRowSelect(SelectEvent e) {
        selectedsatff = (OthantileStaff) e.getObject();
        System.out.println("____________Eder_______");
    }

    public void onRowUnselect(UnselectEvent e) {
        selectedsatff = null;
        System.out.println("____________Rosa_______");
    }

    public OthantileStaff getSelectedsatff() {
        return selectedsatff;
    }

    public void setSelectedsatff(OthantileStaff selectedsatff) {
        this.selectedsatff = selectedsatff;
    }

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
       List<OthantileStaff> staffList= getAllStaffMemebers();
       staffList.forEach((sta) -> {
           staffMemberNames.add(sta.getFirstname() +" " +sta.getLastname() );
        });
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
        System.out.println("We are here_________");
        if (getFirstname().trim().isEmpty() || getLastname().trim().isEmpty() || getAddress().trim().isEmpty() || getPlaceOfBirth().trim().isEmpty()) {
            MenuView view = new MenuView();
            view.error("Spaces", "Spaces are not avild input");

        } else {
            staff = new OthantileStaff(staffiid, getFirstname(), getLastname(), getGender(), getAddress(), getPlaceOfBirth(), getDateOfBirth(), getEmailAddress());
            System.out.println(role);
            staff.setAccessLevel(assignAccessLevel(role));
            staff.setRoleName(role);
             new StaffTableConnection().addStaffMemeber(staff);
              System.out.println("We are  not in if here_________");
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        }

        

    }

    public List<OthantileStaff> getAllStaffMemebers() {
        System.out.println("All________________________");
        staff = new StaffTableConnection().getStaffMemebers();
        return staff;
    }

    public void loasSaffMember(int id) {
        staffiid = id;
        this.id = id;
        setStaffID(id);
        setStaffiid(staffiid);
        if (selectedsatff != null) {
            int iddd = selectedsatff.getStaffID();

            try {
                OthantileStaff sta = new StaffTableConnection().getAStaffMember(iddd);
                if (sta != null) {
                    //super(sta.getFirstname(),sta.getLastname(),sta.getGender(),sta.getAddress(),sta.getDateOfBirth(),sta.getDateOfBirth(),sta.getEmailAddress());
                    setFirstname(sta.getFirstname());
                    setLastname(sta.getLastname());
                    setGender(sta.getGender());
                    setAddress(sta.getAddress());
                    setEmailAddress(sta.getEmailAddress());
                    setDateOfBirth(sta.getDateOfBirth());
                    setPlaceOfBirth(sta.getPlaceOfBirth());
                    role = sta.getRoleName();
                    MenuView view = new MenuView();
                    //view.addMessage("Record has been loaded", null);

                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }

    public String updateStaffRecord() throws ClassNotFoundException, SQLException {
        System.out.println("_________ID__        dkdkddkdkkdkdnkdnkdnkdnkdkdnkdnkdnkdnkdnkdnk________" + selectedsatff.getStaffID());
        OthantileStaff staff = new OthantileStaff(selectedsatff.getStaffID(), getFirstname(), getLastname(), getGender(), getAddress(), getPlaceOfBirth(), getDateOfBirth(), getEmailAddress());
        System.out.println(role);
        staff.setAccessLevel(assignAccessLevel(role));
        staff.setRoleName(role);
        new StaffTableConnection().updateStaffMember(staff);
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "viewStaff";
    }

    public String deleteStaffMember() throws ClassNotFoundException {
        String toPage = null;
        if (selectedsatff != null) {
            Bean setPage = new Bean();
            setPage.setPage("viewStaff");
            new StaffTableConnection().deleteStaff(selectedsatff.getStaffID());
        } else {
            MenuView out = new MenuView();
            out.error("Detele error", "No staff member has been selected");

        }
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return toPage;
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
