package com.onsoftwares.zensource.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.onsoftwares.zensource.enums.BroadcastIntentsEnum;

import java.util.Calendar;
import java.util.Date;

public class ZenQuoteReceiver extends WakefulBroadcastReceiver {

    private static final int NOTIFICATIONS_INTERVAL_IN_HOURS = 8;

    public static void setupAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                getTriggerAt(new Date()),
                NOTIFICATIONS_INTERVAL_IN_HOURS * AlarmManager.INTERVAL_HOUR,
                alarmIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Intent serviceIntent = null;

        if (BroadcastIntentsEnum.GET_RANDOM_QUOTE.value().equals(action)) {
            // Start the IntentService
        }

        if (serviceIntent != null) {
            startWakefulService(context, serviceIntent);
        }
    }

    private static long getTriggerAt(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        //calendar.add(Calendar.HOUR, NOTIFICATIONS_INTERVAL_IN_HOURS);
        return calendar.getTimeInMillis();
    }

    private static PendingIntent getStartPendingIntent(Context context) {
//        Intent intent = new Intent(context, NotificationEventReceiver.class);
//        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
//        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return null;
    }

}
