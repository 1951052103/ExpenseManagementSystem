/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.repository.impl;

import com.btl.pojo.CustomGroup;
import com.btl.pojo.GroupUser;
import com.btl.pojo.User;
import com.btl.repository.GroupRepository;
import com.btl.repository.UserRepository;
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
public class GroupRepositoryImpl implements GroupRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean addGroup(CustomGroup group) {
        Session session = this.sessionFactory.getObject().getCurrentSession();

        group.setActive(Boolean.TRUE);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(group);
        groupUser.setUserId(this.userRepository.getUserByUsername(currentPrincipalName));
        groupUser.setIsLeader(Boolean.TRUE);
        groupUser.setActive(Boolean.TRUE);

        try {
            session.save(group);
            session.save(groupUser);
            return true;
        } catch (Exception ex) {
            session.clear();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public CustomGroup getGroupById(int groupId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        return session.get(CustomGroup.class, groupId);
    }

    @Override
    public boolean joinGroup(CustomGroup group) {
        Session session = this.sessionFactory.getObject().getCurrentSession();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if(this.checkCurrentUserInGroup(group.getId()) <=0 ) {
            GroupUser groupUser = new GroupUser();
            groupUser.setGroupId(group);
            groupUser.setUserId(this.userRepository.getUserByUsername(currentPrincipalName));
            groupUser.setIsLeader(Boolean.FALSE);
            groupUser.setActive(Boolean.TRUE);

            try {
                session.save(groupUser);
                return true;
            } catch (Exception ex) {
                session.clear();
                ex.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public int checkCurrentUserInGroup(int groupId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);

        Root root = q.from(GroupUser.class);
        q.select(b.count(root.get("id")));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        List<Predicate> predicates = new ArrayList<>();
        Predicate p1 = b.equal(root.get("userId").as(User.class),
                this.userRepository.getUserByUsername(currentPrincipalName));
        predicates.add(p1);

        Predicate p2 = b.equal(root.get("active").as(Boolean.class), Boolean.TRUE);
        predicates.add(p2);

        Predicate p3 = b.equal(root.get("groupId").as(CustomGroup.class), 
                this.getGroupById(groupId));
        predicates.add(p3);
        
        q.where(predicates.toArray(new Predicate[]{}));

        Query query = session.createQuery(q);

        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public List<CustomGroup> getGroups(Map<String, String> params, int pageSize, int page) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root rootGroup = q.from(CustomGroup.class);
        Root rootGroupUser = q.from(GroupUser.class);
        
        q.multiselect(rootGroup.get("id"), rootGroup.get("name"));
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        
        List<Predicate> predicates = new ArrayList<>();
        Predicate p1 = b.equal(rootGroupUser.get("userId").as(User.class),
                this.userRepository.getUserByUsername(currentPrincipalName));
        predicates.add(p1);
        
        Predicate p2 = b.equal(rootGroupUser.get("active").as(Boolean.class), Boolean.TRUE);
        predicates.add(p2);
        
        Predicate p3 = b.equal(rootGroup.get("active").as(Boolean.class), Boolean.TRUE);
        predicates.add(p3);
        
        Predicate p4 = b.equal(rootGroup.get("id"), rootGroupUser.get("groupId"));
        predicates.add(p4);
        
        if (params != null && !params.isEmpty()) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                Predicate p = b.like(rootGroup.get("name").as(String.class),
                        String.format("%%%s%%", kw));
                predicates.add(p);
            }
        }
        
        q.where(predicates.toArray(new Predicate[]{}));
        q.orderBy(b.desc(rootGroup.get("id")));
        
        Query query = session.createQuery(q);
        
        return query.getResultList();
    }
    
    @Override
    public List<User> getUsersInGroup(Map<String, String> params, int pageSize, int page) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        
        Root root = q.from(User.class);
        q.select(root);
        
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
        q.orderBy(b.desc(root.get("id")));
        
        Query query = session.createQuery(q);
        
        if (pageSize > 0) {
            int start = (page - 1) * pageSize;
            query.setFirstResult(start);
            query.setMaxResults(pageSize);
        }
        
        return query.getResultList();
    }
}
