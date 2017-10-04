package com.child.Model;

/**
 *
 * @author efsan1
 */
public class Allergies {
    
    private int allergyId;
    private String description;

    public int getAllergyId() {
        return allergyId;
    }

    public void setAllergyId(int allergyId) {
        this.allergyId = allergyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String toString (){
        return this.description;
    }
}
