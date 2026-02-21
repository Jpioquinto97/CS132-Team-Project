package com.todolist.app;
/// Edited and filled in by Faith on 02-21-2026. Code notes are in the teams group chat of 
/// changes and google drive folder on a word doc!! :)

import java.util.Scanner;
public class ToDoListApp {
    // NOTE: Current user session data - holds the logged-in user's information and tasks
    // This is shared across all methods to maintain application state
    private static User currentUser;
    
    // NOTE: Single Scanner instance for all user input
    // Created once and reused to prevent resource leaks and input conflicts
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== To-Do List Application ===\n");
        
        // NOTE: Authenticate user before showing main menu
        // Ensures we know who is using the application
        // TODO: Implement login system - should verify user credentials
        login();
        
        // NOTE: Main program loop - keeps application running until user chooses to exit
        boolean running = true;
        while (running) {
            displayMenu();                    // Show available options
            int choice = getUserChoice();     // Get user's selection
            
            // NOTE: Menu navigation using switch statement
            // Each case corresponds to a specific user action
            switch (choice) {
                case 1:
                    viewTasks();              // Show incomplete tasks
                    break;
                case 2:
                    addTask();                 // Create new task
                    break;
                case 3:
                    completeTask();            // Mark task as done
                    break;
                case 4:
                    removeTask();              // Delete task from list
                    break;
                case 5:
                    viewCompletedTasks();      // Show completed tasks only
                    break;
                case 6:
                    saveAndExit();              // Save data and quit
                    running = false;            // Exit the loop
                    break;
                default:
                    // NOTE: Handle invalid menu selections gracefully
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        // NOTE: Clean up resources before program termination
        scanner.close();  // Prevent resource leak
    }
    
    private static void displayMenu() {
        // NOTE: Format menu with clear numbering and descriptions.
        // Provides user-friendly interface for navigation. This is essentally the main menu.
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. View To-Do List");          // Show current tasks
        System.out.println("2. Add New Task");              // Create new task
        System.out.println("3. Mark Task as Completed");    // Update task status
        System.out.println("4. Remove Task");               // Delete task
        System.out.println("5. View Completed Tasks");      // Filter completed tasks
        System.out.println("6. Save and Exit");             // Persist data and quit
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            // NOTE: Read entire line and parse as integer.
            // This will get the user's choice and return the selected menu option. 
            // Using nextLine() instead of nextInt() to avoid input buffer issues
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            // NOTE: Handle non-numeric input gracefully
            // Returns -1 which will trigger default case in menu switch
            return -1; // Invalid input
        }
    }
    
    private static void login() {
        // NOTE: Simple username collection for now
        // In future versions, this could include password authentication
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        // NOTE: Create user object for this session
        currentUser = new User(username);
        System.out.println("Welcome, " + username + "!\n");
        
        // TODO: Load user's saved tasks from file using FileManager
        // Should check if user has existing data using FileManager.userDataExists()
        // Then load tasks with FileManager.loadTasks(currentUser)
    }

    private static void viewTasks() {
        System.out.println("\n=== Your To-Do List ===");
        
        // TODO: Implement task viewing logic
        // Should iterate through currentUser.getAllTasks()
        // Filter to show only incomplete tasks
        // Display each task using task.displayTask()
        
        // NOTE: Placeholder until implementation
        System.out.println("No tasks available.");
    }
    private static void addTask() {
        // NOTE: Collect task details from user input
        System.out.print("\nEnter task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        
        // TODO: Create new Task object and add to user's list
        // Task newTask = new Task(title, description);
        // currentUser.addTask(newTask);
        
        // NOTE: Confirm successful operation to user
        System.out.println("Task added successfully!");
    }

    private static void completeTask() {
        System.out.println("\n=== Mark Task as Completed ===");
        
        // TODO: Implement task completion logic
        // 1. Display list of incomplete tasks with numbers
        // 2. Ask user to select a task by number
        // 3. Get selected task from currentUser
        // 4. Call task.markAsCompleted()
        // 5. Confirm completion to user
        
        // NOTE: Placeholder until implementation
        System.out.println("Feature coming soon!");
    }

    private static void removeTask() {
        System.out.println("\n=== Remove Task ===");
        
        // TODO: Implement task removal logic
        // 1. Display list of all tasks with numbers
        // 2. Ask user to select task to remove
        // 3. Remove from currentUser's task list
        // 4. Confirm removal to user
        
        // NOTE: Placeholder until implementation
        System.out.println("Feature coming soon!");
    }

    private static void viewCompletedTasks() {
        System.out.println("\n=== Completed Tasks ===");
        
        // TODO: Implement completed task viewing logic
        // Should iterate through currentUser.getAllTasks()
        // Filter to show only completed tasks (isCompleted() == true)
        // Display each task with completion marker
        
        // NOTE: Placeholder until implementation
        System.out.println("No completed tasks.");
    }

    private static void saveAndExit() {
        System.out.println("\nSaving your tasks...");
        
        // TODO: Save tasks to file using FileManager
        // FileManager.saveTasks(currentUser) should be called here
        
        // NOTE: Provide feedback on save operation
        System.out.println("Tasks saved successfully!");
        System.out.println("Goodbye!");
    }
}
