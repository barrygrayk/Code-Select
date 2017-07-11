
package com.controller;

import InventoryModels.Inventory;
import InventoryModels.Stock;
import com.db.connection.InventorytableConneection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 * 
 * @author Barry Gray
 */
@ManagedBean(name = "InventoryCont", eager = true)
@SessionScoped
public class InventoryController extends Inventory implements Serializable{
    private List <Stock> inventory = new ArrayList <>();
    private Stock slectedStock;
    private Stock stockItem;

    public InventoryController() {
        super();
    }
    

    public Stock getSlectedStock() {
        return slectedStock;
    }

    public void setSlectedStock(Stock slectedStock) {
        this.slectedStock = slectedStock;
    }
    

    public List<Stock> getInventory() {
        return inventory;
    }

    public void setInventory(List<Stock> inventory) {
        this.inventory = inventory;
    }
    
    
    public void addStockItem (){
        //perform validationwoith mr kaplin
        stockItem.setDescription(getDescription());
        stockItem.setQuantity(getQuantity());
        stockItem.setLowThreshold(getLowThreshold());
        stockItem.setExpirryDate(getExpiraryDate());
        new InventorytableConneection().addStockItem(stockItem);
        
        
    }
    
    
    

}
