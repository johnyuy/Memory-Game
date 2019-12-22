package com.example.memorygame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImage extends AsyncTask<String, Integer, Integer> {
    private ICallback callback = null;


    private WeakReference<AppCompatActivity> caller;

    private String[] imagePaths = new String[21];

    public DownloadImage(WeakReference<AppCompatActivity> caller) {
        this.caller = caller;
    }

    public DownloadImage(ICallback callback) {
        this.caller = caller;
    }


    @Override
    protected Integer doInBackground(String... urls) {
        int result = 0;

        try {
            for (int i = 1; i < urls.length; i++) {
                result = i;
                Log.d("URLLENGTH", Integer.toString(urls.length));

                URL url = new URL(urls[i]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream input = connection.getInputStream();
                BufferedInputStream bufIn = new BufferedInputStream(input, 2048); // 2048 is the buffer size

                String imagePath = caller.get().getFilesDir() + "/image" + i + ".jpg";
                imagePaths[i] = imagePath;
                OutputStream out = new FileOutputStream(imagePath); // Creates a file output stream to write to the file with the specified name.

                int readLen;
                byte[] data = new byte[1024];
                while ((readLen = bufIn.read(data)) != -1) {
                    // Writes readLen bytes from the data byte array starting from 0 to the output stream.
                    out.write(data, 0, readLen);
                }

                input.close();
                out.close();

                publishProgress(i);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        FetchImageActivity parent = (FetchImageActivity) caller.get();

        int i = values[0];

        if (i == 1) {
            for (int k = 1; k < 21; k++) {
                String progressbarid = "pb" + String.format("%02d", k);
                int pbId = parent.getResources().getIdentifier(progressbarid, "id", parent.getPackageName());
                ProgressBar pb = parent.findViewById(pbId);
                pb.setVisibility(View.VISIBLE);
            }
        }

        // to convert the image to bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(imagePaths[i]);

        // to display the image on screen
        String imageViewId = "img" + String.format("%02d", i);

        Log.d("URL", imageViewId);

        int resId = parent.getResources().getIdentifier(imageViewId, "id", parent.getPackageName());
        ImageView imageView = parent.findViewById(resId);
        imageView.setImageBitmap(bitmap);

        String progressbarid = "pb" + String.format("%02d", i);

        int pbId = parent.getResources().getIdentifier(progressbarid, "id", parent.getPackageName());
        ProgressBar pb = parent.findViewById(pbId);
        pb.setVisibility(View.GONE);

        String longprogressbarid = "progressBar";
        int longpbId = parent.getResources().getIdentifier(longprogressbarid, "id", parent.getPackageName());
        ProgressBar longpb = parent.findViewById(longpbId);

        int percent = i*100/20;
        Log.d("PERCENTPERCENT", Integer.toString(percent));

        longpb.setProgress(percent);


    }

    @Override
    protected void onPostExecute(Integer result) {
        FetchImageActivity parent = (FetchImageActivity) caller.get();

        String progressbarid = "progressBar";
        int pbId = parent.getResources().getIdentifier(progressbarid, "id", parent.getPackageName());
        ProgressBar pb = parent.findViewById(pbId);
        pb.setVisibility(View.INVISIBLE);

        if (result == null)
            return;

        if (callback != null) {
            callback.onImageProgressDone(result);
        }

    }

    public interface ICallback {
        void onImageProgressDone(Integer result);

    }


}