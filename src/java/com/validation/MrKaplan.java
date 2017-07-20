package com.validation;

import com.MenuView.MenuView;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Barry Gray Kapelembe
 */
public class MrKaplan {

    private MenuView out = new MenuView();

    public boolean isEmpty(String input) {
        boolean valid = false;
        if (input.trim().equals("")) {
            out.error("Input must not be empty", null);
            valid = true;

        }
        return valid;
    }

    /*public boolean isNumeric(String input) {
        boolean valid = false;
        if (input.matches("-?\\d+(\\.\\d+)?")) {

            valid = true;
        } else {
            out.error("This: "+Double.parseDouble(input)+" input must be numeric", null);
        }
        return valid;
    }*/
    
    public boolean isNumeric(String input){
  
     return (!input.matches("[0-9]+"));

    }
    public boolean isAnEmail(String input) {
        boolean valid = false;
        final Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(input);
        if (mat.matches()) {
            valid = true;
        } else {
            out.error("Input is not a valid email", null);
        }
        return valid;
    }

    public boolean stirngIsWithinRange(String input, int min, int max) {
        boolean valid = false;
        if (input.length() >= min && input.length() <= max) {
            valid = true;
        } else {
            out.error(input + " Too short", null);
        }
        return true;
    }

    public boolean isNull(String input) {
        boolean valid = false;
        if (input == null) {
            valid = true;
        }
        return valid;
    }

    public boolean isScript(String input) {
        boolean valid = false;
        final String script = "<script>";
        if (input.toLowerCase().contains(script)) {
            out.error(input + "Is not valid", null);
            valid = true;
        }
        return valid;
    }

    public boolean isAcceptableString(String input) {
        boolean valid = false;
        final Pattern pattern = Pattern.compile("^[A-Za-z-' ]++$");
        if (!pattern.matcher(input).matches()) {
            valid = false;
            out.error(input + " is not acceptable", null);
        } else {
            valid = true;
            System.out.println("acceptable string");
        }
        return valid;
    }

    public boolean isValidInput(String input) {
        boolean valid = false;
        if (basicCheck(input)) {
            if (stirngIsWithinRange(input, 2, 60)) {
                if (isAcceptableString(input)) {
                    valid = true;
                } 
            }
        }
        return valid;
    }

    public boolean isAcceptableAddress(String input) {
        boolean valid = false;
        Pattern p = Pattern.compile("[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-]");
        Matcher m = p.matcher(input);
        if (basicCheck(input)) {
            if (m.find()) {
                valid = false;
                out.error(input+ " must not have special charecters", input);
            } else {
                System.out.println("acceptable string");
                valid = true;
            }
        }

        return valid;
    }
    public boolean basicCheck(String input) {
        boolean valid = false;

        if (!isEmpty(input) && !isNull(input) && !isScript(input)) {
            valid = true;
        }

        return valid;
    }

    //getAddress()
    //"\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)"
    
    // for update validation 
    public String getColumId(String id){
        
        //viewListOfBabies:viewTable:2:firstName
        String columnID = "";
        int begin = id.lastIndexOf(':');
        
        /*while(begin < id.length()-1){
            columnID = columnID + id.charAt(begin+1);
        }*/
        for (int i = begin; i<id.length()-1;i++){
            System.out.println("----"+i);
            columnID += id.charAt(i+1);
            
        }
        System.out.println("Solution: "+columnID);
        return id;
    }
    
    public boolean isValidDate(Date date) {
        boolean valid = true;
        Date dt = new Date();
        boolean before = dt.after(date);
       /* if (before) {
            valid = true;
        } else {
            out.error("Date not recorgnised", null);
        }*/

        return valid;

    }
}
