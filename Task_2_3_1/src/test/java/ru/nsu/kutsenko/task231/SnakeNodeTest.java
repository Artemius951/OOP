package ru.nsu.kutsenko.task231;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SnakeNodeTest {

    @Test
    public void testSnakeNodeCreation() {
        Cell cell = new Cell(5, 10);
        SnakeNode node = new SnakeNode(cell);

        assertEquals(cell, node.cell);
        assertNull(node.next);
    }

    @Test
    public void testSnakeNodeWithNext() {
        Cell cell1 = new Cell(5, 10);
        Cell cell2 = new Cell(5, 11);
        SnakeNode node2 = new SnakeNode(cell2);
        SnakeNode node1 = new SnakeNode(cell1, node2);

        assertEquals(cell1, node1.cell);
        assertEquals(node2, node1.next);
        assertEquals(cell2, node1.next.cell);
    }

    @Test
    public void testSnakeNodeChain() {
        Cell cell1 = new Cell(0, 0);
        Cell cell2 = new Cell(0, 1);
        Cell cell3 = new Cell(0, 2);

        SnakeNode node3 = new SnakeNode(cell3);
        SnakeNode node2 = new SnakeNode(cell2, node3);
        SnakeNode node1 = new SnakeNode(cell1, node2);

        assertEquals(cell1, node1.cell);
        assertEquals(cell2, node1.next.cell);
        assertEquals(cell3, node1.next.next.cell);
        assertNull(node1.next.next.next);
    }
}