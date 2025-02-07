package com.example.connectfour;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class TwoPlayerActivity extends AppCompatActivity {
    String playerOneName;
    String playerTwoName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_menu);




    }

    public void goToGame(View view) {
        EditText playerOneEdit = (EditText) findViewById(R.id.editPlayerOneName);
        EditText playerTwoEdit = (EditText) findViewById(R.id.editPlayerTwoName);
        playerOneName = playerOneEdit.getText().toString();
        playerTwoName = playerTwoEdit.getText().toString();

        Intent intent = new Intent(this, MainGameActivity.class);
        intent.putExtra("GAME_TYPE", "TwoPlayer");
        intent.putExtra("OPPONENT_TYPE", "None");
        intent.putExtra("PLAYER_ONE_NAME", playerOneName);
        intent.putExtra("PLAYER_TWO_NAME", playerTwoName);
        startActivity(intent);
    }
}
