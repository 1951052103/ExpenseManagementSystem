/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.controllers;

import com.btl.service.IncomeService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author admin
 */
@Controller
public class IncomeController {
    @Autowired
    private IncomeService incomeService;
    
    @RequestMapping("/income")
    private String income(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("incomes", this.incomeService.getIncomes(params, 0));
        
        return "income";
    }
}
