package com.example.connectfour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


    }

    public void goToTwoPlayerMenu(View view) {
        Intent intent = new Intent(this, TwoPlayerActivity.class);
        startActivity(intent);
    }
    public void goToOpponentMenu(View view) {
        Intent intent = new Intent(this, OpponentActivity.class);
        startActivity(intent);
    }

    public void goToGame(View view) {
        Intent intent = new Intent(this, MainGameActivity.class);
        startActivity(intent);
    }

    public void goToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}