package com.example.memorygame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    EditText playerName;
    Button game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPrefs = getSharedPreferences(
                "high_score", MODE_PRIVATE);

        //init High Scores if new install
        if(!sharedPrefs.contains("initialized")){
            initTestHighScores();
        }
//        //save player name
//        savePlayerName();
        //Display High Scores
        initHighScore();

        game = findViewById(R.id.playBtn);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FetchImageActivity.class);
                savePlayerName();
                startActivity(intent);
            }
        });

    }

    public void initHighScore() {
        // try initiating highscores
        SharedPreferences sphighscore = getSharedPreferences(
                "high_score", MODE_PRIVATE);

        for (int i = 1; i < 6; i++) {
            String resscore = "hscore" + Integer.toString(i);
            int rescoreId = this.getResources().getIdentifier(resscore, "id", this.getPackageName());
            TextView textViewhs = (TextView) findViewById(rescoreId);
            String highscorekey = "HIGHSCORE_" + i;
            String highscores = sphighscore.getString(highscorekey, "");
            textViewhs.setText(highscores);
            String namekey = "NAME_" + i;
            String resname = "hsname" + Integer.toString(i);
            int resId = this.getResources().getIdentifier(resname, "id", this.getPackageName());
            TextView textViewname = (TextView) findViewById(resId);
            String playername = sphighscore.getString(namekey, "");
            textViewname.setText(playername);
        }
    }

    public void initTestHighScores() {
        SharedPreferences highscore = getSharedPreferences(
                "high_score", MODE_PRIVATE);
        final SharedPreferences.Editor editor = highscore.edit();
        for (int i = 1; i < 6; i++) {
            String highscorekey = "HIGHSCORE_" + i;
            editor.putString(highscorekey, Integer.toString(0));
            String namekey = "NAME_" + i;
            String playername = "Player " + i;
            editor.putString(namekey, playername);
        }
        editor.putString("initialized", "true");

        editor.commit();
    }

    public void savePlayerName() {
        SharedPreferences highscore = getSharedPreferences(
                "high_score", MODE_PRIVATE);
        final SharedPreferences.Editor editor = highscore.edit();
        playerName = (EditText) findViewById(R.id.playername);
        String playerNameStr = playerName.getText().toString();
        Log.d("ENTERNAME", playerNameStr);
        editor.putString("newplayer", playerNameStr);
        editor.commit();
    }
}
