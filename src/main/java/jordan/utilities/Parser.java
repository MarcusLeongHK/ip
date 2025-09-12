package jordan.utilities;

import jordan.JordanException;
import jordan.tasks.Task;
import jordan.tasks.TaskList;
import jordan.tasks.Todo;
import jordan.tasks.Deadline;
import jordan.tasks.Event;
import jordan.ui.Ui;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Represents a Parser which handles commands given by the user to retrieve information of tasks
 * or to add mutate the task or the list.
 */
public class Parser {
    private static boolean isExit = false;
    /**
     * Returns lateral location of the specified position.
     * If the position is unset, NaN is returned.
     *
     * @param ui UI of Jordan
     * @param tasks TaskList containing tasks
     * @param phrase the command phrase given
     * @throws JordanException If improper formatting is given for the commands.
     */
    public static String parse(Ui ui, TaskList tasks, String phrase) throws JordanException {
        String command = phrase.split(" ")[0];
        command = Finder.fuzzyMatch(command);
        switch (command) {
        case "list":
            return ui.printListTasks(tasks);
        case "bye":
            return "Bye! See you again!";
        case "mark":
            Scanner markScanner = new Scanner(phrase);
            markScanner.next();
            int markTaskNumber = markScanner.nextInt() - 1;
            if (markTaskNumber < 0 || markTaskNumber >= tasks.size()) {
                throw new JordanException("Task index is not in the TaskList");
            }
            Task markedTask = tasks.get(markTaskNumber);
            tasks.mark(markedTask);
            // return response string
            return ui.printMarkTask(tasks.get(markTaskNumber));
        case "find":
            String keyword = phrase.substring("find".length()).trim();
            return Finder.findTask(keyword, tasks, ui);
        case "delete":
            Scanner deleteScanner = new Scanner(phrase);
            deleteScanner.next();
            int deleteTaskNumber = deleteScanner.nextInt() - 1;
            if (deleteTaskNumber < 0 || deleteTaskNumber >= tasks.size()) {
                throw new JordanException("Task index is not in the TaskList");
            }
            Task deletedTask  = tasks.get(deleteTaskNumber);
            tasks.delete(deletedTask);
            return ui.printDeleteTask(deletedTask, tasks);
        case "todo":
            String desc = phrase.substring("todo".length()).trim();
            if (desc.isEmpty()) {
                throw new JordanException("Todo task requires a description");
            }
            Task newTask = new Todo(desc);
            tasks.addTask(newTask);
            return ui.printAddTask(newTask, tasks);
        case "deadline":
            int indexOfBy = phrase.indexOf("/by");
            if (indexOfBy == -1) {
                throw new JordanException("Deadline requires a due date");
            }
            String deadlineDesc = phrase.substring("deadline".length(), indexOfBy).trim();
            if (deadlineDesc.isEmpty()) {
                throw new JordanException("Deadline requires a description");
            }
            String by = phrase.substring(indexOfBy + 3).trim();
            LocalDate byDate = LocalDate.parse(by);
            if (by.isEmpty()) {
                throw new JordanException("Deadline requires a due date");
            }
            Task newDeadlineTask = new Deadline(deadlineDesc, byDate);
            tasks.addTask(newDeadlineTask);
            return ui.printAddTask(newDeadlineTask, tasks);
        case "event":
            int fromIndex = phrase.indexOf("/from");
            int toIndex = phrase.indexOf("/to");
            if (fromIndex == -1 || toIndex == -1) {
                throw new JordanException("Event requires a from date & to date");
            }
            String eventDesc = phrase.substring("event".length(), fromIndex).trim();
            String from = phrase.substring(fromIndex + 5, toIndex).trim();
            String to = phrase.substring(toIndex + 3).trim();
            LocalDate fromDate = LocalDate.parse(from);
            LocalDate toDate = LocalDate.parse(to);
            if (eventDesc.isEmpty()) {
                throw new JordanException("Event requires a description");
            } else if (from.isEmpty() || to.isEmpty()) {
                throw new JordanException("Event requires a from date & to date");
            }
            Task newEventTask = new Event(eventDesc, fromDate, toDate);
            tasks.addTask(newEventTask);
            return ui.printAddTask(newEventTask, tasks);
        default:
            return "Please enter a prompt";
        }
    }
    /**
     * Returns the boolean value when the user commands "bye"
     * indicating the end of the program.
     *
     * @return isExit boolean to exit the loop.
     */
    public static boolean isExit(){
        return isExit;
    }
}
