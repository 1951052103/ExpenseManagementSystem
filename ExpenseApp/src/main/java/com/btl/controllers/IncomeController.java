/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.controllers;

import com.btl.service.IncomeService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
    @RequestMapping("/income")
    private String income(Model model, @RequestParam Map<String, String> params) {
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", env.getProperty("page.key.10")));
        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        
        model.addAttribute("incomes", this.incomeService.getIncomes(params, pageSize, page));
        model.addAttribute("IncomeCounter", this.incomeService.countIncome(params));
        
        Map<String, Integer> sizes = new HashMap<>();
        sizes.put(env.getProperty("page.key.10"), Integer.parseInt(env.getProperty("page.value.10")));
        sizes.put(env.getProperty("page.key.20"), Integer.parseInt(env.getProperty("page.value.20")));
        sizes.put(env.getProperty("page.key.all"), Integer.parseInt(env.getProperty("page.value.all")));
        model.addAttribute("sizes", sizes);
        
        LocalDate today = LocalDate.now();
        LocalDate start = today.withDayOfMonth(1);
        LocalDate end = today.withDayOfMonth(today.getMonth().length(today.isLeapYear()));
        
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", page);
        model.addAttribute("kw", params.getOrDefault("kw", ""));
        model.addAttribute("fd", params.getOrDefault("fromDate", start.toString()));
        model.addAttribute("td", params.getOrDefault("toDate", end.toString()));
        
        return "income";
    }
}
