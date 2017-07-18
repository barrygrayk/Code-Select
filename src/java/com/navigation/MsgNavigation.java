package com.navigation;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * 
 * @author Barry Gray
 */
@ManagedBean(name="MsgNav", eager = true)
@ViewScoped
public class MsgNavigation {
    private String page;
    
    @PostConstruct 
    public void init() {
         page = "inventoryAlerts"; //  Default include.
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
