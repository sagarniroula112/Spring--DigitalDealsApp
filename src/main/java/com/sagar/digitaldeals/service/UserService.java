package com.sagar.digitaldeals.service;

import com.sagar.digitaldeals.model.User;

import java.util.List;

public interface UserService {
    User getByUsernameAndPassword(String username, String password);
    User addUser(User user);
    void deleteUser(int id);
    void updateUser(User user);
    User getUserById(int id);
    List<User> getAllUsers();
}
