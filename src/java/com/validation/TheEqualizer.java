package com.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

}
