package com.db.connection;

import com.MenuView.MenuView;
import com.staff.Model.OthantileStaff;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Barry Gray Kapelembe
 */
public class StaffTableConnection extends DatabaseConnection {

    private List<OthantileStaff> staff = new ArrayList<>();
    private MenuView feedback = new MenuView ();

    public StaffTableConnection() {
        super();
    }

    boolean recordValidator(String validate) {
        String toVaildate =validate;
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addStaffMemeber(OthantileStaff staff) throws ClassNotFoundException {
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            MenuView view = new MenuView();
            String sql = "INSERT INTO .`onthantilestaff`(`firstName`,`LastName`,`gender`,`address`,`placeOfBirth`,`dateOfBirth`,`emailAddress`) VALUES(?,?,?,?,?,?,?)";
            ps1 = setOthantileStaffColumns(staff, sql, false);
            if (ps1.execute()) {
                view.errorMessage(staff.getFirstname() + " Could not be added");
            } else {
                view.addSatff(staff.getFirstname());
                ResultSet key = ps1.getGeneratedKeys();
                if (key != null && key.next()) {
                    int id = key.getInt(1);
                    String sql2 = "INSERT INTO .`staffroles` (`roleName`,`accessLevel`,`OnthantileStaff_staffID`) VALUES (?,?,?)";
                    ps2 = setOthantileRoleColumns(staff, sql2, id);
                    if (!ps2.execute()) {
                        view.addSatff(staff.getFirstname() + "'s role has been assigned");
                    } else {
                        view.errorMessage(staff.getFirstname() + " role was not assigned");
                    }
                } else {
                    view.errorMessage(staff.getFirstname() + "s' key was not found");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps1.close();
                ps2.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<OthantileStaff> getStaffMemebers() {
        String sqls = "SELECT staffID, firstName, LastName,gender,address,placeOfBirth,dateOfBirth, emailAddress, roleName "
                + "     FROM"
                + "     onthantilestaff,`staffroles` "
                + "     WHERE "
                + "     onthantilestaff.staffID = OnthantileStaff_staffID";
        try {
            getResultSet(sqls);
            List<OthantileStaff> list = getOthantileStaffRecord();
            if (list.size() > 0) {
                staff = list;
            } else {

            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
                resultset.close();
                statement.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return staff;
    }

    public OthantileStaff getAStaffMember(int id) throws ClassNotFoundException {
        OthantileStaff oStaff = null;
        try {
            
            String sql = "SELECT staffID, firstName, LastName,gender,address,placeOfBirth,dateOfBirth, emailAddress, roleName "
                    + "     FROM"
                    + "     onthantilestaff,`staffroles` "
                    + "     WHERE "
                    + "     onthantilestaff.staffID = OnthantileStaff_staffID AND onthantilestaff.staffID =?  ";
            getResultSet(id, sql);
            List<OthantileStaff> list = getOthantileStaffRecord();
            if (list.size()>=1)
                oStaff = list.get(0);
        } catch (SQLException ex) {
            Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
                resultset.close();
            } catch (SQLException ex) {
                Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return oStaff;
    }

    public void updateStaffMember(OthantileStaff staff) throws ClassNotFoundException {
        connection = getConnection();
        String sql1 = "UPDATE onthantilestaff SET firstName=?, LastName=?,gender=?,address=?,placeOfBirth=?,dateOfBirth=?, emailAddress=? WHERE staffID =?";
        String sql2 = "UPDATE `staffroles`SET roleName=?, accessLevel=? WHERE OnthantileStaff_staffID =?";
        try {
            setOthantileStaffColumns(staff, sql1, true).execute();
            setOthantileRoleColumns(staff, sql2, staff.getStaffID()).execute();
            MenuView view = new MenuView ();
            view.addMessage("Update complete", null);
        } catch (SQLException ex) {
            Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deleteStaff(int id) throws ClassNotFoundException {
        connection = getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        String sql1 = " DELETE FROM  `staffroles` where  OnthantileStaff_staffID=?";
        String sql2 = " DELETE FROM onthantilestaff where staffID=?";
        try {
            ps1 = connection.prepareStatement(sql1);
            ps1.setInt(1, id);
            ps1.execute();
            feedback.addMessage("Record has been deleted",null);
            ps2 = connection.prepareStatement(sql2);
            ps2.setInt(1, id);
            ps2.execute();
            
             feedback.addMessage("Role has been detacted",null);
        } catch (SQLException ex) {
            Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
                ps1.close();
                ps2.close();
            } catch (SQLException ex) {

            }
        }

    }

    public PreparedStatement setOthantileStaffColumns(OthantileStaff staff, String query, boolean update) throws ClassNotFoundException, SQLException {
        connection = getConnection();
        PreparedStatement ps = null;
        ResultSet keyset = null;
        String sql = query;
        ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        Date date = new Date();
        date = staff.getDateOfBirth();
        java.util.Date utilStartDate = date;
        java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
        ps = connection.prepareStatement(sql);
        ps.setString(1, staff.getFirstname());
        ps.setString(2, staff.getLastname());
        String g = Character.toString(staff.getGender());
        ps.setString(3, g);
        ps.setString(4, staff.getAddress());
        ps.setString(5, staff.getPlaceOfBirth());
        ps.setDate(6, sqlStartDate);
        ps.setString(7, staff.getEmailAddress());
        if (update) {
            ps.setInt(8, staff.getStaffID());
        }
        return ps;
    }
    public PreparedStatement setOthantileRoleColumns(OthantileStaff staff, String query, int roleID) throws ClassNotFoundException, SQLException {
        //String sql2 = "INSERT INTO .`roles` (`roleName`,`accessLevel`,`OnthantileStaff_staffID`) VALUES (?,?,?)";
        PreparedStatement ps = null;
        ps = connection.prepareStatement(query);
        ps.setString(1, staff.getRoleName());
        ps.setInt(2, staff.getAccessLevel());
        ps.setInt(3, roleID);
        return ps;
    }

    public void getResultSet(String query) throws ClassNotFoundException, SQLException {
        connection = getConnection();
        statement = connection.createStatement();
        resultset = statement.executeQuery(query);
    }

    public void getResultSet(int id, String query) throws ClassNotFoundException, SQLException {
        connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        resultset = ps.executeQuery();
    }

    public List<OthantileStaff> getOthantileStaffRecord() throws SQLException {
        List<OthantileStaff> members = new ArrayList<>();
        while (resultset.next()) {
            int user_id = resultset.getInt("staffID");///
            String firstname = resultset.getString("firstName");
            String lastname = resultset.getString("LastName");
            String gender = resultset.getString("gender");
            String address = resultset.getString("address");
            String placeOfBirth = resultset.getString("placeOfBirth");
            Date date = new Date();
            date = resultset.getDate("dateOfBirth");
            String email = resultset.getString("emailAddress");
            char gnd = gender.charAt(0);
            OthantileStaff st = new OthantileStaff(user_id, firstname, lastname, gnd, address, placeOfBirth, date, email);
            st.setRoleName(resultset.getString("roleName"));
            members.add(st);
            //(`firstName`,`LastName`,`gender`,`address`,`placeOfBirth`,`dateOfBirth`,`emailAddress`
        }
        return members;
    }

    @Override
    boolean recordValidator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    // task asignmnet 
    public void getTaskes (){
        
    }
    
    
   
   public void getShifts (){
   }
    
    //TO Do authenticatio algorithm 
}
