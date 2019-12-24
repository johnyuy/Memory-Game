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

        if(!sharedPrefs.contains("initialized")){
            initTestHighScores();
        }
        //save player name
        savePlayerName();
        //init High Scores
        initTestHighScores();
        //Display High Scores
        initHighScore();
        // test
//        compareHighScores("100000");

        game = findViewById(R.id.playBtn);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FetchImageActivity.class);
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
        for (int i = 1; i < 7; i++) {
            String highscorekey = "HIGHSCORE_" + i;
            editor.putString(highscorekey, Integer.toString(i * 120));
            String namekey = "NAME_" + i;
            String playername = "Player " + i;
            editor.putString(namekey, playername);
        }
        editor.putString("initialized", "true");

        editor.commit();
    }

//    public void compareHighScores(String newscore) {
//        ArrayList<HighScore> highscores = new ArrayList<HighScore>();
//        // get array of high score
//        SharedPreferences sphighscore = getSharedPreferences(
//                "high_score", MODE_PRIVATE);
//        String playername = sphighscore.getString("newplayer", "");
//        Log.d("PLAYERNAME", playername);
//        for (int i = 1; i < 6; i++) {
//            String highscorekey = "HIGHSCORE_" + i;
//            String highscore = sphighscore.getString(highscorekey, "");
//            String namekey = "NAME_" + i;
//            String topplayername = sphighscore.getString(namekey, "");
//            highscores.add(new HighScore(topplayername, Integer.valueOf(highscore)));
//        }
//        highscores.add(new HighScore(playername, Integer.valueOf(newscore)));
//        Log.d("highscores123", Integer.toString(highscores.size()));
//
//        HighScore[] hsa = highscores.toArray(new HighScore[highscores.size()]);
//        Arrays.sort(hsa, Collections.reverseOrder());
//        for (HighScore hs : hsa) {
//            System.out.println(hs.getName() + " : " + hs.getHighscore());
//        }
//        ArrayList<HighScore> topfive = new ArrayList<HighScore>();
//        for (int i = 0; i < 5; i++) {
//            topfive.add(hsa[i]);
//        }
//        Log.d("topfive", Integer.toString(topfive.size()));
//        saveHighScores(topfive);
//    }
//
//    public void saveHighScores(ArrayList<HighScore> topfive) {
//        SharedPreferences highscore = getSharedPreferences(
//                "high_score", MODE_PRIVATE);
//        final SharedPreferences.Editor editor = highscore.edit();
//        int i = 1;
//        for (HighScore hs : topfive) {
//            String highscorekey = "HIGHSCORE_" + i;
//            editor.putString(highscorekey, hs.getHighscore().toString());
//            String namekey = "NAME_" + i;
//            editor.putString(namekey, hs.getName());
//            i++;
//        }
//        editor.commit();
//
//        for (HighScore hs : topfive) {
//            Log.d("TOPFIVEHIGH", hs.getName() + " : " + hs.getHighscore().toString());
//        }
//    }

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
