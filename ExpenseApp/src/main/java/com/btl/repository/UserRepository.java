/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.repository;

import com.btl.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface UserRepository {
    User getUserByUsername(String username);
    User getUserByUserId(int userId);
    int countUserByUsername(String username);
    boolean addUser(User user);
    List<User> getUsers(Map<String, String> params, int pageSize, int page);
    int countUser(Map<String, String> params);
    boolean updateUser(User user);
    boolean deleteUser(int userId);
}
