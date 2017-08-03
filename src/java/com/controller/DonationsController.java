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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.chartistjsf.model.chart.AspectRatio;
import org.chartistjsf.model.chart.ChartSeries;
import org.primefaces.event.ItemSelectEvent;
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
        //createCombinedModel();
        createBarModel2();
    }

    public CartesianChartModel getCombinedModel() {
        return combinedModel;
    }

    private void createCombinedModel() {
        combinedModel = new BarChartModel();
        BarChartSeries donation = new BarChartSeries();
        LineChartSeries traget = new LineChartSeries();

        if (donations.size() > 0) {
            donations.stream().map((done) -> {
                donation.setLabel("Received");
                donation.set(done.getDescription(), done.getRecieved());
             
                return done;
            }).forEachOrdered((done) -> {
                traget.setLabel("Goals");
                traget.set(done.getDescription(), done.getGoal());
            });
        } else {
            System.out.println("Here");
            donation.setLabel("Received");
            donation.set("", 1);
            traget.setLabel("Goals");
            traget.set("", 1);
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
    private org.chartistjsf.model.chart.BarChartModel barChartModel;

    public void createBarModel2() {
        Random random = new Random();
        barChartModel = new org.chartistjsf.model.chart.BarChartModel();
        barChartModel.setAspectRatio(AspectRatio.GOLDEN_SECTION);
        org.chartistjsf.model.chart.BarChartSeries series1 = new org.chartistjsf.model.chart.BarChartSeries();
        series1.setName("Recieved");
        org.chartistjsf.model.chart.BarChartSeries series2 = new org.chartistjsf.model.chart.BarChartSeries();
        series2.setName("Goal");
        for (Donations don : donations) {
            barChartModel.addLabel(don.getDescription());
            series1.set(don.getRecieved());
            series2.set(don.getGoal());
        }
        org.chartistjsf.model.chart.Axis xAxis = barChartModel.getAxis(org.chartistjsf.model.chart.AxisType.X);
        xAxis.setShowGrid(false);
        barChartModel.addSeries(series1);
        barChartModel.addSeries(series2);
        barChartModel.setShowTooltip(true);
        barChartModel.setSeriesBarDistance(15);
        barChartModel.setAnimateAdvanced(true);
    }

    /**
     * @return the barChartModel
     */
    public org.chartistjsf.model.chart.BarChartModel getBarChartModel() {
        return barChartModel;
    }

    /**
     * @param barChartModel the barChartModel to set
     */
    public void setBarChartModel(org.chartistjsf.model.chart.BarChartModel barChartModel) {
        this.barChartModel = barChartModel;
    }

    public void barItemSelect(ItemSelectEvent event) {
      /*  FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Item Value: "
                + ((ChartSeries) barChartModel.getSeries().get(event.getSeriesIndex())).getData().get(
                        event.getItemIndex()) + ", Series name:"
                + ((ChartSeries) barChartModel.getSeries().get(event.getSeriesIndex())).getName());
        FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);*/
    }

    public List<Donations> getDonations() {
        donations = new DonationsConnection().getDonationItems();
        //createCombinedModel();
        createBarModel2();
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
        }
    }

    public void loadDonationItem() {
        if (selectedDonation != null) {
            setDescription(selectedDonation.getDescription());
            setGoal(selectedDonation.getGoal());
            setRecieved(selectedDonation.getRecieved());
            System.out.println();

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
            clear();
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
