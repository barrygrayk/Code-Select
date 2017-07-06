/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Email;

/**
 *
 * @author Barry Gray
 */
public interface IEmail {
    void  sendFromGMail(String from, String pass, String[] to, String subject, String body);
    
}
