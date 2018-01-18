package com.onsoftwares.zensource.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.onsoftwares.zensource.enums.SharedPreferencesEnum;
import com.onsoftwares.zensource.receivers.ZenQuoteReceiver;
import com.onsoftwares.zensource.utils.ZenSourceUtils;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String language = ZenSourceUtils.getSharedPreferencesValue(this, SharedPreferencesEnum.LANGUAGE.value(), String.class);

        if (language == null) {
            language = getResources().getConfiguration().locale.getLanguage();
            ZenSourceUtils.setSharedPreferenceValue(this, SharedPreferencesEnum.LANGUAGE.value(), language, String.class);
        }

        ZenSourceUtils.setLocale(language, this, MainActivity.class);

        // Setting up the daily quote reminder if it doesnt exists
        String alarmTimer = ZenSourceUtils.getSharedPreferencesValue(this, SharedPreferencesEnum.DAILY_QUOTE.value(), String.class);

        if (alarmTimer == null) {
            alarmTimer = ZenSourceUtils.getMillisForNextDay(22, 50) + "";
            ZenSourceUtils.setSharedPreferenceValue(this, SharedPreferencesEnum.DAILY_QUOTE.value(), alarmTimer, String.class);
        }

        ZenQuoteReceiver.setupAlarm(this, Long.parseLong(alarmTimer));

    }

}
