package ru.nsu.kutsenko.task221;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

class PizzeriaTest {

    private PizzaConfig callLoadConfig(String path) {
        try {
            Method loadConfig = Pizzeria.class.getDeclaredMethod("loadConfig", String.class);
            loadConfig.setAccessible(true);
            Object result = loadConfig.invoke(null, path);
            return (PizzaConfig) result;
        } catch (Exception e) {
            if (e.getCause() instanceof RuntimeException) {
                throw (RuntimeException) e.getCause();
            }
            throw new RuntimeException(e);
        }
    }

    @Test
    void mainRunsWithoutExceptionsWithConfigJson() {
        assertDoesNotThrow(() -> Pizzeria.main(new String[0]));
    }

    @Test
    void mainRunsWithNullArgs() {
        assertDoesNotThrow(() -> Pizzeria.main(null));
    }

    @Test
    void mainRunsWithRandomArgs() {
        String[] args = {"config.json", "arg2"};
        assertDoesNotThrow(() -> Pizzeria.main(args));
    }

    @Test
    void loadConfigThrowsForDefinitelyMissingFile() {
        assertThrows(RuntimeException.class, () -> callLoadConfig("missing_cg_file.json"));
    }

    @Test
    void loadConfigThrowsForEmptyPath() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> callLoadConfig(""));
        assertNotNull(ex.getMessage());
    }

    @Test
    void loadConfigThrowsForDirectoryName() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> callLoadConfig("."));
        assertTrue(ex.getMessage().contains("."));
    }

    @Test
    void loadConfigExceptionMessageContainsPath() {
        String path = "no_such_file_12345.json";
        RuntimeException ex = assertThrows(RuntimeException.class, () -> callLoadConfig(path));
        assertTrue(ex.getMessage().contains(path));
    }

    @Test
    void loadConfigThrowsForPathWithSpaces() {
        String path = "some path that does not exist.json";
        RuntimeException ex = assertThrows(RuntimeException.class, () -> callLoadConfig(path));
        assertTrue(ex.getMessage().contains("Troubles with config reading"));
    }

    @Test
    void loadConfigThrowsForAbsoluteNonExistingPath() {
        String path = "/this/path/definitely/does/not/exist/config.json";
        RuntimeException ex = assertThrows(RuntimeException.class, () -> callLoadConfig(path));
        assertTrue(ex.getMessage().contains("config.json"));
    }

    @Test
    void loadConfigThrowsForRelativeNonExistingPathInSubdir() {
        String path = "configs/non_existing.json";
        RuntimeException ex = assertThrows(RuntimeException.class, () -> callLoadConfig(path));
        assertTrue(ex.getMessage().contains("non_existing.json"));
    }

    @Test
    void loadConfigThrowsForWeirdExtension() {
        String path = "config.txt";
        RuntimeException ex = assertThrows(RuntimeException.class, () -> callLoadConfig(path));
        assertTrue(ex.getMessage().contains("config.txt"));
    }

    @Test
    void loadConfigThrowsForJsonLikeNameWithoutFile() {
        String path = "another_config.json";
        RuntimeException ex = assertThrows(RuntimeException.class, () -> callLoadConfig(path));
        assertTrue(ex.getMessage().contains("another_config.json"));
    }
}