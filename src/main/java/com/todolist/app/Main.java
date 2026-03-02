import java.util.Scanner;
import java.util.ArrayList;

/// CS132 Authors: Faith, Jason, Bryant, Jonathon
/// Main.java is intended to be the UI layer of the application
/// Purpose: Handles all user interface operations including:
/// Task 1. Displaying menus and messages
/// Task 2. Getting user input
/// Task 3. Error handling
/// Task 4. Frontline for application flow 

public class Main {
    // Single Scanner instance for all user input
    private static Scanner scanner = new Scanner(System.in);
    private static ToDoListApp app; // Reference to business logic layer

    public static void main(String[] args) {
        // Application entry point with welcome greeting
        displayWelcomeBanner();

        // Authenticate user before showing main menu
        User user = login();

        // Initialize the app with the logged-in user
        app = new ToDoListApp(user);

        // Load tasks from file
        loadUserTasks(user);

        // Main program loop - keeps application running until user chooses to exit
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                case "v":
                    viewCurrentTasks();
                    break;
                case "2":
                case "a":
                    addTask();
                    break;
                case "3":
                case "c":
                    markTaskCompleted();
                    break;
                case "4":
                case "r":
                    removeTask();
                    break;
                case "5":
                case "d":
                    viewCompletedTasks();
                    break;
                case "6":
                case "s":
                    saveAndExit(user);
                    running = false;
                    break;
                case "7":
                case "p":
                    viewTasksWithProgress();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    /// Displays welcome banner when application starts
    private static void displayWelcomeBanner() {
        System.out.println("===============================");
        System.out.println("|         Godspeed            |");
        System.out.println("|     The To-Do List App      |");
        System.out.println("|                             |");
        System.out.println("|         Welcome!            |");
        System.out.println("|    Ready to complete your   |");
        System.out.println("|          tasks?             |");
        System.out.println("|                             |");
        System.out.println("===============================\n");
    }

    private static User login() {
        System.out.println("=== LOGIN ===");
        System.out.print("Enter username to log in: ");
        String username = scanner.nextLine().trim();

        // Validate username is not empty
        while (username.isEmpty()) {
            System.out.print(" Username cannot be empty. Enter username: ");
            username = scanner.nextLine().trim();
        }

        // Create user object
        User user = new User(username);

        // Display personalized welcome message
        System.out.println("\n================================================");
        System.out.println("    WELCOME BACK, " + username.toUpperCase() + "!");
        System.out.println("    Your personal task manager");
        System.out.println("    is ready for you!");
        System.out.println("================================================\n");

        return user;
    }

    /// Helper Method to pad strings for banner formatting.
    private static String padRight(String text, int length) {
        return String.format("%-" + length + "s", text);
    }

    /// Loads users tasks from file with corresponding messages
    private static void loadUserTasks(User user) {
        System.out.println("Loading your tasks..."); 
        boolean loaded = FileManager.loadTasks(user);
        
        if (!loaded) {
            System.out.println("No existing tasks found. Starting fresh!");
        } else if (user.getTaskCount() == 0) {
            System.out.println("Your task list is empty.");
        } else {
            System.out.println("Loaded " + user.getTaskCount() + " task(s)!");
        }
    }

    /// Displays the main menu options to user
    private static void printMenu() {
        System.out.println("\n================================================");
        System.out.println("                 MAIN MENU");
        System.out.println("================================================");
        System.out.println("1) View current to-do list    (V)");
        System.out.println("2) Add new task               (A)");
        System.out.println("3) Mark task as completed     (C)");
        System.out.println("4) Remove task                (R)");
        System.out.println("5) View completed tasks       (D)");
        System.out.println("6) Save and exit              (S)");
        System.out.println("7) View tasks with progress   (P)");
        System.out.println("================================================");
        System.out.print("Select an option: ");
    }

    /// Displays current incomplete tasks
    private static void viewCurrentTasks() {
        System.out.println("\n======== YOUR TO-DO LIST ========");
        app.displayTasks();
    }

    /// Handles adding a new task
    private static void addTask() {
        System.out.println("\n======== ADD NEW TASK ========");

        System.out.print("Enter task title: ");
        String title = scanner.nextLine().trim();

        while (title.isEmpty()) {
            System.out.print("Title cannot be empty. Enter task title: ");
            title = scanner.nextLine().trim();
        }

        System.out.print("Enter task description: ");
        String description = scanner.nextLine().trim();

        // Validate description is not empty
        while (description.isEmpty()) {
            System.out.print("Description cannot be empty. Enter task description: ");
            description = scanner.nextLine().trim();
        }

        app.addNewTask(title, description);
        System.out.println("Task added successfully!");
    }

    /// Handles marking a task as 'completed'
    private static void markTaskCompleted() {
        System.out.println("\n===== MARK TASK AS COMPLETED =====");

        boolean marking = true;
        while (marking) {
            // Get incomplete tasks from the app's business logic
            ArrayList<Task> tasks = app.getAllTasks();
            ArrayList<Task> incompleteTasks = new ArrayList<>();

            // Filter incomplete tasks
            for (Task task : tasks) {
                if (!task.isCompleted()) {
                    incompleteTasks.add(task);
                }
            }

            if (incompleteTasks.isEmpty()) {
                System.out.println("No tasks to complete.");
                return;
            }

            // Display incomplete tasks
            System.out.println("Select a task to mark as completed (or enter 0 to go back):");
            for (int i = 0; i < incompleteTasks.size(); i++) {
                System.out.println("   " + (i + 1) + ") " + incompleteTasks.get(i).getTitle());
            }

            System.out.print("Enter task number: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine()) - 1;

                // 0 entered means go back to main menu
                if (choice == -1) {
                    marking = false;
                    break;
                }

                // Find the actual task index in the complete list
                int actualIndex = -1;
                int incompleteCount = 0;
                for (int i = 0; i < tasks.size(); i++) {
                    if (!tasks.get(i).isCompleted()) {
                        if (incompleteCount == choice) {
                            actualIndex = i;
                            break;
                        }
                        incompleteCount++;
                    }
                }

                if (actualIndex >= 0 && app.markTaskAsCompleted(actualIndex)) {
                    System.out.println("Task marked as completed! Great job!");
                } else {
                    System.out.println("Invalid selection.");
                }

            } catch (Exception e) {
                System.out.println("Invalid selection. Please enter a number.");
            }
        }
    }

    /// Handles removing a task
    private static void removeTask() {
        System.out.println("\n========= REMOVE TASK ==========");

        ArrayList<Task> tasks = app.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks to remove.");
            return;
        }

        System.out.println("Select a task to remove:");
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            String status = t.isCompleted() ? "[COMPLETED]" : "[PENDING]";
            System.out.println("   " + (i + 1) + ") " + status + " " + t.getTitle());
        }

        System.out.print("Enter task number: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (app.removeTaskAtIndex(choice)) {
                System.out.println("Task removed successfully.");
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (Exception e) {
            System.out.println("Invalid selection. Please enter a number.");
        }
    }

    /// Displays completed Tasks
    private static void viewCompletedTasks() {
        System.out.println("\n======== COMPLETED TASKS ========");
        app.displayCompletedTasks();
    }

    /// Displays tasks with progress bars
    private static void viewTasksWithProgress() {
        System.out.println("\n========= TASKS WITH PROGRESS =========");
        app.displayTasksWithProgress();
    }

    /// Saves tasks and displays goodbye messages
    private static void saveAndExit(User user) {
        System.out.println("\n===== SAVING & EXITING =====");

        System.out.println("Saving your tasks...");
        boolean saved = FileManager.saveTasks(user);

        if (saved) {
            System.out.println("Tasks saved successfully!");
        } else {
            System.out.println("Warning: There was an issue saving your tasks.");
        }

        // Display personalized goodbye message
        System.out.println("\n================================================");
        System.out.println("          GOODBYE, " + user.getUsername().toUpperCase() + "!");
        System.out.println("                                                ");
        System.out.println("    Thanks for using the app!");
        System.out.println("    Have a productive day!");
        System.out.println("================================================");
    }
}
