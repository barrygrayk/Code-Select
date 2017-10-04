
package com.reports;

import com.child.Model.Allergies;
import com.child.Model.Anomaly;
import com.child.Model.ChildMedicalHistory;
import com.child.Model.DailyActivities;
import com.child.Model.DetectedIllness;
import com.child.Model.Height;
import com.child.Model.Meals;
import com.child.Model.MedicationTaken;
import com.child.Model.Nappies;
import com.child.Model.SleepingRoutine;
import com.child.Model.Temperature;
import com.child.Model.VaccineTaken;
import com.child.Model.Weight;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author efsan1
 */
public class ChildReports {
    
    // table title font 
    public Paragraph tableTitle(String title){
        Font tableTitleFont = new Font(Font.TIMES_ROMAN,14,Font.BOLD);
        Paragraph tableTitle = new Paragraph(title,tableTitleFont);
        return tableTitle;
    }
    
    // create table for meals taken 
    public PdfPTable getMealsTable(ArrayList<Meals> listOfMeals){
        
       PdfPTable table=new PdfPTable(4);
       PdfPCell cell = new PdfPCell (tableTitle("Meals Record(s)"));
 
       cell.setColspan (4);
       cell.setHorizontalAlignment (Element.ALIGN_CENTER);
       cell.setPadding (10.0f);
       table.addCell(cell);                
       // default fields title
       table.addCell(tableTitle("Meal Type"));
       table.addCell(tableTitle("Time"));
       table.addCell(tableTitle("Date"));
       table.addCell(tableTitle("Comments"));
       
       // data for the above fields
       for(Meals currMeal: listOfMeals){
           table.addCell(currMeal.getMealDescription());
           table.addCell(currMeal.getMealIntakeTime().toString());
           table.addCell(currMeal.getMealDateRecorded().toString());
           table.addCell(currMeal.getCommentOnEating());
       }
       
       table.setSpacingBefore(30.0f);       // Space Before table starts, like margin-top in CSS
       table.setSpacingAfter(30.0f); 
       
       return table;
    }
    
    // create table for activities done
    public PdfPTable getActivitiesTable(ArrayList<DailyActivities> listOfActivities){
        
       PdfPTable table=new PdfPTable(5);
       PdfPCell cell = new PdfPCell (tableTitle("Activities Record(s)"));
 
       cell.setColspan (5);
       cell.setHorizontalAlignment (Element.ALIGN_CENTER);
       cell.setPadding (10.0f);
       table.addCell(cell);                
       // default fields title
       table.addCell(tableTitle("Activity Description"));
       table.addCell(tableTitle("Activity Status"));
       table.addCell(tableTitle("Ocurring Time"));
       table.addCell(tableTitle("Date"));
       table.addCell(tableTitle("Comments"));
       
       // data for the above fields
       for(DailyActivities currActivity: listOfActivities){
           table.addCell(currActivity.getTitle());
           table.addCell(currActivity.getStatus());
           table.addCell(currActivity.getActivityOccuringTime().toString());
           table.addCell(currActivity.getDateRecorded().toString());
           table.addCell(currActivity.getComment());
       }
       
       table.setSpacingBefore(30.0f);       // Space Before table starts, like margin-top in CSS
       table.setSpacingAfter(30.0f); 
       
       return table;
    }
    
    // create table for nappy change records
    public PdfPTable getNappyTable(ArrayList<Nappies> listOfNappies){
        
       PdfPTable table=new PdfPTable(4);
       PdfPCell cell = new PdfPCell (tableTitle("Nappy Record(s)"));
 
       cell.setColspan (4);
       cell.setHorizontalAlignment (Element.ALIGN_CENTER);
       cell.setPadding (10.0f);
       table.addCell(cell);                
       // default fields title
       table.addCell(tableTitle("Nappy Wet"));
       table.addCell(tableTitle("Nappy Dirty"));
       table.addCell(tableTitle("Changing Time"));
       table.addCell(tableTitle("Date"));
       
       // data for the above fields
       for(Nappies currNappy: listOfNappies){
           table.addCell(currNappy.getWetNappy());
           table.addCell(currNappy.getDryNappy());
           table.addCell(currNappy.getNappyChangeTime().toString());
           table.addCell(currNappy.getDateRecorded().toString());
           
       }
       
       table.setSpacingBefore(30.0f);       
       table.setSpacingAfter(30.0f); 
       
       return table;
    }
    
    // create table for sleeping routine
    public PdfPTable getSleepingTable(ArrayList<SleepingRoutine> listOfSleepingRecords){
        
       PdfPTable table=new PdfPTable(3);
       PdfPCell cell = new PdfPCell (tableTitle("Sleeping Record(s)"));
 
       cell.setColspan (3);
       cell.setHorizontalAlignment (Element.ALIGN_CENTER);
       cell.setPadding (10.0f);
       table.addCell(cell);                
       // default fields title
       table.addCell(tableTitle("Sleeping Time"));
       table.addCell(tableTitle("Waking Time"));
       table.addCell(tableTitle("Date"));
       
       // data for the above fields
       for(SleepingRoutine currSleepingRecord: listOfSleepingRecords){
           table.addCell(currSleepingRecord.getSleepingTime().toString());
           table.addCell(currSleepingRecord.getWakingTime().toString());
           table.addCell(currSleepingRecord.getDateRecorded().toString());
       }
       
       table.setSpacingBefore(30.0f);       
       table.setSpacingAfter(30.0f); 
       
       return table;
    }
    
    // create table for height records 
    public PdfPTable getHeightTable(ArrayList<Height> listOfHeightRecords){
        
       PdfPTable table=new PdfPTable(2);
       PdfPCell cell = new PdfPCell (tableTitle("Height Record(s)"));
 
       cell.setColspan (2);
       cell.setHorizontalAlignment (Element.ALIGN_CENTER);
       cell.setPadding (10.0f);
       table.addCell(cell);                
       // default fields title
       table.addCell(tableTitle("Height"));
       table.addCell(tableTitle("Date"));
       
       // data for the above fields
       for(Height currHeightRecord: listOfHeightRecords){
           table.addCell(""+currHeightRecord.getHeight());
           table.addCell(currHeightRecord.getDateRecorded().toString());
       }
       
       table.setSpacingBefore(30.0f);       
       table.setSpacingAfter(30.0f); 
       
       return table;
    }
    
    // create table for weight records 
    public PdfPTable getWeightTable(ArrayList<Weight> listOfWeightRecords){
        
       PdfPTable table=new PdfPTable(2);
       PdfPCell cell = new PdfPCell (tableTitle("Weight Record(s)"));
 
       cell.setColspan (2);
       cell.setHorizontalAlignment (Element.ALIGN_CENTER);
       cell.setPadding (10.0f);
       table.addCell(cell);                
       // default fields title
       table.addCell(tableTitle("Weight"));
       table.addCell(tableTitle("Date"));
       
       // data for the above fields
       for(Weight currWeightRecord: listOfWeightRecords){
           table.addCell(""+currWeightRecord.getWeight());
           table.addCell(currWeightRecord.getDateRecorded().toString());
       }
       
       table.setSpacingBefore(30.0f);       
       table.setSpacingAfter(30.0f); 
       
       return table;
    }
    
    // create table for temperature 
    public PdfPTable getTemperatureTable(ArrayList<Temperature> listOfTemperatureRecords){
        
       PdfPTable table=new PdfPTable(3);
       PdfPCell cell = new PdfPCell (tableTitle("Temperature Record(s)"));
 
       cell.setColspan (3);
       cell.setHorizontalAlignment (Element.ALIGN_CENTER);
       cell.setPadding (10.0f);
       table.addCell(cell);                
       // default fields title
       table.addCell(tableTitle("Temperature"));
       table.addCell(tableTitle("Time"));
       table.addCell(tableTitle("Date"));
       
       // data for the above fields
       for(Temperature currTemperatureRecord: listOfTemperatureRecords){
           table.addCell(""+currTemperatureRecord.getTemperatureReading());
           table.addCell(currTemperatureRecord.getTemperatureTime().toString());
           table.addCell(currTemperatureRecord.getTemperatureDate().toString());
       }
       
       table.setSpacingBefore(30.0f);       
       table.setSpacingAfter(30.0f); 
       
       return table;
    }
    
    // create table for anomaly records
    public PdfPTable getAnomalyTable(ArrayList<Anomaly> listOfAnomalyRecords){
        
       PdfPTable table=new PdfPTable(5);
       PdfPCell cell = new PdfPCell (tableTitle("Anomaly Record(s)"));
 
       cell.setColspan (5);
       cell.setHorizontalAlignment (Element.ALIGN_CENTER);
       cell.setPadding (10.0f);
       table.addCell(cell);                
       // default fields title
       table.addCell(tableTitle("Anomaly Category"));
       table.addCell(tableTitle("Description"));
       table.addCell(tableTitle("Time"));
       table.addCell(tableTitle("Date"));
       table.addCell(tableTitle("Comments"));
       
       // data for the above fields
       for(Anomaly currAnomalyRecord: listOfAnomalyRecords){
           table.addCell(currAnomalyRecord.getCategory());
           table.addCell(currAnomalyRecord.getDescription());
           table.addCell(currAnomalyRecord.getTime().toString());
           table.addCell(currAnomalyRecord.getDate().toString());
           table.addCell(currAnomalyRecord.getComments());
       }
       
       table.setSpacingBefore(30.0f);       
       table.setSpacingAfter(30.0f); 
       
       return table;
    }
    
    // create table for medication taken
    public PdfPTable getMedicationTakenTable(ArrayList<MedicationTaken> listOfMedicationRecords){
        
       PdfPTable table=new PdfPTable(5);
       PdfPCell cell = new PdfPCell (tableTitle("Medication Record(s)"));
 
       cell.setColspan (5);
       cell.setHorizontalAlignment (Element.ALIGN_CENTER);
       cell.setPadding (10.0f);
       table.addCell(cell);                
       // default fields title
       table.addCell(tableTitle("Medicine taken"));
       table.addCell(tableTitle("Quantity"));
       table.addCell(tableTitle("Metric"));
       table.addCell(tableTitle("Admission Time"));
       table.addCell(tableTitle("Comment"));
       
       // data for the above fields
       for(MedicationTaken currMedicationTaken: listOfMedicationRecords){
           table.addCell(currMedicationTaken.getMedicineTaken());
           table.addCell(currMedicationTaken.getQuantity());
           table.addCell(currMedicationTaken.getMetric());
           table.addCell(currMedicationTaken.getAdmisssionTime().toString());
           table.addCell(currMedicationTaken.getComment());
       }
       
       table.setSpacingBefore(30.0f);       
       table.setSpacingAfter(30.0f); 
       
       return table;
    }
    
    // create table for initial medical report
    public PdfPTable getInitialMedicalReportTable(ArrayList<ChildMedicalHistory> listOfMedicationRecords){
        
       PdfPTable table=new PdfPTable(8);
       PdfPCell cell = new PdfPCell (tableTitle("Initial Medical Record(s)"));
 
       cell.setColspan (8);
       cell.setHorizontalAlignment (Element.ALIGN_CENTER);
       cell.setPadding (10.0f);
       table.addCell(cell);                
       // default fields title
       table.addCell(tableTitle("Illness"));
       table.addCell(tableTitle("Cause"));
       table.addCell(tableTitle("Treatment"));
       table.addCell(tableTitle("Special Treatment"));
       table.addCell(tableTitle("Allergy"));
       table.addCell(tableTitle("Date"));
       table.addCell(tableTitle("Doctor"));
       table.addCell(tableTitle("Clinic"));
       
       // data for the above fields
       for(ChildMedicalHistory currInitialMedicalReport: listOfMedicationRecords){
           table.addCell(currInitialMedicalReport.getIllnessDetected());
           table.addCell(currInitialMedicalReport.getCause());
           table.addCell(currInitialMedicalReport.getMedicines());
           table.addCell(currInitialMedicalReport.getSpecialTreatmeants());
           table.addCell(currInitialMedicalReport.getAllergies());
           table.addCell(currInitialMedicalReport.getDateOfVisit().toString());
           table.addCell(currInitialMedicalReport.getDoctersName());
           table.addCell(currInitialMedicalReport.getClincVisited());
       }
       
       table.setSpacingBefore(30.0f);       
       table.setSpacingAfter(30.0f); 
       
       return table;
    } 
    
    // create table for chronic illness
    public PdfPTable getChronicIllnessTable(ArrayList<DetectedIllness> listOfChronicIllnessRecords){
        
       PdfPTable table=new PdfPTable(1);
       PdfPCell cell = new PdfPCell (tableTitle("Chronic Illness Record(s)"));
 
       cell.setColspan (1);
       cell.setHorizontalAlignment (Element.ALIGN_CENTER);
       cell.setPadding (10.0f);
       table.addCell(cell);                
       // default fields title
       table.addCell(tableTitle("Chronic Illness"));
       
       // data for the above fields
       for(DetectedIllness currChronicIllnessRecord: listOfChronicIllnessRecords){
           table.addCell(currChronicIllnessRecord.getDescription());
       }
       
       table.setSpacingBefore(30.0f);       
       table.setSpacingAfter(30.0f); 
       
       return table;
    }
    
    // create table for allergies report 
    public PdfPTable getAllergiesTable(ArrayList<Allergies> listOfAllergiesRecords){
        
       PdfPTable table=new PdfPTable(1);
       PdfPCell cell = new PdfPCell (tableTitle("Allergy Record(s)"));
 
       cell.setColspan (1);
       cell.setHorizontalAlignment (Element.ALIGN_CENTER);
       cell.setPadding (10.0f);
       table.addCell(cell);                
       // default fields title
       table.addCell(tableTitle("Allergy"));
       
       // data for the above fields
       for(Allergies currAllergyRecord: listOfAllergiesRecords){
           table.addCell(currAllergyRecord.getDescription());
       }
       
       table.setSpacingBefore(30.0f);       
       table.setSpacingAfter(30.0f); 
       
       return table;
    }
    
    // create table for vacines taken
    public PdfPTable getVaccineTakenTable(ArrayList<VaccineTaken> listOfVaccineRecords){
        
       PdfPTable table=new PdfPTable(7);
       PdfPCell cell = new PdfPCell (tableTitle("Vaccine Record(s)"));
 
       cell.setColspan (7);
       cell.setHorizontalAlignment (Element.ALIGN_CENTER);
       cell.setPadding (10.0f);
       table.addCell(cell);                
       // default fields title
       table.addCell(tableTitle("Vaccine"));
       table.addCell(tableTitle("AgeGroup"));
       table.addCell(tableTitle("Site"));
       table.addCell(tableTitle("Due Date"));
       table.addCell(tableTitle("Received Date"));
       table.addCell(tableTitle("Location"));
       table.addCell(tableTitle("Comments"));
       
       // data for the above fields
       for(VaccineTaken currVaccineTaken: listOfVaccineRecords){
           table.addCell(currVaccineTaken.getVaccineTaken());
           table.addCell(currVaccineTaken.getAgeGroup());
           table.addCell(currVaccineTaken.getSite());
           table.addCell(currVaccineTaken.getScheduleDate().toString());
           table.addCell(currVaccineTaken.getGivenDate().toString());      
           table.addCell(currVaccineTaken.getLocation());
           table.addCell(currVaccineTaken.getComments());
       }
       
       table.setSpacingBefore(30.0f);       
       table.setSpacingAfter(30.0f); 
       
       return table;
    } 
}
