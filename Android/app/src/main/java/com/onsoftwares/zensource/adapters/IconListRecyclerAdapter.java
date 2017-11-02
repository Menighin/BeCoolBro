package com.onsoftwares.zensource.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onsoftwares.zensource.R;

import java.util.List;

public class IconListRecyclerAdapter extends RecyclerView.Adapter<IconListRecyclerAdapter.IconListViewHolder> {

    private final Context mContext;
    private final List<ItemModel> dataList;
    private final OnItemClickListener listener;
    private int selectedItem = 0;

    public IconListRecyclerAdapter(Context mContext, List<ItemModel> dataList, OnItemClickListener listener) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.listener = listener;
    }

    @Override
    public IconListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IconListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.navigation_drawer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(IconListViewHolder holder, int position) {
        ItemModel itemModel = dataList.get(position);
        holder.getLabel().setText(itemModel.getLabel());
        holder.getIcon().setImageResource(itemModel.getIcon());
        holder.setSelected(selectedItem == position);
        holder.bind(itemModel, listener, position, this);
        holder.itemView.setBackgroundColor(selectedItem == position ? mContext.getResources().getColor(R.color.navBackgroundHighlight) : mContext.getResources().getColor(R.color.navBackground));
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public interface OnItemClickListener {
        void onItemClick(ItemModel item);
    }

    public static class ItemModel {
        private int id;
        private String label;
        private int icon;
        private boolean selected;

        public ItemModel(int id, String label, int icon) {
            this.id = id;
            this.label = label;
            this.icon = icon;
            this.selected = false;
        }

        public ItemModel(int id, String label, int icon, boolean selected) {
            this.id = id;
            this.label = label;
            this.icon = icon;
            this.selected = selected;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    static class IconListViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView label;
        private ImageView icon;

        public View getItemView() {
            return itemView;
        }

        public void setItemView(View itemView) {
            this.itemView = itemView;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        private boolean selected;

        public IconListViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            label = (TextView) itemView.findViewById(R.id.navigation_drawer_item_label);
            icon = (ImageView) itemView.findViewById(R.id.navigation_drawer_item_icon);
        }

        public void bind(final ItemModel item, final OnItemClickListener listener, final int selectedPos, final IconListRecyclerAdapter adapter) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.notifyItemChanged(adapter.selectedItem);
                    listener.onItemClick(item);
                    adapter.selectedItem = selectedPos;
                    adapter.notifyItemChanged(selectedPos);
                }
            });
        }

        public TextView getLabel() {
            return label;
        }

        public void setLabel(TextView label) {
            this.label = label;
        }

        public ImageView getIcon() {
            return icon;
        }

        public void setIcon(ImageView icon) {
            this.icon = icon;
        }

    }

}
