package com.example.memorygame;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class AsyncHtmlSourcecode extends AsyncTask<String, Integer, String[]> {
    private ICallback callback = null;

    public AsyncHtmlSourcecode(ICallback callback) {
        this.callback = callback;
    }

    @Override
    protected String[] doInBackground(String[] urls) {
        String[] imageUrls = new String[21];
        String sourcecode;
        int numOfImages = 0;
        String customUA = "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.63 Safari/537.31";
        try {
            URL url = new URL(urls[0]);
            URLConnection con = null;
            con = url.openConnection();
            String encoding = con.getContentEncoding();
            if (encoding == null) {
                encoding = "ISO-8859-1";
            }
            BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream(), encoding));
            StringBuilder sb = new StringBuilder();
            try {
                String s;
                while ((s = r.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                r.close();
            }
            sourcecode = sb.toString();

        int starthead = sourcecode.indexOf("<head>");
        int endhead = sourcecode.indexOf("</head>");
        String nohead = sourcecode.substring(starthead, endhead);
        sourcecode = sourcecode.substring(starthead + nohead.length());

        for (int i = 0; i < 20; i++) {

            int start = sourcecode.indexOf("img src=\"") + 9;
            int end = sourcecode.indexOf("\"", start);
            String src = sourcecode.substring(start, end);

            sourcecode = sourcecode.substring(sourcecode.indexOf("img src=\"") + src.length(), sourcecode.length());
            imageUrls[i] = src;
            System.out.println(src);
                }
        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return imageUrls;
    }

    @Override
    protected void onProgressUpdate(Integer[] values) {
//        if (callback != null)
//            callback.getSourceCodeProgress(values[0]);

    }

    @Override
    protected void onPostExecute(String[] imageUrls) {

        if (imageUrls == null)
            return;

        if (callback != null) {
            callback.get20ImageUrlsDone(imageUrls);
        }
    }

    public interface ICallback {
        void get20ImageUrlsDone(String[] string);

    }


}
