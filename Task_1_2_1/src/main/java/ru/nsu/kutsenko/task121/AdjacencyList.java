package ru.nsu.kutsenko.task121;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Реализация графа на основе списка смежности.
 * Хранит граф в виде отображения вершины в список ее соседей.
 */
public class AdjacencyList<Data> implements Graph<Data> {
    private Map<Integer, List<Integer>> adjacencyList;
    private Map<Integer, Data> vertexData;

    /**
     * Создает пустой граф со списком смежности.
     */
    public AdjacencyList() {
        this.adjacencyList = new HashMap<>();
        this.vertexData = new HashMap<>();
    }

    @Override
    public boolean addVertex(int vertex, Data data) {
        if (adjacencyList.containsKey(vertex)) {
            return false;
        }
        adjacencyList.put(vertex, new ArrayList<>());
        vertexData.put(vertex, data);
        return true;
    }

    @Override
    public boolean removeVertex(int vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            return false;
        }

        for (List<Integer> neighbors : adjacencyList.values()) {
            neighbors.remove((Integer) vertex);
        }

        adjacencyList.remove(vertex);
        vertexData.remove(vertex);
        return true;
    }

    @Override
    public boolean addEdge(int from, int to) {
        if (!adjacencyList.containsKey(from)) {
            addVertex(from, null);
        }
        if (!adjacencyList.containsKey(to)) {
            addVertex(to, null);
        }

        List<Integer> neighbors = adjacencyList.get(from);
        if (neighbors.contains(to)) {
            return false;
        }

        neighbors.add(to);
        return true;
    }

    @Override
    public boolean removeEdge(int from, int to) {
        if (!adjacencyList.containsKey(from)) {
            return false;
        }

        List<Integer> neighbors = adjacencyList.get(from);
        boolean removed = neighbors.remove((Integer) to);
        return removed;
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        return new ArrayList<>(adjacencyList.getOrDefault(vertex, new ArrayList<>()));
    }

    @Override
    public void readFromFile(String filename, DataParser<Data> dataParser) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    try {
                        int from = Integer.parseInt(parts[0]);
                        int to = Integer.parseInt(parts[1]);
                        Data fromData = dataParser != null ? dataParser.parse(parts[0]) : null;
                        Data toData = dataParser != null ? dataParser.parse(parts[1]) : null;
                        if (!hasVertex(from)) {
                            addVertex(from, fromData);
                        }
                        if (!hasVertex(to)) {
                            addVertex(to, toData);
                        }
                        addEdge(from, to);
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException("Invalid number format in line: " + line);
                    }
                }
            }
        }
    }

    @Override
    public Set<Integer> getVertices() {
        return new HashSet<>(adjacencyList.keySet());
    }

    @Override
    public boolean hasVertex(int vertex) {
        return adjacencyList.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(int from, int to) {
        return adjacencyList.containsKey(from)
            && adjacencyList.get(from).contains(to);
    }

    @Override
    public int getVertexCount() {
        return adjacencyList.size();
    }

    @Override
    public int getEdgeCount() {
        int count = 0;
        for (List<Integer> neighbors : adjacencyList.values()) {
            count += neighbors.size();
        }
        return count;
    }

    @Override
    public Data getVertexData(int vertex) {
        return vertexData.get(vertex);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Adjacency List Graph (vertices: ").append(getVertexCount())
            .append(", edges: ").append(getEdgeCount()).append(")\n");

        List<Integer> sortedVertices = new ArrayList<>(adjacencyList.keySet());
        Collections.sort(sortedVertices);

        for (int vertex : sortedVertices) {
            List<Integer> neighbors = new ArrayList<>(adjacencyList.get(vertex));
            Collections.sort(neighbors);
            Data data = vertexData.get(vertex);
            sb.append(vertex).append(" [").append(data != null ? data : "null").append("] -> ");
            sb.append(neighbors).append("\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AdjacencyList<?> other = (AdjacencyList<?>) obj;

        if (!adjacencyList.keySet().equals(other.adjacencyList.keySet())) {
            return false;
        }

        if (!vertexData.equals(other.vertexData)) {
            return false;
        }

        for (int vertex : adjacencyList.keySet()) {
            List<Integer> thisNeighbors = new ArrayList<>(adjacencyList.get(vertex));
            List<Integer> otherNeighbors = new ArrayList<>(other.adjacencyList.get(vertex));
            Collections.sort(thisNeighbors);
            Collections.sort(otherNeighbors);
            if (!thisNeighbors.equals(otherNeighbors)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        List<Integer> sortedVertices = new ArrayList<>(adjacencyList.keySet());
        Collections.sort(sortedVertices);
        for (int vertex : sortedVertices) {
            result = 31 * result + vertex;
            result = 31 * result + (vertexData.get(vertex) != null ? vertexData.get(vertex).hashCode() : 0);
            List<Integer> neighbors = new ArrayList<>(adjacencyList.get(vertex));
            Collections.sort(neighbors);
            result = 31 * result + neighbors.hashCode();
        }
        return result;
    }
}
