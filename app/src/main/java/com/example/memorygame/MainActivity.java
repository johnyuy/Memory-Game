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
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);

                for (int i = 1; i < 7; i++) {
                    File file = new File(getFilesDir() + "/image" + i + ".jpg");
                    String key = Integer.toString(i);
                    intent.putExtra(key, file);
                }
                startActivity(intent);
            }
        });

        game = findViewById(R.id.display);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(intent);
            }
        });
    }


    public class ImageForSelection {
        private ImageView img;
        private boolean isSelected = false;

        public ImageForSelection(ImageView img) {
            this.img = img;
            this.isSelected = false;
        }

        public ImageView getImg() {
            return img;
        }

        public void setImg(ImageView img) {
            this.img = img;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }


    }

    public void initGridReferences() {
        for (int i = 1; i < 21; i++) {
            String name = "f" + String.format("%02d", i);
            int resourceId = this.getResources().getIdentifier(name, "id", this.getPackageName());
            ImageView imageview = (ImageView) findViewById(resourceId);
            ImageForSelection imageForSelection = new ImageForSelection(imageview);
            fullImageReference.add(imageForSelection);

        }
    }


    public void selectedImage(View v) {
        ImageView selectedview = (ImageView) findViewById(v.getId());
        String imgPath = this.getResources().getResourceName(v.getId());
        int imageIndex = Integer.parseInt(imgPath.substring(imgPath.length() - 2));
        int clickcount = 0;
        for (int i = 0; i < fullImageReference.size(); i++) {
            int truecounter = 0;
            if (fullImageReference.get(i).isSelected) {
                truecounter = truecounter + 1;
                if (truecounter == 6) {
                    int k = 0;
                    for (int j = 0; j < fullImageReference.size(); j++) {

                        if (fullImageReference.get(j).isSelected) {
                            k = k + 1;
                            File file = new File(getFilesDir() + "/image" + Integer.toString(k) + ".jpg");
                            selectedImages.add(file);
                            playGame();
                        }

                    }

                }
            }
        }

    }

    public void playGame(){
        Button btn = findViewById(R.id.playBtn);
        if (btn.getVisibility() == View.INVISIBLE) {
            btn.setVisibility(View.VISIBLE);
        }
        //set background alpha to 0.3f

        if(btn != null){
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
