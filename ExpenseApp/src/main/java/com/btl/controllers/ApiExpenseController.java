/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.controllers;

import com.btl.pojo.Expense;
import com.btl.service.ExpenseService;
import java.math.BigDecimal;
import java.time.Instant;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/api")
public class ApiExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @DeleteMapping("/expense/{expenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "expenseId") int id) {
        this.expenseService.deleteExpense(id);
    }

    @PostMapping(path = "/expense/{expenseId}", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<Expense> update(@RequestBody Map<String, String> params,
            @PathVariable(value = "expenseId") int expenseId) {

        Expense expense = this.expenseService.getExpenseById(expenseId);

        BigDecimal amount = new BigDecimal(params.get("amount"));
        expense.setAmount(amount);
        expense.setPurpose(params.get("purpose"));
        expense.setDate(Date.valueOf(params.get("date")));
        expense.setDescription(params.get("description"));

        this.expenseService.updateExpense(expense);

        return new ResponseEntity<>(expense, HttpStatus.CREATED);

    }
}