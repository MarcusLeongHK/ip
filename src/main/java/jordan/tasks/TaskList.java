package jordan.tasks;

import java.util.ArrayList;
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
     * @param taskNumber the index of the task in the list
     */
    public void mark(int taskNumber){
        System.out.println("Nice! I've marked this task as complete!");
        Task markedTask = this.get(taskNumber - 1);
        markedTask.markAsDone();
        System.out.print(markedTask);
    }
    /**
     * Deletes a task from the list and prints a completion message.
     *
     * @param taskNumber the index of the task in the list
     */
    public void delete(int taskNumber){
        System.out.println("I have removed this task.");
        Task deletedTask = this.get(taskNumber - 1);
        this.remove(deletedTask);
        System.out.print(deletedTask);
        System.out.println("Now you have " + this.size() + " tasks in the list");
    }
    /**
     * Adds a task from the list and prints a completion message.
     *
     * @param task the task to be added to the list
     */
    public void addTask(Task task){
        this.add(task);
        System.out.println("I have added task: " + this.get(this.size() - 1));
        System.out.println("Now you have " + this.size() + " tasks in the list");
    }
}
