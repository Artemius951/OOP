package ru.nsu.kutsenko.task121;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для топологической сортировки ориентированных графов.
 * Реализует DFS-алгоритм для топологической сортировки.
 */
public class TopSort {

    /**
     * DFS-алгоритм для топологической сортировки.
     * Использует подход на основе DFS.
     * Работает только с ациклическими ориентированными графами.
     * Иначе выбрасывает исключение.
     * Временная сложность алгоритма: О(V+E), где V - кол-во вершин, E - кол-во ребёр.
     *
     * @param graph граф для сортировки
     * @return список вершин в топологическом порядке
     * @throws IllegalArgumentException если граф содержит циклы
     */
    public static List<Integer> topologicalSortDfs(Graph<?> graph) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> visited = new HashMap<>();

        for (int vertex : graph.getVertices()) {
            visited.put(vertex, 0);
        }

        for (int vertex : graph.getVertices()) {
            if (visited.get(vertex) == 0) {
                if (!dfs(vertex, graph, visited, result)) {
                    throw new IllegalArgumentException("Graph contains cycles");
                }
            }
        }

        Collections.reverse(result);
        return result;
    }

    private static boolean dfs(int vertex, Graph<?> graph, Map<Integer, Integer> visited,
                               List<Integer> result) {
        visited.put(vertex, 1);

        for (int neighbor : graph.getNeighbors(vertex)) {
            if (visited.get(neighbor) == 1) {
                return false;
            }
            if (visited.get(neighbor) == 0) {
                if (!dfs(neighbor, graph, visited, result)) {
                    return false;
                }
            }
        }

        visited.put(vertex, 2);
        result.add(vertex);
        return true;
    }
}