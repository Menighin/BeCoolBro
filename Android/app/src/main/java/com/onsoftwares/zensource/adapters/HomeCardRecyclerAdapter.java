package com.onsoftwares.zensource.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onsoftwares.zensource.R;
import com.onsoftwares.zensource.activities.ZenCardZoomActivity;
import com.onsoftwares.zensource.interfaces.OnLoadMoreListener;
import com.onsoftwares.zensource.interfaces.OnZenCardAction;
import com.onsoftwares.zensource.models.ZenCardModel;
import com.onsoftwares.zensource.utils.ZenSourceUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeCardRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context mContext;
    private List<ZenCardModel> dataList;
    private OnLoadMoreListener onLoadMore;
    private OnZenCardAction onZenCardAction;
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

            final HomeCardViewHolder homeCardViewHolder = (HomeCardViewHolder) holder;

            if (zenCard.getImage64encoded() != null && zenCard.getImage64encoded().length() > 0 && position != 0) {
                byte[] decodedString = Base64.decode(zenCard.getImage64encoded(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                Bitmap circleBitmap = ZenSourceUtils.getCroppedBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, 100);

                homeCardViewHolder.getImageView().setImageBitmap(circleBitmap);
            }

            homeCardViewHolder.getQuote().setText(zenCard.getMessage());
            homeCardViewHolder.getAuthor().setText(zenCard.getAuthor());
            homeCardViewHolder.getTextLike().setText(zenCard.getLikes() + "");
            homeCardViewHolder.getTextDislike().setText(zenCard.getDislikes() + "");

            // Setting image click
            homeCardViewHolder.getContentView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                onZenCardAction.onCardClick(zenCard, v);
                }
            });

            // Setting like and dislike click
            homeCardViewHolder.getButtonLike().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean likeOn = onZenCardAction.onLike(zenCard);

                    if(likeOn)
                        homeCardViewHolder.getButtonLike().setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_thumb_up));
                    else
                        homeCardViewHolder.getButtonLike().setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_thumb_up_grey));

                }
            });

            // Setting share click
            homeCardViewHolder.getButtonShare().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onZenCardAction.onShare(homeCardViewHolder.getImageView());
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

    public void setOnZenCardAction(OnZenCardAction onZenCardAction) {
        this.onZenCardAction = onZenCardAction;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    static class HomeCardViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private RelativeLayout contentView;
        private ImageView imageView;
        private TextView quote;
        private TextView author;
        private Button buttonShare;
        private ImageButton buttonLike;
        private TextView textLike;
        private ImageButton buttonDislike;
        private TextView textDislike;

        public HomeCardViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.imageView = (ImageView) itemView.findViewById(R.id.home_card_image);
            this.buttonShare = (Button) itemView.findViewById(R.id.home_card_share_btn);
            this.buttonLike = (ImageButton) itemView.findViewById(R.id.home_card_like_btn);
            this.buttonDislike = (ImageButton) itemView.findViewById(R.id.home_card_dislike_btn);
            this.contentView = (RelativeLayout) itemView.findViewById(R.id.home_card_content);
            this.quote = (TextView) itemView.findViewById(R.id.home_card_quote);
            this.author = (TextView) itemView.findViewById(R.id.home_card_author);
            this.textLike = (TextView) itemView.findViewById(R.id.home_card_like_text);
            this.textDislike = (TextView) itemView.findViewById(R.id.home_card_dislike_text);
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

        public RelativeLayout getContentView() {
            return contentView;
        }

        public void setContentView(RelativeLayout contentView) {
            this.contentView = contentView;
        }

        public TextView getQuote() {
            return quote;
        }

        public void setQuote(TextView quote) {
            this.quote = quote;
        }

        public TextView getAuthor() {
            return author;
        }

        public void setAuthor(TextView author) {
            this.author = author;
        }

        public ImageButton getButtonLike() {
            return buttonLike;
        }

        public void setButtonLike(ImageButton buttonLike) {
            this.buttonLike = buttonLike;
        }

        public ImageButton getButtonDislike() {
            return buttonDislike;
        }

        public void setButtonDislike(ImageButton buttonDislike) {
            this.buttonDislike = buttonDislike;
        }

        public TextView getTextLike() {
            return textLike;
        }

        public void setTextLike(TextView textLike) {
            this.textLike = textLike;
        }

        public TextView getTextDislike() {
            return textDislike;
        }

        public void setTextDislike(TextView textDislike) {
            this.textDislike = textDislike;
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
