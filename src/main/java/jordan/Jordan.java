package jordan;

import jordan.tasks.TaskList;
import jordan.ui.Ui;
import jordan.utilities.Parser;
import jordan.utilities.Storage;

import java.util.Scanner;

public class Jordan {
    private static final String FILE_PATH = "./data/jordan.txt";
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

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
            String response = Parser.parse(ui, tasks, phrase);
            storage.save(tasks);
            return response;
        }
        catch (JordanException e){
            return ui.printError(e);
        }
    }

    public Ui getUi() {
        return this.ui;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean isExitProgram = false;
        while (!isExitProgram) {
            ui.promptUserAddTask();
            String phrase = scanner.nextLine();
            try {
                Parser.parse(ui, tasks, phrase);
                storage.save(tasks);
                isExitProgram = Parser.isExit();
            } catch (JordanException e) {
                ui.printError(e);
            }
        }
    }
    public static void main(String[] args){
        new Jordan("data/jordan.txt").run();
    }
}
