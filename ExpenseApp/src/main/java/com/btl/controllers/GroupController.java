/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.controllers;

import com.btl.pojo.CustomGroup;
import com.btl.pojo.GroupUser;
import com.btl.service.GroupService;
import com.btl.service.UserService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author admin
 */
@Controller
public class GroupController {
    
    @Autowired
    private GroupService groupService;
    
    @GetMapping("/group")
    public String group(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("group", new CustomGroup());
        model.addAttribute("groupUser", new GroupUser());
        
        model.addAttribute("groups", this.groupService.getGroups(params, 0, 0));
        
        model.addAttribute("kw", params.getOrDefault("kw", ""));
        
        return "group";
    }
    
    @PostMapping("/group")
    public String groupProcess(Model model,
            @ModelAttribute(value = "group") @Valid CustomGroup group,
            BindingResult result) {
        if (result.hasErrors()) {
            return "group";
        }
        
        if (this.groupService.addGroup(group) == true)
            return "redirect:/group";

        return "group";
    }
    
    @PostMapping("/groupUser")
    public String groupUserProcess(Model model,
            @ModelAttribute(value = "groupUser") @Valid GroupUser groupUser,
            BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/group";
        }
        
        if (this.groupService.joinGroup( groupUser.getGroupId() ) == true) {
            return "redirect:/group";
        }
        
        return "redirect:/group";
    }
    
    @GetMapping("/group/{groupId}")
    public String groupDetails(@PathVariable(value = "groupId") int groupId,
            Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("group", this.groupService.getGroupById(groupId));
        model.addAttribute("users", this.groupService.getUsersInGroup(params,0,0));
        
        return "group-details";
    }
}
