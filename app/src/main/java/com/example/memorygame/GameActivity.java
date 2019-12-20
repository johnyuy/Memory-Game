package com.example.memorygame;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    ImageView[] gameImages = new ImageView[5];
    List<GridImageView> gridImageList = null;
    GridImageView[] gridImages = new GridImageView[11];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

//        ImageView img1 = new ImageView(this);
//        img1.setImageResource(R.drawable.item1);
        initImages();
        initRandom(this.gridImageList);
        for(GridImageView img:gridImages){
            Log.d("INIT STATUS", "SEQUENCE imageCode = " + img.getImageCode());
        }
        initBlanks();
        //initGrid();

    }

    private void initImages(){

        ImageView gameImage1 = new ImageView(this);
        gameImage1.setImageResource(R.drawable.item1);
        GridImageView gridImageView1 = new GridImageView(gameImage1, 1);

        ImageView gameImage2 = new ImageView(this);
        gameImage2.setImageResource(R.drawable.item2);
        GridImageView gridImageView2 = new GridImageView(gameImage2, 2);

        ImageView gameImage3 = new ImageView(this);
        gameImage3.setImageResource(R.drawable.item3);
        GridImageView gridImageView3 = new GridImageView(gameImage3, 3);

        ImageView gameImage4 = new ImageView(this);
        gameImage4.setImageResource(R.drawable.item4);
        GridImageView gridImageView4 = new GridImageView(gameImage4, 4);
        
        ImageView gameImage5 = new ImageView(this);
        gameImage5.setImageResource(R.drawable.item5);
        GridImageView gridImageView5 = new GridImageView(gameImage5, 5);
        
        ImageView gameImage6 = new ImageView(this);
        gameImage6.setImageResource(R.drawable.item6);
        GridImageView gridImageView6 = new GridImageView(gameImage6, 6);
        
        this.gridImageList = new ArrayList<GridImageView>();
        this.gridImageList.add(gridImageView1);
        this.gridImageList.add(gridImageView2);
        this.gridImageList.add(gridImageView3);
        this.gridImageList.add(gridImageView4);
        this.gridImageList.add(gridImageView5);
        this.gridImageList.add(gridImageView6);
        Log.d("INIT STATUS:", "gameImageList Loaded");

    }

    private void initRandom(List<GridImageView> gridImageList){
        List<GridImageView> gridList = new ArrayList<GridImageView>();
        for(GridImageView img: gridImageList){
            gridList.add(img);
            gridList.add(img);
        }
        Collections.shuffle(gridList);
        this.gridImages = gridList.toArray(this.gridImages);

    }

    private void initGrid(){
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

        for(int i = 0; i<imgList.size(); i++){
            ImageView img = this.gridImages[i].getImg();
            imgList.get(i).setImageDrawable(img.getDrawable());
        }
        Log.d("INIT STATUS:", "Imported images to grid");
    }

    private void initBlanks(){

    }
}
