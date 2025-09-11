package jordan;

import javafx.application.Application;
import jordan.tasks.TaskList;
import jordan.ui.Ui;
import jordan.utilities.Parser;
import jordan.utilities.Storage;

import java.util.Scanner;

public class Jordan {
    private static final String FILE_PATH = "./data/jordan.txt";
    private final Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Jordan(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
        try {
            this.tasks = new TaskList(this.storage.load());
        } catch (JordanException e) {
            ui.printError(e);
            tasks = new TaskList();
        }
    }
    public Jordan(){
        this(FILE_PATH);
    }
    public String getResponse(String phrase) {
        try {
            String res = Parser.parse(ui, tasks, phrase);
            assert res != null : "Response cannot be null";
            storage.save(tasks);
            return res;
        }
        catch (JordanException e){
            return ui.printError(e);
        }
    }
    public void run() {
        ui.intro();
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;
        while (!isExit) {
            ui.promptAddTask();
            String phrase = scanner.nextLine();
            try {
                Parser.parse(ui, tasks, phrase);
                storage.save(tasks);
                isExit = Parser.isExit();
            } catch (JordanException e) {
                ui.printError(e);
            }
        }
        ui.bye();
    }
    public static void main(String[] args){
        new Jordan("data/jordan.txt").run();
    }
}
