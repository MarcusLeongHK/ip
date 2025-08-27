import java.time.LocalDate;

public class Deadline extends Task {

    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    public String saveToString(){
        return String.format("D | %d | %s | %s",
                this.isDone ? 1 : 0, this.description, this.by);
    }
}
