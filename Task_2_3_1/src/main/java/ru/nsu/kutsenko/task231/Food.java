package ru.nsu.kutsenko.task231;

import java.util.HashSet;
import java.util.Set;

public class Food {
    private Set<Cell> foodCells;

    public Food() {
        this.foodCells = new HashSet<>();
    }

    public synchronized void add(Cell cell) {
        foodCells.add(cell);
    }

    public synchronized void remove(Cell cell) {
        foodCells.remove(cell);
    }

    public synchronized boolean contains(Cell cell) {
        return foodCells.contains(cell);
    }

    public synchronized Set<Cell> getAll() {
        return new HashSet<>(foodCells);
    }

    public synchronized int getCount() {
        return foodCells.size();
    }

    public synchronized void clear() {
        foodCells.clear();
    }
}