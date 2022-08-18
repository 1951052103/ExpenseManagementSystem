/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.repository.impl;

import com.btl.pojo.Expense;
import com.btl.pojo.User;
import com.btl.repository.ExpenseRepository;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository
@Transactional
public class ExpenseRepositoryImpl implements ExpenseRepository {
    
    @Autowired
    private Environment env;
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    public List<Expense> getExpenses(Map<String, String> params, int page) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Expense> q = b.createQuery(Expense.class);
        
        Root root = q.from(Expense.class);
        q.select(root);
        
        List<Predicate> predicates = new ArrayList<>();
        Predicate p1 = b.equal(root.get("userId").as(User.class), session.get(User.class, 1));
        predicates.add(p1);
        
        Predicate p2 = b.equal(root.get("active").as(Boolean.class), Boolean.TRUE);
        predicates.add(p2);

        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                Predicate p = b.like(root.get("purpose").as(String.class),
                        String.format("%%%s%%", kw));
                predicates.add(p);
            }

            String fd = params.get("fromDate");
            if (fd != null && !fd.isEmpty()) {
                Predicate p = b.greaterThanOrEqualTo(root.get("date").as(Date.class),
                        Date.valueOf(fd));
                predicates.add(p);
            }

            String td = params.get("toDate");
            if (td != null && !td.isEmpty()) {
                Predicate p = b.lessThanOrEqualTo(root.get("date").as(Date.class),
                        Date.valueOf(td));
                predicates.add(p);
            }
        }
        
        q.where(predicates.toArray(new Predicate[]{}));
        q.orderBy(b.desc(root.get("id")));
        Query query = session.createQuery(q);
        
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", env.getProperty("page.key.10")));
        if (pageSize > 0) {
            int start = (page - 1) * pageSize;
            query.setFirstResult(start);
            query.setMaxResults(pageSize);
        }
        
        return query.getResultList();
    }

    @Override
    public int countExpense(Map<String, String> params) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        
        Root root = q.from(Expense.class);
        q.select(b.count(root.get("id")));
        
        List<Predicate> predicates = new ArrayList<>();
        Predicate p1 = b.equal(root.get("userId").as(User.class), session.get(User.class, 1));
        predicates.add(p1);
        
        Predicate p2 = b.equal(root.get("active").as(Boolean.class), Boolean.TRUE);
        predicates.add(p2);

        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                Predicate p = b.like(root.get("purpose").as(String.class),
                        String.format("%%%s%%", kw));
                predicates.add(p);
            }

            String fd = params.get("fromDate");
            if (fd != null && !fd.isEmpty()) {
                Predicate p = b.greaterThanOrEqualTo(root.get("date").as(Date.class),
                        Date.valueOf(fd));
                predicates.add(p);
            }

            String td = params.get("toDate");
            if (td != null && !td.isEmpty()) {
                Predicate p = b.lessThanOrEqualTo(root.get("date").as(Date.class),
                        Date.valueOf(td));
                predicates.add(p);
            }
        }
        
        q.where(predicates.toArray(new Predicate[]{}));

        Query query = session.createQuery(q);

        return Integer.parseInt(query.getSingleResult().toString());
    }
}
