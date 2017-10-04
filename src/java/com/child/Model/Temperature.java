package com.child.Model;

import java.util.Date;

/**
 * 
 * @author efsan1
 */
public class Temperature {
    
    private int temperatureId;
    private Date temperatureDate;
    private Date temperatureTime;
    private double temperatureReading;
    

    public double getTemperatureReading() {
        return temperatureReading;
    }

    public void setTemperatureReading(double temperatureReading) {
        boolean set =false;
        if (temperatureReading >= 30 && temperatureReading <= 48){
            this.temperatureReading = temperatureReading;
            set = true;          
        }
       // return set;
    }

    public int getTemperatureId() {
        return temperatureId;
    }

    public void setTemperatureId(int temperatureId) {
        this.temperatureId = temperatureId;
    }

    public Date getTemperatureDate() {
        return temperatureDate;
    }

    public void setTemperatureDate(Date temperatureDate) {
        this.temperatureDate = temperatureDate;
    }

    public Date getTemperatureTime() {
        return temperatureTime;
    }

    public void setTemperatureTime(Date temperatureTime) {
        this.temperatureTime = temperatureTime;
    }
    

}
