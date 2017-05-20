package com.child.Model;


import com.staff.Model.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @author Barry Gray Kapelembe 
 */
public class Child extends Person implements Serializable{
    
    private String destingushingMarks;
    private ArrayList<Temperature> listOfTempRecorded;
    private ArrayList<Meals> listOfMealsHad;
    private ArrayList<Weight> listOfWeightRecorded;
    private ArrayList<Nappies> listOfNappyRecords;
    private ChildMedicalHistory medicalHistory;
    private ArrayList<SleepingRoutine> listOfSleepingRecords;
    private ArrayList<DailyActivities> listOfActivitiesRecorded; 
    private int babyProfileid;
    //this needs to be rethought
    private int staffID;

    
    public void clearChild(){
        this.destingushingMarks = null;
        super.setLastname(null);
        super.setFirstname(null);
    }
    
    public Child(int babyProfileid, String firstname, String lastname, char gender, String placeOfBirth, Date dateOfBirth, String marks, int staffID) {
        super(firstname, lastname, gender, placeOfBirth, dateOfBirth);
        this.destingushingMarks =  marks;
        this.babyProfileid = babyProfileid;
        this.staffID = staffID;
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
    

    public ArrayList<Weight> getlistOfWeightRecorded() {
        return listOfWeightRecorded;
    }

    public void setlistOfWeightRecorded(ArrayList<Weight> weight) {
        this.listOfWeightRecorded = weight;
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
    
    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public ArrayList<DailyActivities> getListOfActivitiesRecorded() {
        return listOfActivitiesRecorded;
    }

    public void setListOfActivitiesRecorded(ArrayList<DailyActivities> listOfActivitiesRecorded) {
        this.listOfActivitiesRecorded = listOfActivitiesRecorded;
    }
    
    @Override
    public int getAge() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
