package com.sample.jdbc.dao;

import com.sample.jdbc.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/jfsd"; 
    private static final String USERNAME = "root"; 
    private static final String PASSWORD = "root"; 

    // Method to insert a new user into the database
    public boolean insertUser(User user) {
        String query = "INSERT INTO todo (username, email, password) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword()); // Storing plain password

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if a user was successfully inserted

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of an error
        }
    }

    // Method to retrieve a user by email
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM todo WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Create and return a User object if a matching user is found
                User user = new User();
                user.setUserId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password")); // Get plain password
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no user is found
    }
}
