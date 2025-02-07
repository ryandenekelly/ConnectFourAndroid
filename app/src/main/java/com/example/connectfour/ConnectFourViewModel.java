package com.example.connectfour;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.connectfour.game.Board;
import com.example.connectfour.game.Game;
import com.example.connectfour.game.Player;

public class ConnectFourViewModel extends ViewModel {
    protected Game game;

    public void init(String gameType, String opponentType, String playerOneName, String playerTwoName) {

        game = new Game();

        switch(gameType) {
            case "OnePlayer":
                game.addUserPlayer(playerOneName);
                game.addComputerPlayer(opponentType);
                game.setGameType(Game.GameType.OnePlayer);
                break;

            case "TwoPlayer":
                game.addUserPlayer(playerOneName);
                game.addUserPlayer(playerTwoName);
                game.setGameType(Game.GameType.TwoPlayer);
                break;

            default:
                break;
        }
        game.initCurrentPlayer();

    }

    public void onBoardUpdate(int column) {

        if(!game.getBoard().checkMove(column)) {
            return;
        }

        if(game.hasGameEnded(column)) {
            game.winner.setValue(game.getCurrentPlayer());
        }
        else {

            if(game.getGameType() == Game.GameType.TwoPlayer) {
                game.changeTurn();
            }
            else {
                game.changeTurn();
                game.opponentMove.setValue(game.opponentMove.getValue() == null ? true : !game.opponentMove.getValue());
            }
        }
    }

    public LiveData<Player> getWinner() {
        return game.winner;
    }

    public LiveData <Boolean> isOpponentMove() {
        return game.opponentMove;
    }
}
