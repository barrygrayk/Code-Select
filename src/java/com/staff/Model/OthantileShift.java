package com.staff.Model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Barry Gray
 */
public class OthantileShift implements Shift {

    private int id;
    private Date shiftStartDate;
    private Date shiftStartTIme;
    private Date shiftEndDate;
    private Date shiftEndTIme;
    private String status;
    private String names;

    public OthantileShift(Date shiftStartDate, Date shiftStartTIme) {
        this.shiftStartDate = shiftStartDate;
        this.shiftStartTIme = shiftStartTIme;
    }

    public OthantileShift(int id, Date shiftStartDate, Date shiftStartTIme, Date shiftEndDate, Date shiftEndTIme, String status) {
        this.id = id;
        this.shiftStartDate = shiftStartDate;
        this.shiftStartTIme = shiftStartTIme;
        this.shiftEndDate = shiftEndDate;
        this.shiftEndTIme = shiftEndTIme;
        this.status = status;
    }

    public OthantileShift(int fKey, Date shitDate, Date shiftTime) {
        id = fKey;
        this.shiftStartDate = shitDate;
        this.shiftStartTIme = shiftTime;

    }

    public OthantileShift() {
       
    }

    @Override
    public int shitID() {
        return id;
    }

    @Override
    public boolean setShiftID(int id) {
        boolean set = false;
        if (id > 0) {
            this.id = id;
            set = true;
        }
        return set;
    }

    @Override
    public boolean setshiftdate(Date date) {
        boolean set = false;
        if (date != null) {
            shiftStartDate = date;
            set = true;
        }
        return set;
    }

    @Override
    public Date getShiftDate() {
        return shiftStartDate;
    }

    @Override
    public boolean setShiftTime(Date date) {
        boolean set = false;
        if (date != null) {
            shiftStartTIme = date;
            set = true;
        }
        return set;
    }

    @Override
    public Date getShiftTime() {
        return shiftStartTIme;
    }

    @Override
    public boolean setStatus(String status) {
        boolean set = false;
        if (status != null) {
            this.status = status;
            set = true;
        }
        return set;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public boolean setShiftndTime(String type) {
        boolean set = false;
        if (type != null) {
            if (type.equals("Single (8hrs)")) {
                shiftEndTIme = new Date(shiftStartTIme.getTime() + TimeUnit.HOURS.toMillis(8));
            } else {
                shiftEndTIme = new Date(shiftStartTIme.getTime() + TimeUnit.HOURS.toMillis(16));
            }
            set = true;
        }
        return set;
    }

    @Override
    public Date getShiftEndTime() {
        return shiftEndTIme;
    }

    @Override
    public boolean setShiftndTime(Date end) {
       boolean set =false;
       if (end != null){
           shiftEndTIme = end;
           set = false;
       }
       return set;
    }

    @Override
    public boolean setNames(String names) {
      boolean set= false;
      if(names != null){
          this.names = names;
          set = true;
      }
      return set;
    }

    @Override
    public String getName() {
        return names;
    }

}
