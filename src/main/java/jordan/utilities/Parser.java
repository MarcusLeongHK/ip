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
 * Handles parsing and execution of user commands for the Jordan task manager.
 * Supports commands to list, add, mark, delete, and find tasks.
 */
public class Parser {
    /** Indicates if the user has requested to exit the application. */
    private static boolean isExit = false;

    /**
     * Parses the user input and executes the corresponding command.
     *
     * @param ui The UI instance for displaying messages.
     * @param tasks The TaskList containing all current tasks.
     * @param phrase The full command input from the user.
     * @return The response message after executing the command.
     * @throws JordanException If the command format is invalid or an error occurs.
     */
    public static String parse(Ui ui, TaskList tasks, String phrase) throws JordanException {
        String command = phrase.split(" ")[0];
        command = Finder.fuzzyMatch(command);
        switch (command) {
        case "list":
            // List all tasks
            return ui.printListTasks(tasks);
        case "bye":
            // Exit the application
            isExit = true;
            return "Bye! See you again!";
        case "mark":
            // Mark a task as done
            return handleMarkCommand(ui, tasks, phrase);
        case "find":
            // Find tasks by keyword
            return handleFindCommand(ui, tasks, phrase);
        case "delete":
            // Delete a task
            return handleDeleteCommand(ui, tasks, phrase);
        case "todo":
            // Add a Todo task
            return handleTodoCommand(ui, tasks, phrase);
        case "deadline":
            // Add a Deadline task
            return handleDeadlineCommand(ui, tasks, phrase);
        case "event":
            // Add an Event task
            return handleEventCommand(ui, tasks, phrase);
        default:
            // Unknown command
            return "Please enter a prompt";
        }
    }

    /**
     * Handles the 'mark' command to mark a task as done.
     *
     * @param ui The UI instance.
     * @param tasks The TaskList.
     * @param phrase The user input.
     * @return The response message.
     * @throws JordanException If the task index is invalid.
     */
    private static String handleMarkCommand(Ui ui, TaskList tasks, String phrase) throws JordanException {
        int markTaskNumber = extractTaskNumber(phrase);
        validateTaskIndex(markTaskNumber, tasks.size());
        Task markedTask = tasks.get(markTaskNumber);
        tasks.mark(markedTask);
        return ui.printMarkTask(markedTask);
    }

    /**
     * Handles the 'find' command to search for tasks by keyword.
     *
     * @param ui The UI instance.
     * @param tasks The TaskList.
     * @param phrase The user input.
     * @return The response message.
     */
    private static String handleFindCommand(Ui ui, TaskList tasks, String phrase) {
        String keyword = phrase.substring("find".length()).trim();
        return Finder.findTask(keyword, tasks, ui);
    }

    /**
     * Handles the 'delete' command to remove a task.
     *
     * @param ui The UI instance.
     * @param tasks The TaskList.
     * @param phrase The user input.
     * @return The response message.
     * @throws JordanException If the task index is invalid.
     */
    private static String handleDeleteCommand(Ui ui, TaskList tasks, String phrase) throws JordanException {
        int deleteTaskNumber = extractTaskNumber(phrase);
        validateTaskIndex(deleteTaskNumber, tasks.size());
        Task deletedTask = tasks.get(deleteTaskNumber);
        tasks.delete(deletedTask);
        return ui.printDeleteTask(deletedTask, tasks);
    }

    /**
     * Handles the 'todo' command to add a new Todo task.
     *
     * @param ui The UI instance.
     * @param tasks The TaskList.
     * @param phrase The user input.
     * @return The response message.
     * @throws JordanException If the description is empty.
     */
    private static String handleTodoCommand(Ui ui, TaskList tasks, String phrase) throws JordanException {
        String desc = phrase.substring("todo".length()).trim();
        if (desc.isEmpty()) {
            throw new JordanException("Todo task requires a description");
        }
        Task newTask = new Todo(desc);
        tasks.addTask(newTask);
        return ui.printAddTask(newTask, tasks);
    }

    /**
     * Handles the 'deadline' command to add a new Deadline task.
     *
     * @param ui The UI instance.
     * @param tasks The TaskList.
     * @param phrase The user input.
     * @return The response message.
     * @throws JordanException If the description or date is missing or invalid.
     */
    private static String handleDeadlineCommand(Ui ui, TaskList tasks, String phrase) throws JordanException {
        int indexOfBy = phrase.indexOf("/by");
        if (indexOfBy == -1) {
            throw new JordanException("Deadline requires a due date\nE.g. deadline return book /by 2019-12-01");
        }
        String deadlineDesc = phrase.substring("deadline".length(), indexOfBy).trim();
        if (deadlineDesc.isEmpty()) {
            throw new JordanException("Deadline requires a description");
        }
        String by = phrase.substring(indexOfBy + 3).trim();
        if (by.isEmpty()) {
            throw new JordanException("Deadline requires a due date\nE.g. deadline return book /by 2019-12-01");
        }
        LocalDate byDate = LocalDate.parse(by);
        Task newDeadlineTask = new Deadline(deadlineDesc, byDate);
        tasks.addTask(newDeadlineTask);
        return ui.printAddTask(newDeadlineTask, tasks);
    }

    /**
     * Handles the 'event' command to add a new Event task.
     *
     * @param ui The UI instance.
     * @param tasks The TaskList.
     * @param phrase The user input.
     * @return The response message.
     * @throws JordanException If the description or dates are missing or invalid.
     */
    private static String handleEventCommand(Ui ui, TaskList tasks, String phrase) throws JordanException {
        int fromIndex = phrase.indexOf("/from");
        int toIndex = phrase.indexOf("/to");
        if (fromIndex == -1 || toIndex == -1) {
            throw new JordanException("Event requires a from date & to date\n" +
                    "E.g. event project meeting /from 2019-10-15 /to 2019-10-16");
        }
        String eventDesc = phrase.substring("event".length(), fromIndex).trim();
        String from = phrase.substring(fromIndex + 5, toIndex).trim();
        String to = phrase.substring(toIndex + 3).trim();
        if (eventDesc.isEmpty()) {
            throw new JordanException("Event requires a description");
        } else if (from.isEmpty() || to.isEmpty()) {
            throw new JordanException("Event requires a from date & to date\n" +
                    "E.g. event project meeting /from 2019-10-15 /to 2019-10-16");
        }
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);
        Task newEventTask = new Event(eventDesc, fromDate, toDate);
        tasks.addTask(newEventTask);
        return ui.printAddTask(newEventTask, tasks);
    }

    /**
     * Extracts the task number from the user input.
     *
     * @param phrase The user input.
     * @return The zero-based task index.
     * @throws JordanException If the index is not a valid number.
     */
    private static int extractTaskNumber(String phrase) throws JordanException {
        Scanner scanner = new Scanner(phrase);
        scanner.next();
        if (!scanner.hasNextInt()) {
            throw new JordanException("Task index must be an Integer");
        }
        return scanner.nextInt() - 1;
    }

    /**
     * Validates that the task index is within the valid range.
     *
     * @param index The task index.
     * @param size The size of the task list.
     * @throws JordanException If the index is out of bounds.
     */
    private static void validateTaskIndex(int index, int size) throws JordanException {
        if (index < 0 || index >= size) {
            throw new JordanException("Task index is not in the TaskList");
        }
    }

    /**
     * Returns whether the user has requested to exit the application.
     *
     * @return True if the user entered 'bye', false otherwise.
     */
    public static boolean isExit() {
        return isExit;
    }
}
