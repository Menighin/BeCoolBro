package com.onsoftwares.zensource.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onsoftwares.zensource.R;
import com.onsoftwares.zensource.adapters.HomeCardRecyclerAdapter;
import com.onsoftwares.zensource.models.ZenCardModel;
import com.onsoftwares.zensource.utils.httputil.HttpUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView homeCardRecyclerView;
    private List<ZenCardModel> homeCardsList;
    private HomeCardRecyclerAdapter recyclerAdapter;

    public HomeFragment() {
        // Required empty public constructor
        homeCardsList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        homeCardRecyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_view);
        homeCardRecyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new HomeCardRecyclerAdapter(getContext(), homeCardsList);
        homeCardRecyclerView.setAdapter(recyclerAdapter);

        // Request for the data of the recycler view
        HttpUtil.Builder()
            .withUrl("http://zensource-dev.sa-east-1.elasticbeanstalk.com/api/zen/images?page=1")
            .withConverter(new ZenCardModel())
            .ifSuccess(new HttpUtil.CallbackConverted<List<ZenCardModel>>() {
                @Override
                public void callback(final List<ZenCardModel> list) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            homeCardsList.addAll(list);
                            homeCardRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    });
                }
            })
            .makeGet();

        return view;
    }

}
