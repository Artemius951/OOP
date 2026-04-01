package ru.nsu.kutsenko.task231;

/**
 * Представляет узел в связной структуре данных змейки.
 */
public class SnakeNode {
    /**
     * Ячейка, занимаемая этим узлом.
     */
    public Cell cell;

    /**
     * Следующий узел в змейке, или null если это хвост.
     */
    public SnakeNode next;

    /**
     * Создает узел змейки с заданной ячейкой.
     *
     * @param cell ячейка для этого узла
     */
    public SnakeNode(Cell cell) {
        this.cell = cell;
        this.next = null;
    }

    /**
     * Создает узел змейки с заданной ячейкой и следующим узлом.
     *
     * @param cell ячейка для этого узла
     * @param next следующий узел в цепи
     */
    public SnakeNode(Cell cell, SnakeNode next) {
        this.cell = cell;
        this.next = next;
    }
}