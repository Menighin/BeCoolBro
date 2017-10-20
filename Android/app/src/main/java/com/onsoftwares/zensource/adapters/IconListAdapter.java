package com.onsoftwares.zensource.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.onsoftwares.zensource.R;

import java.util.ArrayList;

public class IconListAdapter extends ArrayAdapter<IconListAdapter.ItemModel> {

    private ArrayList<ItemModel> dataSet;
    private Context mContext;
    private int lastPosition = -1;

    public IconListAdapter(ArrayList<ItemModel> data, Context context) {
        super(context, R.layout.navigation_drawer_item, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ItemModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.navigation_drawer_item, parent, false);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.navigation_drawer_item_icon);
            viewHolder.label = (TextView) convertView.findViewById(R.id.navigation_drawer_item_label);

            result = convertView;

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.label.setText(dataModel.getLabel());

        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView label;
        ImageView icon;
    }

    public static class ItemModel {
        private String label;
        private String icon;

        public ItemModel(String label, String icon) {
            this.label = label;
            this.icon = icon;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

}
