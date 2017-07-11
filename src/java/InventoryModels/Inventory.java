package InventoryModels;

import java.util.Date;

/**
 * 
 * @author Barry Gray
 */
public class Inventory implements Stock {
    private int id;
    private String description;
    private double quantity;
    private double lowThresh;
    private Date expireyDate;

    public Inventory() {
    }

    
    public Inventory(int id, String desc, double quantity, double lowThresh, Date expireyDate) {
        this.id = id;
        this.description = desc;
        this.quantity = quantity;
        this.lowThresh = lowThresh;
        this.expireyDate = expireyDate;
    }

    public Inventory(String desc, double quantity, double lowThresh) {
        this.description = desc;
        this.quantity = quantity;
        this.lowThresh = lowThresh;
    }
    

    @Override
    public Date getExpiraryDate() {
        return expireyDate;
    }

    @Override
    public boolean setstockID(int id) {
       boolean set = false;
       if(id>0){
           this.id = id;
           set =true;
       }
       return set;
    }

    @Override
    public boolean setDescription(String desc) {
        boolean set = false;
        if (desc !=null){
            this.description = desc;
            set = true;
        }
        return set;
    }

    @Override
    public boolean setQuantity(double quantity) {
       boolean set = false;
       if (quantity >= 0){
           this.quantity = quantity;
           set = true;
       }
       return set;
    }

    @Override
    public boolean setLowThreshold(double thresh) {
        boolean set = false;
         if (thresh>= 0){
           lowThresh = thresh;
           set = true;
       }
       return set;
    }

    @Override
    public boolean setExpirryDate(Date date) {
        boolean set = false;
        if (date !=null){
            expireyDate =date;
            set = true;
        }
        return set;
    }

    @Override
    public int getstockID() {
       return id;
    }

    @Override
    public String getDescription() {
       return description;
    }

    @Override
    public double getQuantity() {
      return quantity;
    }

    @Override
    public double getLowThreshold() {
       return lowThresh;
    }

}
