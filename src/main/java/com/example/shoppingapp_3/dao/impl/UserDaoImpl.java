package com.example.shoppingapp_3.dao.impl;

import com.example.shoppingapp_3.dao.UserDao;
import com.example.shoppingapp_3.domain.Permission;
import com.example.shoppingapp_3.domain.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;


@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Override
    public List<User> getUserByUserName(String userName) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("From User WHERE username = :username", User.class);
        query.setParameter("username", userName);
        // executes the query against the database and returns a List of User objects that match the query
        return query.getResultList();
    }

    @Override
    public List<User> getUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultList();
    }

    @Override
    public User getUserByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE userId = :userId", User.class);
        query.setParameter("userId", userId);
        return query.uniqueResult();
    }

    @Override
    public void insertUser(String userName, String email, String password) {
        User user = new User();
        user.setUsername(userName);
        user.setEmail(email);
        user.setPassword(password);

        Session session = sessionFactory.getCurrentSession();
        Query<Permission> query = session.createQuery("FROM Permission p WHERE p.role = 'buyer'", Permission.class);
        Permission permission = query.uniqueResult();
        Set<Permission> ps = new HashSet<>();
        ps.add(permission);
        user.setPermissions(ps);

        session.save(user);

    }

    @Override
    public User getUser(String userName, String password) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE username = :username AND password = :password", User.class);
        query.setParameter("username", userName);
        query.setParameter("password", password);

        List<User> resultList = query.getResultList();

        return resultList.isEmpty() ? null : resultList.get(0);
    }
}
