package jordan.utilities;

import java.util.List;
import jordan.tasks.Task;
import jordan.tasks.TaskList;
import jordan.ui.Ui;
import org.apache.commons.text.similarity.LevenshteinDistance;


public class Finder {
    public static String findTask (String keyword, TaskList allTasks, Ui ui){
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

    public static String fuzzyMatch (String command) {
        int maxDistThreshold = 2;
        LevenshteinDistance levDist = new LevenshteinDistance();
        List<String> keywords = List.of("list", "bye", "mark", "find",
                "delete", "todo", "deadline", "event");
        for (String keyword : keywords) {
            int distance = levDist.apply(keyword, command);
            if (distance <= maxDistThreshold) {
                return keyword;
            }
        }
        return "No Command Found";
    }
}
