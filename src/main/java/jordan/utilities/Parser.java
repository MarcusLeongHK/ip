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
        if (phrase.equals("list")) {
            return ui.listTasks(tasks);
        } else if (phrase.equals("bye")) {
            return "Bye! See you again!";
        } else if (phrase.startsWith("mark")) {
            Scanner markScanner = new Scanner(phrase);
            markScanner.next();
            int taskNumber = markScanner.nextInt() - 1;
            Task markedTask = tasks.get(taskNumber);
            tasks.mark(markedTask);
            // return response string
            return ui.markTask(tasks.get(taskNumber));
        } else if (phrase.startsWith("find")) {
            String keyword = phrase.substring("find".length()).trim();
            return Finder.find(keyword, tasks);
        } else if (phrase.startsWith("delete")) {
            Scanner markScanner = new Scanner(phrase);
            markScanner.next();
            int taskNumber = markScanner.nextInt() - 1;
            Task deletedTask  = tasks.get(taskNumber);
            tasks.delete(deletedTask);
            return ui.deleteTask(deletedTask, tasks);
        } else if (phrase.startsWith("todo")) {
            String desc = phrase.substring("todo".length()).trim();
            if (desc.isEmpty()) {
                throw new JordanException("Todo task requires a description");
            }
            Task newTask = new Todo(desc);
            tasks.addTask(newTask);
            return ui.addTask(newTask, tasks);
        } else if (phrase.startsWith("deadline")) {
            int indexOfBy = phrase.indexOf("/by");
            if (indexOfBy == -1) {
                throw new JordanException("Deadline requires a due date");
            }
            String desc = phrase.substring("deadline".length(), indexOfBy).trim();
            if (desc.isEmpty()) {
                throw new JordanException("Deadline requires a description");
            }
            String by = phrase.substring(indexOfBy + 3).trim();
            LocalDate byDate = LocalDate.parse(by);
            if (by.isEmpty()) {
                throw new JordanException("Deadline requires a due date");
            }
            Task newTask = new Deadline(desc, byDate);
            tasks.addTask(newTask);
            return ui.addTask(newTask, tasks);
        } else if (phrase.startsWith("event")) {
            int fromIndex = phrase.indexOf("/from");
            int toIndex = phrase.indexOf("/to");
            if (fromIndex == -1 || toIndex == -1) {
                throw new JordanException("Event requires a from / to");
            }
            String desc = phrase.substring("event".length(), fromIndex).trim();
            String from = phrase.substring(fromIndex + 5, toIndex).trim();
            String to = phrase.substring(toIndex + 3).trim();
            LocalDate fromDate = LocalDate.parse(from);
            LocalDate toDate = LocalDate.parse(to);
            if (desc.isEmpty()) {
                throw new JordanException("Event requires a description");
            } else if (from.isEmpty() || to.isEmpty()) {
                throw new JordanException("Event requires a from / to");
            }
            Task newTask = new Event(desc, fromDate, toDate);
            tasks.addTask(newTask);
            return ui.addTask(newTask, tasks);
        }
        else{
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
