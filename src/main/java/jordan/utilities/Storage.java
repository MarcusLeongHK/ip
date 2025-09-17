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

/**
 * Handles saving and loading of tasks to and from a file for data persistence.
 */
public class Storage {
    /** Path to the data file. */
    private final String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath Path to the data file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the tasks in the provided TaskList to the data file.
     * Creates the data directory if it does not exist.
     *
     * @param tasks TaskList containing tasks to save.
     * @throws JordanException If an I/O error occurs during saving.
     */
    public void save(TaskList tasks) throws JordanException {
        assert tasks != null : "TaskList cannot be null";
        ensureDataDirectoryExists();
        try (FileWriter fw = new FileWriter(this.filePath)) {
            for (Task task : tasks) {
                fw.write(task.saveToString() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new JordanException("An error occurred while saving the tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the data file into an ArrayList.
     * If the file does not exist, returns an empty list.
     *
     * @return ArrayList of loaded tasks.
     * @throws JordanException If an I/O error or data format error occurs.
     */
    public ArrayList<Task> load() throws JordanException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(this.filePath);
        if (!file.exists()) {
            System.out.println("Data file not found. Starting with an empty task list.");
            return tasks;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parseTaskFromLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException | IndexOutOfBoundsException | IllegalArgumentException e) {
            throw new JordanException("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Ensures that the data directory exists, creating it if necessary.
     */
    private void ensureDataDirectoryExists() {
        File directory = new File("./data");
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Parses a task from a line in the data file.
     *
     * @param line The line representing a task.
     * @return The corresponding Task object, or null if the line is invalid.
     * @throws IllegalArgumentException If the line format is invalid.
     */
    private Task parseTaskFromLine(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task format: " + line);
        }
        String type = parts[0];
        String status = parts[1];
        String desc = parts[2];
        Task task;
        switch (type) {
        case "T":
            task = new Todo(desc);
            break;
        case "D":
            if (parts.length < 4) throw new IllegalArgumentException("Deadline missing date: " + line);
            task = new Deadline(desc, LocalDate.parse(parts[3]));
            break;
        case "E":
            if (parts.length < 5) throw new IllegalArgumentException("Event missing dates: " + line);
            task = new Event(desc, LocalDate.parse(parts[3]), LocalDate.parse(parts[4]));
            break;
        default:
            throw new IllegalArgumentException("Unknown task type: " + type);
        }
        if ("1".equals(status)) {
            task.markAsDone();
        }
        return task;
    }
}
