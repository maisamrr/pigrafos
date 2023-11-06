package com.pigrafos.service;

import java.util.*;
import com.pigrafos.model.LabyrinthGraph;

public class BfsSearch {
    public List<Integer> bfs(LabyrinthGraph graph, int source, int destination) {
        Map<Integer, Integer> distance = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();

        for (int vertex : graph.getAdjacencyList().keySet()) {
            distance.put(vertex, Integer.MAX_VALUE);
            parent.put(vertex, null);
        }

        distance.put(source, 0);
        queue.add(source);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int neighbor : graph.getNeighbors(current)) {
                if (distance.get(neighbor) == Integer.MAX_VALUE) {
                    distance.put(neighbor, distance.get(current) + 1);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        List<Integer> shortestPath = new ArrayList<>();
        int current = destination;

        while (current != source) {
            shortestPath.add(current);
            current = parent.get(current);
        }

        shortestPath.add(source);
        Collections.reverse(shortestPath);

        return shortestPath;
    }
}
