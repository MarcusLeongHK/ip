package jordan.ui;

import jordan.JordanException;
import jordan.tasks.Task;
import jordan.tasks.TaskList;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Handles user interface interactions for the Jordan application.
 * Provides methods to display messages and interact with the user.
 */
public class Ui {
    /** Introductory message shown to the user. */
    private static final String INTRO = "Hello! I'm Jordan!\n"
            + "How can I help you?";
    /** Farewell message shown to the user. */
    private static final String BYE = "Bye! See you again!\n";

    /**
     * Constructs a Ui object with the specified input and output streams.
     *
     * @param in  the input stream to read user input from
     * @param out the output stream to print messages to
     */
    public Ui(InputStream in, PrintStream out) {
        Scanner in1 = new Scanner(in);
    }

    /**
     * Constructs a Ui object using standard input and output streams.
     */
    public Ui() {
        this(System.in, System.out);
    }

    /**
     * Returns the introductory message.
     *
     * @return the intro message
     */
    public String printIntro() {
        return INTRO;
    }

    /**
     * Returns the farewell message.
     *
     * @return the bye message
     */
    public String printBye() {
        return BYE;
    }

    /**
     * Returns a message indicating a task has been deleted.
     *
     * @param task  the task that was deleted
     * @param tasks the current list of tasks
     * @return the delete task message
     */
    public String printDeleteTask(Task task, TaskList tasks) {
        String res = "I have removed this task. \n";
        res += task.toString();
        return res + "\n" + "Now you have " + tasks.size() + " tasks in the list";
    }

    /**
     * Returns the error message from a JordanException.
     *
     * @param e the exception to display
     * @return the error message
     */
    public String printError(JordanException e) {
        return e.getMessage();
    }

    /**
     * Returns a message indicating a task has been marked as complete.
     *
     * @param task the task that was marked
     * @return the mark task message
     */
    public String printMarkTask(Task task) {
        String res = "Nice! I've marked this task as complete! \n";
        return res + task.toString();
    }

    /**
     * Returns a message indicating a task has been added.
     *
     * @param task  the task that was added
     * @param tasks the current list of tasks
     * @return the add task message
     */
    public String printAddTask(Task task, TaskList tasks) {
        String res = "I have added task: " + task.toString() + "\n";
        return res + "Now you have " + tasks.size() + " tasks in the list";
    }

    /**
     * Returns a formatted list of all tasks.
     *
     * @param tasks the list of tasks
     * @return the formatted task list, or a message if the list is empty
     */
    public String printListTasks(TaskList tasks) {
        if (tasks.isEmpty()) {
            return "There are no tasks in the list";
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            Task currTask = tasks.get(i);
            res.append(String.format("%d." + currTask.toString() + "\n", i + 1));
        }
        return res.toString();
    }

    /**
     * Returns a message with the filtered list of tasks.
     *
     * @param tasks the filtered list of tasks
     * @return the filtered task list message
     */
    public String listFilteredTasks(TaskList tasks) {
        String res = "Here are the matching tasks in your list: \n";
        return res + printListTasks(tasks);
    }

    /**
     * Prompts the user to add a task.
     */
    public void promptUserAddTask() {
        System.out.println("Add Task: ");
    }
}
