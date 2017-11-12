package com.onsoftwares.zensource.utils;


import android.app.Activity;
import android.content.Context;
import android.view.View;

public class ZenSourceUtils {

    public static final String PACKKAGE_NAME = "com.onsoftwares.zensource";
    public static final String IMAGE_NAME_ON_CACHE = "zen_quote";

    public static void goFullScreen(Activity act) {
        act.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    public static void restoreFullScreen(Activity act) {
        act.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
