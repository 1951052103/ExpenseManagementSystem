/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.controllers;

import com.btl.service.ExpenseService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author admin
 */
@Controller
@PropertySource("classpath:messages.properties")
public class ExpenseController {

    @Autowired
    private Environment env;
    @Autowired
    private ExpenseService expenseService;

    @RequestMapping("/expense")
    public String expense(Model model, @RequestParam Map<String, String> params) {
        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        model.addAttribute("expenses", this.expenseService.getExpenses(params, page));
        model.addAttribute("expenseCounter", this.expenseService.countExpense(params));
        
        Map<String, Integer> sizes = new HashMap<>();

        sizes.put(env.getProperty("page.key.10"), Integer.parseInt(env.getProperty("page.value.10")));
        sizes.put(env.getProperty("page.key.20"), Integer.parseInt(env.getProperty("page.value.20")));
        sizes.put(env.getProperty("page.key.all"), Integer.parseInt(env.getProperty("page.value.all")));

        model.addAttribute("sizes", sizes);
        
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", env.getProperty("page.key.10")));
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("kw", params.getOrDefault("kw", ""));
        model.addAttribute("fd", params.getOrDefault("fromDate", ""));
        model.addAttribute("td", params.getOrDefault("toDate", ""));
        
        return "expense";
    }
    
}
