package ru.nsu.kutsenko.task121;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки алгоритма топологической сортировки.
 */
public class TopSortTest {

    @Test
    void testTopologicalSortAcyclicGraph() {
        Graph graph = new AdjacencyListGraph();

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        List<Integer> result = TopSort.topologicalSort(graph);

        assertEquals(3, result.size());

        assertTrue(result.indexOf(1) < result.indexOf(2));
        assertTrue(result.indexOf(2) < result.indexOf(3));
    }

    @Test
    void testTopologicalSortMultiplePaths() {
        Graph graph = new AdjacencyMatrixGraph();

        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);

        List<Integer> result = TopSort.topologicalSort(graph);

        assertEquals(4, result.size());
        assertTrue(result.indexOf(1) < result.indexOf(2));
        assertTrue(result.indexOf(1) < result.indexOf(3));
        assertTrue(result.indexOf(2) < result.indexOf(4));
        assertTrue(result.indexOf(3) < result.indexOf(4));
    }

    @Test
    void testTopologicalSortWithCycle() {
        Graph graph = new IncidenceMatrixGraph();

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            TopSort.topologicalSort(graph);
        });
    }

    @Test
    void testTopologicalSortEmptyGraph() {
        Graph graph = new AdjacencyListGraph();

        List<Integer> result = TopSort.topologicalSort(graph);

        assertTrue(result.isEmpty());
    }

    @Test
    void testTopologicalSortSingleVertex() {
        Graph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);

        List<Integer> result = TopSort.topologicalSort(graph);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0));
    }

    @Test
    void testTopologicalSortDisconnectedGraph() {
        Graph graph = new AdjacencyListGraph();

        graph.addEdge(1, 2);
        graph.addEdge(3, 4);

        List<Integer> result = TopSort.topologicalSort(graph);

        assertEquals(4, result.size());
        assertTrue(result.indexOf(1) < result.indexOf(2));
        assertTrue(result.indexOf(3) < result.indexOf(4));
    }

    @Test
    void testTopologicalSortWithAllGraphImplementations() {
        Graph[] graphs = {
            new AdjacencyMatrixGraph(),
            new IncidenceMatrixGraph(),
            new AdjacencyListGraph()
        };

        for (Graph graph : graphs) {
            graph.addEdge(1, 2);
            graph.addEdge(2, 3);
            graph.addEdge(1, 3);

            List<Integer> result = TopSort.topologicalSort(graph);

            assertEquals(3, result.size());
            assertTrue(result.indexOf(1) < result.indexOf(2));
            assertTrue(result.indexOf(2) < result.indexOf(3));
            assertTrue(result.indexOf(1) < result.indexOf(3));
        }
    }

    @Test
    void testTopologicalSortComplexAcyclicGraph() {
        Graph graph = new AdjacencyListGraph();

        graph.addEdge(5, 11);
        graph.addEdge(7, 11);
        graph.addEdge(7, 8);
        graph.addEdge(3, 8);
        graph.addEdge(3, 10);
        graph.addEdge(11, 2);
        graph.addEdge(11, 9);
        graph.addEdge(11, 10);
        graph.addEdge(8, 9);

        List<Integer> result = TopSort.topologicalSort(graph);

        assertEquals(8, result.size());

        assertTrue(result.indexOf(5) < result.indexOf(11));
        assertTrue(result.indexOf(7) < result.indexOf(11));
        assertTrue(result.indexOf(7) < result.indexOf(8));
        assertTrue(result.indexOf(3) < result.indexOf(8));
        assertTrue(result.indexOf(3) < result.indexOf(10));
        assertTrue(result.indexOf(11) < result.indexOf(2));
        assertTrue(result.indexOf(11) < result.indexOf(9));
        assertTrue(result.indexOf(11) < result.indexOf(10));
        assertTrue(result.indexOf(8) < result.indexOf(9));
    }
}