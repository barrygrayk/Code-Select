
package com.staff.Model;
import com.staff.Model.OthantileStaff;
import java.util.Date;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
/**
 *
 * @author efsan1
 */
@ManagedBean (name= "Authentication", eager= true)
@SessionScoped

public class Authentication extends OthantileStaff{
    
    private String userName;
    private String password;

    public Authentication(String firstname, String lastname, char gender, String phoneNumber, String address, String placeOfBirth, Date dateOfBirth, String emailAddress) {
        super(firstname, lastname, gender, phoneNumber, address, placeOfBirth, dateOfBirth, emailAddress);
    }
    
    //All this needs to account for validation 
    public boolean setUserName(String name){
       boolean ans = true;
       
       if(name.matches("[a-zA-Z]+"))
          this.userName = name;
       else 
            ans = false;
       return ans;
    }
    
    public void setPassword(String code){
        this.password = code;
    }
        
    public String getUserName(){
        return userName;
    }
    
    public String getPassword(){
        return password;
    }
    
    public boolean createUserName(){
        
        Random rnd = new Random();
        int num = rnd.nextInt(10) + 1;
        
        String username = super.getFirstname().substring(0,1).toLowerCase() + 
                                    super.getLastname().substring(0,3).toLowerCase() + num;
        
        return (setUserName(username))? true:false;
        
    }
    // obs: coming back for the hash and authentication functions
}
