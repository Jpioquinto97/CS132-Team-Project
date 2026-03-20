import java.util.ArrayList;
/// CS132 Authors: Faith, Jason, Bryant
/// User.java Represents a user of the to-do list application
/// Purpose: Manages the user's task list

public class User {
    private String username;
    private ArrayList<Task> tasks;
    
    /// Action to create a new user and gets username
    public User(String username) {
        this.username = username;
        this.tasks = new ArrayList<>();
    }
    
    /// Gets username
    public String getUsername() {
        return username;
    }
    
    /// Ads a task to the users list
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    /// Removes a task from the users lsit.
    /// Return true if successful. 
    public boolean removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            return true;
        }
        return false;
    }
    
    /// Retrieves task
    public Task getTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            return tasks.get(index);
        }
        return null;
    }
    
    /// Gets all tasks
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
    
    /// Retreives only incomplete tasks
    public ArrayList<Task> getIncompleteTasks() {
        ArrayList<Task> incompleteTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
    }
    
    /// Retreives only complete tasks
    public ArrayList<Task> getCompletedTasks() {
        ArrayList<Task> completedTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isCompleted()) {
                completedTasks.add(task);
            }
        }
        return completedTasks;
    }
    
    /// Retreives ototal number of tasks
    public int getTaskCount() {
        return tasks.size();
    }
}
