package com.onsoftwares.becoolbro.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onsoftwares.becoolbro.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TestFragment1 extends BaseFragment {



    public TestFragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_fragment1, container, false);
    }

}
