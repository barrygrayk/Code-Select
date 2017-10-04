package com.child.Model;


import com.staff.Model.Person;
import com.validation.TheEqualizer;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author efsan1 
 */
public class Child extends Person implements Serializable{
    
    private double headCir;
    private int babyProfileid;
    private int currentIndex = -1;
    private String photography;
    private String destingushingMarks, childDietComments;
    private String requiredDocNote, ChildArrivalMedicalReport, generalRemarks;
    
    // arrayList 
    private ArrayList<Meals> listOfMealsHad;
    private ArrayList<Weight> listOfWeightRecords;
    private ArrayList<Height> listOfHeightRecords;
    private ArrayList<Nappies> listOfNappyRecords;
    private ArrayList<Anomaly> listOfAnomalyRecords;
    private ArrayList<Temperature> listOfTempRecorded;
    private ArrayList<SleepingRoutine> listOfSleepingRecords;
    private ArrayList childRequiredDocs,childArrivalCondition, childDietDetails;
    private ArrayList<DailyActivities> listOfActivitiesRecorded;
    protected ArrayList<ChildMedicalHistory> listOfMedicalHistoryRecords = new ArrayList<ChildMedicalHistory>();
    private ArrayList<Appointment> listOfAppointmentRecords = new ArrayList<Appointment>();
    private ArrayList<Diagnostics> listOfDiagnosticRecords;
    private ArrayList<MedicalExam> listOfMedicalExamRecords;
    private ArrayList<MedicalEvidence> listOfMedicalEvidenceRecords;
    private ArrayList<TreatmentPlan> listOfTreatmentPlanRecords;
    private ArrayList <DetectedIllness> listOfDetectedIllness;
    private ArrayList <Allergies> listOfAllergyRecords;
    private ArrayList <VaccineTaken> listOfVaccineTaken;
    private ArrayList <Medication> listOfMedicationToTake;
    private ArrayList <MedicationTaken> listOfMedicationTaken;
    
    //temporary objects
    private Meals mealRecorded = new Meals();
    private Weight weightRecorded = new Weight();
    private Height heightRecorded = new Height();
    private Nappies nappyRecorded = new Nappies();
    private Allergies allergy = new Allergies();
    private Medication  medication = new Medication();
    private Anomaly anomalyRecord = new Anomaly();
    private ParentInfo parentInfo = new ParentInfo();
    protected IntakeInfo intakeDetails = new IntakeInfo();
    private Diagnostics diagnosis = new Diagnostics();
    private Appointment appointment = new Appointment();
    private MedicalExam exams = new MedicalExam();
    private Temperature temperatureRecord = new Temperature();
    private SocialWorker socialWorkerDetails = new SocialWorker();
    private DailyActivities activityrecorded = new DailyActivities();
    private SleepingRoutine sleepRecorded = new SleepingRoutine();
    private VaccineTaken vaccineTaken = new VaccineTaken();
    private TreatmentPlan treatmentPlan = new TreatmentPlan();
    private TreatmentPlan treatmentPlan2 = new TreatmentPlan();
    private MedicationTaken medicationTaken = new MedicationTaken();
    private DetectedIllness chronicIllness = new DetectedIllness();
    protected ChildMedicalHistory medicalHistory = new ChildMedicalHistory();

    //clear child Object 
    public void clearChild(){
        this.destingushingMarks = null;
        super.setLastname(null);
        super.setFirstname(null);
        /*super.setGender(' ');
        super.setDateOfBirth(null);
        super.setPlaceOfBirth(null);
        this.headCir = 0;
        this.heightRecorded = null;
        this.weightRecorded = null;
        this.ChildArrivalMedicalReport = null;
        this.generalRemarks = null;*/
        
    }
    
    public Child(int babyProfileid, String firstname, String lastname, char gender, Date dateOfBirth,
            String placeOfBirth, String destingushingMarks, ArrayList childRequiredDocs, 
            ArrayList childArrivalCondition, String requiredDocNote, String ChildArrivalMedicalReport ,
            IntakeInfo intakeDetails, SocialWorker socialWorkerDetails, ParentInfo parentInfo, String generalRemarks,
            Weight weightRecorded, Height heightRecorded, double headCir, 
            ArrayList<ChildMedicalHistory> listOfMedicalHistoryRecords, String photography, ArrayList childDiet, String childDietComments) {
        
        super(firstname, lastname, gender, placeOfBirth, dateOfBirth);
        this.destingushingMarks =  destingushingMarks;
        this.babyProfileid = babyProfileid;
        this.childRequiredDocs = childRequiredDocs;
        this.childArrivalCondition = childArrivalCondition;
        this.requiredDocNote = requiredDocNote;
        this.ChildArrivalMedicalReport = ChildArrivalMedicalReport;
        this.intakeDetails = intakeDetails;
        this.socialWorkerDetails = socialWorkerDetails;
        this.parentInfo = parentInfo;
        this.generalRemarks = generalRemarks;
        this.weightRecorded = weightRecorded;
        this.heightRecorded = heightRecorded;
        this.headCir = headCir;
        this.listOfMedicalHistoryRecords = listOfMedicalHistoryRecords;
        this.photography = photography;
        this.childDietDetails = childDiet;
        this.childDietComments = childDietComments;
        
        
    }
    public Child(){
    }
    
    public Child(String destingushingMarks, String firstname, String lastname) {
        super(firstname, lastname);
        this.destingushingMarks = destingushingMarks;
    }

    public Child(int babyProfileid,  String firstname, String lastname,String destingushingMarks) {
        super(firstname, lastname);
        this.destingushingMarks = destingushingMarks;
        this.babyProfileid = babyProfileid;
    }
    
    public int getBabyProfileid() {
        return babyProfileid;
    }

    public void setBabyProfileid(int babyProfileid) {
        this.babyProfileid = babyProfileid;
    }
   
    public ChildMedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(ChildMedicalHistory medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    
    public String getDestingushingMarks() {
        return destingushingMarks;
    }

    public void setDestingushingMarks(String destingushingMarks) {
        this.destingushingMarks = destingushingMarks;
    }

    public ArrayList<Temperature> getlistOfTempRecorded() {
        return listOfTempRecorded;
    }

    public void setlistOfTempRecorded(ArrayList<Temperature> temp) {
        this.listOfTempRecorded = temp;
    }

    public ArrayList<Meals> getListOfMealsHad() {
        return listOfMealsHad;
    }
    // temporary 
    public Meals getSelectedtMeal(){
        return listOfMealsHad.get(0);
    }

    public void setlistOfMealsHad(ArrayList<Meals> meals) {
        this.listOfMealsHad = meals;
    }
    // in case there are no meals records @@
    public void verifyListOfMealsHad(){
        if(this.listOfMealsHad.isEmpty())
            this.listOfMealsHad.add(new Meals("No meals records were taken"));
    }
    
   /* public void checkIfListIsEmpty(ArrayList<Object> list, String description){
        if(list.isEmpty()){
            list.add(new Object("No"+description+"record"));
        }
    }*/
    
    public Weight getWeightRecorded() {
        return weightRecorded;
    }

    public void setWeightRecorded(Weight weightRecorded) {
        this.weightRecorded = weightRecorded;
    }

    public ArrayList<Nappies> getlistOfNappyRecords() {
        return listOfNappyRecords;
    }
    
    public void verifyListOfNappiesRecord(){
        if(this.listOfNappyRecords.isEmpty()){}
          //  this.listOfNappyRecords.add(new Nappies("No nappies records were taken"));
    }

    public void setlistOfNappyRecords(ArrayList<Nappies> listOfNappies) {
        this.listOfNappyRecords = listOfNappies;
    }  
    
    public ArrayList<SleepingRoutine> getListOfSleepingRecords() {
        return listOfSleepingRecords;
    }

    public void setListOfSleepingRecords(ArrayList<SleepingRoutine> listOfSleepingRecords) {
        this.listOfSleepingRecords = listOfSleepingRecords;
    }
    
    public ArrayList<DailyActivities> getListOfActivitiesRecorded() {
        return listOfActivitiesRecorded;
    }

    public void setListOfActivitiesRecorded(ArrayList<DailyActivities> listOfActivitiesRecorded) {
        this.listOfActivitiesRecorded = listOfActivitiesRecorded;
    }

    public ArrayList<ChildMedicalHistory> getListOfMedicalHistoryRecords() {
        return listOfMedicalHistoryRecords;
    }

    public void setListOfMedicalHistoryRecords(ArrayList<ChildMedicalHistory> listOfMedicalHistoryRecords) {
        this.listOfMedicalHistoryRecords = listOfMedicalHistoryRecords;
    }
    
    public ArrayList getChildRequiredDocs() {
        return childRequiredDocs;
    }

    public void setChildRequiredDocs(ArrayList childRequiredDocs) {
        this.childRequiredDocs = childRequiredDocs;
    }

    public String getRequiredDocNote() {
        return requiredDocNote;
    }

    public void setRequiredDocNote(String requiredDocNote) {
        this.requiredDocNote = requiredDocNote;
    }

    public ArrayList getChildArrivalCondition() {
        return childArrivalCondition;
    }

    public void setChildArrivalCondition(ArrayList childArrivalCondition) {
        this.childArrivalCondition = childArrivalCondition;
    }

    public String getChildArrivalMedicalReport() {
        return ChildArrivalMedicalReport;
    }

    public void setChildArrivalMedicalReport(String ChildArrivalMedicalReport) {
        this.ChildArrivalMedicalReport = ChildArrivalMedicalReport;
    }

    public double getHeadCir() {
        return headCir;
    }

    public void setHeadCir(double headCir) {
        this.headCir = headCir;
    }

    public String getGeneralRemarks() {
        return generalRemarks;
    }

    public void setGeneralRemarks(String generalRemarks) {
        this.generalRemarks = generalRemarks;
    }

    public ParentInfo getParentInfo() {
        return parentInfo;
    }

    public void setParentInfo(ParentInfo parentInfo) {
        this.parentInfo = parentInfo;
    }

    public SocialWorker getSocialWorkerDetails() {
        return socialWorkerDetails;
    }

    public void setSocialWorkerDetails(SocialWorker socialWorkerDetails) {
        this.socialWorkerDetails = socialWorkerDetails;
    }

    public IntakeInfo getIntakeDetails() {
        return intakeDetails;
    }

    public void setIntakeDetails(IntakeInfo intakeDetails) {
        this.intakeDetails = intakeDetails;
    }

    public Height getHeightRecorded() {
        return heightRecorded;
    }

    public void setHeightRecorded(Height heightRecorded) {
        this.heightRecorded = heightRecorded;
    }

    public Meals getMealRecorded() {
        return mealRecorded;
    }

    public void setMealRecorded(Meals mealRecorded) {
        this.mealRecorded = mealRecorded;
    }

    public SleepingRoutine getSleepRecorded() {
        return sleepRecorded;
    }

    public void setSleepRecorded(SleepingRoutine sleepRecorded) {
        this.sleepRecorded = sleepRecorded;
    }

    public Nappies getNappyRecorded() {
        return nappyRecorded;
    }

    public void setNappyRecorded(Nappies nappyRecorded) {
        this.nappyRecorded = nappyRecorded;
    }

    public DailyActivities getActivityrecorded() {
        return activityrecorded;
    }

    public void setActivityrecorded(DailyActivities activityrecorded) {
        this.activityrecorded = activityrecorded;
    }

    public ArrayList<Height> getListOfHeightRecords() {
        return listOfHeightRecords;
    }

    public void setListOfHeightRecords(ArrayList<Height> listOfHeightRecords) {
        this.listOfHeightRecords = listOfHeightRecords;
    }

    public ArrayList<Weight> getListOfWeightRecords() {
        return listOfWeightRecords;
    }

    public void setListOfWeightRecords(ArrayList<Weight> listOfWeightRecords) {
        this.listOfWeightRecords = listOfWeightRecords;
    }

    public ArrayList<Appointment> getListOfAppointmentRecords() {
        return listOfAppointmentRecords;
    }

    public void setListOfAppointmentRecords(ArrayList<Appointment> listOfAppointmentRecords) {
        this.listOfAppointmentRecords = listOfAppointmentRecords;
    }

    public String getPhotography() {
        return photography;
    }

    public void setPhotography(String photography) {
        this.photography = photography;
    }

    public ArrayList<Anomaly> getListOfAnomalyRecords() {
        return listOfAnomalyRecords;
    }

    public void setListOfAnomalyRecords(ArrayList<Anomaly> listOfAnomalyRecords) {
        this.listOfAnomalyRecords = listOfAnomalyRecords;
    }

    public Anomaly getAnomalyRecord() {
        return anomalyRecord;
    }

    public void setAnomalyRecord(Anomaly anomalyRecord) {
        this.anomalyRecord = anomalyRecord;
    }

    public ArrayList getChildDietDetails() {
        return childDietDetails;
    }

    public void setChildDietDetails(ArrayList childDietDetails) {
        this.childDietDetails = childDietDetails;
    }

    public String getChildDietComments() {
        return childDietComments;
    }

    public void setChildDietComments(String childDietComments) {
        this.childDietComments = childDietComments;
    }

    public Temperature getTemperatureRecord() {
        return temperatureRecord;
    }

    public void setTemperatureRecord(Temperature temperatureRecord) {
        this.temperatureRecord = temperatureRecord;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public ArrayList<Diagnostics> getListOfDiagnosticRecords() {
        return listOfDiagnosticRecords;
    }

    public void setListOfDiagnosticRecords(ArrayList<Diagnostics> listOfDiagnosticRecords) {
        this.listOfDiagnosticRecords = listOfDiagnosticRecords;
    }

    public ArrayList<MedicalExam> getListOfMedicalExamRecords() {
        return listOfMedicalExamRecords;
    }

    public void setListOfMedicalExamRecords(ArrayList<MedicalExam> listOfMedicalExamRecords) {
        this.listOfMedicalExamRecords = listOfMedicalExamRecords;
    }

    public MedicalExam getExams() {
        return exams;
    }

    public void setExams(MedicalExam exams) {
        this.exams = exams;
    }

    public ArrayList<TreatmentPlan> getListOfTreatmentPlanRecords() {
        return listOfTreatmentPlanRecords;
    }

    public void setListOfTreatmentPlanRecords(ArrayList<TreatmentPlan> listOfTreatmentPlanRecords) {
        this.listOfTreatmentPlanRecords = listOfTreatmentPlanRecords;
    }

    public TreatmentPlan getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(TreatmentPlan treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public TreatmentPlan getTreatmentPlan2() {
        return treatmentPlan2;
    }

    public void setTreatmentPlan2(TreatmentPlan treatmentPlan2) {
        this.treatmentPlan2 = treatmentPlan2;
    }

    public ArrayList<DetectedIllness> getListOfDetectedIllness() {
        return listOfDetectedIllness;
    }

    public void setListOfDetectedIllness(ArrayList<DetectedIllness> listOfDetectedIllness) {
        this.listOfDetectedIllness = listOfDetectedIllness;
    }

    public ArrayList<Allergies> getListOfAllergyRecords() {
        return listOfAllergyRecords;
    }

    public void setListOfAllergyRecords(ArrayList<Allergies> listOfAllergyRecords) {
        this.listOfAllergyRecords = listOfAllergyRecords;
    }

    public ArrayList<VaccineTaken> getListOfVaccineTaken() {
        return listOfVaccineTaken;
    }

    public void setListOfVaccineTaken(ArrayList<VaccineTaken> listOfVaccineTaken) {
        this.listOfVaccineTaken = listOfVaccineTaken;
    }

    public VaccineTaken getVaccineTaken() {
        return vaccineTaken;
    }

    public void setVaccineTaken(VaccineTaken vaccineTaken) {
        this.vaccineTaken = vaccineTaken;
    }

    public Allergies getAllergy() {
        return allergy;
    }

    public void setAllergy(Allergies allergy) {
        this.allergy = allergy;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public ArrayList<Medication> getListOfMedicationToTake() {
        return listOfMedicationToTake;
    }

    public void setListOfMedicationToTake(ArrayList<Medication> listOfMedicationToTake) {
        this.listOfMedicationToTake = listOfMedicationToTake;
    }
    
    public DetectedIllness getChronicIllness() {
        return chronicIllness;
    }

    public void setChronicIllness(DetectedIllness chronicIllness) {
        this.chronicIllness = chronicIllness;
    }

    public ArrayList<MedicalEvidence> getListOfMedicalEvidenceRecords() {
        return listOfMedicalEvidenceRecords;
    }

    public void setListOfMedicalEvidenceRecords(ArrayList<MedicalEvidence> listOfMedicalEvidenceRecords) {
        this.listOfMedicalEvidenceRecords = listOfMedicalEvidenceRecords;
    }

    public Diagnostics getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnostics diagnosis) {
        this.diagnosis = diagnosis;
    }

    public ArrayList<MedicationTaken> getListOfMedicationTaken() {
        return listOfMedicationTaken;
    }

    public void setListOfMedicationTaken(ArrayList<MedicationTaken> listOfMedicationTaken) {
        this.listOfMedicationTaken = listOfMedicationTaken;
    }

    public MedicationTaken getMedicationTaken() {
        return medicationTaken;
    }

    public void setMedicationTaken(MedicationTaken medicationTaken) {
        this.medicationTaken = medicationTaken;
    }
    
    // select medicalRecord to update
    public void selectMedicalRecordToUpdateByIndex(int i){
        
        if((!this.listOfMedicalHistoryRecords.isEmpty()) && (i>=0 && i<this.listOfMedicalHistoryRecords.size())){
            
            this.medicalHistory.setDoctersName(this.listOfMedicalHistoryRecords.get(i).getDoctersName());
            this.medicalHistory.setIllnessDetected(this.listOfMedicalHistoryRecords.get(i).getIllnessDetected());
            this.medicalHistory.setAllergies(this.listOfMedicalHistoryRecords.get(i).getAllergies());
            this.medicalHistory.setCause(this.listOfMedicalHistoryRecords.get(i).getCause());
            this.medicalHistory.setClincVisited(this.listOfMedicalHistoryRecords.get(i).getClincVisited());
            this.medicalHistory.setMedicines(this.listOfMedicalHistoryRecords.get(i).getMedicines());
            this.medicalHistory.setSpecialTreatmeants(this.listOfMedicalHistoryRecords.get(i).getSpecialTreatmeants());
            this.medicalHistory.setDateOfVisit(this.listOfMedicalHistoryRecords.get(i).getDateOfVisit());
             
        }
    }
    // load first medicalRecord to update: default
    public void selectMedicalRecordToUpdate(){
        currentIndex = 0;
        selectMedicalRecordToUpdateByIndex(0);
    }
    // select next medicalRecord to Update :next button 
    public void next(){
        if(!this.listOfMedicalHistoryRecords.isEmpty()){
            if(currentIndex == -1){
                currentIndex = this.listOfMedicalHistoryRecords.indexOf(this.medicalHistory);
            }
            if((currentIndex + 1) < this.listOfMedicalHistoryRecords.size()){
                currentIndex = currentIndex + 1;
                selectMedicalRecordToUpdateByIndex(currentIndex);
            }else{
                selectMedicalRecordToUpdate();
            }
        }
    }
    
    // select medicalRecord to update: previous button 
    public void previous(){
        /*if(!this.listOfMedicalHistoryRecords.isEmpty()){
            if(currentIndex == -1){
                currentIndex = this.listOfMedicalHistoryRecords.indexOf(this.medicalHistory);
            }
            if((currentIndex - 1) > 0 ){
                currentIndex = currentIndex - 1;
                selectMedicalRecordToUpdateByIndex(currentIndex);
            }else{
                currentIndex = this.listOfMedicalHistoryRecords.size() - 1;
                selectMedicalRecordToUpdateByIndex(currentIndex);
            }
        }*/
    }
    // update temporary illnessList 
    public void updateOnTempList(){
        ChildMedicalHistory altered = new ChildMedicalHistory();
        if(currentIndex != -1){
            
            altered.setIllnessDetected(this.medicalHistory.getIllnessDetected());
            
            this.listOfMedicalHistoryRecords.get(currentIndex).setSpecialTreatmeants(this.medicalHistory.getSpecialTreatmeants());
            this.listOfMedicalHistoryRecords.get(currentIndex).setIllnessDetected(this.medicalHistory.getIllnessDetected());
            this.listOfMedicalHistoryRecords.get(currentIndex).setClincVisited(this.medicalHistory.getClincVisited());
            this.listOfMedicalHistoryRecords.get(currentIndex).setDoctersName(this.medicalHistory.getDoctersName());
            this.listOfMedicalHistoryRecords.get(currentIndex).setDateOfVisit(this.medicalHistory.getDateOfVisit());
            this.listOfMedicalHistoryRecords.get(currentIndex).setAllergies(this.medicalHistory.getAllergies());
            this.listOfMedicalHistoryRecords.get(currentIndex).setMedicines(this.medicalHistory.getMedicines());
            this.listOfMedicalHistoryRecords.get(currentIndex).setCause(this.medicalHistory.getCause());
           
        }   
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
        
    @Override
    public int getAge() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        TheEqualizer x = new TheEqualizer();
        LocalDate currentDate = LocalDate.now();
        return Period.between(x.convertdateToLocalDate(this.getDateOfBirth()),currentDate).getYears();
    }

    @Override
    public String toString(){
        return super.getFirstname();
    }
    
}