package jordan.utilities;

import java.util.List;
import jordan.tasks.Task;
import jordan.tasks.TaskList;
import jordan.ui.Ui;
import org.apache.commons.text.similarity.LevenshteinDistance;


/**
 * Utility class for finding and matching tasks.
 */
public class Finder {

    /**
     * Finds tasks in the given {@link TaskList} that match the specified keyword.
     *
     * @param keyword   The keyword to search for in tasks.
     * @param allTasks  The list of all tasks to search within.
     * @param ui        The UI instance used to format the filtered tasks.
     * @return          A formatted string of filtered tasks matching the keyword.
     */
    public static String findTask(String keyword, TaskList allTasks, Ui ui) {
        TaskList filteredTasks = new TaskList();
        assert keyword != null : "Keyword cannot be null";
        assert filteredTasks != null : "Filtered tasklist cannot be null";
        for (Task task : allTasks) {
            if (task.isTargetTask(keyword)) {
                filteredTasks.add(task);
            }
        }
        return ui.listFilteredTasks(filteredTasks);
    }

    /**
     * Attempts to fuzzy match the given command to a known keyword using Levenshtein distance.
     *
     * @param command   The command string to match.
     * @return          The closest matching keyword if within the threshold, or "No Command Found".
     */
    public static String fuzzyMatch(String command) {
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
