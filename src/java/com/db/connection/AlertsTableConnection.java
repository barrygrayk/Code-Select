package com.db.connection;

import com.MenuView.MenuView;
import com.Messages.InventoryAlerts;
import com.staff.Model.Message;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Barry Gray
 */
public class AlertsTableConnection extends DatabaseConnection {

    private final MenuView feedback = new MenuView();

    public List<InventoryAlerts> getInventoryAlerts() {
        List<InventoryAlerts> alerts = new ArrayList<>();
        try {
            String query = "SELECT * FROM `inventoryalerts`";
            connection = getConnection();
            statement = connection.createStatement();
            resultset = statement.executeQuery(query);
            while (resultset.next()) {
                //idinventoryAlerts, inventoryItem, daysTillExpirey, alertDescription, Inventory_idInventory
                InventoryAlerts alert = new InventoryAlerts(
                        resultset.getInt("Inventory_idInventory"),
                        resultset.getString("inventoryItem"),
                        resultset.getString("alertDescription"),
                        resultset.getInt("daysTillExpirey")
                );
                alerts.add(alert);
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
        return alerts;
    }

    public void deleteStockItem(int id) throws ClassNotFoundException {
        connection = getConnection();
        PreparedStatement deleteRecord = null;
        String deleteItem = " DELETE FROM  `inventoryalerts` where  Inventory_idInventory=?";
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
    

    @Override
    boolean recordValidator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void sendMessage (Message msg){
        String insertMsgQeury = "INSERT INTO .`message`(,`body`,`onthantilestaff_staffID`,`subject`)VALUES(?,?,?);";
        PreparedStatement sendMessage= null;
        try {
            connection = getConnection();
            sendMessage = connection.prepareStatement(insertMsgQeury);
            sendMessage.setString(1,msg.getDescription());
            sendMessage.setInt(2,msg.getId());
            sendMessage.setString(3, msg.getSubject());
            sendMessage.execute();
            feedback.addMessage("Sucess", "Message sent");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlertsTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }

}
