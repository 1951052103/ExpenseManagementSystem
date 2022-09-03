/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.service;

import com.btl.pojo.Expense;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface ExpenseService {
    List<Expense> getExpenses(Map<String, String> params, int pageSize, int page);
    int countExpense(Map<String, String> params);
    boolean deleteExpense(int id);
    BigDecimal getTotalExpense(Map<String, String> params);
    boolean addExpense(Expense expense);
    boolean updateExpense(Expense expense);
    Expense getExpenseById(int expenseId);
    List<Object[]> getUnconfirmedTotalExpenseOfUsersInGroup(Map<String, String> params);
    List<Object[]> getExpenseStatsByMonth(int month, int year);
    List<Object[]> getExpenseStatsByYear(int year);
}