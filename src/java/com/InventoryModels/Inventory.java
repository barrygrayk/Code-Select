package com.InventoryModels;

import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 *
 * @author Barry Gray
 */
public class Inventory {

    private int id;
    private String description;
    private double quantity;
    private double lowThresh;
    private Date expireyDate;
    private int daysLeft;
    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        if (quantity > 1 && (!unit.endsWith("s"))) {
            this.unit = unit + "s";
        } else if (quantity == 1 && (unit.endsWith("s"))) {
            if (unit.equals("Cases")) {
                this.unit = "Case";
            } else {
                this.unit = unit.replace("s", "");
            }
        } else {
            this.unit = unit;
        }
    }

    //private int daysLeft
    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft() {
        Date d1 = new Date();
        System.out.println("InventoryModels.Inventory.setDaysLeft()" + d1);
        final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
        int diffInDays = (int) ((expireyDate.getTime() - d1.getTime()) / DAY_IN_MILLIS);
        daysLeft = diffInDays;
    }

    public Inventory() {
    }

    public Inventory(String description, double quantity, double lowThresh, Date expireyDate) {
        this.description = description;
        this.quantity = quantity;
        this.lowThresh = lowThresh;
        this.expireyDate = expireyDate;
    }

    public Inventory(int id, String description, double quantity, double lowThresh, Date expireyDate) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.lowThresh = lowThresh;
        this.expireyDate = expireyDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getLowThresh() {
        return lowThresh;
    }

    public void setLowThresh(double lowThresh) {
        this.lowThresh = lowThresh;
    }

    public Date getExpireyDate() {
        return expireyDate;
    }

    public void setExpireyDate(Date expireyDate) {

        this.expireyDate = expireyDate;
    }

}
