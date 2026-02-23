package com.todolist.app;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main entry point for the TO-DO List application.
 * Handles login, menu navigation, and calls User/FileManager logic.
 * 
 * @author CS132 Team
 */

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Login
        System.out.printl("=== To-Do List App ===");
        System.out.print("Enter username to log in: ");
        String username = scanner.nextLine().trim();

        while (username.isEmpty()) {
            System.out.println("Username cannot be empty. Enter username");
            username = scanner.nextLine().trim();

        }

        User user = newUser(username);

        // Load Tasks
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
                    removeTaskCompleted(scanner.user);

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
                    System.out.println("Invalid option. Try Again.");
            }
        }
        scanner.close();
    }


private static void printMenu() {
    System.out.println("\na--- Main Menu ---");
    System.out.println("1) View Current to-do list (V)");
    System.out.println("2) Add new task (A)");
    System.out.println("3) Mark task as completed (C)");
    System.out.println("4) Remove task (R)");
    System.out.println("5) View completed tasks (D)");
    System.out.println("6) Save and exit (S)");
    System.out.println("Select an option: ");
    }



private static void viewCurrentTasks(User user) {
    ArrayList<Task> tasks = user.getIncompleteTasks();
    if (task.isEmpty()) {
        System.out.println("No tasks available.");
        return;

    }

    for (int i = 0; i < tasks.size(); i++) {

    }
}

private static void addTask(Scanner scanner, User user) {
    System.out.println("Enter task title: ");
    String title = scanner.nextLine().trim();

    while (title.isEmpty()) {
        System.out.println("Title cannot be empty. Enter task title: ");
        title = scanner.nextLine().trim();
    }

    System.out.print("Enter task description: "):
    String description = scanner.nextLine().trim();

    user.addTask(new Task(title, description));
    System.out.println("Task added. ");

}
