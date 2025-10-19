package ru.nsu.kutsenko.task121;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Интерфейс для представления ориентированного графа.
 * Определяет основные операции для работы с графом.
 */
public interface Graph<Data> {
    /**
     * Добавляет вершину в граф.
     *
     * @param vertex вершина, которую будем добавлять
     * @param data данные, хранящиеся в вершине
     * @return true если вершина была добавлена, false если вершина уже существует
     */
    boolean addVertex(int vertex, Data data);

    /**
     * Удаляет вершину из графа.
     *
     * @param vertex вершина, которую будем удалять
     * @return true если вершина была удалена, false если вершина не существует
     */
    boolean removeVertex(int vertex);

    /**
     * Добавляет ориентированное ребро между вершинами.
     *
     * @param from начальная вершина ребра
     * @param to конечная вершина ребра
     * @return true если ребро было добавлено, false если ребро уже существует
     */
    boolean addEdge(int from, int to);

    /**
     * Удаляет ориентированное ребро между вершинами.
     *
     * @param from начальная вершина ребра
     * @param to конечная вершина ребра
     * @return true если ребро было удалено, false если ребро не существует
     */
    boolean removeEdge(int from, int to);

    /**
     * Проверяет наличие вершины в графе.
     *
     * @param vertex вершина для проверки
     * @return true если вершина существует, false в противном случае
     */
    boolean hasVertex(int vertex);

    /**
     * Проверяет наличие ребра между вершинами.
     *
     * @param from начальная вершина ребра
     * @param to конечная вершина ребра
     * @return true если ребро существует, false в противном случае
     */
    boolean hasEdge(int from, int to);

    /**
     * Читает граф из файла.
     * Формат файла: первая строка игнорируется, последующие строки содержат пары чисел,
     * представляющие ориентированные ребра.
     *
     * @param filename имя файла для чтения
     * @param dataParser парсер для преобразования строки в данные вершины
     * @throws IOException если произошла ошибка ввода-вывода
     */
    void readFromFile(String filename, DataParser<Data> dataParser) throws IOException;

    /**
     * Возвращает строковое представление графа.
     *
     * @return строковое представление графа
     */
    String toString();

    /**
     * Сравнивает данный граф с другим графом на равенство.
     *
     * @param obj объект для сравнения
     * @return true если графы равны, false в противном случае
     */
    boolean equals(Object obj);

    /**
     * Возвращает список соседей вершины (вершины, в которые ведут ребра из данной вершины).
     *
     * @param vertex вершина, для которой нужно получить соседей
     * @return список соседних вершин
     */
    List<Integer> getNeighbors(int vertex);

    /**
     * Возвращает количество вершин в графе.
     *
     * @return количество вершин
     */
    int getVertexCount();

    /**
     * Возвращает количество ребер в графе.
     *
     * @return количество ребер
     */
    int getEdgeCount();

    /**
     * Возвращает множество всех вершин графа.
     *
     * @return множество вершин
     */
    Set<Integer> getVertices();

    /**
     * Возвращает данные, хранящиеся в вершине.
     *
     * @param vertex вершина
     * @return данные вершины или null, если вершина не существует
     */
    Data getVertexData(int vertex);
}
