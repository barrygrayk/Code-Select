
package com.record.Audit;

import java.util.Date;

/**
 *
 * @author efsan1
 */
public class CaregiverRecordAudit {
    
    private int recordAuditId;
    private Date date;
    private String caregiver;
    private Date time;

    public int getRecordAuditId() {
        return recordAuditId;
    }

    public void setRecordAuditId(int recordAuditId) {
        this.recordAuditId = recordAuditId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(String caregiver) {
        this.caregiver = caregiver;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
    
    
}
