package com.staff.Model;

import java.util.Date;

/**
 *
 * @author Barry Gray
 */
public interface Shift {

    int shitID();

    boolean setShiftID(int id);

    boolean setshiftdate(Date date);

    Date getShiftDate();

    boolean setShiftTime(Date date);

    Date getShiftTime();

    boolean setShiftndTime(String type);
    
    boolean setShiftndTime(Date end);

    Date getShiftEndTime();

    boolean setStatus(String status);

    String getStatus();
    
    boolean setNames (String names);
    
    String getName ();

}
