package jordan.tasks;

public class Todo extends Task {
    public Todo(String description){
        super(description);
    }
    public String toString(){
        return "[T]"+super.toString();
    }

    public String saveToString(){
        return String.format("T | %d | %s ",
                        this.isDone ? 1 : 0, this.description);
    }
}

