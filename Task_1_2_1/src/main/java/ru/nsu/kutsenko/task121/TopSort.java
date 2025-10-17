package ru.nsu.kutsenko.task121;

import java.util.*;

/**
 * Класс для топологической сортировки ориентированных графов.
 * Реализует алгоритм Тарьяна для топологической сортировки.
 */
public class TopSort {

    /**
     * Алгоритм DFS-based топологической сортировки (алгоритм Тарьяна).
     * Работает с любой реализацией интерфейса Graph.
     *
     * @param graph граф для сортировки
     * @return список вершин в топологическом порядке
     * @throws IllegalArgumentException если граф содержит циклы
     */
    public static List<Integer> topologicalSort(Graph graph) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> recursionStack = new HashSet<>();

        for (int vertex : graph.getVertices()) {
            if (!visited.contains(vertex)) {
                if (!dfs(vertex, graph, visited, recursionStack, result)) {
                    throw new IllegalArgumentException("Graph contains cycles, topsort" +
                        " is impossible");
                }
            }
        }

        Collections.reverse(result);
        return result;
    }

    /**
     * Вспомогательный метод для обхода графа в глубину.
     *
     * @param vertex текущая вершина
     * @param graph граф для обхода
     * @param visited множество посещенных вершин
     * @param recursionStack стек рекурсии для обнаружения циклов
     * @param result список для сохранения результата
     * @return true если обход завершен успешно, false если обнаружен цикл
     */
    private static boolean dfs(int vertex, Graph graph, Set<Integer> visited,
                               Set<Integer> recursionStack, List<Integer> result) {
        if (recursionStack.contains(vertex)) {
            return false;
        }

        if (visited.contains(vertex)) {
            return true;
        }

        visited.add(vertex);
        recursionStack.add(vertex);

        for (int neighbor : graph.getNeighbors(vertex)) {
            if (!dfs(neighbor, graph, visited, recursionStack, result)) {
                return false;
            }
        }

        recursionStack.remove(vertex);
        result.add(vertex);

        return true;
    }
}