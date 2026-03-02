import java.util.ArrayList;
/// CS132 Authors: Faith, Jason, Bryant, Jonathon
/// ToDoListApp.java - Business Logic Layer
/// Purpose: Handles core application logic including:
/// Task 1. Managing tasks and completed tasks
/// Task 2. Coordinating between User object and Task objects
/// Task 3. Processing user commands (without UI) - UI is stored within 'Main'
/// Task 4. Data validation and business rules

public class ToDoListApp {
    // Reference to current user and their data. Uses OOP composition to manage user's tasks
    private User currentUser;
    
    // Separate list for completed tasks for quick access
    private ArrayList<Task> completedTasks;

    /// Constructor initializes app with a specific user
    public ToDoListApp(User user) {
        this.currentUser = user;
        this.completedTasks = new ArrayList<>();
        // Initialize completed tasks from user's existing tasks
        updateCompletedTasks();
    }

    /// Displays all incomplete tasks for the current user
    public void displayTasks() {
        ArrayList<Task> tasks = currentUser.getIncompleteTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                System.out.println((i + 1) + ". " + task.getTitle() + 
                                 " - " + task.getDescription());
            }
        }
    }

    /// Creates and adds a new task for the current user
    public void addNewTask(String title, String description) {
        // Create new Task object and add to user's list
        Task newTask = new Task(title, description);
        currentUser.addTask(newTask);
    }

    /// Handles task completion logic
    public void completeTask() {
        ArrayList<Task> incompleteTasks = currentUser.getIncompleteTasks();
        
        if (incompleteTasks.isEmpty()) {
            System.out.println("No tasks to complete.");
            return;
        }
        
        // Display available tasks for completion
        System.out.println("Select task to complete:");
        for (int i = 0; i < incompleteTasks.size(); i++) {
            Task task = incompleteTasks.get(i);
            System.out.println((i + 1) + ". " + task.getTitle() + 
                             " - " + task.getDescription());
        }
        
        // Input handling is done in UI layer (Main.java)
        System.out.println("Please enter task number in the UI layer");
    }

    /// Handles task removal logic
    public void removeTask() {
        ArrayList<Task> tasks = currentUser.getAllTasks();
        
        if (tasks.isEmpty()) {
            System.out.println("No tasks to remove.");
            return;
        }
        
        // Display all tasks for removal selection
        System.out.println("Select task to remove:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String status = task.isCompleted() ? "[COMPLETED]" : "[PENDING]";
            System.out.println((i + 1) + ". " + status + " " + 
                             task.getTitle() + " - " + task.getDescription());
        }
        
        // Input handling is done in UI layer (Main.java)
        System.out.println("Please enter task number in the UI layer");
    }

    /// Displays all completed tasks
    public void displayCompletedTasks() {
        updateCompletedTasks(); // Refresh the list
        if (completedTasks.isEmpty()) {
            System.out.println("No completed tasks.");
        } else {
            for (int i = 0; i < completedTasks.size(); i++) {
                Task task = completedTasks.get(i);
                System.out.println((i + 1) + ". " + task.getTitle() + 
                                 " - " + task.getDescription());
            }
        }
    }

    /// Saves tasks to persistent storage via FileManager
    public void saveTasks() {
        System.out.println("Saving " + currentUser.getUsername() + "'s tasks...");
        FileManager.saveTasks(currentUser);
    }

    /// Updates the completed tasks cache
    private void updateCompletedTasks() {
        completedTasks = currentUser.getCompletedTasks();
    }

    /// Marks a specific task as completed
    public boolean markTaskAsCompleted(int taskIndex) {
        ArrayList<Task> incompleteTasks = currentUser.getIncompleteTasks();
        if (taskIndex >= 0 && taskIndex < incompleteTasks.size()) {
            Task task = incompleteTasks.get(taskIndex);
            task.setCompleted(true);
            updateCompletedTasks();
            return true;
        }
        return false;
    }

    /// Removes a specific task
    public boolean removeTaskAtIndex(int taskIndex) {
        ArrayList<Task> allTasks = currentUser.getAllTasks();
        if (taskIndex >= 0 && taskIndex < allTasks.size()) {
            boolean result = currentUser.removeTask(taskIndex);
            updateCompletedTasks();
            return result;
        }
        return false;
    }

    /// Displays all tasks with progress bars
    public void displayTasksWithProgress() {
        ArrayList<Task> tasks = currentUser.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                System.out.println((i + 1) + ". " + task.getDisplayString());
                System.out.println(); // Add blank line between tasks
            }
        }
    }

    /// Updates task progress percentage - actually sets the value on the task
    public void updateTaskProgress(int taskIndex, int progress) {
        ArrayList<Task> tasks = currentUser.getAllTasks();
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            Task task = tasks.get(taskIndex);
            if (progress >= 0 && progress <= 100) {
                task.setProgressPercentage(progress);
                System.out.println("Progress updated for: " + task.getTitle() + " to " + progress + "%");
                // Auto mark complete if progress hits 100
                if (progress == 100) {
                    task.setCompleted(true);
                    updateCompletedTasks();
                    System.out.println(task.getTitle() + " marked as completed!");
                }
            } else {
                System.out.println("Progress must be between 0 and 100");
            }
        }
    }

    // Getters for data access
    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<Task> getAllTasks() {
        return currentUser.getAllTasks();
    }

    public ArrayList<Task> getCompletedTasks() {
        updateCompletedTasks();
        return completedTasks;
    }
}
