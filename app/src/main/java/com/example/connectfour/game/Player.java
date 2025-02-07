package com.example.connectfour.game;

public abstract class Player {


    protected Piece.Type playerPiece;
    protected String playerName;
    protected int playerScore;

    public abstract Piece.Type getPlayerPiece() ;
    public abstract void setPlayerPiece(Piece.Type playerPiece);

    public abstract int getPlayerScore();
    public abstract void setPlayerScore(int playerScore);

    public abstract String getPlayerName();
    public abstract void setPlayerName(String playerName);

    public abstract int getMove(Board board);

}
