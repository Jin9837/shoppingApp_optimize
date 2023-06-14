package com.example.shoppingapp_3.service;

import com.example.shoppingapp_3.domain.User;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    User getUser(String userName, String password);

    @Transactional
    boolean isValidUserName(String userName);

    @Transactional
    boolean isValidEmail(String email);

    @Transactional
    void register(String userName, String password, String email);

    @Transactional
    User getUserByUserId(Long userId);

    @Transactional
    CompletableFuture<User> getUserByUserIdAsync(Long userId);
}
