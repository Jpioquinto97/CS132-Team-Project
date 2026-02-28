// package com.todolist.app;
/// Edited and filled in by Faith on 02-21-2026. Code notes are in the teams group chat of 
/// changes and google drive folder on a word doc!! :)
package com.todolist.app;

/**
 * Represents a single task in the to-do list
 * Contains task information and completion status
 * 
 * @author CS132 Team (Jonathon, Bryant, Faith, Jason)
 */
public class Task {
    // NOTE: Private fields to encapsulate task data
    // These can only be accessed through public methods (getters/setters)
    private String title;        // Stores the task name/title
    private String description;  // Stores detailed task description
    private boolean isCompleted; // Tracks whether task is done (true) or not (false)
    
    public Task(String title, String description) {
        // NOTE: Initialize task with user-provided title and description
        this.title = title;
        this.description = description;
        // NOTE: New tasks are always created as not completed
        // User must explicitly mark them as done later
        this.isCompleted = false;
    }
    public String getTitle() {
        // NOTE: Getter method to access private title field
        // Provides read-only access to title without allowing direct modification
        return title;
    }
    public String getDescription() {
        // NOTE: Getter method to access private description field
        // Allows other classes to view task details
        return description;
    }
    public boolean isCompleted() {
        // NOTE: Getter method to check completion status
        // Returns boolean indicating if task is done
        return isCompleted;
    }
    public void markAsCompleted() {
        // NOTE: Setter method to update completion status
        // Once a task is marked complete, this change is permanent
        this.isCompleted = true;
    }
    public void displayTask() {
        // NOTE: Format task display for console output
        // Uses [X] for completed tasks and [ ] for incomplete tasks
        String status = isCompleted ? "[X]" : "[ ]";
        // NOTE: Display task title with status checkbox
        System.out.println(status + " " + title);
        
        // NOTE: Display description indented for better readability
        System.out.println("    Description: " + description);
    }
    public String toFileString() {
        // NOTE: Convert task object to storable string format
        // Uses pipe (|) as delimiter to separate fields
        // Format: title|description|isCompleted
        // Example: "Buy groceries|Milk and eggs|false"
        return title + "|" + description + "|" + isCompleted;
    }
    
    /**
     * Creates a Task object from file string
     * @param fileString String from file
     * @return Task object
     */
    public static Task fromFileString(String fileString) {
        // NOTE: Static factory method to recreate task from saved string
        // Splits the string using pipe delimiter (escaped as \\| in regex)
        String[] parts = fileString.split("\\|");
        
        // NOTE: Create new task using the first two parts (title and description)
        Task task = new Task(parts[0], parts[1]);
        
        // NOTE: Restore completion status from the third part
        // If saved as "true", mark task as completed
        if (parts[2].equals("true")) {
            task.markAsCompleted();
        }
        
        return task;
    }
}
