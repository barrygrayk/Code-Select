package com.controller;

import com.InventoryModels.Inventory;
import com.MenuView.MenuView;
import com.db.connection.InventorytableConneection;
import com.staff.Model.OthantileStaff;
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

/**
 *
 * @author Barry Gray
 */
@ManagedBean(name = "InventoryCont", eager = true)
@SessionScoped
public class InventoryController extends Inventory implements Serializable {

    private final MenuView feedback = new MenuView();
    private List<Inventory> inventory = new ArrayList<>();
    private ArrayList<String> units = new ArrayList<>();

    public ArrayList<String> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<String> units) {
        this.units = units;
    }

   
    private Inventory selectedStock;
    private Inventory stockItem;
  

    public InventoryController() {
        super();
    }

    @PostConstruct
    public void init() {
        getInventory();
        units.add("Box");
        units.add("Case");
        units.add("Gram");
        units.add("Liter");
        units.add("Milliliter ");
        units.add("Kilogram");
    }

    public Inventory getSlectedStock() {
        return selectedStock;
    }

    public void setSlectedStock(Inventory slectedStock) {
        this.selectedStock = slectedStock;
    }

    public void onRowSelect(SelectEvent e) {
        selectedStock = (Inventory) e.getObject();
    }

    public void onRowUnselect(UnselectEvent e) {
        selectedStock = null;
    }

    public List<Inventory> getInventory() {
        inventory = new InventorytableConneection().getStockItems();
        return inventory;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }

    public void addStockItem() {
        MrKaplan validate = new MrKaplan();
        TheEqualizer eqi = new TheEqualizer();
        if (validate.isValidInput(getDescription())) {
            stockItem = new Inventory();
            stockItem.setDescription(eqi.toUperAndLower(getDescription()));
            if (getQuantity() > getLowThresh()) {
                stockItem.setQuantity(getQuantity());
                stockItem.setLowThresh(getLowThresh());
                stockItem.setExpireyDate(getExpireyDate());
                stockItem.setUnit(getUnit());
                new InventorytableConneection().addStockItem(stockItem);
            } else {
                feedback.error("Quantity must be greater than threshhold", "");
            }
        }
    }

    public void loadStockItem() {
        if (selectedStock != null) {
            setDescription(selectedStock.getDescription());
            setQuantity(selectedStock.getQuantity());
            setLowThresh(selectedStock.getLowThresh());
            setExpireyDate(selectedStock.getExpireyDate());
             System.out.println(getUnit()+"====================================================");
            setUnit(selectedStock.getUnit());
        }
    }

    public void edittockItem() {
        MrKaplan validate = new MrKaplan();
        TheEqualizer eqi = new TheEqualizer();
        if (validate.isValidInput(getDescription())) {
            stockItem = new Inventory();
            stockItem.setDescription(eqi.toUperAndLower(getDescription()));
            if (getQuantity() > getLowThresh()) {
                stockItem.setId(selectedStock.getId());
                stockItem.setQuantity(getQuantity());
                stockItem.setLowThresh(getLowThresh());
                stockItem.setExpireyDate(getExpireyDate());
                stockItem.setUnit(getUnit());
                new InventorytableConneection().updateStockItem(stockItem);
            } else {
                feedback.error("Quantity must be greater than threshhold", "");
            }
        }

    }

    public void deleteStockItem() {
        if (selectedStock != null) {
            try {
                new InventorytableConneection().deleteStockItem(selectedStock.getId());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InventoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
