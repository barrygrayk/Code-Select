package com.controller;

import com.Donations.Donations;
import com.InventoryModels.Inventory;
import com.MenuView.MenuView;
import com.db.connection.DonationsConnection;
import com.db.connection.InventorytableConneection;
import com.validation.MrKaplan;
import com.validation.TheEqualizer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Barry Gray
 */
@ManagedBean(name = "DonationsCont")
@SessionScoped
public class DonationsController extends Donations implements Serializable {

    private final MenuView feedback = new MenuView();
    private List<Donations> donations = new ArrayList<>();
    private Donations selectedDonation;
    private Donations donatedItem;
    private CartesianChartModel combinedModel;

    @PostConstruct
    public void init() {
        getDonations();
        createCombinedModel();
    }

    public CartesianChartModel getCombinedModel() {
        return combinedModel;
    }

    private void createCombinedModel() {
        combinedModel = new BarChartModel();
        BarChartSeries donation = new BarChartSeries();
        LineChartSeries traget = new LineChartSeries();
        for (Donations done : donations) {

            donation.setLabel("Received");
            donation.set(done.getDescription(), done.getRecieved());

            traget.setLabel("Goals");
            traget.set(done.getDescription(), done.getGoal());

        }
        combinedModel.addSeries(donation);
        combinedModel.addSeries(traget);
        combinedModel.setTitle("Donations graph");
        combinedModel.setLegendPosition("ne");
        combinedModel.setMouseoverHighlight(false);
        combinedModel.setShowDatatip(false);
        combinedModel.setShowPointLabels(true);
        Axis yAxis = combinedModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(50);
    }

    public List<Donations> getDonations() {
        donations = new DonationsConnection().getDonationItems();
        createCombinedModel();
        return donations;
    }

    public void setDonations(List<Donations> donations) {
        this.donations = donations;
    }

    public Donations getSelectedDonation() {
        return selectedDonation;
    }

    public void setSelectedDonation(Donations selectedDonation) {
        this.selectedDonation = selectedDonation;
    }

    public Donations getDonatedItem() {
        return donatedItem;
    }

    public void setDonatedItem(Donations donatedItem) {
        this.donatedItem = donatedItem;
    }

    public DonationsController() {
        super();
    }

    public void onRowSelect(SelectEvent e) {
        selectedDonation = (Donations) e.getObject();
    }

    public void onRowUnselect(UnselectEvent e) {
        selectedDonation = null;
    }

    public void addDonation() {
        MrKaplan validate = new MrKaplan();
        TheEqualizer eqi = new TheEqualizer();
        if (validate.isValidInput(getDescription())) {
            donatedItem = new Donations();
            donatedItem.setDescription(eqi.toUperAndLower(getDescription()));
            donatedItem.setGoal(getGoal());
            donatedItem.setRecieved(getRecieved());
            new DonationsConnection().addDonation(donatedItem);
            clear();

        }
    }

    public void loadDonationItem() {
        if (selectedDonation != null) {
            setDescription(selectedDonation.getDescription());
            setGoal(selectedDonation.getGoal());
            setRecieved(selectedDonation.getRecieved());

        }
    }

    public void editDonationItem() {
        MrKaplan validate = new MrKaplan();
        TheEqualizer eqi = new TheEqualizer();
        if (validate.isValidInput(getDescription())) {
            donatedItem = new Donations();
            donatedItem.setId(selectedDonation.getId());
            donatedItem.setDescription(eqi.toUperAndLower(getDescription()));
            donatedItem.setGoal(getGoal());
            donatedItem.setRecieved(getRecieved());
            new DonationsConnection().updateDonationItem(donatedItem);
            

        }
    }

    public void deleteDonatedItem() {
        if (selectedDonation != null) {
            try {
                new DonationsConnection().deleteDonationItem(selectedDonation.getId());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InventoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void clear() {
        setDescription(null);
        setGoal(0);
        setRecieved(0);
    }

}
