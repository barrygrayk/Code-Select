package com.controller;

import com.MenuView.MenuView;
import com.Messages.InventoryAlerts;
import com.db.connection.AlertsTableConnection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Barry Gray
 */
@ManagedBean(name = "msgCont", eager = true)
@ViewScoped
public class MessagesController extends InventoryAlerts implements Serializable {

    private List<InventoryAlerts> alerts = new ArrayList<>();
    private List<InventoryAlerts> selectedAlerts = new ArrayList<>();

    public List<InventoryAlerts> getSelectedAlerts() {
        return selectedAlerts;
    }

    public void setSelectedAlerts(List<InventoryAlerts> selectedAlerts) {
        System.out.println("88888888888888888888888888888");
        this.selectedAlerts = selectedAlerts;
    }
    private InventoryAlerts selectedAlert;

    public void onRowSelect(SelectEvent e) {
        selectedAlert = (InventoryAlerts) e.getObject();
        selectedAlerts.add(selectedAlert);

        System.out.println("88888888888888888888888888888" + selectedAlerts.size());
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

}
