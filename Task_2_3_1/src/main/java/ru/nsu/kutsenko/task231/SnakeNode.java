package ru.nsu.kutsenko.task231;


public class SnakeNode {
    public Cell cell;
    public SnakeNode next;

    public SnakeNode(Cell cell) {
        this.cell = cell;
        this.next = null;
    }

    public SnakeNode(Cell cell, SnakeNode next) {
        this.cell = cell;
        this.next = next;
    }
}