package com.onsoftwares.zensource.interfaces;

import android.view.View;
import android.widget.ImageView;

import com.onsoftwares.zensource.models.ZenCardModel;

/**
 * Created by joao.menighin on 19/11/2017.
 */

public interface OnZenCardAction {
    void onLike(ZenCardModel z);
    void onDislike(ZenCardModel z);
    void onCardClick(ZenCardModel z, View v);
    void onShare(ImageView imageView);
}
