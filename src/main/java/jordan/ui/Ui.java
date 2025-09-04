package jordan.ui;

import jordan.JordanException;
import jordan.tasks.Task;
import jordan.tasks.TaskList;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Ui {
    private static final String INTRO = "Hello! I'm Jordan!\n"
            + "How can I help you?";
    private static final String BYE = "Bye! See you again!\n";

    private final Scanner in;
    private final PrintStream out;

    public Ui (InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }
    public Ui(){
        this(System.in,System.out);
    }

    public String intro(){
        return INTRO;
    }

    public String bye(){
        return BYE;
    }

    public String deleteTask (Task task, TaskList tasks){
        String res = "I have removed this task. \n";
        res += task.toString();
        return res + "Now you have " + tasks.size() + " tasks in the list";
    }

    public String printError(JordanException e){
        return e.getMessage();
    }

    public void printTask(Task task){
        System.out.println(task);
    }

    public String markTask (Task task) {
        String res = "Nice! I've marked this task as complete! \n";
        return res + task.toString();
    }

    public String addTask (Task task, TaskList tasks) {
        String res = "I have added task: " + task.toString() + "\n";
        return res + "Now you have " + tasks.size() + " tasks in the list";
    }

    public String listTasks (TaskList tasks){
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

    public void promptAddTask(){
        System.out.println("Add Task: ");
    }
}
