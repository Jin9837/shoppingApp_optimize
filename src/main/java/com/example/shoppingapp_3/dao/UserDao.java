package com.example.shoppingapp_3.dao;

import com.example.shoppingapp_3.domain.User;

import java.util.List;

public interface UserDao {
    List<User> getUserByUserName(String userName);

    List<User> getUserByEmail(String email);

    User getUserByUserId(Long userId);

    void insertUser(String userName, String email, String password);

    User getUser(String userName, String password);
}
