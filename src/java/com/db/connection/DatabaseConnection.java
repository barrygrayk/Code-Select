package com.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Barry Gray Kapelembe
 */
public  abstract class  DatabaseConnection {

    private String conString;
    private final String dbUserName;
    private final String dbPassWord;
    protected Connection connection = null;
    protected Statement statement = null;
    protected ResultSet resultset = null;

    public DatabaseConnection() {
        this.dbUserName = "root";
        this.dbPassWord = "password";
        conString = "jdbc:mysql://localhost:3306/onthatile children's ministries";
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
    
   abstract boolean recordValidator ();
    
    

}
