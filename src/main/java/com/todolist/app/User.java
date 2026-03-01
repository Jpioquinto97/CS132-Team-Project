import java.util.ArrayList;
/// CS132 Authors: Faith, Jason, Bryant
/// User.java Represents a user of the to-do list application
/// Purpose: Manages the user's task list

public class User {
    private String username;
    private ArrayList<Task> tasks;
    
    /**
     * Constructor to create a new user
     * @param username The user's username
     */
    public User(String username) {
        this.username = username;
        this.tasks = new ArrayList<>();
    }
    
    /**
     * Gets the username
     * @return Username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Adds a task to the user's list
     * @param task Task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    /**
     * Removes a task from the user's list
     * @param index Index of task to remove
     * @return true if successful, false if invalid index
     */
    public boolean removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            return true;
        }
        return false;
    }
    
    /**
     * Gets a specific task
     * @param index Index of task
     * @return Task object or null if invalid index
     */
    public Task getTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            return tasks.get(index);
        }
        return null;
    }
    
    /**
     * Gets all tasks
     * @return ArrayList of all tasks
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
    
    /**
     * Gets only incomplete tasks
     * @return ArrayList of incomplete tasks
     */
    public ArrayList<Task> getIncompleteTasks() {
        ArrayList<Task> incompleteTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
    }
    
    /**
     * Gets only completed tasks
     * @return ArrayList of completed tasks
     */
    public ArrayList<Task> getCompletedTasks() {
        ArrayList<Task> completedTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isCompleted()) {
                completedTasks.add(task);
            }
        }
        return completedTasks;
    }
    
    /**
     * Gets total number of tasks
     * @return Number of tasks
     */
    public int getTaskCount() {
        return tasks.size();
    }
}
