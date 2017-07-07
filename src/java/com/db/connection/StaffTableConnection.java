package com.db.connection;

import com.Email.IEmail;
import com.Email.SendEmail;
import com.MenuView.MenuView;
import com.staff.Model.Authenticate;
import com.staff.Model.Authentication;
import com.staff.Model.OthantileStaff;
import com.validation.AuthTokens;
import com.validation.JJWT;
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
    private final MenuView feedback = new MenuView();

    public StaffTableConnection() {
        super();
    }

    boolean recordValidator(String validate) {
        String toVaildate = validate;

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addStaffMemeber(OthantileStaff staff) throws ClassNotFoundException {
        PreparedStatement staffInsert = null;
        PreparedStatement roleInsert = null;
        PreparedStatement authInsert = null;
        String token = null;

        try {
            String staffInsertQuery = "INSERT INTO .`onthantilestaff`(`firstName`,`LastName`,`gender`,`address`,`placeOfBirth`,`dateOfBirth`,`emailAddress`) VALUES(?,?,?,?,?,?,?)";
            staffInsert = setOthantileStaffColumns(staff, staffInsertQuery, false);
            connection.setAutoCommit(false);
            int update = staffInsert.executeUpdate();
            int fKey = 0;
            if (update == 1) {
                fKey = 0;
                ResultSet key = staffInsert.getGeneratedKeys();
                if (key.next()) {
                    fKey = key.getInt(1);
                }
                String roleQuery = "INSERT INTO .`staffroles` (`roleName`,`accessLevel`,`OnthantileStaff_staffID`) VALUES (?,?,?)";
                roleInsert = setOthantileRoleColumns(staff, roleQuery, fKey);
                roleInsert.execute();
                String authQuery = "INSERT INTO .`stafflogins` (`passwordSalt`,`passwordHash`,`userName`,`OnthantileStaff_staffID`,`status`,`token`) VALUES (?,?,?,?,?,?)";
                AuthTokens jwt = new JJWT();
                String id = fKey + "";
                System.out.println("_____" + staff.getAuthcateDetails());
                token = jwt.creatJWt(id, "OthantileWebApplication", staff.getAuthcateDetails().getUsername(), 0L);
                authInsert = setStaffAuthenticationColumns(staff, authQuery, fKey, token);
                authInsert.execute();
            }
            connection.commit();
            String id = fKey + "";
            String body = "Dear " + staff.getFirstname() + ", " + staff.getLastname() + "\n" + "\nWelcome to othantile Childrens ministries. "
                    + "Your account has been"
                    + " sucessfully created. Your account details are as follows:\n" + "\n"
                    + "User name: " + staff.getAuthcateDetails().getUsername() + "\n" + "Role: " + staff.getRoleName()
                    + "\nFollow the link below to activate your accout.\n"
                    + "\nhttp://localhost:8080/OnthatileWebApplication/faces/" + token + "/" + "register.xhtml";
            sendEmailToStaff(staff.getEmailAddress(), "Othantile Staff Account", body);
            feedback.addMessage("Sucess", staff.getFirstname() + "'s bio has been added");
        } catch (SQLException ex) {
            Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            feedback.error("Insert error", ex.getMessage());
        } finally {
            try {
                if (staffInsert != null) {
                    staffInsert.close();
                }
                if (roleInsert != null) {
                    roleInsert.close();
                }
                if (authInsert != null) {
                    authInsert.close();
                }
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
                feedback.error("Connection error", ex.getMessage());
            }
        }
    }

    public List<OthantileStaff> getStaffMemebers() {
        String staffQuery = "SELECT \n"
                + "    staff.*, roles.*, logins.*\n"
                + "FROM\n"
                + "    onthantilestaff staff\n"
                + "        INNER JOIN\n"
                + "    staffroles roles ON staff.staffID = roles.OnthantileStaff_staffID\n"
                + "        LEFT JOIN\n"
                + "    stafflogins logins ON staff.staffID = logins.OnthantileStaff_staffID\n"
                + "WHERE\n"
                + "    staff.staffID = roles.OnthantileStaff_staffID AND staff.staffID = logins.OnthantileStaff_staffID   ";
        try {
            getResultSet(staffQuery);
            List<OthantileStaff> list = getOthantileStaffRecord();
            if (list.size() > 0) {
                staff = list;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            feedback.error("Read error", ex.getMessage());
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
            String staffselectQuery = "SELECT \n"
                    + "    staff.*, roles.*, logins.*\n"
                    + "FROM\n"
                    + "    onthantilestaff staff\n"
                    + "        INNER JOIN\n"
                    + "    staffroles roles ON staff.staffID = roles.OnthantileStaff_staffID\n"
                    + "        LEFT JOIN\n"
                    + "    stafflogins logins ON staff.staffID = logins.OnthantileStaff_staffID\n"
                    + "WHERE\n"
                    + "    staff.staffID = ?";
            getResultSet(id, staffselectQuery);
            List<OthantileStaff> list = getOthantileStaffRecord();
            if (list.size() >= 1) {
                oStaff = list.get(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            feedback.error("Read error", ex.getMessage());
        } finally {
            try {
                connection.close();
                resultset.close();
            } catch (SQLException ex) {
                Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
                feedback.error("Read error", ex.getMessage());
            }
        }
        return oStaff;
    }

    public void updateStaffMember(OthantileStaff staff) throws ClassNotFoundException {
        connection = getConnection();
        String updateStaffQuery = "UPDATE onthantilestaff SET firstName=?, LastName=?,gender=?,address=?,placeOfBirth=?,dateOfBirth=?, emailAddress=? WHERE staffID =?";
        String updateRoles = "UPDATE `staffroles`SET roleName=?, accessLevel=? WHERE OnthantileStaff_staffID =?";
        try {
            setOthantileStaffColumns(staff, updateStaffQuery, true).execute();
            setOthantileRoleColumns(staff, updateRoles, staff.getStaffID()).execute();
            feedback.addMessage("Success", staff.getFirstname() + "'s bio has been updated");
        } catch (SQLException ex) {
            Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            feedback.error("Update error", ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
                feedback.error("Connection error", ex.getMessage());
            }
        }
    }

    public void deleteStaff(int id) throws ClassNotFoundException {
        connection = getConnection();
        PreparedStatement deleteRecord = null;
        String sql1 = " DELETE FROM  `onthantilestaff` where  staffID=?";
        try {
            deleteRecord = connection.prepareStatement(sql1);
            deleteRecord.setInt(1, id);
            deleteRecord.execute();
            feedback.addMessage("Sucess", "Record has been deleted");
        } catch (SQLException ex) {
            Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            feedback.error("Delete error", ex.getMessage());
        } finally {
            try {
                connection.close();
                deleteRecord.close();
            } catch (SQLException ex) {
                feedback.error("Connection error", ex.getMessage());
            }
        }

    }

    public PreparedStatement setOthantileStaffColumns(OthantileStaff staff, String query, boolean update) throws ClassNotFoundException, SQLException {
        connection = getConnection();
        PreparedStatement ps = null;
        ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        Date date = new Date();
        date = staff.getDateOfBirth();
        java.util.Date utilStartDate = date;
        java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
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

    public PreparedStatement setOthantileRoleColumns(OthantileStaff staff, String query, int fKey) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        ps = connection.prepareStatement(query);
        ps.setString(1, staff.getRoleName());
        ps.setInt(2, staff.getAccessLevel());
        ps.setInt(3, fKey);
        return ps;
    }

    public PreparedStatement setStaffAuthenticationColumns(OthantileStaff staff, String query, int fkey, String token) throws SQLException {
        PreparedStatement ps = null;
        ps = connection.prepareStatement(query);
        ps.setBytes(1, staff.getAuthcateDetails().getSalt());
        ps.setBytes(2, staff.getAuthcateDetails().getHashedPassword());
        ps.setString(3, staff.getAuthcateDetails().getUsername());
        ps.setInt(4, fkey);
        ps.setString(5, staff.getAuthcateDetails().getStatus());
        ps.setString(6, token);
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
            int user_id = resultset.getInt("staffID");
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
            Authenticate auth = new Authentication();
            auth.setUsername(resultset.getString("userName"));
            auth.setSalt(resultset.getBytes("passwordSalt"));
            auth.sethashPassword(resultset.getBytes("passwordHash"));
            auth.setStatus(getAccountStatus(resultset.getString("status")));
            st.setAuthcateDetails((Authentication) auth);
            members.add(st);
            //    String [] statusArray = {"Pending activaton","Pending reset","Active"};

            //      "INSERT INTO .`stafflogins` (`passwordSalt`,`passwordHash`,`userName`,`OnthantileStaff_staffID`,`status` ) VALUES (?,?,?,?,?)";
        }
        return members;
    }

    @Override
    boolean recordValidator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // task asignmnet 
    public void getTaskes() {

    }

    public void getShifts() {
    }

    public int getAccountStatus(String status) {
        //String [] statusArray = {"Pending activaton","Pending reset","Active"};
        int statusIndex = 4;
        switch (status) {
            case "Pending activaton":
                statusIndex = 0;
                break;
            case "Pending reset":
                statusIndex = 1;
                break;
            case "Active":
                statusIndex = 2;
                break;
        }
        return statusIndex;
    }

    public List<Authenticate> getAuthenticatedStaff() throws ClassNotFoundException, SQLException {
        List<Authenticate> authenticatedStaff = new ArrayList<>();
        String authQuery = "SELECT * FROM `stafflogins`";
        getResultSet(authQuery);
        while (resultset.next()) {
            Authenticate auth = new Authentication();
            auth.setAuthId(resultset.getInt("OnthantileStaff_staffID"));
            auth.setUsername(resultset.getString("userName"));
            auth.setSalt(resultset.getBytes("passwordSalt"));
            auth.sethashPassword(resultset.getBytes("passwordHash"));
            auth.setStatus(getAccountStatus(resultset.getString("status")));
            auth.setToken(resultset.getString("token"));
            authenticatedStaff.add(auth);
        }
        return authenticatedStaff;
    }

    public void updatePassword(Authenticate auth) throws ClassNotFoundException, SQLException {
        connection = getConnection();
        String updateAuthfQuery = "UPDATE stafflogins SET passwordSalt=?, passwordHash=?,status=? WHERE userName =?";
        PreparedStatement ps = null;
        ps = connection.prepareStatement(updateAuthfQuery);
        ps.setBytes(1, auth.getSalt());
        ps.setBytes(2, auth.getHashedPassword());
        ps.setString(3, auth.getStatus());
        ps.setString(4, auth.getUsername());
        ps.execute();
        feedback.addMessage(auth.getUsername(),"Account has been sucessfully activated" );

    }

    public String getFullname(int id) {
        String fullnameQuery = "SELECT `firstName`,`LastName` FROM `onthantilestaff`  WHERE staffID =?";
        String fullname = "";
        try {
            getResultSet(id, fullnameQuery);
            if (resultset.next()) {
                fullname = resultset.getString("firstName") + " " + resultset.getString("LastName");
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            feedback.error("Read error", ex.getMessage());
        }

        return fullname;
    }

    public void sendEmailToStaff(String sendTo, String emailSubject, String emailBody) {
        String USER_NAME = "barry.kapelembeg";  // GMail user name (just the part before "@gmail.com")
        String PASSWORD = "06Yarg9595"; // GMail password
        String RECIPIENT = sendTo;
        //https://stackoverflow.com/questions/46663/how-can-i-send-an-email-by-java-application-using-gmail-yahoo-or-hotmail/47452#47452
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = {RECIPIENT}; // list of recipient email addresses
        String subject = emailSubject;
        String body = emailBody;
        IEmail email = new SendEmail();
        email.sendFromGMail(from, pass, to, subject, body);
    }

    //TO Do authenticatio algorithm 
}
