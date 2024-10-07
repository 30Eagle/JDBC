package com.sample.jdbc.service;

import com.sample.jdbc.dao.UserDAO;
import com.sample.jdbc.model.User;

public class UserService {

    private UserDAO userDAO;

    // Constructor to initialize UserDAO
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Check if a user already exists by email
    public boolean userExists(String email) {
        return userDAO.getUserByEmail(email) != null;
    }

    // Register a new user
    public boolean registerUser(User user) {
        return userDAO.insertUser(user);
    }

    // Get a user by email
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public boolean checkPassword(String enteredPassword, String storedPassword) {
        return enteredPassword.equals(storedPassword);
}
}
