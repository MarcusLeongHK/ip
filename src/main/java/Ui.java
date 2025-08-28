import java.awt.*;
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

    public void intro(){
        System.out.println(INTRO);
    }

    public void bye(){
        System.out.println(BYE);
    }

    public void printError(JordanException e){
        System.out.println(e.getMessage());
    }

    public void printTask(Task task){
        System.out.println(task);
    }

    public void addTask(){
        System.out.println("Add Task: ");
    }
}
