package jordan.tasks;

/**
 * Represents a Todo task.
 * Inherits from the Task class and provides specific implementations
 * for Todo-type tasks.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo task with the specified description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description){
        super(description);
    }

    /**
     * Returns the string representation of the Todo task.
     *
     * @return A string in the format "[T]..." where ... is the parent Task's string.
     */
    @Override
    public String toString(){
        return "[T]"+super.toString();
    }

    /**
     * Returns a string suitable for saving the Todo task to a file.
     *
     * @return A string in the format "T | 0/1 | description ".
     */
    public String saveToString(){
        return String.format("T | %d | %s ",
                this.isDone ? 1 : 0, this.description);
    }
}

