/// CS132 Authors: Faith, Jason, Bryant, Jonathon
/// Task.java - Data Model with Progress Tracking
/// Purpose: Represents a single task with progress tracking capability
package com.todolist.app;
import java.util.ArrayList;

public class Task {
    private String title;
    private String description;
    private boolean isCompleted;
    private int progressPercentage; // Track progress 0-100%
    private ArrayList<SubTask> subTasks; // Subtasks for detailed tracking

    /// Constructor creates a new incomplete task
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.isCompleted = false;
        this.progressPercentage = 0;
        this.subTasks = new ArrayList<>();
    }

    // ─────────────────────────────────────────────
    // GETTERS AND SETTERS
    // ─────────────────────────────────────────────

    /// Returns the task title
    public String getTitle() {
        return title;
    }

    /// Updates the task title
    public void setTitle(String title) {
        this.title = title;
    }

    /// Returns the task description
    public String getDescription() {
        return description;
    }

    /// Updates the task description
    public void setDescription(String description) {
        this.description = description;
    }

    /// Returns whether the task is completed
    public boolean isCompleted() {
        return isCompleted;
    }

    /// Returns the current progress percentage (0-100)
    public int getProgressPercentage() {
        return progressPercentage;
    }

    /// Sets progress percentage directly - validates range 0-100
    public void setProgressPercentage(int progress) {
        this.progressPercentage = Math.min(100, Math.max(0, progress));
        if (this.progressPercentage == 100) {
            isCompleted = true;
        }
    }

    /// Returns the list of subtasks
    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    /// Sets completion status and updates progress accordingly
    public void setCompleted(boolean completed) {
        isCompleted = completed;
        if (completed) {
            progressPercentage = 100;
            // Mark all subtasks as complete
            for (SubTask subTask : subTasks) {
                subTask.setCompleted(true);
            }
        }
    }

    // ─────────────────────────────────────────────
    // SUBTASK AND PROGRESS METHODS
    // ─────────────────────────────────────────────

    /// Adds a subtask to this task
    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
        updateProgress(); // Recalculate progress when subtask added
    }

    /// Updates progress percentage based on subtask completion
    public void updateProgress() {
        if (subTasks.isEmpty()) {
            // If no subtasks, progress is either 0 or 100
            progressPercentage = isCompleted ? 100 : 0;
        } else {
            // Calculate average progress of all subtasks
            int totalProgress = 0;
            for (SubTask subTask : subTasks) {
                totalProgress += subTask.getProgressPercentage();
            }
            progressPercentage = totalProgress / subTasks.size();

            // Auto-mark complete if all subtasks are done
            if (progressPercentage == 100) {
                isCompleted = true;
            }
        }
    }

    /// Creates a visual progress bar string of the given length
    public String getProgressBar(int length) {
        int filledLength = (int) Math.round(length * progressPercentage / 100.0);
        StringBuilder bar = new StringBuilder();

        bar.append("[");
        for (int i = 0; i < length; i++) {
            if (i < filledLength) {
                bar.append("█"); // Filled block
            } else {
                bar.append("░"); // Empty block
            }
        }
        bar.append("] ");
        bar.append(progressPercentage).append("%");

        return bar.toString();
    }

    /// Returns a formatted string with title, description, progress bar, and subtasks
    public String getDisplayString() {
        StringBuilder display = new StringBuilder();

        // Add title and description
        display.append(title);
        if (description != null && !description.isEmpty()) {
            display.append(" - ").append(description);
        }
        display.append("\n");

        // Add progress bar
        display.append("  Progress: ").append(getProgressBar(20)).append("\n");

        // Add subtasks if any
        if (!subTasks.isEmpty()) {
            display.append("  Subtasks:\n");
            for (int i = 0; i < subTasks.size(); i++) {
                SubTask subTask = subTasks.get(i);
                display.append("    ").append(i + 1).append(". ");
                display.append(subTask.getDisplayString(15)).append("\n");
            }
        }

        return display.toString();
    }

    /// Converts task to a pipe-separated string for file storage
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("|").append(description).append("|")
                .append(isCompleted).append("|").append(progressPercentage);

        // Save subtasks if any
        if (!subTasks.isEmpty()) {
            sb.append("|SUBTASKS:");
            for (SubTask subTask : subTasks) {
                sb.append(subTask.toFileString()).append(";");
            }
        }

        return sb.toString();
    }

    /// Creates a Task object from a pipe-separated file string
    public static Task fromFileString(String line) {
        String[] parts = line.split("\\|");
        Task task = new Task(parts[0], parts[1]);
        task.setCompleted(Boolean.parseBoolean(parts[2]));
        task.progressPercentage = Integer.parseInt(parts[3]);

        // Load subtasks if present
        if (parts.length > 4 && parts[4].startsWith("SUBTASKS:")) {
            String subtaskData = parts[4].substring(9); // Remove "SUBTASKS:" prefix
            String[] subtaskParts = subtaskData.split(";");
            for (String subtaskPart : subtaskParts) {
                if (!subtaskPart.isEmpty()) {
                    SubTask subTask = SubTask.fromFileString(subtaskPart);
                    task.subTasks.add(subTask);
                }
            }
        }

        return task;
    }

    @Override
    public String toString() {
        return getDisplayString();
    }
}

/// SubTask - Inner class for task breakdown
class SubTask {
    private String name;
    private boolean isCompleted;
    private int progressPercentage;

    /// Constructor creates a new incomplete subtask
    public SubTask(String name) {
        this.name = name;
        this.isCompleted = false;
        this.progressPercentage = 0;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    /// Sets completion status and updates progress to match
    public void setCompleted(boolean completed) {
        isCompleted = completed;
        progressPercentage = completed ? 100 : 0;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    /// Sets progress percentage, validates range 0-100
    public void setProgressPercentage(int progress) {
        this.progressPercentage = Math.min(100, Math.max(0, progress));
        if (progressPercentage == 100) {
            isCompleted = true;
        }
    }

    /// Returns formatted display string with progress bar
    public String getDisplayString(int barLength) {
        StringBuilder sb = new StringBuilder();
        sb.append(name);

        if (isCompleted) {
            sb.append(" ✓");
        }

        sb.append("\n      ").append(getProgressBar(barLength));

        return sb.toString();
    }

    /// Creates a visual progress bar of the given length
    private String getProgressBar(int length) {
        int filledLength = (int) Math.round(length * progressPercentage / 100.0);
        StringBuilder bar = new StringBuilder();

        bar.append("[");
        for (int i = 0; i < length; i++) {
            if (i < filledLength) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        bar.append("] ");
        bar.append(progressPercentage).append("%");

        return bar.toString();
    }

    /// Converts subtask to a comma-separated string for file storage
    public String toFileString() {
        return name + "," + isCompleted + "," + progressPercentage;
    }

    /// Creates a SubTask object from a comma-separated string
    public static SubTask fromFileString(String data) {
        String[] parts = data.split(",");
        SubTask subTask = new SubTask(parts[0]);
        subTask.setCompleted(Boolean.parseBoolean(parts[1]));
        subTask.setProgressPercentage(Integer.parseInt(parts[2]));
        return subTask;
    }
}