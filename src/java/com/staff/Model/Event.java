package com.staff.Model;

import java.util.Date;

/**
 *
 * @author Barry Gray
 */
public interface Event {

    String getEventTitl();

    void setEventTitle(String eventTitle);

    int getId();

    void setId(int id);

    void setAllDaEvent(boolean allDayEvent);

    boolean getIsAllDayEvent();

    void setStartDateTime(Date startDate);

    Date getStartDateTime();

    void setEndDateTime(Date endDate);

    Date getEndDateTime();

}
