package com.navigation;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * 
 * @author Barry Gray Kapelembe 
 */
@ManagedBean(name="Include") 
@ViewScoped
public class Bean implements Serializable{
    private String page;
    
    @PostConstruct 
    public void init() {
         page = "default"; //  Default include.
     }

   public String getPage() {
         System.out.println("getting________ "+page);
        return page;
    }

    public void setPage(String page) {
        System.out.println("__Setting____"+page);
        this.page = page;
    }
    
    
    

}
