/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profile.Controller;


import com.child.Model.Child;
import com.db.connection.DBConnection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Barry Gray Kapelembe
 */
@ManagedBean(name="ViewChild", eager =true)
@SessionScoped
public class ViewBabyController implements Serializable {
    private List <Child> child = new ArrayList <>();

    public ViewBabyController() {
    }

    public List<Child> getChild() {
        DBConnection connect = new DBConnection ();
        child = connect.getChildren();
        return child;
    }
       
}
