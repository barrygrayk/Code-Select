package com.Donations;

/**
 * 
 * @author Barry Gray
 */
public class Donations {
    private int id;
    private String description;
    private double goal;
    private double recieved;

    public Donations() {
    }

    public Donations(int id, String description, double goal, double recieved) {
        this.id = id;
        this.description = description;
        this.goal = goal;
        this.recieved = recieved;
    }

    public Donations(String description, double goal, double recieved) {
        this.description = description;
        this.goal = goal;
        this.recieved = recieved;
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

    public double getGoal() {
        return goal;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public double getRecieved() {
        return recieved;
    }

    public void setRecieved(double recieved) {
        this.recieved = recieved;
    }
    
    
    

}
