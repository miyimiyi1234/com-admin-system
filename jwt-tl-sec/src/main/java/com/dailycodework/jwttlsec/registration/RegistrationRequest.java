/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dailycodework.jwttlsec.registration;

import com.dailycodework.jwttlsec.user.Role;
import java.util.Collection;
import lombok.Data;

/**
 *
 * @author admlegall
 */
@Data
public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Collection<Role> roles;
    
    
}
