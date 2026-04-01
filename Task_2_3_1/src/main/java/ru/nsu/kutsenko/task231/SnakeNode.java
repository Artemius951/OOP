package ru.nsu.kutsenko.task231;

/**
 * Узел связного списка, представляющий один сегмент змейки.
 */
public class SnakeNode {
    public Cell cell;
    public SnakeNode next;

    /**
     * Создает узел змейки без ссылки на следующий.
     *
     * @param cell клетка, занимаемая узлом
     */
    public SnakeNode(Cell cell) {
        this.cell = cell;
        this.next = null;
    }

    /**
     * Создает узел змейки с указанием следующего узла.
     *
     * @param cell клетка, занимаемая узлом
     * @param next следующий узел в списке
     */
    public SnakeNode(Cell cell, SnakeNode next) {
        this.cell = cell;
        this.next = next;
    }
}