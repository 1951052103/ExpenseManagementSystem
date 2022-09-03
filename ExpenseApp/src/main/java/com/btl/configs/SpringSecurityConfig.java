/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.configs;

import com.btl.handlers.LoginSuccessHandler;
import com.btl.handlers.LogoutSuccessHandler;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author admin
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.btl.controllers",
    "com.btl.repository",
    "com.btl.service",
    "com.btl.handlers",
    "com.btl.validator",
    "com.btl.formatters"
})
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "drnh9htgv",
                        "api_key", "538624196835262",
                        "api_secret", "du3wpYdHCGQn1nKjoIkjz85g3SY",
                        "secure", true));
        return cloudinary;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password");
        http.formLogin().successHandler(loginSuccessHandler);

        http.logout().logoutSuccessHandler(logoutSuccessHandler);

        http.exceptionHandling()
                .accessDeniedPage("/login?accessDenied");

        http.authorizeRequests().antMatchers("/").authenticated()
                .antMatchers("/expense/**").authenticated()
                .antMatchers("/income/**").authenticated()
                .antMatchers("/group/**").authenticated()
                .antMatchers("/admin/**")
                .access("hasAuthority('ADMIN')");

        http.csrf().disable();
    }
}
