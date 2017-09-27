package com.db.connection;

import com.MenuView.MenuView;
import com.applicants.Model.Applicant;
import com.applicants.Model.ApplicationProgress;
import com.applicants.Model.EducationAndQualification;
import com.applicants.Model.EmergencyContact;
import com.applicants.Model.InternshipInfo;
import com.applicants.Model.LegalHistory;
import com.applicants.Model.MediaclHistory;
import com.applicants.Model.PersonanlityTraits;
import com.applicants.Model.SpiritualLife;
import com.applicants.Model.TermAndConditions;
import com.applicants.Model.WorkExperience;
import com.controller.InternApplicationController;
import com.staff.Model.Authenticate;
import com.staff.Model.Authentication;
import com.validation.AuthTokens;
import com.validation.JJWT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
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

    public List<Applicant> getApplicants(String status) {
        List<Applicant> applicants = new ArrayList<>();
        try {
            String query = "SELECT * FROM `applicant`";
            connection = getConnection();
            statement = connection.createStatement();
            resultset = statement.executeQuery(query);
            while (resultset.next()) {
                if (resultset.getString("applicationStatus").equals(status)) {
                    Applicant applicant = new Applicant();
                    applicant.setId(resultset.getInt("idApplicant"));
                    applicant.setFirstname(resultset.getString("firstname"));
                    applicant.setLastname(resultset.getString("lastname"));
                    applicant.setPhoneNumber(resultset.getString("phoneNumber"));
                    applicant.setEmailAddress(resultset.getString("emailAddress"));
                    applicant.setMotivationForApllication(resultset.getString("message"));
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

    public Applicant getApplicant(int id) throws ClassNotFoundException, SQLException {
        Applicant applicant = new Applicant();
        try {
            String query = "SELECT * FROM applicant WHERE idApplicant=?";
            getResultSet(id, query);
            if (resultset.next()) {
                applicant.setId(resultset.getInt("idApplicant"));
                applicant.setFirstname(resultset.getString("firstname"));
                applicant.setLastname(resultset.getString("lastname"));
                applicant.setPrename(resultset.getString("prename"));
                applicant.setDateOfBirth(resultset.getDate("dateOfBirth"));
                applicant.setPhoneNumber(resultset.getString("phoneNumber"));
                applicant.setEmailAddress(resultset.getString("emailAddress"));
                applicant.setCity(resultset.getString("citystate"));
                applicant.setAddress(resultset.getString("streetAddress"));
                applicant.setCountry(resultset.getString("country"));
                applicant.setZipCode(resultset.getString("zipCode"));
                applicant.setGender(resultset.getString("gender").charAt(0));
                applicant.setMaritalStatus(resultset.getString("maritalStatus"));
                applicant.setMotivationForApllication(resultset.getString("message"));
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
        return applicant;
    }

    public InternshipInfo getApplicantionInfo(int id) {
        InternshipInfo info = new InternshipInfo();
        try {
            String query = "SELECT * FROM applicantInternShipInfo WHERE applicantId=?";
            getResultSet(id, query);
            if (resultset.next()) {
                info.setStartDate(resultset.getDate("startdate"));
                info.setEndDate(resultset.getDate("enddate"));
                info.setHowUHeard(resultset.getString("heardFrom"));
                info.setAreDayFlex(resultset.getString("aredaysflex"));
                info.setInternshipGoal(resultset.getString("goalForInternship"));
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
        return info;
    }

    public SpiritualLife getSperitualLife(int id) {
        SpiritualLife life = new SpiritualLife();
        try {
            String query = "SELECT * FROM applicantspirituallife WHERE Applicant_idApplicant=?";
            getResultSet(id, query);
            if (resultset.next()) {
                life.setAttend(resultset.getString("attendChurch"));
                life.setWhichChurch(resultset.getString("whichChurch"));
                life.setAttendDuration(resultset.getDouble("attendDuration"));
                String ministriesList = resultset.getString("ministrisIn");
                if (ministriesList != null && ministriesList.length() > 1) {
                    life.setListOFMinistries(new ArrayList<>(Arrays.asList(ministriesList.split(","))));
                }
                life.setCommitedJesus(resultset.getString("commitdJesus"));
                life.setCommitedTest(resultset.getString("commitedTest"));
                life.setViewBefSaved(resultset.getString("viewBefSaved"));
                life.setHearGospel(resultset.getString("hearGospel"));
                life.setGospelMess(resultset.getString("gospelMess"));
                life.setBackGround(resultset.getString("background"));
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
        return life;
    }

    public PersonanlityTraits getersonalityTraits(int id) {
        PersonanlityTraits allTraits = new PersonanlityTraits();
        try {
            String query = "SELECT * FROM applicantPersonalityTraits WHERE applicantID=?";
            getResultSet(id, query);
            if (resultset.next()) {
                String traits = resultset.getString("traits");
                if (traits != null && traits.length() > 1) {
                    allTraits.setSelectedTraits(new ArrayList<>(Arrays.asList(traits.split(" "))));
                }
                allTraits.setReasonForWeekness(resultset.getString("WeeknessReason"));
                allTraits.setResonForStrength(resultset.getString("strenghtReason"));
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
        return allTraits;
    }

    public LegalHistory getLegalHistory(int id) throws ClassNotFoundException, SQLException {
        LegalHistory history = new LegalHistory();
        try {
            String query = "SELECT * FROM applicantLegalHistory WHERE applicantLegLid=?";
            getResultSet(id, query);
            if (resultset.next()) {
                history.setArrested(resultset.getString("offense"));
                history.setConvicedCrime(resultset.getString("convictated"));
                history.setSexualMisCond(resultset.getString("missconduct"));
                history.setGuitlyToSexualMisCond(resultset.getString("guiltyMisconduct"));
                history.setDrugsNotPresc(resultset.getString("drugs"));
                history.setTobaco(resultset.getString("tobacco"));
                history.setWeed(resultset.getString("weed"));
                history.setAlcohol(resultset.getString("alcohol"));
                history.setReason(resultset.getString("reason"));
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
        return history;
    }

    public EducationAndQualification getEducationqualification(int id) throws ClassNotFoundException, SQLException {
        EducationAndQualification edu = new EducationAndQualification();
        try {
            String query = "SELECT * FROM applicanteducationqualification WHERE Applicant_idApplicant=?";
            getResultSet(id, query);
            if (resultset.next()) {
                edu.setHighestQualification(resultset.getString("highestQual"));
                edu.setHighestGraduationDate(resultset.getDate("dateOfGraduation"));
                edu.setSpecialQualification(resultset.getString("specialCertification"));
                edu.setSpecialGraduationDate(resultset.getDate("specialGraduationDate"));
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
        return edu;
    }

    public MediaclHistory getMedicalhistory(int id) throws ClassNotFoundException, SQLException {
        MediaclHistory med = new MediaclHistory();
        try {
            String query = "SELECT * FROM applicantmedicalhistory WHERE Applicant_idApplicant=?";
            getResultSet(id, query);
            if (resultset.next()) {
                med.setConditions(resultset.getString("anyMedicalConditions"));
                med.setConditionExplanation(resultset.getString("medicalConditionDesc"));
                med.setMedications(resultset.getString("anyMedication"));
                med.setMedicationsExplanation(resultset.getString("medicationDec"));
                med.setSeriousIllness(resultset.getString("anyPastMedicalCondition"));
                med.setSeriousIllnessExplanation(resultset.getString("pastMedicalConditionDesc"));
                med.setRestrictions(resultset.getString("diataryRestrictions"));
                med.setRestrictionsExplanation(resultset.getString("dietaryRestrictionsDesc"));
                med.setPhysicalHandicap(resultset.getString("physicalHandicap"));
                med.setPhysicalHandicapExplanation(resultset.getString("handicapDesc"));
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
        return med;
    }

    public EmergencyContact getEmergencycontact(int id) throws ClassNotFoundException, SQLException {
        EmergencyContact emerg = new EmergencyContact();
        try {
            String query = "SELECT * FROM applicantemergencycontact WHERE Applicant_idApplicant=?";
            getResultSet(id, query);
            if (resultset.next()) {
                emerg.setFirstname(resultset.getString("firstname"));
                emerg.setLastname(resultset.getString("lastname"));
                emerg.setRelationship(resultset.getString("relationship"));
                emerg.setPhoneNumber(resultset.getString("phoneNumber"));
                emerg.setEmailAddress(resultset.getString("emailAddress"));
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
        return emerg;
    }

    public TermAndConditions getTermsAndConditions(int id) throws ClassNotFoundException, SQLException {
        TermAndConditions tsAndCs = new TermAndConditions();
        try {
            String query = "SELECT * FROM applicantTermsAndConditions WHERE appliacantid_fk=?";
            getResultSet(id, query);
            if (resultset.next()) {
                tsAndCs.setServantHearted(resultset.getString("servantHearted"));
                tsAndCs.setHonourChrist(resultset.getString("honorChrist"));
                tsAndCs.setRespectAuthority(resultset.getString("submitToAuthority"));
                tsAndCs.setRasieFunds(resultset.getString("raiseFunds"));
                tsAndCs.setReadP1P2(resultset.getString("readP1P2"));
                tsAndCs.setReadChecklist(resultset.getString("readChecklist"));
                tsAndCs.setPermissionUsePhotos(resultset.getString("permissionPhotos"));
                tsAndCs.setTerminateInternShip(resultset.getString("terminateInternship"));
                tsAndCs.setConfirmAccuracy(resultset.getString("filledAccuratly"));
                tsAndCs.setAwareOfCrime(resultset.getString("awareOfCrime"));
                tsAndCs.setSign(resultset.getString("sign"));
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
        return tsAndCs;
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
            case "Suspended":
                statusIndex = 4;
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
            PreparedStatement insertPersTraits = connection.prepareStatement("INSERT INTO applicantPersonalityTraits (`applicantID`) VALUES(?)");
            insertPersTraits.setInt(1, fk);
            insertPersTraits.execute();
            PreparedStatement insertLegalHist = connection.prepareStatement("INSERT INTO idapplicantLegalHistory (`applicantLegLid`) VALUES(?)");
            insertLegalHist.setInt(1, fk);
            insertLegalHist.execute();
            PreparedStatement insertLegalExp = connection.prepareStatement("INSERT INTO applicantExperience (`appicantID_Fk`) VALUES(?)");
            insertLegalExp.setInt(1, fk);
            insertLegalExp.execute();
            PreparedStatement insertProgress = connection.prepareStatement("INSERT INTO applicantprogress (`applicant_ID`) VALUES(?)");
            insertProgress.setInt(1, fk);
            insertProgress.execute();
            PreparedStatement inserRecDocs = connection.prepareStatement("INSERT INTO applicantRequiredDocuments (`applicant_Id`) VALUES(?)");
            inserRecDocs.setInt(1, fk);
            inserRecDocs.execute();
        } catch (SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void updateInternInfo(Applicant applicant) {
        try {
            connection = getConnection();
            String updateAuthfQuery = "UPDATE applicantInternShipInfo SET  `startdate` = ?,`enddate` = ?,"
                    + "`aredaysflex` = ?,`heardFrom` = ?,`goalForInternship` = ? WHERE `applicantId` = ?";
            PreparedStatement ps = null;
            ps = connection.prepareStatement(updateAuthfQuery);
            ps.setDate(1, toSqlDate(applicant.getInternshipInfo().getStartDate()));
            ps.setDate(2, toSqlDate(applicant.getInternshipInfo().getEndDate()));
            ps.setString(3, applicant.getInternshipInfo().getAreDayFlex());
            ps.setString(4, applicant.getInternshipInfo().getHowUHeard());
            ps.setString(5, applicant.getInternshipInfo().getInternshipGoal());
            ps.setInt(6, applicant.getId());
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
    }

    public void updateGenralInfo(Applicant applicant) {
        try {
            connection = getConnection();
            String updateAuthfQuery = "UPDATE  applicant SET `firstname` = ?,`lastname` = ?,`prename` = ?,"
                    + "`dateOfBirth` = ?,`phoneNumber` = ?,`emailAddress` = ?,`citystate` = ?,"
                    + "`streetAddress` = ?,`country` = ?,`zipCode` = ?,`gender` = ?,`maritalStatus` = ?"
                    + " WHERE `idApplicant` = ?";
            PreparedStatement ps = null;
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

    }

    public void updateSpiritualLife(Applicant applicant) {
        System.out.println("------in 1---------");
        try {
            connection = getConnection();
            String updateAuthfQuery = "UPDATE applicantspirituallife SET "
                    + " `attendChurch` = ?, `whichChurch` = ?, `attendDuration` = ?, `ministrisIn` = ?, "
                    + "`commitdJesus` = ?, `commitedTest` = ?, `viewBefSaved` = ?, `hearGospel` = ?, `gospelMess` = ?, `background` = ?  "
                    + "WHERE `Applicant_idApplicant` = ?";
            PreparedStatement ps = null;
            System.out.println("------in 2---------");
            ps = connection.prepareStatement(updateAuthfQuery);
            ps.setString(1, applicant.getBeliefs().getAttend());
            ps.setString(2, applicant.getBeliefs().getWhichChurch());
            ps.setDouble(3, applicant.getBeliefs().getAttendDuration());

            ps.setString(4, applicant.getBeliefs().getMinistriesIn());
            ps.setString(5, applicant.getBeliefs().getCommitedJesus());
            ps.setString(6, applicant.getBeliefs().getCommitedTest());
            ps.setString(7, applicant.getBeliefs().getViewBefSaved());
            ps.setString(8, applicant.getBeliefs().getHearGospel());
            ps.setString(9, applicant.getBeliefs().getGospelMess());
            ps.setString(10, applicant.getBeliefs().getBackGround());
            ps.setInt(11, applicant.getId());
            ps.execute();
            System.out.println("------update ex complete---------");
            feedback.addMessage("Success", "Your Spiritual Life has been saved");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updatePersonalityTraits(Applicant applicant) {
        try {
            connection = getConnection();
            String updateAuthfQuery = "UPDATE applicantPersonalityTraits  SET `traits` = ?, `WeeknessReason` = ?, `strenghtReason` = ?"
                    + " WHERE `applicantID` = ?";
            PreparedStatement ps = null;
            ps = connection.prepareStatement(updateAuthfQuery);
            ps.setString(1, applicant.getPersonalityTraits().getTraitsToString());
            ps.setString(2, applicant.getPersonalityTraits().getReasonForWeekness());
            ps.setString(3, applicant.getPersonalityTraits().getResonForStrength());
            ps.setInt(4, applicant.getId());
            ps.execute();
            System.out.println("------update ex---------");
            feedback.addMessage("Success", "Your Personality Traits has been saved");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateEducationQualification(Applicant applicant) {
        try {
            connection = getConnection();
            String updateAuthfQuery = "UPDATE applicanteducationqualification SET `highestQual` = ?, `dateOfGraduation` = ?,"
                    + " `specialCertification` = ?, `specialGraduationDate` = ? WHERE"
                    + " `Applicant_idApplicant` = ?";
            PreparedStatement ps = null;
            ps = connection.prepareStatement(updateAuthfQuery);
            ps.setString(1, applicant.getFormation().getHighestQualification());
            ps.setDate(2, toSqlDate(applicant.getFormation().getHighestGraduationDate()));
            ps.setString(3, applicant.getFormation().getSpecialQualification());
            ps.setDate(4, toSqlDate(applicant.getFormation().getSpecialGraduationDate()));
            ps.setInt(5, applicant.getId());
            ps.execute();
            System.out.println("------update ex---------");
            feedback.addMessage("Success", "Your Qualifiaction has been saved");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateEmergencContact(Applicant applicant) {
        try {
            connection = getConnection();
            String updateAuthfQuery = "UPDATE applicantemergencycontact SET `firstname` = ?, `lastname` = ?, `relationship` = ?,"
                    + " `phoneNumber` = ?, `emailAddress` =? "
                    + "WHERE `Applicant_idApplicant` =?";
            PreparedStatement ps = null;
            ps = connection.prepareStatement(updateAuthfQuery);
            ps.setString(1, applicant.getNextSkin().getFirstname());
            ps.setString(2, applicant.getNextSkin().getLastname());
            ps.setString(3, applicant.getNextSkin().getRelationship());
            ps.setString(4, applicant.getNextSkin().getPhoneNumber());
            ps.setString(5, applicant.getNextSkin().getEmailAddress());
            ps.setInt(6, applicant.getId());
            ps.execute();
            System.out.println("------update ex---------");
            feedback.addMessage("Success", "Your Emergency Contact has been saved");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void insertWorkxperience(Applicant applicant, String action) {
        try {
            connection = getConnection();
            String update = "UPDATE applicantworkexperience SET "
                    + " `nameOfEmployer` = ?,`jobTitle`=?, `startDate`=?,`endDate`=?,`duties`=? WHERE "
                    + "`idapplicantworkexperience`=?";
            String insert = "INSERT INTO applicantworkexperience "
                    + "(`nameOfEmployer`,`jobTitle`, `startDate`,`endDate`,`duties`, `Applicant_idApplicant`) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            String query = "";
            int id = 0;
            if (action.equals("Edit")) {
                query = update;
                id = applicant.getExperience().getId();
            } else {
                query = insert;
                id = applicant.getId();
            }
            PreparedStatement insertEdu = connection.prepareStatement(query);
            insertEdu.setString(1, applicant.getExperience().getNameOfEmployer());
            insertEdu.setString(2, applicant.getExperience().getJobTitle());
            insertEdu.setDate(3, toSqlDate(applicant.getExperience().getJobStart()));
            insertEdu.setDate(4, toSqlDate(applicant.getExperience().getJobEnd()));
            insertEdu.setString(5, applicant.getExperience().getDailyDuities());
            insertEdu.setInt(6, id);

            insertEdu.execute();
            feedback.addMessage("Success", "Your Work Experience has been saved");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void updateApplicantLegalHist(Applicant applicant) {

        try {
            connection = getConnection();
            String updateLegalHist = "UPDATE applicantLegalHistory SET  `offense` = ?, `convictated` = ?, `missconduct` = ?, "
                    + "`guiltyMisconduct` = ?, `drugs` =?, `tobacco` = ?, `weed` = ?, `alcohol` = ?, `reason` = ? "
                    + "WHERE `applicantLegLid` = ?";
            PreparedStatement ps = null;
            ps = connection.prepareStatement(updateLegalHist);
            ps.setString(1, applicant.getLegalHistory().getArrested());
            ps.setString(2, applicant.getLegalHistory().getConvicedCrime());
            ps.setString(3, applicant.getLegalHistory().getSexualMisCond());
            ps.setString(4, applicant.getLegalHistory().getGuitlyToSexualMisCond());
            ps.setString(5, applicant.getLegalHistory().getDrugsNotPresc());
            ps.setString(6, applicant.getLegalHistory().getTobaco());
            ps.setString(7, applicant.getLegalHistory().getWeed());
            ps.setString(8, applicant.getLegalHistory().getAlcohol());
            ps.setString(9, applicant.getLegalHistory().getReason());
            ps.setInt(10, applicant.getId());
            ps.execute();
            System.out.println("------update ex complete---------");
            feedback.addMessage("Success", "Your  Legal History has been saved");

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

    }

    public void updateApplicantMedicalhistory(Applicant applicant) {

        try {
            connection = getConnection();
            String updateLegalHist = "UPDATE applicantmedicalhistory SET `anyMedicalConditions` = ?, `medicalConditionDesc` = ?,"
                    + " `anyMedication` =?, `medicationDec` = ?, `anyPastMedicalCondition` =?, `pastMedicalConditionDesc` = ?, "
                    + "`diataryRestrictions` = ?, `dietaryRestrictionsDesc` = ?, `physicalHandicap` = ?, `handicapDesc` = ?"
                    + " WHERE  `Applicant_idApplicant` = ?;";
            PreparedStatement ps = null;
            ps = connection.prepareStatement(updateLegalHist);
            ps.setString(1, applicant.getMediaclHistory().getConditions());
            ps.setString(2, applicant.getMediaclHistory().getConditionExplanation());
            ps.setString(3, applicant.getMediaclHistory().getMedications());
            ps.setString(4, applicant.getMediaclHistory().getMedicationsExplanation());
            ps.setString(5, applicant.getMediaclHistory().getSeriousIllness());
            ps.setString(6, applicant.getMediaclHistory().getSeriousIllnessExplanation());
            ps.setString(7, applicant.getMediaclHistory().getRestrictions());
            ps.setString(8, applicant.getMediaclHistory().getRestrictionsExplanation());
            ps.setString(9, applicant.getMediaclHistory().getPhysicalHandicap());
            ps.setString(10, applicant.getMediaclHistory().getPhysicalHandicapExplanation());
            ps.setInt(11, applicant.getId());
            ps.execute();
            System.out.println("------update ex complete---------");
            feedback.addMessage("Success", "Your Medical History has been saved");

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

    }

    public void deleteWorkExperience(int id) {
        try {
            deleteRecord(id, "DELETE FROM  `applicantworkexperience` where  idapplicantworkexperience=?");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<WorkExperience> getWorkExperience(int id) {
        List<WorkExperience> works = new ArrayList<>();
        try {

            getResultSet(id, "SELECT * FROM applicantworkexperience WHERE `Applicant_idApplicant` = ?");
            while (resultset.next()) {
                WorkExperience work = new WorkExperience();
                work.setId(resultset.getInt("idapplicantworkexperience"));
                work.setJobTitle(resultset.getString("jobTitle"));
                work.setNameOfEmployer(resultset.getString("nameOfEmployer"));
                work.setJobEnd(resultset.getDate("endDate"));
                work.setJobStart(resultset.getDate("startDate"));
                work.setDailyDuities(resultset.getString("duties"));
                works.add(work);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return works;
    }

    public void updateApplicantExperience(Applicant applicant) {
        try {
            connection = getConnection();
            String updateAuthfQuery = "UPDATE applicantExperience SET  `certificates` = ?, `deallingWithNeg` = ?, `dealingWithExplanation` = ?, "
                    + "`victimOf` = ?, `victimOFExplanation` = ?  WHERE `appicantID_Fk` = ?";
            PreparedStatement ps = null;
            ps = connection.prepareStatement(updateAuthfQuery);
            ps.setString(1, applicant.getExperience().getCertificatsToString());
            ps.setString(2, applicant.getExperience().getDealingWith());
            ps.setString(3, applicant.getExperience().getDealingWithExplanation());
            ps.setString(4, applicant.getExperience().getVictimOf());
            ps.setString(5, applicant.getExperience().getVictimOfxplanation());
            ps.setInt(6, applicant.getId());
            ps.execute();
            System.out.println("------update ex---------");
            feedback.addMessage("Success", "Your Experience has been saved");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateApplicantTermsAndConditions(Applicant applicant) {

        try {
            connection = getConnection();
            String updateLegalHist = "UPDATE applicantTermsAndConditions SET `servantHearted` = ?, `honorChrist` = ?,"
                    + " `submitToAuthority` = ?, `raiseFunds` = ?, `readP1P2` = ?, `readChecklist` =?,`permissionPhotos` = ?, "
                    + "`terminateInternship` =?, `filledAccuratly` = ?, `awareOfCrime` = ?, `sign` = ? WHERE `appliacantid_fk` = ?;";
            PreparedStatement ps = null;
            ps = connection.prepareStatement(updateLegalHist);
            ps.setString(1, applicant.getTsAndCs().getServantHearted());
            ps.setString(2, applicant.getTsAndCs().getHonourChrist());
            ps.setString(3, applicant.getTsAndCs().getRespectAuthority());
            ps.setString(4, applicant.getTsAndCs().getRasieFunds());
            ps.setString(5, applicant.getTsAndCs().getReadP1P2());
            ps.setString(6, applicant.getTsAndCs().getReadChecklist());
            ps.setString(7, applicant.getTsAndCs().getPermissionUsePhotos());
            ps.setString(8, applicant.getTsAndCs().getTerminateInternShip());
            ps.setString(9, applicant.getTsAndCs().getConfirmAccuracy());
            ps.setString(10, applicant.getTsAndCs().getAwareOfCrime());
            ps.setString(11, applicant.getTsAndCs().getSign());
            ps.setInt(12, applicant.getId());
            ps.execute();
            System.out.println("------update ex complete---------");
            feedback.addMessage("Success", "Your Terms And Conditions has been saved");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    boolean recordValidator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateApplicantInterviewProgress(Applicant applicant) {
        try {
            connection = getConnection();
            String updateAuthfQuery = "UPDATE applicantprogress SET `referenceRecieved` =?, `interviewDateTime` = ?, `interviewers` = ? WHERE `applicant_ID` = ?";
            PreparedStatement ps = null;
            ps = connection.prepareStatement(updateAuthfQuery);
            ps.setString(1, applicant.getProgress().getReqDocToString());
            ps.setTimestamp(2, toSqlDateTime(applicant.getProgress().getInterviewDate()));
            ps.setString(3, applicant.getProgress().getIntervwersToString());
            ps.setInt(4, applicant.getId());
            ps.execute();
            System.out.println("------update ex---------");
            feedback.addMessage("Success", "Progress has been updated");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public ApplicationProgress getApplicationProgress(int id) {
        ApplicationProgress progress = new ApplicationProgress();
        /*UPDATE applicantprogress SET `idinternprogressTable` = ?,`referenceRecieved` =?, `interviewDateTime` = ?, `interviewers` = ?,`interviewRecomendation` = ?,
`comments` = ? WHERE `applicant_ID` = ?;
         */
        try {
            String query = "SELECT * FROM applicantprogress WHERE applicant_ID=?";
            getResultSet(id, query);
            if (resultset.next()) {
                progress.setRequiredDocs(resultset.getString("referenceRecieved") != null
                        ? new ArrayList<>(Arrays.asList(resultset.getString("referenceRecieved").split(",")))
                        : new ArrayList<>());
                progress.setInterviewDate(resultset.getTimestamp("interviewDateTime"));
                progress.setInterviewers(resultset.getString("interviewers") != null
                        ? new ArrayList<>(Arrays.asList(resultset.getString("interviewers").split(",")))
                        : new ArrayList<>());
                progress.setOutcoume(resultset.getString("interviewRecomendation"));
                progress.setComments(resultset.getString("comments"));
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
        return progress;

    }

    public void updateInterviewOutcome(Applicant applicant) {
        try {
            connection = getConnection();
            String updateAuthfQuery = "UPDATE applicantprogress SET `interviewRecomendation` = ?, `comments` = ? WHERE `applicant_ID` = ?";
            PreparedStatement ps = null;
            ps = connection.prepareStatement(updateAuthfQuery);
            ps.setString(1, applicant.getProgress().getOutcoume());
            ps.setString(2, applicant.getProgress().getComments());
            ps.setInt(3, applicant.getId());
            ps.execute();
            feedback.addMessage("Success", "Progress has been updated");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

 

    public void updateAccountStatus(String suspended, int id) {
           try {
            connection = getConnection();
            String updateAuthfQuery = "UPDATE `applicantLogin`SET `status` = ? WHERE `idApplicant` = ?";
            PreparedStatement ps = null;
            ps = connection.prepareStatement(updateAuthfQuery);
            ps.setString(1, suspended);
            ps.setInt(2, id);
            ps.execute();
            feedback.addMessage("Success", "Account has been suspended");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(InternAplicationTableConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


}

/*
UPDATE `onthatile children's ministries`.`applicantRequiredDocuments`
SET
`idinternRequiredDocuments` = <{idinternRequiredDocuments: }>,
`generalRefe` = <{generalRefe: }>,
`pasroralRef` = <{pasroralRef: }>,
`parentalCon` = <{parentalCon: }>,
`applicant_Id` = <{applicant_Id: }>
WHERE `idinternRequiredDocuments` = <{expr}> AND `applicant_Id` = <{expr}>;

 */
