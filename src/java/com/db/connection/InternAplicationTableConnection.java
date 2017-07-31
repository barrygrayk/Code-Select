package com.db.connection;

import com.MenuView.MenuView;
import com.applicants.Model.Applicant;
import com.staff.Model.Authenticate;
import com.validation.AuthTokens;
import com.validation.JJWT;
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
public class InternAplicationTableConnection extends DatabaseConnection {

    private final MenuView feedback = new MenuView();

    public void sendApllicationRequest(Applicant application) {
        String requestQuery = "INSERT INTO `applicant` (`firstname`,`lastname`,`phoneNumber`,"
                + "`emailAddress`,`applicationStatus`,`message`)VALUES (?,?,?,?,?,?)";
        try {
            setApplicationRequestColumns(requestQuery, application).execute();
            feedback.addMessage("Success", "Your application request has been sucessfully sent. "
                    + "The success of this application will be communicated via your eamil. Thank you.");
        } catch (SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                feedback.error("Connection error", ex.getMessage());
            }
        }

    }

    private PreparedStatement setApplicationRequestColumns(String requstQuery, Applicant application) {
        PreparedStatement insertAppRequst = null;
        try {
            connection = getConnection();
            insertAppRequst = connection.prepareStatement(requstQuery);
            insertAppRequst.setString(1, application.getFirstname());
            insertAppRequst.setString(2, application.getLastname());
            insertAppRequst.setString(3, application.getPhoneNumber());
            insertAppRequst.setString(4, application.getEmailAddress());
            insertAppRequst.setString(5, "Request pending");
            insertAppRequst.setString(6, application.getMotivationForApllication());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return insertAppRequst;
    }

    public List<Applicant> getApplicants() {
        List<Applicant> applicants = new ArrayList<>();
        try {
            String query = "SELECT * FROM `applicant`";
            connection = getConnection();
            statement = connection.createStatement();
            resultset = statement.executeQuery(query);
            while (resultset.next()) {
                Applicant applicant = new Applicant();
                applicant.setId(resultset.getInt("idApplicant"));
                applicant.setFirstname(resultset.getString("firstname"));
                applicant.setLastname(resultset.getString("lastname"));
                applicant.setPhoneNumber(resultset.getString("phoneNumber"));
                applicant.setEmailAddress(resultset.getString("emailAddress"));
                applicant.setMotivationForApllication(resultset.getString("message"));
                if (resultset.getString("applicationStatus").equals("Request pending")) {
                    applicant.setApplicationStatus(resultset.getString("applicationStatus"));
                    applicants.add(applicant);
                }
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
        return applicants;
    }

    public void sendAcceRequest(Applicant applicant) {
        AuthTokens jwt = new JJWT();
        String token = jwt.creatJWt(applicant.getId() + "", "OthantileWebApplication", applicant.getEmailAddress(), 0L);
        String body = "Dear " + applicant.getFirstname() + " " + applicant.getLastname() + ", " + "\n" + "\nWelcome to Othantile Childrens Ministries. "
                + "Your account has been"
                + " sucessfully created. Your account details are as follows:\n" + "\n"
                + "User name: " + applicant.getEmailAddress() + "\n"
                + "\nFollow the link below to activate your accout.\n"
                + "\nhttp://localhost:8080/OnthatileWebApplication/faces/register.xhtml?token=" + token;
        //http://localhost:8080/OnthatileWebApplication/faces/register.xhtml
        //http://onthatilewebapplication.j.layershift.co.uk/faces/register.xhtml
        new StaffTableConnection().sendEmailToStaff(applicant.getEmailAddress(), "Intern portal activation", body);
        feedback.addMessage("Sucess", "Acceptance email has been sucessfully sent to " + applicant.getFirstname());
    }

    public void setApplicantAuthDetails(Authenticate auth) {
        try {
            /* INSERT INTO `applicantLogin`(`username`,`passwordSalt`,`passwordHash`,`status`,`idApplicant`,`token`)
            VALUES()*/
            connection = getConnection();
            String insertAuthfQuery = "INSERT INTO `applicantLogin`(`username`,`passwordSalt`,`passwordHash`,`status`,`idApplicant`,`token`)\n"
                    + "VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = null;
            ps = connection.prepareStatement(insertAuthfQuery);
            ps.setString(1, auth.getUsername());
            ps.setBytes(2, auth.getSalt());
            ps.setString(3, auth.getHashedPassword());
            ps.setString(4, auth.getStatus());
            ps.setInt(5, auth.authId());
            ps.setString(6, auth.getToken());
            ps.execute();
            PreparedStatement changeAppStatus = connection.prepareStatement("UPDATE applicant SET applicationStatus=? WHERE idApplicant =?");
            changeAppStatus.setString(1, "Application pending");
            changeAppStatus.setInt(2, auth.authId());
            changeAppStatus.execute();
            feedback.addMessage(auth.getUsername(), "Account has been sucessfully activated");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    boolean recordValidator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
