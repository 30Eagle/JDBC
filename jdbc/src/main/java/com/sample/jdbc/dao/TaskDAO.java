package com.sample.jdbc.dao;

import com.sample.jdbc.model.Task;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/jfsd";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    // Method to insert a new task into the database
    public boolean insertTask(Task task) {
        String query = "INSERT INTO todotasks (task_description, status, created_at, updated_at, user_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, task.getTaskDescription());
            preparedStatement.setBoolean(2, task.isStatus());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(task.getCreatedAt()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(task.getUpdatedAt()));
            preparedStatement.setInt(5, task.getUserId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update an existing task in the database
    public boolean updateTask(Task task) {
        String query = "UPDATE todotasks SET task_description = ?, status = ?, updated_at = ? WHERE task_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, task.getTaskDescription());
            preparedStatement.setBoolean(2, task.isStatus());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(task.getUpdatedAt()));
            preparedStatement.setInt(4, task.getTaskId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a task from the database
    public boolean deleteTask(int taskId) {
        String query = "DELETE FROM todotasks WHERE task_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, taskId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve tasks by user ID
    public List<Task> getTasksByUserId(int userId) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM todotasks WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task();
                task.setTaskId(resultSet.getInt("task_id"));
                task.setTaskDescription(resultSet.getString("task_description"));
                task.setStatus(resultSet.getBoolean("status"));
                task.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                task.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                task.setUserId(resultSet.getInt("user_id"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
