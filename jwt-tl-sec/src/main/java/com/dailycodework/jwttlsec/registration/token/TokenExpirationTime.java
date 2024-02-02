/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dailycodework.jwttlsec.registration.token;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author admlegall
 */
public  class TokenExpirationTime {
    
    private static final int EXPIRATION_TIME = 10;
 
    public static Date getExpirationTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}