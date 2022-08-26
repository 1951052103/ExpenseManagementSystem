/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.repository.impl;

import com.btl.pojo.Income;
import com.btl.pojo.User;
import com.btl.repository.IncomeRepository;
import com.btl.repository.UserRepository;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
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
public class IncomeRepositoryImpl implements IncomeRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Income> getIncomes(Map<String, String> params, int pageSize, int page) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Income> q = b.createQuery(Income.class);

        Root root = q.from(Income.class);
        q.select(root);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        List<Predicate> predicates = new ArrayList<>();
        Predicate p1 = b.equal(root.get("userId").as(User.class),
                this.userRepository.getUserByUsername(currentPrincipalName));
        predicates.add(p1);

        Predicate p2 = b.equal(root.get("active").as(Boolean.class), Boolean.TRUE);
        predicates.add(p2);
        
        Predicate p5 = b.equal(root.get("approved").as(Boolean.class), Boolean.TRUE);
        predicates.add(p5);

        if (params != null && !params.isEmpty()) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                Predicate p = b.like(root.get("source").as(String.class),
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
        } else {
            LocalDate today = LocalDate.now();
            LocalDate start = today.withDayOfMonth(1);
            LocalDate end = today.withDayOfMonth(today.getMonth().length(today.isLeapYear()));

            Predicate p3 = b.greaterThanOrEqualTo(root.get("date").as(Date.class),
                    Date.valueOf(start));
            predicates.add(p3);

            Predicate p4 = b.lessThanOrEqualTo(root.get("date").as(Date.class),
                    Date.valueOf(end));
            predicates.add(p4);
        }

        q.where(predicates.toArray(new Predicate[]{}));
        q.orderBy(b.desc(root.get("id")));

        Query query = session.createQuery(q);

        if (pageSize > 0) {
            int start = (page - 1) * pageSize;
            query.setFirstResult(start);
            query.setMaxResults(pageSize);
        }

        return query.getResultList();
    }

    @Override
    public int countIncome(Map<String, String> params) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);

        Root root = q.from(Income.class);
        q.select(b.count(root.get("id")));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        List<Predicate> predicates = new ArrayList<>();
        Predicate p1 = b.equal(root.get("userId").as(User.class),
                this.userRepository.getUserByUsername(currentPrincipalName));
        predicates.add(p1);

        Predicate p2 = b.equal(root.get("active").as(Boolean.class), Boolean.TRUE);
        predicates.add(p2);

        Predicate p5 = b.equal(root.get("approved").as(Boolean.class), Boolean.TRUE);
        predicates.add(p5);

        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                Predicate p = b.like(root.get("source").as(String.class),
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
        } else {
            LocalDate today = LocalDate.now();
            LocalDate start = today.withDayOfMonth(1);
            LocalDate end = today.withDayOfMonth(today.getMonth().length(today.isLeapYear()));

            Predicate p3 = b.greaterThanOrEqualTo(root.get("date").as(Date.class),
                    Date.valueOf(start));
            predicates.add(p3);

            Predicate p4 = b.lessThanOrEqualTo(root.get("date").as(Date.class),
                    Date.valueOf(end));
            predicates.add(p4);
        }

        q.where(predicates.toArray(new Predicate[]{}));

        Query query = session.createQuery(q);

        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public boolean deleteIncome(int id) {
        Session session = this.sessionFactory.getObject().getCurrentSession();

        try {
            Income in = session.get(Income.class, id);
            session.delete(in);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public BigDecimal getTotalIncome(Map<String, String> params) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> q = b.createQuery(BigDecimal.class);

        Root root = q.from(Income.class);
        q.select(b.sum(root.get("amount")));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        List<Predicate> predicates = new ArrayList<>();
        Predicate p1 = b.equal(root.get("userId").as(User.class),
                this.userRepository.getUserByUsername(currentPrincipalName));
        predicates.add(p1);

        Predicate p2 = b.equal(root.get("active").as(Boolean.class), Boolean.TRUE);
        predicates.add(p2);

        Predicate p5 = b.equal(root.get("approved").as(Boolean.class), Boolean.TRUE);
        predicates.add(p5);

        if (params != null && !params.isEmpty()) {
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

        return (BigDecimal) query.getSingleResult();
    }

    @Override
    public boolean addIncome(Income income) {
        Session session = this.sessionFactory.getObject().getCurrentSession();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        income.setUserId(userRepository.getUserByUsername(currentPrincipalName));
        income.setActive(Boolean.TRUE);

        try {
            if (income.getGroupId() == null || income.getGroupId().getId() <= 0) {
                income.setGroupId(null);
                income.setApproved(Boolean.TRUE);
            } else {
                income.setApproved(Boolean.FALSE);
            }

            session.save(income);
            return true;
        } catch (Exception ex) {
            session.clear();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateIncome(Income income) {
        Session session = this.sessionFactory.getObject().getCurrentSession();

        try {
            if(income.getGroupId() != null && income.getGroupId().getId() > 0) {
                return false;
            }
            
            session.update(income);
            return true;
        } catch (Exception ex) {
            session.clear();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Income getIncomeById(int incomeId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        return session.get(Income.class, incomeId);
    }
}
