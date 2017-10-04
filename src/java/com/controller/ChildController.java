package com.controller;

import com.MenuView.MenuView;
import com.appstuff.models.DBConnection;
import com.child.Model.Allergies;
import com.child.Model.Anomaly;
import com.child.Model.Appointment;
import com.child.Model.Child;
import com.child.Model.ChildMedicalHistory;
import com.child.Model.DailyActivities;
import com.child.Model.DetectedIllness;
import com.child.Model.Diagnostics;
import com.child.Model.Height;
import com.child.Model.IntakeInfo;
import com.child.Model.Meals;
import com.child.Model.MedicalEvidence;
import com.child.Model.MedicalExam;
import com.child.Model.Medication;
import com.child.Model.MedicationTaken;
import com.child.Model.Nappies;
import com.child.Model.ParentInfo;
import com.child.Model.SleepingRoutine;
import com.child.Model.SocialWorker;
import com.child.Model.Temperature;
import com.child.Model.TreatmentPlan;
import com.child.Model.VaccineList;
import com.child.Model.VaccineTaken;
import com.child.Model.Weight;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.record.Audit.CaregiverRecordAudit;
import com.reports.ChildReports;
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
import com.validation.MrKaplan;
import com.validation.TheEqualizer;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.servlet.http.Part;
//
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.UploadedFile;
import org.chartistjsf.model.chart.Axis;
import org.chartistjsf.model.chart.AxisType;
import org.chartistjsf.model.chart.BarChartModel;
import org.chartistjsf.model.chart.BarChartSeries;
import org.chartistjsf.model.chart.LineChartModel;
import org.chartistjsf.model.chart.LineChartSeries;
import org.chartistjsf.model.chart.AspectRatio;
import org.primefaces.event.ItemSelectEvent;

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
    private List<Anomaly> filteredAnomalies;
    private List<VaccineList> listOfVaccines;
    private VaccineList vaccine = new VaccineList();
    private Child child;
    
    private PreparedStatement ps;
    private Statement stmt, stmt2, stmt3;
    private ResultSet rs, rs2, rs3;
    private String sql, sql2, sql3, buttonValue="Administer", order ="insert", buttonSaveVal = "Add";
    private Connection connection, connection2, connection3;
    private DailyActivities activity;
    
    private LineChartModel heightChart, temperatureChart, temperatureChartM;
    private BarChartModel weightChart;
    
    private String selectedChild, selectedAppointment, selectedReportType;
    private boolean displayFields = false;
    private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);//new SimpleDateFormat("yyyy/mm/dd");
    private Date searchDate, reportStartDate, reportEndDate, today;
    private Part perfilImage;
    private UploadedFile file;
    private Document reportPdf = new Document();
    private boolean reportTab = false;
    private ArrayList<String> reportCriteria = new ArrayList<String>();
    private List<String> images;

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }
    
    public String getTodayDate(){
        return dateFormat.format(this.today);
    }
    
    public boolean verifyIfChildIsSelected(){
        boolean answer = false;
        if(this.child !=null){
            answer = true;
        }else{
            FacesMessage message = new FacesMessage("No Child Selected!","Please Select child to view profile");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return answer;
    }
    
    public List<String> getImages() {
        return images;
    }
    
    public ArrayList<String> getReportCriteria() {
        System.out.println(reportCriteria);
        return reportCriteria;
    }

    public void setReportCriteria(ArrayList<String> reportCriteria) {
        this.reportCriteria = reportCriteria;        
    }
    
    // reports
    public Document getReportPdf() {
        return reportPdf;
    }

    public void setReportPdf(Document reportPdf) {
        this.reportPdf = reportPdf;
    }

    public Date getReportStartDate() {
        return reportStartDate;
    }

    public void setReportStartDate(Date reportStartDate) {
        this.reportStartDate = reportStartDate;
    }

    public Date getReportEndDate() {
        return reportEndDate;
    }

    public void setReportEndDate(Date reportEndDate) {
        this.reportEndDate = reportEndDate;
    }

    public boolean isReportTab() {
        return reportTab;
    }

    public void setReportTab(boolean reportTab) {
        this.reportTab = reportTab;
    }

    public String getSelectedReportType() {
        return selectedReportType;
    }

    public void setSelectedReportType(String selectedReportType) {
        this.selectedReportType = selectedReportType;
    }
    
    public  void updateReportTypeCriteria(){
        if(selectedReportType.equals("fullReport")){
            reportCriteria .add("mealsReport");
            reportCriteria .add("activitiesReport");reportCriteria .add("anomlayReport");
            reportCriteria .add("nappyReport");reportCriteria .add("heightReport");
            reportCriteria .add("weightReport");reportCriteria .add("sleepReports");
            reportCriteria .add("temperatureReport");reportCriteria .add("initialMedicalReport");
            reportCriteria .add("vaccineSummary");reportCriteria .add("followedTreatment");reportCriteria .add("doctorVisits");           
        }else if(selectedReportType.equals("customReport")){
            for(int x=0; x<reportCriteria.size();x++){
                reportCriteria.set(x, "");
            }            
        }
    }
    
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        //if(tableToExport.equalsIgnoreCase("manageMedicalHTb")){
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        pdf.addTitle("Individual Report for: name of baby"); 
        
        // ** for the cover **
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "Logo.png";
        Image x  = Image.getInstance(logo);//
        x.setIndentationLeft((float) 30.00);
        pdf.add(x);
        
        String background = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "reportPic.png";
        Image y  = Image.getInstance(background);
        y.setIndentationLeft((float) -13.00);
        pdf.add(y);
       // Title 
        Font titleFont = new Font(Font.TIMES_ROMAN,28,Font.BOLD);
        Paragraph firstPara = new Paragraph();
        String currYear = ""+yearConverter(new Date()); 
        addEmptyLine(firstPara, 4);
        firstPara = new Paragraph("Individual Child Report\n"+currYear, titleFont);
        firstPara.setAlignment(Element.ALIGN_CENTER);
        
        addEmptyLine(firstPara, 3);
    
        Font nameFont = new Font(Font.TIMES_ROMAN,18,Font.BOLDITALIC);
        Paragraph childName = new Paragraph(""+this.child.getFirstname()+" "+this.child.getLastname()+"\nJohannesburg, South Africa", nameFont);
        childName.setAlignment(Element.ALIGN_CENTER);
    
        pdf.add(firstPara);
        pdf.add(childName);
        pdf.newPage();
        // above is just cover page! 
        
        Font tableTitleFont = new Font(Font.TIMES_ROMAN,18,Font.BOLD);
        Paragraph tableTitle = new Paragraph(); 
        
         ChildReports xr = new ChildReports();
            try {
                Font subHeadingFont = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
                for (String curr: reportCriteria){
                    switch(curr){
                        case "mealsReport" :tableTitle = new Paragraph("List of Meals Recorded",tableTitleFont);
                                            tableTitle.setAlignment(Element.ALIGN_CENTER);
                                            pdf.add(tableTitle);
                                            pdf.add(xr.getMealsTable(this.getListOfMealsHadDb()));
                            break;
                        case "activitiesReport" :pdf.newPage(); 
                                                tableTitle = new Paragraph("List of Activities Recorded",tableTitleFont);
                                                tableTitle.setAlignment(Element.ALIGN_CENTER);
                                                pdf.add(tableTitle);
                                                pdf.add(xr.getActivitiesTable(this.getListOfActivitiesDb()));
                            break;
                        case "anomlayReport" :pdf.newPage();
                                              tableTitle = new Paragraph("List of Anomalies Recorded",tableTitleFont);
                                              tableTitle.setAlignment(Element.ALIGN_CENTER);
                                              pdf.add(tableTitle);
                                              pdf.add(xr.getAnomalyTable(this.getListOfAllAnomalytRecords()));
                            break;
                        case "nappyReport" :pdf.newPage();
                                            tableTitle = new Paragraph("List of Nappies Changing Recorded",tableTitleFont);
                                            tableTitle.setAlignment(Element.ALIGN_CENTER);
                                            pdf.add(tableTitle);
                                            pdf.add(xr.getNappyTable(this.getListOfNappiesDb())) ;
                            break;
                        case "heightReport" :pdf.newPage();
                                             tableTitle = new Paragraph("Height Changes Recorded",tableTitleFont);
                                             tableTitle.setAlignment(Element.ALIGN_CENTER);
                                             pdf.add(tableTitle);
                                             pdf.add(xr.getHeightTable(this.getListOfAllHeightRecords())) ;
                            break;
                        case "weightReport" :pdf.newPage();
                                             tableTitle = new Paragraph("Weight Changes Recorded",tableTitleFont);
                                             tableTitle.setAlignment(Element.ALIGN_CENTER);
                                             pdf.add(tableTitle);
                                             pdf.add(xr.getWeightTable(this.getListOfAllWeightRecords())) ;
                            break;
                        case "sleepReports" :pdf.newPage();
                                             tableTitle = new Paragraph("Sleeping tracking Recorded",tableTitleFont);
                                             tableTitle.setAlignment(Element.ALIGN_CENTER);
                                             pdf.add(tableTitle);
                                             pdf.add(xr.getSleepingTable(this.getListOfSleepingRoutineDb())) ;
                            break;
                        case "temperatureReport" :pdf.newPage();
                                                 tableTitle = new Paragraph("Temperature Changes Recorded",tableTitleFont);
                                                 tableTitle.setAlignment(Element.ALIGN_CENTER);
                                                 pdf.add(tableTitle);
                                                 pdf.add(xr.getTemperatureTable(this.getListOfAllTemperatureRecords())) ;
                            break;
                        case "initialMedicalReport" :pdf.newPage();
                                                    tableTitle = new Paragraph("Initial Medical Report Recorded",tableTitleFont);
                                                    tableTitle.setAlignment(Element.ALIGN_CENTER);
                                                    pdf.add(tableTitle);
                                                    pdf.add(xr.getInitialMedicalReportTable(this.getListOfMedicalHystoryDb(this.child.getBabyProfileid()))) ;
                            break;
                        case "vaccineSummary" :pdf.newPage();
                                               tableTitle = new Paragraph("List of Vaccines Received",tableTitleFont);
                                               tableTitle.setAlignment(Element.ALIGN_CENTER);
                                               pdf.add(tableTitle);
                                               pdf.add(xr.getVaccineTakenTable(this.getListOfVaccinesTakenDb())) ;
                            break;
                        case "followedTreatment" :pdf.newPage(); 
                                                 tableTitle = new Paragraph("List of Followed Treatments Recorded",tableTitleFont);
                                                 tableTitle.setAlignment(Element.ALIGN_CENTER);
                                                 pdf.add(tableTitle);
                                                 pdf.add(xr.getMedicationTakenTable(this.getListOfMedicationTakenDb())) ;
                            break;
                        case "doctorVisits" :pdf.newPage();
                                            tableTitle = new Paragraph("List of Doctor's Visit Recorded",tableTitleFont);
                                            tableTitle.setAlignment(Element.ALIGN_CENTER);
                                            pdf.add(tableTitle);
                                            Font subTitleFont = new Font(Font.TIMES_ROMAN,14,Font.UNDERLINE,Color.BLUE); 
                                            Font dateFont = new Font(Font.TIMES_ROMAN,14,Font.BOLDITALIC,Color.DARK_GRAY);                                     
                                            
                                           
                                            for(Appointment current: this.getListOfAllAppointmentRecords()){
                                                this.child.setAppointment(current);// so that is possible to get other date relative to the appointment                                       
                                                pdf.add(new Paragraph("\nAppointment #"+current.getAppointmentId()+"  Date: "+current.getDate()+"    Doctor: "+
                                                current.getDoctor()+"    Hospital: "+current.getHospital(),dateFont));
                                          
                                                Paragraph threeSubTitle= new Paragraph("\nHeadCirc                Age                Height                 Weight",subTitleFont);
                                                threeSubTitle.setAlignment(Element.ALIGN_CENTER);
                                                pdf.add(threeSubTitle);
                                                Paragraph threeSubTitleContent= new Paragraph(this.child.getHeadCir()+"                            "+this.child.getAge()+"                      "+this.child.getHeightRecorded()+"                       "+this.child.getHeightRecorded());
                                                threeSubTitleContent.setAlignment(Element.ALIGN_CENTER);
                                                pdf.add(threeSubTitleContent);
                                               
                                                Paragraph subHeading, subHeadingContent = new Paragraph();
                                                subHeading = new Paragraph("\n                                Child Complaints: ",subHeadingFont);
                                                pdf.add(subHeading);
                                                subHeadingContent = new Paragraph("                                                          "+current.getComplaints());
                                                pdf.add(subHeadingContent);
                                                
                                                subHeading = new Paragraph("\n                                Phisical Examination: ",subHeadingFont);
                                                pdf.add(subHeading);
                                                subHeadingContent = new Paragraph("                                                          "+current.getPhysicalExamination());
                                                pdf.add(subHeadingContent);
                                                
                                                subHeading = new Paragraph("\n                                Diagnostic List: ",subHeadingFont);
                                                pdf.add(subHeading);
                                                for(Diagnostics currDiagnose: this.getListOfDiagnosticRecordDb()){
                                                    subHeadingContent = new Paragraph("                                                          "+currDiagnose.getTitle());
                                                    pdf.add(subHeadingContent);
                                                }
                                                
                                                subHeading = new Paragraph("\n                                Required Exams: ",subHeadingFont);
                                                pdf.add(subHeading);
                                                for(MedicalExam currExam: this.getListOfMedicalExamRecordDb()){
                                                    subHeadingContent = new Paragraph("                                                          "+currExam.getExamTitle());
                                                    pdf.add(subHeadingContent);
                                                }
                                                
                                                subHeading = new Paragraph("\n                                Blood Test: ",subHeadingFont);
                                                pdf.add(subHeading);
                                                subHeadingContent = new Paragraph("                                                          "+current.getBloodTestNote());
                                                pdf.add(subHeadingContent);
                                                
                                                subHeading = new Paragraph("\n                                Doctor FeedBack: ",subHeadingFont);
                                                pdf.add(subHeading);
                                                subHeadingContent = new Paragraph("                                                          "+current.getDoctorFeedback());
                                                pdf.add(subHeadingContent);
                                                
                                               /*subHeading = new Paragraph("\n                                Treatment Plan: ",subHeadingFont);
                                                pdf.add(subHeading);
                                                subHeadingContent = new Paragraph("                                                          "+this.getTreatmentPlanDb());
                                                pdf.add(subHeadingContent);*/                                   
                                                pdf.newPage();
                                                
                                            }
                                            tableTitle = new Paragraph("Medical Concerns",tableTitleFont);
                                            tableTitle.setAlignment(Element.ALIGN_CENTER);
                                            pdf.add(tableTitle);
                                            tableTitle = new Paragraph("\nDetected Illnesse(s): ",subHeadingFont);
                                            tableTitle.setAlignment(Element.ALIGN_CENTER);
                                            pdf.add(tableTitle);                               
                                            for(DetectedIllness currIllness: this.getListOfDetectedIllnessRecordDb()){
                                                tableTitle = new Paragraph("  "+currIllness.getDescription());
                                                tableTitle.setAlignment(Element.ALIGN_CENTER);
                                                pdf.add(tableTitle);
                                            }
                                            
                                            tableTitle = new Paragraph("\nIdentified Allergy(ies): ",subHeadingFont);
                                            tableTitle.setAlignment(Element.ALIGN_CENTER);
                                            pdf.add(tableTitle);
                                            for(Allergies currAllergy: this.getListOfDetectedAllergiesRecordDb()){
                                                tableTitle = new Paragraph("  "+currAllergy.getDescription());
                                                tableTitle.setAlignment(Element.ALIGN_CENTER);
                                                pdf.add(tableTitle);
                                            }                                                                                      
                            break;
                    }
                }
                     tableTitle = new Paragraph("\n\nEND OF REPORT",subHeadingFont);
                                            tableTitle.setAlignment(Element.ALIGN_CENTER);
                                            tableTitle.setAlignment(Element.CCITT_ENDOFBLOCK);
                                            pdf.add(tableTitle);
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
            }
        //}
    }
    
    
    
    @PostConstruct 
    public void init(){
        today = new Date();
        images = new ArrayList<String>();
        for (int i = 1; i <= 8; i++) {
            images.add("nature" + i + ".jpg");
        }
        try {
            getlistOfChildren();
            getListOfVaccinesDb();
            reportPdf.open();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
        searchDate = new Date();
        activity = new DailyActivities();
    }
    
    public void setDefaultChild() throws ClassNotFoundException, SQLException{
        setChild(getlistOfChildren().get(0));
        getListOfAllHeightRecords();
        getListOfAllTemperatureRecords();
        getListOfAllWeightRecords();
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
            
            int id = rs.getInt("idBabyProfile");
            String fName = rs.getString("firstName");
            String lName = rs.getString("lastName");
            String distinguishMarks = rs.getString("distinguishingMarks");
            Date dateOfBirth = rs.getDate("DOB");
            char gender;
            if(rs.getString("gender").equalsIgnoreCase("M"))
                gender = 'M';
            else 
                gender = 'F';
            String placeOfBirth = rs.getString("placeOfBirth");
            String generalRemarks = rs.getString("generalRemarks");
            Double headCircumference = rs.getDouble("headCir");
            String photo ="default.png";
            if(rs.getString("profilePhoto") != null){
                photo = rs.getString("profilePhoto");
            }
            
            // Required Docs
            ArrayList<String> docsRequired = new ArrayList<String>();            
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
            ArrayList<String> childArrivalCondtion = new ArrayList<String>();  
            if(rs.getInt("abuse")==1)
                childArrivalCondtion.add("abuse");     
            if(rs.getInt("neglect")==1)
                childArrivalCondtion.add("neglect");
            if(rs.getInt("others")==1)
                childArrivalCondtion.add("others");
            String medicalReport = rs.getString("medicalReportComments");
            
            // Diet details
            ArrayList<String> childDietDetails = new ArrayList<String>();
            if(rs.getInt("formula")==1)
                childDietDetails.add("formula");
            if(rs.getInt("solid")==1)
                childDietDetails.add("solid");
            String dietComments = rs.getString("dietComments");
            
            // now intake
            IntakeInfo intakeInfo = new IntakeInfo(rs.getString("droppedBy"), rs.getString("FetchLocation"),
                    rs.getString("FetchedBy"),rs.getLong("PhoneNumber"), (Date)rs.getObject("Date/Time"));
            
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
                    generalRemarks, weight, height, headCircumference, getListOfMedicalHystoryDb(id),photo,childDietDetails,dietComments));
             
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
    
    // get height for a specifi a appointment 
    public Height getAppointmentHeight () throws SQLException, ClassNotFoundException {
        stmt2 = (Statement) connection2.createStatement();
        sql2="SELECT babyheight, babyheight.date, babyheight.idbabyheight FROM babyheight, babyappointment WHERE "
                + "babyheight.BabyProfile_idBabyProfile = "+this.child.getBabyProfileid()+" AND babyappointment.idbabyappointment ="+this.child.getAppointment().getAppointmentId()+" AND "+this.child.getAppointment().getDate()+" = babyheight.date";
        rs2 = stmt2.executeQuery(sql2);
        
        
        /*SELECT babyheight, babyheight.date FROM 
                `onthatile children's ministries`.babyheight,
                `onthatile children's ministries`.babyappointment WHERE babyheight.BabyProfile_idBabyProfile = 1 
                        AND babyappointment.idbabyappointment = 1 AND babyappointment.date >= babyheight.date;*/

        
        Height height = new Height();//Height(-0.5,this.child.getDateOfBirth());
        
        if(rs.getFetchSize()!=0){
            height.setDateRecorded(rs.getDate("date"));
            height.setHeight(rs.getDouble("babyheight"));
            height.setHeightID(rs.getInt("idbabyheight"));
        }else{
            if(this.getListOfAllHeightRecords().isEmpty()==false){
                height.setHeightID(this.getListOfAllHeightRecords().get(this.getListOfAllHeightRecords().size()-1).getHeightID());
                height.setDateRecorded(this.getListOfAllHeightRecords().get(this.getListOfAllHeightRecords().size()-1).getDateRecorded());
                height.setHeight(this.getListOfAllHeightRecords().get(this.getListOfAllHeightRecords().size()-1).getHeight());
            }

        }


        this.child.setHeightRecorded(height);
        return height;
    }
    
    // get weight relative an appointment 
        public Weight getAppointmentWeight () throws SQLException, ClassNotFoundException {
        stmt2 = (Statement) connection2.createStatement();
        sql2="SELECT weight, date,idBabyWeight FROM babyweight, babyappointment WHERE "
                + "babyweight.BabyProfile_idBabyProfile = "+this.child.getBabyProfileid()+" AND babyappointment.idbabyappointment ="+this.child.getAppointment().getAppointmentId()+" AND "+this.child.getAppointment().getDate()+" = babyheight.date";
        rs2 = stmt2.executeQuery(sql2);
                
        Weight weight = new Weight();
        
        if(rs.getFetchSize()!=0){
            weight.setDateRecorded(rs.getDate("date"));
            weight.setWeight(rs.getDouble("weight"));
            weight.setWeightId(rs.getInt("idBabyWeight"));
        }else{
            if(this.getListOfAllHeightRecords().isEmpty()==false){
                weight.setWeightId(this.getListOfAllWeightRecords().get(this.getListOfAllWeightRecords().size()-1).getWeightId());
                weight.setDateRecorded(this.getListOfAllWeightRecords().get(this.getListOfAllWeightRecords().size()-1).getDateRecorded());
                weight.setWeight(this.getListOfAllWeightRecords().get(this.getListOfAllHeightRecords().size()-1).getWeight());
            }

        }


        this.child.setWeightRecorded(weight);
        return weight;
    }
    //reading list of child's medicalhistory from DB
    public ArrayList<ChildMedicalHistory> getListOfMedicalHystoryDb(int id) throws SQLException{
        sql2="SELECT * FROM babymedicalhistory WHERE BabyProfile_idBabyProfile = "+id;
        rs2 = stmt2.executeQuery(sql2);
        ArrayList<ChildMedicalHistory> medicalHistoryRecords = new ArrayList<ChildMedicalHistory>();
        while(rs2.next()){
            medicalHistoryRecords.add(new ChildMedicalHistory(rs2.getString("doctName"), rs2.getString("illnessesDetected"),
                    rs2.getString("reasonForIllnesses"), rs2.getString("medications"), rs2.getString("allergies"), 
                    rs2.getString("specialTreatments"), rs2.getString("hospitalVisited"), rs2.getDate("dateofVisit"), rs2.getInt("idbabymedicalhistory")));        
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
        
        ArrayList<Nappies> currentNappiesRecord = new ArrayList<Nappies>();
        if(reportTab == true){
            sql = "SELECT idbabynappys, nappyWet, nappyDirty, nappyChangeTime, date FROM babynappys WHERE BabyProfile_idBabyProfile ="+this.child.getBabyProfileid()+" AND"
                    + " date between '"+dateConverter(this.reportStartDate)+"' AND '"+dateConverter(this.reportEndDate)+"'";
        }else{
            sql = "SELECT idbabynappys, nappyWet, nappyDirty, nappyChangeTime, date FROM babynappys WHERE"
                    + " BabyProfile_idBabyProfile ="+this.child.getBabyProfileid()+" AND babynappys.date ='"+dateConverter(getSearchDate())+"'";
        }
        rs = stmt.executeQuery(sql);
        
        while(rs.next()){
          Nappies nappyRecord = new Nappies();
          
          nappyRecord.setNappyId(rs.getInt("idbabynappys"));
          nappyRecord.setConditionDry(rs.getBoolean("NappyDirty"));
          nappyRecord.setConditionWet(rs.getBoolean("nappyWet"));
          nappyRecord.setNappyChangeTime(rs.getTime("nappyChangeTime"));
          nappyRecord.setDateRecorded(rs.getDate("date"));
          currentNappiesRecord.add(nappyRecord);
        }
        this.child.setlistOfNappyRecords(currentNappiesRecord);
        
        return this.child.getlistOfNappyRecords();
    }
    
    // reading list of child's meals from DB
    public ArrayList<Meals> getListOfMealsHadDb()throws ClassNotFoundException, SQLException{
        
        ArrayList<Meals> currentMealsList = new ArrayList<Meals>(); 
        if(reportTab == true){
        sql = "SELECT idBabyMeals, typeOfMeal, date, time, BabyMealsComment FROM babymeals WHERE babymeals.BabyProfile_idBabyProfile ="+this.child.getBabyProfileid()+" AND"
                + " date between '"+dateConverter(this.reportStartDate)+"' AND '"+dateConverter(this.reportEndDate)+"'";
        }else{
            sql = "SELECT idBabyMeals, typeOfMeal, date, time, BabyMealsComment FROM babymeals "
                    + "WHERE babymeals.BabyProfile_idBabyProfile ="+this.child.getBabyProfileid()+" AND babymeals.date ='"+dateConverter(getSearchDate())+"'";
        }
        rs = stmt.executeQuery(sql);
        
        while(rs.next()){
            Meals meal = new Meals();
            meal.setMealID(rs.getInt("idBabyMeals"));
            meal.setMealDescription(rs.getString("typeOfMeal"));
            meal.setCommentOnEating(rs.getString("BabyMealsComment"));
            meal.setMealIntakeTime(rs.getTime("time"));
            meal.setMealDateRecorded(rs.getDate("date"));
            currentMealsList.add(meal);
        }
        this.child.setlistOfMealsHad(currentMealsList);
        return this.child.getListOfMealsHad();
    }
    
    // reading records of child's daily activities
    public ArrayList<DailyActivities> getListOfActivitiesDb()throws ClassNotFoundException, SQLException{
        
        if(reportTab == true){
            sql = "SELECT idbabyactivity, date, time, activity, activityStatus, comment FROM babyactivities WHERE babyactivities.BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+" AND"
                    + " date between '"+dateConverter(this.reportStartDate)+"' AND '"+dateConverter(this.reportEndDate)+"'";
        }else{
            sql = "SELECT idbabyactivity, date, time, activity, activityStatus, comment FROM babyactivities "
                    + "WHERE babyactivities.BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+" AND babyactivities.date ='"+dateConverter(getSearchDate())+"'";
        }
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
        return this.child.getListOfActivitiesRecorded();
    }
    
    // reading records of child's sleeping routine
    public ArrayList<SleepingRoutine> getListOfSleepingRoutineDb()throws ClassNotFoundException, SQLException{
        
        ArrayList<SleepingRoutine> currentListOfSleepRoutine = new ArrayList<SleepingRoutine>();
        if(reportTab == true){
            sql = "SELECT idbabysleeptime, sleeptime, waketime, Date FROM babysleeptime WHERE babysleeptime.BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+" AND"
                    + " date between '"+dateConverter(this.reportStartDate)+"' AND '"+dateConverter(this.reportEndDate)+"'";
        }else{
            sql = "SELECT idbabysleeptime, sleeptime, waketime, Date FROM babysleeptime WHERE"
                    + " babysleeptime.BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+" AND babysleeptime.Date ='"+dateConverter(getSearchDate())+"'";
        }
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
    
    // get list of height records 
        public ArrayList<Height> getListOfAllHeightRecords()throws ClassNotFoundException, SQLException{
        
        ArrayList<Height> currentListOfHeightRecords = new ArrayList<Height>();
        if(reportTab == true){
            sql = "SELECT idbabyheight, date, babyheight FROM babyheight WHERE babyheight.BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+" AND "
                     + "date between '"+dateConverter(this.reportStartDate)+"' AND '"+dateConverter(this.reportEndDate)+"'";            
        }else{
            sql = "SELECT idbabyheight, date, babyheight FROM babyheight WHERE babyheight.BabyProfile_idBabyProfile="+this.child.getBabyProfileid();
        }
        rs = stmt.executeQuery(sql);
        while(rs.next()){
            Height currentHeight = new Height();
            currentHeight.setHeightID(rs.getInt("idbabyheight"));
            currentHeight.setDateRecorded(rs.getDate("date"));
            currentHeight.setHeight(rs.getDouble("babyheight"));
            currentListOfHeightRecords.add(currentHeight); 
        }
        this.child.setListOfHeightRecords(currentListOfHeightRecords);
        return this.child.getListOfHeightRecords();
    }
    
    // get list of Temperature records 
    public ArrayList<Temperature> getListOfAllTemperatureRecords()throws ClassNotFoundException, SQLException{
        
        ArrayList<Temperature> currentListOfTemperatureRecords = new ArrayList<Temperature>();
        if(reportTab == true){
            sql2 = "SELECT idBabyTemperature, babyTemperature, date, time FROM babytemperature WHERE babytemperature.BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+" AND"
                    + " date between '"+dateConverter(this.reportStartDate)+"' AND '"+dateConverter(this.reportEndDate)+"'";
        }else{
             sql2 = "SELECT idBabyTemperature, babyTemperature, date, time FROM babytemperature WHERE"
                     + " babytemperature.BabyProfile_idBabyProfile="+this.child.getBabyProfileid();//+" AND babyTemperature.date <='"+dateConverter(getSearchDate())+"'";
        }
        rs = stmt.executeQuery(sql2);
        while(rs.next()){
            Temperature currentTemperature = new Temperature();
            currentTemperature.setTemperatureId(rs.getInt("idBabyTemperature"));
            currentTemperature.setTemperatureReading(rs.getDouble("babyTemperature"));
            currentTemperature.setTemperatureDate(rs.getDate("date"));
            currentTemperature.setTemperatureTime(rs.getTime("time"));
            currentListOfTemperatureRecords.add(currentTemperature); 
        }
        this.child.setlistOfTempRecorded(currentListOfTemperatureRecords);
        return this.child.getlistOfTempRecorded();
    }
    
    // get list of Temperature records 
    public ArrayList<Weight> getListOfAllWeightRecords()throws ClassNotFoundException, SQLException{
        
        ArrayList<Weight> currentListOfWeightRecords = new ArrayList<Weight>();
        if(reportTab == true){
            sql2 = "SELECT idBabyWeight, weight, date FROM babyweight WHERE babyweight.BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+" AND "
                    + "date between '"+dateConverter(this.reportStartDate)+"' AND '"+dateConverter(this.reportEndDate)+"'";
        }else{
            sql2 = "SELECT idBabyWeight, weight, date FROM babyweight WHERE babyweight.BabyProfile_idBabyProfile="+this.child.getBabyProfileid();
        }
        rs = stmt.executeQuery(sql2);
        while(rs.next()){
            Weight currentWeight = new Weight();
            currentWeight.setWeightId(rs.getInt("idBabyWeight"));
            currentWeight.setWeight(rs.getDouble("weight"));
            currentWeight.setDateRecorded(rs.getDate("date"));
            currentListOfWeightRecords.add(currentWeight); 
        }
        this.child.setListOfWeightRecords(currentListOfWeightRecords);
        return this.child.getListOfWeightRecords();
    }
    
    // get list of Anomaly records 
    public ArrayList<Anomaly> getListOfAllAnomalytRecords()throws ClassNotFoundException, SQLException{
        
        ArrayList<Anomaly> currentListOfAnomalyRecords = new ArrayList<Anomaly>();
        if(reportTab == true){
            sql3 = "SELECT idbabyAnomalies, anomalyCategory, description, time, date, comments FROM babyAnomalies WHERE babyAnomalies.BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+" AND"
                    + " date between '"+dateConverter(this.reportStartDate)+"' AND '"+dateConverter(this.reportEndDate)+"'";
        }else{
            sql3 = "SELECT idbabyAnomalies, anomalyCategory, description, time, date, comments FROM babyAnomalies WHERE babyAnomalies.BabyProfile_idBabyProfile="+this.child.getBabyProfileid();
        }
        rs = stmt.executeQuery(sql3);
        while(rs.next()){
            Anomaly currentAnomaly = new Anomaly();
            currentAnomaly.setAnomalyId(rs.getInt("idbabyAnomalies"));
            currentAnomaly.setCategory(rs.getString("anomalyCategory"));
            currentAnomaly.setDescription(rs.getString("description"));
            currentAnomaly.setTime(rs.getTime("time"));
            currentAnomaly.setDate(rs.getDate("date"));
            currentAnomaly.setComments(rs.getString("comments"));
            currentListOfAnomalyRecords.add(currentAnomaly); 
        }
        this.child.setListOfAnomalyRecords(currentListOfAnomalyRecords);
        return this.child.getListOfAnomalyRecords();
    }
    public void setListOfChildren(ArrayList<Child> listOfChildren) {
        this.listOfChildren = listOfChildren;
    }
    
    // get list of Appointments 
    public ArrayList<Appointment> getListOfAllAppointmentRecords()throws ClassNotFoundException, SQLException{
        
        ArrayList<Appointment> currentListOfAppointmentRecords = new ArrayList<Appointment>();
        if(reportTab == true){
            sql3 = "SELECT idbabyappointment, date, doctor, hospital, childComplaints, physicalExamination, doctorFeedback, bloodTestNote"
                + " FROM babyappointment WHERE babyappointment.BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+" AND"
                    + " date between '"+dateConverter(this.reportStartDate)+"' AND '"+dateConverter(this.reportEndDate)+"'";
        }else{
            sql3 = "SELECT idbabyappointment, date, doctor, hospital, childComplaints, physicalExamination, doctorFeedback, bloodTestNote"
                    + " FROM babyappointment WHERE babyappointment.BabyProfile_idBabyProfile="+this.child.getBabyProfileid();
        }
        rs = stmt.executeQuery(sql3);
        while(rs.next()){
            
            Appointment currentAppointment = new Appointment();
            currentAppointment.setAppointmentId(rs.getInt("idbabyappointment"));
            currentAppointment.setDate(rs.getDate("date"));
            currentAppointment.setDoctor(rs.getString("doctor"));
            currentAppointment.setHospital(rs.getString("hospital"));
            currentAppointment.setComplaints(rs.getString("childComplaints"));
            currentAppointment.setPhysicalExamination(rs.getString("physicalExamination"));
            currentAppointment.setDoctorFeedback(rs.getString("doctorFeedback"));
            currentAppointment.setBloodTestNote(rs.getString("bloodTestNote"));
            currentListOfAppointmentRecords.add(currentAppointment); 
        }
        this.child.setListOfAppointmentRecords(currentListOfAppointmentRecords);
        return this.child.getListOfAppointmentRecords();
    }
    
    // get list of appointments with no treatment plan 
    public ArrayList<Appointment> getListOfAppointmentWithNoPrescription()throws ClassNotFoundException, SQLException{
        
        ArrayList<Appointment> currentListAppointments = new ArrayList<Appointment>();
        sql3="SELECT * FROM babyappointment where idbabyappointment NOT IN (SELECT babyappointmentfk FROM babytreatmentplan ) "
                + "AND BabyProfile_idBabyProfile="+this.child.getBabyProfileid();
        rs = stmt.executeQuery(sql3);
        while(rs.next()){
            Appointment currentAppointment = new Appointment();
            currentAppointment.setAppointmentId(rs.getInt("idbabyappointment"));
            currentAppointment.setDate(rs.getDate("date"));
            currentAppointment.setDoctor(rs.getString("doctor"));
            currentAppointment.setHospital(rs.getString("hospital"));
            currentAppointment.setComplaints(rs.getString("childComplaints"));
            currentAppointment.setPhysicalExamination(rs.getString("physicalExamination"));
            currentAppointment.setDoctorFeedback(rs.getString("doctorFeedback"));
            currentAppointment.setBloodTestNote(rs.getString("bloodTestNote"));
            currentListAppointments.add(currentAppointment);
        }
        return currentListAppointments;
    }
    
    // get list of diagnoses relative to an appointment 
    public ArrayList<Diagnostics > getListOfDiagnosticRecordDb()throws ClassNotFoundException, SQLException{
        
        ArrayList<Diagnostics> currentListOfDiagnosticRecords = new ArrayList<Diagnostics >();
        
        sql3 = "SELECT idbabyDiagnostics, diagnoseTitle"
                + " FROM babyDiagnostics WHERE babyDiagnostics.diagnosefk="+this.child.getAppointment().getAppointmentId();
        rs = stmt.executeQuery(sql3);
        while(rs.next()){
            
            Diagnostics currentDiagnose = new Diagnostics();
            currentDiagnose.setDiagnosticId(rs.getInt("idbabyDiagnostics"));
            currentDiagnose.setTitle(rs.getString("diagnoseTitle"));
            currentListOfDiagnosticRecords.add(currentDiagnose); 
            
        }
        this.child.setListOfDiagnosticRecords(currentListOfDiagnosticRecords);
        return this.child.getListOfDiagnosticRecords();
    }
    
    // get list of medical exams relative to an appointment 
    public ArrayList<MedicalExam> getListOfMedicalExamRecordDb()throws ClassNotFoundException, SQLException{
        
        ArrayList<MedicalExam> currentListOfMedicalExamRecords = new ArrayList<MedicalExam>();
        
        sql3 = "SELECT idbabyMedicalExams, examTitle, commentsOnResults"
                + " FROM babyMedicalExams WHERE babyMedicalExams.appointmentfk="+this.child.getAppointment().getAppointmentId();
        rs = stmt.executeQuery(sql3);
        while(rs.next()){
            
            MedicalExam currentMedicalExam = new MedicalExam();
            currentMedicalExam.setMedicalExamId(rs.getInt("idbabyMedicalExams"));
            currentMedicalExam.setExamTitle(rs.getString("examTitle"));
            currentMedicalExam.setResultComments(rs.getString("commentsOnResults"));
            currentListOfMedicalExamRecords.add(currentMedicalExam); 
            
        }
        this.child.setListOfMedicalExamRecords(currentListOfMedicalExamRecords);
        return this.child.getListOfMedicalExamRecords();
    }
    
    // get list of medical evidence docs relative to an appointment
    public ArrayList<MedicalEvidence> getListOfMedicalEvidenceRecordDb()throws ClassNotFoundException, SQLException{
        
        ArrayList<MedicalEvidence> currentListOfMedicalEvidenceRecords = new ArrayList<MedicalEvidence>();
        sql3 = "SELECT idbabyMedicalEvidence, evidenceTitle"
                + " FROM babyMedicalEvidence WHERE babyMedicalEvidence.babyappointmentfk="+this.child.getAppointment().getAppointmentId();
        rs = stmt.executeQuery(sql3);
        while(rs.next()){
            
            MedicalEvidence currentMedicalEvidence = new MedicalEvidence();
            currentMedicalEvidence.setMedicalEvidenceId(rs.getInt("idbabyMedicalEvidence"));
            currentMedicalEvidence.setEvidenceTitle(rs.getString("evidenceTitle"));
            currentListOfMedicalEvidenceRecords.add(currentMedicalEvidence);
        }
        System.out.println(""+currentListOfMedicalEvidenceRecords.size());
        this.child.setListOfMedicalEvidenceRecords(currentListOfMedicalEvidenceRecords);
        return this.child.getListOfMedicalEvidenceRecords();
    }
    
    // get list of treatment plan relative to an appointment
    public ArrayList<TreatmentPlan> getListOfTreatmentPlanRecordDb()throws ClassNotFoundException, SQLException{
        
        ArrayList<TreatmentPlan> currentListOfTreatmentPlanRecords = new ArrayList<TreatmentPlan>();
        
        sql3 = "SELECT idbabytreatmentplan, treatmentDescription, treatmentApproach, startDate, endDate"
                + " FROM babytreatmentplan WHERE babytreatmentplan.babyappointmentfk="+this.child.getAppointment().getAppointmentId();

        rs = stmt.executeQuery(sql3);
        while(rs.next()){
            TreatmentPlan currentTreatmentPlan = new TreatmentPlan();
            currentTreatmentPlan.setTreatmentPlanId(rs.getInt("idbabytreatmentplan"));
            currentTreatmentPlan.setDescription(rs.getString("treatmentDescription"));
            currentTreatmentPlan.setApproach(rs.getString("treatmentApproach"));
            currentTreatmentPlan.setStartDate(rs.getDate("startDate"));
            currentTreatmentPlan.setEndDate(rs.getDate("endDate"));
            currentListOfTreatmentPlanRecords .add(currentTreatmentPlan ); 
            this.child.setTreatmentPlan(currentTreatmentPlan);
        }
        this.child.setListOfTreatmentPlanRecords(currentListOfTreatmentPlanRecords);
        return this.child.getListOfTreatmentPlanRecords();
    }
    
    public TreatmentPlan getTreatmentPlanDb()throws ClassNotFoundException, SQLException{
        
        sql3 = "SELECT idbabytreatmentplan, treatmentDescription, treatmentApproach, startDate, endDate"
                + " FROM babytreatmentplan WHERE babytreatmentplan.babyappointmentfk="+this.child.getAppointment().getAppointmentId();

        rs = stmt.executeQuery(sql3);
        
        TreatmentPlan currentTreatmentPlan = new TreatmentPlan();
        currentTreatmentPlan.setTreatmentPlanId(rs.getInt("idbabytreatmentplan"));
        currentTreatmentPlan.setDescription(rs.getString("treatmentDescription"));
        currentTreatmentPlan.setApproach(rs.getString("treatmentApproach"));
        currentTreatmentPlan.setStartDate(rs.getDate("startDate"));
        currentTreatmentPlan.setEndDate(rs.getDate("endDate"));
        
        return currentTreatmentPlan;
    }
    
    
    // get list 0f all treatments done
    public ArrayList<TreatmentPlan> getListOfAllTreatmentPlan()throws ClassNotFoundException, SQLException{
        
        ArrayList<TreatmentPlan> currentListOfTreatmentPlanRecords = new ArrayList<TreatmentPlan>();
        
        sql3 ="SELECT * FROM babytreatmentplan WHERE babyappointmentfk IN (SELECT idbabyappointment FROM"
                    + " babyappointment WHERE BabyProfile_idBabyProfile ="+this.child.getBabyProfileid()+")";
        rs = stmt.executeQuery(sql3);
        while(rs.next()){
            
            TreatmentPlan currentTreatmentPlan = new TreatmentPlan();
            currentTreatmentPlan.setTreatmentPlanId(rs.getInt("idbabytreatmentplan"));
            currentTreatmentPlan.setDescription(rs.getString("treatmentDescription"));
            currentTreatmentPlan.setApproach(rs.getString("treatmentApproach"));
            currentTreatmentPlan.setStartDate(rs.getDate("startDate"));
            currentTreatmentPlan.setEndDate(rs.getDate("endDate"));
            currentListOfTreatmentPlanRecords .add(currentTreatmentPlan ); 
            this.child.setTreatmentPlan(currentTreatmentPlan);
        }
        return currentListOfTreatmentPlanRecords;
    }
    
    // get list of identified illnesses
    public ArrayList<DetectedIllness> getListOfDetectedIllnessRecordDb()throws ClassNotFoundException, SQLException{
        
        ArrayList<DetectedIllness> currentListOfDetectedIllnessRecords = new ArrayList<DetectedIllness>();
        
        sql3 = "SELECT idbabyDetectedIllness, description"
                + " FROM babyDetectedIllness WHERE babyDetectedIllness.BabyProfile_idBabyProfile="+this.child.getBabyProfileid();
        rs = stmt.executeQuery(sql3);
        while(rs.next()){
            
            DetectedIllness currentDetectedIllness = new DetectedIllness();
            currentDetectedIllness.setDetectedIllnessId(rs.getInt("idbabyDetectedIllness"));
            currentDetectedIllness.setDescription(rs.getString("description"));
            currentListOfDetectedIllnessRecords.add(currentDetectedIllness);
        }
        this.child.setListOfDetectedIllness(currentListOfDetectedIllnessRecords);
        return this.child.getListOfDetectedIllness();
    }
    
    // get list of identified allergies
    public ArrayList<Allergies> getListOfDetectedAllergiesRecordDb()throws ClassNotFoundException, SQLException{
        
        ArrayList<Allergies> currentListOfDetectedAllergyRecords = new ArrayList<Allergies>();
        
        sql2 = "SELECT idbabyAllergies, description"
                + " FROM babyAllergies WHERE babyAllergies.BabyProfile_idBabyProfile="+this.child.getBabyProfileid();
        rs = stmt.executeQuery(sql2);
        while(rs.next()){
            
            Allergies currentDetectedAllergy = new Allergies();
            currentDetectedAllergy.setAllergyId(rs.getInt("idbabyAllergies"));
            currentDetectedAllergy.setDescription(rs.getString("description"));
            currentListOfDetectedAllergyRecords.add(currentDetectedAllergy);
        }
        this.child.setListOfAllergyRecords(currentListOfDetectedAllergyRecords);
        return this.child.getListOfAllergyRecords();
    }
    
    // get list of vaccines
    public void getListOfVaccinesDb()throws ClassNotFoundException, SQLException{
        
        ArrayList<VaccineList> currentListOfVaccine = new ArrayList<VaccineList>();
        
        sql2 = "SELECT idvaccineList, vaccine, ageGroup, site FROM vaccineList";
        rs = stmt.executeQuery(sql2);
        while(rs.next()){
            
            VaccineList currentVaccine = new VaccineList();
            currentVaccine.setVaccineId(rs.getInt("idvaccineList"));
            currentVaccine.setVaccine(rs.getString("vaccine"));
            currentVaccine.setAgeGroup(rs.getString("ageGroup"));
            currentVaccine.setSite(rs.getString("site"));
            currentListOfVaccine.add(currentVaccine);
        }
        this.setListOfVaccines(currentListOfVaccine);
        
    }
    
    // get list of vaccines taken
    public ArrayList<VaccineTaken> getListOfVaccinesTakenDb()throws ClassNotFoundException, SQLException{
        
        ArrayList<VaccineTaken> currentListOfVaccineTaken = new ArrayList<VaccineTaken>();
        if(reportTab == true){
            sql2 = "SELECT idvaccineTaken, vaccine, ageGroup, scheduleDate, givenDate, location, site, status, comments"
                    + " FROM vaccineTaken WHERE vaccineTaken.BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+" AND "
                    + "givenDate between '"+dateConverter(this.reportStartDate)+"' AND '"+dateConverter(this.reportEndDate)+"'";
        }else{
            sql2 = "SELECT idvaccineTaken, vaccine, ageGroup, scheduleDate, givenDate, location, site, status, comments"
                    + " FROM vaccineTaken WHERE vaccineTaken.BabyProfile_idBabyProfile="+this.child.getBabyProfileid();
        }
        rs = stmt.executeQuery(sql2);
        while(rs.next()){
            
            VaccineTaken currentVaccine = new VaccineTaken();
            currentVaccine.setVaccineTakenId(rs.getInt("idvaccineTaken"));
            currentVaccine.setVaccineTaken(rs.getString("vaccine"));
            currentVaccine.setAgeGroup(rs.getString("ageGroup"));
            currentVaccine.setScheduleDate(rs.getDate("scheduleDate"));
            currentVaccine.setGivenDate(rs.getDate("givenDate"));
            currentVaccine.setLocation(rs.getString("location"));
            currentVaccine.setSite(rs.getString("site"));
            currentVaccine.setStatus(rs.getBoolean("status"));
            currentVaccine.setComments(rs.getString("comments"));
            currentListOfVaccineTaken.add(currentVaccine);
        }
        this.child.setListOfVaccineTaken(currentListOfVaccineTaken);
        return this.child.getListOfVaccineTaken();
        
    }
    
    // get list of medication to take
    public ArrayList<Medication> getListOfMedicationToTakeDb()throws ClassNotFoundException, SQLException{
        
        ArrayList<Medication> currentListOfMedicationToTake = new ArrayList<Medication>();
        
        sql2 = "SELECT idmedicationToTake, medicine, quantity, metric, timeInterval, "
                + "comments FROM medicationToTake WHERE medicationToTakeFk ="+this.child.getBabyProfileid();
        rs = stmt.executeQuery(sql2);
        while(rs.next()){
            
            Medication currentMedication = new Medication();
            currentMedication.setMedicationId(rs.getInt("idmedicationToTake"));
            currentMedication.setMedicine(rs.getString("medicine"));
            currentMedication.setQuantity(rs.getString("quantity"));
            currentMedication.setMetric(rs.getString("metric"));
            currentMedication.setTimeInterval(rs.getString("timeInterval"));
            currentMedication.setComments(rs.getString("comments"));
            currentListOfMedicationToTake.add(currentMedication);
        }
        this.child.setListOfMedicationToTake(currentListOfMedicationToTake);
        return this.child.getListOfMedicationToTake();
    }
    
    // get list of medication taken 
    public ArrayList<MedicationTaken> getListOfMedicationTakenDb()throws ClassNotFoundException, SQLException{
        
        ArrayList<MedicationTaken> currentListOfMedicationTaken = new ArrayList<MedicationTaken>();
        if(reportTab == true){
            sql2 = "SELECT idmediciationTaken, medicineTaken, quantity, metric, admissionTime, "
                + "comment FROM mediciationTaken WHERE mediciationTakenFk ="+this.child.getBabyProfileid()+" AND "
                    + "admissionTime between '"+dateConverter(this.reportStartDate)+"' AND '"+dateConverter(this.reportEndDate)+"'";
        }else{
            sql2 = "SELECT idmediciationTaken, medicineTaken, quantity, metric, admissionTime, "
                    + "comment FROM mediciationTaken WHERE mediciationTakenFk ="+this.child.getBabyProfileid();
        }
        rs = stmt.executeQuery(sql2);
        while(rs.next()){
            
            MedicationTaken currentMedication = new MedicationTaken();
            currentMedication.setMedicationTakenId(rs.getInt("idmediciationTaken"));
            currentMedication.setMedicineTaken(rs.getString("medicineTaken"));
            currentMedication.setQuantity(rs.getString("quantity"));
            currentMedication.setMetric(rs.getString("metric"));
            currentMedication.setAdmisssionTime(rs.getDate("admissionTime"));
            currentMedication.setComment(rs.getString("comment"));
            currentListOfMedicationTaken.add(currentMedication);
        }
        this.child.setListOfMedicationTaken(currentListOfMedicationTaken);
        return this.child.getListOfMedicationTaken();
    }
    
    // get list of audit record 
    public ArrayList<CaregiverRecordAudit> getrecordAuditDb()throws ClassNotFoundException, SQLException{
        
        ArrayList<CaregiverRecordAudit> caregiverAudit = new ArrayList<CaregiverRecordAudit>();
        
        sql3 = "SELECT idrecordAudit, date, staffName, Time FROM recordAudit WHERE BabyProfile_idBabyProfile ="+this.child.getBabyProfileid()+" AND "
                + "date ="+dateConverter(getSearchDate());
        rs = stmt.executeQuery(sql3);
        while(rs.next()){
            
            CaregiverRecordAudit currentCaregiver = new CaregiverRecordAudit();
            currentCaregiver.setRecordAuditId(rs.getInt("idrecordAudit"));
            currentCaregiver.setDate(rs.getDate("date"));
            currentCaregiver.setCaregiver(rs.getString("staffName,"));
            currentCaregiver.setTime(rs.getDate("Time"));
            caregiverAudit.add(currentCaregiver);
        }
        System.out.println("sixxea "+caregiverAudit.size());
        return caregiverAudit;   
    }
    
    // display vaccines on the table
     public ArrayList<VaccineTaken> displayVaccines()throws ClassNotFoundException, SQLException{
         ArrayList<VaccineTaken> temporaryList = new ArrayList<VaccineTaken>();
         boolean condition = false, found = false;
         VaccineList element = new VaccineList();
         int i=0;
         do{
             element = this.listOfVaccines.get(i);
             for(VaccineTaken current: getListOfVaccinesTakenDb()){
                 if(element.getVaccine().compareToIgnoreCase(current.getVaccineTaken())==0 && element.getAgeGroup().compareToIgnoreCase(current.getAgeGroup())== 0){
                     temporaryList.add(current);
                     found = true;
                 }
             }
             if(found==false){
                 temporaryList.add(new VaccineTaken(element.getVaccine(),element.getAgeGroup(),element.getSite(),
                         calculateVaccineDueDate(element.getVaccine(), element.getAgeGroup(),this.child.getDateOfBirth())));
             }
             i++;
             found = false;
             if(i>=this.listOfVaccines.size()){
                 condition = true;
             }
         }while(condition == false & i<this.listOfVaccines.size());
         return temporaryList;
     }
     
     // set Vaccine Due date based on child's age
     public Date calculateVaccineDueDate(String vaccine, String ageGroup, Date birth){
         Date dateResult = null;
         TheEqualizer eqi = new TheEqualizer();
         switch(vaccine){
             case "BCG" : dateResult = birth; 
                 break;
             case "OPV0": dateResult = birth; 
                 break;
             case "OPV1": dateResult = addDayToDate(birth, 45); // review 45 days {6 weeks}
                 break;
             case "RV1":  dateResult = addDayToDate(birth, 45); 
                 break;
             case "DTa-P-IPV-Hib1" :  dateResult = addDayToDate(birth, 45); 
                 break;
             case "Hep B1" :  dateResult = addDayToDate(birth, 45); 
                 break;
             case "PCV 1" :  dateResult = addDayToDate(birth, 45); 
                 break;
             case "DTa-P-IPV-Hib2" :  dateResult = addDayToDate(birth, 70); //70 days {10 weeks}
                 break;        
             case "Hep B2" :  dateResult = addDayToDate(birth, 70); 
                 break; 
             case "DTa-P-IPV-Hib3" :  dateResult = addDayToDate(birth, 98); //98 days {14 weeks}
                 break;
             case "Hep B3 " :  dateResult = addDayToDate(birth, 98); 
                 break;
             case "PCV2" :  dateResult = addDayToDate(birth, 98); 
                 break;
             case "RV2" :  dateResult = addDayToDate(birth, 98); 
                 break;
             case "Measles1" :  dateResult = addDayToDate(birth, 279); // 9 months {279 days}
                 break;
             case "PCV3" :  dateResult = addDayToDate(birth, 279); 
                 break;
             case "DTa-P-IPV-Hib4" :  dateResult = addDayToDate(birth, 558); // 18 months {558 days}
                 break;
             case "Measles2" :  dateResult = addDayToDate(birth, 558); 
                 break;
             case "Td" :  if(ageGroup.equalsIgnoreCase("6 years")){
                            dateResult = addYearToDate(birth, 6);
                          }else{
                            dateResult = addYearToDate(birth, 12);
                          }; 
                 break;
                
         }
         return eqi.formatDate(dateResult);
     }
     
     // adding day(s) to a date
     public Date addDayToDate(Date givenDate, int numberOfDays){
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        cal.add(Calendar.DATE, numberOfDays); 
        return cal.getTime();
     }
     
     // adding year(s) to a date
     public Date addYearToDate(Date givenDate, int numberOfYears){
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        cal.add(Calendar.YEAR, numberOfYears); 
        return cal.getTime();
     }
    
    // validates data before adding 
    public void addChild() throws SQLException, ClassNotFoundException{
        MrKaplan x = new MrKaplan();
        MenuView messages = new MenuView();
        if( x.isValidInput(this.getFirstname()) && x.isValidInput(this.getLastname())){
            
            if(x.isValidInput(this.getDestingushingMarks()) && x.isValidInput(this.getPlaceOfBirth())){
                if(x.isValidInput(this.getGeneralRemarks())  && x.isValidInput(this.getChildArrivalMedicalReport()) && 
                        x.isValidInput(this.getRequiredDocNote()) &&x.isAnEmail(this.getSocialWorkerDetails().getEmail())){
                    if(x.isValidInput(this.getIntakeDetails().getPersonWhoDropped()) && x.isValidInput(this.getIntakeDetails().getPersonWhoFetched())){
                        if(x.isValidInput(this.getParentInfo().getFatherName()) && x.isValidInput(this.getParentInfo().getMotherName()) 
                                && x.isValidInput(this.getSocialWorkerDetails().getSocialWorkerName()) && x.isNumeric(Double.toString(this.getParentInfo().getContactNumber()))){
                            if(x.isNumeric(Double.toString(this.getHeadCir())) && x.isNumeric(Double.toString(this.getHeightRecorded().getHeight())) 
                                    && x.isNumeric(Double.toString(this.getWeightRecorded().getWeight()))){
                                if(x.isNumeric(Double.toString(this.getSocialWorkerDetails().getPhoneNumber())) 
                                        && x.isNumeric(Double.toString(this.getIntakeDetails().getPhoneNumber()))){
                                    //setChild();
                                    //messages.save();
                                    if(this.getMedicalHistory()!=null){
                                        if(x.isValidInput(this.getMedicalHistory().getDoctersName()) && x.isValidInput(this.getMedicalHistory().getAllergies()) 
                                            &&  x.isValidInput(this.getMedicalHistory().getCause()) &&  x.isValidInput(this.getMedicalHistory().getClincVisited()) 
                                            &&  x.isValidInput(this.getMedicalHistory().getMedicines()) &&  x.isValidInput(this.getMedicalHistory().getSpecialTreatmeants()) 
                                            &&  x.isValidInput(this.getMedicalHistory().getIllnessDetected())){
                                            setChild();
                                            messages.save();
                                        } 
                                    }else{
                                        setChild();
                                        messages.save();
                                        this.clearChild();
                                    }
                                }
                            }
                           
                        }
                    }
                }
                
            }
            
        }
    }
    // Adding children details to the DB
    public void setChild() throws SQLException, ClassNotFoundException{
        
        connection = new DBConnection().getConnection();
        stmt = (Statement) connection.createStatement();
        
        sql = "INSERT INTO .`babyprofile`(`firstName`,`lastName`,`gender`,`DOB`,`placeOfBirth`,"
                + "`distinguishingMarks`,`form36`,`clinicCard`,`birthCertificate`,`medicalReport`,"
                + "`requiredDocsComment`,`medicalReportComments`,`abuse`,`neglect`,`others`,`generalRemarks`,`headCir`,"
                + "`dietComments`,`formula`,`solid`) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  
     
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
        ps.setString(18, this.getChildDietComments());
        ps.setInt(19,quickCheck("formula", this.getChildDietDetails()));
        ps.setInt(20,quickCheck("solid",this.getChildDietDetails()));
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
        //ps.setDate(5,dateConverter(this.getIntakeDetails().getDateAndTime()));
        ps.setObject(5, this.getIntakeDetails().getDateAndTime());
        ps.execute();
        
        // to another table- medicalHistory
        sql ="INSERT INTO .`babymedicalhistory`(`doctName`,`illnessesDetected`,`reasonForIllnesses`,`medications`"
                + ",`allergies`,`specialTreatments`,`dateofVisit`,`hospitalVisited`,`babyprofile_idBabyProfile`)"
                + "VALUES(?,?,?,?,?,?,?,?,"+fk+")";
        ps = connection.prepareStatement(sql);
        for(int i=0; i < this.listOfMedicalHistoryRecords.size();i++){
            ps.setString(1,this.listOfMedicalHistoryRecords.get(i).getDoctersName());
            ps.setString(2,this.listOfMedicalHistoryRecords.get(i).getIllnessDetected());
            ps.setString(3,this.listOfMedicalHistoryRecords.get(i).getCause());
            ps.setString(4,this.listOfMedicalHistoryRecords.get(i).getMedicines());
            ps.setString(5,this.listOfMedicalHistoryRecords.get(i).getAllergies());
            ps.setString(6,this.listOfMedicalHistoryRecords.get(i).getSpecialTreatmeants());
            ps.setDate(7,dateConverter(this.listOfMedicalHistoryRecords.get(i).getDateOfVisit()));
            ps.setString(8,this.listOfMedicalHistoryRecords.get(i).getClincVisited());
            ps.execute();
        }
         
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
    
    // validate vaccine 
    public void addVaccine() throws SQLException, ClassNotFoundException{
        MrKaplan x = new MrKaplan();
        MenuView messages = new MenuView();
        if( x.isValidInput(this.getVaccine().getVaccine()) && x.isValidInput(this.getVaccine().getAgeGroup()) && 
                x.isValidInput(this.getVaccine().getSite())){
            if(this.vaccine != null){
                createVaccine();
                messages.save();
            }else{
                messages.errorMessage("Object not set");
            }
        }
    }
    
    // create new Vaccine 
    public void createVaccine() throws SQLException, ClassNotFoundException{
        connection3 = new DBConnection().getConnection();
        stmt = (Statement) connection3.createStatement();
        
        sql = "INSERT INTO .`vaccineList`(`vaccine`,`ageGroup`,`site`) "
                + "VALUES(?,?,?)";
        ps = connection3.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  
     
        ps.setString(1,this.getVaccine().getVaccine());
        ps.setString(2,this.getVaccine().getAgeGroup());
        ps.setString(3,this.getVaccine().getSite());
        ps.execute();
    }
    
    //add new allergy
    public void addAllergy() throws SQLException, ClassNotFoundException{
        connection3 = new DBConnection().getConnection();
        stmt = (Statement) connection3.createStatement();
        
        sql = "INSERT INTO .`babyAllergies`(`description`,`BabyProfile_idBabyProfile`) "
                + "VALUES(?,?)";
        ps = connection3.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
        ps.setString(1,this.child.getAllergy().getDescription());
        ps.setInt(2,this.child.getBabyProfileid());
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Allergy recorded with Success", "Please Check!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // add detected illness (chronic illness)
    public void addChronicIlless() throws SQLException, ClassNotFoundException{
        connection3 = new DBConnection().getConnection();
        stmt = (Statement) connection3.createStatement();
        
        sql = "INSERT INTO .`babyDetectedIllness`(`description`,`BabyProfile_idBabyProfile`) "
                + "VALUES(?,?)";
        ps = connection3.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
        ps.setString(1,this.child.getChronicIllness().getDescription());
        ps.setInt(2,this.child.getBabyProfileid());
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Chronic Illness recorded with Success", "Please Check!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // add new diagnosis 
    public void addDiagnosis() throws SQLException, ClassNotFoundException{
         
        connection3 = new DBConnection().getConnection();
        stmt = (Statement) connection3.createStatement();
        
        sql = "INSERT INTO .`babyDiagnostics`(`diagnoseTitle`,`diagnosefk`) "
                + "VALUES(?,?)";
        ps = connection3.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
        ps.setString(1,this.child.getDiagnosis().getTitle());
        ps.setInt(2,this.child.getAppointment().getAppointmentId());
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Diagnosis recorded with Success", "Please Check!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // add new exam
    public void addExam() throws SQLException, ClassNotFoundException{
         
        connection3 = new DBConnection().getConnection();
        stmt = (Statement) connection3.createStatement();
        
        sql = "INSERT INTO .`babyMedicalExams`(`examTitle`,`commentsOnResults`,`appointmentfk`) "
                + "VALUES(?,?,?)";
        ps = connection3.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
        ps.setString(1,this.child.getExams().getExamTitle());
        ps.setString(2,this.child.getExams().getResultComments());
        ps.setInt(3,this.child.getAppointment().getAppointmentId());
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Exam recorded with Success", "Please Check!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    //add new medication (to take)
    public void addMedication() throws SQLException, ClassNotFoundException{
        connection3 = new DBConnection().getConnection();
        stmt = (Statement) connection3.createStatement();
        
        sql = "INSERT INTO .`medicationToTake`(`medicine`,`quantity`,`metric`,`timeInterval`,`comments`,`medicationToTakeFk`) "
                + "VALUES(?,?,?,?,?,?)";
        ps = connection3.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
        ps.setString(1,this.child.getMedication().getMedicine());
        ps.setString(2,this.child.getMedication().getQuantity());
        ps.setString(3,this.child.getMedication().getMetric());
        ps.setString(4,this.child.getMedication().getTimeInterval());
        ps.setString(5,this.child.getMedication().getComments());
        ps.setInt(6,this.child.getBabyProfileid());
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Medication created with Success", "Please Check!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // add new treatment plan (from doctor)
    public void addtreatmentPlan() throws SQLException, ClassNotFoundException{
        connection3 = new DBConnection().getConnection();
        stmt = (Statement) connection3.createStatement();
        
        sql = "INSERT INTO .`babytreatmentplan`(`treatmentDescription`,`treatmentApproach`,`startDate`,`endDate`,`babyappointmentfk`) "
                + "VALUES(?,?,?,?,?)";
        ps = connection3.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
        ps.setString(1,this.child.getTreatmentPlan().getDescription());
        ps.setString(2,this.child.getTreatmentPlan().getApproach());
        ps.setDate(3,dateConverter(this.child.getTreatmentPlan().getStartDate()));
        ps.setDate(4,dateConverter(this.child.getTreatmentPlan().getEndDate()));
        ps.setInt(5,this.child.getAppointment().getAppointmentId());
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Treatment recorded with Success", "Please Check!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // add new appointment 
    public void addAppointment() throws SQLException, ClassNotFoundException{
        connection3 = new DBConnection().getConnection();
        stmt = (Statement) connection3.createStatement();
        sql = "INSERT INTO .`babyappointment`(`date`,`doctor`,`hospital`,`childComplaints`,`physicalExamination`,`doctorFeedback`,`bloodTestNote`,`BabyProfile_idBabyProfile`) "
                + "VALUES(?,?,?,?,?,?,?,?)";
        ps = connection3.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1,dateConverter(this.child.getAppointment().getDate()));
        ps.setString(2,this.child.getAppointment().getDoctor());
        ps.setString(3,this.child.getAppointment().getHospital());
        ps.setString(4,this.child.getAppointment().getComplaints());
        ps.setString(5,this.child.getAppointment().getPhysicalExamination());
        ps.setString(6,this.child.getAppointment().getDoctorFeedback());
        ps.setString(7,this.child.getAppointment().getBloodTestNote());
        ps.setInt(8, this.child.getBabyProfileid());
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Appointment created with Success", "Please Check!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // administer vaccine to child
    public void administerVaccine() throws SQLException, ClassNotFoundException{
        connection3 = new DBConnection().getConnection();
        stmt = (Statement) connection3.createStatement();
        
        switch(order){
            case "insert": 
                sql = "INSERT INTO .`vaccineTaken`(`vaccine`,`ageGroup`,`scheduleDate`,`givenDate`,`location`,`site`,`status`,`comments`,`BabyProfile_idBabyProfile`) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
                ps = connection3.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,this.child.getVaccineTaken().getVaccineTaken());
                ps.setString(2,this.child.getVaccineTaken().getAgeGroup());
                ps.setDate(3, dateConverter(this.child.getVaccineTaken().getScheduleDate()));
                ps.setDate(4, dateConverter(this.child.getVaccineTaken().getGivenDate()));
                ps.setString(5,this.child.getVaccineTaken().getLocation());
                ps.setString(6,this.child.getVaccineTaken().getSite());
                ps.setBoolean(7,true);//this.child.getVaccineTaken().getStatus()
                ps.setString(8,this.child.getVaccineTaken().getComments());
                ps.setInt(9,this.child.getBabyProfileid());
                ps.execute();
                break;
            
            case "update":   
                sql = "UPDATE vaccineTaken SET scheduleDate=?, givenDate=?, location=?, status=?, comments=? " 
                + " WHERE idvaccineTaken = "+this.child.getVaccineTaken().getVaccineTakenId()+" AND "
                + "BabyProfile_idBabyProfile = "+this.child.getBabyProfileid();
                ps = connection3.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setDate(1, dateConverter(this.child.getVaccineTaken().getScheduleDate()));
                ps.setDate(2, dateConverter(this.child.getVaccineTaken().getGivenDate()));
                ps.setString(3,this.child.getVaccineTaken().getLocation());
                ps.setBoolean(4,true);
                ps.setString(5,this.child.getVaccineTaken().getComments());
                ps.executeUpdate();
                break;
            
            case "remove": 
                sql = "DELETE FROM vaccineTaken WHERE idvaccineTaken="+this.child.getVaccineTaken().getVaccineTakenId()+""
                + " AND BabyProfile_idBabyProfile="+this.child.getBabyProfileid();
                ps = connection3.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.execute();
                break;
                
        }
        FacesMessage msg;
        if(order.equals("remove"))
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vaccine Unchecked with Success", "Please Check!");
        else
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vaccine Administered with Success", "Please Check!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    // add Medication taken to db
    public void addMedicationTakenDb() throws SQLException, ClassNotFoundException{
        connection3 = new DBConnection().getConnection();
        stmt = (Statement) connection3.createStatement();
        sql = "INSERT INTO .`mediciationTaken`(`medicineTaken`,`quantity`,`metric`,`admissionTime`,`comment`,`mediciationTakenFk`) "
                + "VALUES(?,?,?,?,?,?)";
        ps = connection3.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);    
        ps.setString(1,this.child.getMedicationTaken().getMedicineTaken());
        ps.setString(2,this.child.getMedicationTaken().getQuantity());
        ps.setString(3,this.child.getMedicationTaken().getMetric());
        ps.setObject(4,this.child.getMedicationTaken().getAdmisssionTime());
        ps.setString(5,this.child.getMedicationTaken().getComment());
        ps.setInt(6, this.child.getBabyProfileid());
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Medicine administered with Success", "Please Check!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    // add Medication Taken
    public void addMedicationTaken() throws SQLException, ClassNotFoundException{
        if(this.buttonSaveVal.equals("Add")){
            this.buttonSaveVal = "Save";
            this.setDisplayFields(true);
            System.out.println("Amor easfa "+buttonSaveVal+"\n"+displayFields);
        }else if(this.buttonSaveVal.equals("Save")){
            addMedicationTakenDb();
            this.setDisplayFields(false);
            this.buttonSaveVal = "Add";
        }
    }
   
    // check selectedCheckBoxes for RequiredDocs
    public int quickCheck(String a, ArrayList<String> vector){
        int value =0;
        for(String curr: vector){
            if (curr.equals(a)){
                value = 1;
            }
        } 

        return value;
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
    
    // delete Child meals 
    public void deleteSelectedMeal() throws SQLException{
        sql2 = "DELETE FROM babymeals WHERE BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+""
                + " AND idBabyMeals="+this.child.getMealRecorded().getMealID();
        ps= connection.prepareStatement(sql2);
        ps.execute();
    }
    
    // delete Child sleep record 
        public void deleteSleepRecord() throws SQLException{
        sql2 = "DELETE FROM babysleeptime WHERE BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+""
                + " AND idbabysleeptime="+this.child.getSleepRecorded().getSleepingRoutineID();
        ps= connection.prepareStatement(sql2);
        ps.execute();
    }
    // delete Child nappy record 
    public void deleteNappyRecord() throws SQLException{
        sql2 = "DELETE FROM babynappys WHERE BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+""
                + " AND idbabynappys="+this.child.getNappyRecorded().getNappyId();
        ps= connection.prepareStatement(sql2);
        ps.execute();
    } 
    
    // delete Child Activity record 
    public void deleteActivityRecord() throws SQLException{
        sql2 = "DELETE FROM babyactivities WHERE BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+""
                + " AND idbabyactivity="+this.child.getActivityrecorded().getActivityID();
        ps= connection.prepareStatement(sql2);
        ps.execute();
    }
    
    // delete Child Anomaly record 
    public void deleteAnomalyRecord() throws SQLException{
        sql2 = "DELETE FROM babyAnomalies WHERE BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+""
                + " AND idbabyAnomalies="+this.child.getAnomalyRecord().getAnomalyId();
        ps= connection.prepareStatement(sql2);
        ps.execute();
    }
    
    // delete Child height Entry 
    public void deleteHeightEntry() throws SQLException{
        sql2 = "DELETE FROM babyheight WHERE BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+""
                + " AND idbabyheight="+this.child.getHeightRecorded().getHeightID();
        ps= connection.prepareStatement(sql2);
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Height Entry Deleted with Success", "Please Refresh the Chart");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // delete Child temperature Entry
    public void deleteTemperatureEntry() throws SQLException{
        sql2 = "DELETE FROM babytemperature WHERE BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+""
                + " AND idBabyTemperature="+this.child.getTemperatureRecord().getTemperatureId();
        ps= connection.prepareStatement(sql2);
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Temperature Entry Deleted with Success", "Please Refresh the Chart");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // delete weight entry 
    public void deleteWeightEntry() throws SQLException{
        sql2 = "DELETE FROM babyweight WHERE BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+""
                + " AND idBabyWeight="+this.child.getWeightRecorded().getWeightId();
        ps= connection.prepareStatement(sql2);
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Weight Entry Deleted with Success", "Please Refresh the Chart");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // delete allergy record
    public void deleteAllergyRecord() throws SQLException{
        sql2 = "DELETE FROM babyAllergies WHERE BabyProfile_idBabyProfile="+this.child.getBabyProfileid()+""
                + " AND idbabyAllergies="+this.child.getAllergy().getAllergyId();
        ps= connection.prepareStatement(sql2);
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Allergy record eliminated with Success", "Please Check");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // delete vacine 
    public void deleteVaccineRecord() throws SQLException{
        sql2 = "DELETE FROM vaccineList WHERE idvaccineList="+this.vaccine.getVaccineId();
        ps= connection.prepareStatement(sql2);
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vaccine record eliminated with Success", "Please Check");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // delete medication record 
    public void deleteMedicationRecord() throws SQLException{
        sql2 = "DELETE FROM medicationToTake WHERE idmedicationToTake="+this.child.getMedication().getMedicationId()+" AND"
                + " medicationToTakeFk ="+this.child.getBabyProfileid();
        ps= connection.prepareStatement(sql2);
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Medication eliminated with Success", "Please Check");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // delete diagnosis record 
    public void deleteDiagnosisRecord() throws SQLException{
        sql2 = "DELETE FROM babyDiagnostics WHERE idbabyDiagnostics="+this.child.getDiagnosis().getDiagnosticId()+" AND"
                + " diagnosefk ="+this.child.getAppointment().getAppointmentId();
        ps= connection.prepareStatement(sql2);
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Diagnostic eliminated with Success", "Please Check");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // delete Appointment record 
    public void deleteAppointmentRecord() throws SQLException{
        sql2 = "DELETE FROM babyappointment WHERE idbabyappointment="+this.child.getAppointment().getAppointmentId()+" AND"
                + " BabyProfile_idBabyProfile ="+this.child.getBabyProfileid();
        ps= connection.prepareStatement(sql2);
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Appointment eliminated with Success", "Please Check");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // delete Exam record 
    public void deleteExamRecord() throws SQLException{
        sql2 = "DELETE FROM babyMedicalExams WHERE idbabyMedicalExams="+this.child.getExams().getMedicalExamId()+" AND"
                + " appointmentfk ="+this.child.getAppointment().getAppointmentId();
        ps= connection.prepareStatement(sql2);
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam record eliminated with Success", "Please Check");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // delete Chronic illness 
    public void deleteChronicIllnessRecord() throws SQLException{
        sql2 = "DELETE FROM babyDetectedIllness WHERE idbabyDetectedIllness="+this.child.getChronicIllness().getDetectedIllnessId()+" AND"
                + " BabyProfile_idBabyProfile ="+this.child.getBabyProfileid();
        ps= connection.prepareStatement(sql2);
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Chronic illness record eliminated with Success", "Please Check");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // delete treatment plan 
    public void deleteTreatmentPlan() throws SQLException{
        sql2 = "DELETE FROM babytreatmentplan WHERE idbabytreatmentplan ="+this.child.getTreatmentPlan2().getTreatmentPlanId();
        ps= connection.prepareStatement(sql2);
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Treatment Plan eliminated with Success", "Please Check");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // delete Medication taken 
    public void deleteMedicationTaken() throws SQLException{
        sql2 = "DELETE FROM mediciationTaken WHERE idmediciationTaken ="+this.child.getMedicationTaken().getMedicationTakenId();
        ps= connection.prepareStatement(sql2);
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Medication taken eliminated with Success", "Please Check");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // delete initial medical history
    public void deleteInitialMedicalRecord()throws SQLException{
        sql2 = "DELETE FROM babymedicalhistory WHERE idbabymedicalhistory ="+this.child.getMedicalHistory().getId();
        ps= connection.prepareStatement(sql2);
        ps.execute();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Medical record eliminated with Success", "Please Check");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
        
    // dataTable, event handler methods
    public void onRowSelection(SelectEvent e){
        child = (Child) e.getObject();
        this.selectedChildren.add(child);
        
    }
    
    public void onRowUnselection(UnselectEvent e){
        child = (Child) e.getObject();
        for (Child current: selectedChildren){
            if(current.getBabyProfileid() == child.getBabyProfileid())
                this.selectedChildren.remove(current);
        }
       // this.selectedChildren.remove(child);
    }
    // validate before updating 
    public void editChild() throws SQLException, ClassNotFoundException{
        MrKaplan x = new MrKaplan();
        MenuView messages = new MenuView();
        if( x.isValidInput(this.child.getFirstname()) && x.isValidInput(this.child.getLastname())){
            
            if(x.isValidInput(this.child.getDestingushingMarks()) && x.isValidInput(this.child.getPlaceOfBirth())){
                if(x.isValidInput(this.child.getGeneralRemarks())  && x.isValidInput(this.child.getChildArrivalMedicalReport()) && 
                        x.isValidInput(this.child.getRequiredDocNote()) &&x.isAnEmail(this.child.getSocialWorkerDetails().getEmail())){
                    if(x.isValidInput(this.child.getIntakeDetails().getPersonWhoDropped()) && x.isValidInput(this.child.getIntakeDetails().getPersonWhoFetched())){
                        if(x.isValidInput(this.child.getParentInfo().getFatherName()) && x.isValidInput(this.child.getParentInfo().getMotherName()) 
                                && x.isValidInput(this.child.getSocialWorkerDetails().getSocialWorkerName()) && x.isNumeric(Double.toString(this.child.getParentInfo().getContactNumber()))){
                            if(x.isNumeric(Double.toString(this.child.getHeadCir())) && x.isNumeric(Double.toString(this.child.getHeightRecorded().getHeight())) 
                                    && x.isNumeric(Double.toString(this.child.getWeightRecorded().getWeight()))){
                                if(x.isNumeric(Double.toString(this.child.getSocialWorkerDetails().getPhoneNumber())) 
                                        && x.isNumeric(Double.toString(this.child.getIntakeDetails().getPhoneNumber()))){
                                    updateChild();
                                    messages.update();
                                    /*if(this.getMedicalHistory()!=null){
                                        if(x.isValidInput(this.child.getMedicalHistory().getDoctersName()) && x.isValidInput(this.child.getMedicalHistory().getAllergies()) 
                                            &&  x.isValidInput(this.child.getMedicalHistory().getCause()) &&  x.isValidInput(this.child.getMedicalHistory().getClincVisited()) 
                                            &&  x.isValidInput(this.child.getMedicalHistory().getMedicines()) &&  x.isValidInput(this.child.getMedicalHistory().getSpecialTreatmeants()) 
                                            &&  x.isValidInput(this.child.getMedicalHistory().getIllnessDetected())){
                                            updateChild();
                                            messages.save();
                                        } 
                                    }else{
                                       updateChild();
                                        messages.update();
                                        this.child.clearChild();
                                    }*/
                                }
                            }
                           
                        }
                    }
                }
                
            }
            
        }
    }
    // updating a record from the DB
    public void updateChild() throws SQLException {
        
        int selectedChildID = this.child.getBabyProfileid();
        sql = "UPDATE babyprofile,babyintake, parentinfo, socialworker, babyheight, babyweight SET firstName=?, lastName=?, distinguishingMarks=?, droppedBy=?"
                + ",FetchedBy=?, FetchLocation=?, babyintake.PhoneNumber=?, `Date/Time`=?,"
                + "fathername=?, mothername=?,fatherID=?, motherID=?, contactNumber=?,"
                + "physicalAddress=?, socialworkerName=?, organization=?, socialworker.PhoneNumber=?,"
                + "emailAddress=?, additionalInfo=?, headCir=?, generalRemarks=?, "
                + "requiredDocsComment=?, gender=?, medicalReportComments=?, babyheight=?,"
                + " weight=?, DOB=?, placeOfBirth=?, form36=?, clinicCard=?, birthCertificate=?, "
                + "medicalReport=?, abuse=?, neglect=?, others=?, dietComments=?, formula=?, solid=? "
                + "WHERE babyweight.babyprofile_idBabyProfile =idBabyProfile AND socialworker.babyprofile_idBabyProfile =idBabyProfile "
                + "AND babyheight.babyprofile_idBabyProfile =idBabyProfile AND babyintake.babyprofile_idBabyProfile =idBabyProfile AND"
                + " parentinfo.babyprofile_idBabyProfile =idBabyProfile AND idBabyProfile ="+selectedChildID;
        
        //profile table
        ps = connection.prepareStatement(sql);
        ps.setString(1,this.child.getFirstname());
        ps.setString(2, this.child.getLastname());
        ps.setString(3,this.child.getDestingushingMarks());
        //intake table
        ps.setString(4,this.child.getIntakeDetails().getPersonWhoDropped());
        ps.setString(5, this.child.getIntakeDetails().getPersonWhoFetched());
        ps.setString(6, this.child.getIntakeDetails().getFetchLocation());
        ps.setLong(7, this.child.getIntakeDetails().getPhoneNumber());
        ps.setObject(8, this.child.getIntakeDetails().getDateAndTime());
        //parent information
        ps.setString(9,this.child.getParentInfo().getFatherName());
        ps.setString(10,this.child.getParentInfo().getMotherName());
        ps.setString(11,this.child.getParentInfo().getFatherID());
        ps.setString(12,this.child.getParentInfo().getMotherID());
        ps.setLong(13,this.child.getParentInfo().getContactNumber());
        ps.setString(14,this.child.getParentInfo().getPhysicalAddress());
        //social worker
        ps.setString(15,this.child.getSocialWorkerDetails().getSocialWorkerName());
        ps.setString(16,this.child.getSocialWorkerDetails().getOrganization());
        ps.setLong(17,this.child.getSocialWorkerDetails().getPhoneNumber());
        ps.setString(18,this.child.getSocialWorkerDetails().getEmail());
        ps.setString(19,this.child.getSocialWorkerDetails().getAdditionalInfo());
        //profile table again
        ps.setDouble(20,this.child.getHeadCir());
        ps.setString(21, this.child.getGeneralRemarks());
        ps.setString(22, this.child.getRequiredDocNote());
        ps.setString(23, Character.toString(this.child.getGender()));
        ps.setString(24, this.child.getChildArrivalMedicalReport());
        //height 
        ps.setDouble(25, this.child.getHeightRecorded().getHeight());
        //weight 
        ps.setDouble(26, this.child.getWeightRecorded().getWeight());
        ps.setDate(27, dateConverter(this.child.getDateOfBirth()));
        ps.setString(28, this.child.getPlaceOfBirth());
        //Required Docs
        ps.setInt(29, quickCheck("form36",this.child.getChildRequiredDocs()));
        ps.setInt(30, quickCheck("clinicCard",this.child.getChildRequiredDocs()));
        ps.setInt(31, quickCheck("birthCertificate",this.child.getChildRequiredDocs()));
        ps.setInt(32, quickCheck("medicalReport",this.child.getChildRequiredDocs()));
        //Arrival Condition
        ps.setInt(33, quickCheck("abuse",this.child.getChildArrivalCondition()));
        ps.setInt(34, quickCheck("neglect",this.child.getChildArrivalCondition()));
        ps.setInt(35, quickCheck("others",this.child.getChildArrivalCondition()));
        ps.setString(36, this.child.getChildDietComments());
        ps.setInt(37, quickCheck("formula",this.child.getChildDietDetails()));
        ps.setInt(38, quickCheck("solid",this.child.getChildDietDetails()));
        
        ps.executeUpdate();
        
        for(int i=0; i<this.child.getListOfMedicalHistoryRecords().size();i++){
            updateChildMedicalRecord(selectedChildID, this.child.getListOfMedicalHistoryRecords().get(i).getId(),
                    this.child.getListOfMedicalHistoryRecords().get(i));
        }
    }
    // update medicalHistoryRecord 
    public void updateChildMedicalRecord(int id, int recordID, ChildMedicalHistory medicalRecord) throws SQLException {
        sql2 = "UPDATE babymedicalhistory SET doctName=?, illnessesDetected=?, reasonForIllnesses=?"
                + ", medications=?, allergies=?, specialTreatments=?, dateofVisit=?, hospitalVisited=?"
                + " WHERE idbabymedicalhistory="+recordID+" AND babyprofile_idBabyProfile ="+id;
        
        ps = connection.prepareStatement(sql2);
        ps.setString(1, medicalRecord.getDoctersName());
        ps.setString(2, medicalRecord.getIllnessDetected());
        ps.setString(3,medicalRecord.getCause());
        ps.setString(4, medicalRecord.getMedicines());
        ps.setString(5, medicalRecord.getAllergies());
        ps.setString(6, medicalRecord.getSpecialTreatmeants());
        ps.setDate(7, dateConverter(medicalRecord.getDateOfVisit()));
        ps.setString(8, medicalRecord.getClincVisited());
        
        ps.executeUpdate();
    }
    
    //updating meals table
    public void updateChildMeals() throws SQLException {
         
        int selectedChildID = this.child.getBabyProfileid();
        sql2 = "UPDATE babymeals SET typeOfMeal=?, date=?, time=?, BabyMealsComment=? "
                + "WHERE BabyProfile_idBabyProfile ="+selectedChildID+" AND idBabyMeals ="+this.child.getMealRecorded().getMealID();
        
        ps = connection.prepareStatement(sql2);
        ps.setString(1,this.child.getMealRecorded().getMealDescription());
        ps.setDate(2, dateConverter(this.child.getMealRecorded().getMealDateRecorded()));
        ps.setTime(3,timeConverter(this.child.getMealRecorded().getMealIntakeTime()));
        ps.setString(4,this.child.getMealRecorded().getCommentOnEating());
        ps.executeUpdate();    
    }
    
    //updating sleeping record table
    public void updateChildSleepTime() throws SQLException {
         
        int selectedChildID = this.child.getBabyProfileid();
        sql2 = "UPDATE babysleeptime SET sleeptime=?, waketime=?, Date=? "
                + "WHERE BabyProfile_idBabyProfile ="+selectedChildID+" AND "
                + "idbabysleeptime ="+this.child.getSleepRecorded().getSleepingRoutineID();
        
        ps = connection.prepareStatement(sql2);
        ps.setTime(1,timeConverter(this.child.getSleepRecorded().getSleepingTime()));
        ps.setTime(2, timeConverter(this.child.getSleepRecorded().getWakingTime()));
        ps.setDate(3,dateConverter(this.child.getSleepRecorded().getDateRecorded()));
        ps.executeUpdate();    
    }
    
    //updating nappycondition record
    public void updateChildNappyCondition() throws SQLException {
         
        int selectedChildID = this.child.getBabyProfileid();
        sql2 = "UPDATE babynappys SET nappyWet=?, NappyDirty=?, nappyChangeTime=?, date=? "
                + "WHERE BabyProfile_idBabyProfile ="+selectedChildID+" AND "
                + "idbabynappys ="+this.child.getNappyRecorded().getNappyId();
        
        ps = connection.prepareStatement(sql2);
        ps.setBoolean(1,this.child.getNappyRecorded().isConditionWet());
        ps.setBoolean(2, this.child.getNappyRecorded().isConditionDry());
        ps.setTime(3,timeConverter(this.child.getNappyRecorded().getNappyChangeTime()));
        ps.setDate(4, dateConverter(this.child.getNappyRecorded().getDateRecorded()));
        ps.executeUpdate();    
    }
    
    //updating dailyactivities record
    public void updateChildDailyActitvy() throws SQLException {
         
        int selectedChildID = this.child.getBabyProfileid();
        sql2 = "UPDATE babyactivities SET date=?, time=?, activity=?, activityStatus=?, comment=? "
                + "WHERE BabyProfile_idBabyProfile ="+selectedChildID+" AND "
                + "idbabyactivity ="+this.child.getActivityrecorded().getActivityID();
        
        ps = connection.prepareStatement(sql2);
        ps.setDate(1,dateConverter(this.child.getActivityrecorded().getDateRecorded()));
        ps.setTime(2,timeConverter(this.child.getActivityrecorded().getActivityOccuringTime()));
         ps.setString(3,this.child.getActivityrecorded().getTitle());
        ps.setString(4,this.child.getActivityrecorded().getStatus());
        ps.setString(5,this.child.getActivityrecorded().getComment());
        ps.executeUpdate();    
    }
    
    //updating Anomaly record
    public void updateChildAnomalyDetected() throws SQLException {
         
        int selectedChildID = this.child.getBabyProfileid();
        sql2 = "UPDATE babyAnomalies SET anomalyCategory=?, description=?, time=?, date=?, comments=? "
                + "WHERE BabyProfile_idBabyProfile ="+selectedChildID+" AND "
                + "idbabyAnomalies ="+this.child.getAnomalyRecord().getAnomalyId();
        
        ps = connection.prepareStatement(sql2);
        ps.setString(1,this.child.getAnomalyRecord().getCategory());
        ps.setString(2,this.child.getAnomalyRecord().getDescription());
        ps.setTime(3,timeConverter(this.child.getAnomalyRecord().getTime()));
        ps.setDate(4,dateConverter(this.child.getAnomalyRecord().getDate())); 
        ps.setString(5,this.child.getAnomalyRecord().getComments());
        ps.executeUpdate();    
    }
    // updating HeightGraph
    public void updateChildHeightEntry() throws SQLException {
        int selectedChildID = this.child.getBabyProfileid();
        sql2 = "UPDATE babyheight SET date=?, babyheight=? "
                + "WHERE BabyProfile_idBabyProfile ="+selectedChildID+" AND "
                + "idbabyheight ="+this.child.getHeightRecorded().getHeightID();
        
        ps = connection.prepareStatement(sql2);
        ps.setDate(1,dateConverter(this.child.getHeightRecorded().getDateRecorded())); 
        ps.setDouble(2,this.child.getHeightRecorded().getHeight());
        ps.executeUpdate();  
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Height Entry Updated with Success", "Please Refresh the Chart");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // updating TemperatureGraph
    public void updateChildTemperatureEntry() throws SQLException {
        int selectedChildID = this.child.getBabyProfileid();
        sql2 = "UPDATE babytemperature SET date=?, babyTemperature=?, time=? "
                + "WHERE BabyProfile_idBabyProfile ="+selectedChildID+" AND "
                + "idBabyTemperature ="+this.child.getTemperatureRecord().getTemperatureId();
        
        ps = connection.prepareStatement(sql2);
        ps.setDate(1,dateConverter(this.child.getTemperatureRecord().getTemperatureDate())); 
        ps.setDouble(2,this.child.getTemperatureRecord().getTemperatureReading());
        ps.setTime(3, timeConverter(this.child.getTemperatureRecord().getTemperatureTime()));
        ps.executeUpdate();  
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Temperature Entry Update with Success", "Please Refresh the Chart");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // updating WeightGraph 
    public void updateChildWeightEntry() throws SQLException {
        int selectedChildID = this.child.getBabyProfileid();
        sql2 = "UPDATE babyweight SET date=?, weight=? "
                + "WHERE BabyProfile_idBabyProfile ="+selectedChildID+" AND "
                + "idBabyWeight ="+this.child.getWeightRecorded().getWeightId();
        
        ps = connection.prepareStatement(sql2);
        ps.setDate(1,dateConverter(this.child.getWeightRecorded().getDateRecorded())); 
        ps.setDouble(2,this.child.getWeightRecorded().getWeight());
        ps.executeUpdate();  
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Weight Entry Update with Success", "Please Refresh the Chart");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // updating allergy 
    public void updateAllergy() throws SQLException {
        int selectedChildID = this.child.getBabyProfileid();
        sql2 = "UPDATE babyAllergies SET description=? "
                + "WHERE BabyProfile_idBabyProfile ="+selectedChildID+" AND "
                + "idbabyAllergies ="+this.child.getAllergy().getAllergyId();
        
        ps = connection.prepareStatement(sql2);
        ps.setString(1,this.child.getAllergy().getDescription()); 
        ps.executeUpdate();  
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Allergy record Updated with Success", "Please Close the window");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // updating vacine...
    public void updateVaccine() throws SQLException {
        sql2 = "UPDATE vaccineList SET vaccine=?, ageGroup=?, site=? " 
                + " WHERE idvaccineList ="+this.getVaccine().getVaccineId();
        
        ps = connection.prepareStatement(sql2);
        ps.setString(1,this.getVaccine().getVaccine());
        ps.setString(2,this.getVaccine().getAgeGroup());
        ps.setString(3,this.getVaccine().getSite());
        ps.executeUpdate();  
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vaccine record Updated with Success", "Please Confirm!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // updating medication
    public void updateMedicationToTake() throws SQLException {
        sql2 = "UPDATE medicationToTake SET medicine=?, quantity=?, metric=?, timeInterval=?, comments=? " 
                + " WHERE idmedicationToTake ="+this.child.getMedication().getMedicationId()+" AND medicationToTakeFk ="+this.child.getBabyProfileid();
        
        ps = connection.prepareStatement(sql2);
        ps.setString(1,this.child.getMedication().getMedicine());
        ps.setString(2,this.child.getMedication().getQuantity());
        ps.setString(3,this.child.getMedication().getMetric());
        ps.setString(4,this.child.getMedication().getTimeInterval());
        ps.setString(5,this.child.getMedication().getComments());
        ps.executeUpdate();  
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Medication record Update with Success", "Please Confirm!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // updating diagnosis 
    public void updateDiagnosis() throws SQLException {
        sql2 = "UPDATE babyDiagnostics SET diagnoseTitle=? " 
                + " WHERE idbabyDiagnostics ="+this.child.getDiagnosis().getDiagnosticId()+" AND "
                + "diagnosefk="+this.child.getAppointment().getAppointmentId();
        
        ps = connection.prepareStatement(sql2);
        ps.setString(1,this.child.getDiagnosis().getTitle());
        ps.executeUpdate();  
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Diagnosis record Update with Success", "Please Confirm!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // updating Appointment details 
    public void updateAppointment() throws SQLException {
        sql2 = "UPDATE babyappointment SET date=?, doctor=?, hospital=?, childComplaints=?, "
               + "physicalExamination=?, doctorFeedback=?, bloodTestNote=? " 
               + " WHERE idbabyappointment ="+this.child.getAppointment().getAppointmentId()+" AND BabyProfile_idBabyProfile ="+this.child.getBabyProfileid();
        
        ps = connection.prepareStatement(sql2);
        ps.setDate(1,dateConverter(this.child.getAppointment().getDate()));
        ps.setString(2, this.child.getAppointment().getDoctor());
        ps.setString(3, this.child.getAppointment().getHospital());
        ps.setString(4, this.child.getAppointment().getComplaints());
        ps.setString(5, this.child.getAppointment().getPhysicalExamination());
        ps.setString(6, this.child.getAppointment().getDoctorFeedback());
        ps.setString(7, this.child.getAppointment().getBloodTestNote());
        ps.executeUpdate();  
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Appointment record Updated with Success", "Please Confirm!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // updating Exam 
    public void updateMedicalExam() throws SQLException {
        sql2 = "UPDATE babyMedicalExams SET examTitle=?, commentsOnResults=? " 
                + " WHERE idbabyMedicalExams ="+this.child.getExams().getMedicalExamId()+" AND "
                + "appointmentfk="+this.child.getAppointment().getAppointmentId();
        
        ps = connection.prepareStatement(sql2);
        ps.setString(1,this.child.getExams().getExamTitle());
        ps.setString(2,this.child.getExams().getResultComments());
        ps.executeUpdate();  
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam record Updated with Success", "Please Confirm!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // updating chronic illness
    public void updateChronicIllness() throws SQLException {
        sql2 = "UPDATE babyDetectedIllness SET description=? " 
                + " WHERE idbabyDetectedIllness ="+this.child.getChronicIllness().getDetectedIllnessId()+" AND "
                + "BabyProfile_idBabyProfile="+this.child.getBabyProfileid();
        
        ps = connection.prepareStatement(sql2);
        ps.setString(1,this.child.getChronicIllness().getDescription());
        ps.executeUpdate();  
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Chronic Illness record Updated with Success", "Please Confirm!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    // updating TreatmentPlan 
    public void updateTreatmentPlan() throws SQLException {
        sql2 = "UPDATE babytreatmentplan SET treatmentDescription=?, treatmentApproach=?, startDate=?, endDate=? " 
                + " WHERE idbabytreatmentplan ="+this.child.getTreatmentPlan2().getTreatmentPlanId();
        ps = connection.prepareStatement(sql2);
        ps.setString(1, this.child.getTreatmentPlan2().getDescription());
        ps.setString(2, this.child.getTreatmentPlan2().getApproach());
        ps.setDate(3, dateConverter(this.child.getTreatmentPlan2().getStartDate()));
        ps.setDate(4, dateConverter(this.child.getTreatmentPlan2().getEndDate()));
        ps.executeUpdate(); 
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Treatment record Updated with Success", "Please Confirm!");
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
    
    //upload profile photo into DB
    public void setProfilePhoto(String photo) throws SQLException, ClassNotFoundException{
        sql3 = "UPDATE babyprofile SET profilePhoto=? "
                + "WHERE idBabyProfile ="+this.child.getBabyProfileid();
         
        ps = connection.prepareStatement(sql3);
        ps.setString(1,photo);
        ps.executeUpdate(); 
    }
    
    public String getSelectedChild() {
        return selectedChild;
    }

    public void setSelectedChild(String selectedChild) {
        this.selectedChild = selectedChild;
    }

    public String getSelectedAppointment() {
        return selectedAppointment;
    }

    public void setSelectedAppointment(String selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
    }
    
    public void getSelectedChildObject() throws ClassNotFoundException, SQLException{
        for(int i = 0 ; i<getlistOfChildren().size();i++){
            if(getlistOfChildren().get(i).getFirstname().equals(getSelectedChild())){
                this.child = getlistOfChildren().get(i); 
                getListOfAllHeightRecords();
                createChartHeightModel();
                getListOfAllTemperatureRecords();
                createChartTemperatureModel();
                getListOfAllWeightRecords();
                createChartWeightModel();
               
            }
        }
    }
    
    // get appointment selected {object}
    public void getSelectedAppoitmentObject() throws ClassNotFoundException, SQLException{
        for(int i = 0 ; i<this.child.getListOfAppointmentRecords().size();i++){
            if(this.child.getListOfAppointmentRecords().get(i).toString().equals(getSelectedAppointment())){
                this.child.getAppointment().setAppointmentId(this.child.getListOfAppointmentRecords().get(i).getAppointmentId());
            }
        }
    }
    
    // get selected Allergy selected
    public void getSelectedAllergyObject() throws ClassNotFoundException, SQLException{
        for(int i = 0; i<getListOfDetectedAllergiesRecordDb().size(); i++){
            if(getListOfDetectedAllergiesRecordDb().get(i).getDescription().equals(this.child.getAllergy().getDescription())){
                this.child.getAllergy().setAllergyId(getListOfDetectedAllergiesRecordDb().get(i).getAllergyId());
            }
        }
    }
    
    // get selected Chronic Illness
    public void getSelectedChronicIllnessObject() throws ClassNotFoundException, SQLException{
        for(int i = 0; i<getListOfDetectedIllnessRecordDb().size(); i++){
            if(getListOfDetectedIllnessRecordDb().get(i).getDescription().equals(this.child.getChronicIllness().getDescription())){
                this.child.getChronicIllness().setDetectedIllnessId(getListOfDetectedIllnessRecordDb().get(i).getDetectedIllnessId());
                System.out.println("-->yeha"+this.child.getChronicIllness().getDescription());
            }
        }
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }
    
    public java.sql.Time timeConverter( Date date ){
        Time sqlTime;
            if(date == null){
                sqlTime = null;
            }else{
                sqlTime = new Time(date.getTime());
     }
       return sqlTime;     
    }
    
    // dataTable event handlers for updating 
    public void onRowSelect(SelectEvent e){
        if((e.getObject())instanceof Anomaly){
            this.child.setAnomalyRecord((Anomaly)e.getObject()); 
        }else if((e.getObject())instanceof Appointment){
            try {
                this.child.setAppointment((Appointment)e.getObject());
                getListOfDiagnosticRecordDb();
                getListOfMedicalExamRecordDb();
                getListOfTreatmentPlanRecordDb();
                getListOfMedicalEvidenceRecordDb();
                getAppointmentHeight();
                //getListOfMedicationToTakeDb();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if((e.getObject())instanceof VaccineList){
            this.setVaccine((VaccineList) e.getObject());
        } else if((e.getObject())instanceof Medication){
            this.child.setMedication((Medication) e.getObject());
        } else if((e.getObject())instanceof Diagnostics){
            this.child.setDiagnosis((Diagnostics) e.getObject());
        } else if((e.getObject())instanceof MedicalExam){
            this.child.setExams((MedicalExam) e.getObject());
        } else if((e.getObject())instanceof TreatmentPlan){
            this.child.setTreatmentPlan((TreatmentPlan) e.getObject());
        } 
        else{
            this.child = (Child) e.getObject();

            try {
                getListOfAllHeightRecords();
                getListOfAllTemperatureRecords();
                getListOfAllWeightRecords();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
            }
            createChartHeightModel();
            createChartTemperatureModel();
            createChartWeightModel();
        }
    }
   
    public void onRowUnselect(UnselectEvent e){
        //this.child = (Child) e.getObject();
        child = null;
        this.child.setAnomalyRecord(null);
       // this.child.setTreatmentPlan(null);
    }

    public String getButtonValue() {
        return buttonValue;
    }

    public void setButtonValue(String buttonValue) {
        this.buttonValue = buttonValue;
    }
    
    public void rowSelectCheckbox(String vaccine, String ageGroup, boolean status){
        
        this.child.setVaccineTaken(getVaccineTakenObject(vaccine,ageGroup));
        this.buttonValue = (status)? "Removing": "Administer";
        
        // check status: administering (false) or removig (true)
        if(status == false){
            try {
                // adminstering
                // first check if vaccine already exist and determines operation: insert/update
                for(VaccineTaken currVaccine:getListOfVaccinesTakenDb()){
                    if(currVaccine.getVaccineTaken().equalsIgnoreCase(vaccine) &&
                            currVaccine.getAgeGroup().equalsIgnoreCase(ageGroup)){
                        order = "update";
                        this.child.getVaccineTaken().setVaccineTakenId(currVaccine.getVaccineTakenId());
                    }else
                        order = "insert";
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            // removing vaccine
            order = "remove";
        }
         
    }
    
    // rowColoring 
    public boolean rowColoring(Date date, String command, boolean status){
        boolean answer = false;
        if(status == true){
            return false;
        }else{
                  
            Date dateGiven1 = null, systemDate1 = null;
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date todayDate  = new Date();
            // for comparison simplicity
            String dateGiven = dateFormat.format(date), systemDate = dateFormat.format(todayDate); //{convert to string "2017/09/23"}
            try {
                dateGiven1 = dateFormat.parse(dateGiven);
                systemDate1 = dateFormat.parse(systemDate);
            } catch (ParseException ex) {
                Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
            }
            // calc. difference betwen dates
            long diff = dateGiven1.getTime() - systemDate1.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            switch(command){
                case "Overdue" : answer = (systemDate1.compareTo(dateGiven1) > 0); //? true : false ;
                    break;
                case "Warning" : answer = (diffDays <= 15 && diffDays >= 0); //? true : false ;
                    break;
                default : answer = false;
            }
        }
        return answer;
    }
    
    // get Vaccine object 
    public VaccineTaken getVaccineTakenObject(String vaccine, String ageGroup){
        /*boolean found = false;
        for(int i=0; i < this.getListOfVaccineTaken().size(); i++){
            if(this.getListOfVaccineTaken().get(i).getVaccineTaken().equalsIgnoreCase(vaccine) && 
                    this.getListOfVaccineTaken().get(i).getAgeGroup().equalsIgnoreCase(ageGroup)){
                found = true;
                return this.getListOfVaccineTaken().get(i);
            }
        }*/
        try {
            //if(found==false)
            for(VaccineTaken curr: this.displayVaccines()){
                if(curr.getVaccineTaken().equalsIgnoreCase(vaccine) && curr.getAgeGroup().equalsIgnoreCase(ageGroup))
                    return curr;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void onRowSelectTreatment(SelectEvent e){
        if((e.getObject())instanceof TreatmentPlan)
            this.child.setTreatmentPlan2((TreatmentPlan) e.getObject());
        else if((e.getObject())instanceof MedicationTaken)
            this.child.setMedicationTaken((MedicationTaken) e.getObject());
        else if((e.getObject())instanceof ChildMedicalHistory)
            this.child.setMedicalHistory((ChildMedicalHistory) e.getObject());
    }
    public void onRowUnselectTreatment(UnselectEvent e){
        //this.child.setTreatmentPlan2(null);
        this.child.setMedicationTaken(null);
        this.child.setMedicalHistory(null);
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
        
                
    }
    // upload files 

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    public void uploadFile() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    // uploading profile photo
    public Part getPerfilImage() {
        return perfilImage;
    }

    public void setPerfilImage(Part perfilImage) {
        this.perfilImage = perfilImage;
    }
    
    public void uploadProfilePhoto(){
        try{
            
            InputStream in =  perfilImage.getInputStream();
            String photoName = this.child.getBabyProfileid()+""+perfilImage.getSubmittedFileName();
            File file = new File("/Users/efsan1/NetBeansProjects/OnthatileWebApplication/web/resources/images/"+photoName);
            
            file.createNewFile(); 
            FileOutputStream out = new FileOutputStream(file);
            
            byte[] buffer = new byte[1024];
            int lenght;
            
            while((lenght=in.read(buffer))>0){
                out.write(buffer,0,lenght);
            }
            out.close();
            in.close();
            setProfilePhoto(photoName);
            
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
    }
    
    public String humorMe(){     
        System.out.println("Test DAte:"+this.addYearToDate(this.child.getDateOfBirth(), 3));
        return "Rosaaaa";
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
    
    public void onRowDblClckSelect(final SelectEvent event) {
        this.child.setMealRecorded((Meals) event.getObject());
    }
    
    public void onRowDblClckSelectSleep(final SelectEvent event) {
        this.child.setSleepRecorded((SleepingRoutine) event.getObject());
    }
    
    public void onRowDblClckSelectNappy(final SelectEvent event) {
        this.child.setNappyRecorded((Nappies) event.getObject());

    }
    
    public void onRowDblClckSelectActivity(final SelectEvent event) {
        this.child.setActivityrecorded((DailyActivities) event.getObject());       
    }

    
    public DailyActivities getActivity() {
        return activity;
    }

    public void setActivity(DailyActivities activity) {
        this.activity = activity;
    }
    // year format 
    public String yearConverter(Date date){
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        return yearFormat.format(date);
    }
    //day-month format
    public String dayMonthConverter(Date date){
        SimpleDateFormat yearFormat = new SimpleDateFormat("dd-MM");
        return yearFormat.format(date);
    }
    
    // chart methods
        private LineChartModel initHeightModel() {
        
        LineChartModel model = new LineChartModel();
        
        LineChartSeries heightValues = new LineChartSeries();
        heightValues.setName("Heights(mm)");
        this.child.getListOfHeightRecords().forEach((currHeight) -> {
            model.addLabel(dayMonthConverter(currHeight.getDateRecorded()));
            heightValues.set(currHeight.getHeight());
        });    
         
        model.addSeries(heightValues);
        model.setAnimateAdvanced(true);
	model.setShowTooltip(true);
        model.setAspectRatio(AspectRatio.GOLDEN_SECTION);
          
        return model;
    }
    
    private LineChartModel initTemperatureModel() {
        
        LineChartModel tempModel = new LineChartModel();
        
        LineChartSeries temperatureValues = new LineChartSeries();
        temperatureValues.setName("Temperature(Celsius)");
        this.child.getlistOfTempRecorded().forEach((currTemperature) -> {
            tempModel.addLabel(dayMonthConverter(currTemperature.getTemperatureDate()));
            temperatureValues.set(currTemperature.getTemperatureReading());
        }); 
        
        tempModel.addSeries(temperatureValues); 
        tempModel.setAnimateAdvanced(true);
	tempModel.setShowTooltip(true);
        tempModel.setAspectRatio(AspectRatio.GOLDEN_SECTION);
        
        return tempModel;
    }
    
    private LineChartModel reInitTemperatureModel() {
        
        LineChartModel tempModel = new LineChartModel();
        
        LineChartSeries temperatureValues = new LineChartSeries();
        temperatureValues.setName("Temperature(Celsius)");
        this.child.getlistOfTempRecorded().forEach((currTemperature) -> {
            tempModel.addLabel(yearConverter(currTemperature.getTemperatureDate()));
            temperatureValues.set(currTemperature.getTemperatureReading());
        }); 
        
        tempModel.addSeries(temperatureValues); 
        tempModel.setAnimateAdvanced(true);
	tempModel.setShowTooltip(true);
        tempModel.setAspectRatio(AspectRatio.GOLDEN_SECTION);
        
        return tempModel;
    }
    private BarChartModel initWeightModel() {
      
        BarChartModel model = new BarChartModel();
        model.setAspectRatio(AspectRatio.GOLDEN_SECTION);
 
        BarChartSeries weightValues = new BarChartSeries();
        weightValues.setName("Weight");
        this.child.getListOfWeightRecords().forEach((currWeight) -> {
            model.addLabel(dayMonthConverter(currWeight.getDateRecorded()));
            weightValues.set(currWeight.getWeight());
        }); 
 
        model.addSeries(weightValues);
        model.setShowTooltip(true);
        model.setSeriesBarDistance(15);
        model.setAnimateAdvanced(true);
        
        return model;
    }
    
    public void createChartHeightModel(){
        
        heightChart = initHeightModel();    
        Axis yAxis = heightChart.getAxis(AxisType.Y);
        yAxis.setShowLabel(true); 
        
    }
    
    public void createChartTemperatureModel(){

        temperatureChart = initTemperatureModel();
        Axis yAxisTemp = temperatureChart.getAxis(AxisType.Y);
        yAxisTemp.setShowLabel(true); 
    }
    
    public void reCreateChartTemperatureModel(){

        temperatureChartM = reInitTemperatureModel();
        Axis yAxisTemp = temperatureChartM.getAxis(AxisType.Y);
        yAxisTemp.setShowLabel(true); 
    }
    
    private void createChartWeightModel() {
        
        weightChart = initWeightModel(); 
        Axis xAxis = weightChart.getAxis(AxisType.X);
        xAxis.setShowGrid(false);     
    }
    
    public LineChartModel getHeightChart() {
        return heightChart;
    }

    public LineChartModel getTemperatureChart() {
        return temperatureChart;
    }

    public BarChartModel getWeightChart() {
        return weightChart;
    }

    public LineChartModel getTemperatureChartM() {
        return temperatureChartM;
    }

    public void setTemperatureChartM(LineChartModel temperatureChartM) {
        this.temperatureChartM = temperatureChartM;
    }
    
    // select event 
    public void onItemSelect(ItemSelectEvent event) throws ParseException, ClassNotFoundException, SQLException{
        
        String selectedDate = (String) this.heightChart.getLabels().get(event.getItemIndex());
        double height = (double)(heightChart.getSeries().get(event.getSeriesIndex())).getData().get(event.getItemIndex());
        
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Height Record: "
				+ ((LineChartSeries) heightChart.getSeries().get(event.getSeriesIndex())).getData().get(event.getItemIndex())
				+ " (m)" +"\nDay and Week: "+this.heightChart.getLabels().get(event.getItemIndex()));

		FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);   
                findSelectedHeightObject(height, dateConverter(selectedDate));
    }
    // for temperature
    public void onTemperatureSelect(ItemSelectEvent event) throws ParseException, ClassNotFoundException, SQLException{
        
        String selectedDate = (String) this.temperatureChart.getLabels().get(event.getItemIndex());
        double temperature = (double)(temperatureChart.getSeries().get(event.getSeriesIndex())).getData().get(event.getItemIndex());
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Temperature Entry: "
				+ ((LineChartSeries) temperatureChart.getSeries().get(event.getSeriesIndex())).getData().get(event.getItemIndex())
				+ " (Celsius)" +"\nDay and Week: "+this.temperatureChart.getLabels().get(event.getItemIndex()));
        FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);
        findSelectedTemperatureObject(temperature, dateConverter(selectedDate));
    }
    
    //for weight
    public void onWeightSelect(ItemSelectEvent event) throws ParseException, ClassNotFoundException, SQLException{
        
        String selectedDate = (String) this.weightChart.getLabels().get(event.getItemIndex());
        double weight = (double)(weightChart.getSeries().get(event.getSeriesIndex())).getData().get(event.getItemIndex());
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Weight Entry: "
				+ ((BarChartSeries) weightChart.getSeries().get(event.getSeriesIndex())).getData().get(event.getItemIndex())
				+ " (Kilograms)" +"\nDay and Week: "+this.weightChart.getLabels().get(event.getItemIndex()));
        FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);      
        findSelectedWeightObject(weight, dateConverter(selectedDate));
    }
    
    // Convert string to date
    public Date dateConverter(String date) throws ParseException{
        Date currentDate = new Date();
        String currentYear = yearConverter(currentDate);
        date= date +"-"+currentYear; // now is day-month-year
        Date dateToLook = new SimpleDateFormat("dd-MM-yyyy").parse(date);
        return dateToLook;
    }
    //find selected heigth ID
    public void findSelectedHeightObject(double height, Date date) throws ClassNotFoundException, SQLException {
        
        for (Height listOfHeightRecord : this.getListOfAllHeightRecords()) {
            if((listOfHeightRecord.getDateRecorded().compareTo(date)==0) && (listOfHeightRecord.getHeight()==height)){
                this.child.setHeightRecorded(null);
                this.child.setHeightRecorded(listOfHeightRecord);
            }
        }    
    }
    
    //find selected temperature ID
    public void findSelectedTemperatureObject(double temp, Date date) throws ClassNotFoundException, SQLException {
        
        for (Temperature temperature : this.getListOfAllTemperatureRecords()) {
            if((temperature.getTemperatureDate().compareTo(date)==0) && (temperature.getTemperatureReading()==temp)){
                this.child.setTemperatureRecord(null);
                this.child.setTemperatureRecord(temperature);
            }
        }    
    }
    
    //find selected weight ID
    public void findSelectedWeightObject(double weight, Date date) throws ClassNotFoundException, SQLException {
        
        for (Weight currWeight: this.getListOfAllWeightRecords()) {
            if((currWeight.getDateRecorded().compareTo(date)==0) && (currWeight.getWeight()==weight)){
                this.child.setWeightRecorded(null);
                this.child.setWeightRecorded(currWeight);
            }
        }    
    }
    
    //update charts
    public void refreshChartS(){
        initTemperatureModel();
        createChartTemperatureModel();
    }

    public List<Anomaly> getFilteredAnomalies() {
        return filteredAnomalies;
    }

    public void setFilteredAnomalies(List<Anomaly> filteredAnomalies) {
        this.filteredAnomalies = filteredAnomalies;
    }

    public List<VaccineList> getListOfVaccines() {
        return listOfVaccines;
    }

    public void setListOfVaccines(List<VaccineList> listOfVaccines) {
        this.listOfVaccines = listOfVaccines;
    }

    public VaccineList getVaccine() {
        return vaccine;
    }

    public void setVaccine(VaccineList vaccine) {
        this.vaccine = vaccine;
    } 

    public boolean isDisplayFields() {
        return displayFields;
    }

    public void setDisplayFields(boolean displayFields) {
        this.displayFields = displayFields;
    }

    public String getButtonSaveVal() {
        return buttonSaveVal;
    }

    public void setButtonSaveVal(String buttonSaveVal) {
        this.buttonSaveVal = buttonSaveVal;
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
          for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
}
