/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.repository;

import com.btl.pojo.Expense;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface ExpenseRepository {
    List<Expense> getExpenses(Map<String, String> params, int page);
    int countExpense(Map<String, String> params);
}