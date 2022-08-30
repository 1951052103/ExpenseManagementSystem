/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.controllers;

import com.btl.pojo.User;
import com.btl.service.ExpenseService;
import com.btl.service.IncomeService;
import com.btl.service.UserService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@ControllerAdvice
public class IndexController {

    @Autowired
    private IncomeService incomeService;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private Environment env;

    @ModelAttribute
    public void commonAttributes(Model model, HttpSession session) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));

        Map<String, Integer> sizes = new HashMap<>();
        sizes.put(env.getProperty("page.key.10"), Integer.parseInt(env.getProperty("page.value.10")));
        sizes.put(env.getProperty("page.key.20"), Integer.parseInt(env.getProperty("page.value.20")));
        sizes.put(env.getProperty("page.key.all"), Integer.parseInt(env.getProperty("page.value.all")));
        model.addAttribute("sizes", sizes);
    }

    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {       
        Map<String, String> myParams = new HashMap<>();

        //current month
        LocalDate today = LocalDate.now();
        String start = today.withDayOfMonth(1).toString();
        String end = today.withDayOfMonth(today.getMonth().length(today.isLeapYear())).toString();

        myParams.put("fromDate", start);
        myParams.put("toDate", end);
        model.addAttribute("cmfd", start);
        model.addAttribute("cmtd", end);

        model.addAttribute("currentMonthIncome", getIncome(myParams));
        model.addAttribute("currentMonthExpense", getExpense(myParams));

        //last month
        start = today.minusMonths(1).withDayOfMonth(1).toString();
        end = today.minusMonths(1).withDayOfMonth(today.getMonth().length(today.isLeapYear())).toString();

        myParams.put("fromDate", start);
        myParams.put("toDate", end);
        model.addAttribute("lmfd", start);
        model.addAttribute("lmtd", end);

        model.addAttribute("lastMonthExpense", getExpense(myParams));

        //last year
        start = today.minusYears(1).withDayOfMonth(1).toString();
        end = today.minusYears(1).withDayOfMonth(today.getMonth().length(today.isLeapYear())).toString();

        myParams.put("fromDate", start);
        myParams.put("toDate", end);
        model.addAttribute("lyfd", start);
        model.addAttribute("lytd", end);

        model.addAttribute("lastYearExpense", getExpense(myParams));

        return "index";
    }

    private BigDecimal getIncome(Map<String, String> params) {
        return this.incomeService.getTotalIncome(params);
    }

    private BigDecimal getExpense(Map<String, String> params) {
        return this.expenseService.getTotalExpense(params);
    }
}
