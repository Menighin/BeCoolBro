package com.onsoftwares.zensource.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.onsoftwares.zensource.utils.ZenCardUtils;
import com.onsoftwares.zensource.utils.ZenSourceUtils;
import com.onsoftwares.zensource.utils.httputil.HttpUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
        likedIds = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_liked_quotes, container, false);

        String likedQuotes = ZenSourceUtils.getSharedPreferencesValue(getActivity(), getString(R.string.shared_preferences_liked), String.class);

        if (likedQuotes != null)
            likedIds = Arrays.asList(likedQuotes.split(";"));

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        numberLikedQuotes = (TextView) view.findViewById(R.id.number_liked_quotes);

        numberLikedQuotes.setText(likedIds.size() + " " + getResources().getString(R.string.liked_number));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new HomeCardRecyclerAdapter(getContext(), likedList, recyclerView);

        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    @Override
    public void onLoadMore() {
		// TODO: Implement this right
		if (page * perPage < likedIds.size()) {
			
			for (int i = page * perPage; i < (page + 1) * perPage; i++) {
				likedList.add(new ZenCardModel());
			}
			recyclerAdapter.notifyDataSetChanged();
			page++;
			
		}
    }

    @Override
    public void onLike(final ZenCardModel z) {
        // Http Put to like the post
        ZenCardUtils.dislikeZenQuote(z, new HttpUtil.CallbackVoid() {
            @Override
            public void callback() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(recyclerView, getResources().getString(R.string.dislike_success), Snackbar.LENGTH_SHORT).show();
                        likedList.remove(z);
                        recyclerAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        // Update the model
        z.setDisliked(true);
        z.setDislikes(z.getDislikes() + 1);

        if (z.isLiked()) {
            z.setLiked(false);
            z.setLikes(z.getLikes() - 1);
        }

        String likedQuotesStr = ZenSourceUtils.getSharedPreferencesValue(getActivity(), getString(R.string.shared_preferences_liked), String.class);
        HashSet<String> likedQuotes = likedQuotesStr == null ? new HashSet<String>() : new HashSet<String>(Arrays.asList(likedQuotesStr.split(";")));

        String dislikedQuotesStr = ZenSourceUtils.getSharedPreferencesValue(getActivity(), getString(R.string.shared_preferences_disliked), String.class);
        HashSet<String> dislikedQuotes = likedQuotesStr == null ? new HashSet<String>() : new HashSet<String>(Arrays.asList(dislikedQuotesStr.split(";")));

        // If it was liked, it is being disliked now and vice-versa
        String id = z.getId() + "";

        if (likedQuotes.contains(id))
            likedQuotes.remove(id);

        if (dislikedQuotes.contains(id))
            dislikedQuotes.remove(id);
        else
            dislikedQuotes.add(id);

        ZenSourceUtils.setSharedPreferenceValue(getActivity(), getString(R.string.shared_preferences_liked), TextUtils.join(";", likedQuotes), String.class);
        ZenSourceUtils.setSharedPreferenceValue(getActivity(), getString(R.string.shared_preferences_disliked), TextUtils.join(";", dislikedQuotes), String.class);
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
