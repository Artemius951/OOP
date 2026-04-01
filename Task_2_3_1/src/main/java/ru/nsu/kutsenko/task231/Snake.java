package ru.nsu.kutsenko.task231;

/**
 * Представляет змейку как двусвязный список клеток.
 */
public class Snake {
    private SnakeNode head;
    private SnakeNode tail;
    private int length;

    /**
     * Создает змейку с начальной клеткой.
     *
     * @param startCell начальная клетка головы змейки
     */
    public Snake(Cell startCell) {
        this.head = new SnakeNode(startCell);
        this.tail = this.head;
        this.length = 1;
    }

    /**
     * Возвращает клетку головы змейки.
     *
     * @return клетка головы
     */
    public synchronized Cell getHead() {
        return head.cell;
    }

    /**
     * Возвращает клетку хвоста змейки.
     *
     * @return клетка хвоста
     */
    public synchronized Cell getTail() {
        return tail.cell;
    }

    /**
     * Возвращает длину змейки.
     *
     * @return количество сегментов змейки
     */
    public synchronized int getLength() {
        return length;
    }

    /**
     * Проверяет, находится ли заданная клетка в теле змейки.
     *
     * @param cell проверяемая клетка
     * @return true, если клетка принадлежит змейке, иначе false
     */
    public synchronized boolean contains(Cell cell) {
        SnakeNode current = head;
        while (current != null) {
            if (current.cell.equals(cell)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Перемещает змейку на новую клетку, добавляя голову и удаляя хвост.
     *
     * @param newHead новая клетка головы
     */
    public synchronized void move(Cell newHead) {
        addHead(newHead);
        removeTail();
    }

    /**
     * Увеличивает змейку, добавляя новую голову без удаления хвоста.
     *
     * @param newHead новая клетка головы
     */
    public synchronized void grow(Cell newHead) {
        addHead(newHead);
    }

    /**
     * Добавляет новую голову к змейке.
     *
     * @param newHead новая клетка головы
     */
    private void addHead(Cell newHead) {
        SnakeNode newNode = new SnakeNode(newHead, head);
        head = newNode;
        length++;
    }

    /**
     * Удаляет хвост змейки.
     */
    private void removeTail() {
        if (length <= 1) {
            return;
        }

        SnakeNode current = head;
        while (current.next != tail) {
            current = current.next;
        }

        current.next = null;
        tail = current;
        length--;
    }

    /**
     * Возвращает список всех клеток змейки от головы к хвосту.
     *
     * @return список клеток змейки
     */
    public synchronized java.util.List<Cell> getAllCells() {
        java.util.List<Cell> cells = new java.util.ArrayList<>();
        SnakeNode current = head;
        while (current != null) {
            cells.add(current.cell);
            current = current.next;
        }
        return cells;
    }

    /**
     * Возвращает строковое представление змейки.
     *
     * @return строка с последовательностью клеток
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Snake[");
        SnakeNode current = head;
        while (current != null) {
            sb.append(current.cell);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}