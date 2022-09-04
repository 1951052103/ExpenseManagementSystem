/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.repository.impl;

import com.btl.pojo.User;
import com.btl.repository.UserRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author admin
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private Cloudinary cloudinary;
    
    @Override
    public User getUserByUsername(String username) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        
        Root root = q.from(User.class);
        q.select(root);
        
        List<Predicate> predicates = new ArrayList<>();
        Predicate p2 = b.equal(root.get("active").as(Boolean.class), Boolean.TRUE);
        predicates.add(p2);

        Predicate p3 = b.equal(root.get("username"), username);
        predicates.add(p3);
        q.where( predicates.toArray(new Predicate[]{}) );

        Query query = session.createQuery(q);

        return (User) query.getSingleResult();
    }
    
    @Override
    public User getUserByUserId(int userId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        
        Root root = q.from(User.class);
        q.select(root);
        
        List<Predicate> predicates = new ArrayList<>();

        Predicate p3 = b.equal(root.get("id"), userId);
        predicates.add(p3);
        
        q.where( predicates.toArray(new Predicate[]{}) );

        Query query = session.createQuery(q);

        return (User) query.getSingleResult();
    }
    
    @Override
    public int countUserByUsername(String username){
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        
        Root root = q.from(User.class);
        q.select(b.count(root.get("id")));
        
        q.where( b.equal(root.get("username"), username) );
        Query query = session.createQuery(q);

        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public boolean addUser(User user) {
        Session session = this.sessionFactory.getObject().getCurrentSession();

        try {
            if (user.getRole().equals(User.UserRole.ADMIN.toString())) {
                throw new IOException();
            }
            
            user.setUsername(user.getUsername().trim());
            user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword().trim()));
            user.setActive(Boolean.TRUE);
            user.setRegistrationDate(Date.valueOf(LocalDate.now()));
            
            if (user.getFile().getSize() > 0) {
                Map m = this.cloudinary.uploader().upload(user.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(m.get("secure_url").toString());
            }
            
            session.save(user);
            return true;
        } 
        catch (Exception ex) {
            session.clear();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getUsers(Map<String, String> params, int pageSize, int page) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        
        Root root = q.from(User.class);
        q.select(root);
        
        List<Predicate> predicates = new ArrayList<>();
//        Predicate p2 = b.equal(root.get("active").as(Boolean.class), Boolean.TRUE);
//        predicates.add(p2);
        
        if (params != null && !params.isEmpty()) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                Predicate p = b.like(root.get("username").as(String.class),
                        String.format("%%%s%%", kw));
                predicates.add(p);
            }
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
    public int countUser(Map<String, String> params) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        
        Root root = q.from(User.class);
        q.select(b.count(root.get("id")));
        
        List<Predicate> predicates = new ArrayList<>();
        Predicate p2 = b.equal(root.get("active").as(Boolean.class), Boolean.TRUE);
        predicates.add(p2);
        
        if (params != null && !params.isEmpty()) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                Predicate p = b.like(root.get("username").as(String.class),
                        String.format("%%%s%%", kw));
                predicates.add(p);
            }
        }
        
        q.where(predicates.toArray(new Predicate[]{}));
        
        Query query = session.createQuery(q);
        
        return Integer.parseInt(query.getSingleResult().toString());
    }
    
    @Override
    public boolean updateUser(User user) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        
        try {
            user.setUsername(user.getUsername().trim());
            System.out.println(user.getUsername().trim());
            user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword().trim()));
            
            session.clear();
            session.update(user);
            return true;
        } catch (Exception ex) {
            session.clear();
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteUser(int userId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();

        try {
            User user = this.getUserByUserId(userId);

            session.clear();
            session.delete(user);
            return true;
        } catch (Exception ex) {
            session.clear();
            ex.printStackTrace();
            return false;
        }
    }
}
