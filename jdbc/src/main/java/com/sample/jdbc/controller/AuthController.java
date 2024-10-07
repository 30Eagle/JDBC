package com.sample.jdbc.controller;

import com.sample.jdbc.model.User;
import com.sample.jdbc.service.UserService;

public class AuthController {
    
    // Dependency: UserService handles business logic related to users
    private UserService userService;

    // Constructor to initialize UserService
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Method to handle user registration
    public void register(String username, String email, String password) {
        // Check if the user already exists (using email or username)
        if (userService.userExists(email)) {
            System.out.println("User with this email already exists!");
        } else {
            // Create a new User object
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(password); // No password hashing here

            // Call UserService to register the new user
            boolean isRegistered = userService.registerUser(newUser);

            if (isRegistered) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Failed to register user.");
            }
        }
    }

    // Method to handle user login
    public int login(String email, String password) {
        // Find the user by email
        User user = userService.getUserByEmail(email);

        if (user != null) {
            // Check if the provided password matches the stored password
            if (password.equals(user.getPassword())) {
                System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
                return user.getUserId();
            } else {
                System.out.println("Invalid password. Please try again.");
                return -1;
            }
        } else {
            System.out.println("No user found with the given email.");
            return 0;
        }
    }
}
