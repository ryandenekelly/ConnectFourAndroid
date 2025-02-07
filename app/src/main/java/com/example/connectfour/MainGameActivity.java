package com.example.connectfour;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.connectfour.game.Player;

public class MainGameActivity extends AppCompatActivity {

    protected String gameType;
    protected String opponentType;
    protected String playerOneName;
    protected String playerTwoName;

    ConnectFourViewModel gameViewModel;

    TextView messageDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        messageDisplay = (TextView) findViewById(R.id.messageDisplay);

        Intent intent = getIntent();

        // get players from intents
        gameType = intent.getStringExtra("GAME_TYPE");
        opponentType = intent.getStringExtra("OPPONENT_TYPE");
        playerOneName = intent.getStringExtra("PLAYER_ONE_NAME");
        playerTwoName = intent.getStringExtra("PLAYER_TWO_NAME");

        gameViewModel = new ConnectFourViewModel();
        gameViewModel.init(gameType, opponentType,
                playerOneName.equals("") ? "PlayerOne" : playerOneName,
                    playerTwoName.equals("") ? "PlayerTwo" : playerTwoName);

        setUpOnGameWinnerListener();
        setUpOnOpponentMoveListener();


    }

    public void onClickHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickPlayAgain(View view){
        finish();
        startActivity(getIntent());
    }

    public void onGameWinner(Player winner) {

        ConnectFourBoard connectFourBoard = (ConnectFourBoard) this.findViewById(R.id.connectFourBoard);
        connectFourBoard.enableClick = false;
        if(gameViewModel.game.getBoard().isBoardFull()) {
            messageDisplay.setText("Game drawn!");
        }
        else {
            messageDisplay.setText(winner.getPlayerName() + " wins!");
        }
    }

    private void setUpOnGameWinnerListener() {
        gameViewModel.getWinner().observe(this, this::onGameWinner);
    }

    private void setUpOnOpponentMoveListener() {
        gameViewModel.isOpponentMove().observe(this, this::onOpponentMove);
    }
    public void onOpponentMove(Boolean bool) {

        if(bool) {
            ConnectFourBoard connectFourBoard = (ConnectFourBoard) this.findViewById(R.id.connectFourBoard);
            connectFourBoard.setAlpha(.5f);
            // connectFourBoard.invalidate();


            int move = gameViewModel.game.getCurrentPlayer().getMove(gameViewModel.game.getBoard());
            gameViewModel.onBoardUpdate(move);

            connectFourBoard.setAlpha(1.0f);
            // connectFourBoard.invalidate();

        }
    }



}

