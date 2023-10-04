package com.pigrafos.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LabyrinthResponse {
    @JsonProperty("pos_atual")
    private int actualPosition;
    @JsonProperty("inicio")
    private boolean start;
    @JsonProperty("final")
    private boolean end;
    @JsonProperty("movimentos")
    private List<Integer> possibleMoves;

    public int getActualPosition() {
        return actualPosition;
    }

    public boolean isInicio() {
        return start;
    }

    public boolean isFinal() {
        return end;
    }

    public List<Integer> getMovimentos() {
        return possibleMoves;
    }

    @Override
    public String toString() {
        return "LabirinthResponse{" +
                "actualPosition=" + actualPosition +
                ", start=" + start +
                ", end=" + end +
                ", possibleMoves=" + possibleMoves +
                '}';
    }
}
