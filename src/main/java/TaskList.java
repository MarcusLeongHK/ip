import java.util.ArrayList;

public class TaskList extends ArrayList<Task> {
    private ArrayList<Task> tasks;
    public TaskList(){
        super();
    }

    public TaskList(ArrayList<Task> loadedTasks){
        super(loadedTasks);
    }
}
