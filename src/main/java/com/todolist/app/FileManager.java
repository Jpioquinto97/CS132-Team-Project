package/// CS132 Authors: Faith, Jason, Bryant
// FileManager.java - File I/O Operations
/// Purpose: Manages all file input and output operations for the application
// Handles saving and loading user tasks to/from persistent storage

import java.io.*;
import java.util.ArrayList;
public class FileManager {
    // OOP Encapsulation - Private constant with public access through methods
    // This creates a centralized location for all saved data
    private static final String DATA_FOLDER = "userdata/";
    
    // Saves a user's tasks to a file
    public static boolean saveTasks(User user) {

        try {
            //Create data folder if it doesn't exist

            File folder = new File(DATA_FOLDER);
            if (!folder.exists()) {
            
                folder.mkdir(); // Creates the directory
                System.out.println("Created data folder: " + DATA_FOLDER);
            }
            
            // Each user gets their own file to prevent data mixing between users
            String filename = DATA_FOLDER + user.getUsername() + "_tasks.txt";
            
            // Will create new file or overwrite existing
            // OOP Concept: Using FileWriter object for file operations
            FileWriter writer = new FileWriter(filename);
            
            // NOTE: Get all tasks from user and write each one to file
            // Tasks are converted to a storable string format
            ArrayList<Task> tasks = user.getAllTasks(); // OOP: Method call to get data
            for (Task task : tasks) {
                writer.write(task.toFileString() + "\n"); // OOP: Task serialization
            }
            
            writer.close(); // Always close file handles to prevent memory leaks
            System.out.println("Successfully saved " + tasks.size() + " tasks for user: " + user.getUsername());
            return true;
            
        } catch (IOException e) {
            // OOP Concept: Exception handling - Graceful degradation
            System.out.println("Error saving tasks: " + e.getMessage());
            return false;
        }
    }
    
    // Loads a user's tasks from a file
    public static boolean loadTasks(User user) {
        try {
            // Construct the expected filename for this user
            String filename = DATA_FOLDER + user.getUsername() + "_tasks.txt";
            File file = new File(filename);
            
            // Check if user has saved data before attempting to load
            // This verifies that the user's task file exists to avoid FileNotFoundException
            if (!userDataExists(user.getUsername())) {
                System.out.println("No saved data found for user: " + user.getUsername() + ". Starting with empty list.");
                return false; // This will return false instead of throwing error
            }
            
            // Open file for reading and load each task line by line
            // OOP Concept: Using BufferedReader for efficient reading
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int taskCount = 0;
            
            // Read until end of file
            while ((line = reader.readLine()) != null) {
                // Convert stored string back into Task object
                Task task = Task.fromFileString(line); 
                user.addTask(task); 
                taskCount++;
            }
            
            reader.close(); // Always close file handles to prevent memory leaks
            System.out.println("Loaded " + taskCount + " task(s) from file for user: " + user.getUsername());
            return true;
            
        } catch (IOException e) {
            // Handle any file operation errors gracefully without throwing an error
            System.out.println("Error loading tasks: " + e.getMessage());
            return false;
        }
    }
    
    // Checks if a user has saved data
    public static boolean userDataExists(String username) {
        // Construct the expected filename for this user
        String filename = DATA_FOLDER + username + "_tasks.txt";
        File file = new File(filename);
        
        // Check if file exists in the filesystem.  Returns true if file exists, false if not
        boolean exists = file.exists(); // OOP: Method call on File object
        
        if (exists) {
            System.out.println("Found existing data for user: " + username);
        } else {
            System.out.println("No existing data found for user: " + username);
        }
        
        return exists;
    }
    
    // Deletes a user's saved data file
    public static boolean deleteUserData(String username) {
        String filename = DATA_FOLDER + username + "_tasks.txt";
        File file = new File(filename);
        
        if (file.exists()) {
            boolean deleted = file.delete(); // OOP: File object 
            if (deleted) {
                System.out.println("Deleted data for user: " + username);
            }
            return deleted;
        }
        return false;
    }
    
    // Lists all users with saved data @return ArrayList of usernames that have saved data
    public static ArrayList<String> getAllUsersWithData() {
        ArrayList<String> users = new ArrayList<>();
        File folder = new File(DATA_FOLDER);
        
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.endsWith("_tasks.txt"));
            
            if (files != null) {
                for (File file : files) {
                    String filename = file.getName();
                    // Extract username from filename (remove "_tasks.txt")
                    String username = filename.substring(0, filename.length() - 10);
                    users.add(username);
                }
            }
        }
        
        return users;
    }
}
