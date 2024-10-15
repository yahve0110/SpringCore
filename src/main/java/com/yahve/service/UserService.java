package com.yahve.service;

import com.yahve.entity.User;

import java.util.List;

public interface UserService {
     User createUser(String login);
     User findUserById(int id);
     List<User>getAllUsers();
}
