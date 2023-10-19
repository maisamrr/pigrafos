package com.pigrafos.service;

import com.pigrafos.client.LabyrinthClient;
import com.pigrafos.model.FinalResponse;
import com.pigrafos.model.LabyrinthGraph;
import com.pigrafos.model.LabyrinthResponse;
import com.pigrafos.model.VertexType;

import java.io.IOException;
import java.util.*;

public class LabyrinthSolver {
    private LabyrinthClient client;
    private LabyrinthGraph graph;
    private Stack<LabyrinthResponse> path;
    private Set<Integer> visited;

    public LabyrinthSolver(LabyrinthClient client) {
        this.client = client;
        this.graph = new LabyrinthGraph();
        this.visited = new HashSet<>();
        this.path = new Stack<>();
    }

    public String getRandomLabyrinth() throws IOException {
        List<String> labirinthList = client.verifyLabyrinths();


        Random random = new Random();
        int labyrinthNumber = random.nextInt(0, labirinthList.size());
        return labirinthList.get(labyrinthNumber);
    }

    public LabyrinthGraph graphCreator(String user, String labirinth) throws IOException {

        LabyrinthResponse starting = client.startExploration(user, labirinth);


        navigating(user, labirinth, starting);
        return graph;
    }

    private void navigating(String user, String labirinth, LabyrinthResponse currentPosition) throws IOException {
        visited.add(currentPosition.getActualPosition());

        if (currentPosition.isInicio()) {
            graph.setVertexType(currentPosition.getActualPosition(), VertexType.INITIAL);
        } else if (currentPosition.isFinal()) {
            graph.setVertexType(currentPosition.getActualPosition(), VertexType.FINAL);
        } else {
            graph.setVertexType(currentPosition.getActualPosition(), VertexType.COMMON);
        }

        if (graph.getNeighbors(currentPosition.getActualPosition()) == null) {


            graph.buildGraph(List.of(currentPosition));
        }
        path.push(currentPosition);


        for (int newPosition : currentPosition.getMovimentos()) {
            if (!visited.contains(newPosition)) {
                LabyrinthResponse moveResponse = client.move(user, labirinth, newPosition);
                navigating(user, labirinth, moveResponse);
            }
        }

        path.pop();
        if (path.size() - 1 >= 0) {
            client.move(user, labirinth, path.get(path.size() - 1).getActualPosition());
        }

    }

    public List<Integer> shortestPath(int source, int destination) {
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

    public FinalResponse validatePath(String user, String labirinth, List<Integer> moves) throws IOException {
        return client.validatePath(user, labirinth, moves);
    }
}
