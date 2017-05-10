
package com.applicants.Model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author efsan1
 */
@ManagedBean (name= "SpiritualLife", eager= true)
@SessionScoped

public class SpiritualLife {
    
    private boolean beliefInChrist;
    private String churchName;
    private String testimony;
    
    public boolean isBeliefInChrist() {
        return beliefInChrist;
    }

    public void setBeliefInChrist(boolean beliefInChrist) {
        this.beliefInChrist = beliefInChrist;
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
