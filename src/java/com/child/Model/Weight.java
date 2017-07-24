package com.child.Model;

import java.util.Date;

/**
 * 
 * @author efsan1
 */
public class Weight {
    
    private int weightId;
    private double weight;
    private Date dateRecorded;

    public Weight(double weightReading, Date dateRecorded) {
        this.weight = weightReading;
        this.dateRecorded = dateRecorded;
    }
    
    public Weight(){
    }
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weightReading) {
        this.weight = weightReading;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public int getWeightId() {
        return weightId;
    }

    public void setWeightId(int weightId) {
        this.weightId = weightId;
    }
    
    // needs rethinking
    public boolean VerifyWeightReading(double weightReading) {
        boolean set = false;
        if (weightReading > 0 ){
            this.weight = weightReading;
            set = true;
        }
        return set;   
    }

   
    
    

}
