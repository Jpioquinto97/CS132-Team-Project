package com.todolist.app;

import java.io.*;
import java.util.ArrayList;

/**
 * Manages file I/O operations for saving and loading user tasks
 * Each user's tasks are stored in a separate file
 * 
 * @author CS132 Team (Jonathon, Bryant, Faith, Jason)
 */
public class FileManager {
    private static final String DATA_FOLDER = "userdata/";
    
    /**
     * Saves a user's tasks to a file
     * @param user User whose tasks should be saved
     * @return true if successful, false if error occurred
     */
    public static boolean saveTasks(User user) {
        try {
            // Create data folder if it doesn't exist
            File folder = new File(DATA_FOLDER);
            if (!folder.exists()) {
                folder.mkdir();
            }
            
            // Create filename based on username
            String filename = DATA_FOLDER + user.getUsername() + "_tasks.txt";
            FileWriter writer = new FileWriter(filename);
            
            // Write each task to file
            ArrayList<Task> tasks = user.getAllTasks();
            for (Task task : tasks) {
                writer.write(task.toFileString() + "\n");
            }
            
            writer.close();
            return true;
            
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Loads a user's tasks from a file
     * @param user User whose tasks should be loaded
     * @return true if successful, false if no file exists or error occurred
     */
    public static boolean loadTasks(User user) {
        try {
            String filename = DATA_FOLDER + user.getUsername() + "_tasks.txt";
            File file = new File(filename);
            
            // Check if file exists
            if (!file.exists()) {
                System.out.println("No saved tasks found. Starting with empty list.");
                return false;
            }
            
            // Read tasks from file
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromFileString(line);
                user.addTask(task);
            }
            
            reader.close();
            System.out.println("Loaded " + user.getTaskCount() + " task(s) from file.");
            return true;
            
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if a user has saved data
     * @param username Username to check
     * @return true if saved data exists, false otherwise
     */
    public static boolean userDataExists(String username) {
        String filename = DATA_FOLDER + username + "_tasks.txt";
File file = new File(filename);        return file.exists();
    }
}