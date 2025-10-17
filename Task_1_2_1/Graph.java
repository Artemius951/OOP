// Graph.java
import java.util.*;
import java.io.*;

public interface Graph {
    // Основные операции
    boolean addVertex(int vertex);
    boolean removeVertex(int vertex);
    boolean addEdge(int from, int to);
    boolean removeEdge(int from, int to);
    List<Integer> getNeighbors(int vertex);
    
    // Вспомогательные операции
    int getVertexCount();
    int getEdgeCount();
    Set<Integer> getVertices();
    boolean hasVertex(int vertex);
    boolean hasEdge(int from, int to);
    
    // Ввод-вывод
    void readFromFile(String filename) throws IOException;
    String toString();
    
    // Операции сравнения
    boolean equals(Object obj);
    int hashCode();
    
    // Алгоритмы
    List<Integer> topologicalSort();
}