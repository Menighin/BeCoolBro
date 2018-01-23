package com.onsoftwares.zensource.components;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Menighin on 17/01/2018.
 */

public class TimePickerFragment extends DialogFragment {

    private Calendar mTime;
    private TimePickerDialog.OnTimeSetListener mListener;

    public static TimePickerFragment newInstance(Calendar c, TimePickerDialog.OnTimeSetListener listener) {
        TimePickerFragment f = new TimePickerFragment();

        f.setup(c, listener);

        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        if (mTime == null) mTime = Calendar.getInstance();

        int hour = mTime.get(Calendar.HOUR_OF_DAY);
        int minute = mTime.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), mListener, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    private void setup(Calendar c, TimePickerDialog.OnTimeSetListener listener) {
        mTime = c;
        mListener = listener;
    }


}
