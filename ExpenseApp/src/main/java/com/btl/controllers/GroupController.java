/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.controllers;

import com.btl.pojo.CustomGroup;
import com.btl.pojo.GroupUser;
import com.btl.service.ExpenseService;
import com.btl.service.GroupService;
import com.btl.service.IncomeService;
import com.btl.service.UserService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    private Environment env;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private UserService userService;

    @GetMapping("/group")
    public String group(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("group", new CustomGroup());
        model.addAttribute("groupUser", new GroupUser());

        model.addAttribute("groups", this.groupService.getGroupsOfCurrentUser(params, 0, 0));

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

        if (this.groupService.addGroup(group) == true) {
            return "redirect:/group";
        }

        return "group";
    }

    @PostMapping("/groupUser")
    public String groupUserProcess(Model model,
            @ModelAttribute(value = "groupUser") @Valid GroupUser groupUser,
            BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/group";
        }

        if (this.groupService.joinGroup(groupUser.getGroupId()) == true) {
            return "redirect:/group";
        }

        return "redirect:/group";
    }

    @GetMapping("/group/{groupId}")
    public String groupDetails(@PathVariable(value = "groupId") int groupId,
            Model model, @RequestParam Map<String, String> params) {

        List<Object[]> check = this.groupService.checkCurrentUserInGroup(groupId);      
        if (Integer.parseInt(check.get(0)[0].toString()) > 0) {
            model.addAttribute("joined", true);
            model.addAttribute("group", this.groupService.getGroupById(groupId));

            params.put("groupId", String.valueOf(groupId));
            
            int pageSize = Integer.parseInt(params.getOrDefault("pageSize", env.getProperty("page.key.10")));
            int page = Integer.parseInt(params.getOrDefault("page", "1"));
            
            model.addAttribute("users", this.groupService.getUsersInGroup(params, pageSize, page));
            model.addAttribute("expenses", this.expenseService.getExpenses(params, pageSize, page));
            model.addAttribute("incomes", this.incomeService.getIncomes(params, pageSize, page));
            
            int userCounter = this.userService.countUser(params);
            int expenseCounter = this.expenseService.countExpense(params);
            int IncomeCounter = this.incomeService.countIncome(params);
            
            int counter = Math.max(userCounter, Math.max(expenseCounter, IncomeCounter));
            
            model.addAttribute("counter", counter);
            
            LocalDate today = LocalDate.now();
            String start = today.withDayOfMonth(1).toString();
            String end = today.withDayOfMonth(today.getMonth().length(today.isLeapYear())).toString();
            
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("page", page);
            model.addAttribute("kw", params.getOrDefault("kw", ""));
            model.addAttribute("fd", params.getOrDefault("fromDate", start));
            model.addAttribute("td", params.getOrDefault("toDate", end));
            
            if(check.get(0)[1].equals(true) ) {
                model.addAttribute("isLeader", true);
            }
            
        }

        System.out.println(groupService.checkCurrentUserInGroup(groupId).get(0)[1]);

        return "group-details";
    }
}
