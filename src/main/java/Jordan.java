import java.time.LocalDate;
import java.util.Scanner;

public class Jordan {
    private static final String FILE_PATH = "./data/jordan.txt";
    private final Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Jordan(String filePath) {
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(this.storage.load());
        } catch (JordanException e) {
            System.out.println(e.getMessage());
            tasks = new TaskList();
        }
    }
    public void run(){
        String introText = "Hello! I'm Jordan!\n"
                + "How can I help you?";
        System.out.println(introText);
        Scanner scanner = new Scanner(System.in);
        boolean isEcho = true;
        while (isEcho) {
            System.out.println("Add task: ");
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
                    System.out.println("Nice! I've marked this task as complete!");
                    Task markedTask = tasks.get(taskNumber - 1);
                    markedTask.markAsDone();
                    System.out.print(markedTask);
                } else if (phrase.startsWith("delete")) {
                    Scanner markScanner = new Scanner(phrase);
                    markScanner.next();
                    int taskNumber = markScanner.nextInt();
                    System.out.println("I have removed this task.");
                    Task deletedTask = tasks.get(taskNumber - 1);
                    tasks.remove(deletedTask);
                    System.out.print(deletedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list");
                } else if (phrase.startsWith("todo")) {
                    String desc = phrase.substring("todo".length()).trim();
                    if (desc.isEmpty()) {
                        throw new JordanException("Todo task requires a description");
                    }
                    tasks.add(new Todo(desc));
                    System.out.println("I have added task: " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list");
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
                    tasks.add(new Deadline(desc, byDate));
                    System.out.println("I have added task: " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list");
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
                    tasks.add(new Event(desc, fromDate, toDate));
                    System.out.println("I have added task: " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list");
                }
                storage.save(tasks);
            } catch (JordanException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Bye! See you again!\n");
    }
        public static void main(String[] args){
            new Jordan("data/jordan.txt").run();
        }
    }
