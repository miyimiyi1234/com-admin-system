/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dailycodework.jwttlsec.user;

import java.util.List;
import java.util.Optional;
import com.dailycodework.jwttlsec.registration.RegistrationRequest;

/**
 *
 * @author admlegall
 */
public interface UserService {

    List<User> getAllUsers();

    User registerUser(RegistrationRequest registrationRequest);

    User findByEmail(String email);
}
