/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.controllers;

import com.btl.pojo.Income;
import com.btl.service.IncomeService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author admin
 */
@Controller
public class IncomeController {
    @Autowired
    private Environment env;
    @Autowired
    private IncomeService incomeService;
    
    @GetMapping("/income")
    private String income(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("income", new Income());
        
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", env.getProperty("page.key.10")));
        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        
        model.addAttribute("incomes", this.incomeService.getIncomes(params, pageSize, page));
        model.addAttribute("IncomeCounter", this.incomeService.countIncome(params));
        
        LocalDate today = LocalDate.now();
        String start = today.withDayOfMonth(1).toString();
        String end = today.withDayOfMonth(today.getMonth().length(today.isLeapYear())).toString();
        
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", page);
        model.addAttribute("kw", params.getOrDefault("kw", ""));
        model.addAttribute("fd", params.getOrDefault("fromDate", start));
        model.addAttribute("td", params.getOrDefault("toDate", end));
        
        model.addAttribute("today", today.toString());
        
        return "income";
    }
    
    @PostMapping("/income")
    public String incomeProcess(Model model,
            @ModelAttribute(value = "income") @Valid Income income,
            BindingResult result) {
        if (result.hasErrors()) {
            return "income";
        }
        
        if (this.incomeService.addIncome(income) == true)
            return "redirect:/income";

        return "income";
    }
}
