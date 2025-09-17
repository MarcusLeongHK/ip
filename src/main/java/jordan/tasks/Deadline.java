package jordan.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Represents a Task which has a description, and a due date. The due date is consumed
 * as a LocalDate object but displayed in MMM d yyyy format
 */
public class Deadline extends Task {

    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the formatted string of a deadline task.
     *
     * @return Deadline task string.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + this.by.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")\n";
    }

    /**
     * Returns the formatted string of a deadline task.
     * The due date is saved in LocalDate string format instead of MMM d yyyy
     *
     * @return Deadline task string.
     */
    public String saveToString() {
        return String.format("D | %d | %s | %s",
                this.isDone ? 1 : 0, this.description, this.by);
    }
}
