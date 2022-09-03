/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.formatters;

import com.btl.pojo.CustomGroup;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author admin
 */
public class GroupFormatter implements Formatter<CustomGroup> {

    @Override
    public String print(CustomGroup obj, Locale locale) {
        return String.valueOf(obj.getId());
    }

    @Override
    public CustomGroup parse(String text, Locale locale) throws ParseException {
        CustomGroup g = new CustomGroup();
        g.setId(Integer.parseInt(text));
        return g;
    }
}