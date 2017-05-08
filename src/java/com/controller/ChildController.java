package com.controller;

import com.appstuff.models.DBConnection;
import com.child.Model.Child;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author efsan1
 */
@ManagedBean (name="ChildCont", eager =true)
@SessionScoped 

public class ChildController extends Child implements Serializable{
    
    private ArrayList <Child> listOfChildren;
    private ArrayList <Child> selectedChildren;
    private Child child;
    private PreparedStatement ps;
    private Statement stmt;
    private ResultSet rs;
    private String sql;
    private Connection connection;
    

    public ChildController() throws SQLException, ClassNotFoundException {
        connection = new DBConnection().getConnection();
        listOfChildren= new ArrayList();
        selectedChildren = new ArrayList();
        child = null;
        ps = null;
        rs =null;
        sql = null;
    }

    //reading children details from DB
    public ArrayList <Child> getlistOfChildren () throws ClassNotFoundException, SQLException {
        
        stmt = (Statement) connection.createStatement();
        sql = "SELECT * FROM babyprofile";
        rs = stmt.executeQuery(sql);
        listOfChildren.clear();
        
        while (rs.next()){
            int id = rs.getInt("idBabyProfile");
            String fName = rs.getString("firstName");
            String lName = rs.getString("lastName");
            String marks = rs.getString("distinguishingMarks");
            listOfChildren.add(new Child(id,fName,lName,marks));
        }
        return listOfChildren;
    } 
    public void setListOfChildren(ArrayList<Child> listOfChildren) {
        this.listOfChildren = listOfChildren;
    }
    
    // Adding children details to the DB
    public void setChild() throws SQLException, ClassNotFoundException{
        
        stmt = (Statement) connection.createStatement();
        connection = new DBConnection().getConnection();
       
        sql = "INSERT INTO .`babyprofile`(`firstName`,`lastName`,`distinguishingMarks`) VALUES(?,?,?)";
        ps = connection.prepareStatement(sql);  
        //ps.setInt(1,9);
        ps.setString(1,this.getFirstname());
        ps.setString(2,this.getLastname());
        ps.setString(3,this.getDestingushingMarks());
        ps.execute();
       
       //return (ps.executeUpdate() >1 ) ; // for future validation reasons
    }
    
    public Child getChild() {
        return child;
    }
    public void setChild(Child child) {
        this.child = child;
    }
    
    // Deleting a child record from the DB
    public ArrayList<Child> getSelectedChildren() {
        return this.selectedChildren;
    }
    
    public void setSelectedChildren(ArrayList<Child> child) {
       this.selectedChildren = child;
    }
    
    public void deleteChild() throws SQLException{
        int id = 0;
        for (Child current: getSelectedChildren()){
           id = current.getBabyProfileid();
           sql = "DELETE FROM babyprofile WHERE idBabyProfile="+id;
           ps=connection.prepareStatement(sql);
           ps.execute();
        }
    } 
    
    // dataTable, event handler methods
    public void onRowSelection(SelectEvent e){
        child = (Child) e.getObject();
        this.selectedChildren.add(child);
        System.out.printf("List size: "+selectedChildren.size());
    }
    
    public void onRowUnselection(UnselectEvent e){
        child = (Child) e.getObject();
        for (Child current: selectedChildren){
            if(current.getBabyProfileid() == child.getBabyProfileid())
                this.selectedChildren.remove(current);
        }
       // this.selectedChildren.remove(child);
    }
    
    // updating a record from the DB
    public void updateChild() throws SQLException {
        
        int selectedChildID = this.child.getBabyProfileid();
        sql = "UPDATE babyprofile SET firstName=?, lastName=?, distinguishingMarks=? WHERE idBabyProfile ="+selectedChildID;
        
        ps = connection.prepareStatement(sql);
        ps.setString(1,this.child.getFirstname());
        ps.setString(2, this.child.getLastname());
        ps.setString(3,this.child.getDestingushingMarks());
        ps.executeUpdate();    
    }
    
    // dataTable event handlers for updating 
    public void onRowSelect(SelectEvent e){
        this.child = (Child) e.getObject();
    }
    
    public void onRowUnselect(UnselectEvent e){
        //this.child = (Child) e.getObject();
        child = null;
    }
   
}
