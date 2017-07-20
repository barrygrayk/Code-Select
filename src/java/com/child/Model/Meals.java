package com.child.Model;

import java.util.Date;

/**
 * 
 * @author efsan1 
 */
public class Meals {
    
    private String mealDescription;
    private int mealID;
    private String commentOnEating;
    private Date mealIntakeTime;
    private Date mealDateRecorded;

    public Meals(){
    }
    
    public Meals(String desc){
        this.mealDescription = desc;
    }
    
    public void setMealIntakeTime(Date mealIntakeTime) {
        this.mealIntakeTime = mealIntakeTime;
    }

    public Date getMealIntakeTime() {
        return mealIntakeTime;
    }

    public String getMealDescription() {
        return mealDescription;
    }

    public void setMealDescription(String mealDescription) {
        this.mealDescription = mealDescription;
    }

    public int getMealID() {
        return mealID;
    }

    public void setMealID(int mealID) {
        this.mealID = mealID;
    }

    public String getCommentOnEating() {
        return commentOnEating;
    }

    public void setCommentOnEating(String commentOnEating) {
        this.commentOnEating = commentOnEating;
    }

    public Date getMealDateRecorded() {
        return mealDateRecorded;
    }

    public void setMealDateRecorded(Date mealDateRecorded) {
        this.mealDateRecorded = mealDateRecorded;
    }

}
