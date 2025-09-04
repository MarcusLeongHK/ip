package jordan.tasks;

import java.util.ArrayList;
import jordan.ui.Ui;

/**
 * Represents a list of Tasks. The methods provided in this class allow for the tasks in the list
 * to be mutated. Moreover, for the list itself to be mutated.
 */
public class TaskList extends ArrayList<Task> {
    public TaskList(){
        super();
    }

    public TaskList(ArrayList<Task> loadedTasks){
        super(loadedTasks);
    }
    /**
     * Marks a task as completed and prints a completion message.
     *
     * @param markedTask the index of the task in the list
     */
    public void mark(Task markedTask){
        markedTask.markAsDone();
    }
    /**
     * Deletes a task from the list and prints a completion message.
     *
     * @param deletedTask the index of the task in the list
     */
    public void delete(Task deletedTask){
        this.remove(deletedTask);
    }
    /**
     * Adds a task from the list and prints a completion message.
     *
     * @param task the task to be added to the list
     */
    public void addTask(Task task){
        this.add(task);
    }
}
