package jordan.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    public void toStringTest(){
        assertEquals("[E][ ] anniversary \n (from: Dec 1 2019 to: Dec 10 2020)",
                new Event("anniversary", LocalDate.parse("2019-12-01"),LocalDate.parse("2020-12-10")
        ).toString());
    }
}
