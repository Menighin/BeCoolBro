package com.onsoftwares.zensource.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.DisplayMetrics;
import android.view.View;

import com.onsoftwares.zensource.R;
import com.onsoftwares.zensource.enums.LanguagesEnum;
import com.onsoftwares.zensource.enums.SharedPreferencesEnum;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ZenSourceUtils {

    public static final String PACKKAGE_NAME = "com.onsoftwares.zensource";
    public static final String IMAGE_NAME_ON_CACHE = "zen_quote";

    public static <T> T getSharedPreferencesValue(Context c, String key, Class<T> returnType) {
        SharedPreferences sharedPref = c.getSharedPreferences(SharedPreferencesEnum.SHARED_PREFERENCES_TAG.value(), Context.MODE_PRIVATE);

        if (returnType.equals(String.class)) {
            return (T) sharedPref.getString(key, null);
        } else if (returnType.equals(Integer.class)) {
            return (T) new Integer(sharedPref.getInt(key, -1));
        } else {
            return (T) sharedPref.getString(key, "");
        }
    }

    public static <T> void setSharedPreferenceValue(Context c, String key, T value, Class<T> classToWrite) {
        SharedPreferences sharedPref = c.getSharedPreferences(SharedPreferencesEnum.SHARED_PREFERENCES_TAG.value(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (classToWrite.equals(String.class)) {
            editor.putString(key, value.toString().trim());
        } else if (classToWrite.equals(Integer.class)) {
            editor.putInt(key, (Integer) value);
        }

        editor.commit();
    }

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

    public static Bitmap getCroppedBitmap(Bitmap bitmap, Integer cx, Integer cy, Integer radius) {
        int diam = radius << 1;
        Bitmap targetBitmap = Bitmap.createBitmap(diam, diam, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);
        final int color = 0xff424242;
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(radius, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, -cx + radius, -cy + radius, paint);
        return targetBitmap;
    }

    public static String getLanguageAPICode(Context c) {
        String language = ZenSourceUtils.getSharedPreferencesValue(c, SharedPreferencesEnum.LANGUAGE.value(), String.class);

        if (language.equals(LanguagesEnum.PORTUGUESE.value())) {
            return "pt-br";
        } else {
            return "en";
        }
    }

    public static void setLocale(String lang, Activity a, Class activityClass) {
        Locale myLocale = new Locale(lang);
        Resources res = a.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(a, activityClass);
        a.startActivity(refresh);
        a.finish();
    }

    public static long getMillisForNextDay(int hour, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minutes);
        return calendar.getTimeInMillis();
    }

    public static <T> Calendar getCalendarFromMillis(T millis) {
        Long longMillis = 0L;
        if (millis instanceof String)
            longMillis = Long.parseLong((String) millis);
        else if (millis instanceof Long)
            longMillis = (Long) millis;
        else
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(longMillis);
        return calendar;

    }
}
