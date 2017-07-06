    package com.validation;

import com.MenuView.MenuView;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author Barry Gray Kapelembe 
 */
 @Path ("Secured")
public class SecureResource {
     @GET
     @Path("")
     @Produces(MediaType.TEXT_PLAIN)
     public String secured(){
         MenuView feedback = new MenuView ();
         feedback.error("user is not login", "Please login");
         return null;
     }

}
