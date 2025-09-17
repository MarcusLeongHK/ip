package jordan.utilities;

import jordan.JordanException;
import jordan.tasks.TaskList;
import jordan.tasks.Todo;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {
    private static final String TEST_FILE = "./data/test_tasks.txt";
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage(TEST_FILE);
        // Clean up before each test
        File file = new File(TEST_FILE);
        if (file.exists()) file.delete();
    }

    @Test
    void save_and_load_tasks_success() throws JordanException {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("JUnit test"));
        storage.save(tasks);

        ArrayList<jordan.tasks.Task> loaded = storage.load();
        assertEquals(1, loaded.size());
        assertEquals("JUnit test ", loaded.get(0).getDescription());
    }

    @Test
    void load_nonexistentFile_returnsEmptyList() throws JordanException {
        ArrayList<jordan.tasks.Task> loaded = storage.load();
        assertTrue(loaded.isEmpty());
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE);
        if (file.exists()) file.delete();
    }
}
