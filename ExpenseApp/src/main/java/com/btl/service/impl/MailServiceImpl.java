/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.service.impl;

import com.btl.repository.MailRepository;
import com.btl.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private MailRepository mailRepository; 

    @Override
    public boolean sendSimpleMessage(String subject, String text) {
        return this.mailRepository.sendSimpleMessage(subject, text);
    }
}
