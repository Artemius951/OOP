package ru.nsu.kutsenko.task231;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Cell.
 */
public class CellTest {

    @Test
    public void testCellCreation() {
        Cell cell = new Cell(5, 10);
        assertEquals(5, cell.cellx);
        assertEquals(10, cell.celly);
    }

    @Test
    public void testCellAdd() {
        Cell cell = new Cell(5, 10);
        Cell result = cell.add(3, 2);

        assertEquals(8, result.cellx);
        assertEquals(12, result.celly);
        assertEquals(5, cell.cellx);
        assertEquals(10, cell.celly);
    }

    @Test
    public void testCellAddNegative() {
        Cell cell = new Cell(5, 10);
        Cell result = cell.add(-2, -3);

        assertEquals(3, result.cellx);
        assertEquals(7, result.celly);
    }

    @Test
    public void testCellEquals() {
        Cell cell1 = new Cell(5, 10);
        Cell cell2 = new Cell(5, 10);
        Cell cell3 = new Cell(5, 11);

        assertEquals(cell1, cell2);
        assertNotEquals(cell1, cell3);
    }

    @Test
    public void testCellHashCode() {
        Cell cell1 = new Cell(5, 10);
        Cell cell2 = new Cell(5, 10);

        assertEquals(cell1.hashCode(), cell2.hashCode());
    }

    @Test
    public void testCellToString() {
        Cell cell = new Cell(5, 10);
        assertEquals("5,10", cell.toString());
    }
}