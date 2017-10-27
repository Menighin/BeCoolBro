package com.onsoftwares.zensource.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onsoftwares.zensource.R;
import com.onsoftwares.zensource.models.ZenCardModel;
import com.onsoftwares.zensource.utils.httputil.HttpUtil;

import org.json.JSONArray;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private TextView textView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        textView = (TextView) view.findViewById(R.id.home_text);

        HttpUtil.Builder()
            .withUrl("http://zensource-dev.sa-east-1.elasticbeanstalk.com/api/zen/images?page=1")
            .withConverter(new ZenCardModel())
            .ifSuccess(new HttpUtil.CallbackConverted<List<ZenCardModel>>() {
                @Override
                public void callback(final List<ZenCardModel> list) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list.size() > 0) {
                                textView.setText(list.get(0).getAuthor());

                                ImageView image = (ImageView) view.findViewById(R.id.home_image);

                                byte[] decodedString = Base64.decode(list.get(0).getImage64encoded(), Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                                image.setImageBitmap(decodedByte);
                            }
                        }
                    });
                }
            })
            .makeGet();

        return view;
    }

}
