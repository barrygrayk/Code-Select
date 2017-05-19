package com.staff.Controllers;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;
 
@ManagedBean(name = "limitTimelineRangeView")
@ViewScoped
public class LimitTimelineRangeView implements Serializable {
 
    private TimelineModel model;
 
    private Date min;
    private Date max;
    private long zoomMin;
    private long zoomMax;
 
    @PostConstruct
    public void init() {
        Calendar cal1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));  
        Date now = new Date();
        model = new TimelineModel();
 
        Calendar cal = Calendar.getInstance();
        cal.set(2017, Calendar.MAY, 25, 0, 0, 0);
        model.add(new TimelineEvent("First", cal.getTime()));
 
        cal.set(2017, Calendar.MAY, 26, 0, 0, 0);
        model.add(new TimelineEvent("Last", cal.getTime()));
 
        // lower limit of visible range
        cal.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
        min = cal.getTime();
 
        // upper limit of visible range
        cal.set(2020, Calendar.DECEMBER, 31, 0, 0, 0);
        max = cal.getTime();
 
        // one day in milliseconds for zoomMin
        zoomMin = 1000L * 60 * 60 * 24;
 
        // about three months in milliseconds for zoomMax
        zoomMax = 1000L * 60 * 60 * 24 * 31 * 3;
    }
 
    public TimelineModel getModel() {
        return model;
    }
 
    public Date getMin() {
        return min;
    }
 
    public Date getMax() {
        return max;
    }
 
    public long getZoomMin() {
        return zoomMin;
    }
 
    public long getZoomMax() {
        return zoomMax;
    }
     
}