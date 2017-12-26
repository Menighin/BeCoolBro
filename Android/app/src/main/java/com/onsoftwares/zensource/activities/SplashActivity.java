package com.onsoftwares.zensource.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.onsoftwares.zensource.enums.SharedPreferencesEnum;
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
    }

}
