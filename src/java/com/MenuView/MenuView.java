package com.MenuView;

/**
 * 
 * @author Barry Gray Kapelembe 
 */
 
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
@ManagedBean
public class MenuView {
     
    public void save() {
        addMessage("Success", "Data saved");
    }
    public void addSatff(String message) {
        addMessage( message,null );
    }
    public void errorMessage(String message) {
        error("Error", message);
    }
    public void update() {
        addMessage("Success", "Data updated");
    }
     
    public void delete() {
        addMessage("Success", "Data deleted");
    }
   public void error(String summary, String detail) {
         FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(summary, message);
    }
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(summary, message);
    }
}