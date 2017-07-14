package com.db.connection;

import com.Donations.Donations;
import com.MenuView.MenuView;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Barry Gray Kapelembe
 */
public class DonationsConnection extends DatabaseConnection { 

    private final MenuView feedback = new MenuView();

    public DonationsConnection() {
        super();
    }

    @Override
    boolean recordValidator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addDonation(Donations donate) {
        String insertSql = "INSERT INTO .`needs`(`needDescription`,`goal`,`donated`)VALUES(?,?,?);";
        try {
            setDonatiosClumns(insertSql, donate, false).execute();
            feedback.addMessage("Sucess", "Donation sucessfully added");
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

    public List<Donations> getDonationItems() {
        List<Donations> donated = new ArrayList<>();
        try {
            String query = "SELECT * FROM `needs`";
            connection = getConnection();
            statement = connection.createStatement();
            resultset = statement.executeQuery(query);
            while (resultset.next()) {
                Donations donatedItems = new Donations(resultset.getInt("idNeeds"),
                        resultset.getString("needDescription"),
                        resultset.getDouble("goal"),
                        resultset.getDouble("donated"));
                System.out.println("Getting "+donatedItems.getDescription());
                donated.add(donatedItems);
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
        return donated;
    }

    public void updateDonationItem(Donations donate) {
        String updateSql = "UPDATE `needs` SET needDescription=?, goal=?,donated=? WHERE idNeeds =?";
        try {
            setDonatiosClumns(updateSql, donate, true).execute();
            feedback.addMessage("Sucess", "Item sucessfully added");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InventorytableConneection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public PreparedStatement setDonatiosClumns(String query, Donations donate, boolean update) throws ClassNotFoundException, SQLException {
        PreparedStatement insertStock = null;
        connection = getConnection();
        insertStock = connection.prepareStatement(query);
        insertStock.setString(1, donate.getDescription());
        System.out.println("in db---"+donate.getDescription());
        insertStock.setDouble(2, donate.getGoal());
        insertStock.setDouble(3, donate.getRecieved());
        if (update) {
            insertStock.setInt(4, donate.getId());
        }

        return insertStock;
    }

    public void deleteDonationItem(int id) throws ClassNotFoundException {
        connection = getConnection();
        PreparedStatement deleteRecord = null;
        String deleteItem = " DELETE FROM  `needs` where idNeeds =?";
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
