/// CS132 Authors: Faith, Jason, Bryant, Jonathon
// FileManager.java - File I/O Operations
/// Purpose: Manages all file input and output operations for the application.
/// Handles saving and loading user tasks and credentials to/from persistent storage.

import java.io.*;
import java.util.ArrayList;

public class FileManager {
    // OOP Encapsulation - Private constants with public access through methods
    // Centralized location for all saved data
    private static final String DATA_FOLDER = "userdata/";
    private static final String CREDENTIALS_FILE = DATA_FOLDER + "credentials.txt";

    // ─────────────────────────────────────────────
    // CREDENTIAL METHODS
    // ─────────────────────────────────────────────

    /// Saves a username and password to the credentials file.
    /// Each line in the file stores one user in the format: username|password
    public static boolean saveCredentials(String username, String password) {
        try {
            // Create data folder if it doesn't exist
            File folder = new File(DATA_FOLDER);
            if (!folder.exists()) {
                folder.mkdir();
            }

            // Append new credentials to the file (does not overwrite existing users)
            FileWriter writer = new FileWriter(CREDENTIALS_FILE, true);
            writer.write(username + "|" + password + "\n");
            writer.close();
            return true;

        } catch (IOException e) {
            System.out.println("Error saving credentials: " + e.getMessage());
            return false;
        }
    }

    /// Checks whether a username already exists in the credentials file.
    /// Returns true if found, false if the user is new or the file does not exist.
    public static boolean credentialsExist(String username) {
        File file = new File(CREDENTIALS_FILE);
        if (!file.exists()) {
            return false;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE));
            String line;

            // Read each line and check if the username matches
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2 && parts[0].equalsIgnoreCase(username)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading credentials: " + e.getMessage());
        }

        return false;
    }

    /// Verifies a username and password against stored credentials.
    /// Returns true if the username and password match, false otherwise.
    public static boolean verifyPassword(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE));
            String line;

            // Check each stored credential line for a match
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2
                        && parts[0].equalsIgnoreCase(username)
                        && parts[1].equals(password)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("Error verifying credentials: " + e.getMessage());
        }

        return false;
    }

    // ─────────────────────────────────────────────
    // TASK METHODS
    // ─────────────────────────────────────────────

    /// Saves a user's tasks to a file.
    /// Each user gets their own file to prevent data mixing between users.
    public static boolean saveTasks(User user) {
        try {
            // Create data folder if it doesn't exist
            File folder = new File(DATA_FOLDER);
            if (!folder.exists()) {
                folder.mkdir();
                System.out.println("Created data folder: " + DATA_FOLDER);
            }

            // Each user gets their own file
            String filename = DATA_FOLDER + user.getUsername() + "_tasks.txt";

            // Will create new file or overwrite existing
            FileWriter writer = new FileWriter(filename);

            // Get all tasks from user and write each one to file
            ArrayList<Task> tasks = user.getAllTasks();
            for (Task task : tasks) {
                writer.write(task.toFileString() + "\n");
            }

            writer.close();
            System.out.println("Successfully saved " + tasks.size() + " tasks for user: " + user.getUsername());
            return true;

        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
            return false;
        }
    }

    /// Loads a user's tasks from their saved file.
    /// Returns false and starts fresh if no file is found.
    public static boolean loadTasks(User user) {
        try {
            String filename = DATA_FOLDER + user.getUsername() + "_tasks.txt";
            File file = new File(filename);

            // Check if user has saved data before attempting to load
            if (!userDataExists(user.getUsername())) {
                System.out.println("No saved data found for user: " + user.getUsername() + ". Starting with empty list.");
                return false;
            }

            // Open file for reading and load each task line by line
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int taskCount = 0;

            // Read until end of file
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromFileString(line);
                user.addTask(task);
                taskCount++;
            }

            reader.close();
            System.out.println("Loaded " + taskCount + " task(s) from file for user: " + user.getUsername());
            return true;

        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
            return false;
        }
    }

    /// Checks if a user has a saved task file.
    public static boolean userDataExists(String username) {
        String filename = DATA_FOLDER + username + "_tasks.txt";
        File file = new File(filename);
        boolean exists = file.exists();

        if (exists) {
            System.out.println("Found existing data for user: " + username);
        } else {
            System.out.println("No existing data found for user: " + username);
        }

        return exists;
    }

    /// Deletes a user's saved task data file.
    public static boolean deleteUserData(String username) {
        String filename = DATA_FOLDER + username + "_tasks.txt";
        File file = new File(filename);

        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("Deleted data for user: " + username);
            }
            return deleted;
        }
        return false;
    }

    /// Lists all usernames that have saved task data.
    public static ArrayList<String> getAllUsersWithData() {
        ArrayList<String> users = new ArrayList<>();
        File folder = new File(DATA_FOLDER);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.endsWith("_tasks.txt"));

            if (files != null) {
                for (File file : files) {
                    String filename = file.getName();
                    // Extract username by removing "_tasks.txt" suffix
                    String username = filename.substring(0, filename.length() - 10);
                    users.add(username);
                }
            }
        }

        return users;
    }
}
