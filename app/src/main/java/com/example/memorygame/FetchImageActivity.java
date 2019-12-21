package com.example.memorygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class FetchImageActivity extends AppCompatActivity
        implements AsyncHtmlSourcecode.ICallback, DownloadImage.ICallback {

    private String[] imagePaths = new String[20];
    Button fetchBtn;
    EditText htmlTxt;

    String htmlcode;
    String error;
    ProgressBar bar = null;
    ProgressBar pb01 = null;
    DownloadImage downloadTask = null;


    AsyncHtmlSourcecode fetchservice = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String imagePath;
        for (int i = 1; i < imagePaths.length; i++) {
            imagePath = getFilesDir() + "/image" + i + ".jpg";
            imagePaths[i] = imagePath;
        }


        htmlTxt = (EditText) findViewById(R.id.htmlurl);
        bar = findViewById(R.id.progressBar);
        pb01 = findViewById(R.id.pb01);


        fetchBtn = findViewById(R.id.fetch);
        fetchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide keyboard
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //stop async if active
                if (fetchservice != null) {
                    fetchservice.cancel(true);
                    for (int k = 1; k < 21; k++) {
                        String ImageViewStr = "img" + String.format("%02d", k);
                        int ImagViewId = getResources().getIdentifier(ImageViewStr, "id",getPackageName());
                        ImageView iv = findViewById(ImagViewId);
                        iv.setImageResource(R.drawable.white);
                    }
                }
                if (downloadTask != null)
                    downloadTask.cancel(true);

                htmlTxt = (EditText) findViewById(R.id.htmlurl);


                htmlcode = htmlTxt.getText().toString();
                if (!URLUtil.isValidUrl(htmlcode)) {
                    error = "Please enter valid URL";
                    Toast.makeText(FetchImageActivity.this, error,
                            Toast.LENGTH_LONG).show();
                }

                Log.d("HTML", htmlcode);
                fetchservice = new AsyncHtmlSourcecode(FetchImageActivity.this);
                fetchservice.execute(htmlcode);
            }
        });
    }


    public void get20ImageUrlsDone(String[] imageUrls) {
        // download the 20 images to internal storage and display on screen
        downloadTask = new DownloadImage(new WeakReference<AppCompatActivity>(FetchImageActivity.this));
        downloadTask.execute(imageUrls);
    }

    public void get20ImgaesUrlProgress(int percent) {
        Log.d("PERCENTAGE", Integer.toString(percent));
        bar.setProgress(percent);
    }

    public void onImageProgressDone(Integer result) {
//        int resId = getResources().getIdentifier(progressbarid, "id", getPackageName());
//        ProgressBar pb = findViewById(resId);
//        pb.setVisibility(View.GONE);
    }


}

