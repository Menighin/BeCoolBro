package com.onsoftwares.zensource.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.onsoftwares.zensource.R;
import com.onsoftwares.zensource.utils.ZenSourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ZenCardZoomActivity extends AppCompatActivity {

    private View rootView;
    private Toolbar toolbar;
    private ImageView imageView;
    private boolean fullscreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zen_card_zoom);

        rootView = findViewById(R.id.zen_card_zoom_root);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setBackgroundColor(Color.TRANSPARENT);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        String image64encoded = getIntent().getStringExtra("image64encoded");
        byte[] decodedString = Base64.decode(image64encoded, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        imageView = (ImageView) findViewById(R.id.zen_card_zoom_img);

        imageView.setImageBitmap(decodedByte);

        ZenSourceUtils.restoreFullScreen(this);

    }

    public void fullView(View v) {
        if (!fullscreen) {
            ZenSourceUtils.goFullScreen(this);
            toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        } else {
            ZenSourceUtils.restoreFullScreen(this);
            toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        }


        fullscreen = !fullscreen;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.zen_card_zoom_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            shareQuote();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareQuote() {

        // Saving image on Cache
        try {
            File cachePath = new File(getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/" + ZenSourceUtils.IMAGE_NAME_ON_CACHE + ".png"); // overwrites this image every time
            ((BitmapDrawable)imageView.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sharing
        File imagePath = new File(getCacheDir(), "images");
        File newFile = new File(imagePath,  ZenSourceUtils.IMAGE_NAME_ON_CACHE + ".png");
        Uri contentUri = FileProvider.getUriForFile(this, ZenSourceUtils.PACKKAGE_NAME + ".fileprovider", newFile);

        if (contentUri != null) {

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));

        }
    }
}
