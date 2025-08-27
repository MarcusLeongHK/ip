import java.time.LocalDate;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    public String saveToString(){
        return String.format("E | %d | %s | %s | %s",
                this.isDone ? 1 : 0, this.description, this.from, this.to);
    }
}