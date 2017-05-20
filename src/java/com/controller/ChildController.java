package com.controller;

import com.appstuff.models.DBConnection;
import com.child.Model.Child;
import com.child.Model.DailyActivities;
import com.child.Model.Meals;
import com.child.Model.Nappies;
import com.child.Model.SleepingRoutine;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author efsan1
 */
@ManagedBean (name="ChildCont", eager =true)
@SessionScoped 

public class ChildController extends Child implements Serializable{
    
    private ArrayList <Child> listOfChildren;
    private ArrayList <Child> selectedChildren;
    private ArrayList<String> careTakerList;
    private Child child;
    private DailyActivities currentActivity;
    private PreparedStatement ps;
    private Statement stmt;
    private ResultSet rs;
    private String sql;
    private Connection connection;
    private UploadedFile file;
    private LineChartModel HeightChart;
    private Date calendar;
    private String gender1;
    private Date currentDate;
    
    
    public LineChartModel getHeightChart() {
        return HeightChart;
    }
     
    private void createLineChart() {
         
        HeightChart = initHeightModel();
        HeightChart.setTitle("Height Chart");
        HeightChart.setLegendPosition("e");
        HeightChart.setShowPointLabels(true);
        HeightChart.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        Axis yAxis = HeightChart.getAxis(AxisType.Y);
        yAxis.setLabel("Height(mm)");
        yAxis.setMin(0);
        yAxis.setMax(2);
    }
     
    private LineChartModel initHeightModel() {
        LineChartModel heightModel = new LineChartModel();
 
        ChartSeries heightDevelopment = new ChartSeries();
        heightDevelopment.setLabel("Height");
        heightDevelopment.set("2017", 0.3);
        heightDevelopment.set("2018", 1.2);
        heightDevelopment.set("2019", 1.7);
        heightDevelopment.set("2020", 1.9);
        heightDevelopment.set("2021", 2);
 
        heightModel.addSeries(heightDevelopment);
         
        return heightModel;
    }

    public ChildController() throws SQLException, ClassNotFoundException {
        connection = new DBConnection().getConnection();
        listOfChildren= new ArrayList();
        selectedChildren = new ArrayList();
        child = null;
        ps = null;
        rs =null;
        sql = null;
        gender1 = "Male";
        createLineChart();
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
            Date dateOfBirth = rs.getDate("DOB");
            char gender = 'f';
            if(rs.getString("gender").equals("male"))
                gender = 'm';
            String placeOfBirth = rs.getString("placeOfBirth");
            int staffID = rs.getInt("stafflogins_loginID");
            
            listOfChildren.add(new Child(id,fName,lName,gender, placeOfBirth, dateOfBirth,marks,staffID));
        }
        return listOfChildren;
    }
    
    // reading staff -attendant from DB "needs to be rethought"
    public ArrayList<String> getCareTakerList() throws ClassNotFoundException, SQLException{
        
        ArrayList<String> currentStaffList = new ArrayList<String>();
        sql = "SELECT firstName FROM othantilestaff WHERE staffID="+this.child.getStaffID();
        rs = stmt.executeQuery(sql);
        while(rs.next()){
            currentStaffList.add(rs.getString("firstName"));
        }
        return currentStaffList;
    }
    // reading list of child's nappies from DB
    public ArrayList<Nappies> getListOfNappiesDb() throws ClassNotFoundException, SQLException{
        Date dateOfNappyChange = new Date();
        ArrayList<Nappies> currentNappiesRecord = new ArrayList<Nappies>();
        
        sql = "SELECT idbabynappys, nappyWet, nappyDirty, nappyChangeTime FROM babynappys WHERE babynappys.idbabynappys ="+this.child.getBabyProfileid();
        rs = stmt.executeQuery(sql);
        
        while(rs.next()){
          Nappies nappyRecord = new Nappies();
          
          nappyRecord.setNappyId(rs.getInt("idbabynappys"));
          nappyRecord.setConditionDry(rs.getBoolean("NappyDirty"));
          nappyRecord.setConditionWet(rs.getBoolean("nappyWet"));
          nappyRecord.setNappyChangeTime(rs.getTime("nappyChangeTime"));
          currentNappiesRecord.add(nappyRecord);
        }
        this.child.setlistOfNappyRecords(currentNappiesRecord);
        // validation required
        return this.child.getlistOfNappyRecords();
    }
    
    // reading list of child's meals from DB
    public ArrayList<Meals> getListOfMealsHadDb()throws ClassNotFoundException, SQLException{
        
        Date dateOfMealIntake = new Date();
        ArrayList<Meals> currentMealsList = new ArrayList<Meals>();
       
        sql = "SELECT idBabyMeals, typeOfMeal, time, BabyMealsComment FROM babymeals WHERE babymeals.BabyProfile_idBabyProfile ="+this.child.getBabyProfileid();
        rs = stmt.executeQuery(sql);
        
        while(rs.next()){
            
            Meals meal = new Meals();
            int idMeal = rs.getInt("idBabyMeals");
            dateOfMealIntake= rs.getTime("time");
            
            meal.setMealID(idMeal);
            meal.setMealDescription(rs.getString("typeOfMeal"));
            meal.setCommentOnEating(rs.getString("BabyMealsComment"));
            meal.setMealIntakeTime(dateOfMealIntake);
            currentMealsList.add(meal);
        }
        this.child.setlistOfMealsHad(currentMealsList);
        this.child.verifyListOfMealsHad();
        return this.child.getListOfMealsHad();
    }
    
    // reading records of child's daily activities
    public ArrayList<DailyActivities> getListOfActivitiesDb()throws ClassNotFoundException, SQLException{
        
        sql = "SELECT idbabyactivity, date, time, activity, activityStatus, comment FROM babyactivities WHERE babyactivities.BabyProfile_idBabyProfile="+this.child.getBabyProfileid();
        rs = stmt.executeQuery(sql);
        
        ArrayList<DailyActivities> currentActivityList = new ArrayList<DailyActivities>();
       
        
        while(rs.next()){
            
            DailyActivities activity = new DailyActivities();
            activity.setActivityID(rs.getInt("idbabyactivity"));
            activity.setActivityOccuringTime(rs.getTime("time"));
            activity.setDateRecorded(rs.getDate("date"));
            activity.setTitle(rs.getString("activity"));
            activity.setComment(rs.getString("comment"));
            activity.setStatus(rs.getString("activityStatus"));
            
            currentActivityList.add(activity); 
        }
        this.child.setListOfActivitiesRecorded(currentActivityList);
        this.currentActivity = currentActivityList.get(0);
        return this.child.getListOfActivitiesRecorded();
    }
    
    // reading records of child's sleeping routine
    public ArrayList<SleepingRoutine> getListOfSleepingRoutineDb()throws ClassNotFoundException, SQLException{
        
        ArrayList<SleepingRoutine> currentListOfSleepRoutine = new ArrayList<SleepingRoutine>();
        
        sql = "SELECT idbabysleeptime, sleeptime, waketime, Date FROM babysleeptime WHERE babysleeptime.BabyProfile_idBabyProfile="+this.child.getBabyProfileid();
        rs = stmt.executeQuery(sql);
        while(rs.next()){
            SleepingRoutine currentRoutine = new SleepingRoutine();
            currentRoutine.setSleepingRoutineID(rs.getInt("idbabysleeptime"));
            currentRoutine.setDateRecorded(rs.getDate("Date"));
            currentRoutine.setSleepingTime(rs.getTime("sleeptime"));
            currentRoutine.setWakingTime(rs.getTime("waketime"));
            currentListOfSleepRoutine.add(currentRoutine); // can validate here before adding to list...
        }
        this.child.setListOfSleepingRecords(currentListOfSleepRoutine);
        return this.child.getListOfSleepingRecords();
    }
    
    public void setListOfChildren(ArrayList<Child> listOfChildren) {
        this.listOfChildren = listOfChildren;
    }
    
    // Adding children details to the DB
    public void setChild() throws SQLException, ClassNotFoundException{
        System.out.println("_____Insert Child________");
        connection = new DBConnection().getConnection();
        stmt = (Statement) connection.createStatement();
        
       
        sql = "INSERT INTO .`babyprofile`(`firstName`,`lastName`,`gender`,`DOB`,`placeOfBirth`,`distinguishingMarks`,`stafflogins_loginID`) VALUES(?,?,?,?,?,?,?)";
        ps = connection.prepareStatement(sql);  
        //ps.setInt(1,9);
        Date date = new Date();
        date= this.currentDate;
        java.util.Date convert = date;
        java.sql.Date sqlDate = new  java.sql.Date(convert.getTime()); 
        
        ps.setString(1,this.getFirstname());
        ps.setString(2,this.getLastname());
        ps.setString(3,this.getGender1());
        ps.setDate(4, sqlDate);
        ps.setString(5,this.getPlaceOfBirth());
        ps.setString(6,this.getDestingushingMarks());
        ps.setInt(7,1);
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
    
    //updating meals table
        /*public void updateChildMeals() throws SQLException {
        
        Date date = new Date();
        date= this.currentDate;
        java.util.Date convert = date;
        java.sql.Date sqlDate = new  java.sql.Date(convert.getTime());
        
        int selectedChildID = this.child.getBabyProfileid();
        sql = "UPDATE babymeals SET typeOfMeal=?, time=?, BabyMealsComment=? WHERE idBabyProfile ="+selectedChildID;
        
        ps = connection.prepareStatement(sql);
        ps.setString(1,this.child.getFirstname());
        ps.setString(2, this.child.getLastname());
        ps.setString(3,this.child.getDestingushingMarks());
        ps.executeUpdate();    
    }*/
    
    // dataTable event handlers for updating 
    public void onRowSelect(SelectEvent e){
        this.child = (Child) e.getObject();
    }
    
    public void onRowUnselect(UnselectEvent e){
        //this.child = (Child) e.getObject();
        child = null;
    }

    // uploading a thing
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
     
    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public DailyActivities getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(DailyActivities currentActivity) {
        this.currentActivity = currentActivity;
    }

    public Date getCalendar() {
        return calendar;
    }

    public void setCalendar(Date calendar) {
        this.calendar = calendar;
    }
    
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
        System.out.println("newbie:"+format.format(event.getObject()));
        this.currentDate = ((Date)event.getObject());
    }
    public void humorMe(){
        System.out.println("Safado");
    }

    public String getGender1() {
        return gender1;
    }

    public void setGender1(String gender1) {
        this.gender1 = gender1;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }
    
    
}
