package ru.nsu.kutsenko.task231;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Food.
 */
public class FoodTest {

    @Test
    public void testFoodCreation() {
        Food food = new Food();
        assertEquals(0, food.getCount());
    }

    @Test
    public void testFoodAdd() {
        Food food = new Food();
        Cell cell = new Cell(5, 5);

        food.add(cell);

        assertEquals(1, food.getCount());
        assertTrue(food.contains(cell));
    }

    @Test
    public void testFoodRemove() {
        Food food = new Food();
        Cell cell = new Cell(5, 5);
        food.add(cell);

        food.remove(cell);

        assertEquals(0, food.getCount());
        assertFalse(food.contains(cell));
    }

    @Test
    public void testFoodContains() {
        Food food = new Food();
        Cell cell1 = new Cell(5, 5);
        Cell cell2 = new Cell(6, 6);

        food.add(cell1);

        assertTrue(food.contains(cell1));
        assertFalse(food.contains(cell2));
    }

    @Test
    public void testFoodMultiple() {
        Food food = new Food();
        Cell cell1 = new Cell(5, 5);
        Cell cell2 = new Cell(6, 6);
        Cell cell3 = new Cell(7, 7);

        food.add(cell1);
        food.add(cell2);
        food.add(cell3);

        assertEquals(3, food.getCount());
        assertTrue(food.contains(cell1));
        assertTrue(food.contains(cell2));
        assertTrue(food.contains(cell3));
    }

    @Test
    public void testFoodGetAll() {
        Food food = new Food();
        Cell cell1 = new Cell(5, 5);
        Cell cell2 = new Cell(6, 6);

        food.add(cell1);
        food.add(cell2);

        var all = food.getAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(cell1));
        assertTrue(all.contains(cell2));
    }

    @Test
    public void testFoodClear() {
        Food food = new Food();
        food.add(new Cell(5, 5));
        food.add(new Cell(6, 6));

        food.clear();

        assertEquals(0, food.getCount());
    }
}