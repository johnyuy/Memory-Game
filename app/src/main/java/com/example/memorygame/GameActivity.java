package com.example.memorygame;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    ImageView[] gameImages = new ImageView[5];
    List<GridImageView> gridImageList = null;
    GridImageView[] gridImages = new GridImageView[11];
    int clickNumber = 0;
    int selection1;
    int selection2;


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
        this.gridImageList = new ArrayList<GridImageView>();
        for(int i = 1; i<=6; i++){
            String imgId = "item" + i;
            int resourceId = this.getResources().getIdentifier(imgId,"drawable", this.getPackageName());
            ImageView img = new ImageView(this);
            img.setImageResource(resourceId);
            GridImageView gimg = new GridImageView(img, i);
            this.gridImageList.add(gimg);
        }

        Log.d("GAME SYSTEM", "gameImageList Loaded");

    }

    private void initRandom(List<GridImageView> gridImageList){
        List<GridImageView> gridList = new ArrayList<GridImageView>();
        for(GridImageView img: gridImageList){
            gridList.add(img);
            GridImageView img2 = new GridImageView(img.getImg(),img.getImageCode());
            gridList.add(img2);
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
        Log.d("GAME SYSTEM", "Imported images to grid");
    }

    private void initBlanks(){
        for(int i = 0; i< gridImages.length;i++){
            //
        }
    }


    private void flipToShow(int gridIndex){


        flipIsUpStatus(gridIndex);

        //check if the current selection is first click
        if(clickNumber==1 || clickNumber==0) {
            getGridImageView(gridIndex).setClickable(false);
            selection1 = gridIndex;
            clickNumber = 2;
        } else {
            selection2 = gridIndex;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //do comparison

                    //disable all clicks
                    setAllClickable(false);
                    compareSelections();
                }
            },2000);
            //enable clickable to unsolved images only
            updateClickable();
            //reset
            clickNumber=1;
        }

        //get layout image view id
        String imgId = "img" + String.format("%02d",gridIndex);
        int resourceId = this.getResources().getIdentifier(imgId,"id", this.getPackageName());
        ImageView gridView = (ImageView) findViewById(resourceId);

        //find corresponding image
        ImageView  img = gridImages[gridIndex-1].getImg();
        gridView.setImageDrawable(img.getDrawable());
        Log.d("GAME SYSTEM", "Flipped grid number " + gridIndex + " to show image");


    }

    private void flipToHide(int gridIndex){
        updateClickable();
        flipIsUpStatus(gridIndex);
        //get layout image view id
        ImageView gridView = (ImageView) getGridImageView(gridIndex);
        //set image view to blank
        gridView.setImageResource(R.drawable.blank);

        Log.d("GAME SYSTEM", "Flipped grid number " + gridIndex + " to hide image");

    }

    private void flipIsUpStatus(int gridIndex){
        Log.d("GAME SYSTEM", "flipping");
        if(gridImages[gridIndex-1].isUp()){
            gridImages[gridIndex-1].setUp(false);
        } else {
            gridImages[gridIndex-1].setUp(true);
        }
    }

    private ImageView getGridImageView(int gridIndex){
        //get layout image view id
        String gridId = "img" + String.format("%02d",gridIndex);
        int resourceId = this.getResources().getIdentifier(gridId,"id", this.getPackageName());
        ImageView gridView = (ImageView) findViewById(resourceId);
        return  gridView;
    }

    public void flipImage(View view){
        //get the grid position index
        ImageView img = (ImageView) findViewById(view.getId());
        String name = this.getResources().getResourceName(img.getId());
        int gridIndex = Integer.parseInt(name.substring(name.length()-2));
        Log.d("GAME SYSTEM", "grid index " + gridIndex + " selected");

        //flip
        if(!gridImages[gridIndex-1].isUp()){
            flipToShow(gridIndex);

        } else {
            //flipToHide(gridIndex);
        }
    }

    public void compareSelections(){
        setAllClickable(false);
        int id1 = gridImages[selection1-1].getImageCode();
        Log.d("GAME SYSTEM", "1st image id = " + id1);
        int id2 = gridImages[selection2-1].getImageCode();
        Log.d("GAME SYSTEM", "2nd image id = " + id2);
        if(id1!=id2)
        {
            flipToHide(selection1);
            flipToHide(selection2);
        } else {
            gridImages[selection1-1].setSolved(true);
            gridImages[selection2-1].setSolved(true);
        }
        updateClickable();
    }

    private void setAllClickable(boolean clickable) {

        for (int i = 0; i < gridImages.length; i++) {
            getGridImageView(i + 1).setClickable(clickable);
            getGridImageView(i + 1).setEnabled(clickable);
        }
    }

    private void updateClickable(){
        for (int i = 0; i < gridImages.length; i++){
            if(gridImages[i].isSolved()){
                getGridImageView(i+1).setClickable(false);
                getGridImageView(i+1).setEnabled(false);
            } else {
                getGridImageView(i+1).setClickable(true);
                getGridImageView(i+1).setEnabled(true);
            }
        }
    }
}
