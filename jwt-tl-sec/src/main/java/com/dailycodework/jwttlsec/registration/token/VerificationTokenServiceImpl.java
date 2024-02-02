/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dailycodework.jwttlsec.registration.token;

import com.dailycodework.jwttlsec.user.User;
import com.dailycodework.jwttlsec.user.UserRepository;
import java.util.Calendar;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author admlegall
 */
@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService{
    
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    @Override
    public String validateToken(String token) {
        Optional<VerificationToken> theToken = verificationTokenRepository.findByToken(token);
        if (theToken.isEmpty()) {
            return "INVALID";
        }
        User user = theToken.get().getUser();
        Calendar calendar = Calendar.getInstance();
        if ((theToken.get().getExpirationTime().getTime()-calendar.getTime().getTime())<= 0) {
            return "EXPIRED";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "VALID";
    }

    @Override
    public void saveVerificationTokenForUser(User user, String token) {
        var  verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }
    
}
