package com.pigrafos.service;

import com.pigrafos.client.LabyrinthClient;
import com.pigrafos.model.LabyrinthGraph;
import com.pigrafos.model.LabyrinthResponse;

import java.io.IOException;
import java.util.*;

public class LabyrinthSolver {
    private LabyrinthClient client;
    private LabyrinthGraph graph;
    private Stack<LabyrinthResponse> path;
    private Set<Integer> visited;
    private boolean foundExit;

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

    public void solve(String user, String labirinth) throws IOException {
        foundExit = false;

        LabyrinthResponse starting = client.startExploration(user, labirinth);


        navigating(user, labirinth, starting);
        System.out.println(graph.getAdjacencyList());
    }


    private void navigating(String user, String labirinth, LabyrinthResponse currentPosition) throws IOException {
        visited.add(currentPosition.getActualPosition());

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


}