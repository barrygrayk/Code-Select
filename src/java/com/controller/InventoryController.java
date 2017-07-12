package com.controller;

import InventoryModels.Inventory;
import com.MenuView.MenuView;
import com.db.connection.InventorytableConneection;
import com.validation.MrKaplan;
import com.validation.TheEqualizer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Barry Gray
 */
@ManagedBean(name = "InventoryCont", eager = true)
@SessionScoped
public class InventoryController extends Inventory implements Serializable {

    private final MenuView feedback = new MenuView();
    private List<Inventory> inventory = new ArrayList<>();
    private Inventory selectedStock;
    private Inventory stockItem;

    public InventoryController() {
        super();
    }

    @PostConstruct
    public void init() {
        getInventory();
    }

    public Inventory getSlectedStock() {
        return selectedStock;
    }

    public void setSlectedStock(Inventory slectedStock) {
        System.out.println("_________________________________________________");
        this.selectedStock = slectedStock;
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
                new InventorytableConneection().addStockItem(stockItem);
            } else {
                feedback.error("Quantity must be greater than threshhold","");
            }
        }
    }

}
