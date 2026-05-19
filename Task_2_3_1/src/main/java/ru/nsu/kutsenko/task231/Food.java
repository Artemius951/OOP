package ru.nsu.kutsenko.task231;

import java.util.HashSet;
import java.util.Set;

/**
 * Представляет еду в игре.
 */
public class Food {
    private Set<Cell> foodCells;

    /**
     * Создает пустой набор клеток с едой.
     */
    public Food() {
        this.foodCells = new HashSet<>();
    }

    /**
     * Добавляет клетку с едой.
     *
     * @param cell клетка, на которой появляется еда
     */
    public synchronized void add(Cell cell) {
        foodCells.add(cell);
    }

    /**
     * Удаляет клетку с едой.
     *
     * @param cell клетка, с которой удаляется еда
     */
    public synchronized void remove(Cell cell) {
        foodCells.remove(cell);
    }

    /**
     * Проверяет, находится ли еда на заданной клетке.
     *
     * @param cell проверяемая клетка
     * @return true, если на клетке есть еда, иначе false
     */
    public synchronized boolean contains(Cell cell) {
        return foodCells.contains(cell);
    }

    /**
     * Возвращает копию всех клеток с едой.
     *
     * @return новый набор, содержащий все клетки с едой
     */
    public synchronized Set<Cell> getAll() {
        return new HashSet<>(foodCells);
    }

    /**
     * Возвращает количество клеток с едой.
     *
     * @return количество объектов еды на поле
     */
    public synchronized int getCount() {
        return foodCells.size();
    }

    /**
     * Удаляет всю еду с игрового поля.
     */
    public synchronized void clear() {
        foodCells.clear();
    }
}