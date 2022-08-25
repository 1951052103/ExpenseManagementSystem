/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.service.impl;

import com.btl.pojo.Expense;
import com.btl.pojo.User;
import com.btl.repository.ExpenseRepository;
import com.btl.repository.UserRepository;
import com.btl.service.ExpenseService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class ExpenseServiceImpl implements ExpenseService{
    @Autowired
    private ExpenseRepository expenseRepository; 
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public List<Expense> getExpenses(Map<String, String> params, int pageSize, int page) {
        return this.expenseRepository.getExpenses(params, pageSize, page);
    }

    @Override
    public int countExpense(Map<String, String> map) {
        return this.expenseRepository.countExpense(map);
    }

    @Override
    public boolean deleteExpense(int id) {
        return this.expenseRepository.deleteExpense(id);
    }

    @Override
    public BigDecimal getTotalExpense(Map<String, String> params) {
        return this.expenseRepository.getTotalExpense(params);
    }

    @Override
    public boolean addExpense(Expense expense) {
        return this.expenseRepository.addExpense(expense);
    }

    @Override
    public boolean updateExpense(Expense expense) {
        return this.expenseRepository.updateExpense(expense);
    }
    
    @Override
    public Expense getExpenseById(int expenseId) {
        return this.expenseRepository.getExpenseById(expenseId);
    }
}
