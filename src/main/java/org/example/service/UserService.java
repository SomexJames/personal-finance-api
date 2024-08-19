package org.example.service;

import org.example.model.User;
import org.example.model.Account;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Map<String, User> userRepository = new HashMap<>();

    public User createUser(User user) {
        userRepository.put(user.getId(), user);
        return user;
    }

    public Account addAccount(String userId, Account account) {
        User user = userRepository.get(userId);
        if (user != null) {
            user.addAccount(account);
            return account;
        }
        return null;
    }

    public User getUserById(String userId) {
        return userRepository.get(userId);
    }
}
