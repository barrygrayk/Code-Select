package com.db.connection;

import InventoryModels.Inventory;
import com.MenuView.MenuView;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Barry Gray
 */
public class InventorytableConneection extends DatabaseConnection {

    private MenuView feedback = new MenuView();

    public InventorytableConneection() {
        super();
    }

    @Override
    boolean recordValidator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addStockItem(Inventory stock) {
        PreparedStatement insertStock = null;
        String insertSql = "INSERT INTO `inventory`(`Description`,`Quantity`,`lowThreshold`,`expireyDate`) VALUES(?,?,?,?)";
        try {
            connection = getConnection();
            insertStock = connection.prepareStatement(insertSql);
            insertStock.setString(1, stock.getDescription());
            insertStock.setDouble(2, stock.getQuantity());
            insertStock.setDouble(3, stock.getLowThresh());
            Date date = new Date();
            date = stock.getExpireyDate();
            java.util.Date utilStartDate = date;
            java.sql.Date sqlExpiryDate = new java.sql.Date(utilStartDate.getTime());
            insertStock.setDate(4, sqlExpiryDate);
            insertStock.execute();
            feedback.addMessage("Sucess", "Item sucessfully added");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InventorytableConneection.class.getName()).log(Level.SEVERE, null, ex);
            feedback.error("database error", ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                feedback.error("Connection error", ex.getMessage());
            }
        }
    }

    public List<Inventory> getStockItems() {
        List<Inventory> stockItems = new ArrayList<>();
        try {
            String query = "SELECT * FROM `inventory`";
            connection = getConnection();
            statement = connection.createStatement();
            resultset = statement.executeQuery(query);
            while (resultset.next()) {
                Inventory stock = new Inventory(resultset.getInt("idInventory"),
                        resultset.getString("Description"),
                        resultset.getDouble("Quantity"),
                        resultset.getDouble("lowThreshold"),
                        resultset.getDate("expireyDate"));
                stockItems.add(stock);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InventorytableConneection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                resultset.close();
                connection.close();
            } catch (SQLException ex) {
                feedback.error("Connection error", ex.getMessage());
            }
        }
        return stockItems;
    }

}
