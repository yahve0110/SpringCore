package com.yahve.service;

import com.yahve.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    Map<Integer, User> userMap = new HashMap<Integer, User>();

    private int generatedUserId = 1;



    public User createUser(String login) {
        for (User user : userMap.values()) {
            if (user.getLogin().equals(login)) {
                throw new IllegalArgumentException("User with login " + login + " already exists");
            }
        }
        User user = new User(generatedUserId,login);
        userMap.put(generatedUserId, user);
        generatedUserId++;
        return user;
    }

    public User findUserById(int id) {
    User user =  userMap.get(id);
    if (user == null) {
        throw new IllegalArgumentException("User with id " + id + " not found");
    }
    return user;

    }

    public List<User> getAllUsers() {
        return userMap.values().stream().collect(Collectors.toList());
    }
}
