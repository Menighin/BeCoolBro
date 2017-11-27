package com.onsoftwares.zensource.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.onsoftwares.zensource.R;
import com.onsoftwares.zensource.models.ZenCardModel;
import com.onsoftwares.zensource.utils.httputil.HttpUtil;

import java.util.concurrent.Callable;

/**
 * Created by joao.menighin on 27/11/2017.
 */

public class ZenCardUtils {

    public static void likeZenQuote(ZenCardModel z, final HttpUtil.CallbackVoid callback) {
        HttpUtil.Builder builder = HttpUtil.Builder()
                .withUrl("http://zensource-dev.sa-east-1.elasticbeanstalk.com/api/zen/" + z.getId() + "/rate")
                .addRequestBody("id", z.getId() + "");


        if (z.isLiked()) return;

        builder.addRequestBody("like", "1");

        if(z.isDisliked())
            builder.addRequestBody("dislike", "-1");
        else
            builder.addRequestBody("dislike", "0");

        builder.ifSuccess(callback).makePut();
    }

    public static void dislikeZenQuote(ZenCardModel z, final HttpUtil.CallbackVoid callback) {
        HttpUtil.Builder builder = HttpUtil.Builder()
                .withUrl("http://zensource-dev.sa-east-1.elasticbeanstalk.com/api/zen/" + z.getId() + "/rate")
                .addRequestBody("id", z.getId() + "");

        if (z.isDisliked()) return;

        builder.addRequestBody("dislike", "1");

        if(z.isLiked())
            builder.addRequestBody("like", "-1");
        else
            builder.addRequestBody("like", "0");

        builder.ifSuccess(callback).makePut();
    }

}
