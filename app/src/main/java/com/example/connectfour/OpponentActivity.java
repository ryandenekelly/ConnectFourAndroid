package com.example.connectfour;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class OpponentActivity extends AppCompatActivity {
    String playerOneName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponent_menu);

    }

    public void onClickDumb(View view) {
        Intent intent = new Intent(this, MainGameActivity.class);

        EditText playerOneEdit = (EditText) findViewById(R.id.editPlayerOneNameOpponent);
        playerOneName = playerOneEdit.getText().toString();

        intent.putExtra("GAME_TYPE", "OnePlayer");
        intent.putExtra("OPPONENT_TYPE", "Dumb");
        intent.putExtra("PLAYER_ONE_NAME", playerOneName);
        intent.putExtra("PLAYER_TWO_NAME", "None");
        startActivity(intent);
    }

    public void onClickSmarter(View view) {
        Intent intent = new Intent(this, MainGameActivity.class);

        EditText playerOneEdit = (EditText) findViewById(R.id.editPlayerOneNameOpponent);
        playerOneName = playerOneEdit.getText().toString();

        intent.putExtra("GAME_TYPE", "OnePlayer");
        intent.putExtra("OPPONENT_TYPE", "Smarter");
        intent.putExtra("PLAYER_ONE_NAME", playerOneName);
        intent.putExtra("PLAYER_TWO_NAME", "None");
        startActivity(intent);
    }
}
