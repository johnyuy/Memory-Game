package com.example.memorygame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    ArrayList<ImageView> ivarray = new ArrayList<ImageView>();
    List<GridImageView> gridImageList = null;
    GridImageView[] gridImages = new GridImageView[19];
    GridImageView gview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ivarray = initImageView();
        initImages(ivarray);

    }

    private void initImages(ArrayList<ImageView> ivarray) {
        try {
            int i = 1;
            for (ImageView iv : ivarray) {
                File file = new File(getFilesDir() + "/image" + i + ".jpg");

                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));

                Bitmap resizedbitmap1 = Bitmap.createBitmap(bitmap, 30, 20, 120, 120);

                iv.setImageBitmap(resizedbitmap1);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<ImageView> initImageView() {
        ArrayList<ImageView> ivarray = new ArrayList<ImageView>();

        ImageView img1 = (ImageView) findViewById(R.id.img01);
        ivarray.add(img1);
        ImageView img2 = (ImageView) findViewById(R.id.img02);
        ivarray.add(img2);
        ImageView img3 = (ImageView) findViewById(R.id.img03);
        ivarray.add(img3);
        ImageView img4 = (ImageView) findViewById(R.id.img04);
        ivarray.add(img4);
        ImageView img5 = (ImageView) findViewById(R.id.img05);
        ivarray.add(img5);
        ImageView img6 = (ImageView) findViewById(R.id.img06);
        ivarray.add(img6);
        ImageView img7 = (ImageView) findViewById(R.id.img07);
        ivarray.add(img7);
        ImageView img8 = (ImageView) findViewById(R.id.img08);
        ivarray.add(img8);
        ImageView img9 = (ImageView) findViewById(R.id.img09);
        ivarray.add(img9);
        ImageView img10 = (ImageView) findViewById(R.id.img10);
        ivarray.add(img10);
        ImageView img11 = (ImageView) findViewById(R.id.img11);
        ivarray.add(img11);
        ImageView img12 = (ImageView) findViewById(R.id.img12);
        ivarray.add(img12);
        ImageView img13 = (ImageView) findViewById(R.id.img13);
        ivarray.add(img13);
        ImageView img14 = (ImageView) findViewById(R.id.img14);
        ivarray.add(img14);
        ImageView img15 = (ImageView) findViewById(R.id.img15);
        ivarray.add(img15);
        ImageView img16 = (ImageView) findViewById(R.id.img16);
        ivarray.add(img16);
        ImageView img17 = (ImageView) findViewById(R.id.img17);
        ivarray.add(img17);
        ImageView img18 = (ImageView) findViewById(R.id.img18);
        ivarray.add(img18);
        ImageView img19 = (ImageView) findViewById(R.id.img19);
        ivarray.add(img19);
        ImageView img20 = (ImageView) findViewById(R.id.img20);
        ivarray.add(img20);

        return ivarray;
    }
}
