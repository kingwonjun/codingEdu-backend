import engine.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DatabaseTest {

    private Database database;

    @BeforeEach
    public void setUp() {
        database = new Database();
    }

    @Test
    public void shouldStoreAndRetrieveValue() {
        database.set("name", "alice");
        assertEquals("alice", database.get("name"));
    }

    @Test
    public void shouldOverwriteExistingValue() {
        database.set("name", "alice");
        database.set("name", "bob");
        assertEquals("bob", database.get("name"));
    }

    @Test
    public void shouldReturnNullForMissingKey() {
        assertEquals("(nil)",database.get("unknown"));
    }

    @Test
    public void shouldDeleteExistingKey() {
        database.set("name", "alice");
        assertTrue(database.delete("name"));
        assertEquals("(nil)", database.get("name"));
    }

    @Test
    public void shouldReturnFalseWhenDeletingMissingKey() {
        assertFalse(database.delete("unknown"));
    }

    @Test
    public void shouldStoreMultipleKeys() {
        for (int i = 0; i < 100; i ++) {
            database.set(String.valueOf(i), String.valueOf(i));
        }
        for (int i = 0; i < 100; i ++) {
            assertEquals(String.valueOf(i), database.get(String.valueOf(i)));
        }
    }
}
