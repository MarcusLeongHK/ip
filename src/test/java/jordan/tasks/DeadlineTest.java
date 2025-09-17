package jordan.tasks;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class DeadlineTest {
    @Test
    void saveToString_correctFormat() {
        Deadline d = new Deadline("Submit", LocalDate.of(2023, 12, 1));
        assertEquals("D | 0 | Submit | 2023-12-01", d.saveToString());
    }
}
