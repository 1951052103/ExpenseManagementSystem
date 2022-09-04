/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.repository.impl;

import com.btl.pojo.User;
import com.btl.repository.MailRepository;
import com.btl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository
@Transactional
public class MailRepositoryImpl implements MailRepository {

    @Autowired
    private Environment env;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean sendSimpleMessage(String subject, String text) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();

            User user = this.userRepository.getUserByUsername(currentPrincipalName);
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(env.getProperty("mail.username"));
                message.setTo(user.getEmail());
                message.setSubject(subject);
                message.setText(text);

                mailSender.send(message);
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
