/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dailycodework.jwttlsec.event;

import com.dailycodework.jwttlsec.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author admlegall
 */

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private final User user;
    private final String confimationUrl;
    public RegistrationCompleteEvent(User user, String confirmationUrl){
        super(user);
        this.user = user;
        this.confimationUrl = confirmationUrl;
    }
}
