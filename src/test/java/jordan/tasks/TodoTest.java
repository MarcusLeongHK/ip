package jordan.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void toStringTest(){
        assertEquals("[T][ ] watch tv \n",new Todo("watch tv").toString());
    }
}
