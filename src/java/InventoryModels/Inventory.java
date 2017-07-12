package InventoryModels;

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
