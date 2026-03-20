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
                case "8":
                case "e":
                    editTask();
                    break;
                case "9":
                case "f":
                    searchTasks();
                    break;
                default:
                    System.out.println("Invalid option. Please enter a number from 1-9.");
            }
        }

        scanner.close();
    }

    /// Displays welcome banner when application starts
    private static void displayWelcomeBanner() {
        System.out.println("===============================");
        System.out.println("|      My Life Organized      |");
        System.out.println("|     The To-Do List App      |");
        System.out.println("|                             |");
        System.out.println("|          Welcome!           |");
        System.out.println("|     Ready to complete your  |");
        System.out.println("|           tasks?            |");
        System.out.println("|                             |");
        System.out.println("===============================\n");
    }

    /// Handles user login with password authentication.
    /// If the user is new, prompts them to create a password and saves credentials.
    /// If the user exists, verifies their password against the stored credentials
    /// file.
    private static User login() {
        System.out.println("=== LOGIN ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        // Validate username is not empty
        while (username.isEmpty()) {
            System.out.print("Username cannot be empty. Enter username: ");
            username = scanner.nextLine().trim();
        }

        // Check if this user already has saved credentials
        boolean userExists = FileManager.credentialsExist(username);

        if (userExists) {
            // Returning user - verify password
            System.out.print("Enter password: ");
            String password = scanner.nextLine().trim();

            // Keep prompting until password is correct
            while (!FileManager.verifyPassword(username, password)) {
                System.out.println("Incorrect password. Please try again.");
                System.out.print("Enter password: ");
                password = scanner.nextLine().trim();
            }

            System.out.println("\n================================================");
            System.out.println("    WELCOME BACK, " + username.toUpperCase() + "!");
            System.out.println("    Your personal task manager");
            System.out.println("    is ready for you!");
            System.out.println("================================================\n");

        } else {
            // New user - create a password
            System.out.println("New user detected. Please create a password.");
            System.out.print("Enter new password: ");
            String password = scanner.nextLine().trim();

            // Validate password is not empty
            while (password.isEmpty()) {
                System.out.print("Password cannot be empty. Enter new password: ");
                password = scanner.nextLine().trim();
            }

            // Confirm password
            System.out.print("Confirm password: ");
            String confirm = scanner.nextLine().trim();

            // Keep prompting until passwords match
            while (!password.equals(confirm)) {
                System.out.println("Passwords do not match. Try again.");
                System.out.print("Enter new password: ");
                password = scanner.nextLine().trim();
                System.out.print("Confirm password: ");
                confirm = scanner.nextLine().trim();
            }

            // Save credentials to file
            FileManager.saveCredentials(username, password);
            System.out.println("Account created successfully!");

            System.out.println("\n================================================");
            System.out.println("    WELCOME, " + username.toUpperCase() + "!");
            System.out.println("    Your personal task manager");
            System.out.println("    is ready for you!");
            System.out.println("================================================\n");
        }

        return new User(username);
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
        System.out.println("8) Edit a task                (E)");
        System.out.println("9) Search tasks               (F)");
        System.out.println("================================================");
        System.out.print("Select an option: ");
    }

    /// Displays current incomplete tasks
    private static void viewCurrentTasks() {
        System.out.println("\n======== YOUR TO-DO LIST ========");
        app.displayTasks();
    }

    /// Handles adding a new task with cancel option
    private static void addTask() {
        System.out.println("\n======== ADD NEW TASK ========");
        System.out.println("(Enter 0 at any prompt to cancel and return to main menu)");
        System.out.println();

        System.out.print("Enter task title (or 0 to cancel): ");
        String title = scanner.nextLine().trim();

        // Check for cancellation
        if (title.equals("0")) {
            System.out.println("Task creation cancelled. Returning to main menu.");
            return;
        }

        // Validate title is not empty
        while (title.isEmpty()) {
            System.out.print("Title cannot be empty. Enter task title (or 0 to cancel): ");
            title = scanner.nextLine().trim();
            if (title.equals("0")) {
                System.out.println("Task creation cancelled. Returning to main menu.");
                return;
            }
        }

        System.out.print("Enter task description (or 0 to cancel): ");
        String description = scanner.nextLine().trim();

        // Check for cancellation
        if (description.equals("0")) {
            System.out.println("Task creation cancelled. Returning to main menu.");
            return;
        }

        // Validate description is not empty
        while (description.isEmpty()) {
            System.out.print("Description cannot be empty. Enter task description (or 0 to cancel): ");
            description = scanner.nextLine().trim();
            if (description.equals("0")) {
                System.out.println("Task creation cancelled. Returning to main menu.");
                return;
            }
        }

        app.addNewTask(title, description);
        System.out.println("Task added successfully!");
    }

    /// Handles marking a task as completed with cancel option
    private static void markTaskCompleted() {
        System.out.println("\n===== MARK TASK AS COMPLETED =====");
        System.out.println("(Enter 0 to cancel and return to main menu)");
        System.out.println();

        boolean marking = true;
        while (marking) {
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

            // Display incomplete tasks with clear numbering
            System.out.println("Select a task to mark as completed:");
            for (int i = 0; i < incompleteTasks.size(); i++) {
                System.out.println("   " + (i + 1) + ") " + incompleteTasks.get(i).getTitle());
            }
            System.out.println("   0) Cancel and return to main menu");
            System.out.print("Enter task number: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim()) - 1;

                // 0 entered means go back to main menu
                if (choice == -1) {
                    System.out.println("Operation cancelled. Returning to main menu.");
                    marking = false;
                    break;
                }

                // Validate range before accessing
                if (choice < 0 || choice >= incompleteTasks.size()) {
                    System.out.println(
                            "Invalid task number. Please enter a number between 1 and " + incompleteTasks.size() + ".");
                    continue;
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

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /// Handles removing a task with cancel option
    private static void removeTask() {
        System.out.println("\n========= REMOVE TASK ==========");
        System.out.println("(Enter 0 to cancel and return to main menu)");
        System.out.println();

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
        System.out.println("   0) Cancel and return to main menu");
        System.out.print("Enter task number: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim()) - 1;

            if (choice == -1) {
                System.out.println("Operation cancelled. Returning to main menu.");
                return;
            }

            if (choice < 0 || choice >= tasks.size()) {
                System.out.println("Invalid task number. Please enter a number between 1 and " + tasks.size() + ".");
                return;
            }

            if (app.removeTaskAtIndex(choice)) {
                System.out.println("Task removed successfully.");
            } else {
                System.out.println("Failed to remove task.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    /// Displays completed tasks
    private static void viewCompletedTasks() {
        System.out.println("\n======== COMPLETED TASKS ========");
        app.displayCompletedTasks();
    }

    /// Displays tasks with progress bars
    private static void viewTasksWithProgress() {
        System.out.println("\n========= TASKS WITH PROGRESS =========");
        app.displayTasksWithProgress();
    }

    /// Handles editing an existing task's title or description with cancel options
    private static void editTask() {
        System.out.println("\n======== EDIT TASK ========");
        System.out.println("(Enter 0 to cancel and return to main menu)");
        System.out.println();

        ArrayList<Task> tasks = app.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks to edit.");
            return;
        }

        // Display all tasks for selection
        System.out.println("Select a task to edit:");
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            String status = t.isCompleted() ? "[COMPLETED]" : "[PENDING]";
            System.out.println("   " + (i + 1) + ") " + status + " " + t.getTitle() + " - " + t.getDescription());
        }
        System.out.println("   0) Cancel and return to main menu");
        System.out.print("Enter task number: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim()) - 1;

            if (choice == -1) {
                System.out.println("Operation cancelled. Returning to main menu.");
                return;
            }

            // Validate range
            if (choice < 0 || choice >= tasks.size()) {
                System.out.println("Invalid task number. Please enter a number between 1 and " + tasks.size() + ".");
                return;
            }

            Task selectedTask = tasks.get(choice);
            System.out.println("\nEditing: " + selectedTask.getTitle());
            System.out.println("1) Edit title");
            System.out.println("2) Edit description");
            System.out.println("3) Edit both");
            System.out.println("0) Cancel and return to main menu");
            System.out.print("Select an option: ");

            String editChoice = scanner.nextLine().trim();

            // Handle cancellation
            if (editChoice.equals("0")) {
                System.out.println("Edit cancelled. Returning to main menu.");
                return;
            }

            // Handle title edit
            if (editChoice.equals("1") || editChoice.equals("3")) {
                System.out.print("Enter new title (current: " + selectedTask.getTitle() + "): ");
                String newTitle = scanner.nextLine().trim();
                while (newTitle.isEmpty()) {
                    System.out.print("Title cannot be empty. Enter new title: ");
                    newTitle = scanner.nextLine().trim();
                }
                app.editTaskTitle(choice, newTitle);
            }

            // Handle description edit
            if (editChoice.equals("2") || editChoice.equals("3")) {
                System.out.print("Enter new description (current: " + selectedTask.getDescription() + "): ");
                String newDesc = scanner.nextLine().trim();
                while (newDesc.isEmpty()) {
                    System.out.print("Description cannot be empty. Enter new description: ");
                    newDesc = scanner.nextLine().trim();
                }
                app.editTaskDescription(choice, newDesc);
            }

            if (editChoice.equals("1") || editChoice.equals("2") || editChoice.equals("3")) {
                System.out.println("Task updated successfully!");
            } else {
                System.out.println("Invalid option. No changes made.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    /// Handles searching tasks by keyword with cancel option
    private static void searchTasks() {
        System.out.println("\n======== SEARCH TASKS ========");
        System.out.println("(Enter 0 to cancel and return to main menu)");
        System.out.println();

        System.out.print("Enter search keyword (or 0 to cancel): ");
        String keyword = scanner.nextLine().trim();

        // Check for cancellation
        if (keyword.equals("0")) {
            System.out.println("Search cancelled. Returning to main menu.");
            return;
        }

        // Validate keyword is not empty
        if (keyword.isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return;
        }

        // Get results from business logic layer
        ArrayList<Task> results = app.searchTasks(keyword);

        if (results.isEmpty()) {
            System.out.println("No tasks found matching \"" + keyword + "\".");
        } else {
            System.out.println("Found " + results.size() + " result(s) for \"" + keyword + "\":");
            for (int i = 0; i < results.size(); i++) {
                Task t = results.get(i);
                String status = t.isCompleted() ? "[COMPLETED]" : "[PENDING]";
                System.out.println("   " + (i + 1) + ") " + status + " " + t.getTitle() + " - " + t.getDescription());
            }
        }
    }

    /// Saves tasks and displays goodbye message
    private static void saveAndExit(User user) {
        System.out.println("\n===== SAVING & EXITING =====");

        System.out.println("Saving your tasks...");
        boolean saved = FileManager.saveTasks(user);

        if (saved) {
            System.out.println("Tasks saved successfully!");
        } else {
            System.out.println("Warning: There was an issue saving your tasks.");
        }

        System.out.println("\n================================================");
        System.out.println("          GOODBYE, " + user.getUsername().toUpperCase() + "!");
        System.out.println("                                                ");
        System.out.println("    Thanks for using the app!");
        System.out.println("    Have a productive day!");
        System.out.println("================================================");
    }
}
