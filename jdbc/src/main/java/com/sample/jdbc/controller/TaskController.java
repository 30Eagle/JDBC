package com.sample.jdbc.controller;

import com.sample.jdbc.model.Task;
import com.sample.jdbc.service.TaskService;

import java.util.List;
import java.util.Scanner;

public class TaskController {
    private TaskService taskService;
    private Scanner scanner;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
        this.scanner = new Scanner(System.in);
    }

    public void addTask(int userId, String taskDescription) {
        boolean isAdded = taskService.addTask(userId, taskDescription);
        if (isAdded) {
            System.out.println("Task added successfully!");
        } else {
            System.out.println("Failed to add task.");
        }
    }

    public void updateTask(int taskId, String newDescription, boolean status) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setTaskDescription(newDescription);
        task.setStatus(status);
        boolean isUpdated = taskService.updateTask(task);
        if (isUpdated) {
            System.out.println("Task updated successfully!");
        } else {
            System.out.println("Failed to update task.");
        }
    }

    public void deleteTask(int taskId) {
        boolean isDeleted = taskService.deleteTask(taskId);
        if (isDeleted) {
            System.out.println("Task deleted successfully!");
        } else {
            System.out.println("Failed to delete task.");
        }
    }

    public void searchTask(int userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);
        if (tasks.isEmpty()) {
            System.out.println("No tasks found for the user.");
        } else {
            System.out.println("Tasks for the user:");
            for (Task task : tasks) {
                System.out.println("Task ID: " + task.getTaskId());
                System.out.println("Description: " + task.getTaskDescription());
                System.out.println("Status: " + (task.isStatus() ? "Completed" : "Pending"));
                System.out.println("Created At: " + task.getCreatedAt());
                System.out.println("Updated At: " + task.getUpdatedAt());
                System.out.println();
            }
        }
    }
}
