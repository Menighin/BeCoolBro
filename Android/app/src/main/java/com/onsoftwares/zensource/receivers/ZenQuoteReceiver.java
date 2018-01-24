package com.onsoftwares.zensource.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import com.onsoftwares.zensource.enums.BroadcastIntentsEnum;
import com.onsoftwares.zensource.services.ZenQuoteIntentService;

import java.util.Calendar;
import java.util.Date;

public class ZenQuoteReceiver extends WakefulBroadcastReceiver {

    public static void setupAlarm(Context context, long timeMillis, boolean feedback) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                timeMillis,
                alarmIntent);

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeMillis);

        if (feedback)
            Toast.makeText(context, "Daily quote set to " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Broadcast", "Received intent: " + intent.getAction());
        String action = intent.getAction();
        Intent serviceIntent = null;

        if (BroadcastIntentsEnum.GET_RANDOM_QUOTE.value().equals(action)) {
            Log.i("Broadcast", "onReceive from alarm, starting notification service");
            serviceIntent = ZenQuoteIntentService.createIntentStartNotificationService(context);
        }

        if (serviceIntent != null) {
            startWakefulService(context, serviceIntent);
        }
    }

    private static PendingIntent getStartPendingIntent(Context context) {
        Intent intent = new Intent(context, ZenQuoteReceiver.class);
        intent.setAction(BroadcastIntentsEnum.GET_RANDOM_QUOTE.value());
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
