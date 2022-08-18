/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.service.impl;

import com.btl.pojo.Expense;
import com.btl.repository.ExpenseRepository;
import com.btl.service.ExpenseService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class ExpenseServiceImpl implements ExpenseService{
    @Autowired
    private ExpenseRepository expenseRepository; 
    
    @Override
    public List<Expense> getExpenses(Map<String, String> params, int page) {
        return this.expenseRepository.getExpenses(params, page);
    }

    @Override
    public int countExpense(Map<String, String> map) {
        return this.expenseRepository.countExpense(map);
    }
    
}
