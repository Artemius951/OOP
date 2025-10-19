package ru.nsu.kutsenko.task121;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки реализации графа на основе списка смежности.
 */
public class AdjacencyListTest {
    private AdjacencyList<String> graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyList<>();
    }

    @Test
    void testAddVertex() {
        assertTrue(graph.addVertex(1, "data1"));
        assertTrue(graph.addVertex(2, "data2"));
        assertFalse(graph.addVertex(1, "duplicate"));
        assertEquals(2, graph.getVertexCount());
        assertEquals("data1", graph.getVertexData(1));
        assertEquals("data2", graph.getVertexData(2));
    }

    @Test
    void testAddVertexWithoutData() {
        assertTrue(graph.addVertex(1, null));
        assertNull(graph.getVertexData(1));
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
        assertNull(graph.getVertexData(2));
    }

    @Test
    void testAddEdge() {
        assertTrue(graph.addEdge(1, 2));
        assertFalse(graph.addEdge(1, 2));
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
        graph.readFromFile(tempFile.getAbsolutePath(), new DataParser<String>() {
            @Override
            public String parse(String vertexStr) {
                return "data" + vertexStr;
            }
        });
        assertEquals(4, graph.getVertexCount());
        assertEquals(4, graph.getEdgeCount());
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 3));
        assertTrue(graph.hasEdge(3, 4));
        assertTrue(graph.hasEdge(4, 1));
        assertEquals("data1", graph.getVertexData(1));
        assertEquals("data2", graph.getVertexData(2));
        tempFile.delete();
    }

    @Test
    void testReadFromFileWithoutParser() throws IOException {
        File tempFile = File.createTempFile("graph", ".txt");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("2");
            writer.println("1 2");
            writer.println("2 3");
        }
        graph.readFromFile(tempFile.getAbsolutePath(), null);
        assertEquals(3, graph.getVertexCount());
        assertEquals(2, graph.getEdgeCount());
        assertNull(graph.getVertexData(1));
        tempFile.delete();
    }

    @Test
    void testToString() {
        graph.addVertex(1, "vertex1");
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        String result = graph.toString();
        assertTrue(result.contains("Adjacency List Graph"));
        assertTrue(result.contains("1 [vertex1]"));
        assertTrue(result.contains("2"));
        assertTrue(result.contains("3"));
    }

    @Test
    void testEquals() {
        AdjacencyList<String> graph1 = new AdjacencyList<>();
        AdjacencyList<String> graph2 = new AdjacencyList<>();
        graph1.addVertex(1, "data1");
        graph1.addVertex(2, "data2");
        graph1.addVertex(3, "data3");
        graph1.addEdge(1, 2);
        graph1.addEdge(2, 3);
        graph2.addVertex(1, "data1");
        graph2.addVertex(2, "data2");
        graph2.addVertex(3, "data3");
        graph2.addEdge(1, 2);
        graph2.addEdge(2, 3);
        assertEquals(graph1, graph2);
        AdjacencyList<String> graph3 = new AdjacencyList<>();
        graph3.addVertex(1, "data1");
        graph3.addVertex(2, "data2");
        graph3.addVertex(3, "different");
        graph3.addEdge(1, 2);
        graph3.addEdge(2, 3);
        assertNotEquals(graph1, graph3);
    }

    @Test
    void testIsolatedVertices() {
        graph.addVertex(1, "data1");
        graph.addVertex(2, "data2");
        graph.addVertex(3, "data3");
        assertEquals(3, graph.getVertexCount());
        assertEquals(0, graph.getEdgeCount());
        assertTrue(graph.getNeighbors(1).isEmpty());
        assertEquals("data1", graph.getVertexData(1));
    }

    @Test
    void testVertexData() {
        graph.addVertex(1, "custom data");
        graph.addVertex(2, null);
        assertEquals("custom data", graph.getVertexData(1));
        assertNull(graph.getVertexData(2));
        assertNull(graph.getVertexData(999));
    }
}