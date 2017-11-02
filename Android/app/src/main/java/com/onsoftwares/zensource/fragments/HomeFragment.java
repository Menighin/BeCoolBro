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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.onsoftwares.zensource.R;
import com.onsoftwares.zensource.adapters.HomeCardRecyclerAdapter;
import com.onsoftwares.zensource.interfaces.OnLoadMoreListener;
import com.onsoftwares.zensource.models.ZenCardModel;
import com.onsoftwares.zensource.utils.httputil.HttpUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnLoadMoreListener{

    private RecyclerView homeCardRecyclerView;
    private List<ZenCardModel> homeCardsList;
    private HomeCardRecyclerAdapter recyclerAdapter;
    private ProgressBar progressBar;
    private int page = 2;

    private boolean loading = false;

    public HomeFragment() {
        // Required empty public constructor
        homeCardsList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.home_progress_bar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        homeCardRecyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_view);
        homeCardRecyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new HomeCardRecyclerAdapter(getContext(), homeCardsList, homeCardRecyclerView);
        recyclerAdapter.setOnLoadMore(this);
        homeCardRecyclerView.setAdapter(recyclerAdapter);

        // Request for the data of the recycler view
        loading = true;
        recyclerAdapter.setLoading(true);
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
                            progressBar.setVisibility(View.INVISIBLE);
                            loading = false;
                            recyclerAdapter.setLoading(false);

                        }
                    });
                }
            })
            .makeGet();

        return view;
    }

    @Override
    public void onLoadMore() {
        if (page != 0) {

            homeCardsList.add(null);

            recyclerAdapter.notifyItemInserted(homeCardsList.size() - 1);

            HttpUtil.Builder()
                    .withUrl("http://zensource-dev.sa-east-1.elasticbeanstalk.com/api/zen/images")
                    .addQueryParameter("page", (page++) + "")
                    .withConverter(new ZenCardModel())
                    .ifSuccess(new HttpUtil.CallbackConverted<List<ZenCardModel>>() {
                        @Override
                        public void callback(final List<ZenCardModel> list) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    //Remove loading item
                                    homeCardsList.remove(homeCardsList.size() - 1);
                                    recyclerAdapter.notifyItemRemoved(homeCardsList.size());

                                    recyclerAdapter.setLoading(false);

                                    homeCardsList.addAll(list);
                                    homeCardRecyclerView.getAdapter().notifyDataSetChanged();
                                    progressBar.setVisibility(View.INVISIBLE);
                                    loading = false;

                                    if (list.size() == 0) page = 0;
                                }
                            });
                        }
                    })
                    .makeGet();
        }
    }
}
