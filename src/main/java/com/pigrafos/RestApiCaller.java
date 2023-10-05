package com.pigrafos;

import com.pigrafos.client.LabyrinthClient;
import com.pigrafos.service.LabyrinthSolver;

import java.util.List;

public class RestApiCaller {
    public static void main(String[] args) {
        LabyrinthSolver solver = new LabyrinthSolver(new LabyrinthClient());
        List<String> labirinthList;
        try {
            String labyrinth = solver.getRandomLabyrinth();
            String user = "Pigrafos";

//            List<Integer> pigrafos = solver.solve("Pigrafos", labyrinth);
//            pigrafos.remove(0);
//            System.out.println(pigrafos);
//
//            System.out.println(solver.verifyResponse(user, labyrinth, pigrafos));

                solver.solve(user, labyrinth);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}