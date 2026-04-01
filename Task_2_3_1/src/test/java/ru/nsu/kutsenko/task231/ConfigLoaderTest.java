package ru.nsu.kutsenko.task231;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigLoaderTest {

    @Test
    public void testCreateDefault() {
        GameConfig config = ConfigLoader.createDefault();

        assertEquals(20, config.getFieldWidth());
        assertEquals(20, config.getFieldHeight());
        assertEquals(3, config.getFoodCount());
        assertEquals(30, config.getWinLength());
        assertEquals(150, config.getTickMs());
    }

    @Test
    public void testLoadConfigValid(@TempDir File tempDir) throws IOException {
        File configFile = new File(tempDir, "config.json");
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write("{\"fieldWidth\":30,\"fieldHeight\":25,\"foodCount\":5," +
                "\"winLength\":100,\"tickMs\":200}");
        }

        GameConfig config = ConfigLoader.loadConfig(configFile.getAbsolutePath());

        assertEquals(30, config.getFieldWidth());
        assertEquals(25, config.getFieldHeight());
        assertEquals(5, config.getFoodCount());
        assertEquals(100, config.getWinLength());
        assertEquals(200, config.getTickMs());
    }

    @Test
    public void testLoadConfigFileNotFound() {
        assertThrows(RuntimeException.class, () -> {
            ConfigLoader.loadConfig("/nonexistent/path/config.json");
        });
    }

    @Test
    public void testLoadConfigInvalidJson(@TempDir File tempDir) throws IOException {
        File configFile = new File(tempDir, "config.json");
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write("{invalid json}");
        }

        assertThrows(RuntimeException.class, () -> {
            ConfigLoader.loadConfig(configFile.getAbsolutePath());
        });
    }
}