package jordan.utilities;

import jordan.JordanException;
import jordan.tasks.TaskList;
import jordan.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private TaskList tasks;
    private Ui ui;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        ui = new Ui();
    }

    @Test
    void parse_todoCommand_addsTodo() throws JordanException {
        String response = Parser.parse(ui, tasks, "todo read book");
        String res =
                """
                        I have added task: [T][ ] read book\s
                        
                        Now you have 1 tasks in the list""";
        assertEquals(res,response);
    }

    @Test
    void parse_deadlineCommand_addsDeadline() throws JordanException {
        String response = Parser.parse(ui, tasks, "deadline return book /by 2023-12-01");
        String res = """
                I have added task: [D][ ] return book\s
                 (by: Dec 1 2023)\n
                Now you have 1 tasks in the list""";
        assertEquals(res,response);
    }

    @Test
    void parse_eventCommand_addsEvent() throws JordanException {
        String response = Parser.parse(ui, tasks, "event project meeting /from 2023-12-01 /to 2023-12-02");
        String res = """
                I have added task: [E][ ] project meeting\s
                 (from: Dec 1 2023 to: Dec 2 2023)\n
                Now you have 1 tasks in the list""";
        assertEquals(res,response);
    }

    @Test
    void parse_markCommand_marksTask() throws JordanException {
        Parser.parse(ui, tasks, "todo read book");
        String response = Parser.parse(ui, tasks, "mark 1");
        String res = "Nice! I've marked this task as complete! \n" + "[T][X] read book \n";
        assertEquals(res,response);
    }

    @Test
    void parse_deleteCommand_deletesTask() throws JordanException {
        Parser.parse(ui, tasks, "todo read book");
        String response = Parser.parse(ui, tasks, "delete 1");
        String res = """
            I have removed this task.\s
            [T][ ] read book\s
            
            Now you have 0 tasks in the list""";
        assertEquals(res,response);
    }

    @Test
    void parse_invalidCommand_returnsPrompt() throws JordanException {
        String response = Parser.parse(ui, tasks, "foobar");
        assertEquals("Please enter a prompt", response);
    }

    @Test
    void parse_todoWithoutDescription_throwsException() {
        JordanException ex = assertThrows(JordanException.class, () ->
                Parser.parse(ui, tasks, "todo "));
        assertTrue(ex.getMessage().contains("description"));
    }
}
