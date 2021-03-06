package com.onsoftwares.zensource.fragments;


import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.onsoftwares.zensource.R;
import com.onsoftwares.zensource.activities.MainActivity;
import com.onsoftwares.zensource.components.TimePickerFragment;
import com.onsoftwares.zensource.enums.LanguagesEnum;
import com.onsoftwares.zensource.enums.SharedPreferencesEnum;
import com.onsoftwares.zensource.receivers.ZenQuoteReceiver;
import com.onsoftwares.zensource.utils.ZenSourceUtils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationFragment extends Fragment {

    private Spinner mSpinner;
    private Button mEditButton;
    private ArrayList<String> languageOptions;
    private TextView mTimeText;
    private Calendar mSavedTime;

    public ConfigurationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_configuration, container, false);

        mSpinner = (Spinner) v.findViewById(R.id.config_spinner);

        languageOptions = new ArrayList<String>();

        for (LanguagesEnum l : LanguagesEnum.values()) {
            String strValue = getResources().getString(getResources().getIdentifier("str_" + l.value(), "string", getContext().getPackageName()));

            languageOptions.add(strValue);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, languageOptions);

        mSpinner.setAdapter(adapter);

        // Get the current saved Language
        String savedLanguage = ZenSourceUtils.getSharedPreferencesValue(getContext(), SharedPreferencesEnum.LANGUAGE.value(), String.class);
        String savedValue = getResources().getString(getResources().getIdentifier("str_" + savedLanguage, "string", getContext().getPackageName()));

        int position = languageOptions.indexOf(savedValue);
        if (position < 0) position = 0;
        mSpinner.setSelection(position);

        // Callback when spinner changes
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onLanguageSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        mTimeText = (TextView) v.findViewById(R.id.config_time_txt);
        mSavedTime = ZenSourceUtils.getCalendarFromMillis(ZenSourceUtils.getSharedPreferencesValue(getContext(), SharedPreferencesEnum.DAILY_QUOTE.value(), String.class));
        mTimeText.setText(String.format("%02d:%02d", mSavedTime.get(Calendar.HOUR_OF_DAY), mSavedTime.get(Calendar.MINUTE)));

        mEditButton = (Button) v.findViewById(R.id.config_edit_btn);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment newFragment = TimePickerFragment.newInstance(mSavedTime, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                        // Update TextView
                        mTimeText.setText(String.format("%02d:%02d", hour, minute));

                        // Save new value to preferences
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hour);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        mSavedTime = c;
                        ZenSourceUtils.setSharedPreferenceValue(getContext(), SharedPreferencesEnum.DAILY_QUOTE.value(), c.getTimeInMillis() + "", String.class);

                        ZenQuoteReceiver.setupAlarm(getContext(), c.getTimeInMillis(), true);
                    }
                });

                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        setHasOptionsMenu(true);

        return v;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem mSearchMenuItem = menu.findItem(R.id.menu_search);
        mSearchMenuItem.setVisible(false);
    }


    private void onLanguageSelected(int pos) {
        LanguagesEnum savedLanguage = LanguagesEnum.fromStr(ZenSourceUtils.getSharedPreferencesValue(getContext(), SharedPreferencesEnum.LANGUAGE.value(), String.class));

        if ((savedLanguage == LanguagesEnum.ENGLISH && pos == 0) || (savedLanguage == LanguagesEnum.PORTUGUESE && pos == 1)) return;

        LanguagesEnum selectedLanguage = LanguagesEnum.ENGLISH;
        if (pos == 1) selectedLanguage = LanguagesEnum.PORTUGUESE;

        // Saving new language
        ZenSourceUtils.setSharedPreferenceValue(getContext(), SharedPreferencesEnum.LANGUAGE.value(), selectedLanguage.value(), String.class);

        ZenSourceUtils.setLocale(selectedLanguage.value(), getActivity(), MainActivity.class);
    }

}
