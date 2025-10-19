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
 * Реализация графа на основе матрицы смежности.
 * Хранит граф в виде двумерного boolean массива, где true - ребро есть.
 */
public class AdjacencyMatrix<DataT> implements Graph<DataT> {
    private Map<Integer, Integer> vertexIndexMap;
    private List<Integer> vertices;
    private Map<Integer, DataT> vertexData;
    private boolean[][] adjacencyMatrix;
    private int size;

    /**
     * Создает пустой граф с матрицей смежности.
     */
    public AdjacencyMatrix() {
        this.vertexIndexMap = new HashMap<>();
        this.vertices = new ArrayList<>();
        this.vertexData = new HashMap<>();
        this.adjacencyMatrix = new boolean[0][0];
        this.size = 0;
    }

    @Override
    public boolean addVertex(int vertex, DataT data) {
        if (vertexIndexMap.containsKey(vertex)) {
            return false;
        }
        ensureCapacity();
        vertexIndexMap.put(vertex, size);
        vertices.add(vertex);
        vertexData.put(vertex, data);
        size++;
        return true;
    }

    @Override
    public boolean removeVertex(int vertex) {
        if (!vertexIndexMap.containsKey(vertex)) {
            return false;
        }

        int index = vertexIndexMap.get(vertex);

        for (int i = index; i < size - 1; i++) {
            int currentVertex = vertices.get(i + 1);
            vertices.set(i, currentVertex);
            vertexIndexMap.put(currentVertex, i);
        }

        for (int i = 0; i < size; i++) {
            for (int j = index; j < size - 1; j++) {
                adjacencyMatrix[i][j] = adjacencyMatrix[i][j + 1];
            }
        }
        for (int i = index; i < size - 1; i++) {
            for (int j = 0; j < size; j++) {
                adjacencyMatrix[i][j] = adjacencyMatrix[i + 1][j];
            }
        }

        vertices.remove(size - 1);
        vertexIndexMap.remove(vertex);
        vertexData.remove(vertex);
        size--;
        return true;
    }

    @Override
    public boolean addEdge(int from, int to) {
        if (!vertexIndexMap.containsKey(from)) {
            addVertex(from, null);
        }
        if (!vertexIndexMap.containsKey(to)) {
            addVertex(to, null);
        }

        int fromIndex = vertexIndexMap.get(from);
        int toIndex = vertexIndexMap.get(to);

        if (adjacencyMatrix[fromIndex][toIndex]) {
            return false;
        }

        adjacencyMatrix[fromIndex][toIndex] = true;
        return true;
    }

    @Override
    public boolean removeEdge(int from, int to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            return false;
        }

        int fromIndex = vertexIndexMap.get(from);
        int toIndex = vertexIndexMap.get(to);

        if (!adjacencyMatrix[fromIndex][toIndex]) {
            return false;
        }

        adjacencyMatrix[fromIndex][toIndex] = false;
        return true;
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        if (hasVertex(vertex)) {
            int vertexIndex = vertexIndexMap.get(vertex);
            for (int i = 0; i < size; i++) {
                if (adjacencyMatrix[vertexIndex][i]) {
                    neighbors.add(vertices.get(i));
                }
            }
        }
        return neighbors;
    }

    @Override
    public void readFromFile(String filename, DataParser<DataT> dataParser) throws IOException {
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
                        DataT fromData = dataParser != null ? dataParser.parse(parts[0]) : null;
                        DataT toData = dataParser != null ? dataParser.parse(parts[1]) : null;
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
        return new HashSet<>(vertices);
    }

    @Override
    public boolean hasVertex(int vertex) {
        return vertexIndexMap.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(int from, int to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            return false;
        }
        int fromIndex = vertexIndexMap.get(from);
        int toIndex = vertexIndexMap.get(to);
        return adjacencyMatrix[fromIndex][toIndex];
    }

    @Override
    public int getVertexCount() {
        return size;
    }

    @Override
    public int getEdgeCount() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (adjacencyMatrix[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public DataT getVertexData(int vertex) {
        return vertexData.get(vertex);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Adjacency Matrix Graph (size: ").append(size).append(")\n");

        if (size == 0) {
            return sb.toString();
        }

        sb.append("    ");
        for (int i = 0; i < size; i++) {
            sb.append(String.format("%-4d", vertices.get(i)));
        }
        sb.append("\n");

        for (int i = 0; i < size; i++) {
            int vertex = vertices.get(i);
            DataT data = vertexData.get(vertex);
            sb.append(String.format("%-4d", vertex)).append("[");
            sb.append(data != null ? data : "null").append("]");
            for (int j = 0; j < size; j++) {
                sb.append(adjacencyMatrix[i][j] ? "1   " : "0   ");
            }
            sb.append("\n");
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

        AdjacencyMatrix<?> other = (AdjacencyMatrix<?>) obj;

        if (size != other.size) {
            return false;
        }

        if (!new HashSet<>(vertices).equals(new HashSet<>(other.vertices))) {
            return false;
        }
        if (!vertexData.equals(other.vertexData)) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            int vertex1 = vertices.get(i);
            for (int j = 0; j < size; j++) {
                int vertex2 = vertices.get(j);
                boolean thisEdge = hasEdge(vertex1, vertex2);
                boolean otherEdge = other.hasEdge(vertex1, vertex2);
                if (thisEdge != otherEdge) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        List<Integer> sortedVertices = new ArrayList<>(vertices);
        Collections.sort(sortedVertices);

        for (int vertex : sortedVertices) {
            result = 31 * result + vertex;
            result = 31 * result
                + (vertexData.get(vertex) != null ? vertexData.get(vertex).hashCode() : 0);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (adjacencyMatrix[i][j]) {
                    result = 31 * result + vertices.get(i);
                    result = 31 * result + vertices.get(j);
                }
            }
        }

        return result;
    }

    /**
     * Увеличивает емкость матрицы смежности при необходимости.
     * Удваивает размер матрицы, если текульный размер недостаточен.
     */
    private void ensureCapacity() {
        if (size >= adjacencyMatrix.length) {
            int newCapacity = size == 0 ? 1 : size * 2;
            boolean[][] newMatrix = new boolean[newCapacity][newCapacity];
            for (int i = 0; i < size; i++) {
                System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, size);
            }
            adjacencyMatrix = newMatrix;
        }
    }
}