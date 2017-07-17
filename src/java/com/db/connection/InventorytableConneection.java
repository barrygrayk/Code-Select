package com.db.connection;

import com.InventoryModels.Inventory;
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

    private final MenuView feedback = new MenuView();

    public InventorytableConneection() {
        super();
    }

    @Override
    boolean recordValidator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addStockItem(Inventory stock) {
        String insertSql = "INSERT INTO `inventory`(`Description`,`Quantity`,`lowThreshold`,`expireyDate`,`units`) VALUES(?,?,?,?,?)";
        try {
            setInventoryColumns(insertSql, stock, false).execute();
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
                stock.setUnit( resultset.getString("units"));
                stock.setDaysLeft();
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

    public void updateStockItem(Inventory stock) {
        String updateSql = "UPDATE `inventory` SET Description=?,Quantity=?,lowThreshold=?,expireyDate=?,units=? WHERE idInventory=?";
        try {
            setInventoryColumns(updateSql, stock, true).execute();
            System.out.println("in db " + stock.getDescription());
            //connection.commit();
            feedback.addMessage("Sucess", "Item sucessfully added");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InventorytableConneection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
                feedback.error("Connection error", ex.getMessage());
            }
        }

    }

    public PreparedStatement setInventoryColumns(String query, Inventory stock, boolean update) throws ClassNotFoundException, SQLException {
        PreparedStatement insertStock = null;
        connection = getConnection();
        insertStock = connection.prepareStatement(query);
        insertStock.setString(1, stock.getDescription());
        insertStock.setDouble(2, stock.getQuantity());
        insertStock.setDouble(3, stock.getLowThresh());
        Date date = new Date();
        date = stock.getExpireyDate();
        java.util.Date utilStartDate = date;
        java.sql.Date sqlExpiryDate = new java.sql.Date(utilStartDate.getTime());
        insertStock.setDate(4, sqlExpiryDate);
        insertStock.setString(5, stock.getUnit());
        if (update) {
            insertStock.setInt(6, stock.getId());
            System.out.println("ID at update__ " + stock.getId());
        }

        return insertStock;
    }

    public void deleteStockItem(int id) throws ClassNotFoundException {
        connection = getConnection();
        PreparedStatement deleteRecord = null;
        String deleteItem = " DELETE FROM  `inventory` where  idInventory=?";
        try {
            deleteRecord = connection.prepareStatement(deleteItem);
            deleteRecord.setInt(1, id);
            deleteRecord.execute();
            feedback.addMessage("Sucess", "Record has been deleted");
        } catch (SQLException ex) {
            Logger.getLogger(StaffTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            feedback.error("Delete error", ex.getMessage());
        } finally {
            try {
                connection.close();
                deleteRecord.close();
            } catch (SQLException ex) {
                feedback.error("Connection error", ex.getMessage());
                MenuView out = new MenuView();
                out.error("Detele error", "No staff member has been selected");
            }
        }

    }

}
