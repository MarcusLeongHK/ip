package jordan.utilities;

import jordan.tasks.Task;
import jordan.tasks.TaskList;

public class Finder {
    public static void find (String keyword, TaskList allTasks){
        TaskList filteredTasks = new TaskList();
        for (Task task: allTasks){
            if (task.isTargetTask(keyword)){
                filteredTasks.add(task);
            }
        }
        System.out.println("Here are the matching tasks in your list: ");
        for (Task task: filteredTasks){
            System.out.printf("%d. %s \n", filteredTasks.indexOf(task) + 1,task);
        }
    }
}
