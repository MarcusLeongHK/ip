package jordan.utilities;

import jordan.tasks.Task;
import jordan.tasks.TaskList;
import jordan.ui.Ui;

public class Finder {
    public static String find (String keyword, TaskList allTasks, Ui ui){
        TaskList filteredTasks = new TaskList();
        assert keyword != null : "Keyword cannot be null";
        assert filteredTasks != null : "Filtered tasklist cannot be null";
        for (Task task: allTasks){
            if (task.isTargetTask(keyword)){
                filteredTasks.add(task);
            }
        }
        return ui.listFilteredTasks(filteredTasks);
    }
}
