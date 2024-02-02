/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dailycodework.jwttlsec.registration.token;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author admlegall
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
     Optional<VerificationToken> findByToken(String token);
}
