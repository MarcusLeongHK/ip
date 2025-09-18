package jordan.tasks;

/**
 * Represents a generic task with a description and completion status.
 */
public class Task {
    /**
     * The description of the task.
     */
    protected String description;

    /**
     * Indicates whether the task is completed.
     */
    protected boolean isDone;

    /**
     * Constructs a new Task with the specified description.
     *
     * @param description The description of the task.
     * @throws AssertionError if the description is null.
     */
    public Task(String description) {
        assert description != null : "Description cannot be null";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether the task is done.
     *
     * @return "X" if the task is done, otherwise a space character.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Returns a string representation of the task, including its status icon and description.
     *
     * @return The formatted string representation of the task.
     */
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.description);
    }

    /**
     * Returns a string representation of the task for saving purposes.
     *
     * @return The string to be saved.
     */
    public String saveToString() {
        return this.toString();
    }

    /**
     * Checks if the task's description contains the specified keyword.
     *
     * @param desc The keyword to search for.
     * @return true if the description contains the keyword, false otherwise.
     */
    public boolean isTargetTask(String desc) {
        return this.description.contains(desc);
    }
/**
         * Returns the description of the task.
         *
         * @return The task description.
         */
    public String getDescription() {
        return this.description;
    }
}
