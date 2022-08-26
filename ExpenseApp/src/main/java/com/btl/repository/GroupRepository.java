/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.repository;

import com.btl.pojo.CustomGroup;
import com.btl.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface GroupRepository {
    boolean addGroup(CustomGroup group);
    CustomGroup getGroupById(int groupId);
    boolean joinGroup(CustomGroup group);
    int checkCurrentUserInGroup(int groupId);
    List<CustomGroup> getGroupsOfCurrentUser(Map<String, String> params, int pageSize, int page);
    List<User> getUsersInGroup(Map<String, String> params, int pageSize, int page);
}
