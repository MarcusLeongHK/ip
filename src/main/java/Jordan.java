import java.time.LocalDate;
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
        boolean isEcho = true;
        while (isEcho) {
            ui.addTask();
            String phrase = scanner.nextLine();
            try {
                if (phrase.equals("list")) {
                    for (int i = 0; i < tasks.size(); i++) {
                        Task currTask = tasks.get(i);
                        System.out.printf("%d." + currTask.toString() + "\n", i + 1);
                    }
                } else if (phrase.equals("bye")) {
                    isEcho = false;
                } else if (phrase.startsWith("mark")) {
                    Scanner markScanner = new Scanner(phrase);
                    markScanner.next();
                    int taskNumber = markScanner.nextInt();
                    tasks.mark(taskNumber);
                } else if (phrase.startsWith("delete")) {
                    Scanner markScanner = new Scanner(phrase);
                    markScanner.next();
                    int taskNumber = markScanner.nextInt();
                    tasks.delete(taskNumber);
                } else if (phrase.startsWith("todo")) {
                    String desc = phrase.substring("todo".length()).trim();
                    if (desc.isEmpty()) {
                        throw new JordanException("Todo task requires a description");
                    }
                    tasks.addTask(new Todo(desc));
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
                    tasks.addTask(new Deadline(desc, byDate));
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
                    tasks.addTask(new Event(desc, fromDate, toDate));
                }
                storage.save(tasks);
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
