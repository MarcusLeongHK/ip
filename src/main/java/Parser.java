import java.time.LocalDate;
import java.util.Scanner;

public class Parser {
    private static boolean isExit = false;
    public static void parse(Ui ui, TaskList tasks, String phrase) throws JordanException {
        if (phrase.equals("list")) {
            ui.listTasks(tasks);
        } else if (phrase.equals("bye")) {
            isExit = true;
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
    }
    public static boolean isExit(){
        return isExit;
    }
}
