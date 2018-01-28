package com.onsoftwares.zensource.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.onsoftwares.zensource.R;
import com.onsoftwares.zensource.activities.ZenCardZoomActivity;
import com.onsoftwares.zensource.enums.SharedPreferencesEnum;
import com.onsoftwares.zensource.fragments.HomeFragment;
import com.onsoftwares.zensource.models.ZenCardModel;
import com.onsoftwares.zensource.receivers.ZenQuoteReceiver;
import com.onsoftwares.zensource.utils.ZenSourceUtils;
import com.onsoftwares.zensource.utils.httputil.HttpUtil;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Menighin on 15/01/2018.
 */

public class ZenQuoteIntentService extends IntentService {

    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";

    public ZenQuoteIntentService() {
        super(ZenQuoteIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                processStartNotification();
                resetAlarm();
            }
        } finally {
            ZenQuoteReceiver.completeWakefulIntent(intent);
        }
    }

    private void resetAlarm() {

        String alarmTimer = ZenSourceUtils.getSharedPreferencesValue(this, SharedPreferencesEnum.DAILY_QUOTE.value(), String.class);
        Calendar lastFired = Calendar.getInstance();
        lastFired.setTimeInMillis(Long.parseLong(alarmTimer));

        lastFired.add(Calendar.DATE, 1);

        ZenSourceUtils.setSharedPreferenceValue(this, SharedPreferencesEnum.DAILY_QUOTE.value(), lastFired.getTimeInMillis() + "", String.class);
        
        ZenQuoteReceiver.setupAlarm(this, lastFired.getTimeInMillis(), false);

    }

    public static Intent createIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context, ZenQuoteIntentService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    private void processStartNotification() {
        Log.i("Broadcast", "Getting random quote");

        // Get random quote and show in notification
        HttpUtil.Builder()
            .withUrl("http://zensource-dev.sa-east-1.elasticbeanstalk.com/api/zen/randomQuote")
            .addQueryParameter("l", ZenSourceUtils.getLanguageAPICode(getApplicationContext()))
            .withConverter(new ZenCardModel.SingleItemConverter())
            .ifSuccess(new HttpUtil.CallbackConverted<ZenCardModel>() {
                @Override
                public void callback(final ZenCardModel zenCard) {

                    Log.i("Broadcast", "Returned from random Quote");

                    try {
                        final NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                        builder.setContentTitle(getResources().getString(R.string.daily_quote_title))
                                .setAutoCancel(true)
                                .setColor(getResources().getColor(R.color.colorAccent))
                                .setContentText(getResources().getString(R.string.daily_quote_content) + " " + zenCard.getAuthor())
                                .setSmallIcon(R.mipmap.zensource_notification)
                                .setVibrate(new long[] { 1000, 1000, 500})
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.zensource_notification));

                        Intent intent = new Intent(getApplicationContext(), ZenCardZoomActivity.class);
                        intent.putExtra("image64encoded", zenCard.getImage64encoded());

                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                                NOTIFICATION_ID,
                                intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);


                        builder.setContentIntent(pendingIntent);
                        final NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(NOTIFICATION_ID, builder.build());
                    }
                    catch(Exception e) {
                        Log.e("Braodcast", e.getMessage());
                        e.printStackTrace();
                    }
                }
            }).makeGet();

    }
}
