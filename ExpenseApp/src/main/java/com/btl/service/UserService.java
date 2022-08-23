/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.btl.pojo.User;

/**
 *
 * @author admin
 */
public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);
}
