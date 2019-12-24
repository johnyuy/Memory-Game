package com.example.memorygame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    TextView timerView;
    ImageView[] gameImages = new ImageView[5];
    List<GridImageView> gridImageList = null;
    GridImageView[] gridImages = new GridImageView[11];
    int clickNumber = 1, selection1, selection2, gameScore = 0, finalScore =0, gameLevel = 1;
    boolean result = false, start = false;
    CountUpTimer timer = null;
    int markedTime =0;
    List<String> ifrestartimgs = new ArrayList<String>();
    ImageButton menuBtn;
    ImageButton restartBtn;
    ImageButton backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        Toast.makeText(getApplicationContext(),"LEVEL " + gameLevel, Toast.LENGTH_SHORT).show();

        restartBtn = findViewById(R.id.restart);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(GameActivity.this, GameActivity.class);
                int k = 1;
                for (String path : ifrestartimgs) {
                    Log.d("FILENAMEE", path);
                    String key = Integer.toString(k);
                    intent.putExtra(key, path);
                    startActivity(intent);
                    k++;
                }
                intent.putExtra("from","self");
                startActivity(intent);
            }

        });

        backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(GameActivity.this, FetchImageActivity.class);
                intent.putExtra("from","game");
                startActivity(intent);
            }
        });


        menuBtn = findViewById(R.id.menu);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        timerView = (TextView) findViewById(R.id.timerView);
        initImages();
        initRandom(this.gridImageList);

        //Answers log
        for (GridImageView img : gridImages) {
            Log.d("FLIPDOKU", "SEQUENCE imageCode = " + img.getImageCode());
        }


    }

    private void initImages() {
        this.gridImageList = new ArrayList<GridImageView>();
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        for (int i = 1; i <= 6; i++) {
            String imagepath = b.getString(Integer.toString(i));
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);

            ifrestartimgs.add(imagepath);
            Log.d("IMAGEPATH", imagepath);
            String imgId = "item" + i;
            int resourceId = this.getResources().getIdentifier(imgId, "drawable", this.getPackageName());

            ImageView img = new ImageView(this);
            img.setImageBitmap(bitmap);

            GridImageView gimg = new GridImageView(img, i);
            this.gridImageList.add(gimg);
        }

        Log.d("FLIPDOKU", "gameImageList Loaded");

    }

    private void initRandom(List<GridImageView> gridImageList) {
        List<GridImageView> gridList = new ArrayList<GridImageView>();
        for (GridImageView img : gridImageList) {
            gridList.add(img);
            GridImageView img2 = new GridImageView(img.getImg(), img.getImageCode());
            gridList.add(img2);
        }
        Collections.shuffle(gridList);
        this.gridImages = gridList.toArray(this.gridImages);

    }

    private void initGrid() {
        List<ImageView> imgList = new ArrayList<ImageView>();

        ImageView img1 = (ImageView) findViewById(R.id.img01);
        imgList.add(img1);
        ImageView img2 = (ImageView) findViewById(R.id.img02);
        imgList.add(img2);
        ImageView img3 = (ImageView) findViewById(R.id.img03);
        imgList.add(img3);
        ImageView img4 = (ImageView) findViewById(R.id.img04);
        imgList.add(img4);
        ImageView img5 = (ImageView) findViewById(R.id.img05);
        imgList.add(img5);
        ImageView img6 = (ImageView) findViewById(R.id.img06);
        imgList.add(img6);
        ImageView img7 = (ImageView) findViewById(R.id.img07);
        imgList.add(img7);
        ImageView img8 = (ImageView) findViewById(R.id.img08);
        imgList.add(img8);
        ImageView img9 = (ImageView) findViewById(R.id.img09);
        imgList.add(img9);
        ImageView img10 = (ImageView) findViewById(R.id.img10);
        imgList.add(img10);
        ImageView img11 = (ImageView) findViewById(R.id.img11);
        imgList.add(img11);
        ImageView img12 = (ImageView) findViewById(R.id.img12);
        imgList.add(img12);

        for (int i = 0; i < imgList.size(); i++) {
            ImageView img = this.gridImages[i].getImg();
            imgList.get(i).setImageDrawable(img.getDrawable());
        }
        Log.d("FLIPDOKU", "Imported images to grid");
    }

    private void initBlanks() {
        for (int i = 1; i <= gridImages.length; i++) {
            ImageView img = getGridImageView(i);
            img.setImageResource(R.drawable.blank2);
        }
    }

    private void initGrayscale(){
        for(int i=0; i<gridImages.length;i++){
            ImageView img = gridImages[i].getImg();
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            img.setColorFilter(new ColorMatrixColorFilter(matrix));
        }
    }

    private  void initRotate(){
        for(int i= 1; i<=gridImages.length; i++) {
            ImageView imageView = getGridImageView(i);

            imageView.setRotation(i*90);
        }
    }

    private void flipToShow(int gridIndex) {
        Log.d("FLIPDOKU", "CLICK NUMBER = " + clickNumber);
        flipIsUpStatus(gridIndex);
        //get layout image view id
        String imgId = "img" + String.format("%02d", gridIndex);
        int resourceId = this.getResources().getIdentifier(imgId, "id", this.getPackageName());
        ImageView gridView = (ImageView) findViewById(resourceId);

        //find corresponding image
        ImageView img = gridImages[gridIndex - 1].getImg();
        gridView.setImageDrawable(img.getDrawable());
        Log.d("FLIPDOKU", "Flipped grid number " + gridIndex + " to show image");

        //check if the current selection is first click
        if (clickNumber == 1) {

            selection1 = gridIndex;
            getGridImageView(selection1).setEnabled(false);
            clickNumber = 2;

        } else {
            setAllEnabled(false);
            clickNumber = 1;
            selection2 = gridIndex;
            result = compareSelections();
            Log.d("FLIPDOKU", "result = " + result);
            if (result) {
                gridImages[selection1 - 1].setSolved(true);
                gridImages[selection2 - 1].setSolved(true);
                updateScore();
                updateEnabled();
            } else {
                hideSelections();
            }

        }
        printSolvedStatus();
        Log.d("FLIPDOKU", "Score = " + gameScore);
    }

    private void flipToHide(int gridIndex) {

        flipIsUpStatus(gridIndex);
        //get layout image view id
        ImageView img = (ImageView) getGridImageView(gridIndex);
        img.setImageResource(R.drawable.blank2);
        Log.d("FLIPDOKU", "Flipped grid number " + gridIndex + " to hide image");

    }

    private void flipIsUpStatus(int gridIndex) {
        if (gridImages[gridIndex - 1].isUp()) {
            gridImages[gridIndex - 1].setUp(false);
        } else {
            gridImages[gridIndex - 1].setUp(true);
        }
        Log.d("FLIPDOKU", "Grid " + gridIndex + " status = " + gridImages[gridIndex - 1].isUp());
    }

    private ImageView getGridImageView(int gridIndex) {
        //get layout image view id
        String gridId = "img" + String.format("%02d", gridIndex);
        int resourceId = this.getResources().getIdentifier(gridId, "id", this.getPackageName());
        ImageView gridView = (ImageView) findViewById(resourceId);
        return gridView;
    }

    public void updateGame(View view) {
        updateTimer();
        //get the grid position index
        ImageView img = (ImageView) findViewById(view.getId());
        String name = this.getResources().getResourceName(img.getId());
        int gridIndex = Integer.parseInt(name.substring(name.length() - 2));
        Log.d("FLIPDOKU", "grid index " + gridIndex + " selected");

        //flip
        if (!gridImages[gridIndex - 1].isUp()) {
            flipToShow(gridIndex);
        } else {
            //flipToHide(gridIndex);
        }
    }

    private void hideSelections() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flipToHide(selection1);
                flipToHide(selection2);
                updateEnabled();
            }
        }, 2000);
    }

    public boolean compareSelections() {

        int id1 = gridImages[selection1 - 1].getImageCode();
        int id2 = gridImages[selection2 - 1].getImageCode();
        Log.d("FLIPDOKU", "comparing grid " + selection1 + " and grid " + selection2);
        if (id1 == id2)
            return true;
        return false;
    }

    private void setAllEnabled(boolean enabled) {

        for (int i = 0; i < gridImages.length; i++) {
            getGridImageView(i + 1).setEnabled(enabled);
        }
    }

    private void updateEnabled() {
        for (int i = 0; i < gridImages.length; i++) {
            if (gridImages[i].isSolved()) {
                getGridImageView(i + 1).setEnabled(false);
            } else {
                getGridImageView(i + 1).setEnabled(true);
            }
        }
    }

    private void printSolvedStatus() {
        Log.d("FLIPDOKU", "Solved Statuses");
        for (int i = 0; i < gridImages.length; i++) {
            int j = i + 1;
            Log.d("FLIPDOKU", "Grid " + j + " = " + gridImages[i].isSolved());
        }
        Log.d("FLIPDOKU", " ");
    }

    public abstract class CountUpTimer extends CountDownTimer {
        private static final long INTERVAL_MS = 1000;
        private final long duration;

        protected CountUpTimer(long durationMs) {
            super(durationMs, INTERVAL_MS);
            this.duration = durationMs;
        }

        public abstract void onTick(int second);

        @Override
        public void onTick(long msUntilFinished) {
            int second = (int) ((duration - msUntilFinished) / 1000);
            onTick(second);
        }

        @Override
        public void onFinish() {
            onTick(duration / 1000);
        }


    }

    public void updateScore() {
        int score = 0;
        int elapsedTime = Integer.parseInt(timerView.getText().toString()) - markedTime;
        Log.d("FLIPDUKO", String.format("Elapsed Time = " + timerView.getText().toString()));
        markedTime = Integer.parseInt(timerView.getText().toString());

        for (int i = 0; i < gridImages.length; i++) {
            if (gridImages[i].isSolved()) {
                score++;
            }
        }
        score /= 2;
        TextView scoretext = (TextView) findViewById(R.id.scoreView);
        scoretext.setText("score : " + score + "/6");
        gameScore = score;



        if (gameScore == gameImages.length + 1) {
            Log.d("FLIPDOKU", "STOP LEVEL" + gameScore);
            //
            if(gameLevel<3){
                finalScore += gameScore;
                gameScore=0;
                nextLevel();
            } else {
                timer.cancel();
                //registerScore();
                Toast.makeText(getApplicationContext(),"Well Done!", Toast.LENGTH_LONG).show();
                ///ADD GAME SCORE HERE (JOHANN)
                compareHighScores("1000000");

                //Back to main menu
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }

    }

    public void updateTimer() {
        if (!start) {
            start = true;
            timer = new CountUpTimer(999999999) {
                public void onTick(int second) {
                    timerView.setText(String.valueOf(second));
                }
            };
            timer.start();
        }
    }

    public void nextLevel(){
        gameLevel ++;

        Toast.makeText(getApplicationContext(),"LEVEL " + gameLevel, Toast.LENGTH_SHORT).show();

        for(int i = 0; i <gridImages.length; i++){
            gridImages[i].setSolved(false);
            gridImages[i].setUp(false);
        }

        initRandom(gridImageList);

        if(gameLevel>1)
            initGrayscale();

        if(gameLevel>2)
            initRotate();

        initBlanks();
        setAllEnabled(true);
    }



    public void compareHighScores(String newscore) {
        ArrayList<HighScore> highscores = new ArrayList<HighScore>();
        // get array of high score
        SharedPreferences sphighscore = getSharedPreferences(
                "high_score", MODE_PRIVATE);
        String playername = sphighscore.getString("newplayer", "");
        Log.d("PLAYERNAME", playername);
        for (int i = 1; i < 6; i++) {
            String highscorekey = "HIGHSCORE_" + i;
            String highscore = sphighscore.getString(highscorekey, "");
            String namekey = "NAME_" + i;
            String topplayername = sphighscore.getString(namekey, "");
            highscores.add(new HighScore(topplayername, Integer.valueOf(highscore)));
        }
        highscores.add(new HighScore(playername, Integer.valueOf(newscore)));
        Log.d("highscores123", Integer.toString(highscores.size()));

        HighScore[] hsa = highscores.toArray(new HighScore[highscores.size()]);
        Arrays.sort(hsa, Collections.reverseOrder());
        for (HighScore hs : hsa) {
            System.out.println(hs.getName() + " : " + hs.getHighscore());
        }
        ArrayList<HighScore> topfive = new ArrayList<HighScore>();
        for (int i = 0; i < 5; i++) {
            topfive.add(hsa[i]);
        }
        Log.d("topfive", Integer.toString(topfive.size()));
        saveHighScores(topfive);
    }

    public void saveHighScores(ArrayList<HighScore> topfive) {
        SharedPreferences highscore = getSharedPreferences(
                "high_score", MODE_PRIVATE);
        final SharedPreferences.Editor editor = highscore.edit();
        int i = 1;
        for (HighScore hs : topfive) {
            String highscorekey = "HIGHSCORE_" + i;
            editor.putString(highscorekey, hs.getHighscore().toString());
            String namekey = "NAME_" + i;
            editor.putString(namekey, hs.getName());
            i++;
        }
        editor.commit();

        for (HighScore hs : topfive) {
            Log.d("TOPFIVEHIGH", hs.getName() + " : " + hs.getHighscore().toString());
        }
    }

}
