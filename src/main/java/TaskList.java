import java.util.ArrayList;

public class TaskList extends ArrayList<Task> {
    public TaskList(){
        super();
    }

    public TaskList(ArrayList<Task> loadedTasks){
        super(loadedTasks);
    }

    public void mark(int taskNumber){
        System.out.println("Nice! I've marked this task as complete!");
        Task markedTask = this.get(taskNumber - 1);
        markedTask.markAsDone();
        System.out.print(markedTask);
    }

    public void delete(int taskNumber){
        System.out.println("I have removed this task.");
        Task deletedTask = this.get(taskNumber - 1);
        this.remove(deletedTask);
        System.out.print(deletedTask);
        System.out.println("Now you have " + this.size() + " tasks in the list");
    }

    public void addTask(Task task){
        this.add(task);
        System.out.println("I have added task: " + this.get(this.size() - 1));
        System.out.println("Now you have " + this.size() + " tasks in the list");
    }
}
