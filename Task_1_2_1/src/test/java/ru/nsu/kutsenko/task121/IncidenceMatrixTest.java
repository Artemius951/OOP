package ru.nsu.kutsenko.task121;

import static org.junit.jupiter.api.Assertions.assertAll;
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
 * Тестовый класс для проверки реализации графа на основе матрицы инцидентности.
 */
public class IncidenceMatrixTest {
    private IncidenceMatrix<String> graph;

    @BeforeEach
    void setUp() {
        graph = new IncidenceMatrix<>();
    }

    @Test
    void testAddVertexWithData() {
        assertTrue(graph.addVertex(1, "data1"));
        assertTrue(graph.addVertex(2, "data2"));
        assertFalse(graph.addVertex(1, "duplicate"));
        assertEquals(2, graph.getVertexCount());
        assertEquals("data1", graph.getVertexData(1));
        assertEquals("data2", graph.getVertexData(2));
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
        assertNull(graph.getVertexData(2));
        assertTrue(graph.addEdge(1, 3));
        assertTrue(graph.hasEdge(1, 3));
        assertEquals(1, graph.getEdgeCount());
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
        graph.addEdge(2, 4);
        List<Integer> neighbors1 = graph.getNeighbors(1);
        List<Integer> neighbors2 = graph.getNeighbors(2);
        assertAll(
            () -> assertEquals(2, neighbors1.size()),
            () -> assertTrue(neighbors1.contains(2)),
            () -> assertTrue(neighbors1.contains(3))
        );
        assertAll(
            () -> assertEquals(1, neighbors2.size()),
            () -> assertTrue(neighbors2.contains(4))
        );
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
        graph.readFromFile(tempFile.getAbsolutePath(), new DataParser<String>() {
            @Override
            public String parse(String vertexStr) {
                return "vertex" + vertexStr;
            }
        });
        assertEquals(3, graph.getVertexCount());
        assertEquals(3, graph.getEdgeCount());
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 3));
        assertTrue(graph.hasEdge(3, 1));
        assertEquals("vertex1", graph.getVertexData(1));
        assertEquals("vertex2", graph.getVertexData(2));
        tempFile.delete();
    }

    @Test
    void testToString() {
        graph.addVertex(1, "data1");
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        String result = graph.toString();
        assertTrue(result.contains("Incidence Matrix Graph"));
        assertTrue(result.contains("vertices: 3"));
        assertTrue(result.contains("edges: 2"));
        assertTrue(result.contains("data1"));
    }



    @Test
    void testEqualsWithDifferentEdges() {
        IncidenceMatrix<String> graph1 = new IncidenceMatrix<>();
        IncidenceMatrix<String> graph2 = new IncidenceMatrix<>();
        graph1.addVertex(1, "A");
        graph1.addVertex(2, "B");
        graph1.addVertex(3, "C");
        graph1.addEdge(1, 2);
        graph1.addEdge(1, 3);
        graph1.addEdge(2, 3);
        graph2.addVertex(1, "A");
        graph2.addVertex(2, "B");
        graph2.addVertex(3, "C");
        graph2.addEdge(1, 2);
        graph2.addEdge(1, 3);
        assertNotEquals(graph1, graph2);
    }

    @Test
    void testComplexGraph() {
        graph.addVertex(1, "v1");
        graph.addVertex(2, "v2");
        graph.addVertex(3, "v3");
        graph.addVertex(4, "v4");
        graph.addVertex(5, "v5");
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
        assertEquals("v1", graph.getVertexData(1));
        assertEquals("v5", graph.getVertexData(5));
    }

    @Test
    void testMultipleVertexRemovals() {
        graph.addVertex(1, "v1");
        graph.addVertex(2, "v2");
        graph.addVertex(3, "v3");
        graph.addVertex(4, "v4");
        graph.addVertex(5, "v5");
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        assertTrue(graph.removeVertex(3));
        assertEquals(4, graph.getVertexCount());
        assertEquals(2, graph.getEdgeCount());
        assertNull(graph.getVertexData(3));
        assertTrue(graph.removeVertex(1));
        assertEquals(3, graph.getVertexCount());
        assertEquals(1, graph.getEdgeCount());
        assertNull(graph.getVertexData(1));
        assertTrue(graph.addEdge(2, 5));
        assertEquals(2, graph.getEdgeCount());
    }

    @Test
    void testVertexDataOperations() {
        graph.addVertex(1, "initial");
        assertEquals("initial", graph.getVertexData(1));
        assertFalse(graph.addVertex(1, "new data"));
        assertEquals("initial", graph.getVertexData(1));
        graph.removeVertex(1);
        graph.addVertex(1, "new data");
        assertEquals("new data", graph.getVertexData(1));
    }
}