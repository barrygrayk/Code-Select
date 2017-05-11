package com.child.Model;


import com.staff.Model.Person;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Barry Gray Kapelembe 
 */
public class Child extends Person implements Serializable{
    private String destingushingMarks;
    private Temperature temp;
    private Meals meal;
    private Weight weight;
    private Nappies nappy;
    private ChildMedicalHistory medicalHistory;
    private int babyProfileid;
    
    public void clearChild(){
        this.destingushingMarks = null;
        super.setLastname(null);
        super.setFirstname(null);
    }
    
    public Child(String firstname, String lastname, char gender, String placeOfBirth, Date dateOfBirth) {
        super(firstname, lastname, gender, placeOfBirth, dateOfBirth);
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

    public Temperature getTemp() {
        return temp;
    }

    public void setTemp(Temperature temp) {
        //this.temp.setTemperatureReading(temp);
        this.temp = temp;
    }

    public Meals getMeal() {
        return meal;
    }

    public void setMeal(String mealDes,String amountEaten,String comment) {
        this.meal.setMealDescription(mealDes);
        this.meal.setAmountEaten(amountEaten);
        this.meal.setCommentOnEating(comment);
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight.setWeightReading(weight);
    }

    public Nappies getNappy() {
        return nappy;
    }

    public void setNappy(boolean wet, boolean dry) {
        this.nappy.setConditionDry(dry);
        this.nappy.setConditionWet(wet);
    }  

    @Override
    public int getAge() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
