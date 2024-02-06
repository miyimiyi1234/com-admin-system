/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dailycodework.jwttlsec.event.listener;

import com.dailycodework.jwttlsec.event.RegistrationCompleteEvent;
import com.dailycodework.jwttlsec.registration.token.VerificationTokenService;
import com.dailycodework.jwttlsec.user.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 *
 * @author admlegall
 */
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final VerificationTokenService tokenService;
    private final JavaMailSender mailSender;

    private User user;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //1.get the user
        user = event.getUser();
        //2.generate a token for the user
        String vToken = UUID.randomUUID().toString();
        //3.save the token for the user
        tokenService.saveVerificationTokenForUser(user, vToken);
        //4.build the verification url
        String url = event.getConfimationUrl() + "/registration/verifyEmail?token=" + vToken;
        try {
            //5.send the email to the user
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            Logger.getLogger(RegistrationCompleteEventListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "Users Verification Service";   
        String mailContent = "<p> Hi, " + user.getFirstName() + ", </p>"
                + "<p>Thank you for registering with us," + ""
                + "Please, follow the link below to complete your registration.</p>"
                + "<a href=\"" + url + "\">Verify your email to activate your account</a>"
                + "<p> Thank you <br> Users Registration Portal Service";
        emailMessage(subject, senderName, mailContent, mailSender, user);
    }

    private static void emailMessage(String subject, String senderName,
            String mailContent, JavaMailSender mailSender, User theUser)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("dailycodeworks@gmail.com", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

}
