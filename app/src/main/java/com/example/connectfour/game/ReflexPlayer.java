package com.example.connectfour.game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ReflexPlayer extends Player{

    public ReflexPlayer(Piece.Type type) {
        this.playerPiece = type;
        this.playerScore = 0;
        this.playerName = "CPU";
    }

    @Override
    public Piece.Type getPlayerPiece() {
        return this.playerPiece;
    }

    @Override
    public void setPlayerPiece(Piece.Type playerPiece) {
        this.playerPiece = playerPiece;
    }

    @Override
    public int getPlayerScore() {
        return this.playerScore;
    }

    @Override
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    public String getPlayerName() {
        return this.playerName;
    }

    @Override
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public int getMove(Board board) {


        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<Integer> legalMoves = board.getLegalMoves();
        ArrayList<Integer> moveValues = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0));
        for (int legalMove : legalMoves) {
            Streak streak = Validator.getStreak(legalMove, board);
            moveValues.set(legalMove, getMoveValue(streak));
        }

        int maxElement = Collections.max(moveValues);
        int maxIndex = moveValues.indexOf(maxElement);
        return maxIndex;
    }

    public int getMoveValue(Streak streak) {
        int value = 0;
        if(streak.type == getPlayerPiece()){
            value = streak.count > 3 ? 400 : 10 * streak.count;
        }
        else if (streak.type == Piece.Type.Empty) {
            value = 1;
        }
        else {
            value = streak.count > 3 ? 200 : 10 * streak.count;
        }
        return value;
    }
}
