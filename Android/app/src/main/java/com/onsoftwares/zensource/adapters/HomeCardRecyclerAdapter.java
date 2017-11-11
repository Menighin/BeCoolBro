package com.onsoftwares.zensource.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.onsoftwares.zensource.R;
import com.onsoftwares.zensource.activities.ZenCardZoomActivity;
import com.onsoftwares.zensource.interfaces.OnLoadMoreListener;
import com.onsoftwares.zensource.models.ZenCardModel;

import java.util.ArrayList;
import java.util.List;

public class HomeCardRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context mContext;
    private List<ZenCardModel> dataList;
    private OnLoadMoreListener onLoadMore;
    private RecyclerView recyclerView;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;

    public HomeCardRecyclerAdapter(Context mContext, List<ZenCardModel> dataList, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.recyclerView = recyclerView;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.recyclerView.getLayoutManager();

        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMore != null) {
                        onLoadMore.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card, parent, false);
            return new HomeCardViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HomeCardViewHolder) {
            final ZenCardModel zenCard = dataList.get(position);

            HomeCardViewHolder homeCardViewHolder = (HomeCardViewHolder) holder;

            if (zenCard.getImage64encoded() != null && zenCard.getImage64encoded().length() > 0) {
                byte[] decodedString = Base64.decode(zenCard.getImage64encoded(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                homeCardViewHolder.getImageView().setImageBitmap(decodedByte);
            }


            homeCardViewHolder.getImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, zenCard.getAuthor(), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(mContext, ZenCardZoomActivity.class);

                    intent.putExtra("image64encoded", zenCard.getImage64encoded());

                    String transitionName = mContext.getString(R.string.transition_zoom_card);

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,
                                    v,   // Starting view
                                    transitionName    // The String
                            );
                    //Start the Intent
                    ActivityCompat.startActivity(mContext, intent, options.toBundle());

                }
            });

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public Context getContext() {
        return mContext;
    }

    public List<ZenCardModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<ZenCardModel> dataList) {
        this.dataList = dataList;
    }

    public void setOnLoadMore(OnLoadMoreListener onLoadMore) {
        this.onLoadMore = onLoadMore;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    static class HomeCardViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private ImageView imageView;
        private Button buttonShare;

        public HomeCardViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.imageView = (ImageView) itemView.findViewById(R.id.home_card_image);
            this.buttonShare = (Button) itemView.findViewById(R.id.home_card_share_btn);
        }

        public View getItemView() {
            return itemView;
        }

        public void setItemView(View itemView) {
            this.itemView = itemView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public Button getButtonShare() {
            return buttonShare;
        }

        public void setButtonShare(Button buttonShare) {
            this.buttonShare = buttonShare;
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.loading_item_progress_bar);
        }
    }

}
