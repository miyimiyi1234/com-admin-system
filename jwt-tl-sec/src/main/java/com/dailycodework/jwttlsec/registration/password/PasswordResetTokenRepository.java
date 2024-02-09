/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dailycodework.jwttlsec.registration.password;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author admlegall
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    
}
