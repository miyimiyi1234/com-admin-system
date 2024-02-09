/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dailycodework.jwttlsec.registration;

import com.dailycodework.jwttlsec.event.RegistrationCompleteEvent;
import com.dailycodework.jwttlsec.event.listener.RegistrationCompleteEventListener;
import com.dailycodework.jwttlsec.registration.password.PasswordResetTokenService;
import com.dailycodework.jwttlsec.registration.token.VerificationToken;
import com.dailycodework.jwttlsec.registration.token.VerificationTokenService;
import com.dailycodework.jwttlsec.user.User;
import com.dailycodework.jwttlsec.user.UserService;
import com.dailycodework.jwttlsec.utility.UrlUtility;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author admlegall
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {
     private final UserService userService;
     private final ApplicationEventPublisher publisher;
     private final VerificationTokenService tokenService;
     private final PasswordResetTokenService passwordResetTokenService;
     private final RegistrationCompleteEventListener eventListener;
     
     @GetMapping("/registration-form")
     public String showRegistrationForm(Model model){
         model.addAttribute("user",new RegistrationRequest());
         return "registration";
     }
     
     @PostMapping("/register")
     public String registerUser(@ModelAttribute("user")RegistrationRequest registration,HttpServletRequest request){
         User user = userService.registerUser(registration);
         //publish the verification email event here
         publisher.publishEvent(new RegistrationCompleteEvent(user,UrlUtility.getApplicationUrl(request)));
         return "redirect:/registration/registration-form?success";
     }
     
     @GetMapping("/verifyEmail")
     public String verifyEmail(@RequestParam("token") String token){
        Optional<VerificationToken> theToken = tokenService.findByToken(token);
         if (theToken.isPresent() && theToken.get().getUser().isEnabled()) {
             return "redirect:/login?verified";
         }
         String verificationResult =  tokenService.validateToken(token);
         switch (verificationResult.toLowerCase()) {
             case "expired":
                 return "redirect:/error?expired";
             case "valid":
                 return "redirect:/login?valid";
             default:
                 return "redirect:/error?invalid";
         }
     }
     
     @GetMapping("/forgot-password-request")
     public String forgotPassword(){
         return "forgot-password-form";
     }
     
     @PostMapping("/forgot-password")
     public String resetPasswordRequest(HttpServletRequest request, Model model){
         String email = request.getParameter("email");
         User user = userService.findByEmail(email);
         if (user == null) {
             return "redirect:/registration/forgot-password-request?not_found";
         }
         String passwordResetToken = UUID.randomUUID().toString();
         passwordResetTokenService.createPasswordRestTokenForUser(user, passwordResetToken);
         //send verification email for the user
         String url = UrlUtility.getApplicationUrl(request)+"/registration/password-reset-form?token="+passwordResetToken;
         try {
             eventListener.sendPasswordVerificationEmail(url);
         } catch (MessagingException | UnsupportedEncodingException e) {
             model.addAttribute("error",e.getMessage());
         }
         return "redirect:/registration/forgot-password-request?success";
     }
     
     
     @GetMapping("/reset-password-form")
     public String passwordResetForm(@RequestParam("token") String token, Model model){
         model.addAttribute("token",token);
         return "password-reset-form"; 
     }
}   
