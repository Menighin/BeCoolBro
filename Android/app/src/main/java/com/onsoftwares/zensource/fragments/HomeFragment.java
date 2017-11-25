package com.onsoftwares.zensource.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.onsoftwares.zensource.R;
import com.onsoftwares.zensource.activities.ZenCardZoomActivity;
import com.onsoftwares.zensource.adapters.HomeCardRecyclerAdapter;
import com.onsoftwares.zensource.interfaces.OnLoadMoreListener;
import com.onsoftwares.zensource.interfaces.OnZenCardAction;
import com.onsoftwares.zensource.models.ZenCardModel;
import com.onsoftwares.zensource.utils.ZenSourceUtils;
import com.onsoftwares.zensource.utils.httputil.HttpUtil;

import org.json.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnLoadMoreListener, OnZenCardAction{

    private RecyclerView homeCardRecyclerView;
    private SwipeRefreshLayout homeCardSwipeRefreshLayout;
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

        homeCardSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_swipe_refresh);

        homeCardRecyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_view);
        homeCardRecyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new HomeCardRecyclerAdapter(getContext(), homeCardsList, homeCardRecyclerView);
        recyclerAdapter.setOnLoadMore(this);
        recyclerAdapter.setOnZenCardAction(this);
        homeCardRecyclerView.setAdapter(recyclerAdapter);

        // First populate the view
        refreshHomeView(null);

        homeCardSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHomeView(new Callable() {
                    @Override
                    public Object call() throws Exception {
                        homeCardSwipeRefreshLayout.setRefreshing(false);
                        return null;
                    }
                });
            }
        });

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

    @Override
    public void onLike(ZenCardModel z) {

        callLikeDislikeAction(z, true);

        // Update the model
        z.setLiked(true);
        z.setLikes(z.getLikes() + 1);

        if (z.isDisliked()) {
            z.setDisliked(false);
            z.setDislikes(z.getDislikes() - 1);
        }

        String likedQuotesStr = ZenSourceUtils.getSharedPreferencesValue(getActivity(), getString(R.string.shared_preferences_liked), String.class);
        HashSet<String> likedQuotes = likedQuotesStr == null ? new HashSet<String>() : new HashSet<String>(Arrays.asList(likedQuotesStr.split(";")));

        String dislikedQuotesStr = ZenSourceUtils.getSharedPreferencesValue(getActivity(), getString(R.string.shared_preferences_disliked), String.class);
        HashSet<String> dislikedQuotes = likedQuotesStr == null ? new HashSet<String>() : new HashSet<String>(Arrays.asList(dislikedQuotesStr.split(";")));

        // If it was liked, it is being disliked now and vice-versa
        String id = z.getId() + "";

        if (dislikedQuotes.contains(id))
            dislikedQuotes.remove(id);

        if (likedQuotes.contains(id))
            likedQuotes.remove(id);
        else
            likedQuotes.add(id);

        ZenSourceUtils.setSharedPreferenceValue(getActivity(), getString(R.string.shared_preferences_liked), TextUtils.join(";", likedQuotes), String.class);
        ZenSourceUtils.setSharedPreferenceValue(getActivity(), getString(R.string.shared_preferences_disliked), TextUtils.join(";", dislikedQuotes), String.class);
    }

    @Override
    public void onDislike(ZenCardModel z) {

        callLikeDislikeAction(z, false);

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
        boolean valueToReturn = !likedQuotes.contains(id);

        if (likedQuotes.contains(id))
            likedQuotes.remove(id);

        if (dislikedQuotes.contains(id))
            dislikedQuotes.remove(id);
        else
            dislikedQuotes.add(id);

        ZenSourceUtils.setSharedPreferenceValue(getActivity(), getString(R.string.shared_preferences_liked), TextUtils.join(";", likedQuotes), String.class);
        ZenSourceUtils.setSharedPreferenceValue(getActivity(), getString(R.string.shared_preferences_disliked), TextUtils.join(";", dislikedQuotes), String.class);
    }

    private void callLikeDislikeAction(ZenCardModel z, final boolean liked) {
        HttpUtil.Builder builder = HttpUtil.Builder()
                .withUrl("http://zensource-dev.sa-east-1.elasticbeanstalk.com/api/zen/" + z.getId() + "/rate")
                .addRequestBody("id", z.getId() + "");


        if ((z.isLiked() && liked) || (z.isDisliked() && !liked)) return;

        if (liked) {
            builder.addRequestBody("like", "1");

            if(z.isDisliked())
                builder.addRequestBody("dislike", "-1");
            else
                builder.addRequestBody("dislike", "0");
        } else {
            builder.addRequestBody("dislike", "1");

            if(z.isLiked())
                builder.addRequestBody("like", "-1");
            else
                builder.addRequestBody("like", "0");
        }


        builder.ifSuccess(new HttpUtil.CallbackVoid() {
            @Override
            public void callback() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (liked)
                            Snackbar.make(homeCardRecyclerView, getResources().getString(R.string.like_success), Snackbar.LENGTH_SHORT).show();
                        else
                            Snackbar.make(homeCardRecyclerView, getResources().getString(R.string.dislike_success), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        }).makePut();
    }

    @Override
    public void onShare(ImageView imageView) {
        // Saving image on Cache
        try {
            File cachePath = new File(getActivity().getCacheDir(), "images");
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
        File imagePath = new File(getActivity().getCacheDir(), "images");
        File newFile = new File(imagePath,  ZenSourceUtils.IMAGE_NAME_ON_CACHE + ".png");
        Uri contentUri = FileProvider.getUriForFile(getActivity(), ZenSourceUtils.PACKKAGE_NAME + ".fileprovider", newFile);

        if (contentUri != null) {

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setDataAndType(contentUri, getActivity().getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));

        }
    }

    @Override
    public void onCardClick(ZenCardModel z, View v) {
        Intent intent = new Intent(getActivity(), ZenCardZoomActivity.class);

        intent.putExtra("image64encoded", z.getImage64encoded());

        String transitionName = getActivity().getString(R.string.transition_zoom_card);

        ImageView startView = (ImageView) v.findViewById(R.id.home_card_image);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) getActivity(),
                startView,   // Starting view
                transitionName    // The String
        );
        //Start the Intent
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    private void refreshHomeView(final Callable callback) {

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

                            String likedQuotesStr = ZenSourceUtils.getSharedPreferencesValue(getActivity(), getString(R.string.shared_preferences_liked), String.class);
                            HashSet<String> likedQuotes = likedQuotesStr == null ? new HashSet<String>() : new HashSet<String>(Arrays.asList(likedQuotesStr.split(";")));

                            String dislikedQuotesStr = ZenSourceUtils.getSharedPreferencesValue(getActivity(), getString(R.string.shared_preferences_disliked), String.class);
                            HashSet<String> dislikedQuotes = likedQuotesStr == null ? new HashSet<String>() : new HashSet<String>(Arrays.asList(dislikedQuotesStr.split(";")));

                            for (int i = 0; i < list.size(); i++) {
                                list.get(i).setLikedState(likedQuotes, dislikedQuotes);
                            }

                            homeCardsList.clear();
                            page = 2;

                            homeCardsList.addAll(list);
                            homeCardRecyclerView.getAdapter().notifyDataSetChanged();
                            progressBar.setVisibility(View.INVISIBLE);
                            loading = false;
                            recyclerAdapter.setLoading(false);

                            if (callback != null)
                                try {
                                    callback.call();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                        }
                    });
                }
            })
            .makeGet();
    }
}
