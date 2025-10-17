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

public class AdjacencyListGraphTest {
    private AdjacencyListGraph graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph();
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
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);

        assertTrue(graph.removeVertex(2));
        assertFalse(graph.hasVertex(2));
        assertFalse(graph.hasEdge(1, 2));
        assertFalse(graph.hasEdge(2, 3));
        assertTrue(graph.hasEdge(3, 1));
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
        graph.addEdge(1, 4);

        List<Integer> neighbors = graph.getNeighbors(1);
        assertEquals(3, neighbors.size());
        assertTrue(neighbors.contains(2));
        assertTrue(neighbors.contains(3));
        assertTrue(neighbors.contains(4));
    }

    @Test
    void testReadFromFile() throws IOException {
        File tempFile = File.createTempFile("graph", ".txt");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("4");
            writer.println("1 2");
            writer.println("2 3");
            writer.println("3 4");
            writer.println("4 1");
        }

        graph.readFromFile(tempFile.getAbsolutePath());

        assertEquals(4, graph.getVertexCount());
        assertEquals(4, graph.getEdgeCount());
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 3));
        assertTrue(graph.hasEdge(3, 4));
        assertTrue(graph.hasEdge(4, 1));

        tempFile.delete();
    }

    @Test
    void testToString() {
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        String result = graph.toString();

        assertTrue(result.contains("Adjacency List Graph"));
        assertTrue(result.contains("1 ->"));
        assertTrue(result.contains("2"));
        assertTrue(result.contains("3"));
    }

    @Test
    void testEqualsAndHashCode() {
        AdjacencyListGraph graph1 = new AdjacencyListGraph();
        AdjacencyListGraph graph2 = new AdjacencyListGraph();

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
    void testIsolatedVertices() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        assertEquals(3, graph.getVertexCount());
        assertEquals(0, graph.getEdgeCount());
        assertTrue(graph.getNeighbors(1).isEmpty());
    }
}