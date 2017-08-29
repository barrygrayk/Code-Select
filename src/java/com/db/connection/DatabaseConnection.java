package com.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Barry Gray Kapelembe
 */
public abstract class DatabaseConnection {

    private final String conString;
    private final String dbUserName;
    private final String dbPassWord;
    protected Connection connection = null;
    protected Statement statement = null;
    protected ResultSet resultset = null;

    public DatabaseConnection() {
       this.dbUserName = "root";
        this.dbPassWord = "password";
        conString = "jdbc:mysql://localhost:3306/onthatile children's ministries";
//jdbc:mysql://node157038-onthatileapp.j.layershift.co.uk
     /*   this.dbUserName = "codeselect";
        this.dbPassWord = "9RstG7oenS";
        conString = "jdbc:mysql://node157038-onthatileapp.j.layershift.co.uk/onthatilechildrensministries"*/
        //jdbc:mysql://node156446-onthatilewebapplication.j.layershift.co.uk/onthatilechildrensministries
        // "https://node156430-codeselect.j.layershift.co.uk/db_structure.php?server=1&db=onthatile+children%27s+ministries";
        //String URL = "jdbc:mysql://mysql{node_id}-{environment_name}.{hoster_domain}/{dbname}"
        //jdbc:mysql://localhost:3306/?user=root
        //jdbc:mysql://127.0.0.1:3306/?user=root
        //root@127.0.0.1:3306
    }

    public Connection getConnection() throws ClassNotFoundException {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(conString, dbUserName, dbPassWord);
            con.setAutoCommit(true);
        } catch (SQLException | ClassNotFoundException ex) {
        }
        return con;
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

    public String getFullname(int id, String query) {
        String fullnameQuery = query;
        String fullname = "";
        try {
            getResultSet(id, fullnameQuery);
            if (resultset.next()) {
                fullname = resultset.getString("firstName") + " " + resultset.getString("LastName");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fullname;
    }
    
      public java.sql.Date toSqlDate(Date util) {
        Date date = util;
        java.util.Date utilStartDate = date;
        java.sql.Date sqlDate = new java.sql.Date(utilStartDate.getTime());
        return sqlDate;
    }

    public java.sql.Timestamp toSqlDateTime(Date util) {
        Date date = util;
        java.util.Date utilStartDate = date;
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilStartDate.getTime());
        return sqlDate;
    }

    abstract boolean recordValidator();

}
