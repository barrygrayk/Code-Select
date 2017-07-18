package com.Messages;

/**
 * 
 * @author Barry Gray
 */
public class InventoryAlerts {
    private int id;
    private String itemName;
    private String desc;
    private int days;

    public InventoryAlerts(int id, String itemName, String desc, int days) {
        this.id = id;
        this.itemName = itemName;
        this.desc = desc;
        this.days = days;
    }

    public InventoryAlerts(int id, String desc, int days) {
        this.id = id;
        this.desc = desc;
        this.days = days;
    }

    public InventoryAlerts(String desc, int days) {
        this.desc = desc;
        this.days = days;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public InventoryAlerts() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
    
    

}
