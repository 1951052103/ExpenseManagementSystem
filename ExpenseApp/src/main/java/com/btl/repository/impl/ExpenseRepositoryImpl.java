/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.repository.impl;

import com.btl.pojo.CustomGroup;
import com.btl.pojo.Expense;
import com.btl.pojo.User;
import com.btl.repository.ExpenseRepository;
import com.btl.repository.UserRepository;
import com.btl.repository.GroupRepository;
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
import org.springframework.core.env.Environment;
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
public class ExpenseRepositoryImpl implements ExpenseRepository {

    @Autowired
    private Environment env;
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public List<Expense> getExpenses(Map<String, String> params, int pageSize, int page) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Expense> q = b.createQuery(Expense.class);

        Root root = q.from(Expense.class);
        q.select(root);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        List<Predicate> predicates = new ArrayList<>();

        Predicate p2 = b.equal(root.get("active").as(Boolean.class), Boolean.TRUE);
        predicates.add(p2);

        if (params != null && !params.isEmpty()) {
            ///////////////////////////////
            String groupId = params.get("groupId");
            if (groupId != null && !groupId.isEmpty()) {
                Predicate p = b.equal(root.get("groupId").as(CustomGroup.class),
                        this.groupRepository.getGroupById(Integer.parseInt(groupId)));
                predicates.add(p);
            }
            else {             
                Predicate p1 = b.equal(root.get("userId").as(User.class),
                        this.userRepository.getUserByUsername(currentPrincipalName));
                predicates.add(p1);
            }
            
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
        } else {
            Predicate p1 = b.equal(root.get("userId").as(User.class),
                        this.userRepository.getUserByUsername(currentPrincipalName));
            predicates.add(p1);
            
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
    public int countExpense(Map<String, String> params) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);

        Root root = q.from(Expense.class);
        q.select(b.count(root.get("id")));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        List<Predicate> predicates = new ArrayList<>();

        Predicate p2 = b.equal(root.get("active").as(Boolean.class), Boolean.TRUE);
        predicates.add(p2);

        if (params != null) {
            //////////////////
            String groupId = params.get("groupId");
            if (groupId != null && !groupId.isEmpty()) {
                Predicate p = b.equal(root.get("groupId").as(CustomGroup.class),
                        groupRepository.getGroupById(Integer.parseInt(groupId)));
                predicates.add(p);
            }
            else {             
                Predicate p1 = b.equal(root.get("userId").as(User.class),
                        this.userRepository.getUserByUsername(currentPrincipalName));
                predicates.add(p1);
            }
            
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
        } else {
            Predicate p1 = b.equal(root.get("userId").as(User.class),
                        this.userRepository.getUserByUsername(currentPrincipalName));
            predicates.add(p1);
            
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
    public BigDecimal getTotalExpense(Map<String, String> params) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> q = b.createQuery(BigDecimal.class);

        Root root = q.from(Expense.class);
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
        
        Predicate p6 = b.equal(root.get("confirmed").as(Boolean.class), Boolean.TRUE);
        predicates.add(p6);

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
    public boolean addExpense(Expense expense) {
        Session session = this.sessionFactory.getObject().getCurrentSession();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        expense.setUserId(userRepository.getUserByUsername(currentPrincipalName));
        expense.setActive(Boolean.TRUE);

        try {
            if (expense.getGroupId() == null || expense.getGroupId().getId() <= 0) {
                expense.setGroupId(null);
                expense.setApproved(Boolean.TRUE);
                expense.setConfirmed(Boolean.TRUE);
            } else {
                expense.setApproved(Boolean.FALSE);
                expense.setConfirmed(Boolean.FALSE);
            }

            session.save(expense);
            return true;
        } catch (Exception ex) {
            session.clear();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateExpense(Expense expense) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        
        try {
            if(expense.getGroupId() != null &&
                    groupRepository.checkCurrentUserInGroup( expense.getGroupId().getId() ).get(0)[1].equals(false) ) {
                return false;
            }

            session.clear();
            session.update(expense);
            return true;
        } catch (Exception ex) {
            session.clear();
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteExpense(int id) {
        Session session = this.sessionFactory.getObject().getCurrentSession();

        try {
            Expense expense = session.get(Expense.class, id);
            
            if(expense.getGroupId() != null &&
                    groupRepository.checkCurrentUserInGroup( expense.getGroupId().getId() ).get(0)[1].equals(false) ) {
                return false;
            }

            session.delete(expense);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Expense getExpenseById(int expenseId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        return session.get(Expense.class, expenseId);
    }
    
    @Override
    public List<Object[]> getUnconfirmedTotalExpenseOfUsersInGroup(Map<String, String> params) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root rootExpense = q.from(Expense.class);
        Root rootUser = q.from(User.class);
        
        List<Predicate> predicates = new ArrayList<>();

        Predicate p1 = b.equal(rootUser.get("id"), rootExpense.get("userId"));
        predicates.add(p1);
        
        Predicate p2 = b.equal(rootExpense.get("active").as(Boolean.class), Boolean.TRUE);
        predicates.add(p2);

        Predicate p5 = b.equal(rootExpense.get("approved").as(Boolean.class), Boolean.TRUE);
        predicates.add(p5);
        
        Predicate p6 = b.equal(rootExpense.get("confirmed").as(Boolean.class), Boolean.FALSE);
        predicates.add(p6);
        
        if (params != null) {
            String groupId = params.get("groupId");
            if (groupId != null && !groupId.isEmpty()) {
                Predicate p = b.equal(rootExpense.get("groupId").as(CustomGroup.class),
                        this.groupRepository.getGroupById(Integer.parseInt(groupId)));
                predicates.add(p);
            }
        }
        
        q.multiselect(b.sum(rootExpense.get("amount")), rootUser.get("username"));
        q.where(predicates.toArray(new Predicate[]{}));
        q.groupBy(rootExpense.get("userId"));
        q.orderBy(b.desc(rootUser.get("id")));
        
        Query query = session.createQuery(q);

        return query.getResultList();
    }
    
    @Override
    public List<Object[]> getExpenseStatsByMonth(int month, int year) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        
        Root root = q.from(Expense.class);
        
        List<Predicate> predicates = new ArrayList<>();
        
        Predicate p1 = b.equal(b.function("MONTH", Integer.class, root.get("date")), month);
        predicates.add(p1);
        
        Predicate p2 = b.equal(b.function("YEAR", Integer.class, root.get("date")), year);
        predicates.add(p2);
        
        q.where(predicates.toArray(new Predicate[]{}));
        
        q.multiselect(b.function("DAY", Integer.class, root.get("date")), b.sum(root.get("amount")));
        q.groupBy(b.function("DAY", Integer.class, root.get("date")));
        q.orderBy(b.asc( b.function("DAY", Integer.class, root.get("date"))));
        
        Query query = session.createQuery(q);
        return query.getResultList();
    }
    
    @Override
    public List<Object[]> getExpenseStatsByYear(int year) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        
        Root root = q.from(Expense.class);
        
        List<Predicate> predicates = new ArrayList<>();
        
        Predicate p2 = b.equal(b.function("YEAR", Integer.class, root.get("date")), year);
        predicates.add(p2);
        
        q.where(predicates.toArray(new Predicate[]{}));
        
        q.multiselect(b.function("MONTH", Integer.class, root.get("date")), b.sum(root.get("amount")));
        q.groupBy(b.function("MONTH", Integer.class, root.get("date")));
        q.orderBy(b.asc( b.function("MONTH", Integer.class, root.get("date"))));
        
        Query query = session.createQuery(q);
        return query.getResultList();
    }
}
