package com.example.connectfour.game;


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private static final int width = 7;
    private static final int height = 6;
    private Piece.Type[][] gameboard;
    private int[] topOfColumns;
    private boolean isBoardFull;
    private int totalPieces;

    public Board() {
        gameboard = new Piece.Type[width][height];
        for(int column=0; column < gameboard.length; column++) {
            gameboard[column] = new Piece.Type[]{Piece.Type.Empty, Piece.Type.Empty,
                    Piece.Type.Empty, Piece.Type.Empty, Piece.Type.Empty, Piece.Type.Empty};
        }

        isBoardFull = false;
        totalPieces = 0;
        topOfColumns = new int[width];
    }

    public void displayBoard() {
        // Top border.
        Log.d("D", "- - - - - - - - -\n");
        for(int y_pos=height-1; y_pos>=0; y_pos--)
        {
            // Left border.
            String line = "";
            line += "|";

            // Print the appropriate symbol for each piece.
            for(int x_pos=0; x_pos<width; x_pos++)
            {
                if( gameboard[x_pos][y_pos] == Piece.Type.Blue)
                {
                    line += " O ";
                }
                else if( gameboard[x_pos][y_pos] == Piece.Type.Red)
                {
                    line += " X ";
                }
                else
                {
                    line += " . ";
                }

            }
            // Write border - new row
            line += "|";
            Log.d("D", line);
        }
        // Bottom border.
        Log.d("D", "- - - - - - - - -\n");

    }

    public boolean checkMove(int move) {
        if(move >= width || move < 0) {
            return false;
        }
        int topOfColumn = topOfColumns[move];
        if(topOfColumn >= height || topOfColumn == -1) {
            topOfColumns[move] = -1;
            return false;
        }
        return true;
    }

    public boolean placePiece(int x_pos, Piece.Type piece) {
        if (piece == Piece.Type.Empty) {
            return false;
        }

        int topOfColumn = topOfColumns[x_pos];
        if (topOfColumn >= height) {
            topOfColumns[x_pos] = -1;
            return false;
        } else {
            gameboard[x_pos][topOfColumn] = piece;
            topOfColumns[x_pos]++;
        }

        isBoardFull = ++totalPieces == height * width;
        return true;
    }

    public ArrayList<Integer> getLegalMoves() {
        ArrayList<Integer> legalMoves = new ArrayList<Integer>();
        for(int i=0; i<topOfColumns.length; i++)
        {
            if(topOfColumns[i] != -1 && topOfColumns[i] < 6)
            {
                legalMoves.add(i);
            }

        }
        return legalMoves;
    }

    public int[] getTopOfColumns() {
        return this.topOfColumns;
    }

    public Piece.Type getPiece(int x, int y)
    {
        if( (x>=0 && x<width) && (y>=0 && y<height) )
        {
            return gameboard[x][y];
        }
        return Piece.Type.Empty;
    }

    public Board deepCopy() {
        Board newBoard = new Board();
        newBoard.gameboard = new Piece.Type[width][height];
        newBoard.topOfColumns = Arrays.copyOf( this.topOfColumns, this.topOfColumns.length);
        newBoard.isBoardFull = this.isBoardFull;
        newBoard.totalPieces = this.totalPieces;

        for(int i=0; i<this.gameboard.length; i++){
            for(int j=0; j< this.gameboard[i].length; j++){
                newBoard.gameboard[i][j] = this.gameboard[i][j];
            }
        }

        return newBoard;

    }


    public boolean isBoardFull() {
        return this.isBoardFull;
    }
}
