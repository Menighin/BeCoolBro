package com.onsoftwares.becoolbro.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.onsoftwares.becoolbro.interfaces.ImageHandler;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Menighin on 26/03/2017.
 */

public class DownloadImageAsyncTask extends AsyncTask<Void, Integer, Integer> {

    private String urlStr;
    private Bitmap bitmap;
    private ImageHandler imageHandler;

    public DownloadImageAsyncTask(String url, ImageHandler imageHandler) {
        this.urlStr = url;
        this.imageHandler = imageHandler;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }

    @Override
    protected void onPostExecute(Integer result) {
        imageHandler.handleImage(bitmap);
    }
}
