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
import java.util.List;

public class MainActivity extends AppCompatActivity {




    EditText playerName;
    Button game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //

        // try initiating highscores
        SharedPreferences highscore = getSharedPreferences(
                "high_score", MODE_PRIVATE);
        final SharedPreferences.Editor editor = highscore.edit();
        for(int i = 1; i<7; i++) {
            editor.putString(Integer.toString(i), Integer.toString(i * 120));
            String playertop = "TOP_" + i;
            String playername = "Player " + i;
            editor.putString(playertop, playername);
        }
        editor.commit();

        initHighScore();

        game = findViewById(R.id.playBtn);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HERER", "onclick");

                SharedPreferences highscore = getSharedPreferences(
                        "high_score", MODE_PRIVATE);
                final SharedPreferences.Editor editor = highscore.edit();
                playerName = (EditText) findViewById(R.id.playername);
                String playerNameStr = playerName.getText().toString();
                Log.d("ENTERNAME", playerNameStr);
                editor.putString("newplayer", playerNameStr);
                editor.commit();

                Log.d("HERER", "intent");

                Intent intent = new Intent(MainActivity.this, FetchImageActivity.class);
                Log.d("HERER", "intent2");

                startActivity(intent);
            }
        });

    }

    public void initHighScore(){

        // try initiating highscores
        SharedPreferences sphighscore = getSharedPreferences(
                "high_score", MODE_PRIVATE);

        for(int i = 1; i<6; i++) {

            String resscore = "hscore" + Integer.toString(i);
            int rescoreId = this.getResources().getIdentifier(resscore, "id", this.getPackageName());
            TextView textViewhs = (TextView) findViewById(rescoreId);
            String highscores = sphighscore.getString(Integer.toString(i), "");
            textViewhs.setText(highscores);

            String resname = "hsname" + Integer.toString(i);
            int resId = this.getResources().getIdentifier(resname, "id", this.getPackageName());
            TextView textViewname = (TextView) findViewById(resId);
            String playertop = "TOP_" + i;
            String playername = sphighscore.getString(playertop, "");
            textViewname.setText(playername);
        }



    }
}
