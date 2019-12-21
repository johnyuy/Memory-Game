package com.example.memorygame;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class FetchImageActivity extends AppCompatActivity
        implements MyBoundService.IServiceCallback, AsyncHtmlSourcecode.ICallback {

    private String[] imagePaths = new String[19];
    Button fetchBtn;
    EditText htmlTxt;
    TextView errorTxt;
    int ipublic = 0;

    String htmlcode;
    String error;

    MyBoundService svc = null;
    ProgressBar bar = null;
    ImageView imgView = null;
    String sourcecode = "";
    String[] imagesurls;
    ArrayList<String> ivarray;

    AsyncHtmlSourcecode fetchservice = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String imagePath;
        for (int i=1; i<imagePaths.length; i++) {
            imagePath = getFilesDir() + "/image" + Integer.toString(i) + ".jpg";
            imagePaths[i] = imagePath;
        }


        htmlTxt = (EditText) findViewById(R.id.htmlurl);
        bar = findViewById(R.id.progressBar);


        fetchBtn = findViewById(R.id.get20images);
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

                //get html url
                htmlcode = htmlTxt.getText().toString();
                if (!URLUtil.isValidUrl(htmlcode)) {
                    error = "Please enter valid URL";
                    Toast.makeText(FetchImageActivity.this, error,
                            Toast.LENGTH_LONG).show();
                }
                Log.d("HTML", htmlcode);

                //stop async if active
                if (fetchservice != null) {
                    fetchservice.cancel(true);
                }
                if (svc != null){
                    svc.stopSelf();
                }
                new AsyncHtmlSourcecode(FetchImageActivity.this).execute(htmlcode);
            }
        });
    }

    protected void initService() {
        Intent intent = new Intent(this, MyBoundService.class);
        bindService(intent, svcConn, BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceProgress(int percent, int k) {
        System.out.println("percent: " + percent);


        // can be user for individual images
        bar.setProgress(percent);
    }

    @Override
    public void onServiceDone(final int x) {


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.d("run", Integer.toString(ipublic));

                int k = ipublic +1;
                File file = new File(getFilesDir() + "/image" + Integer.toString(k) + ".jpg");

                try {
                    // to display the image on screen
                    String imageId = "img" + String.format("%02d",k);
                    int resId = getResources().getIdentifier(imageId, "id", getPackageName());
                    ImageView imageView = findViewById(resId);

                    Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));

                    imageView.setImageBitmap(bitmap);

                }catch(Exception e){
                    e.printStackTrace();

                }
                ipublic = ipublic + 1;
            }

        });
        if (ipublic > 20){
            svc.stopSelf();
            bar.setVisibility(View.GONE);
            ipublic = 0;
        }
    }

    private ServiceConnection svcConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBoundService.LocalBinder binder = (MyBoundService.LocalBinder) service;
            if (binder != null) {
                svc = binder.getService();
                if (svc != null) {
                    try {
                        for (int i = 0; i< 20; i++) {
                            int percent = i * 5;
                            int k = i + 1;

                            File file = new File(getFilesDir() + "/image" + k + ".jpg");
                            svc.downloadImage(FetchImageActivity.this, imagesurls[i], file.toString(), k);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        @Override
        public void onServiceDisconnected(ComponentName name) {
            svc = null;
        }
    };

    public void get20ImageUrlsDone(String[] imagesurl) {

        this.imagesurls = imagesurl;

        initService();
    }

}