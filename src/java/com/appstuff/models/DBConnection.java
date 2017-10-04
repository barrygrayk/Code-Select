package com.appstuff.models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author efsan1
 */
public final class DBConnection{
    
    private Connection connection = null;
    private final String dbUserName;
    private final String dbPassword;
    private String connLink = null;

    
    // non-default constructor
    public DBConnection() throws SQLException {
        
        /*this.dbUserName = "admin";
        this.dbPassword = "Codeselect";
        connLink = "jdbc:mysql://node158047-testingone.j.layershift.co.uk/onthatilechildrensministries";*/
        //local host:
        this.dbUserName = "root";
        this.dbPassword = "password";
        connLink= "jdbc:mysql://localhost:3306/onthatile children's ministries";
    }
    
    // creating a connection to the DB
    public Connection getConnection() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(connLink,dbUserName, dbPassword);
            connection.setAutoCommit(true);
        } catch (SQLException | ClassNotFoundException ex) {
        }
        return this.connection;
    } 

}
