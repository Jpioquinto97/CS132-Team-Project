/// package com.todolist.app;

import java.util.Scanner;

public class ToDoListApp {
    // NOTE: Current user session data - holds the logged-in user's information and
    // tasks
    // This is shared across all methods to maintain application state
    // OOP Encapsulation Object composition.
    private static User currentUser;

    // NOTE: Single Scanner instance for all user input
    // Created once and reused to prevent resource leaks and input conflicts
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("~~~ To-Do List Application ~~~\n");

        // NOTE: Authenticate user before showing main menu
        // Ensures we know who is using the application
        // TODO: Implement login system - should verify user credentials
        login(); // OOP: Method call

        // NOTE: Main program loop - keeps application running until user chooses to
        // exit
        boolean running = true;
        while (running) {
            displayMenu(); // OOP: Abstraction of methood
            int choice = getUserChoice(); // OOP: Method call and returns value

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
                    running = false; // Exit the loop
                    break;
                default:
                    // Error-Proofing. This will handle invalid menu selections 
                    System.out.println("Invalid option. Please try again.");
            }
        }
        // This cleans up resources before program termination // OOP: resource management. 
        scanner.close();
    }

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

    private static int getUserChoice() {
        // Exception handling section / Try-catch block prevents NumberFormatException from crashing the program
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            // Handles non numeric input
            // Returns -1 which will trigger default case in menu switch
            // Exception Handling. Graceful degradation, program continues running.
            return -1;
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        currentUser = new User(username); // OOP - constructor call (object creation)
        System.out.println("Welcome, " + username + "!\n");
    }

    private static void viewTasks() {
        System.out.println("\n=== Your To-Do List ===");
        // TODO: Implement task viewing logic
        System.out.println("No tasks available.");
    }

    private static void addTask() { // Collect task details from user input
        System.out.println("\n=== Add New Task ===");
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        // TODO: Create new Task object and add to user's list
        // Task newTask = new Task(title, description);
        // currentUser.addTask(newTask);

        System.out.println("Task added successfully!");
    }

    private static void completeTask() {
        System.out.println("\n=== Mark Task as Completed ===");
        // TODO: Implement task completion logic
        System.out.println("Feature coming soon!");
    }

    private static void removeTask() {
        System.out.println("\n=== Remove Task ===");
        // TODO: Implement task removal logic
        System.out.println("Feature coming soon!");
    }

    private static void viewCompletedTasks() {
        System.out.println("\n=== Completed Tasks ===");
        // TODO: Implement completed task viewing logic
        System.out.println("No completed tasks.");
    }

    private static void saveAndExit() {
        System.out.println("\nSaving your tasks...");
        // TODO: Save tasks to file using FileManager
        System.out.println("Tasks saved successfully!");
        System.out.println("Goodbye!");
    }
}
