package com.db.connection;

import InventoryModels.Stock;
import com.MenuView.MenuView;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Barry Gray
 */
public class InventorytableConneection extends DatabaseConnection {
    private MenuView feedback = new MenuView ();

    public InventorytableConneection() {
        super();
    }

    @Override
    boolean recordValidator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addStockItem(Stock stock) {
        PreparedStatement insertStock = null;
        String insertSql = "INSERT INTO `inventory`(`Description`,`Quantity`,`lowThreshold`,`expireyDate`) VALUES(?,?,?,?,?)";
        try {
            connection = getConnection();
            insertStock = connection.prepareStatement(insertSql);
            insertStock.setString(1, stock.getDescription());
            insertStock.setDouble(2, stock.getQuantity());
            insertStock.setDouble(3, stock.getLowThreshold());
            Date date = new Date();
            date = stock.getExpiraryDate();
            java.util.Date utilStartDate = date;
            java.sql.Date sqlExpiryDate = new java.sql.Date(utilStartDate.getTime());
            insertStock.setDate(4,sqlExpiryDate);
            insertStock.execute();
            feedback.addMessage("Sucess", "Itm sucessfully added");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InventorytableConneection.class.getName()).log(Level.SEVERE, null, ex);
            feedback.error("database error", ex.getMessage());
        }

    }

}
