package com.onsoftwares.zensource.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onsoftwares.zensource.R;
import com.onsoftwares.zensource.adapters.HomeCardRecyclerAdapter;
import com.onsoftwares.zensource.interfaces.OnLoadMoreListener;
import com.onsoftwares.zensource.interfaces.OnZenCardAction;
import com.onsoftwares.zensource.models.ZenCardModel;
import com.onsoftwares.zensource.utils.ZenSourceUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LikedQuotesFragment extends Fragment implements OnLoadMoreListener, OnZenCardAction {


    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private TextView numberLikedQuotes;
    private List<ZenCardModel> likedList;
    private List<String> likedIds;
    private HomeCardRecyclerAdapter recyclerAdapter;
    private int page = 1;
    private int perPage = 5;

    public LikedQuotesFragment() {
        // Required empty public constructor
        likedList = new ArrayList<ZenCardModel>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_liked_quotes, container, false);

        likedIds = Arrays.asList(ZenSourceUtils.getSharedPreferencesValue(getActivity(), getString(R.string.shared_preferences_liked), String.class).split(";"));

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        numberLikedQuotes = (TextView) view.findViewById(R.id.number_liked_quotes);

        numberLikedQuotes.setText(likedIds.size() + " " + getResources().getString(R.string.liked_number));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new HomeCardRecyclerAdapter(getContext(), likedList, recyclerView);

        recyclerView.setAdapter(recyclerAdapter);

        initList();

        return view;
    }

    private void initList() {
        // TODO: Implement this right
        for (int i = 0; i < perPage; i++) {
            likedList.add(new ZenCardModel());
        }
        recyclerAdapter.notifyDataSetChanged();
        page++;
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onLike(ZenCardModel z) {

    }

    @Override
    public void onDislike(ZenCardModel z) {

    }

    @Override
    public void onCardClick(ZenCardModel z, View v) {

    }

    @Override
    public void onShare(ImageView imageView) {

    }
}
