package com.pigrafos;

import com.pigrafos.client.LabyrinthClient;
import com.pigrafos.model.VertexType;
import com.pigrafos.service.LabyrinthSolver;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;

public class RestApiCaller {
    public static void main(String[] args) {
        LabyrinthSolver solver = null;
        try {
             solver = new LabyrinthSolver(new LabyrinthClient());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
            return;
        }

        List<String> labirinthList;
        long inicioTempoBfs = System.currentTimeMillis();
        try {
            String labyrinth = solver.getRandomLabyrinth();
            String user = "Pigrafos";
            System.out.println("*** PROGRAMA EM EXECUÇÃO ***");
            System.out.println("\nID DO LABIRINTO: " + labyrinth);

            Map<Integer, VertexType> vertexTypes = solver.graphCreator(user, labyrinth).getVertexTypes();
            Map<Integer, List<Integer>> adjacencyList = solver.graphCreator(user, labyrinth).getAdjacencyList();
            AtomicReference<Integer> destination = new AtomicReference<>();
            AtomicReference<Integer> source = new AtomicReference<>();
            System.out.println("\nMAPEAMENTO DO GRAFO: ");
            System.out.println("1 - Identificação de vértices: " + vertexTypes);
            System.out.println("2 - Lista de adjacência: " + adjacencyList);
            vertexTypes.forEach((k, v) -> {
                if (v.equals(VertexType.valueOf("INITIAL"))) {
                    source.set(k);
                } else if (v.equals(VertexType.valueOf("FINAL"))) {
                    destination.set(k);
                }
            });
            System.out.println("\nACHANDO A SAÍDA: ");
            System.out.println("3 - Caminho mais curto: " + solver.bfs(source.get(), destination.get()));
            System.out.println(solver.validatePath(user, labyrinth, solver.bfs(source.get(), destination.get())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        long fimTempoBfs = System.currentTimeMillis();
        long execucaoTempoBfs = fimTempoBfs - inicioTempoBfs;

        System.out.println("5 - Tempo de execução: " + execucaoTempoBfs + " ms");
        System.out.println("\n*** FIM DO PROGRAMA ***");

    }
}