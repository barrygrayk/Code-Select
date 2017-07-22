package com.staff.Controllers;

import com.MenuView.MenuView;
import com.db.connection.StaffTableConnection;
import com.navigation.Bean;
import com.staff.Model.Authenticate;
import com.staff.Model.Authentication;
import com.staff.Model.OthantileShift;
import com.staff.Model.OthantileStaff;
import com.staff.Model.Shift;
import com.validation.MrKaplan;
import com.validation.TheEqualizer;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Barry Gray Kapelembe
 */
@ManagedBean(name = "StaffMembers", eager = true)
@ViewScoped
public class StaffController extends OthantileStaff implements Serializable {

    private List<String> staffMemberNames = new ArrayList();
    private String staffName;
    private Date date, shitDate, shiftTime;
    private int staffiid;
    private List<Shift> shifts = new ArrayList<>();
    private List<OthantileStaff> staff = new ArrayList<>();
    private List<OthantileStaff> selectedStaffMembers = new ArrayList<>();
    private List<OthantileStaff> searchStaff = new ArrayList<>();
    private List<String> roles = new ArrayList<>();
    private String role;
    private OthantileStaff selectedsatff;
    private String accountStatus = "Default";

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<String> getShiftTypes() {
        return shiftTypes;
    }

    public Date getShitDate() {
        return shitDate;
    }

    public void setShitDate(Date shitDate) {
        this.shitDate = shitDate;
    }

    public Date getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(Date shiftTime) {
        this.shiftTime = shiftTime;
    }

    public void setShiftTypes(List<String> shiftTypes) {
        this.shiftTypes = shiftTypes;
    }
    private List<String> shiftTypes = new ArrayList();
    private String shift;

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OthantileStaff> getSearchStaff() {
        return searchStaff;
    }

    public void setSearchStaff(List<OthantileStaff> searchStaff) {
        this.searchStaff = searchStaff;
    }
    private int id;

    public List<OthantileStaff> getSelectedStaffMembers() {
        return selectedStaffMembers;
    }

    public void setSelectedStaffMembers(List<OthantileStaff> staff) {
        this.selectedStaffMembers = staff;
    }

    public void onRowSelect(SelectEvent e) {
        selectedsatff = (OthantileStaff) e.getObject();
    }

    public void onRowUnselect(UnselectEvent e) {
        selectedsatff = null;
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
        shiftTime = new Date();
        roles = new ArrayList<>();
        roles.add("Admin");
        roles.add("Caregiver");
        roles.add("Intern");
        shiftTypes.add("Single (8hrs)");
        shiftTypes.add("double (16hrs)");
        List<OthantileStaff> staffList = getAllStaffMemebers();
        shifts = getShifts();
        System.out.println(shifts.size());
        staffList.forEach((sta) -> {
            staffMemberNames.add(sta.getFirstname() + " " + sta.getLastname());
        });
        accountStatus = "Default";
    }

    public List<Shift> getShifts() {
        shifts = new StaffTableConnection().getAllShifts();
        staff.forEach((stf) -> {
            shifts.stream().filter((sh) -> (stf.getStaffID() == sh.shitID())).forEachOrdered((sh) -> {
                sh.setNames(stf.getFirstname() + " " + stf.getLastname());
            });
        });
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
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

    public String addStaffMember() throws ClassNotFoundException, SQLException {
        MrKaplan validate = new MrKaplan();
        TheEqualizer eqi = new TheEqualizer();
        OthantileStaff staff;
        if (validate.isValidInput(getFirstname()) && validate.isValidInput(getLastname()) && validate.isAcceptableAddress(getAddress())
                && validate.isAcceptableAddress(getPlaceOfBirth())) {
            staff = new OthantileStaff(staffiid, eqi.toUperAndLower(getFirstname()), eqi.toUperAndLower(getLastname()), getGender(),
                    eqi.toUperAndLower(getAddress()), eqi.toUperAndLower(getPlaceOfBirth()), getDateOfBirth(), getEmailAddress().toLowerCase());
            System.out.println(role);//eqi.toUperAndLower(getAddress())
            staff.setAccessLevel(assignAccessLevel(role));
            staff.setRoleName(role);
            Authenticate auth = new Authentication();
            auth.createUsername(staff.getFirstname(), staff.getLastname());
            staff.setAuthcateDetails((Authentication) auth);
            new StaffTableConnection().addStaffMemeber(staff);
        }
        return "viewStaff";
    }

    public List<OthantileStaff> getAllStaffMemebers() {
        staff = new StaffTableConnection().getStaffMemebers();
        return staff;
    }

    public void loasSaffMember() {
        if (selectedsatff != null) {
            int iddd = selectedsatff.getStaffID();
            try {
                OthantileStaff sta = new StaffTableConnection().getAStaffMember(iddd);
                if (sta != null) {
                    setFirstname(sta.getFirstname());
                    setLastname(sta.getLastname());
                    setGender(sta.getGender());
                    setAddress(sta.getAddress());
                    setEmailAddress(sta.getEmailAddress());
                    setDateOfBirth(sta.getDateOfBirth());
                    setPlaceOfBirth(sta.getPlaceOfBirth());
                    role = sta.getRoleName();
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String updateStaffRecord() throws ClassNotFoundException, SQLException {
        MrKaplan validate = new MrKaplan();
        TheEqualizer eqi = new TheEqualizer();
        if (validate.isValidInput(getFirstname()) && validate.isValidInput(getLastname())
                && validate.isAcceptableAddress(getAddress())
                && validate.isAcceptableAddress(getPlaceOfBirth()) && validate.isValidDate(getDateOfBirth())) {
            OthantileStaff staff = new OthantileStaff(selectedsatff.getStaffID(), eqi.toUperAndLower(getFirstname()),
                    eqi.toUperAndLower(getLastname()), getGender(),
                    eqi.toUperAndLower(getAddress()), eqi.toUperAndLower(getPlaceOfBirth()), getDateOfBirth(), getEmailAddress().toLowerCase());
            staff.setAccessLevel(assignAccessLevel(role));
            staff.setRoleName(role);
            Authenticate auth = new Authentication();
            System.out.println("1 " + accountStatus);
            if (accountStatus != null) {
                if (accountStatus.equals("Deactivate")) {
                    auth = selectedsatff.getAuthcateDetails();
                    auth.setStatus(3);
                } else {
                    auth = selectedsatff.getAuthcateDetails();
                    auth.setStatus(1);
                }
            }
            staff.setAuthcateDetails((Authentication) auth);
            new StaffTableConnection().updateStaffMember(staff);
            accountStatus = "Default";
        }
        return "viewStaff";
    }

    public String deleteStaffMember() throws ClassNotFoundException {
        String toPage = null;
        if (selectedsatff != null) {
            new StaffTableConnection().deleteStaff(selectedsatff.getStaffID());
        } else {
            MenuView out = new MenuView();
            out.error("Detele error", "No staff member has been selected");
        }
        return "viewStaff";
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

    public void assignShift() {
        MenuView out = new MenuView();
        int index = 0, fKey = 0;
        if (staffMemberNames.size() > 0) {
            for (String names : staffMemberNames) {
                if (names.endsWith(staffName)) {
                    index = staffMemberNames.indexOf(names);
                }
            }
            if (staff.size() > 0) {
                fKey = staff.get(index).getStaffID();
                System.out.println(fKey);
                Shift assignShift = new OthantileShift(fKey, shitDate, shiftTime);
                assignShift.setShiftndTime(shift);
                new StaffTableConnection().assignAShift(assignShift);
            } else {
                out.error("Shit error", "No staff members found");
            }
        } else {
            out.error("Shit error", "No staff members found");
        }
    }

    public void search() {
    }
}
