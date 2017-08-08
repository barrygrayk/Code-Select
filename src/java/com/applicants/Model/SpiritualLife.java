
package com.applicants.Model;



/**
 *
 * @author efsan1
 */

public class SpiritualLife {
    
    private boolean beliefInChrist;
    private String churchName;
    private String testimony;
    private int id;
    
    public boolean isBeliefInChrist() {
        return beliefInChrist;
    }

    public void setBeliefInChrist(boolean beliefInChrist) {
        this.beliefInChrist = beliefInChrist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getChurchName() {
        return churchName;
    }

    public void setChurchName(String churchName) {
        this.churchName = churchName;
    }

    public String getTestimony() {
        return testimony;
    }

    public void setTestimony(String testimony) {
        this.testimony = testimony;
    }
    
    
    
    
}
