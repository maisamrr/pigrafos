package com.pigrafos.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FinalResponse {

    @JsonProperty("caminho_valido")
    private boolean isValid;
    @JsonProperty("quantidade_movimentos")
    private int moves;

    public FinalResponse() {
    }

    public FinalResponse(boolean isValid, int moves) {
        this.isValid = isValid;
        this.moves = moves;
    }

    public boolean isValid() {
        return isValid;
    }


    public int getMoves() {
        return moves;
    }

    @Override
    public String toString() {
        return "Final Response {" +
                "isValid = " + isValid +
                ", moves = " + moves +
                " }";
    }
}
