/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.service;

import com.btl.pojo.Income;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface IncomeService {
    List<Income> getIncomes(Map<String, String> params, int page);
}
