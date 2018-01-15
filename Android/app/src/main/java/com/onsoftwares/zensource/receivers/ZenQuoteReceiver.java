package com.onsoftwares.zensource.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.onsoftwares.zensource.enums.BroadcastIntentsEnum;
import com.onsoftwares.zensource.services.ZenQuoteIntentService;

import java.util.Calendar;
import java.util.Date;

public class ZenQuoteReceiver extends WakefulBroadcastReceiver {

    private static final int NOTIFICATIONS_INTERVAL_IN_HOURS = 1;

    public static void setupAlarm(Context context) {
        Log.i("Broadcast", "Alarm set up");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                getTriggerAt(new Date()),
                    6000,
//                NOTIFICATIONS_INTERVAL_IN_HOURS * AlarmManager.INTERVAL_HOUR,
                alarmIntent);
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

    private static long getTriggerAt(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.SECOND, 10);
        return calendar.getTimeInMillis();
    }

    private static PendingIntent getStartPendingIntent(Context context) {
        Intent intent = new Intent(context, ZenQuoteReceiver.class);
        intent.setAction(BroadcastIntentsEnum.GET_RANDOM_QUOTE.value());
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
