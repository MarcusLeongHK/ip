package jordan.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoTest {
    @Test
    public void toStringTest(){
        assertEquals("[T][ ] watch tv \n",new Todo("watch tv").toString());
    }

    @Test
    void markAsDone_setsDone() {
        Todo todo = new Todo("Test");
        todo.markAsDone();
        assertEquals("X",todo.getStatusIcon());
    }

    @Test
    void saveToString_correctFormat() {
        Todo todo = new Todo("Test");
        assertEquals("T | 0 | Test ", todo.saveToString());
        todo.markAsDone();
        assertEquals("T | 1 | Test ", todo.saveToString());
    }
}
