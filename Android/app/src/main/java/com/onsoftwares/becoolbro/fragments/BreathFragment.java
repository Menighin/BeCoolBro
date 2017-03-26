package com.onsoftwares.becoolbro.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onsoftwares.becoolbro.R;
import com.onsoftwares.becoolbro.components.GifView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BreathFragment extends BaseFragment {

    public BreathFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_breath, container, false);

        GifView gifView = (GifView) rootView.findViewById(R.id.viewGif);
        gifView.setImageResource(R.drawable.breath);


        return rootView;
    }

}
