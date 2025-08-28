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
    public void run(){
        ui.intro();
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;
        while (!isExit) {
            ui.addTask();
            String phrase = scanner.nextLine();
            try {
                Parser.parse(ui,tasks,phrase);
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
