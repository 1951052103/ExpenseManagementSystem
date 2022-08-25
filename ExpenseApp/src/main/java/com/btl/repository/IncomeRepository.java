/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.repository;

import com.btl.pojo.Income;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface IncomeRepository {
    List<Income> getIncomes(Map<String, String> params, int pageSize, int page);
    int countIncome(Map<String, String> params);
    boolean deleteIncome(int id);
    BigDecimal getTotalIncome(Map<String, String> params);
    boolean addIncome(Income income);
    boolean updateIncome(Income income);
    Income getIncomeById(int incomeId);
}
