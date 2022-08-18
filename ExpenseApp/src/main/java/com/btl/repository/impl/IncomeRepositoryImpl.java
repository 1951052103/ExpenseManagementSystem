/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.repository.impl;

import com.btl.pojo.Income;
import com.btl.pojo.User;
import com.btl.repository.IncomeRepository;
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
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository
@Transactional
public class IncomeRepositoryImpl implements IncomeRepository{
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    public List<Income> getIncomes(Map<String, String> params, int page) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Income> q = b.createQuery(Income.class);
        
        Root root = q.from(Income.class);
        q.select(root);
        
        List<Predicate> predicates = new ArrayList<>();
        Predicate p1 = b.equal(root.get("userId").as(User.class), session.get(User.class, 1));
        predicates.add(p1);
        
        Predicate p2 = b.equal(root.get("active").as(Boolean.class), Boolean.TRUE);
        predicates.add(p2);

        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                Predicate p = b.like(root.get("source").as(String.class),
                        String.format("%%%s%%", kw));
                predicates.add(p);
            }

            String fd = params.get("fromDate");
            if (fd != null) {
                Predicate p = b.greaterThanOrEqualTo(root.get("date").as(Date.class),
                        Date.valueOf(fd));
                predicates.add(p);
            }

            String td = params.get("toDate");
            if (td != null) {
                Predicate p = b.lessThanOrEqualTo(root.get("date").as(Date.class),
                        Date.valueOf(td));
                predicates.add(p);
            }
        }
        
        q.where(predicates.toArray(new Predicate[]{}));
        
        q.orderBy(b.desc(root.get("id")));
        
        Query query = session.createQuery(q);
        
        return query.getResultList();
    }
    
}
