package com.applicants.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Barry Gray
 */
public class PersonanlityTraits {
    private String traitsToString;
    private List<String> traits;
     private List<String> selectedTraits;
    private List<String> strongTraits;
    private List<String> weekTraits;
    private String resonForStrength;
    private String reasonForWeekness;

    public PersonanlityTraits() {
        strongTraits = new ArrayList<>();
        weekTraits = new ArrayList<>();
        selectedTraits = new ArrayList ();
        traits = new ArrayList<>();
        traits.add("Timid");
        traits.add("Tactful");
        traits.add("Congenial");
        traits.add("Secure");
        traits.add("Organized");
        traits.add("Gentle");
        traits.add("Mature");
        traits.add("Compassionate");
        traits.add("Considerate");
        traits.add("Impulsive");
        traits.add("Impatient");
        traits.add("Sarcastic");
        traits.add("Stubborn");
        traits.add("Abrasive");
        traits.add("Intelligent");
        traits.add("Modest");
        traits.add("Patient");
        traits.add("Kind");
        traits.add("Trustworthy");
        traits.add("Insecure");
        traits.add("Nervous");
        traits.add("Angry");
        traits.add("Studious");
        traits.add("Motivated");
        traits.add("Relaxed");
        traits.add("Loving");
        traits.add("Deliberate");
        traits.add("Selfish");
        traits.add("Verbal");
    }

    public List<String> getTraits() {
        return traits;
    }

    public void setTraits(List<String> traits) {
        this.traits = traits;
    }

    public String getResonForStrength() {
        return resonForStrength;
    }

    public void setResonForStrength(String resonForStrength) {
        this.resonForStrength = resonForStrength;
    }

    public String getReasonForWeekness() {
        return reasonForWeekness;
    }

    public void setReasonForWeekness(String reasonForWeekness) {
        this.reasonForWeekness = reasonForWeekness;
    }

    public List<String> getStrongTraits() {
        return strongTraits;
    }

    public void setStrongTraits(List<String> strongTraits) {
        this.strongTraits = strongTraits;
    }

    public List<String> getWeekTraits() {
        return weekTraits;
    }

    public void setWeekTraits(List<String> weekTraits) {
        this.weekTraits = weekTraits;
    }

    public String getTraitsToString() {
        return traitsToString;
    }

    public void setTraitsToString(String traitsToString) {
        this.traitsToString = traitsToString;
    }

    public List<String> getSelectedTraits() {
        return selectedTraits;
    }

    public void setSelectedTraits(List<String> selectedTraits) {
        this.selectedTraits = selectedTraits;
    }
    
    
    
    

}
