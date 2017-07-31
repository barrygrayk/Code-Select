package com.db.connection;
import com.child.Model.Child;
import com.staff.Model.OthantileStaff;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class DBConnection {

    private String conString;
    //private List <OthantileStaff> users = new ArrayList<OthantilieStaff>();
    private List<OthantileStaff> staff = new ArrayList<>();
    private List<Child> child = new ArrayList<>();
    private final String dbUserName;
    private final String dbPassWord;
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    public DBConnection() {
        this.dbUserName = "root";
        this.dbPassWord = "plgwNen8Or";
        conString = "httpnode156430-codeselect.j.layershift.co.uk /onthatile children's ministries";
    }
    public Connection getConnection() throws ClassNotFoundException {
        System.out.println("yessssssss");
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "admin", "06Gray9595");
            con = (Connection) DriverManager.getConnection(conString, dbUserName, dbPassWord);
            con.setAutoCommit(true);
        } catch (SQLException | ClassNotFoundException ex) {
        }
        return con;
    }
    public List<OthantileStaff> getUsers() {
        try {
            conn = getConnection();
            stmt = (Statement) conn.createStatement();
            String sql = "select * from onthantilestaff";
            rs = stmt.executeQuery(sql);
            //staffID, firstName, LastName `onthatile children's ministries`.`onthantilestaff`
            while (rs.next()) {
                //
                //
                int user_id = rs.getInt("staffID");///
                String firstname = rs.getString("firstName");
                String lastname = rs.getString("LastName");
                String gender = rs.getString("gender");

                String address = rs.getString("address");
                String placeOfBirth = rs.getString("placeOfBirth");
                Date date = new Date();
                date = rs.getDate("dateOfBirth");
                String email = rs.getString("emailAddress");
                char gnd = gender.charAt(0);
                // you can use column index or columnname
                staff.add(new OthantileStaff(user_id, firstname, lastname, gnd, address, placeOfBirth, date, email));
                //(`firstName`,`LastName`,`gender`,`address`,`placeOfBirth`,`dateOfBirth`,`emailAddress`
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
                stmt.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return staff;
    }
    public void addUser(OthantileStaff staff) throws ClassNotFoundException {
        System.out.println("yessssssss");
        Connection connn = getConnection();
        PreparedStatement ps = null;
        /*INSERT INTO .`onthantilestaff`(`staffID`,`firstName`,`LastName`,`gender`,`address`,`placeOfBirth`,`dateOfBirth`,`emailAddress`) VALUES ();
         */ String sql = "INSERT INTO .`onthantilestaff`(`firstName`,`LastName`,`gender`,`address`,`placeOfBirth`,`dateOfBirth`,`emailAddress`) VALUES(?,?,?,?,?,?,?)";
        //String sql = "insert into onthantilestaff(firstName,LastName)values(?,?)";
        System.out.printf(staff.getFirstname(), staff.getLastname());//staffID, firstName, LastName `onthatile children's ministries`.`onthantilestaff`
        try {
            Date date = new Date();
            date = staff.getDateOfBirth();
            java.util.Date utilStartDate = date;
            java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
            ps = connn.prepareStatement(sql);
            ps.setString(1, staff.getFirstname());
            ps.setString(2, staff.getLastname());
            String g = Character.toString(staff.getGender());
            ps.setString(3, g);
            ps.setString(4, staff.getAddress());
            ps.setString(5, staff.getPlaceOfBirth());
            ps.setDate(6, sqlStartDate);
            ps.setString(7, staff.getEmailAddress());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
               connn.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public OthantileStaff getUser(int id) {
        OthantileStaff user = null;
        try {
            conn = getConnection();
            PreparedStatement ps = null;
            ResultSet rs;
            //stmt = (Statement) conn.createStatement();
            String sql = "select * from onthantilestaff where staffID=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            //staffID, firstName, LastName `onthatile children's ministries`.`onthantilestaff`
            if (rs.next()) {
                //user = new OthantileStaff(rs.getInt("staffID"), rs.getString("firstName"), rs.getString("emailLastName "));
            } else {
                return null;
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
                stmt.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return user;
    }
    public List<Child> getChildren() {
        try {
            conn = getConnection();
            stmt = (Statement) conn.createStatement();
            String sql = "select * from babyprofile";
            //`babyprofile`
            rs = stmt.executeQuery(sql);
            //idBabyProfile, firstName, lastName, distinguishingMarks
            while (rs.next()) {
                int user_id = rs.getInt("idBabyProfile");
                String firstname = rs.getString("firstName");
                String lastname = rs.getString("lastName");
                String marks = rs.getString("distinguishingMarks");
                // you can use column index or columnname
                child.add(new Child(user_id, firstname, lastname, marks));
                //(`firstName`,`LastName`,`gender`,`address`,`placeOfBirth`,`dateOfBirth`,`emailAddress`
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
                stmt.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return child;
    }

}
