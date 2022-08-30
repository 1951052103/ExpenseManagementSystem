/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.controllers;

import com.btl.pojo.Income;
import com.btl.service.IncomeService;
import java.math.BigDecimal;
import java.sql.Date;
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
public class ApiIncomeController {
    @Autowired
    private IncomeService incomeService;
    
    @DeleteMapping("/income/{incomeId}")
    public ResponseEntity<?> delete(@PathVariable(value = "incomeId") int id) {
        if(this.incomeService.deleteIncome(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    
    @PostMapping(path = "/income/{incomeId}", produces = {
        MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<Income> update(@RequestBody Map<String, String> params,
            @PathVariable(value = "incomeId") int incomeId) {

        Income income = this.incomeService.getIncomeById(incomeId);

        BigDecimal amount = new BigDecimal(params.get("amount"));
        income.setAmount(amount);
        income.setSource(params.get("source"));
        income.setDate(Date.valueOf(params.get("date")));
        income.setDescription(params.get("description"));

        if (params.get("approved").equals("true")) {
            income.setApproved(true);
        } else {
            income.setApproved(false);
        }

        if (params.get("confirmed").equals("true")) {
            income.setConfirmed(true);
        } else {
            income.setConfirmed(false);
        }
        
        if(this.incomeService.updateIncome(income)) {
            return new ResponseEntity<>(income, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
