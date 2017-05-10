
package com.staff.Model;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
/**
 *
 * @author efsan1
 */
@ManagedBean (name= "Message", eager= true)
@SessionScoped

public class Message {
    private Date date;
    private String description;
    
    public void setMessage(String message){
        this.description = message;
    }
    
    public String retrieveMessage(){
        return this.description;
    }
   
}
