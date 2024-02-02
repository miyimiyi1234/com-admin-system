/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dailycodework.jwttlsec.registration;

import com.dailycodework.jwttlsec.event.RegistrationCompleteEvent;
import com.dailycodework.jwttlsec.registration.token.VerificationToken;
import com.dailycodework.jwttlsec.registration.token.VerificationTokenService;
import com.dailycodework.jwttlsec.user.User;
import com.dailycodework.jwttlsec.user.UserService;
import com.dailycodework.jwttlsec.utility.UrlUtility;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
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
}   
