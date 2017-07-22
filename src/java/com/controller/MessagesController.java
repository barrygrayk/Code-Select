package com.controller;

import com.MenuView.MenuView;
import com.Messages.InventoryAlerts;
import com.db.connection.AlertsTableConnection;
import com.staff.Model.Message;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Barry Gray
 */
@ManagedBean(name = "msgCont", eager = true)
@SessionScoped
public class MessagesController extends InventoryAlerts implements Serializable {

    private List<InventoryAlerts> alerts = new ArrayList<>();
    private List<InventoryAlerts> selectedAlerts = new ArrayList<>();
    private String text="Click clear";
    private String subject; 

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<InventoryAlerts> getSelectedAlerts() {
        return selectedAlerts;
    }

    public void setSelectedAlerts(List<InventoryAlerts> selectedAlerts) {
        this.selectedAlerts = selectedAlerts;
    }
    private InventoryAlerts selectedAlert;

    public void onRowSelect(SelectEvent e) {
        selectedAlert = (InventoryAlerts) e.getObject();
        selectedAlerts.add(selectedAlert);
    }

    public void onRowUnselect(UnselectEvent e) {
        selectedAlert = null;
    }

    public InventoryAlerts getSelectedAlert() {
        return selectedAlert;
    }

    public void setSelectedAlert(InventoryAlerts selectedAlert) {
        this.selectedAlert = selectedAlert;
    }

    public List<InventoryAlerts> getAlerts() {
        alerts = new AlertsTableConnection().getInventoryAlerts();
        return alerts;
    }

    public void setAlerts(List<InventoryAlerts> alerts) {
        this.alerts = alerts;
    }

    public void check() {
        MenuView feedback = new MenuView();
        feedback.addMessage(selectedAlerts.size() + "", "");
    }

    public void delete() throws ClassNotFoundException {
        AlertsTableConnection con = new AlertsTableConnection();
        for (InventoryAlerts alrt : selectedAlerts) {
            con.deleteStockItem(alrt.getId());
        }
    }
    
    public void sendMessage (){
        Message msg  = new Message();  
    }

}
