package com.example.connectfour.game;

public class UserPlayer extends Player{

    UserPlayer() {

    }

    UserPlayer(Piece.Type type, String name) {
        this.playerPiece = type;
        this.playerName = name;
        this.playerScore = 0;
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
        this.playerScore = playerScore;
    }

    @Override
    public int getMove(Board board) {
        final int i = 0;
        return i;
    }


}
