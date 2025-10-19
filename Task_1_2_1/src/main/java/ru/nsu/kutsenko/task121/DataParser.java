package ru.nsu.kutsenko.task121;

/**
 * Интерфейс для парсера данных вершины из строки.
 */
public interface DataParser<Data> {
    /**
     * Преобразует строку в данные вершины.
     *
     * @param vertexStr строковое представление вершины
     * @return данные вершины
     */
    Data parse(String vertexStr);
}