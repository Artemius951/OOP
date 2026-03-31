package ru.nsu.kutsenko.task231;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;

public class ConfigLoader {

    /**
     * Загружает конфигурацию игры из JSON-файла.
     *
     * @param path путь к JSON-файлу конфигурации
     * @return объект GameConfig
     * @throws RuntimeException если файл не найден или произошла ошибка чтения
     */
    public static GameConfig loadConfig(String path) {
        try (Reader reader = new FileReader(path)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, GameConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Troubles with config reading: " + path, e);
        }
    }

    /**
     * Возвращает конфигурацию по умолчанию, если файл не найден.
     */
    public static GameConfig createDefault() {
        return new GameConfig(20, 20, 3, 50, 150);
    }
}