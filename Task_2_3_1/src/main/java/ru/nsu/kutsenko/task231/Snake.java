package ru.nsu.kutsenko.task231;

public class Snake {
    private SnakeNode head;
    private SnakeNode tail;
    private int length;

    public Snake(Cell startCell) {
        this.head = new SnakeNode(startCell);
        this.tail = this.head;
        this.length = 1;
    }

    public synchronized Cell getHead() {
        return head.cell;
    }

    public synchronized Cell getTail() {
        return tail.cell;
    }

    public synchronized int getLength() {
        return length;
    }

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

    public synchronized void move(Cell newHead) {
        addHead(newHead);
        removeTail();
    }

    public synchronized void grow(Cell newHead) {
        addHead(newHead);
    }

    private void addHead(Cell newHead) {
        SnakeNode newNode = new SnakeNode(newHead, head);
        head = newNode;
        length++;
    }

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

    public synchronized java.util.List<Cell> getAllCells() {
        java.util.List<Cell> cells = new java.util.ArrayList<>();
        SnakeNode current = head;
        while (current != null) {
            cells.add(current.cell);
            current = current.next;
        }
        return cells;
    }

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