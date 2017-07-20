
package com.staff.Model;
import java.util.Date;

/**
 *
 * @author efsan1
 */

public class Message {
    private Date date;
    private int id;
    private String description;
    private String subject;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    
    public void setMessage(String message){
        this.description = message;
    }
    
    public String retrieveMessage(){
        return this.description;
    }
   
}
