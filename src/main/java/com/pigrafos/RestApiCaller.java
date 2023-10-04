package com.pigrafos;

import com.pigrafos.client.LabyrinthClient;
import com.pigrafos.service.LabyrinthSolver;

import java.util.List;

public class RestApiCaller {
    public static void main(String[] args) {
        LabyrinthSolver client = new LabyrinthSolver(new LabyrinthClient());
        List<String> labirinthList;
        try {
            String labyrinth = client.getRandomLabyrint();

            client.solve("Pigrafos", labyrinth);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}