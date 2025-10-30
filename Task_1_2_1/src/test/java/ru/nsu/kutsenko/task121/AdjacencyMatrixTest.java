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
 * Тестовый класс для проверки реализации графа на основе матрицы смежности.
 */
public class AdjacencyMatrixTest {
    private AdjacencyMatrix<String> graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrix<>();
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
        graph.addVertex(1, "v1");
        graph.addVertex(2, "v2");
        graph.addEdge(1, 2);
        assertTrue(graph.removeVertex(1));
        assertFalse(graph.hasVertex(1));
        assertFalse(graph.hasEdge(1, 2));
        assertEquals(1, graph.getVertexCount());
        assertNull(graph.getVertexData(1)); // Данные удалены
        assertEquals("v2", graph.getVertexData(2)); // Данные другой вершины сохранились
    }

    @Test
    void testAddEdge() {
        graph.addVertex(1, "v1");
        graph.addVertex(2, "v2");
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
        File tempFile = File.createTempFile("graph", ".txt");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("2");
            writer.println("1 2");
            writer.println("2 3");
        }

        graph.readFromFile(tempFile.getAbsolutePath(), new DataParser<String>() {
            @Override
            public String parse(String vertexStr) {
                return "node" + vertexStr;
            }
        });

        assertEquals(3, graph.getVertexCount());
        assertEquals(2, graph.getEdgeCount());
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 3));
        assertEquals("node1", graph.getVertexData(1));
        assertEquals("node2", graph.getVertexData(2));
        tempFile.delete();
    }

    @Test
    void testReadFromFileWithoutParser() throws IOException {
        File tempFile = File.createTempFile("graph", ".txt");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("2");
            writer.println("1 2");
        }

        graph.readFromFile(tempFile.getAbsolutePath(), null);
        assertEquals(2, graph.getVertexCount());
        assertEquals(1, graph.getEdgeCount());
        assertNull(graph.getVertexData(1));
        tempFile.delete();
    }

    @Test
    void testToString() {
        graph.addVertex(1, "vertex1");
        graph.addEdge(1, 2);
        String result = graph.toString();
        assertTrue(result.contains("Adjacency Matrix Graph"));
        assertTrue(result.contains("1") || result.contains("vertex1"));
        assertTrue(result.contains("2"));
        System.out.println("ToString result:");
        System.out.println(result);
    }

    @Test
    void testEquals() {
        AdjacencyMatrix<String> graph1 = new AdjacencyMatrix<>();
        graph1.addVertex(1, "A");
        graph1.addVertex(2, "B");
        graph1.addVertex(3, "C");
        graph1.addEdge(1, 2);
        graph1.addEdge(2, 3);
        AdjacencyMatrix<String> graph2 = new AdjacencyMatrix<>();
        graph2.addVertex(1, "A");
        graph2.addVertex(2, "B");
        graph2.addVertex(3, "C");
        graph2.addEdge(1, 2);
        graph2.addEdge(2, 3);
        assertEquals(graph1, graph2);
        AdjacencyMatrix<String> graph3 = new AdjacencyMatrix<>();
        graph3.addVertex(1, "X"); // Разные данные
        graph3.addVertex(2, "B");
        graph3.addVertex(3, "C");
        graph3.addEdge(1, 2);
        graph3.addEdge(2, 3);
        assertNotEquals(graph1, graph3);
    }

    @Test
    void testEqualsWithDifferentStructure() {
        AdjacencyMatrix<String> graph1 = new AdjacencyMatrix<>();
        graph1.addVertex(1, "data");
        graph1.addVertex(2, "data");
        graph1.addVertex(3, "data");
        graph1.addEdge(1, 2);
        graph1.addEdge(2, 3);
        AdjacencyMatrix<String> graph2 = new AdjacencyMatrix<>();
        graph2.addVertex(1, "data");
        graph2.addVertex(2, "data");
        graph2.addVertex(3, "data");
        graph2.addEdge(1, 3);
        graph2.addEdge(2, 3);
        assertNotEquals(graph1, graph2);
    }

    @Test
    void testLargeGraph() {
        for (int i = 0; i < 100; i++) {
            graph.addVertex(i, "data" + i);
        }
        for (int i = 0; i < 99; i++) {
            graph.addEdge(i, i + 1);
        }
        assertEquals(100, graph.getVertexCount());
        assertEquals(99, graph.getEdgeCount());

        assertEquals("data0", graph.getVertexData(0));
        assertEquals("data99", graph.getVertexData(99));
    }

    @Test
    void testVertexDataRetrieval() {
        graph.addVertex(1, "custom data");
        graph.addVertex(2, null);
        assertEquals("custom data", graph.getVertexData(1));
        assertNull(graph.getVertexData(2));
        assertNull(graph.getVertexData(999));
    }

    @Test
    void testEdgeOperationsWithData() {
        graph.addVertex(1, "start");
        graph.addVertex(2, "end");
        assertTrue(graph.addEdge(1, 2));
        assertTrue(graph.hasEdge(1, 2));
        assertEquals("start", graph.getVertexData(1));
        assertEquals("end", graph.getVertexData(2));
        assertTrue(graph.removeEdge(1, 2));
        assertFalse(graph.hasEdge(1, 2));
        assertEquals("start", graph.getVertexData(1));
        assertEquals("end", graph.getVertexData(2));
    }
}