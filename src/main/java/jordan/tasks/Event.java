package jordan.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Represents a Task which has a description, a start date and a due date.
 * The dates are consumed as a LocalDate object but displayed in MMM d yyyy format
 * This is an extension to the Deadline task but not inherited
 */
public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }
    /**
     * Returns the formatted string of a Event task.
     *
     * @return Event task string.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + this.from.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: " + this.to.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
    /**
     * Returns the formatted string of a Event task.
     * The due date is saved in LocalDate string format instead of MMM d yyyy
     *
     * @return Event task string.
     */
    public String saveToString() {
        return String.format("E | %d | %s | %s | %s",
                this.isDone ? 1 : 0, this.description, this.from, this.to);
    }
}