import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Jordan {
    private static final String FILE_PATH = "./data/jordan.txt";

    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        loadTasksFromFile(tasks);
        String introText = "Hello! I'm Jordan!\n"
                + "How can I help you?";
        System.out.println(introText);
        Scanner scanner = new Scanner(System.in);
        boolean isEcho = true;
        while (isEcho){
            System.out.println("Add task: ");
            String phrase = scanner.nextLine();
            try{
                if (phrase.equals("list")) {
                    for (int i = 0; i < tasks.size(); i++) {
                        Task currTask = tasks.get(i);
                        System.out.printf("%d." + currTask.toString() + "\n",i+1);
                    }
                }
                else if (phrase.equals("bye")){
                    isEcho = false;
                }
                else if (phrase.startsWith("mark")){
                    Scanner markScanner = new Scanner(phrase);
                    markScanner.next();
                    int taskNumber = markScanner.nextInt();
                    System.out.println("Nice! I've marked this task as complete!");
                    Task markedTask = tasks.get(taskNumber-1);
                    markedTask.markAsDone();
                    System.out.print(markedTask);
                }
                else if (phrase.startsWith("delete")){
                    Scanner markScanner = new Scanner(phrase);
                    markScanner.next();
                    int taskNumber = markScanner.nextInt();
                    System.out.println("I have removed this task.");
                    Task deletedTask = tasks.get(taskNumber-1);
                    tasks.remove(deletedTask);
                    System.out.print(deletedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list");
                }
                else if (phrase.startsWith("todo")){
                    String desc = phrase.substring("todo".length()).trim();
                    if (desc.isEmpty()){
                        throw new JordanException("Todo task requires a description");
                    }
                    tasks.add(new Todo(desc));
                    System.out.println("I have added task: " + tasks.get(tasks.size()-1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list");
                }
                else if (phrase.startsWith("deadline")){
                    int indexOfBy = phrase.indexOf("/by");
                    if (indexOfBy == -1){
                        throw new JordanException("Deadline requires a due date");
                    }
                    String desc = phrase.substring("deadline".length(), indexOfBy).trim();
                    if (desc.isEmpty()){
                        throw new JordanException("Deadline requires a description");
                    }
                    String by = phrase.substring(indexOfBy+3).trim();
                    LocalDate byDate = LocalDate.parse(by);
                    if (by.isEmpty()){
                        throw new JordanException("Deadline requires a due date");
                    }
                    tasks.add(new Deadline(desc, byDate));
                    System.out.println("I have added task: " + tasks.get(tasks.size()-1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list");
                }
                else if (phrase.startsWith("event")) {
                    int fromIndex = phrase.indexOf("/from");
                    int toIndex = phrase.indexOf("/to");
                    if (fromIndex == -1 || toIndex == -1){
                        throw new JordanException("Event requires a from / to");
                    }
                    String desc = phrase.substring("event".length(), fromIndex).trim();
                    String from = phrase.substring(fromIndex + 5, toIndex).trim();
                    String to = phrase.substring(toIndex + 3).trim();
                    LocalDate fromDate = LocalDate.parse(from);
                    LocalDate toDate = LocalDate.parse(to);
                    if (desc.isEmpty()){
                        throw new JordanException("Event requires a description");
                    }
                    else if (from.isEmpty() || to.isEmpty()){
                        throw new JordanException("Event requires a from / to");
                    }
                    tasks.add(new Event(desc, fromDate, toDate));
                    System.out.println("I have added task: " + tasks.get(tasks.size()-1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list");
                }
                saveTasksToFile(tasks);
            }
            catch (JordanException e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Bye! See you again!\n");
    }
    private static void saveTasksToFile (ArrayList<Task> tasks){
        try {
            File directory = new File("./data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            FileWriter fw = new FileWriter(FILE_PATH);
            for (Task task : tasks){
                fw.write(task.saveToString() + "\n");
            }
            fw.close();
        }
        catch (IOException e){
            System.out.println("An error occurred from saving the tasks: " + e.getMessage());
        }
    }
    private static void loadTasksFromFile(ArrayList<Task> tasks){
        try{
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                System.out.println("Data file not found. Starting with an empty task list");
                return;
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                String[] parts = line.split(" \\| ");
                String status = parts[1];
                String desc = parts[2];
                if (line.startsWith("T")){
                    Task todoTask = new Todo(desc);
                    if(status.equals("1")){
                        todoTask.markAsDone();
                    }
                    tasks.add(todoTask);
                }
                else if (line.startsWith("D")){
                    String by = parts[3];
                    Task deadlineTask = new Deadline(desc,LocalDate.parse(by));
                    if(status.equals("1")){
                        deadlineTask.markAsDone();
                    }
                    tasks.add(deadlineTask);
                }
                else if (line.startsWith("E")){
                    String from = parts[3];
                    String to = parts[4];
                    Task eventTask = new Event(desc, LocalDate.parse(from) ,LocalDate.parse(to));
                    if(status.equals("1")){
                        eventTask.markAsDone();
                    }
                    tasks.add(eventTask);
                }
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
