package com.todolist.app;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main entry point for the TO-DO List application.
 * Handles login, menu navigation, and calls User/FileManager logic.
 * 
 * @author CS132 Team (Faith, Jason, Jonathan, and Bryant)
 */

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Login
        System.out.println("=== To-Do List App ===");
        System.out.print("Enter username to log in: ");
        String username = scanner.nextLine().trim();

        while (username.isEmpty()) {
            System.out.print("Username cannot be empty. Enter username: ");
            username = scanner.nextLine().trim();
        }

          User user = new User(username);

        // Load tasks
        boolean loaded = FileManager.loadTasks(user);

        if (!loaded || user.getTaskCount() == 0) {
            System.out.println("No tasks available.");
        }

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                case "v":
                    viewCurrentTasks(user);
                    break;
                case "2":
                case "a":
                    addTask(scanner, user);
                    break;
                case "3":
                case "c":
                    markTaskCompleted(scanner, user);
                    break;
                case "4":
                case "r":
                    removeTask(scanner, user);
                    break;
                case "5":
                case "d":
                    viewCompletedTasks(user);
                    break;
                case "6":
                case "s":
                    FileManager.saveTasks(user);
                    System.out.println("Tasks saved. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

     private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1) View current to-do list (V)");
        System.out.println("2) Add new task (A)");
        System.out.println("3) Mark task as completed (C)");
        System.out.println("4) Remove task (R)");
        System.out.println("5) View completed tasks (D)");
        System.out.println("6) Save and exit (S)");
        System.out.print("Select an option: ");
    }

     private static void viewCurrentTasks(User user) {
        ArrayList<Task> tasks = user.getIncompleteTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            System.out.println((i + 1) + ") " + t.getTitle());
            System.out.println("   Description: " + t.getDescription());
        }
    }

 private static void addTask(Scanner scanner, User user) {
        System.out.print("Enter task title: ");
        String title = scanner.nextLine().trim();

        while (title.isEmpty()) {
            System.out.print("Title cannot be empty. Enter task title: ");
            title = scanner.nextLine().trim();
        }

        System.out.print("Enter task description: ");
        String description = scanner.nextLine().trim();

        user.addTask(new Task(title, description));
        System.out.println("Task added.");
    }

private static void markTaskCompleted(Scanner scanner, User user) {
    ArrayList<Task> tasks = user.getIncompleteTasks();
    if (tasks.isEmpty()) {
        System.out.println("No tasks to complete.");
        return;

    }

    for (int i = 0; i < tasks.size(); i++) {
        System.out.println((i + 1) + ") " + tasks.get(i).getTitle());
    }
  System.out.print("Enter task number: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            tasks.get(choice).markAsCompleted();
            System.out.println("Task completed.");
        } catch (Exception e) {
            System.out.println("Invalid selection.");
        }
    }

 private static void removeTask(Scanner scanner, User user) {
        ArrayList<Task> tasks = user.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks to remove.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            System.out.println((i + 1) + ") " +
                    (t.isCompleted() ? "[X] " : "[ ] ") + t.getTitle());
        }

        System.out.print("Enter task number: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            user.removeTask(choice);
            System.out.println("Task removed.");
        } catch (Exception e) {
            System.out.println("Invalid selection.");
        }
    }


    private static void viewCompletedTasks(User user) {
        ArrayList<Task> tasks = user.getCompletedTasks();
        if (tasks.isEmpty()) {
            System.out.println("No completed tasks.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            System.out.println((i + 1) + ") " + t.getTitle());
            System.out.println("   Description: " + t.getDescription());
        }
    }
}
