package com.controller;

import com.MenuView.MenuView;
import com.appstuff.models.DBConnection;
import com.child.Model.Child;
import com.child.Model.ChildMedicalHistory;
import com.child.Model.DailyActivities;
import com.child.Model.Height;
import com.child.Model.IntakeInfo;
import com.child.Model.Meals;
import com.child.Model.Nappies;
import com.child.Model.ParentInfo;
import com.child.Model.RequiredDocumentation;
import com.child.Model.SleepingRoutine;
import com.child.Model.SocialWorker;
import com.child.Model.Weight;
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
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import com.validation.MrKaplan;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

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
    private Statement stmt, stmt2, stmt3;
    private ResultSet rs, rs2, rs3;
    private String sql, sql2, sql3;
    private Connection connection, connection2, connection3;
    private UploadedFile file;
    private LineChartModel HeightChart;
    
    private String slectedChild;
    private ArrayList<String> listOfChildrenDropbox = new ArrayList<String>();

    public String getSlectedChild() {
        System.out.println("____Get___"+ slectedChild);

        return slectedChild;
    }

    public void setSlectedChild(String slectedChild) {
        this.slectedChild = slectedChild;
                System.out.println("___set____"+ this.slectedChild);

    }

    public ArrayList<String> getListOfChildrenDropbox() {
        return listOfChildrenDropbox;
    }

    public void setListOfChildrenDropbox(ArrayList<String> listOfChildrenDropbox) {
        this.listOfChildrenDropbox = listOfChildrenDropbox;
    }
    
    
    @PostConstruct 
    public void init(){
        List <Child> childList = new ArrayList <> ();
        try {
        childList = this.getlistOfChildren();
        
        for(Child child: childList){
            listOfChildrenDropbox.add(child.getFirstname() +" " +  child.getLastname() );
                  
        }
       
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
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
        connection2 = new DBConnection().getConnection();
        connection3 = new DBConnection().getConnection();
        listOfChildren= new ArrayList();
        selectedChildren = new ArrayList();
        child = null;
        ps = null;
        rs =null;
        rs2 = null;
        rs3 = null;
        sql = null;
        sql2 = null;
        sql3 = null;
        createLineChart();
    }
    
    //reading children details from DB
    public ArrayList <Child> getlistOfChildren () throws ClassNotFoundException, SQLException {
        
        stmt = (Statement) connection.createStatement();
        sql = "SELECT babyprofile.*, babyintake.*, socialworker.*, parentinfo.* FROM babyprofile"
                + " INNER JOIN babyintake ON babyprofile.idBabyProfile = babyintake.babyprofile_idBabyProfile"
                + " INNER JOIN socialworker ON babyprofile.idBabyProfile = socialworker.babyprofile_idBabyProfile"
                + " INNER JOIN parentinfo ON babyprofile.idBabyProfile = parentinfo.babyprofile_idBabyProfile";
    
        rs = stmt.executeQuery(sql);
        listOfChildren.clear();
        
        while(rs.next()){
            
            int id = rs.getInt("idBabyProfile"); System.out.println("ID now:"+id);
            String fName = rs.getString("firstName");
            String lName = rs.getString("lastName");
            String distinguishMarks = rs.getString("distinguishingMarks");
            Date dateOfBirth = rs.getDate("DOB");
            char gender = 'f';
            if(rs.getString("gender").equals("Male"))
                gender = 'm';
            String placeOfBirth = rs.getString("placeOfBirth");
            String generalRemarks = rs.getString("generalRemarks");
            Double headCircumference = rs.getDouble("headCir");
            
            // Required Docs
            ArrayList docsRequired = new ArrayList<>();            
            if(rs.getInt("clinicCard")==1)
                docsRequired.add("clinicCard");     
            if(rs.getInt("form36")==1)
                docsRequired.add("form36");
            if(rs.getInt("birthCertificate")==1)
                docsRequired.add("birthCertificate");
            if(rs.getInt("medicalReport")==1)
                docsRequired.add("medicalReport");
            String docsNeededNote = rs.getString("requiredDocsComment");
            
            //Medical History 
            ArrayList childArrivalCondtion = new ArrayList<>();  
            if(rs.getInt("abuse")==1)
                childArrivalCondtion.add("abuse");     
            if(rs.getInt("neglect")==1)
                childArrivalCondtion.add("neglect");
            if(rs.getInt("others")==1)
                childArrivalCondtion.add("others");
            String medicalReport = rs.getString("medicalReportComments");
            
            // now intake
            IntakeInfo intakeInfo = new IntakeInfo(rs.getString("droppedBy"), rs.getString("FetchLocation"),
                    rs.getString("FetchedBy"),rs.getLong("PhoneNumber"), rs.getDate("Date/Time"));
            
            // now socialWorker
            SocialWorker socialWorker = new SocialWorker(rs.getString("socialworkerName"),rs.getLong("PhoneNumber"),
            rs.getString("organization"),rs.getString("emailAddress"), rs.getString("additionalInfo"));
            
            
            // now parentInfo
            ParentInfo parentInfo = new ParentInfo(rs.getString("fathername"), rs.getString("mothername"),
                                    rs.getString("fatherID"),rs.getString("motherID"),rs.getLong("contactNumber"),
                                   rs.getString("physicalAddress"));
            
            //weight 
            Weight weight = new Weight(getListOfRecordedWeigth(id).getWeight(),
                            getListOfRecordedWeigth(id).getDateRecorded());
            
            //Height
            Height height = new Height(getListOfRecordedHeigth(id).getHeight(),
                    getListOfRecordedHeigth(id).getDateRecorded());
            
            
            
            listOfChildren.add(new Child(id,fName,lName,gender,dateOfBirth, placeOfBirth,distinguishMarks,docsRequired,
                    childArrivalCondtion, docsNeededNote, medicalReport, intakeInfo,socialWorker,parentInfo,
                    generalRemarks, weight, height, headCircumference, getListOfMedicalHystoryDb(id)));
             
        }
        return listOfChildren;
    }
    
    // select weights recorded for a specific child
    public Weight getListOfRecordedWeigth(int id) throws SQLException{
        stmt2 = (Statement) connection2.createStatement();
        sql2="SELECT * FROM babyweight WHERE BabyProfile_idBabyProfile = "+id;
        rs2 = stmt2.executeQuery(sql2);
        
        ArrayList<Weight> listOfWeightRecorded= new ArrayList<Weight>();
        while(rs2.next()){
            if(rs2.getInt("BabyProfile_idBabyProfile") == id){
                listOfWeightRecorded.add(new Weight(rs2.getDouble("weight"),rs2.getDate("date")));
            }
        }
        return (!listOfWeightRecorded.isEmpty()) ? listOfWeightRecorded.get(0): null;
    }
    
    // select heights recorded for a specific child
    public Height getListOfRecordedHeigth(int id) throws SQLException{
        stmt2 = (Statement) connection2.createStatement();
        sql2="SELECT * FROM babyheight WHERE BabyProfile_idBabyProfile = "+id;
        rs2 = stmt2.executeQuery(sql2);
        
        ArrayList<Height> listOfHeightRecords = new ArrayList<Height>();
        while(rs2.next()){
            if(rs2.getInt("BabyProfile_idBabyProfile") == id){
                listOfHeightRecords.add(new Height(rs2.getDouble("babyheight"),rs2.getDate("date")));
            }
        }

        return (!listOfHeightRecords.isEmpty()) ? listOfHeightRecords.get(0): null;
    }
    
    //reading list of child's medicalhistory from DB
    public ArrayList<ChildMedicalHistory> getListOfMedicalHystoryDb(int id) throws SQLException{
        sql2="SELECT * FROM babymedicalhistory WHERE BabyProfile_idBabyProfile = "+id;
        rs2 = stmt2.executeQuery(sql2);
        ArrayList<ChildMedicalHistory> medicalHistoryRecords = new ArrayList<ChildMedicalHistory>();
        while(rs2.next()){
            ChildMedicalHistory medicalHistory = new ChildMedicalHistory(rs2.getString("doctName"), rs2.getString("illnessesDetected"),
                    rs2.getString("reasonForIllnesses"), rs2.getString("medications"), rs2.getString("allergies"), 
                    rs2.getString("specialTreatments"), rs2.getString("hospitalVisited"), rs2.getDate("dateofVisit"));
            medicalHistoryRecords.add(medicalHistory);
        }
        return medicalHistoryRecords;
    }
    
    //add to temporary medicalHistory list
    public void addToMedicalHistoryList(){
        
        if(super.getMedicalHistory() != null)
            this.listOfMedicalHistoryRecords.add(new ChildMedicalHistory(super.getMedicalHistory().getDoctersName(),
                    super.getMedicalHistory().getIllnessDetected(), super.getMedicalHistory().getCause(),
                    super.getMedicalHistory().getMedicines(), super.getMedicalHistory().getAllergies(),
                    super.getMedicalHistory().getSpecialTreatmeants(), super.getMedicalHistory().getClincVisited(),
                    super.getMedicalHistory().getDateOfVisit()));
    }
    
    // reading staff -attendant from DB "needs to be rethought"
    public ArrayList<String> getCareTakerList() throws ClassNotFoundException, SQLException{
        
        ArrayList<String> currentStaffList = new ArrayList<String>();
        //sql = "SELECT firstName FROM othantilestaff WHERE staffID="+this.child.getStaffID();
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
    
    // validates data before adding 
    public void addChild() throws SQLException, ClassNotFoundException{
        MrKaplan x = new MrKaplan();
        MenuView messages = new MenuView();
        if( x.isValidInput(this.getFirstname()) && x.isValidInput(this.getLastname())){
            
            if(x.isValidInput(this.getDestingushingMarks()) && x.isValidInput(this.getPlaceOfBirth())){
                setChild();
                messages.save();
            }
            
        }
    }
    // Adding children details to the DB
    public void setChild() throws SQLException, ClassNotFoundException{
        
        connection = new DBConnection().getConnection();
        stmt = (Statement) connection.createStatement();
        
        sql = "INSERT INTO .`babyprofile`(`firstName`,`lastName`,`gender`,`DOB`,`placeOfBirth`,"
                + "`distinguishingMarks`,`form36`,`clinicCard`,`birthCertificate`,`medicalReport`,"
                + "`requiredDocsComment`,`medicalReportComments`,`abuse`,`neglect`,`others`,`generalRemarks`,`headCir`) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);  
     
        ps.setString(1,this.getFirstname());
        ps.setString(2,this.getLastname());
        ps.setString(3,Character.toString(this.gender));
        ps.setDate(4,dateConverter(super.getDateOfBirth()));
        ps.setString(5,this.getPlaceOfBirth());
        ps.setString(6,this.getDestingushingMarks());
        ps.setInt(7,quickCheck("form36",this.getChildRequiredDocs()));
        ps.setInt(8,quickCheck("clinicCard",this.getChildRequiredDocs()));
        ps.setInt(9,quickCheck("birthCertificate",this.getChildRequiredDocs()));
        ps.setInt(10,quickCheck("medicalReport",this.getChildRequiredDocs()));
        ps.setString(11,this.getRequiredDocNote());
        ps.setString(12,this.getChildArrivalMedicalReport());
        ps.setInt(13,quickCheck("abuse",this.getChildArrivalCondition()));
        ps.setInt(14,quickCheck("neglect",this.getChildArrivalCondition()));
        ps.setInt(15,quickCheck("others",this.getChildArrivalCondition()));
        ps.setString(16,this.getGeneralRemarks());
        ps.setDouble(17, this.getHeadCir());
        ps.execute();
        
        // Retrieving the PK to be FK in other tables
        int fk = 0;
        ResultSet keys = ps.getGeneratedKeys();
        if(keys.next()){
            fk = keys.getInt(1);
        }
        
        // to another table - parent information
        sql ="INSERT INTO .`parentinfo`(`fathername`,`mothername`,`fatherID`,`motherID`,`contactNumber`"
                + ",`physicalAddress`,`babyprofile_idBabyProfile`) "
                + "VALUES(?,?,?,?,?,?,"+fk+")";
        ps = connection.prepareStatement(sql); 
       
        ps.setString(1,this.getParentInfo().getFatherName());
        ps.setString(2,this.getParentInfo().getMotherName());
        ps.setString(3,this.getParentInfo().getFatherID());
        ps.setString(4, this.getParentInfo().getMotherID());
        ps.setInt(5, (int) this.getParentInfo().getContactNumber());
        ps.setString(6,this.getParentInfo().getPhysicalAddress());
        ps.execute();
        
        // to another table- social worker
        sql ="INSERT INTO .`socialworker`(`socialworkerName`,`organization`,`PhoneNumber`,`emailAddress`"
                + ",`additionalInfo`,`babyprofile_idBabyProfile`)"
                + "VALUES(?,?,?,?,?,"+fk+")";
        ps = connection.prepareStatement(sql);
     
        ps.setString(1,this.getSocialWorkerDetails().getSocialWorkerName());
        ps.setString(2,this.getSocialWorkerDetails().getOrganization());
        ps.setLong(3,this.getSocialWorkerDetails().getPhoneNumber());
        ps.setString(4,this.getSocialWorkerDetails().getEmail());
        ps.setString(5,this.getSocialWorkerDetails().getAdditionalInfo());
        ps.execute();
        
        // to another table- intake information
        sql ="INSERT INTO .`babyintake`(`droppedBy`,`FetchedBy`,`FetchLocation`,`PhoneNumber`"
                + ",`Date/Time`,`babyprofile_idBabyProfile`)"
                + "VALUES(?,?,?,?,?,"+fk+")";
        ps = connection.prepareStatement(sql);
                
        ps.setString(1,this.getIntakeDetails().getPersonWhoDropped());
        ps.setString(2,this.getIntakeDetails().getPersonWhoFetched());
        ps.setString(3,this.getIntakeDetails().getFetchLocation());
        ps.setLong(4,this.getIntakeDetails().getPhoneNumber());
        ps.setDate(5,dateConverter(this.getIntakeDetails().getDateAndTime()));
        ps.execute();
        
        // to another table- medicalHistory
        sql ="INSERT INTO .`babymedicalhistory`(`doctName`,`illnessesDetected`,`reasonForIllnesses`,`medications`"
                + ",`allergies`,`specialTreatments`,`dateofVisit`,`hospitalVisited`,`babyprofile_idBabyProfile`)"
                + "VALUES(?,?,?,?,?,?,?,?,"+fk+")";
        ps = connection.prepareStatement(sql);
        
        ps.setString(1,this.getMedicalHistory().getDoctersName());
        ps.setString(2,this.getMedicalHistory().getIllnessDetected());
        ps.setString(3,this.getMedicalHistory().getCause());
        ps.setString(4,this.getMedicalHistory().getMedicines());
        ps.setString(5,this.getMedicalHistory().getAllergies());
        ps.setString(6,this.getMedicalHistory().getSpecialTreatmeants());
        ps.setDate(7,dateConverter(this.getMedicalHistory().getDateOfVisit()));
        ps.setString(8,this.getMedicalHistory().getClincVisited());
        ps.execute();
         
        // to another table- babyheigth
        sql ="INSERT INTO .`babyheight`(`date`,`babyheight`,`babyprofile_idBabyProfile`)"
                + "VALUES(?,?,"+fk+")";
        ps = connection.prepareStatement(sql);
        
        Date currentDate = new Date();
        ps.setDate(1,dateConverter(currentDate));
        ps.setDouble(2,this.getHeightRecorded().getHeight());
        ps.execute();
        
        // to another table- babyWeight
        sql ="INSERT INTO .`babyweight`(`weight`,`date`,`babyprofile_idBabyProfile`)"
                + "VALUES(?,?,"+fk+")";
        ps = connection.prepareStatement(sql);
        
        ps.setDouble(1,this.getWeightRecorded().getWeight());
        ps.setDate(2,dateConverter(currentDate));        
        ps.execute();
        
    }
    // check selectedCheckBoxes for RequiredDocs
    public int quickCheck(String a, ArrayList<String> vector){
        
        for(String curr: vector)
            if (curr.equals(a)) 
                return 1;
        
        return 0;
    }
    // util date to sql date converter
    public java.sql.Date dateConverter(Date utilDate){
        
        java.util.Date convert = utilDate;
        java.sql.Date sqlDate = new  java.sql.Date(convert.getTime()); 
        
        return sqlDate;
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
    
    //delete for single
    public void deleteSingleChild() throws SQLException{
        int id = 0;
        id = child.getBabyProfileid();
        sql = "DELETE FROM babyprofile WHERE idBabyProfile="+id;
        ps=connection.prepareStatement(sql);
        ps.execute();
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
        sql = "UPDATE babyprofile,babyintake SET firstName=?, lastName=?, distinguishingMarks=?, droppedBy=? WHERE idBabyProfile ="+selectedChildID;
        
        ps = connection.prepareStatement(sql);
        ps.setString(1,this.child.getFirstname());
        ps.setString(2, this.child.getLastname());
        ps.setString(3,this.child.getDestingushingMarks());
        ps.setString(4,this.child.getIntakeDetails().getPersonWhoDropped());
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
    
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
        super.setDateOfBirth(((Date)event.getObject()));
    }
    public void onDateTimeSelect(SelectEvent event){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date and Time Selected",
                format.format(event.getObject())));
        super.intakeDetails.setDateAndTime((Date)event.getObject());
        System.out.println("Test DAte:"+ (Date)event.getObject());
                
    }
    public void humorMe(){
        System.out.println("Safado");
    }
     
    public void onCellEdit(CellEditEvent event) {
        
        MrKaplan x = new MrKaplan();
        Object oldValue = event.getOldValue();
        Object selectedChildID = event.getRowKey();
        String columnName = event.getColumn().getColumnKey();
        
        System.out.println("velho"+oldValue);
        System.out.println("Novo"+selectedChildID);
        System.out.println("Coluna: "+ x.getColumId(columnName));
    }
        
    public void chooseCar() {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", false);
        RequestContext.getCurrentInstance().openDialog("testEdit", options, null);
    }
     
    public void onCarChosen(SelectEvent event) {
        Child car = (Child) event.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Child Selected", "Id:" + car.getBabyProfileid());
         
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    
}
