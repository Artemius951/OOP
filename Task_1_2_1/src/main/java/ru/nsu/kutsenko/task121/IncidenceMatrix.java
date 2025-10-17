package ru.nsu.kutsenko.task121;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
/**
 * Реализация графа на основе матрицы инцидентности.
 * Хранит граф в виде матрицы, где строки соответствуют вершинам, а столбцы - ребрам.
 * Значение 1 означает исходящее ребро, -1 - входящее ребро.
 */
public class IncidenceMatrix implements Graph {
    private Map<Integer, Integer> vertexIndexMap;
    private List<Integer> vertices;
    private List<Edge> edges;
    private int[][] incidenceMatrix;
    private int vertexCount;
    private int edgeCount;

    /**
     * Внутренний класс для представления ребра графа.
     */
    private static class Edge {
        int from;
        int to;

        Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Edge edge = (Edge) obj;
            return from == edge.from && to == edge.to;
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }
    }

    /**
     * Создает пустой граф с матрицей инцидентности.
     */
    public IncidenceMatrix() {
        this.vertexIndexMap = new HashMap<>();
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.incidenceMatrix = new int[0][0];
        this.vertexCount = 0;
        this.edgeCount = 0;
    }

    @Override
    public boolean addVertex(int vertex) {
        if (vertexIndexMap.containsKey(vertex)) {
            return false;
        }
        ensureCapacity();
        vertexIndexMap.put(vertex, vertexCount);
        vertices.add(vertex);
        vertexCount++;
        return true;
    }

    @Override
    public boolean removeVertex(int vertex) {
        if (!vertexIndexMap.containsKey(vertex)) {
            return false;
        }

        int index = vertexIndexMap.get(vertex);

        List<Edge> edgesToRemove = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.from == vertex || edge.to == vertex) {
                edgesToRemove.add(edge);
            }
        }
        for (int i = edgesToRemove.size() - 1; i >= 0; i--) {
            Edge edge = edgesToRemove.get(i);
            removeEdge(edge.from, edge.to);
        }

        for (int i = index; i < vertexCount - 1; i++) {
            int currentVertex = vertices.get(i + 1);
            vertices.set(i, currentVertex);
            vertexIndexMap.put(currentVertex, i);

            System.arraycopy(incidenceMatrix[i + 1], 0, incidenceMatrix[i], 0, edgeCount);
        }

        vertices.remove(vertexCount - 1);
        vertexIndexMap.remove(vertex);
        vertexCount--;

        return true;
    }

    @Override
    public boolean addEdge(int from, int to) {
        addVertex(from);
        addVertex(to);

        Edge edge = new Edge(from, to);
        if (edges.contains(edge)) {
            return false;
        }

        ensureCapacity();
        edges.add(edge);

        int fromIndex = vertexIndexMap.get(from);
        int toIndex = vertexIndexMap.get(to);
        int edgeIndex = edgeCount;

        incidenceMatrix[fromIndex][edgeIndex] = 1;
        incidenceMatrix[toIndex][edgeIndex] = -1;

        edgeCount++;
        return true;
    }

    @Override
    public boolean removeEdge(int from, int to) {
        Edge edgeToRemove = new Edge(from, to);
        int edgeIndex = -1;

        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).equals(edgeToRemove)) {
                edgeIndex = i;
                break;
            }
        }

        if (edgeIndex == -1) {
            return false;
        }


        for (int i = 0; i < vertexCount; i++) {
            for (int j = edgeIndex; j < edgeCount - 1; j++) {
                incidenceMatrix[i][j] = incidenceMatrix[i][j + 1];
            }
            if (edgeCount > 0) {
                incidenceMatrix[i][edgeCount - 1] = 0;
            }
        }

        edges.remove(edgeIndex);
        edgeCount--;
        return true;
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        if (hasVertex(vertex)) {
            int vertexIndex = vertexIndexMap.get(vertex);

            for (int j = 0; j < edgeCount; j++) {
                if (incidenceMatrix[vertexIndex][j] == 1) {
                    for (int i = 0; i < vertexCount; i++) {
                        if (incidenceMatrix[i][j] == -1) {
                            neighbors.add(vertices.get(i));
                        }
                    }
                }
            }
        }
        return neighbors;
    }

    @Override
    public void readFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    try {
                        int from = Integer.parseInt(parts[0]);
                        int to = Integer.parseInt(parts[1]);
                        addEdge(from, to);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format in line: " + line);
                    }
                }
            }
        }
    }

    @Override
    public Set<Integer> getVertices() {
        return new HashSet<>(vertices);
    }

    @Override
    public boolean hasVertex(int vertex) {
        return vertexIndexMap.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(int from, int to) {
        return edges.contains(new Edge(from, to));
    }

    @Override
    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Incidence Matrix Graph (vertices: ").append(vertexCount)
            .append(", edges: ").append(edgeCount).append(")\n");

        if (vertexCount == 0) {
            return sb.toString();
        }

        sb.append("    ");
        for (int j = 0; j < edgeCount; j++) {
            sb.append(String.format("e%-3d", j));
        }
        sb.append("\n");

        for (int i = 0; i < vertexCount; i++) {
            sb.append(String.format("%-4d", vertices.get(i)));
            for (int j = 0; j < edgeCount; j++) {
                sb.append(String.format("%-4d", incidenceMatrix[i][j]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        IncidenceMatrix other = (IncidenceMatrix) obj;

        if (vertexCount != other.vertexCount || edgeCount != other.edgeCount)
            return false;

        return new HashSet<>(vertices).equals(new HashSet<>(other.vertices)) &&
            new HashSet<>(edges).equals(new HashSet<>(other.edges));
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(vertexCount, edgeCount);
        result = 31 * result + vertices.hashCode();
        result = 31 * result + edges.hashCode();
        return result;
    }

    /**
     * Увеличивает емкость матрицы инцидентности при необходимости.
     * Удваивает размер матрицы по измерениям вершин и/или ребер при необходимости.
     */
    private void ensureCapacity() {
        boolean needResize = false;
        int newVertexCapacity = incidenceMatrix.length;
        int newEdgeCapacity = incidenceMatrix.length > 0 ? incidenceMatrix[0].length : 0;

        if (vertexCount >= newVertexCapacity) {
            newVertexCapacity = vertexCount == 0 ? 1 : vertexCount * 2;
            needResize = true;
        }

        if (edgeCount >= newEdgeCapacity) {
            newEdgeCapacity = edgeCount == 0 ? 1 : edgeCount * 2;
            needResize = true;
        }

        if (needResize) {
            int[][] newMatrix = new int[newVertexCapacity][newEdgeCapacity];
            for (int i = 0; i < vertexCount; i++) {
                if (edgeCount > 0) {
                    System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, edgeCount);
                }
            }
            incidenceMatrix = newMatrix;
        }
    }
}