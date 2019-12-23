package com.example.memorygame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class FetchImageActivity extends AppCompatActivity
        implements AsyncHtmlSourcecode.ICallback, DownloadImage.ICallback {

    private String[] imagePaths = new String[21];
    Button fetchBtn;


    Button dogBtn;
    Button catBtn;
    Button bearBtn;
    Button natureBtn;
    Button beachBtn;
    Button playbtn;
    EditText htmlTxt;
    String stocksnap;
    String htmlcode;
    String error;
    ProgressBar bar = null;
    ProgressBar pb01 = null;
    DownloadImage downloadTask = null;


    AsyncHtmlSourcecode fetchservice = null;

    // FOR CHOOSING IMAGES
    List<String> selectedImages = new ArrayList<String>();
    List<ImageForSelection> fullImageReference = new ArrayList<ImageForSelection>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        SharedPreferences sphighscore = getSharedPreferences(
                "high_score", MODE_PRIVATE);
        String playername = sphighscore.getString("newplayer","");
        Log.d("PLAYERNAME", playername);
        stocksnap = this.getResources().getString(R.string.webUrl);

        String imagePath;
        for (int i = 1; i < imagePaths.length; i++) {
            imagePath = getFilesDir() + "/image" + i + ".jpg";
            imagePaths[i] = imagePath;
        }

        // IF INTENT FROM GAME ACTIVITY
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            if (b.getString("from").equals("game")) {
                //DISPLAY PREVIOUS SEARCH
                fromGameReload();

            }
        }


        htmlTxt = (EditText) findViewById(R.id.htmlurl);
        bar = findViewById(R.id.progressBar);
        pb01 = findViewById(R.id.pb01);

        initGridReferences();
        playbtn = (Button)findViewById(R.id.playBtn);
        playbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                for (ImageForSelection si : fullImageReference) {
                    if (si.isSelected) {
                        Log.d("FILENAMELVL1", si.getFilepath());
                        selectedImages.add(si.getFilepath());
                    }
                }
                Log.d("ImageForSelectionSIZE", Integer.toString(selectedImages.size()));


                Intent intent = new Intent(FetchImageActivity.this, GameActivity.class);
                int k = 1;
                for (String path : selectedImages) {
                    Log.d("FILENAME", path);
                    String key = Integer.toString(k);
                    intent.putExtra(key, path);
                    startActivity(intent);
                    k++;
                }
            }
        });

        fetchBtn = findViewById(R.id.fetch);
        fetchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bar.getVisibility() == View.INVISIBLE) {
                    bar.setVisibility(View.VISIBLE);
                }

                fullImageReference = new ArrayList<ImageForSelection>();
                initGridReferences();

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
                        iv.setAlpha(1.0f);
                        iv.setImageResource(R.drawable.white);
                    }
                }
                if (downloadTask != null)
                    downloadTask.cancel(true);

                htmlTxt = (EditText) findViewById(R.id.htmlurl);

                htmlcode = stocksnap + htmlTxt.getText().toString();

                Log.d("HTML", htmlcode);
                fetchservice = new AsyncHtmlSourcecode(FetchImageActivity.this);
                fetchservice.execute(htmlcode);
            }
        });

        dogBtn = findViewById(R.id.dog);
        dogBtn.setOnClickListener(new View.OnClickListener() {
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

                String dog = "dog";

                htmlcode = stocksnap + dog;

                Log.d("HTML", htmlcode);
                fetchservice = new AsyncHtmlSourcecode(FetchImageActivity.this);
                fetchservice.execute(htmlcode);
            }
        });

        bearBtn = findViewById(R.id.bear);
        bearBtn.setOnClickListener(new View.OnClickListener() {
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

                String bear = "bear";

                htmlcode = stocksnap + bear;

                Log.d("HTML", htmlcode);
                fetchservice = new AsyncHtmlSourcecode(FetchImageActivity.this);
                fetchservice.execute(htmlcode);
            }
        });

        catBtn = findViewById(R.id.cat);
        catBtn.setOnClickListener(new View.OnClickListener() {
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

                String cat = "cat";

                htmlcode = stocksnap +cat;

                Log.d("HTML", htmlcode);
                fetchservice = new AsyncHtmlSourcecode(FetchImageActivity.this);
                fetchservice.execute(htmlcode);
            }
        });

        natureBtn = findViewById(R.id.nature);
        natureBtn.setOnClickListener(new View.OnClickListener() {
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

                String nature = "nature";

                htmlcode = stocksnap +nature;

                Log.d("HTML", htmlcode);
                fetchservice = new AsyncHtmlSourcecode(FetchImageActivity.this);
                fetchservice.execute(htmlcode);
            }
        });

        beachBtn = findViewById(R.id.beach);
        beachBtn.setOnClickListener(new View.OnClickListener() {
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

                String beach = "beach";

                htmlcode = stocksnap +beach;

                Log.d("HTML", htmlcode);
                fetchservice = new AsyncHtmlSourcecode(FetchImageActivity.this);
                fetchservice.execute(htmlcode);
            }
        });





    }

    /// CHOOSE IMAGES

    public void fromGameReload() {
        for (int i = 1; i < imagePaths.length; i++) {
            Log.d("BACKFROMGAME ", Integer.toString(i) + "RELOAD");
            // to convert the image to bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(imagePaths[i]);
            // to display the image on screen
            String imageViewId = "img" + String.format("%02d", i);
            Log.d("URL", imageViewId);
            int resId = getResources().getIdentifier(imageViewId, "id", getPackageName());
            ImageView imageView = findViewById(resId);
            imageView.setImageBitmap(bitmap);
        }
    }

    public void initGridReferences() {
        for (int i = 1; i < 21; i++) {
            String name = "img" + String.format("%02d", i);
            int resourceId = this.getResources().getIdentifier(name, "id", this.getPackageName());
            ImageView imageview = (ImageView) findViewById(resourceId);
            String filepath = getFilesDir() + "/image" + i + ".jpg";
            ImageForSelection imageForSelection = new ImageForSelection(imageview, filepath);
            fullImageReference.add(imageForSelection);
        }
    }


    public void selectedImage(View v) {
        ImageView selectedview = (ImageView) findViewById(v.getId());
        ImageForSelection selected = new ImageForSelection();
        int alreadyselected = 0;
        playbtn.setEnabled(false);

        // selected number 6 cannot return
        for (ImageForSelection ifs : fullImageReference) {
            if (ifs.getImg() == selectedview) {
                ifs.setSelected(!ifs.isSelected);
                if (selectedview.getAlpha() == 0.3f) {
                    selectedview.setAlpha(1.0f);
                } else {
                    selectedview.setAlpha(0.3f);
                }
            }
        }

        for (ImageForSelection ifs : fullImageReference) {
            if (ifs.isSelected) {
                alreadyselected = alreadyselected + 1;
                if (alreadyselected > 6) {
                    for (ImageForSelection ifschange : fullImageReference) {
                        if (ifschange.getImg() == selectedview) {
                            ifschange.setSelected(!ifschange.isSelected);
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

                Log.d("INCREMENT?", Integer.toString(selectednumber));
                if (selectednumber == 6) {
                    Log.d("YAY ", "NEXT BUTTON WILL APPEAR");
                    playbtn.setEnabled(true);



//                    for (ImageForSelection si : fullImageReference) {
//                        if (si.isSelected) {
//                            Log.d("FILENAMEELVL1", si.getFilepath());
//                            selectedImages.add(si.getFilepath());
//                        }
//                    }
//
//                    Log.d("ImageForSelectionSIZE", Integer.toString(selectedImages.size()));
//
//                    Intent intent = new Intent(FetchImageActivity.this, GameActivity.class);
//                    int k = 1;
//                    for (String path : selectedImages) {
//                        Log.d("FILENAMEE", path);
//                        String key = Integer.toString(k);
//                        intent.putExtra(key, path);
//                        startActivity(intent);
//                        k++;
//                    }
//                    startActivity(intent);

                }
            }
        }
        Log.d("END", Integer.toString(selectednumber));
    }

    // CLASS
    public class ImageForSelection {
        private ImageView img;
        private boolean isSelected = false;
        private String filepath = "";

        public ImageForSelection() {
        }

        public ImageForSelection(ImageView img, String filepath) {
            this.img = img;
            this.filepath = filepath;
            this.isSelected = false;
        }

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
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

