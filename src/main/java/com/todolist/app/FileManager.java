package com.todolist.app;
/// Edited and filled in by Faith on 02-21-2026. Code notes are in the teams group chat of changes and google drive folder on a word doc!! :)
import java.io.*;
import java.util.ArrayList;
/**
 * Manages file I/O operations for saving and loading user tasks
 * Each user's tasks are stored in a separate file
 * 
 * @author CS132 Team (Jonathon, Bryant, Faith, Jason)
 */
public class FileManager {
    // NOTE: Constant defining the folder where all user task files will be stored
    // This creates a centralized location for all saved data
    private static final String DATA_FOLDER = "userdata/";
    
    public static boolean saveTasks(User user) {
        try {
            // NOTE: Create data folder if it doesn't exist
            // This ensures we have a directory to store files before attempting to save
            File folder = new File(DATA_FOLDER);
            if (!folder.exists()) {
                folder.mkdir(); // Creates the directory
                System.out.println("Created data folder: " + DATA_FOLDER);
            }
            
            // NOTE: Generate unique filename based on username
            // Each user gets their own file to prevent data mixing between users
            String filename = DATA_FOLDER + user.getUsername() + "_tasks.txt";
            
            // NOTE: Open file for writing (will create new file or overwrite existing)
            FileWriter writer = new FileWriter(filename);
            
            // NOTE: Get all tasks from user and write each one to file
            // Tasks are converted to a storable string format
            ArrayList<Task> tasks = user.getAllTasks();
            for (Task task : tasks) {
                writer.write(task.toFileString() + "\n"); // Each task on new line
            }
            
            writer.close(); // NOTE: Always close file handles to prevent memory leaks
            System.out.println("Successfully saved " + tasks.size() + " tasks for user: " + user.getUsername());
            return true;
            
        } catch (IOException e) {
            // NOTE: Handle any file operation errors gracefully
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
            // NOTE: Construct the expected filename for this user
            String filename = DATA_FOLDER + user.getUsername() + "_tasks.txt";
            File file = new File(filename);
            
            // NOTE: Check if user has saved data before attempting to load
            // This verifies that the user's task file exists to avoid FileNotFoundException
            if (!userDataExists(user.getUsername())) {
                System.out.println("No saved data found for user: " + user.getUsername() + ". Starting with empty list.");
                return false; // This will return false instead of throwing error
            }
            
            // NOTE: Open file for reading and load each task line by line
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int taskCount = 0;
            
            // NOTE: Read until end of file (null means no more lines)
            while ((line = reader.readLine()) != null) {
                // NOTE: Convert stored string back into Task object
                Task task = Task.fromFileString(line);
                user.addTask(task);
                taskCount++;
            }
            
            reader.close(); // NOTE: Always close file handles to prevent memory leaks
            System.out.println("Loaded " + taskCount + " task(s) from file for user: " + user.getUsername());
            return true;
            
        } catch (IOException e) {
            // NOTE: Handle any file operation errors gracefully without throwing an error
            System.out.println("Error loading tasks: " + e.getMessage());
            return false;
        }
    }
    
    // Checks if a user has saved data
    public static boolean userDataExists(String username) {
        // NOTE: Construct the expected filename for this user
        String filename = DATA_FOLDER + username + "_tasks.txt";
        File file = new File(filename);
        
        // NOTE: Check if file exists in the filesystem
        // Returns true if file exists, false if not
        boolean exists = file.exists();
        
        if (exists) {
            System.out.println("Found existing data for user: " + username);
        } else {
            System.out.println("No existing data found for user: " + username);
        }
        
        return exists;
    }
}
