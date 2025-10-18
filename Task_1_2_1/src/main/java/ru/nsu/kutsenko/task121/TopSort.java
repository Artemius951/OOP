package ru.nsu.kutsenko.task121;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Класс для топологической сортировки ориентированных графов.
 * Реализует алгоритм Тарьяна для топологической сортировки.
 */
public class TopSort {

    /**
     * Алгоритм Тарьяна для топологической сортировки.
     * Использует подход на основе подсчета входящих степеней вершин.
     * Работает только с ациклическими ориентированными графами.
     * Иначе выбрасывает исключение.
     * Временная сложность алгоритма: О(V+E), где V - кол-во вершин, E - кол-во ребёр.
     *
     * @param graph граф для сортировки
     * @return список вершин в топологическом порядке
     * @throws IllegalArgumentException если граф содержит циклы
     */
    public static List<Integer> topologicalSort(Graph graph) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();

        for (int vertex : graph.getVertices()) {
            inDegree.put(vertex, 0);
        }

        for (int vertex : graph.getVertices()) {
            for (int neighbor : graph.getNeighbors(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        for (int vertex : graph.getVertices()) {
            if (inDegree.get(vertex) == 0) {
                queue.offer(vertex);
            }
        }

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            result.add(currentVertex);

            for (int neighbor : graph.getNeighbors(currentVertex)) {
                int newDegree = inDegree.get(neighbor) - 1;
                inDegree.put(neighbor, newDegree);

                if (newDegree == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        if (result.size() != graph.getVertices().size()) {
            throw new IllegalArgumentException("Graph contains cycles, topsort is impossible");
        }

        return result;
    }
}