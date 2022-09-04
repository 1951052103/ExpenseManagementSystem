/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.controllers;

import com.btl.service.ExpenseService;
import com.btl.service.IncomeService;
import com.btl.service.MailService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
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
@ControllerAdvice
@PropertySource("classpath:messages.properties")
public class IndexController {

    @Autowired
    private IncomeService incomeService;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private Environment env;
    @Autowired
    private MailService mailService;

    @ModelAttribute
    public void commonAttributes(Model model, HttpSession session) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));

        List<Object[]> size = new ArrayList<>();
        size.add(new Object[]{env.getProperty("page.key.2"), Integer.parseInt(env.getProperty("page.value.2"))});
        size.add(new Object[]{env.getProperty("page.key.10"), Integer.parseInt(env.getProperty("page.value.10"))});
        size.add(new Object[]{env.getProperty("page.key.20"), Integer.parseInt(env.getProperty("page.value.20"))});
        size.add(new Object[]{env.getProperty("page.key.50"), Integer.parseInt(env.getProperty("page.value.50"))});
        size.add(new Object[]{env.getProperty("page.key.100"), Integer.parseInt(env.getProperty("page.value.100"))});
        size.add(new Object[]{env.getProperty("page.key.all"), Integer.parseInt(env.getProperty("page.value.all"))});

        model.addAttribute("sizes", size);
    }

    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        Map<String, String> myParams = new HashMap<>();

        String warning = "";
        BigDecimal currentMonthExpense, currentMonthIncome,
                lastMonthExpense, lastYearExpense,
                currentQuarterExpense, lastQuarterExpense;

        //current month
        LocalDate today = LocalDate.now();
        String start = today.withDayOfMonth(1).toString();
        String end = today.withDayOfMonth(today.getMonth().length(today.isLeapYear())).toString();

        myParams.put("fromDate", start);
        myParams.put("toDate", end);
        model.addAttribute("cmfd", start);
        model.addAttribute("cmtd", end);

        currentMonthExpense = converter(getExpense(myParams));
        currentMonthIncome = converter(getIncome(myParams));

        model.addAttribute("currentMonthExpense", currentMonthExpense);
        model.addAttribute("currentMonthIncome", currentMonthIncome);

        //last month
        start = today.minusMonths(1).withDayOfMonth(1).toString();
        end = today.minusMonths(1).withDayOfMonth(today.minusMonths(1).getMonth()
                .length(today.minusMonths(1).isLeapYear())).toString();

        myParams.put("fromDate", start);
        myParams.put("toDate", end);
        model.addAttribute("lmfd", start);
        model.addAttribute("lmtd", end);

        lastMonthExpense = converter(getExpense(myParams));

        model.addAttribute("lastMonthExpense", lastMonthExpense);

        //last year
        start = today.minusYears(1).withDayOfMonth(1).toString();
        end = today.minusYears(1).withDayOfMonth(today.minusYears(1).getMonth()
                .length(today.minusYears(1).isLeapYear())).toString();

        myParams.put("fromDate", start);
        myParams.put("toDate", end);
        model.addAttribute("lyfd", start);
        model.addAttribute("lytd", end);

        lastYearExpense = converter(getExpense(myParams));

        model.addAttribute("lastYearExpense", lastYearExpense);

        //current quarter
        int currentQuarter = today.get(IsoFields.QUARTER_OF_YEAR);
        start = today.withMonth(currentQuarter * 3).minusMonths(2).withDayOfMonth(1).toString();
        end = today.withMonth(currentQuarter * 3).withDayOfMonth(today.getMonth()
                .length(today.withMonth(currentQuarter * 3).isLeapYear())).toString();

        myParams.put("fromDate", start);
        myParams.put("toDate", end);
        model.addAttribute("cqfd", start);
        model.addAttribute("cqtd", end);

        currentQuarterExpense = converter(getExpense(myParams));

        model.addAttribute("currentQuarterExpense", currentQuarterExpense);

        //last quarter
        int lastQuarter = currentQuarter - 1;

        if (currentQuarter == 1) {
            lastQuarter = 4;
            start = today.minusYears(1).withMonth(lastQuarter * 3).minusMonths(2)
                    .withDayOfMonth(1).toString();
            end = today.minusYears(1).withMonth(lastQuarter * 3).withDayOfMonth(today.getMonth()
                    .length(today.minusYears(1).withMonth(lastQuarter * 3).isLeapYear())).toString();
        } else {
            start = today.withMonth(lastQuarter * 3).minusMonths(2)
                    .withDayOfMonth(1).toString();
            end = today.withMonth(lastQuarter * 3).withDayOfMonth(today.getMonth()
                    .length(today.withMonth(lastQuarter * 3).isLeapYear())).toString();
        }

        myParams.put("fromDate", start);
        myParams.put("toDate", end);
        model.addAttribute("lqfd", start);
        model.addAttribute("lqtd", end);

        lastQuarterExpense = converter(getExpense(myParams));

        model.addAttribute("lastQuarterExpense", lastQuarterExpense);

        //warning
        if (currentMonthExpense.compareTo(currentMonthIncome) > 0) {
            warning += env.getProperty("label.currentMonth.warning") + "<br/>";
            this.mailService.sendSimpleMessage(env.getProperty("label.warning"), env.getProperty("label.currentMonth.warning"));
        }
        if (currentMonthExpense.compareTo(lastMonthExpense) > 0 && !lastMonthExpense.equals(BigDecimal.ZERO)) {
            warning += env.getProperty("label.lastMonth.warning") + "<br/>";
            this.mailService.sendSimpleMessage(env.getProperty("label.warning"), env.getProperty("label.lastMonth.warning"));
        }
        if (currentMonthExpense.compareTo(lastYearExpense) > 0 && !lastYearExpense.equals(BigDecimal.ZERO)) {
            warning += env.getProperty("label.lastYear.warning") + "<br/>";
            this.mailService.sendSimpleMessage(env.getProperty("label.warning"), env.getProperty("label.lastYear.warning"));
        }
        if (currentQuarterExpense.compareTo(lastQuarterExpense) > 0 && !lastQuarterExpense.equals(BigDecimal.ZERO)) {
            warning += env.getProperty("label.lastQuarter.warning") + "<br/>";
            this.mailService.sendSimpleMessage(env.getProperty("label.warning"), env.getProperty("label.lastQuarter.warning"));
        }

        model.addAttribute("warning", warning);

        //Stats
        int month, year;
        String monthStats = params.getOrDefault("month", "");
        if (monthStats != null && !monthStats.isEmpty()) {
            year = Integer.parseInt(monthStats.substring(0, 4));
            month = Integer.parseInt(monthStats.substring(5, 7));
        } else {
            month = today.getMonthValue();
            year = today.getYear();
        }

        model.addAttribute("month", month);
        model.addAttribute("year", year);
        model.addAttribute("expenseStatsByMonth", this.expenseService.getExpenseStatsByMonth(month, year));
        model.addAttribute("expenseStatsByYear", this.expenseService.getExpenseStatsByYear(year));
        model.addAttribute("incomeStatsByYear", this.incomeService.getIncomeStatsByYear(year));

        return "index";
    }

    private BigDecimal getIncome(Map<String, String> params) {
        return this.incomeService.getTotalIncome(params);
    }

    private BigDecimal getExpense(Map<String, String> params) {
        return this.expenseService.getTotalExpense(params);
    }

    public static BigDecimal converter(BigDecimal bigDecimal) {
        return bigDecimal == null ? BigDecimal.ZERO : bigDecimal;
    }
}
