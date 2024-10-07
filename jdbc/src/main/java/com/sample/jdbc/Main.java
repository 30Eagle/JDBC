package com.sample.jdbc;

import com.sample.jdbc.controller.AuthController;
import com.sample.jdbc.controller.TaskController;
import com.sample.jdbc.dao.TaskDAO;
import com.sample.jdbc.dao.UserDAO;
import com.sample.jdbc.service.TaskService;
import com.sample.jdbc.service.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        AuthController authController = new AuthController(userService);

        TaskDAO taskDAO = new TaskDAO();
        TaskService taskService = new TaskService(taskDAO);
        TaskController taskController = new TaskController(taskService);

        Scanner scanner = new Scanner(System.in);
        int choice;
        int taskChoice;
        int loggedInUserId = -1; // To keep track of the logged-in user

        do {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // User Registration
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    authController.register(username, email, password);
                    break;

                case 2:
                    // User Login
                    System.out.print("Enter email: ");
                    email = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                    loggedInUserId = authController.login(email, password);

                    // If login is successful, display task-related options
                    if (loggedInUserId > 0) {
                        do {
                            System.out.println("\n--- Task Menu ---");
                            System.out.println("1. Create Task");
                            System.out.println("2. Update Task");
                            System.out.println("3. Delete Task");
                            System.out.println("4. Search Tasks");
                            System.out.println("5. Logout");
                            System.out.print("Choose an option: ");
                            taskChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            switch (taskChoice) {
                                case 1:
                                    // Create Task
                                    System.out.print("Enter task description: ");
                                    String description = scanner.nextLine();
                                    taskController.addTask(loggedInUserId, description);
                                    break;

                                case 2:
                                    // Update Task
                                    System.out.print("Enter task ID to update: ");
                                    int updateTaskId = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline
                                    System.out.print("Enter new task description: ");
                                    String newDescription = scanner.nextLine();
                                    System.out.print("Is the task completed? (true/false): ");
                                    boolean completed = scanner.nextBoolean();
                                    taskController.updateTask(updateTaskId, newDescription, completed);
                                    break;

                                case 3:
                                    // Delete Task
                                    System.out.print("Enter task ID to delete: ");
                                    int deleteTaskId = scanner.nextInt();
                                    taskController.deleteTask(deleteTaskId);
                                    break;

                                case 4:
                                    // Search Tasks
                                    taskController.searchTask(loggedInUserId);
                                    break;

                                case 5:
                                    System.out.println("Logging out...");
                                    break;

                                default:
                                    System.out.println("Invalid option. Please try again.");
                                    break;
                            }
                        } while (taskChoice != 5);
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (choice != 3);

        scanner.close();
    }
}
