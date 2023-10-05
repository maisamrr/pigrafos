package com.pigrafos.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabyrinthGraph {
    private Map<Integer, List<Integer>> adjacencyList;

    public LabyrinthGraph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addVertex(int vertex) {
        adjacencyList.put(vertex, new ArrayList<>());
    }

    public void addEdge(int source, int destination) {
        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source);
    }

    public List<Integer> getNeighbors(int vertex) {
        return adjacencyList.get(vertex);
    }

    public Map<Integer, List<Integer>> getAdjacencyList() {
        return adjacencyList;
    }

    public void buildGraph(List<LabyrinthResponse> responses) {
        for (LabyrinthResponse response : responses) {
            int currentPosition = response.getActualPosition();
            List<Integer> possibleMoves = response.getMovimentos();

            if (!adjacencyList.containsKey(currentPosition)) {
                addVertex(currentPosition);
            }

            for (int newPosition : possibleMoves) {
                if (!adjacencyList.containsKey(newPosition)) {
                    addVertex(newPosition);
                }
                addEdge(currentPosition, newPosition);
            }
        }
    }
}
