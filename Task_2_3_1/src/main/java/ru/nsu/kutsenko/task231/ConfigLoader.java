package ru.nsu.kutsenko.task231;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Предоставляет методы для загрузки конфигурации игры из JSON-файла.
 * Поддерживает загрузку пользовательской конфигурации и создание конфигурации по умолчанию.
 */
public class ConfigLoader {

    /**
     * Загружает конфигурацию игры из JSON-файла.
     *
     * @param path путь к JSON-файлу конфигурации
     * @return объект GameConfig, содержащий параметры игры
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
     * Возвращает конфигурацию по умолчанию.
     * Используется в случае, если файл конфигурации не найден или произошла ошибка загрузки.
     *
     * @return объект GameConfig с параметрами по умолчанию:
     *         ширина поля = 20, высота поля = 20, начальная длина змейки = 3,
     *         задержка между кадрами = 150 мс, количество очков для победы = 30
     */
    public static GameConfig createDefault() {
        return new GameConfig(20, 20, 3, 30, 150);
    }
}