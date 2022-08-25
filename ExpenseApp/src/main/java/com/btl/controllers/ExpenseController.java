/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.controllers;

import com.btl.pojo.Expense;
import com.btl.service.ExpenseService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:messages.properties")
public class ExpenseController {

    @Autowired
    private Environment env;
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expense")
    public String expense(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("expense", new Expense());
        
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", env.getProperty("page.key.10")));
        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        
        model.addAttribute("expenses", this.expenseService.getExpenses(params, pageSize, page));
        model.addAttribute("expenseCounter", this.expenseService.countExpense(params));
        
        Map<String, Integer> sizes = new HashMap<>();
        sizes.put(env.getProperty("page.key.10"), Integer.parseInt(env.getProperty("page.value.10")));
        sizes.put(env.getProperty("page.key.20"), Integer.parseInt(env.getProperty("page.value.20")));
        sizes.put(env.getProperty("page.key.all"), Integer.parseInt(env.getProperty("page.value.all")));
        model.addAttribute("sizes", sizes);
        
        LocalDate today = LocalDate.now();
        String start = today.withDayOfMonth(1).toString();
        String end = today.withDayOfMonth(today.getMonth().length(today.isLeapYear())).toString();
        
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", page);
        model.addAttribute("kw", params.getOrDefault("kw", ""));
        model.addAttribute("fd", params.getOrDefault("fromDate", start));
        model.addAttribute("td", params.getOrDefault("toDate", end));
        
        model.addAttribute("today", today.toString());
        
        return "expense";
    }
    
    @PostMapping("/expense")
    public String expenseProcess(Model model,
            @ModelAttribute(value = "expense") @Valid Expense expense,
            BindingResult result) {
        if (result.hasErrors()) {
            return "expense";
        }
        
        if (this.expenseService.addExpense(expense) == true)
            return "redirect:/expense";

        return "expense";
    }
    
}
