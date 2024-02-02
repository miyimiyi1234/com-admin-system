/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dailycodework.jwttlsec.registration.token;

import com.dailycodework.jwttlsec.user.User;
import java.util.Optional;

/**
 *
 * @author admlegall
 */
public interface VerificationTokenService {
    public String validateToken(String token);
    
    void saveVerificationTokenForUser(User user, String token);
    
    Optional<VerificationToken> findByToken(String token);
    
 }