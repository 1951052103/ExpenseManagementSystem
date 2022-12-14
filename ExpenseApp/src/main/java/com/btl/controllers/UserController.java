/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.controllers;

import com.btl.pojo.User;
import com.btl.service.UserService;
import com.btl.validator.SpringUserValidator;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author admin
 */
@Controller
public class UserController {

    @Autowired
    private SpringUserValidator userValidator;
    @Autowired
    private UserService userService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());

        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(Model model,
            @ModelAttribute(value = "user") @Valid User u,
            BindingResult result) {
        if (result.hasErrors()) {
            return "login";
        }

        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String registerProcess(Model model,
            @ModelAttribute(value = "user") @Valid User u,
            BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        
        if (this.userService.addUser(u) == true)
            return "redirect:/login";

        return "register";
    }
}
