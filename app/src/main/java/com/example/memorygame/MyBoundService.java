package com.example.memorygame;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyBoundService extends Service {
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        MyBoundService getService() {
            return MyBoundService.this;
        }
    }

    public interface IServiceCallback {
        void onServiceProgress(int percent, int i);
        void onServiceDone(int i);
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("onBind... ");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("onUnbind... ");
        return super.onUnbind(intent);
    }

    public void downloadImage(final IServiceCallback callback,
                              final String imageURL, final String fpath, final int i) {
        Log.d("START", Integer.toString(i));


        if (callback == null)
            return;

        new Thread(new Runnable() {
            @Override
            public void run() {

                long imageLen = 0;
                long totalSoFar = 0;
                int readLen = 0;

                try {
                    URL url = new URL(imageURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    imageLen = conn.getContentLength();
                    byte[] data = new byte[1024];

                    InputStream in = url.openStream();
                    BufferedInputStream bufIn = new BufferedInputStream(in, 2048);
                    OutputStream out = new FileOutputStream(fpath);

                    while ((readLen = bufIn.read(data)) != -1) {
                        totalSoFar += readLen;
                        out.write(data, 0, readLen);

                        int percentint = (int) ((totalSoFar * 100) / imageLen);


                        Log.d("onServiceProgress", Integer.toString(i));
                        callback.onServiceProgress(percentint, i );
                    }
                    Log.d("onServiceDone", Integer.toString(i));
                    callback.onServiceDone(i);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
