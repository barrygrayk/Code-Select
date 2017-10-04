package com.child.Model;

/**
 *
 * @author efsan1
 */
public class DetectedIllness {
    
    private int detectedIllnessId;
    private String description;

    public int getDetectedIllnessId() {
        return detectedIllnessId;
    }

    public void setDetectedIllnessId(int detectedIllnessId) {
        this.detectedIllnessId = detectedIllnessId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString(){
        return this.description;
        
    }
}
