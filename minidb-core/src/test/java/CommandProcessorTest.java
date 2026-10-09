import engine.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandProcessorTest {
    private Database database;

    @BeforeEach
    public void setUp() {
        database = new Database();
    }

    @Test
    void shouldStoreAndRetrieveValue() {
        database.set("a", "b");
        assertEquals("b", database.get("a"));
    }

    @Test
    void shouldOverwriteExistingValue() {
        database.set("a", "b");
        database.set("a", "c");
        assertEquals("c", database.get("a"));
    }

    @Test
    void shouldReturnNilAfterDeletingKey() {
        database.set("a", "b");
        database.delete("a");
        assertEquals("(nil)", database.get("a"));
    }

    @Test
    void shouldCheckKeyExistence() {
        database.set("a", "b");
        assertTrue(database.exists("a"));
    }

    @Test
    void shouldReturnNumberOfStoredKeys() {
        database.set("a", "b");
        database.set("b", "c");
        assertEquals("2", database.count());
    }

    @Test
    void shouldReturnZeroAfterClearingDatabase() {
        database.clear();
        assertEquals("0", database.count());
    }
}
