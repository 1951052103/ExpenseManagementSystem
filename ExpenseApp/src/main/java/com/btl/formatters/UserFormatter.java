/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.formatters;

import com.btl.pojo.User;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author admin
 */
public class UserFormatter implements Formatter<User> {
    
    @Override
    public String print(User obj, Locale locale) {
        return String.valueOf(obj.getId());
    }

    @Override
    public User parse(String text, Locale locale) throws ParseException {
        User u = new User();
        u.setId(Integer.parseInt(text));
        return u;
    }
}
