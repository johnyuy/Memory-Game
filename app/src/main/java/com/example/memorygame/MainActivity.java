package com.example.memorygame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button fetch;
    Button game;
    List<File> selectedImages = new ArrayList<File>();
    List<ImageForSelection> fullImageReference = new ArrayList<ImageForSelection>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //




        fetch = findViewById(R.id.fetch);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FetchImageActivity.class);
                startActivity(intent);
            }
        });

        game = findViewById(R.id.game);
        game.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        game = findViewById(R.id.display);
        game.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(intent);
            }
        });
    }



    public class ImageForSelection {
        private ImageView img;
        private  boolean isSelected = false;

        public ImageForSelection (ImageView img){
            this.img = img;
            this.isSelected = false;
        }
    }

    public void initGridReferences (){
        for(int i = 1; i<21; i++){
            String name = "f" + String.format("%02d", i);
            int resourceId = this.getResources().getIdentifier(name,"id", this.getPackageName());
            ImageView  imageview = (ImageView) findViewById(resourceId);
            ImageForSelection imageForSelection = new ImageForSelection(imageview);
            fullImageReference.add(imageForSelection);
            
        }
    }


    public void selectedImage(View v){
        //int selectedId = v.getId();
        ImageView selectedview = (ImageView)findViewById(v.getId());
        //selectedImages.add(selectedview);

//        selectedviewif(click ==1){
//
//        }
//        File file = openFileInput("image" + selectedId + ".jpg");
//
//
//        selectedSix.add(file);
//        if(selectedSix.size() == 6){
//            Intent intent = new Intent(MainActivity.this, GameActivity.class);
//            startActivity(intent);
//        }

        //File file = new File(getFilesDir() + "/image" + Integer.toString(selectedId) + ".jpg");








    }
}
