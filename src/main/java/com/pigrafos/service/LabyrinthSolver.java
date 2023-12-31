package com.pigrafos.service;

import com.pigrafos.client.LabyrinthClient;
import com.pigrafos.model.FinalResponse;
import com.pigrafos.model.LabyrinthGraph;
import com.pigrafos.model.LabyrinthResponse;
import com.pigrafos.model.VertexType;

import java.io.IOException;
import java.util.*;

public class LabyrinthSolver {
    private final LabyrinthClient client;
    private final LabyrinthGraph graph;
    private final Stack<LabyrinthResponse> path;
    private final Set<Integer> visited;

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

    public LabyrinthGraph graphCreator(String user, String labyrinth) throws IOException {
        LabyrinthResponse starting = client.startExploration(user, labyrinth);

        navigating(user, labyrinth, starting);
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

        graph.buildGraph(List.of(currentPosition));
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

    public List<Integer> bfs(int source, int destination) {
        BfsSearch graphSearch = new BfsSearch();
        return graphSearch.bfs(graph, source, destination);
    }

    public FinalResponse validatePath(String user, String labirinth, List<Integer> moves) throws IOException {
        return client.validatePath(user, labirinth, moves);
    }
}
