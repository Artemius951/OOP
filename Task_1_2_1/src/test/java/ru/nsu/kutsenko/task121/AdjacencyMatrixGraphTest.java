package ru.nsu.kutsenko.task121;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AdjacencyMatrixGraphTest {
    private AdjacencyMatrixGraph graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrixGraph();
    }

    @Test
    void testAddVertex() {
        assertTrue(graph.addVertex(1));
        assertTrue(graph.addVertex(2));
        assertFalse(graph.addVertex(1));
        assertEquals(2, graph.getVertexCount());
    }

    @Test
    void testRemoveVertex() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        assertTrue(graph.removeVertex(1));
        assertFalse(graph.hasVertex(1));
        assertFalse(graph.hasEdge(1, 2));
        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void testAddEdge() {
        graph.addVertex(1);
        graph.addVertex(2);

        assertTrue(graph.addEdge(1, 2));
        assertFalse(graph.addEdge(1, 2));
        assertTrue(graph.hasEdge(1, 2));
    }

    @Test
    void testRemoveEdge() {
        graph.addEdge(1, 2);

        assertTrue(graph.removeEdge(1, 2));
        assertFalse(graph.hasEdge(1, 2));
        assertFalse(graph.removeEdge(1, 2));
    }

    @Test
    void testGetNeighbors() {
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);

        List<Integer> neighbors = graph.getNeighbors(1);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(2));
        assertTrue(neighbors.contains(3));
    }

    @Test
    void testReadFromFile() throws IOException {
        // Создаем временный файл
        File tempFile = File.createTempFile("graph", ".txt");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("2");
            writer.println("1 2");
            writer.println("2 3");
        }

        graph.readFromFile(tempFile.getAbsolutePath());

        assertEquals(3, graph.getVertexCount());
        assertEquals(2, graph.getEdgeCount());
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 3));

        tempFile.delete();
    }

    @Test
    void testToString() {
        graph.addEdge(1, 2);
        String result = graph.toString();
        assertTrue(result.contains("Adjacency Matrix Graph"));
        assertTrue(result.contains("1"));
        assertTrue(result.contains("2"));
    }

    @Test
    void testEqualsAndHashCode() {
        AdjacencyMatrixGraph graph1 = new AdjacencyMatrixGraph();
        AdjacencyMatrixGraph graph2 = new AdjacencyMatrixGraph();

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
    void testLargeGraph() {
        for (int i = 0; i < 100; i++) {
            graph.addVertex(i);
        }

        for (int i = 0; i < 99; i++) {
            graph.addEdge(i, i + 1);
        }

        assertEquals(100, graph.getVertexCount());
        assertEquals(99, graph.getEdgeCount());
    }
}