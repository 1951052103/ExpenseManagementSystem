/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.validator;

import com.btl.pojo.User;
import com.btl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author admin
 */
@Component
public class SpringUserValidator implements Validator {
    @Autowired
    private UserService userService;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User u = (User) target;

        if (!u.getPassword().trim().equals(u.getConfirmPassword().trim())) {
            errors.rejectValue("confirmPassword", "form.confirmPassword.errMsg");
        }

        if (userService.countUserByUsername(u.getUsername()) > 0) {
            errors.rejectValue("username", "form.username.existedMsg");
        }
    }
}
