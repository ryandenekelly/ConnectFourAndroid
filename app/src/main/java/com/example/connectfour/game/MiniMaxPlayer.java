package com.example.connectfour.game;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MiniMaxPlayer extends Player{

    private final int treeDepth = 7;

    public MiniMaxPlayer(Piece.Type type){
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int getMove(Board board) {
        int move = minimax(board, 0, treeDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true).column;
        return move;
    }

    private int getMoveValue(Streak streak) {
        int value = 0;
        if(streak.type == Piece.Type.Blue) {
            value = streak.count >= 4 ? (int) 1e6 : 10 * streak.count;
        }
        else if (streak.type == Piece.Type.Red) {
            value = streak.count >= 4 ? (int) -1e6 : -10 * streak.count;
        }
        else{
            value = 0;
        }
        return value;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private TreeNode minimax(Board board, int node, int depth, int alpha, int beta, boolean maxPlayer) {

        //board.displayBoard();
        int[] moveValue = {0,0,0,0,0,0,0};
        for(int i=0; i<7; i++){
            Streak streak = Validator.isWinningMove(i, board);
            moveValue[i] = getMoveValue(streak);

            if(Math.abs(moveValue[i]) == (int)1e6){
                // board.displayBoard();
                return new TreeNode(node,moveValue[i]);
            }

        }
        int bestMove = maxPlayer ? Arrays.stream(moveValue).max().getAsInt() : Arrays.stream(moveValue).min().getAsInt();
        if(depth == 0 || board.isBoardFull()){
            // board.displayBoard();
            return new TreeNode(node, bestMove);
        }

        int value = 0;
        if(maxPlayer){
            value = Integer.MIN_VALUE;

            ArrayList<Integer> legalMoves = board.getLegalMoves();
            Random rand = new Random(System.currentTimeMillis());
            int column = (int) legalMoves.toArray()[rand.nextInt(legalMoves.size())];

            for(int move : legalMoves){
                Board board_copy = board.deepCopy();
                if(!board_copy.placePiece(move, Piece.Type.Blue)){
                    continue;
                }
                int newScore = minimax(board_copy, move, depth-1, alpha, beta, false).value;

                if(newScore > value) {
                    value = newScore;
                    column = move;
                }
                alpha = Math.max(alpha,value);
                if(value >= beta){
                    break;
                }
            }
            return  new TreeNode(column, value);
        }
        else{
            value = Integer.MAX_VALUE;

            ArrayList<Integer> legalMoves = board.getLegalMoves();
            Random rand = new Random(System.currentTimeMillis());
            int column = (int) legalMoves.toArray()[rand.nextInt(legalMoves.size())];

            for(int move : legalMoves){
                Board board_copy = board.deepCopy();
                if(!board_copy.placePiece(move, Piece.Type.Red)){
                    continue;
                }

                int newScore = minimax(board_copy, move, depth -1, alpha, beta, true).value;

                if(newScore < value){
                    value = newScore;
                    column = move;
                }
                beta = Math.min(beta, value);
                if(value <= alpha){
                    break;
                }

            }
            return new TreeNode(column, value);
        }

    }

}
