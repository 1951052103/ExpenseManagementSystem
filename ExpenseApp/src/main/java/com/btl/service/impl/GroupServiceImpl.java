/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.service.impl;

import com.btl.pojo.CustomGroup;
import com.btl.pojo.User;
import com.btl.repository.GroupRepository;
import com.btl.service.GroupService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;
    
    @Override
    public boolean addGroup(CustomGroup group) {
        return this.groupRepository.addGroup(group);
    }
    
    @Override
    public CustomGroup getGroupById(int groupId) {
        return this.groupRepository.getGroupById(groupId);
    }

    @Override
    public boolean joinGroup(CustomGroup group) {
        return this.groupRepository.joinGroup(group);
    }

    @Override
    public List<Object[]> checkCurrentUserInGroup(int groupId) {
        return this.groupRepository.checkCurrentUserInGroup(groupId);
    }
    
    @Override
    public List<CustomGroup> getGroupsOfCurrentUser(Map<String, String> params, int pageSize, int page) {
        return this.groupRepository.getGroupsOfCurrentUser(params, page, page);
    }
    
    @Override
    public List<User> getUsersInGroup(Map<String, String> params, int pageSize, int page) {
        return this.groupRepository.getUsersInGroup(params, pageSize, page);
    }

    @Override
    public boolean deleteUserFromGroup(int groupId, String username) {
        return this.groupRepository.deleteUserFromGroup(groupId, username);
    }
}
