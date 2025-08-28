package jordan.tasks;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone(){
        this.isDone = true;
    }

    public String toString(){
        return String.format("[%s] %s \n", this.getStatusIcon(),this.description);
    }

    public String saveToString(){
        return this.toString();
    }
}
