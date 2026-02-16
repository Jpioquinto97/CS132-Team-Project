package com.todolist.app;

import java.util.Scanner;

/**
 * Main application class for the To-Do List program
 * Handles user interface and menu navigation
 * 
 * @author CS132 Team (Jonathon, Bryant, Faith, Jason)
 */
public class ToDoListApp {
    private static User currentUser;
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== To-Do List Application ===\n");
        
        // TODO: Implement login system
        login();
        
        // Main menu loop
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    viewTasks();
                    break;
                case 2:
                    addTask();
                    break;
                case 3:
                    completeTask();
                    break;
                case 4:
                    removeTask();
                    break;
                case 5:
                    viewCompletedTasks();
                    break;
                case 6:
                    saveAndExit();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Displays the main menu options
     */
    private static void displayMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. View To-Do List");
        System.out.println("2. Add New Task");
        System.out.println("3. Mark Task as Completed");
        System.out.println("4. Remove Task");
        System.out.println("5. View Completed Tasks");
        System.out.println("6. Save and Exit");
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Gets user's menu choice
     * @return Selected menu option
     */
    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid input
        }
    }
    
    /**
     * Handles user login
     * TODO: Implement actual login logic
     */
    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        currentUser = new User(username);
        System.out.println("Welcome, " + username + "!\n");
        
        // TODO: Load user's saved tasks from file
    }
    
    /**
     * Displays all current (incomplete) tasks
     */
    private static void viewTasks() {
        System.out.println("\n=== Your To-Do List ===");
        // TODO: Implement task viewing logic
        System.out.println("No tasks available.");
    }
    
    /**
     * Adds a new task to the list
     */
    private static void addTask() {
        System.out.print("\nEnter task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        
        // TODO: Create new Task object and add to user's list
        System.out.println("Task added successfully!");
    }
    
    /**
     * Marks a task as completed
     */
    private static void completeTask() {
        System.out.println("\n=== Mark Task as Completed ===");
        // TODO: Implement task completion logic
        System.out.println("Feature coming soon!");
    }
    
    /**
     * Removes a task from the list
     */
    private static void removeTask() {
        System.out.println("\n=== Remove Task ===");
        // TODO: Implement task removal logic
        System.out.println("Feature coming soon!");
    }
    
    /**
     * Displays all completed tasks
     */
    private static void viewCompletedTasks() {
        System.out.println("\n=== Completed Tasks ===");
        // TODO: Implement completed task viewing logic
        System.out.println("No completed tasks.");
    }
    
    /**
     * Saves all data and exits the program
     */
    private static void saveAndExit() {
        System.out.println("\nSaving your tasks...");
        // TODO: Save tasks to file using FileManager
        System.out.println("Tasks saved successfully!");
        System.out.println("Goodbye!");
    }
}