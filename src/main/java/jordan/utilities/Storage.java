package jordan.utilities;

import jordan.JordanException;
import jordan.tasks.Task;
import jordan.tasks.TaskList;
import jordan.tasks.Todo;
import jordan.tasks.Deadline;
import jordan.tasks.Event;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    /**
     * Performs the file functions of saving tasks into a .txt file.
     * Also, it loads tasks from the .txt file to allow for data persistence
     */

    public Storage (String filepath) {
        this.filePath = filepath;
    }
    /**
     * Saves the tasks created in a .txt file
     * If directory does not exist, it creates the directory
     *
     * @param tasks tasks which have been created.
     * @throws JordanException If IOException occurs.
     */
    public void save(TaskList tasks) throws JordanException {
        try {
            File directory = new File("./data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            FileWriter fw = new FileWriter(this.filePath);
            for (Task task : tasks){
                fw.write(task.saveToString() + "\n");
            }
            fw.close();
        }
        catch (IOException e){
            throw new JordanException("An error occurred from saving the tasks: " + e.getMessage());
        }
    }

    public ArrayList<Task> load() throws JordanException{
        ArrayList<Task> tasks = new ArrayList<>();
        try{
            File file = new File(this.filePath);
            if (!file.exists()) {
                System.out.println("Data file not found. Starting with an empty task list");
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
                    Task deadlineTask = new Deadline(desc, LocalDate.parse(by));
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
            throw new JordanException("Invalid task label: " + e.getMessage());
        }
        return tasks;
    }
}
