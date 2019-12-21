package com.example.memorygame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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

    // FOR CHOOSING IMAGES
    List<File> selectedImages = new ArrayList<File>();
    List<ImageForSelection> fullImageReference = new ArrayList<ImageForSelection>();

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
                if (bar.getVisibility() == View.INVISIBLE) {
                    bar.setVisibility(View.VISIBLE);
                }

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
                        int ImagViewId = getResources().getIdentifier(ImageViewStr, "id", getPackageName());
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
                initGridReferences();
            }
        });
    }

    /// CHOOSE IMAGES


    public void initGridReferences() {
        for (int i = 1; i < 21; i++) {
            String name = "img" + String.format("%02d", i);
            int resourceId = this.getResources().getIdentifier(name, "id", this.getPackageName());
            ImageView imageview = (ImageView) findViewById(resourceId);
            ImageForSelection imageForSelection = new ImageForSelection(imageview);
            fullImageReference.add(imageForSelection);
        }
    }


    public void selectedImage(View v) {
        ImageView selectedview = (ImageView) findViewById(v.getId());
        ImageForSelection selected = new ImageForSelection();
        int alreadyselected = 0;

        // selected number 6 cannot return
        for (ImageForSelection ifs : fullImageReference) {
            if (ifs.getImg() == selectedview) {
                ifs.setSelected(!ifs.isSelected);
                if (selectedview.getAlpha() == 0.3f) {
                    selectedview.setAlpha(1.0f);
                }else {
                    selectedview.setAlpha(0.3f);
                }
            }
        }

        for (ImageForSelection ifs : fullImageReference) {
            if (ifs.isSelected) {
                alreadyselected = alreadyselected + 1;
                if (alreadyselected > 6) {
                    for (ImageForSelection ifschange : fullImageReference) {
                        if (ifs.getImg() == selectedview) {
                            ifs.setSelected(!ifs.isSelected);
                            selectedview.setAlpha(1.0f);
                            return;
                        }
                    }

                }
            }
        }


        String imgPath = this.getResources().getResourceName(v.getId());
        int imageIndex = Integer.parseInt(imgPath.substring(imgPath.length() - 2));
        int selectednumber = 0;
        for (ImageForSelection ifs : fullImageReference) {
            if (ifs.isSelected) {
                selectednumber = selectednumber + 1;
                selectedImages.add(ifs.getUrlOfImg(v));
                Log.d("INCREMENT?", Integer.toString(selectednumber));
                if (selectednumber == 6) {
                    Log.d("YAY ", "NEXT BUTTON");

                    for (File f : selectedImages) {
                        String print = f.toString();
                        Log.d("FILENAMe", print);
                        Intent intent = new Intent(FetchImageActivity.this, GameActivity.class);
                        for (int i = 1; i < 7; i++) {
                            File file = new File(getFilesDir() + "/image" + i + ".jpg");
                            String key = Integer.toString(i);
                            intent.putExtra(key, file);
                        }
                        startActivity(intent);

                    }

                    //next button will appear

                    //


//                    int k=0;
//                    for(int j = 0; j<fullImageReference.size(); j++){
//
//                        if (fullImageReference.get(j).isSelected){
//                            k=k+1;
//                            File file = new File(getFilesDir() + "/image" + Integer.toString(k) + ".jpg");
//                            selectedImages.add(file);
//                            Intent intent = new Intent(FetchImageActivity.this, GameActivity.class);
//                            startActivity(intent);
//                        }
//
//                    }

                }
            }
        }
        Log.d("END", Integer.toString(selectednumber));

    }

    // CLASS
    public class ImageForSelection {
        private ImageView img;
        private boolean isSelected = false;

        public ImageForSelection() {
        }

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

        public File getUrlOfImg(View v) {
            String imgPath = v.getResources().getResourceName(v.getId());
            int imageIndex = Integer.parseInt(imgPath.substring(imgPath.length() - 2));

            Log.d("IMAGE VIEW ID ", Integer.toString(imageIndex));

            File file = new File(getFilesDir() + "/image" + imageIndex + ".jpg");

            return file;
        }
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

