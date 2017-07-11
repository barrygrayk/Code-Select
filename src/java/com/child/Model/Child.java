package com.child.Model;


import com.staff.Model.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @author efsan1 
 */
public class Child extends Person implements Serializable{
    
    private String destingushingMarks;
    private ArrayList<Temperature> listOfTempRecorded;
    private ArrayList<Meals> listOfMealsHad;
    private Weight weightRecorded = new Weight();
    private Height heightRecorded = new Height();
    private ArrayList<Nappies> listOfNappyRecords;
    private ChildMedicalHistory medicalHistory = new ChildMedicalHistory();
    protected ArrayList<ChildMedicalHistory> listOfMedicalHistoryRecords = new ArrayList<ChildMedicalHistory>();
    private ArrayList<SleepingRoutine> listOfSleepingRecords;
    private ArrayList<DailyActivities> listOfActivitiesRecorded; 
    private int babyProfileid;
    private double headCir;
    private ArrayList childRequiredDocs,childArrivalCondition;
    private String requiredDocNote, ChildArrivalMedicalReport, generalRemarks;
    private ParentInfo parentInfo = new ParentInfo();
    private SocialWorker socialWorkerDetails = new SocialWorker();
    protected IntakeInfo intakeDetails = new IntakeInfo();
    private int currentIndex = -1;
    
    private ArrayList<Object> spreadProperties;
    
    public void clearChild(){
        this.destingushingMarks = null;
        super.setLastname(null);
        super.setFirstname(null);
    }
    
    public Child(int babyProfileid, String firstname, String lastname, char gender, Date dateOfBirth,
            String placeOfBirth, String destingushingMarks, ArrayList childRequiredDocs, 
            ArrayList childArrivalCondition, String requiredDocNote, String ChildArrivalMedicalReport ,
            IntakeInfo intakeDetails, SocialWorker socialWorkerDetails, ParentInfo parentInfo, String generalRemarks,
            Weight weightRecorded, Height heightRecorded, double headCir, 
            ArrayList<ChildMedicalHistory> listOfMedicalHistoryRecords) {
        
        super(firstname, lastname, gender, placeOfBirth, dateOfBirth);
        this.destingushingMarks =  destingushingMarks;
        this.babyProfileid = babyProfileid;
        this.childRequiredDocs = childRequiredDocs;
        this.childArrivalCondition = childArrivalCondition;
        this.requiredDocNote = requiredDocNote;
        this.ChildArrivalMedicalReport = ChildArrivalMedicalReport;
        this.intakeDetails = intakeDetails;
        this.socialWorkerDetails = socialWorkerDetails;
        this.parentInfo = parentInfo;
        this.generalRemarks = generalRemarks;
        this.weightRecorded = weightRecorded;
        this.heightRecorded = heightRecorded;
        this.headCir = headCir;
        this.listOfMedicalHistoryRecords = listOfMedicalHistoryRecords;
        
    }
    public Child(){
    }
    public Child(String destingushingMarks, String firstname, String lastname) {
        super(firstname, lastname);
        this.destingushingMarks = destingushingMarks;
    }

    public Child(int babyProfileid,  String firstname, String lastname,String destingushingMarks) {
        super(firstname, lastname);
        this.destingushingMarks = destingushingMarks;
        this.babyProfileid = babyProfileid;
    }
    

    public int getBabyProfileid() {
        return babyProfileid;
    }

    public void setBabyProfileid(int babyProfileid) {
        this.babyProfileid = babyProfileid;
    }
    

    public ChildMedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(ChildMedicalHistory medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    
    public String getDestingushingMarks() {
        return destingushingMarks;
    }

    public void setDestingushingMarks(String destingushingMarks) {
        this.destingushingMarks = destingushingMarks;
    }

    public ArrayList<Temperature> getlistOfTempRecorded() {
        return listOfTempRecorded;
    }

    public void setlistOfTempRecorded(ArrayList<Temperature> temp) {
        this.listOfTempRecorded = temp;
    }

    public ArrayList<Meals> getListOfMealsHad() {
        return listOfMealsHad;
    }
    // temporary 
    public Meals getSelectedtMeal(){
        return listOfMealsHad.get(0);
    }

    public void setlistOfMealsHad(ArrayList<Meals> meals) {
        this.listOfMealsHad = meals;
    }
    // in case there are no meals records @@
    public void verifyListOfMealsHad(){
        if(this.listOfMealsHad.isEmpty())
            this.listOfMealsHad.add(new Meals("No meals records were taken"));
    }
    
   /* public void checkIfListIsEmpty(ArrayList<Object> list, String description){
        if(list.isEmpty()){
            list.add(new Object("No"+description+"record"));
        }
    }*/
    

    public Weight getWeightRecorded() {
        return weightRecorded;
    }

    public void setWeightRecorded(Weight weightRecorded) {
        this.weightRecorded = weightRecorded;
    }

    public ArrayList<Nappies> getlistOfNappyRecords() {
        return listOfNappyRecords;
    }
    
    public void verifyListOfNappiesRecord(){
        if(this.listOfNappyRecords.isEmpty()){}
          //  this.listOfNappyRecords.add(new Nappies("No nappies records were taken"));
    }

    public void setlistOfNappyRecords(ArrayList<Nappies> listOfNappies) {
        this.listOfNappyRecords = listOfNappies;
    }  
    
    public ArrayList<SleepingRoutine> getListOfSleepingRecords() {
        return listOfSleepingRecords;
    }

    public void setListOfSleepingRecords(ArrayList<SleepingRoutine> listOfSleepingRecords) {
        this.listOfSleepingRecords = listOfSleepingRecords;
    }
    
    public ArrayList<DailyActivities> getListOfActivitiesRecorded() {
        return listOfActivitiesRecorded;
    }

    public void setListOfActivitiesRecorded(ArrayList<DailyActivities> listOfActivitiesRecorded) {
        this.listOfActivitiesRecorded = listOfActivitiesRecorded;
    }

    public ArrayList<ChildMedicalHistory> getListOfMedicalHistoryRecords() {
        return listOfMedicalHistoryRecords;
    }

    public void setListOfMedicalHistoryRecords(ArrayList<ChildMedicalHistory> listOfMedicalHistoryRecords) {
        this.listOfMedicalHistoryRecords = listOfMedicalHistoryRecords;
    }
    
    public ArrayList getChildRequiredDocs() {
        return childRequiredDocs;
    }

    public void setChildRequiredDocs(ArrayList childRequiredDocs) {
        this.childRequiredDocs = childRequiredDocs;
    }

    public String getRequiredDocNote() {
        return requiredDocNote;
    }

    public void setRequiredDocNote(String requiredDocNote) {
        this.requiredDocNote = requiredDocNote;
    }

    public ArrayList getChildArrivalCondition() {
        return childArrivalCondition;
    }

    public void setChildArrivalCondition(ArrayList childArrivalCondition) {
        this.childArrivalCondition = childArrivalCondition;
    }

    public String getChildArrivalMedicalReport() {
        return ChildArrivalMedicalReport;
    }

    public void setChildArrivalMedicalReport(String ChildArrivalMedicalReport) {
        this.ChildArrivalMedicalReport = ChildArrivalMedicalReport;
    }

    public double getHeadCir() {
        return headCir;
    }

    public void setHeadCir(double headCir) {
        this.headCir = headCir;
    }

    public String getGeneralRemarks() {
        return generalRemarks;
    }

    public void setGeneralRemarks(String generalRemarks) {
        this.generalRemarks = generalRemarks;
    }

    public ParentInfo getParentInfo() {
        return parentInfo;
    }

    public void setParentInfo(ParentInfo parentInfo) {
        this.parentInfo = parentInfo;
    }

    public ArrayList<Object> getSpreadProperties() {
        return spreadProperties;
    }

    public void setSpreadProperties(ArrayList<Object> spreadProperties) {
        
        this.spreadProperties = spreadProperties;
    }

    public SocialWorker getSocialWorkerDetails() {
        return socialWorkerDetails;
    }

    public void setSocialWorkerDetails(SocialWorker socialWorkerDetails) {
        this.socialWorkerDetails = socialWorkerDetails;
    }

    public IntakeInfo getIntakeDetails() {
        return intakeDetails;
    }

    public void setIntakeDetails(IntakeInfo intakeDetails) {
        this.intakeDetails = intakeDetails;
    }

    public Height getHeightRecorded() {
        return heightRecorded;
    }

    public void setHeightRecorded(Height heightRecorded) {
        this.heightRecorded = heightRecorded;
    }
    
    // select medicalRecord to update
    public void selectMedicalRecordToUpdateByIndex(int i){
        
        if((!this.listOfMedicalHistoryRecords.isEmpty()) && (i>=0 && i<this.listOfMedicalHistoryRecords.size())){
            
            this.medicalHistory.setDoctersName(this.listOfMedicalHistoryRecords.get(i).getDoctersName());
            this.medicalHistory.setIllnessDetected(this.listOfMedicalHistoryRecords.get(i).getIllnessDetected());
            this.medicalHistory.setAllergies(this.listOfMedicalHistoryRecords.get(i).getAllergies());
            this.medicalHistory.setCause(this.listOfMedicalHistoryRecords.get(i).getCause());
            this.medicalHistory.setClincVisited(this.listOfMedicalHistoryRecords.get(i).getClincVisited());
            this.medicalHistory.setMedicines(this.listOfMedicalHistoryRecords.get(i).getMedicines());
            this.medicalHistory.setSpecialTreatmeants(this.listOfMedicalHistoryRecords.get(i).getSpecialTreatmeants());
            this.medicalHistory.setDateOfVisit(this.listOfMedicalHistoryRecords.get(i).getDateOfVisit());
            
            
        }
    }
    // load first medicalRecord to update: default
    public void selectMedicalRecordToUpdate(){
        currentIndex = 0;
        selectMedicalRecordToUpdateByIndex(0);
    }
    // select next medicalRecord to Update :next button 
    public void next(){
        if(!this.listOfMedicalHistoryRecords.isEmpty()){
            if(currentIndex == -1){
                currentIndex = this.listOfMedicalHistoryRecords.indexOf(this.medicalHistory);
                System.out.println("Fics index: "+currentIndex);
            }
            if((currentIndex + 1) < this.listOfMedicalHistoryRecords.size()){
                currentIndex = currentIndex + 1;
                selectMedicalRecordToUpdateByIndex(currentIndex);
                System.out.println("nexttttt"+this.medicalHistory.getIllnessDetected()+"\n indesx:"+currentIndex);
            }else{
                selectMedicalRecordToUpdate();
                currentIndex = 0;
            }
        }
    }
    
    // select medicalRecord to update: previous button 
    public void previous(){
        /*if(!this.listOfMedicalHistoryRecords.isEmpty()){
            if(currentIndex == -1){
                currentIndex = this.listOfMedicalHistoryRecords.indexOf(this.medicalHistory);
            }
            if((currentIndex - 1) > 0 ){
                currentIndex = currentIndex - 1;
                selectMedicalRecordToUpdateByIndex(currentIndex);
            }else{
                currentIndex = this.listOfMedicalHistoryRecords.size() - 1;
                selectMedicalRecordToUpdateByIndex(currentIndex);
            }
        }*/
    }
        
    @Override
    public int getAge() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
