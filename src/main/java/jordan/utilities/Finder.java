package jordan.utilities;

import jordan.tasks.Task;
import jordan.tasks.TaskList;

public class Finder {
    public static String find (String keyword, TaskList allTasks){
        TaskList filteredTasks = new TaskList();
        for (Task task: allTasks){
            if (task.isTargetTask(keyword)){
                filteredTasks.add(task);
            }
        }
        String res = "Here are the matching tasks in your list: \n";
        for (Task task: filteredTasks){
            res += String.format("%d. %s \n", filteredTasks.indexOf(task) + 1, task);
        }
        return res;
    }
}
