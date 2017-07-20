package com.staff.Model;

import java.util.Date;

/**
 * 
 * @author Barry Gray
 */
public class AnEvent implements Event {
    private int id;
    private Date startDate;
    private Date endDate;
    private boolean allDayEvent;
    private String eventTitle;

    @Override
    public int getId() {
       return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public void  setAllDaEvent(boolean allDayEvent) {
      this.allDayEvent = allDayEvent;
    }

    @Override
    public boolean getIsAllDayEvent() {
       return  allDayEvent;
    }

    @Override
    public void setStartDateTime(Date startDate) {
        this.startDate =startDate;
    }

    @Override
    public Date getStartDateTime() {
      return startDate;
    }

    @Override
    public void setEndDateTime(Date endDate) {
        this.endDate =endDate;
    }

    @Override
    public Date getEndDateTime() {
        return endDate;
    }

    @Override
    public String getEventTitl() {
       return eventTitle;
    }

    @Override
    public void setEventTitle(String eventTitle) {
        this.eventTitle =eventTitle;
    }
    
}
