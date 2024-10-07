package com.sample.jdbc.service;

import com.sample.jdbc.dao.TaskDAO;
import com.sample.jdbc.model.Task;

import java.time.LocalDateTime;
import java.util.List;

public class TaskService {
    private TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public boolean addTask(int userId, String taskDescription) {
        Task task = new Task();
        task.setTaskDescription(taskDescription);
        task.setStatus(false); // Set initial status as pending
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setUserId(userId);
        return taskDAO.insertTask(task);
    }

    public boolean updateTask(Task task) {
        task.setUpdatedAt(LocalDateTime.now());
        return taskDAO.updateTask(task);
    }

    public boolean deleteTask(int taskId) {
        return taskDAO.deleteTask(taskId);
    }

    public List<Task> getTasksByUserId(int userId) {
        return taskDAO.getTasksByUserId(userId);
    }
}
