package com.example.connectfour.game;

import androidx.lifecycle.MutableLiveData;

public class Game {


    public GameType getGameType() {
        return this.gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public enum GameType {
        OnePlayer,
        TwoPlayer
    }
    public  enum OpponentType {
        Reflex,
        MiniMax,
        None
    }
    private GameType gameType;
    private OpponentType opponentType;
    private Board board;
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;
    private boolean boardFull;

    public MutableLiveData<Player> winner = new MutableLiveData<>();
    public MutableLiveData<Boolean> opponentMove = new MutableLiveData<>();

    public Game(){
        board = new Board();
        playerOne = null;
        playerTwo = null;
        currentPlayer = null;
        boardFull = false;
        gameType = null;
        opponentType = null;
        // opponentMove.setValue(true);
    }

    public boolean addUserPlayer(String playerName){
        if(playerOne == null){
            this.playerOne = new UserPlayer(Piece.Type.Red, playerName);
            return true;
        }
        else if(playerTwo == null){
            this.playerTwo = new UserPlayer(Piece.Type.Blue, playerName);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean addComputerPlayer(String opponentType){
        if(this.playerOne == null) {
            switch(opponentType) {
                case "Dumb":
                    this.playerOne = new ReflexPlayer(Piece.Type.Red);
                    return true;

                case "Smarter":
                    this.playerOne = new MiniMaxPlayer(Piece.Type.Red);
                    return true;

                default:
                    return false;
            }
        }
        else if(this.playerTwo == null) {
            switch(opponentType) {
                case "Dumb":
                    this.playerTwo = new ReflexPlayer(Piece.Type.Blue);
                    return true;

                case "Smarter":
                    this.playerTwo = new MiniMaxPlayer(Piece.Type.Blue);
                    return true;

                default:
                    return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean initCurrentPlayer() {
        if(playerOne == null || playerTwo == null) {
            return false;
        }

        currentPlayer = playerOne;
        return true;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Board getBoard() {
        return this.board;
    }

    public void changeTurn() {
        // if the current player is player one, then set currwent player to player two
        if(currentPlayer.getPlayerName().equals(playerOne.getPlayerName())) {
            currentPlayer = playerTwo;
        }
        // if the current player wasnt player one then it is player two - so switch to player one.
        else {
            currentPlayer = playerOne;
        }
    }

    public boolean hasGameEnded(int lastMove) {

        if(board.isBoardFull()) {
            return true;
        }

        boolean winningMove = false;
        board.placePiece(lastMove, currentPlayer.playerPiece);
        Streak streak = Validator.isWinningMove(lastMove, board);
        winningMove |= (streak.count >= 4) && (streak.type == currentPlayer.playerPiece);

        return winningMove;
    }

    public Direction getWinningDirection(int lastMove) {

        return Direction.Down;
    }






}
