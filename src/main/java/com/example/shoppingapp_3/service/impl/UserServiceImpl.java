package com.example.shoppingapp_3.service.impl;

import com.example.shoppingapp_3.dao.UserDao;
import com.example.shoppingapp_3.domain.User;
import com.example.shoppingapp_3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    @Transactional
    public User getUser(String userName, String password) {
        return userDao.getUser(userName, password);
    }

    @Override
    @Transactional
    public boolean isValidUserName(String userName) {
        List<User> users = userDao.getUserByUserName(userName);
        if (users.isEmpty())
        {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean isValidEmail(String email) {
        List<User> users = userDao.getUserByEmail(email);
        if (users.isEmpty())
        {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void register(String userName, String password, String email) {
        userDao.insertUser(userName, email, password);
    }

    @Override
    @Transactional
    public User getUserByUserId(Long userId) {
        return null;
    }

    @Override
    @Transactional
    @Async("taskExecutor")
    public CompletableFuture<User> getUserByUserIdAsync(Long userId) {
        User user = userDao.getUserByUserId(userId);
        return CompletableFuture.completedFuture(user);
    }
}
