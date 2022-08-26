/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.controllers;

import com.btl.service.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author admin
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private Environment env;
    @Autowired
    private UserService userService;
    
    @GetMapping("/users")
    public String users(Model model, @RequestParam Map<String, String> params) {
        
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", env.getProperty("page.key.10")));
        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        
        model.addAttribute("users", this.userService.getUsers(params, pageSize, page));
        model.addAttribute("userCounter", this.userService.countUser(params));
        
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", page);
        model.addAttribute("kw", params.getOrDefault("kw", ""));
        
        System.out.println(this.userService.countUser(params));
        
        return "users";
    }
}
