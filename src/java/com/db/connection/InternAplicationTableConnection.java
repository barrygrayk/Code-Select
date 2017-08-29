package com.db.connection;

import com.MenuView.MenuView;
import com.applicants.Model.Applicant;
import com.staff.Model.Authenticate;
import com.staff.Model.Authentication;
import com.validation.AuthTokens;
import com.validation.JJWT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public InternAplicationTableConnection() {
        super();
    }

    public void sendApllicationRequest(Applicant application) {
        String requestQuery = "INSERT INTO `applicant` (`firstname`,`lastname`,`phoneNumber`,"
                + "`emailAddress`,`applicationStatus`,`message`)VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement insertApplicant = setApplicationRequestColumns(requestQuery, application);
            int fetchFK = insertApplicant.executeUpdate();
            if (fetchFK == 1) {
                int fKey = 0;
                ResultSet key = insertApplicant.getGeneratedKeys();
                if (key.next()) {
                    fKey = key.getInt(1);
                    setApplicantTablesFK(fKey);
                }

            }
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
            insertAppRequst = connection.prepareStatement(requstQuery, Statement.RETURN_GENERATED_KEYS);
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

    public List<Applicant> getApplicant(int id) throws ClassNotFoundException, SQLException {
        //getResultSet(id, "SELECT * FROM `applicant` WHERE idApplicant=?");
        List<Applicant> applicants = new ArrayList<>();
        try {
            String query = "SELECT * FROM applicant WHERE idApplicant=?";
            getResultSet(id, query);
            if (resultset.next()) {
                Applicant applicant = new Applicant();
                applicant.setId(resultset.getInt("idApplicant"));
                applicant.setFirstname(resultset.getString("firstname"));
                applicant.setLastname(resultset.getString("lastname"));
                applicant.setPhoneNumber(resultset.getString("phoneNumber"));
                applicant.setEmailAddress(resultset.getString("emailAddress"));
                applicant.setMotivationForApllication(resultset.getString("message"));
                applicants.add(applicant);

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

    public List<Authenticate> getAuthenticatedApplicant() throws ClassNotFoundException, SQLException {
        List<Authenticate> authenticatedStaff = new ArrayList<>();
        String authQuery = "SELECT * FROM `applicantLogin`";
        getResultSet(authQuery);
        while (resultset.next()) {
            Authenticate auth = new Authentication();
            auth.setAuthId(resultset.getInt("idApplicant"));
            auth.setUsername(resultset.getString("userName"));
            auth.setSalt(resultset.getBytes("passwordSalt"));
            auth.sethashPassword(resultset.getString("passwordHash"));
            auth.setStatus(getAccountStatus(resultset.getString("status")));
            auth.setToken(resultset.getString("token"));
            authenticatedStaff.add(auth);
        }
        return authenticatedStaff;
    }

    private int getAccountStatus(String status) {
        int statusIndex = 4;
        switch (status) {
            case "Pending activaton":
                statusIndex = 0;
                break;
            case "Pending reset":
                statusIndex = 1;
                break;
            case "Active":
                statusIndex = 2;
                break;
            case "Deactivated":
                statusIndex = 3;
                break;
        }
        return statusIndex;
    }

    public String getFullname(int id) {
        String fullnameQuery = "SELECT `firstname`,`lastname` FROM `applicant`  WHERE idApplicant =?";
        return getFullname(id, fullnameQuery);
    }

    /*
    * Method initializes all applicant related tables with fk to facilitate
    * Update query and auto saving
     */
    public void setApplicantTablesFK(int fk) {
        try {
            PreparedStatement insertEdu = connection.prepareStatement("INSERT INTO applicanteducationqualification(`Applicant_idApplicant`) VALUES(?)");
            insertEdu.setInt(1, fk);
            insertEdu.execute();
            PreparedStatement insertEmeCont = connection.prepareStatement("INSERT INTO applicantemergencycontact(`Applicant_idApplicant`) VALUES(?)");
            insertEmeCont.setInt(1, fk);
            insertEmeCont.execute();
            PreparedStatement insertInternInfo = connection.prepareStatement("INSERT INTO applicantInternShipInfo (`applicantId`) VALUES(?)");
            insertInternInfo.setInt(1, fk);
            insertInternInfo.execute();
            PreparedStatement insertHist = connection.prepareStatement("INSERT INTO applicantmedicalhistory (`Applicant_idApplicant`)VALUES(?)");
            insertHist.setInt(1, fk);
            insertHist.execute();
            PreparedStatement insertSpiLife = connection.prepareStatement("INSERT INTO applicantspirituallife (`Applicant_idApplicant`) VALUES (?)");
            insertSpiLife.setInt(1, fk);
            insertSpiLife.execute();
            PreparedStatement insertWordExp = connection.prepareStatement("INSERT INTO applicantworkexperience (`Applicant_idApplicant`) VALUES(?)");
            insertWordExp.setInt(1, fk);
            insertWordExp.execute();
        } catch (SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateInternInfo(Applicant applicant) {
        try {
            connection = getConnection();
            String updateAuthfQuery = "UPDATE applicantInternShipInfo SET  `startdate` = ?,`enddate` = ?,`heardFrom` = ?,`goalForInternship` = ? WHERE `applicantId` = ?";
            PreparedStatement ps = null;
            ps = connection.prepareStatement(updateAuthfQuery);
            ps.setDate(1, toSqlDate(applicant.getInternshipInfo().getStartDate()));
            ps.setDate(2, toSqlDate(applicant.getInternshipInfo().getEndDate()));
            ps.setString(3, applicant.getInternshipInfo().getHowUHeard());
            ps.setString(4, applicant.getInternshipInfo().getInternshipGoal());
            ps.setInt(5, applicant.getId());
            ps.execute();
            System.out.println("------update ex---------");
            feedback.addMessage("Success", "Your internship Information has been saved");

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /*UPDATE `onthatile children's ministries`.`applicantInternShipInfo` SET  `startdate` = ?,`enddate` = ?,`heardFrom` = ?, `applicantInternShipInfocol` = ? ,`goalForInternship` = ? WHERE `Applicant_idApplicant` = ?;*/

 /*UPDATE `onthatile children's ministries`.`applicantInternShipInfo` SET  `startdate` = ?,`enddate` = ?,`heardFrom` = ?, `applicantInternShipInfocol` = ? ,`goalForInternship` = ? WHERE `Applicant_idApplicant` = ?;*/
    }

    /*UPDATE  applicant SET `idApplicant` = ?,`firstname` = ?,`lastname` = ?,`prename` = ?,`dateOfBirth` = ?,`phoneNumber` = ?,`emailAddress` = ?,`citystate` = ?,`streetAddress` = ?,`country` = ?,`zipCode` = ?,`gender` = ?,`maritalStatus` = ?,`applicationStatus` = ?,`message` = ?WHERE `idApplicant` = ?;
     */

    public void updateGenralInfo(Applicant applicant) {
        System.out.println("------in 1---------");
        try {
            connection = getConnection();
            String updateAuthfQuery = "UPDATE  applicant SET `firstname` = ?,`lastname` = ?,`prename` = ?,`dateOfBirth` = ?,`phoneNumber` = ?,`emailAddress` = ?,`citystate` = ?,"
                    + "`streetAddress` = ?,`country` = ?,`zipCode` = ?,`gender` = ?,`maritalStatus` = ?"
                    + " WHERE `idApplicant` = ?";
            PreparedStatement ps = null;
               System.out.println("------in 2---------");
            ps = connection.prepareStatement(updateAuthfQuery);
            ps.setString(1, applicant.getFirstname());
            ps.setString(2, applicant.getLastname());
            ps.setString(3, applicant.getPrename());
            ps.setDate(4, toSqlDate(applicant.getDateOfBirth()));
            ps.setString(5, applicant.getPhoneNumbe());
            ps.setString(6, applicant.getEmailAddress());
            ps.setString(7, applicant.getCity());
            ps.setString(8, applicant.getAddress());
            ps.setString(9, applicant.getCountry());
            ps.setString(10, applicant.getZipCode());
            String g = Character.toString(applicant.getGender());
            ps.setString(11, g);
            ps.setString(12, applicant.getMaritalStatus());
            ps.setInt(13, applicant.getId());
            ps.execute();
            System.out.println("------update ex complete---------");
            feedback.addMessage("Success", "Your General Information has been saved");

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /*UPDATE `onthatile children's ministries`.`applicantInternShipInfo` SET  `startdate` = ?,`enddate` = ?,`heardFrom` = ?, `applicantInternShipInfocol` = ? ,`goalForInternship` = ? WHERE `Applicant_idApplicant` = ?;*/

 /*UPDATE `onthatile children's ministries`.`applicantInternShipInfo` SET  `startdate` = ?,`enddate` = ?,`heardFrom` = ?, `applicantInternShipInfocol` = ? ,`goalForInternship` = ? WHERE `Applicant_idApplicant` = ?;*/
    }


    @Override
    boolean recordValidator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
/*INSERT INTO applicanteducationqualification
(`idapplicanteducationqualification`,
`highestQual`,
`dateOfGraduation`,
`specialCertification`,
`strenghts`,
`weakness`,
`Applicant_idApplicant`)
VALUES
();
 */
 /*INSERT INTO applicantemergencycontact
(`idApplicantEmergencyContact`,
`firstname`,
`lastname`,
`relationship`,
`phoneNumber`,
`emailAddress`,
`Applicant_idApplicant`)
VALUES
();
 */

 /*INSERT INTO `onthatile children's ministries`.`applicantInternShipInfo`
(`idapplicantInternShipInfo`,
`startdate`,
`enddate`,
`heardFrom`,
`applicantInternShipInfocol`,
`goalForInternship`,
`applicantId`)
VALUES
();
 */
 /*INSERT INTO applicantmedicalhistory
(`idApplicantMedicalHistory`,
`anyMedicalConditions`,
`medicalConditionDesc`,
`anyPastMedicalCondition`,
`pastMedicalConditionDesc`,
`diataryRestrictions`,
`dietaryRestrictionsDesc`,
`physicalHandicap`,
`handicapDesc`,
`Applicant_idApplicant`)
VALUES
();
 */
 /*INSERT INTO applicantspirituallife
(`idapplicantspirituallife`,
`beliefInJesus`,
`nameOfChurch`,
`testimony`,
`Applicant_idApplicant`)
VALUES
();

 */
 /*INSERT INTO applicantworkexperience
(`idapplicantworkexperience`,
`nameOfEmployer`,
`jobTitle`,
`jobLength`,
`reasonForLeaving`,
`Applicant_idApplicant`)
VALUES
();
 */
