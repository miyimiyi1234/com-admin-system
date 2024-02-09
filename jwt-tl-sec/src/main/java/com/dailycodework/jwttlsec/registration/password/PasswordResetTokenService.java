/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dailycodework.jwttlsec.registration.password;

import com.dailycodework.jwttlsec.user.User;

/**
 *
 * @author admlegall
 */
public interface PasswordResetTokenService {

    public void createPasswordRestTokenForUser(User user, String passwordResetToken);
    
}
