package com.todolist.app;

/**
 * Represents a single task in the to-do list
 * Contains task information and completion status
 * 
 * @author CS132 Team (Jonathon, Bryant, Faith, Jason)
 */
public class Task {
    private String title;
    private String description;
    private boolean isCompleted;
    
    /**
     * Constructor to create a new task
     * @param title The task title
     * @param description The task description
     */
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.isCompleted = false;
    }
    
    /**
     * Gets the task title
     * @return Task title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Gets the task description
     * @return Task description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Checks if task is completed
     * @return true if completed, false otherwise
     */
    public boolean isCompleted() {
        return isCompleted;
    }
    
    /**
     * Marks the task as completed
     */
    public void markAsCompleted() {
        this.isCompleted = true;
    }
    
    /**
     * Displays task information
     */
    public void displayTask() {
        String status = isCompleted ? "[X]" : "[ ]";
        System.out.println(status + " " + title);
        System.out.println("    Description: " + description);
    }
    
    /**
     * Converts task to string format for file storage
     * @return String representation of task
     */
    public String toFileString() {
        return title + "|" + description + "|" + isCompleted;
    }
    
    /**
     * Creates a Task object from file string
     * @param fileString String from file
     * @return Task object
     */
    public static Task fromFileString(String fileString) {
        String[] parts = fileString.split("\\|");
        Task task = new Task(parts[0], parts[1]);
        if (parts[2].equals("true")) {
            task.markAsCompleted();
        }
        return task;
    }
}