package com.pigrafos.service;

import com.pigrafos.client.LabyrinthClient;
import com.pigrafos.model.LabyrinthResponse;

import java.io.IOException;
import java.util.*;

public class LabyrinthSolver {
    private LabyrinthClient client;
    private Stack<LabyrinthResponse> path;
    private boolean foundExit;

    public LabyrinthSolver(LabyrinthClient client) {
        this.client = client;
        this.path = new Stack<>();
    }

    public String getRandomLabyrint() throws IOException {
        List<String> labirinthList = client.verifyLabyrinths();


        Random random = new Random();
        int labyrinthNumber = random.nextInt(0, labirinthList.size());
        return labirinthList.get(labyrinthNumber);
    }

    public void solve(String user, String labirinth) throws IOException {
        foundExit = false;
        Set<Integer> visited = new HashSet<>();
        LabyrinthResponse starting = client.startExploration(user, labirinth);

        System.out.println("Posição Inicial: " + starting.getActualPosition() + "\nMovimentos: " + starting.getMovimentos() + "\nFinal: " + starting.isFinal() + "\nInicio: " + starting.isInicio());
        System.out.println("=====================================================");

        dfs(user, labirinth,starting , visited);
    }

    private boolean dfs(String user, String labirinth, LabyrinthResponse currentPosition, Set<Integer> visited) throws IOException {
        visited.add(currentPosition.getActualPosition());
        path.push(currentPosition);

        // Verifique se chegou ao ponto de saída
        if (isExit(currentPosition)) {
            System.out.println("Caminho para a saída encontrado: " + path.stream().map(LabyrinthResponse::getActualPosition).toList());
            foundExit = true;
            return foundExit;
        }

        // Verifique quais são os movimentos possíveis para o nó atual
        for (int newPosition : currentPosition.getMovimentos()) {
            // Se a posição não foi visitada e eu ainda não encontrei a saída, movo para ela
            if (!visited.contains(newPosition) && !foundExit) {
                LabyrinthResponse moveResponse = client.move(user, labirinth, newPosition);
                System.out.println("Posição Atual: " + moveResponse.getActualPosition() + " \nMovimentos: " + moveResponse.getMovimentos() + "\nFinal: " + moveResponse.isFinal() + "\nInicio: " + moveResponse.isInicio());
                System.out.println("=====================================================");
                // Lógica parecida com exercicios de grafos bipartidos
                if (dfs(user, labirinth, moveResponse, visited)) {
                    // Se deu bom eu saio falando para geral da pilha de recursão que deu bom
                    return foundExit;
                } else {
                    // Se retornou falso significa que meu nó passado deu ruim e já saiu da fila, então pego o pai dele que está logo atrás dele na fila...
                    dfs(user, labirinth, client.move(user, labirinth, path.pop().getActualPosition()), visited);
                }
                ;
            }
        }

        // Se não encontrou a saída a partir desta posição, tiro nó da fila e retorno falso
        if (!foundExit) {
            path.pop();
            foundExit= false;
        }
        return foundExit;
    }


    private boolean isExit(LabyrinthResponse currentPosition) {
        return currentPosition.isFinal();
    }
}
