
package com.child.Model;

import java.util.Date;

/**
 *
 * @author efsan1
 */
public class Height {
    
    private int heightID;
    private double height;
    private Date dateRecorded;

    public Height(double height, Date dateRecorded) {
        this.height = height;
        this.dateRecorded = dateRecorded;
    }

    public Height(){}
    
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public int getHeightID() {
        return heightID;
    }

    public void setHeightID(int heightID) {
        this.heightID = heightID;
    }
    
    @Override
    public String toString(){
        return ""+this.height;
    }
}
