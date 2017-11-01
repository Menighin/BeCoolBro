package com.onsoftwares.zensource.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.onsoftwares.zensource.R;
import com.onsoftwares.zensource.interfaces.OnLoadMoreListener;
import com.onsoftwares.zensource.models.ZenCardModel;

import java.util.ArrayList;
import java.util.List;

public class HomeCardRecyclerAdapter extends RecyclerView.Adapter<HomeCardRecyclerAdapter.HomeCardViewHolder>{

    private final Context mContext;
    private List<ZenCardModel> dataList;
    private OnLoadMoreListener onLoadMore;
    private RecyclerView recyclerView;

    public HomeCardRecyclerAdapter(Context mContext, List<ZenCardModel> dataList, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.recyclerView = recyclerView;

        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0)
                    onLoadMore.onLoadMore();
            }
        });

    }

    @Override
    public HomeCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeCardViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_card, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeCardViewHolder holder, int position) {
        ZenCardModel zenCard = dataList.get(position);

        if (zenCard.getImage64encoded() != null && zenCard.getImage64encoded().length() > 0) {
            byte[] decodedString = Base64.decode(zenCard.getImage64encoded(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            holder.getImageView().setImageBitmap(decodedByte);
        }
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

}
