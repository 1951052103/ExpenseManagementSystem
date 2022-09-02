/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.service.impl;

import com.btl.pojo.Income;
import com.btl.repository.IncomeRepository;
import com.btl.service.IncomeService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Override
    public List<Income> getIncomes(Map<String, String> params, int pageSize, int page) {
        return this.incomeRepository.getIncomes(params, pageSize, page);
    }

    @Override
    public int countIncome(Map<String, String> params) {
        return this.incomeRepository.countIncome(params);
    }

    @Override
    public boolean deleteIncome(int id) {
        return this.incomeRepository.deleteIncome(id);
    }

    @Override
    public BigDecimal getTotalIncome(Map<String, String> params) {
        return this.incomeRepository.getTotalIncome(params);
    }

    @Override
    public boolean addIncome(Income income) {
        return this.incomeRepository.addIncome(income);
    }

    @Override
    public boolean updateIncome(Income income) {
        return this.incomeRepository.updateIncome(income);
    }

    @Override
    public Income getIncomeById(int incomeId) {
        return this.incomeRepository.getIncomeById(incomeId);
    }
    
    @Override
    public List<Object[]> getUnconfirmedTotalIncomeOfUsersInGroup(Map<String, String> params) {
        return this.incomeRepository.getUnconfirmedTotalIncomeOfUsersInGroup(params);
    }
}