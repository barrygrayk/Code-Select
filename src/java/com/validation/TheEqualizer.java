package com.validation;

import android.R.string;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Barry Gray Kapelembe
 */
public class TheEqualizer {

    public String toUperAndLower(String input) {
        String toLower = input.toLowerCase();
        String equi = toLower.substring(0, 1).toUpperCase() + toLower.substring(1);
        return equi;
    }

    public Date dateFormatter(String format, Date date)  {
        DateFormat formatter = new SimpleDateFormat(format);
        Date today = date;
        Date todayWithZeroTime = null;
        try {
            todayWithZeroTime = formatter.parse(formatter.format(today));
        } catch (ParseException ex) {
            Logger.getLogger(TheEqualizer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return todayWithZeroTime;
    }
    
    // efsan1 : date format
    public Date formatDate(Date date){
        Date formattedDate = null;
        LocalDate localdate = null;
        SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        // convert date to string
        String formattedDateInString = fm.format(date);
        // convert string to local date
        localdate = LocalDate.parse(formattedDateInString, formatter);
            
        return formattedDate = java.sql.Date.valueOf(localdate);
    }
    
    // convert date to local date
    public LocalDate convertdateToLocalDate(Date param){
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy");
        
        return LocalDate.parse(fm.format(param), formatter);
    }

}
