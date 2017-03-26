package com.onsoftwares.becoolbro.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.onsoftwares.becoolbro.R;
import com.onsoftwares.becoolbro.interfaces.ImageHandler;
import com.onsoftwares.becoolbro.utils.DownloadImageAsyncTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZenImagesFragment extends Fragment implements ImageHandler {

    private ImageView imageView;

    public ZenImagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_zen_images, container, false);

        imageView = (ImageView) view.findViewById(R.id.zen_image);
        new DownloadImageAsyncTask("https://pbs.twimg.com/profile_images/616076655547682816/6gMRtQyY.jpg", this).execute();

        return view;
    }

    @Override
    public void handleImage(Bitmap image) {
        imageView.setImageBitmap(image);
    }
}
