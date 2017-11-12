package com.onsoftwares.zensource.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.widget.ImageView;

import com.onsoftwares.zensource.R;

public class ZenCardZoomActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zen_card_zoom);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setBackgroundColor(Color.TRANSPARENT);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        String image64encoded = getIntent().getStringExtra("image64encoded");
        byte[] decodedString = Base64.decode(image64encoded, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        imageView = (ImageView) findViewById(R.id.zen_card_zoom_img);

        imageView.setImageBitmap(decodedByte);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
