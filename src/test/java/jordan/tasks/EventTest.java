package jordan.tasks;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {
    @Test
    void saveToString_correctFormat() {
        Event e = new Event("Meeting", LocalDate.of(2023, 12, 1), LocalDate.of(2023, 12, 2));
        assertEquals("E | 0 | Meeting | 2023-12-01 | 2023-12-02", e.saveToString());
    }
}
