/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.service.impl;

import com.btl.pojo.User;
import com.btl.repository.UserRepository;
import com.btl.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service("UserDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Environment env;

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(env.getProperty("message.user.notFound"));
        }

        Set<GrantedAuthority> authorities = new HashSet<>(); //Sửa trong btl
        authorities.add(new SimpleGrantedAuthority(user.getRole())); //Sửa trong btl

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }
    
    @Override
    public User getUserByUserId(int userId) {
        return this.userRepository.getUserByUserId(userId);
    }

    @Override
    public int countUserByUsername(String username) {
        return this.userRepository.countUserByUsername(username);
    }

    @Override
    public boolean addUser(User user) {
        return this.userRepository.addUser(user);
    }

    @Override
    public List<User> getUsers(Map<String, String> params, int pageSize, int page1) {
        return this.userRepository.getUsers(params, pageSize, page1);
    }
    
    @Override
    public int countUser(Map<String, String> params) {
        return this.userRepository.countUser(params);
    }

    @Override
    public boolean updateUser(User user) {
        return this.userRepository.updateUser(user);
    }
    
    @Override
    public boolean deleteUser(int userId) {
        return this.userRepository.deleteUser(userId);
    }
}
