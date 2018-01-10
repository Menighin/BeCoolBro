package com.onsoftwares.zensource.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.onsoftwares.zensource.R;
import com.onsoftwares.zensource.activities.MainActivity;
import com.onsoftwares.zensource.enums.LanguagesEnum;
import com.onsoftwares.zensource.enums.SharedPreferencesEnum;
import com.onsoftwares.zensource.utils.ZenSourceUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationFragment extends Fragment {

    private Spinner mSpinner;
    private ArrayList<String> languageOptions;

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
