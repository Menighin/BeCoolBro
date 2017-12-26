package com.onsoftwares.zensource.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.onsoftwares.zensource.R;
import com.onsoftwares.zensource.enums.LanguagesEnum;
import com.onsoftwares.zensource.enums.SharedPreferencesEnum;
import com.onsoftwares.zensource.utils.ZenSourceUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationFragment extends Fragment {

    private Spinner mSpinner;


    public ConfigurationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_configuration, container, false);

        mSpinner = (Spinner) v.findViewById(R.id.config_spinner);

        ArrayList<String> items = new ArrayList<String>();

        for (LanguagesEnum l : LanguagesEnum.values()) {
            String strValue = getResources().getString(getResources().getIdentifier("str_" + l.value(), "string", getContext().getPackageName()));

            items.add(strValue);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, items);

        mSpinner.setAdapter(adapter);

        // Get the current saved Language
        String savedLanguage = ZenSourceUtils.getSharedPreferencesValue(getContext(), SharedPreferencesEnum.LANGUAGE.value(), String.class);
        String savedValue = getResources().getString(getResources().getIdentifier("str_" + savedLanguage, "string", getContext().getPackageName()));

        int position = items.indexOf(savedValue);
        if (position < 0) position = 0;
        mSpinner.setSelection(position);

        return v;
    }

}
