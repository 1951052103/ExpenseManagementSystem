/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.repository;

/**
 *
 * @author admin
 */
public interface MailRepository {
    boolean sendSimpleMessage(String subject, String text);
}
