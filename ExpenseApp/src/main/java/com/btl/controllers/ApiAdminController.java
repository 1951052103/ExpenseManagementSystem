/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.controllers;

import com.btl.pojo.User;
import com.btl.service.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/api")
public class ApiAdminController {
    @Autowired
    private UserService userService;
    
    @PostMapping(path = "/users/{userId}", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<User> update(@RequestBody Map<String, String> params,
            @PathVariable(value = "userId") int userId) {

        User user = this.userService.getUserByUserId(userId);
        user.setUsername(params.get("username"));
        user.setPassword(params.get("password"));
        user.setRole(params.get("role"));
        
        if (params.get("active").equals("true")) {
            user.setActive(true);
        } else {
            user.setActive(false);
        }
        
        if (this.userService.updateUser(user)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> delete(@PathVariable(value = "userId") int userId) {
        if (this.userService.deleteUser(userId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
