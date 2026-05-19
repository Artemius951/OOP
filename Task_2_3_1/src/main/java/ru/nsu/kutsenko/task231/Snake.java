package ru.nsu.kutsenko.task231;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Представляет змейку в игре Snake.
 * Использует LinkedList для эффективного добавления головы и удаления хвоста.
 */
public class Snake {
    private LinkedList<Cell> body;

    /**
     * Создает змейку с одним сегментом в указанной клетке.
     *
     * @param startCell начальная клетка для головы
     */
    public Snake(Cell startCell) {
        this.body = new LinkedList<>();
        this.body.addFirst(startCell);
    }

    /**
     * Возвращает длину змейки.
     *
     * @return количество сегментов
     */
    public int getLength() {
        return body.size();
    }

    /**
     * Возвращает голову змейки.
     *
     * @return клетка головы (первый элемент списка)
     */
    public Cell getHead() {
        return body.getFirst();
    }

    /**
     * Возвращает хвост змейки.
     *
     * @return клетка хвоста (последний элемент списка)
     */
    public Cell getTail() {
        return body.getLast();
    }

    /**
     * Проверяет, содержит ли змейка указанную клетку.
     *
     * @param cell клетка для проверки
     * @return true, если клетка принадлежит змейке
     */
    public boolean contains(Cell cell) {
        return body.contains(cell);
    }

    /**
     * Перемещает змейку на новую клетку, добавляя голову и удаляя хвост.
     * Голова всегда остаётся неразрывно связана с телом.
     *
     * @param newHead новая клетка головы
     */
    public synchronized void move(Cell newHead) {
        body.addFirst(newHead);
        body.removeLast();
    }

    /**
     * Растит змейку, добавляя новый сегмент в голову.
     * Хвост не удаляется.
     *
     * @param newHead новая клетка головы
     */
    public synchronized void grow(Cell newHead) {
        body.addFirst(newHead);
    }

    /**
     * Возвращает все клетки змейки в порядке от головы к хвосту.
     *
     * @return список всех клеток
     */
    public List<Cell> getAllCells() {
        return new ArrayList<>(body);
    }

    /**
     * Возвращает строковое представление змейки.
     *
     * @return строка с информацией о змейке
     */
    @Override
    public String toString() {
        return "Snake{"
            + "body=" + body
            + ", length=" + body.size()
            + '}';
    }
}