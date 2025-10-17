package ru.nsu.kutsenko.task121;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки реализации графа на основе матрицы инцидентности.
 */
public class IncidenceMatrixTest {
    private IncidenceMatrix graph;

    @BeforeEach
    void setUp() {
        graph = new IncidenceMatrix();
    }

    @Test
    void testRemoveVertexWithEdges() {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        assertTrue(graph.removeVertex(2));
        assertFalse(graph.hasVertex(2));
        assertFalse(graph.hasEdge(1, 2));
        assertFalse(graph.hasEdge(2, 3));
        assertEquals(2, graph.getVertexCount());
        assertEquals(0, graph.getEdgeCount());

        // Проверяем, что можно добавлять новые рёбра после удаления
        assertTrue(graph.addEdge(1, 3));
        assertTrue(graph.hasEdge(1, 3));
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testAddVertex() {
        assertTrue(graph.addVertex(1));
        assertTrue(graph.addVertex(2));
        assertFalse(graph.addVertex(1));
        assertEquals(2, graph.getVertexCount());
    }

    @Test
    void testAddEdge() {
        assertTrue(graph.addEdge(1, 2));
        assertFalse(graph.addEdge(1, 2)); // Дубликат
        assertTrue(graph.hasEdge(1, 2));
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testRemoveEdge() {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        assertTrue(graph.removeEdge(1, 2));
        assertFalse(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 3));
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testGetNeighbors() {
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);

        List<Integer> neighbors1 = graph.getNeighbors(1);
        assertEquals(2, neighbors1.size());
        assertTrue(neighbors1.contains(2));
        assertTrue(neighbors1.contains(3));

        List<Integer> neighbors2 = graph.getNeighbors(2);
        assertEquals(1, neighbors2.size());
        assertTrue(neighbors2.contains(4));
    }

    @Test
    void testReadFromFile() throws IOException {
        File tempFile = File.createTempFile("graph", ".txt");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("3");
            writer.println("1 2");
            writer.println("2 3");
            writer.println("3 1");
        }

        graph.readFromFile(tempFile.getAbsolutePath());

        assertEquals(3, graph.getVertexCount());
        assertEquals(3, graph.getEdgeCount());
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 3));
        assertTrue(graph.hasEdge(3, 1));

        tempFile.delete();
    }

    @Test
    void testToString() {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        String result = graph.toString();

        assertTrue(result.contains("Incidence Matrix Graph"));
        assertTrue(result.contains("vertices: 3"));
        assertTrue(result.contains("edges: 2"));
    }

    @Test
    void testEqualsAndHashCode() {
        IncidenceMatrix graph1 = new IncidenceMatrix();
        IncidenceMatrix graph2 = new IncidenceMatrix();

        graph1.addEdge(1, 2);
        graph1.addEdge(2, 3);

        graph2.addEdge(1, 2);
        graph2.addEdge(2, 3);

        assertEquals(graph1, graph2);
        assertEquals(graph1.hashCode(), graph2.hashCode());

        graph2.addEdge(3, 4);
        assertNotEquals(graph1, graph2);
    }

    @Test
    void testComplexGraph() {
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);

        assertEquals(5, graph.getVertexCount());
        assertEquals(5, graph.getEdgeCount());

        List<Integer> neighbors4 = graph.getNeighbors(4);
        assertEquals(1, neighbors4.size());
        assertTrue(neighbors4.contains(5));
    }

    @Test
    void testMultipleVertexRemovals() {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);

        assertTrue(graph.removeVertex(3));
        assertEquals(4, graph.getVertexCount());
        assertEquals(2, graph.getEdgeCount());

        assertTrue(graph.removeVertex(1));
        assertEquals(3, graph.getVertexCount());
        assertEquals(1, graph.getEdgeCount());

        assertTrue(graph.addEdge(2, 5));
        assertEquals(2, graph.getEdgeCount());
    }
}